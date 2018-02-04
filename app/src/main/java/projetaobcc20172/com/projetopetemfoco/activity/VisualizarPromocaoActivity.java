package projetaobcc20172.com.projetopetemfoco.activity;

import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.VisibleForTesting;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import projetaobcc20172.com.projetopetemfoco.R;
import projetaobcc20172.com.projetopetemfoco.model.Promocao;

public class VisualizarPromocaoActivity extends AppCompatActivity {

    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    public Promocao mPromocao;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visualizar_promocao);
        Button btnEst = findViewById(R.id.btnPromocaoEst);
        Toolbar toolbar = findViewById(R.id.tb_acesso_infomacoes_estabelecimento);
        btnEst.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                exibirDetalhesEst();
            }
        });
        toolbar.setTitle(R.string.tb_detalhes_promocao);
        toolbar.setTitleTextColor(Color.WHITE);
        toolbar.setNavigationIcon(R.drawable.ic_action_arrow_left_white);
        setSupportActionBar(toolbar);
        this.mPromocao = (Promocao) getIntent().getSerializableExtra("promocao");
        inicializarTela();
    }


    private void exibirDetalhesEst(){
        Intent intent = new Intent(this, AcessoInformacoesEstabelecimentoActivity.class);
        intent.putExtra("Fornecedor", this.mPromocao.getmFornecedor());
        this.startActivity(intent);
    }

    private void inicializarTela(){
        TextView titulo = findViewById(R.id.tvExibeTituloPromo);
        TextView descricao = findViewById(R.id.tvDescricao);
        TextView valor = findViewById(R.id.tvValor);
        titulo.setText(this.mPromocao.getTitulo());
        descricao.setText(this.mPromocao.getDescricao());
        valor.setText(this.mPromocao.getValor());
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

}
