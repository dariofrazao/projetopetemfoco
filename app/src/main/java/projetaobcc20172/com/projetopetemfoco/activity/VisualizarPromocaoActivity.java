package projetaobcc20172.com.projetopetemfoco.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.VisibleForTesting;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import projetaobcc20172.com.projetopetemfoco.R;
import projetaobcc20172.com.projetopetemfoco.config.ConfiguracaoFirebase;
import projetaobcc20172.com.projetopetemfoco.model.Fornecedor;
import projetaobcc20172.com.projetopetemfoco.model.Promocao;
import projetaobcc20172.com.projetopetemfoco.model.Servico;
import projetaobcc20172.com.projetopetemfoco.utils.Utils;

public class VisualizarPromocaoActivity extends AppCompatActivity {

    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    public Promocao mPromocao;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visualizar_promocao);
        Toolbar toolbar = findViewById(R.id.tb_acesso_infomacoes_estabelecimento);
        toolbar.setTitle(R.string.tb_detalhes_promocao);
        toolbar.setTitleTextColor(Color.WHITE);
        toolbar.setNavigationIcon(R.drawable.ic_action_arrow_left_white);
        setSupportActionBar(toolbar);
        this.mPromocao = (Promocao) getIntent().getSerializableExtra("promocao");
        inicializarTela();
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