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
    private byte raio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filtro_estabelecimento);
        Button btnSalvarFiltro = findViewById(R.id.btnSalvarFiltro);
        cbProx = findViewById(R.id.cbProx);
        cbAvaliacao = findViewById(R.id.cbAva);
        this.configurarSeekBar();

        this.cbAvaliacao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickCheckBoxAvaliacao();
            }
        });

        this.cbProx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickCheckBoxDist();
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
            clickCheckBoxDist();
        }else if(ConfiguracaoBuscaEstab.getsFiltro().equals(Enumerates.Filtro.AVALICAO)){
            clickCheckBoxAvaliacao();
        }
    }
    private void salvarOpcoes(){
        if(this.cbAvaliacao.isChecked()){
            ConfiguracaoBuscaEstab.setsFiltro(Enumerates.Filtro.AVALICAO);
        }
        else if(this.cbProx.isChecked()){
            ConfiguracaoBuscaEstab.setsFiltro(Enumerates.Filtro.DISTANCIA);
        }
        ConfiguracaoBuscaEstab.getRaio().setRaioAtual((this.raio));

    }

    @Override
    public void onClick(View view) {

    }

    private void clickCheckBoxDist(){
        cbProx.setChecked(true);
        cbAvaliacao.setChecked(false);
    }

    private void clickCheckBoxAvaliacao(){
        cbAvaliacao.setChecked(true);
        cbProx.setChecked(false);
    }

    private void configurarSeekBar(){
        SeekBar skRaio = findViewById(R.id.sbRaio);
        final TextView tvRaio = findViewById(R.id.tvRaio);
        this.raio = (ConfiguracaoBuscaEstab.getRaio().getRaioAtual());
        skRaio.setMax(ConfiguracaoBuscaEstab.getRaio().getRange());
        skRaio.setProgress(this.raio);
        tvRaio.setText(this.raio+ConfiguracaoBuscaEstab.getRaio().getInicial()+" km");
        skRaio.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                raio = (byte) i;
                i = i + ConfiguracaoBuscaEstab.getRaio().getInicial();
                tvRaio.setText(i +" km");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }
}
