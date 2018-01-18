package projetaobcc20172.com.projetopetemfoco.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.GoogleAuthProvider;

import org.json.JSONObject;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;

import projetaobcc20172.com.projetopetemfoco.R;
import projetaobcc20172.com.projetopetemfoco.config.ConfiguracaoFirebase;
import projetaobcc20172.com.projetopetemfoco.database.services.EnderecoDaoImpl;
import projetaobcc20172.com.projetopetemfoco.model.Usuario;
import projetaobcc20172.com.projetopetemfoco.utils.Utils;


public class LoginActivity extends AppCompatActivity {

    private FirebaseAuth mAutenticacao;
    private Usuario mUsuario;
    //private static Boolean loginAutomatico = false;
    private static final int RC_SIGN_IN = 1;
    private GoogleApiClient mGoogleApiClient;
    private CallbackManager mCallbackManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        FacebookSdk.sdkInitialize(getApplicationContext());
        mCallbackManager = CallbackManager.Factory.create();

        verificarUsuarioLogado();

        Button mLoginButtonFacebook;
        mLoginButtonFacebook = findViewById(R.id.btnLoginFacebook);
        //Obter as permissões do perfil público e email do usuário logado pelo facebbok
        mLoginButtonFacebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LoginManager.getInstance().logInWithReadPermissions(LoginActivity.this, Arrays.asList("public_profile", "email"));
            }
        });

        //Registrar um callback para realizar um login pelo Facebook
        LoginManager.getInstance().registerCallback(mCallbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {

                GraphRequest request = GraphRequest.newMeRequest(
                        loginResult.getAccessToken(),
                        new GraphRequest.GraphJSONObjectCallback() {
                            @Override
                            public void onCompleted(JSONObject jsonObject,
                                                    GraphResponse response) {

                                //Obter os dados do usuário do facebook
                                Bundle facebookData = getFacebookData(jsonObject);

                                if(getPreferencesKeyConsumidorFacebook(getApplicationContext())){
                                    salvarPreferencias("id", facebookData.getString("idFacebook"));
                                    Utils.mostrarMensagemCurta(getApplicationContext(), getApplicationContext().getString(R.string.sucesso_login_Toast));
                                    abrirTelaPrincipalConsumidor();
                                }
                                else{
                                    salvarPreferencias("idFacebook", facebookData.getString("idFacebook"));
                                    confirmarCadastroEnderecoFacebook(facebookData);
                                }

                            }
                        });

                Bundle parameters = new Bundle();
                parameters.putString("fields", "id,first_name,last_name,email,gender");
                request.setParameters(parameters);
                request.executeAsync();
            }

            @Override
            public void onCancel() {
                Utils.mostrarMensagemCurta(getApplicationContext(), getApplicationContext().getString(R.string.login_cancelado));
            }

            @Override
            public void onError(FacebookException error) {
                Utils.mostrarMensagemCurta(getApplicationContext(), getApplicationContext().getString(R.string.erro_login_invalido_Toast));
            }
        });


        Button mLoginGoogle;

        mLoginGoogle = findViewById(R.id.btnLoginGoogle);

        //Configuração do Google Sign In para login pelo Google
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleApiClient = new GoogleApiClient.Builder(getApplicationContext())
                .enableAutoManage(this, new GoogleApiClient.OnConnectionFailedListener() {
                    @Override
                    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
                        Utils.mostrarMensagemCurta(getApplicationContext(), getApplicationContext().getString(R.string.erro_login_invalido_Toast));
                    }
                })
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();

        mLoginGoogle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signIn();
            }
        });

    }

    //Metodo que recupera os dados do usuário logado pelo Facebook
    private Bundle getFacebookData(JSONObject object) {
        Bundle bundle = new Bundle();

        try {
            String id = object.getString("id");
            URL profile_pic;
            try {
                //Obter a foto de perfil
                profile_pic = new URL("https://graph.facebook.com/" + id + "/picture?type=large");
                Log.i("profile_pic", profile_pic + "");
                bundle.putString("profile_pic", profile_pic.toString());
            } catch (MalformedURLException e) {
                e.printStackTrace();
                return null;
            }

            bundle.putString("idFacebook", id);
            if (object.has("first_name"))
                bundle.putString("first_name", object.getString("first_name"));
            if (object.has("last_name"))
                bundle.putString("last_name", object.getString("last_name"));
            if (object.has("email"))
                bundle.putString("email", object.getString("email"));
            if (object.has("gender"))
                bundle.putString("gender", object.getString("gender"));

        } catch (Exception e) {
            Log.d("VER", "BUNDLE Exception : "+e.toString());
        }

        return bundle;
    }


    private void signIn() {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mCallbackManager.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {

            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            //Se o login pelo Google foi realizado com sucesso pela segunda vez na mesma sessão
            if (result.isSuccess() && getPreferencesKeyConsumidorGoogle(this)) {

                GoogleSignInAccount account = result.getSignInAccount();
                firebaseAuthWithGoogle(account);
                salvarPreferencias("id", account.getId());
                Utils.mostrarMensagemCurta(getApplicationContext(), getApplicationContext().getString(R.string.sucesso_login_Toast));
                abrirTelaPrincipalConsumidor();

                //Se o login pelo Google foi realizado com sucesso pela primeira vez na sessão
            } else if (result.isSuccess() && !getPreferencesKeyConsumidorGoogle(this)) {

                GoogleSignInAccount account = result.getSignInAccount();
                firebaseAuthWithGoogle(account);
                salvarPreferencias("idGoogle", account.getId());
                confirmarCadastroEnderecoGoogle(account);

            } else {
                Utils.mostrarMensagemCurta(getApplicationContext(), getApplicationContext().getString(R.string.erro_login_invalido_Toast));
            }
        }
    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount account) {

        AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(), null);
        mAutenticacao.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {

                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        //vazio
                    }
                });
    }

    //Abrir tela principal do consumidor
    private void abrirTelaPrincipalConsumidor() {
        Intent intent = new Intent(LoginActivity.this, NavigatorMenu.class);
        startActivity(intent);
        //intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        finish();
    }


    //Método que verifica se o usuário já está logado no app
    private void verificarUsuarioLogado() {
        mAutenticacao = ConfiguracaoFirebase.getFirebaseAutenticacao();
        //Se já estiver logado pelo Google
        if (mAutenticacao.getCurrentUser() != null && getPreferencesKeyConsumidorVerificarLogado(this)) {
            abrirTelaPrincipalConsumidor();
        }
        //Se já estiver logado pelo Facebook
        if(isLoggedIn()) abrirTelaPrincipalConsumidor();

    }

    //Método que verifica se o usuário está logado pelo Facebook
    public boolean isLoggedIn() {
        AccessToken accessToken = AccessToken.getCurrentAccessToken();
        return accessToken != null;
    }

    //Método que recupera o id do usuário logado para acessar seus dados e salvar suas informações no banco
    public static boolean getPreferencesKeyConsumidorVerificarLogado(Context context) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.contains("id");
    }

    //Método que recupera o id do usuário logado pelo Google
    public static boolean getPreferencesKeyConsumidorGoogle(Context context) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.contains("idGoogle");
    }

    //Método que recupera o id do usuário logado pelo Facebook
    public static boolean getPreferencesKeyConsumidorFacebook(Context context) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.contains("idFacebook");
    }

    //Método que exibe pergunta de confirmação ao usuário logado pelo Google se o mesmo deseja cadastrar um endereço
    public void confirmarCadastroEnderecoGoogle(final GoogleSignInAccount account) {
        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case DialogInterface.BUTTON_POSITIVE:
                        // Botão sim foi clicado
                        salvarUsuarioGoogle(account);
                        Intent intent = new Intent(LoginActivity.this, CadastroEnderecoActivity.class);
                        startActivity(intent);
                        finish();
                        break;

                    case DialogInterface.BUTTON_NEGATIVE:
                        // Botão não foi clicado
                        salvarUsuarioGoogle(account);
                        break;
                    default:
                        break;
                }
            }
        };

        Utils.mostrarPerguntaSimNao(this, getString(R.string.atencao),
                getString(R.string.pergunta_cadastro_endereco), dialogClickListener,
                dialogClickListener);
    }

    //Método que exibe pergunta de confirmação ao usuário logado pelo Facebook se o mesmo deseja cadastrar um endereço
    public void confirmarCadastroEnderecoFacebook(final Bundle facebookData) {
        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case DialogInterface.BUTTON_POSITIVE:
                        // Botão sim foi clicado
                        salvarUsuarioFacebook(facebookData);
                        Intent intent = new Intent(LoginActivity.this, CadastroEnderecoActivity.class);
                        startActivity(intent);
                        finish();
                        break;

                    case DialogInterface.BUTTON_NEGATIVE:
                        // Botão não foi clicado
                        salvarUsuarioFacebook(facebookData);
                        break;
                    default:
                        break;
                }
            }
        };

        Utils.mostrarPerguntaSimNao(this, getString(R.string.atencao),
                getString(R.string.pergunta_cadastro_endereco), dialogClickListener,
                dialogClickListener);
    }


    //Método que salva o id do usuário nas preferências para login automático ao abrir aplicativo
    private void salvarPreferencias(String key, String value) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, value);
        editor.commit();
    }

    //Método que salva o usuário logado pelo Google no banco
    private boolean salvarUsuarioGoogle(GoogleSignInAccount account) {
        try {

            mUsuario = new Usuario(account.getId(), account.getDisplayName(), account.getEmail(), account.getPhotoUrl().toString());
            salvarPreferencias("id", account.getId());

            //Chamada do DAO para salvar no banco
            EnderecoDaoImpl usuarioDao = new EnderecoDaoImpl(this);
            usuarioDao.inserirUsuario(mUsuario, account.getId());
            Intent intent = new Intent(LoginActivity.this, NavigatorMenu.class);
            startActivity(intent);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            finish();

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    //Método que salva o usuário logado pelo Facebook no banco
    private boolean salvarUsuarioFacebook(Bundle facebookData) {
        try {

            mUsuario = new Usuario(facebookData.getString("idFacebook"), facebookData.getString("first_name") + " " + facebookData.getString("last_name")
                    ,facebookData.getString("email"), facebookData.getString("profile_pic"));
            salvarPreferencias("id", facebookData.getString("idFacebook"));

            //Chamada do DAO para salvar no banco
            EnderecoDaoImpl usuarioDao = new EnderecoDaoImpl(this);
            usuarioDao.inserirUsuario(mUsuario, facebookData.getString("idFacebook"));
            Intent intent = new Intent(LoginActivity.this, NavigatorMenu.class);
            startActivity(intent);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            finish();

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

        /*public static void setLoginAutomatico(Boolean login){
        loginAutomatico = login;
    }*/

    /*
    //Método que exibe o progresso do mLogin
    private void exibirProgresso() {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(this);
            mProgressDialog.setCancelable(false);
            mProgressDialog.setMessage("Verificando Dados...");
            mProgressDialog.setIndeterminate(true);
        }
        mProgressDialog.show();
    }*/


}






