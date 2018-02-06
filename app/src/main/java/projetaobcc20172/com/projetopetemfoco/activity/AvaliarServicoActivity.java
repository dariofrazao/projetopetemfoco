package projetaobcc20172.com.projetopetemfoco.activity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.location.LocationManager;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
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
import projetaobcc20172.com.projetopetemfoco.database.services.AvaliacaoServicoDaoImpl;
import projetaobcc20172.com.projetopetemfoco.excecoes.CampoObrAusenteException;
import projetaobcc20172.com.projetopetemfoco.model.Avaliacao;
import projetaobcc20172.com.projetopetemfoco.model.Servico;
import projetaobcc20172.com.projetopetemfoco.model.Usuario;
import projetaobcc20172.com.projetopetemfoco.utils.VerificadorDeObjetos;

/**
 * Created by Alexsandro on 24/01/2018.
 */


public class AvaliarServicoActivity extends AppCompatActivity {
    private Avaliacao mAvaliacao;
    public Toast mToast;
    private String idUsuarioLogado;
    private Usuario mUsuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_avaliar_servico);

        //Recuperar id do usuário logado
        idUsuarioLogado = getPreferences("id", this);
        buscaNomeUsuario(idUsuarioLogado);

        // Associa os componetes ao layout XML
        final EditText mComentario = findViewById(R.id.etComentarioAvaliacaoServico);
        final RatingBar mRatingBar = findViewById(R.id.rbEstrelasAvaliacaoServico);
        Button mBtnAvaliar = findViewById(R.id.botao_avaliar_servico);
        Toolbar toolbar = findViewById(R.id.tb_avaliacao_servico);

        // Configura toolbar
        toolbar.setTitle(R.string.tb_avaliar_servico);
        toolbar.setTitleTextColor(Color.WHITE);
        toolbar.setNavigationIcon(R.drawable.ic_action_arrow_left_white);
        setSupportActionBar(toolbar);

        //Receber os dados do serviço de outra activity
        Intent i = getIntent();
        final String idServico = (String) i.getSerializableExtra("Servico");

        // Confirma a avaliação
        mBtnAvaliar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                avaliar(mComentario.getText().toString(), (int) mRatingBar.getRating(), idServico);
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    public void avaliar(String comentario, int estrelas, String idServico ) {
        try {
            //Recuperar id do usuário logado
            idUsuarioLogado = getPreferences("id", this);
            buscaNomeUsuario(idUsuarioLogado);

            // Instância uma avaliação
            mAvaliacao = new Avaliacao(idUsuarioLogado, mUsuario.getNome(), estrelas, comentario);

            // Verifica a avaliação
            VerificadorDeObjetos.vDadosObjAvaliacao(mAvaliacao);

            //Chamada do DAO para salvar no banco
            AvaliacaoServicoDaoImpl avaliacaoServicoDao =  new AvaliacaoServicoDaoImpl(this);
            avaliacaoServicoDao.inserir(mAvaliacao, idServico);
            abrirActivityAnterior();

        } catch (CampoObrAusenteException e) {
            mToast = Toast.makeText(AvaliarServicoActivity.this, R.string.erro_avaliacao_servico, Toast.LENGTH_SHORT);
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

    public void abrirActivityAnterior() {
        onBackPressed();
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
                // Vazio
            }
        });
    }
}
