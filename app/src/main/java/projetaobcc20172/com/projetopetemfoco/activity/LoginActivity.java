package projetaobcc20172.com.projetopetemfoco.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import projetaobcc20172.com.projetopetemfoco.R;
import projetaobcc20172.com.projetopetemfoco.config.ConfiguracaoFirebase;
import projetaobcc20172.com.projetopetemfoco.model.Usuario;
import projetaobcc20172.com.projetopetemfoco.helper.Base64Custom;
import projetaobcc20172.com.projetopetemfoco.helper.Preferencias;

public class LoginActivity extends AppCompatActivity {
    //testando pullrequest
    private EditText email, senha;
    private Button login, cadastrar;
    private FirebaseAuth autenticacao;
    private Usuario usuario;
    private String identificadorUsuarioLogado;
    private DatabaseReference firebase;
    private ValueEventListener valueEventListenerUsuario;
    private Toast mToast;
    private static Boolean loginAutomatico = false;
    private ProgressDialog mProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //Verifica se o usuário já está logado
        if(loginAutomatico){
            verificarUsuarioLogado();
        }

        email = findViewById(R.id.editText_email);
        senha = findViewById(R.id.editText_senha);
        login = findViewById(R.id.botao_login);
        cadastrar = findViewById(R.id.botao_cadastrar_novo_usuario);

        cadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, CadastroUsuarioActivity.class);
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

    private void validarLogin(){
        try {
            autenticacao = ConfiguracaoFirebase.getFirebaseAutenticacao();
            autenticacao.signInWithEmailAndPassword(
                    usuario.getEmail(),
                    usuario.getSenha()
            ).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {

                    if (task.isSuccessful()) {
                        identificadorUsuarioLogado = Base64Custom.codificarBase64(usuario.getEmail());

                        firebase = ConfiguracaoFirebase.getFirebase()
                                .child("usuarios")
                                .child(identificadorUsuarioLogado);

                        valueEventListenerUsuario = new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {

                                Usuario usuarioRecuperado = dataSnapshot.getValue(Usuario.class);

                                Preferencias preferencias = new Preferencias(LoginActivity.this);
                                if(usuarioRecuperado != null) preferencias.salvarDados(identificadorUsuarioLogado, usuarioRecuperado.getNome());

                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {
                                // vazio
                            }
                        };

                        firebase.addListenerForSingleValueEvent(valueEventListenerUsuario);


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

    private void verificarUsuarioLogado(){
        autenticacao = ConfiguracaoFirebase.getFirebaseAutenticacao();
        if( autenticacao.getCurrentUser() != null){
            abrirTelaPrincipal();
        }
    }

    public static void setLoginAutomatico(Boolean login){
        loginAutomatico = login;
    }

    public Toast getToast(){
        return this.mToast;
    }

    private void exibirProgresso() {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(this);
            mProgressDialog.setCancelable(false);
            mProgressDialog.setMessage("Verificando Dados...");
            mProgressDialog.setIndeterminate(true);
        }
        mProgressDialog.show();
    }

}
