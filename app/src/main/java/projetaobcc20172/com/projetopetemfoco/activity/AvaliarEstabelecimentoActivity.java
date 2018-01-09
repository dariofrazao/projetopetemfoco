package projetaobcc20172.com.projetopetemfoco.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Toast;
import com.google.firebase.auth.FirebaseAuth;
import projetaobcc20172.com.projetopetemfoco.R;
import projetaobcc20172.com.projetopetemfoco.config.ConfiguracaoFirebase;
import projetaobcc20172.com.projetopetemfoco.database.services.AvaliacaoDaoImpl;
import projetaobcc20172.com.projetopetemfoco.excecoes.CampoObrAusenteException;
import projetaobcc20172.com.projetopetemfoco.model.Avaliacao;
import projetaobcc20172.com.projetopetemfoco.model.Fornecedor;
import projetaobcc20172.com.projetopetemfoco.model.Usuario;
import projetaobcc20172.com.projetopetemfoco.utils.VerificadorDeObjetos;

public class AvaliarEstabelecimentoActivity extends AppCompatActivity {
    private Avaliacao mAvaliacao;
    public Toast mToast;
    private Fornecedor mFornecedor;
    private Usuario mUsuario;
    EditText mComentario;
    RatingBar mRatingBar;
    Button mBtnAvaliar;
    private FirebaseAuth mAutenticacao;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_avaliar_estabelecimento);

        mAutenticacao = ConfiguracaoFirebase.getFirebaseAutenticacao();

        Toolbar toolbar;
        toolbar = findViewById(R.id.tb_avaliacao_estabelecimento);

        // Configura toolbar
        toolbar.setTitle(R.string.tb_avaliar_estabelecimento);
        toolbar.setTitleTextColor(Color.WHITE);
        toolbar.setNavigationIcon(R.drawable.ic_action_arrow_left_white);
        setSupportActionBar(toolbar);

        mComentario = findViewById(R.id.etComentarioAvaliacao);
        mRatingBar = findViewById(R.id.rbEstrelasAvaliacao);
        mAvaliacao = new Avaliacao();
        //Receber os dados do estabelecimento da outra activity
        Intent i = getIntent();
        mFornecedor = (Fornecedor) i.getSerializableExtra("Fornecedor");

        mBtnAvaliar = findViewById(R.id.botao_avaliar);
        mBtnAvaliar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                avaliar();
            }
        });

    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    public void avaliar() {
        try {
            //Recuperar id do fornecedor logado
            String idUsuarioLogado;
            idUsuarioLogado = getPreferences("id", this);

            mAvaliacao.setCometario(mComentario.getText().toString());
            mAvaliacao.setEstrelas(String.valueOf(mRatingBar.getRating()));
            mAvaliacao.setIdUsuario(idUsuarioLogado);

            VerificadorDeObjetos.vDadosObjAvaliacao(mAvaliacao);

            //Chamada do DAO para salvar no banco
            AvaliacaoDaoImpl avaliacaoDao =  new AvaliacaoDaoImpl(this);
            avaliacaoDao.inserir(mAvaliacao, mFornecedor);
            abrirMain();

        } catch (CampoObrAusenteException e) {
            mToast = Toast.makeText(AvaliarEstabelecimentoActivity.this, R.string.erro_cadastro_campos_obrigatorios_Pet, Toast.LENGTH_SHORT);
            mToast.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    //Método que recupera o id do usuario logado, para realizar a avaliacao
    public static String getPreferences(String key, Context context) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.getString(key, null);
    }

    public void abrirMain() {
        Intent intent = new Intent(AvaliarEstabelecimentoActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}
