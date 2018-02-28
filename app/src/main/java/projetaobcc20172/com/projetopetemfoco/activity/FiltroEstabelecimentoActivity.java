package projetaobcc20172.com.projetopetemfoco.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.SeekBar;
import android.widget.TextView;

import projetaobcc20172.com.projetopetemfoco.R;
import projetaobcc20172.com.projetopetemfoco.config.ConfiguracaoBuscaEstab;
import projetaobcc20172.com.projetopetemfoco.utils.Enumerates;
import projetaobcc20172.com.projetopetemfoco.utils.Utils;


public class FiltroEstabelecimentoActivity extends Activity implements View.OnClickListener {

    private CheckBox cbProx;
    private CheckBox cbAvaliacao;
    private CheckBox cbTipoTodos;
    private CheckBox cbTipoEtabelecimento;
    private CheckBox cbTipoAutonomo;
    private int raio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filtro_estabelecimento);
        Button btnSalvarFiltro = findViewById(R.id.btnSalvarFiltro);
        cbProx = findViewById(R.id.cbProx);
        cbAvaliacao = findViewById(R.id.cbAva);

        cbTipoTodos = findViewById(R.id.cbTipoTodos);
        cbTipoEtabelecimento = findViewById(R.id.cbTipoEstabelecimento);
        cbTipoAutonomo = findViewById(R.id.cbTipoAutonomo);

        configurarSeekBar();

        cbAvaliacao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                controleCheckBox(1);
            }
        });
        cbProx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                controleCheckBox(0);
            }
        });

        cbTipoTodos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                controleCheckBoxTipo(0);
            }
        });
        cbTipoEtabelecimento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                controleCheckBoxTipo(1);
            }
        });
        cbTipoAutonomo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                controleCheckBoxTipo(2);
            }
        });


        btnSalvarFiltro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                salvarOpcoes();
                Utils.mostrarMensagemCurta(FiltroEstabelecimentoActivity.this, getString(R.string.filtro_atualizado_servico));
                finish();
            }
        });


    }

    @Override
    protected void onResume(){
        super.onResume();
        if(ConfiguracaoBuscaEstab.getsFiltro().equals(Enumerates.Filtro.DISTANCIA)) {
            controleCheckBox(0);
        }else if(ConfiguracaoBuscaEstab.getsFiltro().equals(Enumerates.Filtro.AVALICAO)){
            controleCheckBox(1);
        }

        if(ConfiguracaoBuscaEstab.getsTipoFornecedor().equals(Enumerates.TipoFornecedor.TODOS)) {
            controleCheckBox(0);
        }else if(ConfiguracaoBuscaEstab.getsTipoFornecedor().equals(Enumerates.TipoFornecedor.ESTABELECIMENTO)){
            controleCheckBox(1);
        }else if(ConfiguracaoBuscaEstab.getsTipoFornecedor().equals(Enumerates.TipoFornecedor.AUTONOMO)){
            controleCheckBox(2);
        }


    }
    private void salvarOpcoes(){
        if(this.cbAvaliacao.isChecked()){
            ConfiguracaoBuscaEstab.setsFiltro(Enumerates.Filtro.AVALICAO);
        }
        else if(this.cbProx.isChecked()){
            ConfiguracaoBuscaEstab.setsFiltro(Enumerates.Filtro.DISTANCIA);
        }


        if(this.cbTipoTodos.isChecked()){
            ConfiguracaoBuscaEstab.setsTipoFornecedor(Enumerates.TipoFornecedor.TODOS);
        }
        else if(this.cbTipoEtabelecimento.isChecked()){
            ConfiguracaoBuscaEstab.setsTipoFornecedor(Enumerates.TipoFornecedor.ESTABELECIMENTO);
        }
        else if(this.cbTipoAutonomo.isChecked()){
            ConfiguracaoBuscaEstab.setsTipoFornecedor(Enumerates.TipoFornecedor.AUTONOMO);
        }


        ConfiguracaoBuscaEstab.getRaio().setRaioAtual((this.raio));

    }

    @Override
    public void onClick(View view) {
        //Método com corpo vazio
    }

    private void configurarSeekBar(){
        SeekBar skRaio = findViewById(R.id.sbRaio);
        final TextView tvRaio = findViewById(R.id.tvRaio);
        this.raio = (ConfiguracaoBuscaEstab.getRaio().getRaioAtual());
        skRaio.setMax(ConfiguracaoBuscaEstab.getRaio().getRange());
        skRaio.setProgress(this.raio);
        tvRaio.setText(this.raio+ConfiguracaoBuscaEstab.getRaio().getInicial()+" km");
        skRaio.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            int i1;
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
//                if(!cbProx.isChecked()){
//                    ConfiguracoesBuscaServico.setsFiltro(Enumerates.Filtro.DISTANCIA);
//                    controleCheckBox(0);
//                }
                raio = (byte) i;
                i1 = i + ConfiguracaoBuscaEstab.getRaio().getInicial();
                tvRaio.setText(i1 +" km");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                //Método com corpo vazio
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                //Método com corpo vazio
            }
        });
    }

    private void controleCheckBox(int i){
        this.cbProx.setChecked(false);
        this.cbAvaliacao.setChecked(false);
        if(i==0){
            this.cbProx.setChecked(true);
        }
        else if(i==1){
            this.cbAvaliacao.setChecked(true);
        }
    }

    private void controleCheckBoxTipo(int i){
        cbTipoTodos.setChecked(false);
        cbTipoEtabelecimento.setChecked(false);
        cbTipoAutonomo.setChecked(false);

        if(i==0){
            cbTipoTodos.setChecked(true);
        }
        else if(i==1){
            cbTipoEtabelecimento.setChecked(true);
        }
        else if(i==2){
            cbTipoAutonomo.setChecked(true);
        }

    }
}
