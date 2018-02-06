package projetaobcc20172.com.projetopetemfoco.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import projetaobcc20172.com.projetopetemfoco.R;
import projetaobcc20172.com.projetopetemfoco.database.services.EnderecoDaoImpl;
import projetaobcc20172.com.projetopetemfoco.excecoes.CampoObrAusenteException;
import projetaobcc20172.com.projetopetemfoco.model.Endereco;
import projetaobcc20172.com.projetopetemfoco.utils.MaskUtil;
import projetaobcc20172.com.projetopetemfoco.utils.Utils;
import projetaobcc20172.com.projetopetemfoco.utils.VerificadorDeObjetos;

public class EditarEnderecoActivity extends AppCompatActivity {

    private EditText mLogradouro, mNumero, mComplemento, mBairro, mLocalidade, mCep;
    private Spinner mSpinnerUf;
    private String mIdEndereco;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_endereco);

        Intent intent = getIntent();

        Toolbar toolbar;
        toolbar = findViewById(R.id.tb_editar_endereco);

        // Configura toolbar
        toolbar.setTitle(R.string.tb_editar_endereco);
        toolbar.setTitleTextColor(Color.WHITE);
        toolbar.setNavigationIcon(R.drawable.ic_action_arrow_left_white);
        setSupportActionBar(toolbar);

        mCep = findViewById(R.id.etEditarCepEndereco);
        mLocalidade = findViewById(R.id.etEditarLocalidadeEndereco);
        mLogradouro = findViewById(R.id.etEditarLogradouroEndereco);
        mNumero = findViewById(R.id.etEditarNumeroEndereco);
        mComplemento = findViewById(R.id.etEditarComplementoEndereco);
        mBairro = findViewById(R.id.etEditarBairroEndereco);

        //Obter os dados do endereço para edição
        mCep.setText(intent.getStringExtra("cepEndereco"));
        mCep.addTextChangedListener(MaskUtil.insert(mCep, MaskUtil.MaskType.CEP));

        mLocalidade.setText(intent.getStringExtra("localidadeEndereco"));
        mLogradouro.setText(intent.getStringExtra("logradouroEndereco"));
        mNumero.setText(intent.getStringExtra("numeroEndereco"));
        mComplemento.setText(intent.getStringExtra("complementoEndereco"));
        mBairro.setText(intent.getStringExtra("bairroEndereco"));
        mIdEndereco = intent.getStringExtra("idEndereco");

        //Preparar o adaptar do Spinner para exibir as UF's do Endereço
        mSpinnerUf = findViewById(R.id.ufEditarSpinner);
        ArrayAdapter<String> adapter_state = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_dropdown_item, getResources().getStringArray(R.array.uf));
        mSpinnerUf.setAdapter(adapter_state);
        String uf = intent.getStringExtra("ufEndereco");
        int posicaoUf = adapter_state.getPosition(uf);
        mSpinnerUf.setSelection(posicaoUf);


        Button mBtnEditarEndereco = findViewById(R.id.botao_editar_endereco);
        mBtnEditarEndereco.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                editarEnderecoUsuario();
            }
        });

    }

    private boolean verificarCamposPreenchidos(){
        return (!mCep.getText().toString().isEmpty() ||
                !mLogradouro.getText().toString().isEmpty() ||
                !mNumero.getText().toString().isEmpty() ||
                !mComplemento.getText().toString().isEmpty() ||
                !mLocalidade.getText().toString().isEmpty() ||
                !mBairro.getText().toString().isEmpty());
    }


    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    //Método do botão voltar
    @Override
    public void onBackPressed() {
        if (verificarCamposPreenchidos()) confirmarSaida();
        else EditarEnderecoActivity.super.onBackPressed();
    }

    //Método que exibe pergunta de confirmação ao usuário caso ele clique no botão de voltar com as
    //informações do endereço inseridas nos campos
    public void confirmarSaida(){
        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which){
                    case DialogInterface.BUTTON_POSITIVE:
                        // Botão sim foi clicado
                        EditarEnderecoActivity.super.onBackPressed();
                        break;

                    case DialogInterface.BUTTON_NEGATIVE:
                        // Botão não foi clicado
                        break;
                    default:
                        break;
                }
            }
        };

        Utils.mostrarPerguntaSimNao(this, getString(R.string.atencao),
                getString(R.string.pergunta_confirma_dados_serao_perdidos), dialogClickListener,
                dialogClickListener);
    }

    //Método que recupera os dados do endereço e chama o DAO para editar no banco
    private void editarEnderecoUsuario() {
        try {

            //Recuperar id do usuário logado
            String mIdUsuarioLogado;
            mIdUsuarioLogado = getPreferences("id", EditarEnderecoActivity.this);

            //Recuperar os campos do endereço informados pelo usuário
            Endereco mEndereco;
            mEndereco = new Endereco();
            mEndereco.setId(mIdEndereco);
            mEndereco.setLogradouro(mLogradouro.getText().toString());
            mEndereco.setNumero(mNumero.getText().toString());
            mEndereco.setComplemento(mComplemento.getText().toString());
            mEndereco.setBairro(mBairro.getText().toString());
            mEndereco.setLocalidade(mLocalidade.getText().toString());
            mEndereco.setCep(mCep.getText().toString());
            mEndereco.setUf(mSpinnerUf.getSelectedItem().toString());

            VerificadorDeObjetos.vDadosObrEndereco(mEndereco);
            EnderecoDaoImpl enderecoDao =  new EnderecoDaoImpl(this);

            //Chamada do DAO para editar no banco
            enderecoDao.atualizar(mEndereco, mIdUsuarioLogado);
            abrirTelaEnderecos();

        } catch (CampoObrAusenteException e) {
            Utils.mostrarMensagemCurta(getApplicationContext(), getApplicationContext().getString(R.string.erro_atualizacao_campos_obrigatorios_endereco));
        } catch (Exception e) {
            Utils.mostrarMensagemCurta(getApplicationContext(), getApplicationContext().getString(R.string.erro_atualizacao_campos_obrigatorios_endereco));
        }
    }

    public void abrirTelaEnderecos() {
        finish();
    }

    //Método que recupera o id do usuário logado
    public static String getPreferences(String key, Context context) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.getString(key, null);
    }
}
