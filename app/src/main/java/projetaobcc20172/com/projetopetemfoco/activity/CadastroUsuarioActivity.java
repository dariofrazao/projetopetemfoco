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
import projetaobcc20172.com.projetopetemfoco.excecoes.CampoObrAusenteException;
import projetaobcc20172.com.projetopetemfoco.excecoes.SenhasDiferentesException;
import projetaobcc20172.com.projetopetemfoco.helper.Base64Custom;
import projetaobcc20172.com.projetopetemfoco.model.Usuario;
import projetaobcc20172.com.projetopetemfoco.utils.VerificadorDeObjetos;

public class CadastroUsuarioActivity extends AppCompatActivity {

    private EditText mNome, mEmail, mSenha, mSenha2;
    private Usuario mUsuario;
    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)//permite que essa variavel seja vista pela classe de teste
    public Toast mToast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_usuario);
        Toolbar toolbar;
        toolbar = (Toolbar) findViewById(R.id.tb_cadastro);
        mNome = findViewById(R.id.editText_nome);
        mEmail = findViewById(R.id.editText_email);
        mSenha = findViewById(R.id.editText_senha);
        mSenha2 = findViewById(R.id.editText_senha2);
        Button mBtnCadastrar = findViewById(R.id.botao_cadastrar_endereco);
        mBtnCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mUsuario = new Usuario();
                mUsuario.setNome( mNome.getText().toString() );
                mUsuario.setEmail(mEmail.getText().toString());
                mUsuario.setSenha(mSenha.getText().toString());
                mUsuario.setSenha2(mSenha2.getText().toString());
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

    //Método para cadastrar o usuário no FirebaseAuthentication
    private void cadastrarUsuario() {
        try {
            FirebaseAuth mAutenticacao;
            VerificadorDeObjetos.vDadosUsuario(mUsuario);
            mAutenticacao = ConfiguracaoFirebase.getFirebaseAutenticacao();
            mAutenticacao.createUserWithEmailAndPassword(
                    mUsuario.getEmail(),
                    mUsuario.getSenha()
            ).addOnCompleteListener(CadastroUsuarioActivity.this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        String identificadorUsuario = Base64Custom.codificarBase64(mUsuario.getEmail());
                        mUsuario.setId(identificadorUsuario);
                        mToast = mToast.makeText(CadastroUsuarioActivity.this, R.string.sucesso_cadastro_proxima_etapa_Toast, Toast.LENGTH_LONG);
                        mToast.show();
                        //Aqui será chamado a continuação do cadastro do usuário, levando-o ao cadastro do endereço
                        abrirCadastroEndereco(mUsuario);
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
        } catch (CampoObrAusenteException e) {
            mToast = mToast.makeText(CadastroUsuarioActivity.this, R.string.erro_cadastro_campos_obrigatorios_Toast, Toast.LENGTH_SHORT);
            mToast.show();
        } catch (Exception e) {
            mToast = mToast.makeText(CadastroUsuarioActivity.this, R.string.erro_cadastro_campos_obrigatorios_Toast, Toast.LENGTH_SHORT);
            mToast.show();
        }
    }

    //Método que chama a activity para cadastrar o endereço, passando os dados básicos aqui cadastrados
    public void abrirCadastroEndereco(Usuario usuario) {
        Intent intent = new Intent(CadastroUsuarioActivity.this, CadastroEnderecoActivity.class);
        intent.putExtra("Usuario", usuario);
        startActivity(intent);
        finish();
    }

}
