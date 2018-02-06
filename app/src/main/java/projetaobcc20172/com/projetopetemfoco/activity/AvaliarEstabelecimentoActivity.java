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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_avaliar_estabelecimento);

        // Associa os componetes ao layout XML
        final EditText mComentario = findViewById(R.id.etComentarioAvaliacao);
        final RatingBar mRatingBar = findViewById(R.id.rbEstrelasAvaliacao);
        Button mBtnAvaliar = findViewById(R.id.botao_avaliar);
        Toolbar toolbar = findViewById(R.id.tb_avaliacao_estabelecimento);

        // Configura toolbar
        toolbar.setTitle(R.string.tb_avaliar_estabelecimento);
        toolbar.setTitleTextColor(Color.WHITE);
        toolbar.setNavigationIcon(R.drawable.ic_action_arrow_left_white);
        setSupportActionBar(toolbar);

        //Receber os dados do estabelecimento da outra activity
        Intent i = getIntent();
        mFornecedor = (Fornecedor) i.getSerializableExtra("Fornecedor");

        //Recuperar id do fornecedor logado
        String idUsuarioLogado = getPreferences("id", this);

        buscaNomeUsuario(idUsuarioLogado);

        // Confirma a avaliação
        mBtnAvaliar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                avaliar(mComentario.getText().toString(), (int) mRatingBar.getRating());
            }
        });

    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    public void avaliar(String comentario, int estrelas ) {
        try {
            //Recuperar id do fornecedor logado
            String idUsuarioLogado = getPreferences("id", this);

            buscaNomeUsuario(idUsuarioLogado);

            // Instância uma avaliação
            mAvaliacao = new Avaliacao(idUsuarioLogado, mUsuario.getNome(), estrelas, comentario);

            // Verifica a avaliação
            VerificadorDeObjetos.vDadosObjAvaliacao(mAvaliacao);

            //Chamada do DAO para salvar no banco
            AvaliacaoDaoImpl avaliacaoDao =  new AvaliacaoDaoImpl(this);
            avaliacaoDao.inserir(mAvaliacao, mFornecedor);
            abrirMain();

        } catch (CampoObrAusenteException e) {
            mToast = Toast.makeText(AvaliarEstabelecimentoActivity.this, R.string.erro_avaliacao_estabelecimento, Toast.LENGTH_SHORT);
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
        Intent intent = new Intent(AvaliarEstabelecimentoActivity.this, AcessoInformacoesEstabelecimentoActivity.class);
        intent.putExtra("Fornecedor", mFornecedor);
        //startActivity(intent);
        finish();
    }


    //Método que busca o nome do usuário
    public void buscaNomeUsuario(final String idUsuariologado){
        //Buscar nome do usuário logado
        DatabaseReference mReferenciaFirebase;
        mReferenciaFirebase = ConfiguracaoFirebase.getFirebase();
        mReferenciaFirebase.child("usuarios").child(idUsuariologado).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.getValue() != null){
                    Usuario usuario = dataSnapshot.getValue(Usuario.class);
                    mUsuario = new Usuario();//idUsuario, nome, email, foto
                    mUsuario.setNome(usuario.getNome());
                    mUsuario.setEmail(usuario.getEmail());
                    mUsuario.setId(idUsuariologado);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                assert true;
            }
        });

    }

    //Método do botão voltar
    @Override
    public void onBackPressed() {
        AvaliarEstabelecimentoActivity.super.onBackPressed();
    }


}
