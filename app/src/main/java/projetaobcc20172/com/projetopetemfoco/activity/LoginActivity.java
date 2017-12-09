package projetaobcc20172.com.projetopetemfoco.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import org.xml.sax.helpers.LocatorImpl;

import projetaobcc20172.com.projetopetemfoco.R;
import projetaobcc20172.com.projetopetemfoco.config.ConfiguracaoFirebase;
import projetaobcc20172.com.projetopetemfoco.model.Fornecedor;
import projetaobcc20172.com.projetopetemfoco.model.Usuario;
import projetaobcc20172.com.projetopetemfoco.helper.Base64Custom;

public class LoginActivity extends AppCompatActivity {

    private EditText email, senha;
    private Button login, cadastrar, cadastrar_fornecedor;
    private FirebaseAuth autenticacao;
    private Usuario usuario;
    private Fornecedor fornecedor;
    private String identificadorUsuarioLogado;
    private Toast mToast;
    //private static Boolean loginAutomatico = false;
    private ProgressDialog mProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //Verifica se o usuário já está logado
        //if(loginAutomatico){
        verificarUsuarioLogado();
        //}

        email = findViewById(R.id.editText_email);
        senha = findViewById(R.id.editText_senha);
        login = findViewById(R.id.botao_login);
        cadastrar = findViewById(R.id.botao_cadastrar_novo_usuario);
        cadastrar_fornecedor = findViewById(R.id.botao_cadastrar_fornecedor);

        //usuario.setValor("0");

        //salvarTipoFornecedor(fornecedor);

        cadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, CadastroUsuarioActivity.class);
                startActivity(intent);
                //finish();
            }
        });

        cadastrar_fornecedor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, CadastroFornecedorActivity.class);
                startActivity(intent);
                //finish();
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                usuario = new Usuario();
                usuario.setEmail( email.getText().toString() );
                usuario.setSenha( senha.getText().toString() );
                exibirProgresso();
                validarLogin();

            }
        });
    }

    //Método que valida o login do usuário junto ao Firebase
    private void validarLogin(){
        try {
            autenticacao = ConfiguracaoFirebase.getFirebaseAutenticacao();
            autenticacao.signInWithEmailAndPassword(
                    usuario.getEmail(),
                    usuario.getSenha()
            ).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {

                    //Se o login foi realizado com sucesso
                    if (task.isSuccessful()) {
                        identificadorUsuarioLogado = Base64Custom.codificarBase64(usuario.getEmail());
                        //Salva o id do usuário logado nas preferências
                        salvarPreferencias("id", identificadorUsuarioLogado);
                        abrirTelaPrincipal();
                        mToast = mToast.makeText(LoginActivity.this,R.string.sucesso_login_Toast, Toast.LENGTH_SHORT);
                        mToast.show();
                        mProgressDialog.dismiss();
                    } else {
                        mToast = mToast.makeText(LoginActivity.this, R.string.erro_login_invalido_Toast, Toast.LENGTH_SHORT);
                        mToast.show();
                        mProgressDialog.dismiss();
                    }

                }
            });
        }catch (Exception e){
            mToast = mToast.makeText(LoginActivity.this, R.string.erro_login_invalido_Toast, Toast.LENGTH_SHORT);
            mToast.show();
            mProgressDialog.dismiss();
        }
    }

    private void abrirTelaPrincipal(){
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        startActivity( intent );
        finish();
    }

    //Método que verifica se o usuário já está logado no app
    private void verificarUsuarioLogado(){
        autenticacao = ConfiguracaoFirebase.getFirebaseAutenticacao();
        if( autenticacao.getCurrentUser() != null){
            abrirTelaPrincipal();
        }
    }

    /*public static void setLoginAutomatico(Boolean login){
        loginAutomatico = login;
    }*/

    //Método que exibe o progresso do login
    private void exibirProgresso() {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(this);
            mProgressDialog.setCancelable(false);
            mProgressDialog.setMessage("Verificando Dados...");
            mProgressDialog.setIndeterminate(true);
        }
        mProgressDialog.show();
    }

    //Método que salva o id do usuário nas preferências para login automático ao abrir aplicativo
    private void salvarPreferencias(String key, String value){
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, value);
        editor.commit();
    }

}
