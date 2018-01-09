package projetaobcc20172.com.projetopetemfoco.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.VisibleForTesting;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.io.Serializable;

import projetaobcc20172.com.projetopetemfoco.R;
import projetaobcc20172.com.projetopetemfoco.adapter.ServicoAdapterListaViewAvaliacoes;
import projetaobcc20172.com.projetopetemfoco.model.Avaliacao;
import projetaobcc20172.com.projetopetemfoco.model.Fornecedor;

public class AvalicoesEstabelecimentoActivity extends AppCompatActivity implements Serializable {
    private Fornecedor mFornecedor;
    @SuppressLint("WrongConstant")
    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    //permite que essa variavel seja vista pela classe de teste

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_avalicoes_estabelecimento);

        ArrayAdapter<Avaliacao> mAdapter;

        Toolbar toolbar;
        toolbar = findViewById(R.id.tb_lista_avaliacoes_estabelecimento);

        //Receber os dados do estabelecimento da outra activity
        Intent i = getIntent();
        mFornecedor = (Fornecedor) i.getSerializableExtra("Fornecedor");

        // Configura toolbar
        toolbar.setTitle(R.string.tb_avaliacoes_estabelecimento);
        toolbar.setTitleTextColor(Color.WHITE);
        toolbar.setNavigationIcon(R.drawable.ic_action_arrow_left_white);
        setSupportActionBar(toolbar);

        TextView mNomeEstabelecimento = findViewById(R.id.tvExibeNomeEstabelecimentoAvaliacao);
        ListView mExibeListaAvaliacao = findViewById(R.id.lvListaAvaliacoes);

        // Monta listview e mAdapter
        mAdapter = new ServicoAdapterListaViewAvaliacoes(this, mFornecedor.getAvaliacoes());
        mExibeListaAvaliacao.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();

        mNomeEstabelecimento.setText(mFornecedor.getNome());

    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}