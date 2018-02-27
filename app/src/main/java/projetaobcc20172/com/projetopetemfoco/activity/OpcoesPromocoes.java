package projetaobcc20172.com.projetopetemfoco.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.SeekBar;
import android.widget.TextView;

import projetaobcc20172.com.projetopetemfoco.R;
import projetaobcc20172.com.projetopetemfoco.config.ConfiguracaoExibirPromocao;
import projetaobcc20172.com.projetopetemfoco.utils.Enumerates;
import projetaobcc20172.com.projetopetemfoco.utils.Utils;


public class OpcoesPromocoes extends Activity implements View.OnClickListener {
    private CheckBox cbProximo;
    private CheckBox cbPadrao;
    private int raio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_opcoes_promocoes);
        configurarSeekBar();

        cbPadrao = findViewById(R.id.cbPadraoOpcoePromocao);
        cbPadrao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                controleCheckBox(0);
            }
        });

        cbProximo = findViewById(R.id.cbProximoOpcoePromocao);
        cbProximo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                controleCheckBox(1);
            }
        });

        Button bntSalvarOpcao = findViewById(R.id.btnSalvarOpcaoPromocao);
        bntSalvarOpcao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                salvarOpcoes();
                Utils.mostrarMensagemCurta(OpcoesPromocoes.this, getString(R.string.filtro_atualizado_servico));
                finish();
            }
        });

        onResume();
    }

    @Override
    public void onClick(View v) {
        //vazio
    }


    private void configurarSeekBar(){
        SeekBar skRaio = findViewById(R.id.sbRaioOpcaoPromocao);
        final TextView tvRaio = findViewById(R.id.tvRaioOpcaoPromocao);
        raio = ConfiguracaoExibirPromocao.getRaio().getRaioAtual();
        skRaio.setMax(ConfiguracaoExibirPromocao.getRaio().getRange());
        skRaio.setProgress(raio);
        tvRaio.setText(this.raio+" m");
        skRaio.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                raio = i;
                tvRaio.setText(i +" m");
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

    @Override
    public void onResume(){
        super.onResume();
        if(ConfiguracaoExibirPromocao.getsOpcoesPromocao().equals(Enumerates.Promocao.PADRAO)) {
            controleCheckBox(0);
        }
        else if(ConfiguracaoExibirPromocao.getsOpcoesPromocao().equals(Enumerates.Promocao.PROXIMO)){
            controleCheckBox(1);
        }
    }

    private void controleCheckBox(int i){
        this.cbPadrao.setChecked(false);
        this.cbProximo.setChecked(false);
        if(i==0){
            this.cbPadrao.setChecked(true);
        }
        else if(i==1){
            this.cbProximo.setChecked(true);
        }
    }

    private void salvarOpcoes(){
        if(cbPadrao.isChecked()){
            ConfiguracaoExibirPromocao.setsOpcoesPromocao(Enumerates.Promocao.PADRAO);
        }
        else if(this.cbProximo.isChecked()){
            ConfiguracaoExibirPromocao.setsOpcoesPromocao(Enumerates.Promocao.PROXIMO);
        }
        ConfiguracaoExibirPromocao.getRaio().setRaioAtual((raio));
    }

}
