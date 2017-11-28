package projetaobcc20172.com.projetopetemfoco.activity;

import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.annotation.VisibleForTesting;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseNetworkException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;

import projetaobcc20172.com.projetopetemfoco.R;
import projetaobcc20172.com.projetopetemfoco.config.ConfiguracaoFirebase;
import projetaobcc20172.com.projetopetemfoco.excecoes.SenhasDiferentesException;
import projetaobcc20172.com.projetopetemfoco.helper.Base64Custom;
import projetaobcc20172.com.projetopetemfoco.helper.Preferencias;
import projetaobcc20172.com.projetopetemfoco.model.Usuario;

public class CadastroUsuarioActivity extends AppCompatActivity {

    private EditText nome, email, senha, senha2;
    private Button botaoCadastrar;
    private Usuario usuario;
    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)//permite que essa variavel seja vista pela classe de teste
    private Toast mToast;
    private FirebaseAuth autenticacao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_usuario);

        Toolbar toolbar;
        toolbar = (Toolbar) findViewById(R.id.tb_cadastro);
        nome = findViewById(R.id.editText_nome);
        email = findViewById(R.id.editText_email);
        senha = findViewById(R.id.editText_senha);
        senha2 = findViewById(R.id.editText_senha2);
        botaoCadastrar = findViewById(R.id.botao_cadastrar);

        botaoCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                usuario = new Usuario();
                usuario.setNome( nome.getText().toString() );
                usuario.setEmail(email.getText().toString());
                usuario.setSenha(senha.getText().toString());
                cadastrarUsuario();
            }
        });

        // Configura toolbar
        toolbar.setTitle("Cadastro de Usuário");
        toolbar.setTitleTextColor(Color.WHITE);
        toolbar.setNavigationIcon(R.drawable.ic_action_arrow_left_white);
        setSupportActionBar(toolbar);


    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
    //Verifica se a senha 1 é igual a senha 2
    private void verificarSenhar() throws SenhasDiferentesException {
        if(!senha.getText().toString().equals(senha2.getText().toString())){
            throw new SenhasDiferentesException();
        }
    }
    private void cadastrarUsuario() {
        try {
            this.verificarSenhar();
            autenticacao = ConfiguracaoFirebase.getFirebaseAutenticacao();
            autenticacao.createUserWithEmailAndPassword(
                    usuario.getEmail(),
                    usuario.getSenha()
            ).addOnCompleteListener(CadastroUsuarioActivity.this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {

                    if (task.isSuccessful()) {


                        String identificadorUsuario = Base64Custom.codificarBase64(usuario.getEmail());
                        usuario.setId(identificadorUsuario);
                        usuario.salvar();

                        Preferencias preferencias = new Preferencias(CadastroUsuarioActivity.this);
                        preferencias.salvarDados(identificadorUsuario, usuario.getNome());

                        mToast = mToast.makeText(CadastroUsuarioActivity.this, R.string.sucesso_cadastro_Toast, Toast.LENGTH_LONG);
                        mToast.show();

                        abrirLoginUsuario();

                    } else {

                        String erro = "";
                        try {
                            throw task.getException();
                        } catch (FirebaseAuthWeakPasswordException e) {
                            erro = getResources().getString(R.string.erro_cadastro_senha_invalida_Toast);
                        } catch (FirebaseAuthInvalidCredentialsException e) {
                            erro = getResources().getString(R.string.erro_cadastro_email_invalido_Toast);
                        } catch (FirebaseAuthUserCollisionException e) {
                            erro = getResources().getString(R.string.erro_cadastro_email_usado_Toast);
                        } catch (FirebaseNetworkException e) {
                            erro = getResources().getString(R.string.erro_cadastro_sem_conexao);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                        mToast = mToast.makeText(CadastroUsuarioActivity.this, erro, Toast.LENGTH_SHORT);
                        mToast.show();
                    }

                }
            });
        } catch (SenhasDiferentesException e) {
            mToast = mToast.makeText(CadastroUsuarioActivity.this, R.string.erro_cadastro_senhas_diferentes_Toast, Toast.LENGTH_SHORT);
            mToast.show();
        } catch (Exception e) {
            mToast = mToast.makeText(CadastroUsuarioActivity.this, R.string.erro_cadastro_campos_obrigatorios_Toast, Toast.LENGTH_SHORT);
            mToast.show();
        }
    }

    public void abrirLoginUsuario(){
        Intent intent = new Intent(CadastroUsuarioActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

}
