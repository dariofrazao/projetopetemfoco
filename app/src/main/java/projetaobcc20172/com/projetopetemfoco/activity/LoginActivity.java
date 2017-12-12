package projetaobcc20172.com.projetopetemfoco.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
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
import projetaobcc20172.com.projetopetemfoco.model.Fornecedor;
import projetaobcc20172.com.projetopetemfoco.model.Usuario;
import projetaobcc20172.com.projetopetemfoco.helper.Base64Custom;

public class LoginActivity extends AppCompatActivity {

    private EditText mEmail, mSenha;
    private FirebaseAuth mAutenticacao;
    private Usuario mUsuario;
    private String mIdentificadorUserLogado;
    private Toast mToast;
    //private static Boolean loginAutomatico = false;
    private ProgressDialog mProgressDialog;
    private DatabaseReference mFirebase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //Verifica se o usuário já está logado
        //if(loginAutomatico){
        verificarUsuarioLogado();
        //}


        mEmail = findViewById(R.id.etLoginEmail);
        mSenha = findViewById(R.id.etLoginSenha);
        Button mLogin;
        Button mCadastrar;
        Button mCadastrarFornecedor;
        mLogin = findViewById(R.id.botao_login);
        mCadastrar = findViewById(R.id.botao_cadastrar_novo_usuario);
        mCadastrarFornecedor = findViewById(R.id.botao_cadastrar_fornecedor);

        mCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, CadastroUsuarioActivity.class);
                startActivity(intent);
                //finish();
            }
        });

        mCadastrarFornecedor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, CadastroFornecedorActivity.class);
                startActivity(intent);
                //finish();
            }
        });

        mLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fornecedor mFornecedor;
                mUsuario = new Usuario();
                mFornecedor = new Fornecedor();
                mUsuario.setEmail( mEmail.getText().toString() );
                mUsuario.setSenha( mSenha.getText().toString() );
                mFornecedor.setEmail( mEmail.getText().toString() );
                mFornecedor.setSenha( mSenha.getText().toString() );
                exibirProgresso();
                validarLogin();

            }
        });
    }

    //Método que valida o mLogin do usuário junto ao Firebase
    private void validarLogin(){
        try {
            mAutenticacao = ConfiguracaoFirebase.getFirebaseAutenticacao();
            mAutenticacao.signInWithEmailAndPassword(
                    mUsuario.getEmail(),
                    mUsuario.getSenha()
            ).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {

                    //Se o mLogin foi realizado com sucesso
                    if (task.isSuccessful()) {
                        mIdentificadorUserLogado = Base64Custom.codificarBase64(mUsuario.getEmail());

                        //Salva o id do usuário logado nas preferências
                        mFirebase = ConfiguracaoFirebase.getFirebase().child("fornecedor");
                        mFirebase.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                    if (dataSnapshot.child(mIdentificadorUserLogado).child("cpfCnpj").exists()) {
                                        salvarPreferenciasFornecedor("idFornecedor", mIdentificadorUserLogado);
                                        abrirTelaPrincipalFornecedor();
                                    } else {
                                        salvarPreferenciasConsumidor("id", mIdentificadorUserLogado);
                                        abrirTelaPrincipalConsumidor();
                                    }

                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {
                                //vazio
                            }
                        });

                        mToast = Toast.makeText(LoginActivity.this,R.string.sucesso_login_Toast, Toast.LENGTH_SHORT);
                        mToast.show();
                        mProgressDialog.dismiss();
                    } else {
                        mToast = Toast.makeText(LoginActivity.this, R.string.erro_login_invalido_Toast, Toast.LENGTH_SHORT);
                        mToast.show();
                        mProgressDialog.dismiss();
                    }

                }
            });
        }catch (Exception e){
            mToast = Toast.makeText(LoginActivity.this, R.string.erro_login_invalido_Toast, Toast.LENGTH_SHORT);
            mToast.show();
            mProgressDialog.dismiss();
        }
    }

    private void abrirTelaPrincipalConsumidor(){
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        startActivity( intent );
        finish();
    }

    private void abrirTelaPrincipalFornecedor(){
        Intent intent = new Intent(LoginActivity.this, MainActivityFornecedor.class);
        startActivity( intent );
        finish();
    }

    //Método que verifica se o usuário já está logado no app
    private void verificarUsuarioLogado(){
        mAutenticacao = ConfiguracaoFirebase.getFirebaseAutenticacao();
        if( mAutenticacao.getCurrentUser() != null && getPreferencesKeyConsumidor(this)){
            abrirTelaPrincipalConsumidor();
        }
        else if (mAutenticacao.getCurrentUser() != null){
            abrirTelaPrincipalFornecedor();
        }
    }

    /*public static void setLoginAutomatico(Boolean login){
        loginAutomatico = login;
    }*/

    //Método que exibe o progresso do mLogin
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
    private void salvarPreferenciasConsumidor(String key, String value){
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, value);
        editor.commit();
    }

    //Método que salva o id do usuário nas preferências para login automático ao abrir aplicativo
    private void salvarPreferenciasFornecedor(String key, String value){
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, value);
        editor.commit();
    }

    //Método que recupera o id do usuário logado, para salvar o mPet no nó do usuário que o está cadastrando
    public static boolean getPreferencesKeyConsumidor(Context context) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.contains("id");
    }

}
