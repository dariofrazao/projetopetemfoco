package projetaobcc20172.com.projetopetemfoco.activity;

/**
 * Created by Alexsandro on 03/12/17.
 */

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.VisibleForTesting;
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
import projetaobcc20172.com.projetopetemfoco.helper.Util;
import projetaobcc20172.com.projetopetemfoco.helper.ZipCodeListener;
import projetaobcc20172.com.projetopetemfoco.model.Endereco;
import projetaobcc20172.com.projetopetemfoco.utils.MaskUtil;
import projetaobcc20172.com.projetopetemfoco.utils.Utils;
import projetaobcc20172.com.projetopetemfoco.utils.VerificadorDeObjetos;

/**
 * Activity de cadastro de endereço
 */
public class CadastroEnderecoActivity extends AppCompatActivity{

    private EditText mLogradouro, mNumero, mComplemento, mBairro, mLocalidade, mCep;
    private Spinner mSpinnerUf;
    private Util mUtil;
    private Endereco mEndereco;
    private String mIdUsuarioLogado;

    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    //permite que essa variavel seja vista pela classe de teste

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_endereco);

        Toolbar toolbar;
        toolbar = findViewById(R.id.tb_endereco);

        mCep = findViewById(R.id.etCadastroCepEndereco);
        mLocalidade = findViewById(R.id.etCadastroLocalidadeEndereco);
        mLogradouro = findViewById(R.id.etCadastroLogradouroEndereco);
        mNumero = findViewById(R.id.etCadastroNumeroEndereco);
        mComplemento = findViewById(R.id.etCadastroComplementoEndereco);
        mBairro = findViewById(R.id.etCadastroBairroEndereco);

        mCep.addTextChangedListener(new ZipCodeListener(this));
        mCep.addTextChangedListener(MaskUtil.insert(mCep, MaskUtil.MaskType.CEP));

        //Preparar o adaptar do Spinner para exibir as UF's do Endereço
        mSpinnerUf = findViewById(R.id.ufSpinner);
        ArrayAdapter<String> adapter_state = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_dropdown_item, getResources().getStringArray(R.array.uf));
        mSpinnerUf.setAdapter(adapter_state);

        mUtil = new Util(this,
                R.id.etCadastroCepEndereco,
                R.id.etCadastroLogradouroEndereco,
                R.id.etCadastroLocalidadeEndereco,
                R.id.etCadastroNumeroEndereco,
                R.id.etCadastroComplementoEndereco,
                R.id.etCadastroBairroEndereco,
                R.id.ufSpinner);

        Button mBtnCadastrarEndereco = findViewById(R.id.botao_finalizar_cadastro_endereco);
        mBtnCadastrarEndereco.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                cadastrarEnderecoUsuario();

            }
        });

        // Configura toolbar
        toolbar.setTitle(R.string.tb_cadastro_endereco);
        toolbar.setTitleTextColor(Color.WHITE);
        toolbar.setNavigationIcon(R.drawable.ic_action_arrow_left_white);
        setSupportActionBar(toolbar);

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
        else CadastroEnderecoActivity.super.onBackPressed();
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
                        CadastroEnderecoActivity.super.onBackPressed();
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

    //Método que recupera os dados básicos do usuário, adicionando o endereço e chamando o DAO para salvar no banco
    private void cadastrarEnderecoUsuario() {
        try {

            //Recuperar id do usuário logado
            mIdUsuarioLogado = getPreferences("id", CadastroEnderecoActivity.this);

            //Recuperar os campos do endereço informados pelo usuário
            mEndereco = new Endereco();
            mEndereco.setLogradouro(mLogradouro.getText().toString());
            mEndereco.setNumero(mNumero.getText().toString());
            mEndereco.setComplemento(mComplemento.getText().toString());
            mEndereco.setBairro(mBairro.getText().toString());
            mEndereco.setLocalidade(mLocalidade.getText().toString());
            mEndereco.setCep(mCep.getText().toString());
            mEndereco.setUf(mSpinnerUf.getSelectedItem().toString());

            VerificadorDeObjetos.vDadosObrEndereco(mEndereco);

            EnderecoDaoImpl enderecoDao =  new EnderecoDaoImpl(this);

            //Chamada do DAO para salvar no banco
            enderecoDao.inserir(mEndereco, mIdUsuarioLogado);
            abrirTelaEnderecos();

            } catch (CampoObrAusenteException e) {
            Utils.mostrarMensagemCurta(getApplicationContext(), getApplicationContext().getString(R.string.erro_cadastro_endereco_campos_obrigatorios_Toast));
            } catch (Exception e) {
            Utils.mostrarMensagemCurta(getApplicationContext(), getApplicationContext().getString(R.string.erro_cadastro_endereco_campos_obrigatorios_Toast));
            }
    }

    public void abrirTelaEnderecos() {
        finish();
    }

    //Método que trava os campos de endereço enquanto a busca pelo cep é realizada
    public void lockFields (boolean isToLock){
        mUtil.lockFields(isToLock);
    }

    //Método que retorna o endereço completo a partir do cep informado
    public String getUriZipCode(){
        return "https://viacep.com.br/ws/"+mCep.getText()+"/json/";
    }

    //Método que insere nos campos de endereço as informações obtidas pela busca (pelo cep)
    public void setDataViews (Endereco mEndereco){
        setFields(R.id.etCadastroLocalidadeEndereco, mEndereco.getLocalidade());
        setFields(R.id.etCadastroBairroEndereco, mEndereco.getBairro());
        setFields(R.id.etCadastroLogradouroEndereco, mEndereco.getLogradouro());
        setFields(R.id.etCadastroComplementoEndereco, mEndereco.getComplemento());
        setSpinner(R.id.ufSpinner, R.array.uf, mEndereco.getUf());
    }

    private void setFields (int id, String data){
        ((EditText)findViewById(id)).setText(data);
    }

    private void setSpinner (int id, int arrayId, String data){
        String[] itens = getResources().getStringArray(arrayId);

        for(int i = 0; i < itens.length; i++){

            if(itens[i].equals(data)){
                ((Spinner)findViewById(id)).setSelection(i);
                return;
            }
        }
        ((Spinner)findViewById(id)).setSelection(0);

    }

    //Método que recupera o id do usuário logado
    public static String getPreferences(String key, Context context) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.getString(key, null);
    }
}
