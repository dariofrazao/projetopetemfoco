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
import projetaobcc20172.com.projetopetemfoco.database.services.EnderecoDaoImpl;
import projetaobcc20172.com.projetopetemfoco.model.Endereco;
import projetaobcc20172.com.projetopetemfoco.utils.Utils;

public class InfoEnderecoActivity extends AppCompatActivity {

    private TextView mTvLogradouro, mTvNumero, mTvComplemento, mTvBairro, mTvCidade, mTvEstado, mTvCep;
    private Button mEditar, mExcluir;
    private Endereco mEndereco;
    private String mIdUsuarioLogado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_endereco);

        mEditar = findViewById(R.id.btnEditar);
        mExcluir = findViewById(R.id.btnExcluir);

        mTvLogradouro = findViewById(R.id.tvLogradouro);
        mTvNumero = findViewById(R.id.tvNumero);
        mTvComplemento = findViewById(R.id.tvComplemento);
        mTvBairro = findViewById(R.id.tvBairro);
        mTvCidade = findViewById(R.id.tvCidade);
        mTvEstado = findViewById(R.id.tvEstado);
        mTvCep = findViewById(R.id.tvCep);

        mEndereco = (Endereco) getIntent().getSerializableExtra("Endereco");

        //Recuperar id do usuário logado
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(InfoEnderecoActivity.this);
        mIdUsuarioLogado = preferences.getString("id", "");

        Toolbar toolbar;
        toolbar = findViewById(R.id.tb_main);

        // Configura toolbar
        toolbar.setTitle(R.string.tb_detalhes_endereco);
        toolbar.setTitleTextColor(Color.WHITE);
        toolbar.setNavigationIcon(R.drawable.ic_action_arrow_left_white);
        setSupportActionBar(toolbar);

        preencherCampos();

        editarListener();

        excluirListener();

    }

    //Método que preenche os campos com as informações do endereço
    public void preencherCampos() {
        mTvLogradouro.setText(mEndereco.getLogradouro());
        if(mEndereco.getNumero() == ""){
            mTvNumero.setVisibility(View.INVISIBLE);
        }
        else{
            mTvNumero.setText("Número: " + mEndereco.getNumero());
        }
        if(mEndereco.getComplemento() == ""){
            mTvComplemento.setVisibility(View.INVISIBLE);
        }
        else{
            mTvComplemento.setText("Complemento: " + mEndereco.getComplemento());
        }
        mTvBairro.setText("Bairro: " + mEndereco.getBairro());
        mTvCidade.setText("Cidade: " + mEndereco.getLocalidade());
        mTvEstado.setText("Estado: " + mEndereco.getUf());
        mTvCep.setText("CEP: " + mEndereco.getCep());
    }

    //Método para excluir um endereço
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
                                EnderecoDaoImpl enderecoDao = new EnderecoDaoImpl(InfoEnderecoActivity.this);
                                enderecoDao.remover(mEndereco, mIdUsuarioLogado);
                                InfoEnderecoActivity.super.onBackPressed();
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

                Utils.mostrarPerguntaSimNao(InfoEnderecoActivity.this, getString(R.string.atencao),
                        getString(R.string.pergunta_confirma_remocao_endereco), dialogClickListener,
                        dialogClickListener);
            }
        });
    }

    public void editarListener() {
        mEditar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Enviar para a Activity de Edição do endereço seus atuais dados salvos para exibição
                Intent intent = new Intent(InfoEnderecoActivity.this, EditarEnderecoActivity.class);
                intent.putExtra("idEndereco", mEndereco.getId());
                intent.putExtra("logradouroEndereco", mEndereco.getLogradouro());
                intent.putExtra("numeroEndereco", mEndereco.getNumero());
                intent.putExtra("complementoEndereco", mEndereco.getComplemento());
                intent.putExtra("bairroEndereco", mEndereco.getBairro());
                intent.putExtra("localidadeEndereco", mEndereco.getLocalidade());
                intent.putExtra("ufEndereco", mEndereco.getUf());
                intent.putExtra("cepEndereco", mEndereco.getCep());
                startActivity(intent);
                finish();
            }
        });
    }

    //Método do botão voltar
    @Override
    public void onBackPressed() {
        InfoEnderecoActivity.super.onBackPressed();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }


}

