package projetaobcc20172.com.projetopetemfoco.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.VisibleForTesting;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.io.Serializable;
import java.util.ArrayList;

import projetaobcc20172.com.projetopetemfoco.R;
import projetaobcc20172.com.projetopetemfoco.adapter.ListaAvaliacoesServicoAdapterView;
import projetaobcc20172.com.projetopetemfoco.config.ConfiguracaoFirebase;
import projetaobcc20172.com.projetopetemfoco.model.Avaliacao;

public class ExibiAvalicoesServicosActivity extends AppCompatActivity implements Serializable {
    private ArrayList<Avaliacao> mAvaliacoes;
    private ArrayAdapter<Avaliacao> mAdapter;
    @SuppressLint("WrongConstant")
    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    //permite que essa variavel seja vista pela classe de teste

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_avalicoes_servico);

        // Associa os componetes ao layout XML
        TextView mTipoServico = findViewById(R.id.tvExibeTipoServicoAvaliacoes);
        ListView mExibeListaAvaliacao = findViewById(R.id.lvListaAvaliacoesServico);
        Toolbar toolbar = findViewById(R.id.tb_lista_avaliacoes_servico);

        // Configura toolbar
        toolbar.setTitle(R.string.tb_avaliacoes_servico);
        toolbar.setTitleTextColor(Color.WHITE);
        toolbar.setNavigationIcon(R.drawable.ic_action_arrow_left_white);
        setSupportActionBar(toolbar);

        //Receber os dados do estabelecimento da outra activity
        Intent i = getIntent();
        String[] mServico = (String[]) i.getSerializableExtra("Servico");

              // Busca as avaliações atribuidas ao fornecedor
        buscaAvaliacoes(mServico[9]);
        // Monta listview e mAdapter
        mAdapter = new ListaAvaliacoesServicoAdapterView(this, mAvaliacoes);

        mExibeListaAvaliacao.setAdapter(mAdapter);
        mTipoServico.setText(mServico[1]+" : "+mServico[0]);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    //Método que chama a activity para exibir as avaliações do estabelecimento
    public void buscaAvaliacoes(final String idServico){
        //Buscar avaliações do serviço selecionado
        mAvaliacoes = new ArrayList<>();
        final DatabaseReference mFireBase;
        mFireBase = ConfiguracaoFirebase.getFirebase().child("servico_fornecedor").child(idServico).child("avaliacao");
        mFireBase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot dados : dataSnapshot.getChildren()) {
                    Avaliacao avaliacao = dados.getValue(Avaliacao.class);
                    mAvaliacoes.add((avaliacao));
                }
                mAdapter.notifyDataSetChanged();
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                assert true;
            }
        });
    }
}