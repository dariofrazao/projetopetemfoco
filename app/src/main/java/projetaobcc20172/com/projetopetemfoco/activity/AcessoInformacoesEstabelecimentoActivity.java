package projetaobcc20172.com.projetopetemfoco.activity;
/**
 * Created by Alexsandro on 03/12/17.
 */


import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.VisibleForTesting;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import projetaobcc20172.com.projetopetemfoco.R;
import projetaobcc20172.com.projetopetemfoco.model.Estabelecimento;

public class AcessoInformacoesEstabelecimentoActivity extends AppCompatActivity {
    Estabelecimento mEstabelecimento;
    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    //permite que essa variavel seja vista pela classe de teste

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_acesso_informacoes_estabelecimento);

        Toolbar toolbar;
        toolbar = findViewById(R.id.tb_acesso_infomacoes_estabelecimento);

        // Configura toolbar
        toolbar.setTitle(R.string.tb_acesso_estabelecimento);
        toolbar.setTitleTextColor(Color.WHITE);
        toolbar.setNavigationIcon(R.drawable.ic_action_arrow_left_white);
        setSupportActionBar(toolbar);

        TextView mExibeNomeEstabelecimento = findViewById(R.id.tvExibeNomeEstabelecimento);
        TextView mExibeEmailEstabelecimentor = findViewById(R.id.tvExibeEmailEstabelecimentor);
        TextView mExibeTelefoneEstabelecimento = findViewById(R.id.tvExibeTelefoneEstabelecimento);
        TextView mExibeCpfCnpjEstabelecimento = findViewById(R.id.tvExibeCpfCnpjEstabelecimento);
        TextView mExibeHorarioEstabelecimento = findViewById(R.id.tvExibeHorarioEstabelecimento);

        //Receber os dados do estabelecimento da outra activity
        Intent i = getIntent();
        mEstabelecimento = (Estabelecimento) i.getSerializableExtra("Estabelecimento");

        mExibeNomeEstabelecimento.setText(mEstabelecimento.getNome());
        mExibeEmailEstabelecimentor.setText(mEstabelecimento.getEmail());
        mExibeTelefoneEstabelecimento.setText(mEstabelecimento.getTelefone());
        mExibeCpfCnpjEstabelecimento.setText(mEstabelecimento.getCpfCnpj());
        mExibeHorarioEstabelecimento.setText(mEstabelecimento.getHorarios());
    }
}
