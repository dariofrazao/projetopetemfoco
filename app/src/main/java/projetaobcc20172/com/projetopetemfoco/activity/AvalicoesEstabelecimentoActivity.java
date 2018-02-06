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

import projetaobcc20172.com.projetopetemfoco.R;
import projetaobcc20172.com.projetopetemfoco.adapter.ListaAvaliacoesAdapterView;
import projetaobcc20172.com.projetopetemfoco.config.ConfiguracaoFirebase;
import projetaobcc20172.com.projetopetemfoco.model.Avaliacao;
import projetaobcc20172.com.projetopetemfoco.model.Fornecedor;

public class AvalicoesEstabelecimentoActivity extends AppCompatActivity implements Serializable {
    private Fornecedor mFornecedor;
    private ArrayAdapter<Avaliacao> mAdapter;
    @SuppressLint("WrongConstant")
    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    //permite que essa variavel seja vista pela classe de teste

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_avalicoes_estabelecimento);

        // Associa os componetes ao layout XML
        TextView mNomeEstabelecimento = findViewById(R.id.tvExibeNomeEstabelecimentoAvaliacao);
        ListView mExibeListaAvaliacao = findViewById(R.id.lvListaAvaliacoes);
        Toolbar toolbar = findViewById(R.id.tb_lista_avaliacoes_estabelecimento);

        // Configura toolbar
        toolbar.setTitle(R.string.tb_avaliacoes_estabelecimento);
        toolbar.setTitleTextColor(Color.WHITE);
        toolbar.setNavigationIcon(R.drawable.ic_action_arrow_left_white);
        setSupportActionBar(toolbar);

        //Receber os dados do estabelecimento da outra activity
        Intent i = getIntent();
        mFornecedor = (Fornecedor) i.getSerializableExtra("Fornecedor");

        // Busca as avaliações atribuidas ao fornecedor
        buscaAvaliacoes(mFornecedor);

        // Monta listview e mAdapter
        mAdapter = new ListaAvaliacoesAdapterView(this, mFornecedor.getAvaliacoes());

        mExibeListaAvaliacao.setAdapter(mAdapter);
        mNomeEstabelecimento.setText(mFornecedor.getNome());
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    //Método que chama a activity para exibir as avaliações do estabelecimento
    public void buscaAvaliacoes(final Fornecedor fornecedor){
        //Buscar avaliações do estabelecimento selecionado
        final DatabaseReference mFireBase;
        mFireBase = ConfiguracaoFirebase.getFirebase().child("fornecedor").child(fornecedor.getId()).child("avaliacao");
        mFireBase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot dados : dataSnapshot.getChildren()) {
                    Avaliacao avaliacao = dados.getValue(Avaliacao.class);
                    mFornecedor.getAvaliacoes().add((avaliacao));
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