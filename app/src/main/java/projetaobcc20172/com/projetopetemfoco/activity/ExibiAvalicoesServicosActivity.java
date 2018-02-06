package projetaobcc20172.com.projetopetemfoco.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.VisibleForTesting;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import java.io.Serializable;
import projetaobcc20172.com.projetopetemfoco.R;
import projetaobcc20172.com.projetopetemfoco.adapter.ListaAvaliacoesAdapterView;
import projetaobcc20172.com.projetopetemfoco.adapter.ListaAvaliacoesServicoAdapterView;
import projetaobcc20172.com.projetopetemfoco.config.ConfiguracaoFirebase;
import projetaobcc20172.com.projetopetemfoco.model.Avaliacao;
import projetaobcc20172.com.projetopetemfoco.model.Fornecedor;
import projetaobcc20172.com.projetopetemfoco.model.Servico;

public class ExibiAvalicoesServicosActivity extends AppCompatActivity implements Serializable {

    private Servico mServico;
    private ArrayAdapter<Avaliacao> mAdapter;
    @SuppressLint("WrongConstant")
    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    //permite que essa variavel seja vista pela classe de teste

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exibi_avalicoes_servicos);

        // Associa os componetes ao layout XML
        TextView mNomeServico = findViewById(R.id.tvExibeNomeServicoAvaliacao);
        ListView mExibeListaAvaliacao = findViewById(R.id.lvListaAvaliacoesServico);
        Toolbar toolbar = findViewById(R.id.tb_lista_avaliacoes_servico);

        // Configura toolbar
        toolbar.setTitle(R.string.tb_avaliacoes_servico);
        toolbar.setTitleTextColor(Color.WHITE);
        toolbar.setNavigationIcon(R.drawable.ic_action_arrow_left_white);
        setSupportActionBar(toolbar);

        //Receber os dados do estabelecimento da outra activity
        Intent i = getIntent();
        String idServico = (String) i.getSerializableExtra("Servico");

        // Busca as avaliações atribuidas ao fornecedor
        buscaServico(idServico);
        buscaAvaliacoes(idServico);
        // Monta listview e mAdapter
        mAdapter = new ListaAvaliacoesServicoAdapterView(this, mServico.getAvaliacoes());

        mExibeListaAvaliacao.setAdapter(mAdapter);
        mNomeServico.setText(mServico.getNome());
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    //Método que chama a activity para exibir as avaliações do estabelecimento
    public void buscaAvaliacoes(final String idServico){
        //Buscar avaliações do serviço selecionado
        final DatabaseReference mFireBase;
        mFireBase = ConfiguracaoFirebase.getFirebase().child("servico_fornecedor").child(idServico).child("avaliacao");
        mFireBase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot dados : dataSnapshot.getChildren()) {
                    Avaliacao avaliacao = dados.getValue(Avaliacao.class);
                    mServico.getAvaliacoes().add((avaliacao));
                }
                mAdapter.notifyDataSetChanged();
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                assert true;
            }
        });
    }

    //Método que busca o servico por meio do id
    public void buscaServico(final String idServico){
        //Buscar servico
        DatabaseReference mReferenciaFirebase;
        mReferenciaFirebase = ConfiguracaoFirebase.getFirebase();
        mReferenciaFirebase.child("servico_fornecedor").child(idServico).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.getValue() != null){
                    mServico = dataSnapshot.getValue(Servico.class);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Vazio
            }
        });
    }

}