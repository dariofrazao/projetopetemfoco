package projetaobcc20172.com.projetopetemfoco.activity;

import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;

import projetaobcc20172.com.projetopetemfoco.R;
import projetaobcc20172.com.projetopetemfoco.config.ConfiguracaoFirebase;
import projetaobcc20172.com.projetopetemfoco.helper.Base64Custom;
import projetaobcc20172.com.projetopetemfoco.helper.Preferencias;
import projetaobcc20172.com.projetopetemfoco.model.Usuario;

public class CadastroUsuarioActivity extends AppCompatActivity {

    private EditText nome, email, senha, senha2;
    private Button botaoCadastrar;
    private Usuario usuario;


    private FirebaseAuth autenticacao;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_usuario);

        Toolbar toolbar;
        toolbar = (Toolbar) findViewById(R.id.tb_cadastro);
        nome = (EditText)findViewById(R.id.editText_nome);
        email = (EditText)findViewById(R.id.editText_email);
        senha = (EditText)findViewById(R.id.editText_senha);
        senha2 = (EditText)findViewById(R.id.editText_senha2);
        botaoCadastrar = (Button)findViewById(R.id.botao_cadastrar);

        botaoCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Verifica se o campo nome esta vazio
                if(!nome.getText().toString().isEmpty()) {
                    //Verifica se o campo email esta vazio
                    if (!email.getText().toString().isEmpty()) {
                        //Verifica se campo senha esta vazio
                        if (!senha.getText().toString().isEmpty()) {
                            //Verifica se campo senha2 esta vazio
                            if (!senha2.getText().toString().isEmpty()) {
                                //Verifica se as senhas são iguais
                                if (senha.getText().toString().equals(senha2.getText().toString())) {
                                    usuario = new Usuario();
                                    usuario.setNome(nome.getText().toString());
                                    usuario.setEmail(email.getText().toString());
                                    usuario.setSenha(senha.getText().toString());
                                    cadastrarUsuario();
                                } else {
                                    Toast.makeText(CadastroUsuarioActivity.this, "As senhas devem ser iguais", Toast.LENGTH_LONG).show();
                                }
                            } else {
                                Toast.makeText(CadastroUsuarioActivity.this, "A Senha de confirmação deve ser preenchida", Toast.LENGTH_LONG).show();
                            }
                        } else {
                            Toast.makeText(CadastroUsuarioActivity.this, "A Senha deve ser preenchida", Toast.LENGTH_LONG).show();
                        }
                    } else {
                        Toast.makeText(CadastroUsuarioActivity.this, "O E-mail deve ser preenchido", Toast.LENGTH_LONG).show();
                    }
                }else{
                    Toast.makeText(CadastroUsuarioActivity.this, "O Nome deve ser preenchido", Toast.LENGTH_LONG).show();
                }
            }
        });

        // Configura toolbar
        toolbar.setTitle("Cadastro de Usuário");
        toolbar.setTitleTextColor(Color.WHITE);
        toolbar.setNavigationIcon(R.drawable.ic_action_arrow_left_white);
        setSupportActionBar(toolbar);

        senha2.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus && !senha.getText().toString().equals(senha2.getText().toString())) {
                    Toast.makeText(CadastroUsuarioActivity.this, "As senhas devem ser iguais", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }


    private void cadastrarUsuario() {
        autenticacao = ConfiguracaoFirebase.getFirebaseAutenticacao();
        autenticacao.createUserWithEmailAndPassword(
                usuario.getEmail(),
                usuario.getSenha()
        ).addOnCompleteListener(CadastroUsuarioActivity.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(CadastroUsuarioActivity.this, "Sucesso ao cadastrar usuário", Toast.LENGTH_LONG).show();
                    String identificadorUsuario = Base64Custom.codificarBase64(usuario.getEmail());
                    usuario.setId(identificadorUsuario);
                    usuario.salvar();
                    Preferencias preferencias = new Preferencias(CadastroUsuarioActivity.this);
                    preferencias.salvarDados(identificadorUsuario, usuario.getNome());
                    abrirLoginUsuario();
                } else {
                    String erro = "";
                    try {
                        throw task.getException();
                    } catch (FirebaseAuthWeakPasswordException e) {
                        erro = "Escolha uma senha que contenha, letras e números.";
                    } catch (FirebaseAuthInvalidCredentialsException e) {
                        erro = "Email indicado não é válido.";
                    } catch (FirebaseAuthUserCollisionException e) {
                        erro = "Já existe uma conta com esse e-mail.";
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    Toast.makeText(CadastroUsuarioActivity.this, "Erro ao cadastrar usuário: " + erro, Toast.LENGTH_LONG).show();
                }
            }
        });

    }

    public void abrirLoginUsuario(){
        Intent intent = new Intent(CadastroUsuarioActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }
}
