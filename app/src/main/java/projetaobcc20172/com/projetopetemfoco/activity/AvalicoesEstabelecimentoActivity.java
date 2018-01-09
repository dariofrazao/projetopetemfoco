package projetaobcc20172.com.projetopetemfoco.activity;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.ListView;
import android.widget.TextView;

import projetaobcc20172.com.projetopetemfoco.R;
import projetaobcc20172.com.projetopetemfoco.adapter.ServicoAdapterListaViewInformacoes;
import projetaobcc20172.com.projetopetemfoco.model.Fornecedor;

public class AvalicoesEstabelecimentoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_avalicoes_estabelecimento);

        Toolbar toolbar;
        toolbar = findViewById(R.id.tb_acesso_infomacoes_estabelecimento);

        // Configura toolbar
        toolbar.setTitle(R.string.tb_acesso_estabelecimento);
        toolbar.setTitleTextColor(Color.WHITE);
        toolbar.setNavigationIcon(R.drawable.ic_action_arrow_left_white);
        setSupportActionBar(toolbar);
    }
/*
        TextView mExibeNomeEstabelecimento = findViewById(R.id.tvExibeNomeEstabelecimento);
        TextView mExibeEmailEstabelecimentor = findViewById(R.id.tvExibeEmailEstabelecimentor);
        TextView mExibeTelefoneEstabelecimento = findViewById(R.id.tvExibeTelefoneEstabelecimento);
        TextView mExibeCpfCnpjEstabelecimento = findViewById(R.id.tvExibeCpfCnpjEstabelecimento);
        TextView mExibeHorarioEstabelecimento = findViewById(R.id.tvExibeHorarioEstabelecimento);
        ListView mExibeListaServicos = findViewById(R.id.lvListaServicos);

        //Receber os dados do estabelecimento da outra activity
        Intent i = getIntent();
        mFornecedor = (Fornecedor) i.getSerializableExtra("Fornecedor");

        mExibeNomeEstabelecimento.setText(mFornecedor.getNome());
        mExibeEmailEstabelecimentor.setText(mFornecedor.getEmail());
        mExibeTelefoneEstabelecimento.setText(mFornecedor.getTelefone());
        mExibeCpfCnpjEstabelecimento.setText(mFornecedor.getCpfCnpj());
        mExibeHorarioEstabelecimento.setText(mFornecedor.getHorarios());

        // Monta listview e mAdapter
        mAdapter = new ServicoAdapterListaViewInformacoes(this, mFornecedor.getServicos());
        mExibeListaServicos.setAdapter(mAdapter);

    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }*/
}