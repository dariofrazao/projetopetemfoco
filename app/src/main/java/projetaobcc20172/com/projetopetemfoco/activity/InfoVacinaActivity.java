package projetaobcc20172.com.projetopetemfoco.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import projetaobcc20172.com.projetopetemfoco.R;
import projetaobcc20172.com.projetopetemfoco.database.services.VacinaDaoImpl;
import projetaobcc20172.com.projetopetemfoco.model.Vacina;
import projetaobcc20172.com.projetopetemfoco.utils.Utils;

public class InfoVacinaActivity extends AppCompatActivity {

    private TextView mTvDescricao, mTvData;
    private Button mEditar, mExcluir;
    private Vacina mVacina;
    private String mIdUsuarioLogado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_vacina);

        mEditar = findViewById(R.id.btnEditar);
        mExcluir = findViewById(R.id.btnExcluir);

        mTvDescricao = findViewById(R.id.tvDescricao);
        mTvData = findViewById(R.id.tvData);

        mVacina = (Vacina) getIntent().getSerializableExtra("Vacina");

        //Recuperar id do usuário logado
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(InfoVacinaActivity.this);
        mIdUsuarioLogado = preferences.getString("id", "");

        Toolbar toolbar;
        toolbar = findViewById(R.id.tb_main);

        // Configura toolbar
        toolbar.setTitle(R.string.tb_detalhes_vacina);
        toolbar.setTitleTextColor(Color.WHITE);
        toolbar.setNavigationIcon(R.drawable.ic_action_arrow_left_white);
        setSupportActionBar(toolbar);

        preencherCampos();

        editarListener();

        excluirListener();

    }

    //Método que preenche os campos com as informações da vacina
    public void preencherCampos() {
        mTvDescricao.setText("Descrição: " + mVacina.getmDescricao());
        mTvData.setText("Data: " + mVacina.getmData());
    }

    //Método para excluir uma vacina
    public void excluirListener() {
        mExcluir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case DialogInterface.BUTTON_POSITIVE:
                                // Botão sim foi clicado
                                VacinaDaoImpl vacinaDao = new VacinaDaoImpl(InfoVacinaActivity.this);
                                vacinaDao.remover(mVacina, mIdUsuarioLogado);
                                InfoVacinaActivity.super.onBackPressed();
                                finish();
                                break;

                            case DialogInterface.BUTTON_NEGATIVE:
                                // Botão não foi clicado
                                break;
                            default:
                                break;
                        }
                    }
                };

                Utils.mostrarPerguntaSimNao(InfoVacinaActivity.this, getString(R.string.atencao),
                        getString(R.string.pergunta_confirma_remocao_vacina), dialogClickListener,
                        dialogClickListener);
            }
        });
    }

    public void editarListener() {
        mEditar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Enviar para a Activity de Edição do pet seus atuais dados salvos para exibição
                Intent intent = new Intent(InfoVacinaActivity.this, CadastroVacinaActivity.class);
                intent.putExtra("vacina", mVacina);
                intent.putExtra("editar",true);
                startActivity(intent);
                finish();
            }
        });
    }

    //Método do botão voltar
    @Override
    public void onBackPressed() {
        InfoVacinaActivity.super.onBackPressed();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }


}

