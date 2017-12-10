package projetaobcc20172.com.projetopetemfoco.activity;
/**
 * Created by Alexsandro on 03/12/17.
 */

import android.content.Intent;
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
import android.widget.Toast;

import projetaobcc20172.com.projetopetemfoco.R;
import projetaobcc20172.com.projetopetemfoco.excecoes.CampoEnderecoObrAusenteException;
import projetaobcc20172.com.projetopetemfoco.helper.MaskUtil;
import projetaobcc20172.com.projetopetemfoco.helper.Util;
import projetaobcc20172.com.projetopetemfoco.helper.ZipCodeListener;
import projetaobcc20172.com.projetopetemfoco.model.Endereco;
import projetaobcc20172.com.projetopetemfoco.model.Fornecedor;
import projetaobcc20172.com.projetopetemfoco.model.Usuario;

public class CadastroEnderecoActivity extends AppCompatActivity{

    private EditText logradouro, numero, complemento, bairro, localidade, cep;
    private Spinner mSpinnerUf;
    private Button botaoCadastrarEndereco;
    private Endereco endereco;
    private Usuario usuario;
    private Fornecedor fornecedor;
    private Util util;

    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE) //permite que essa variavel seja vista pela classe de teste
    private Toast mToast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_endereco);

        Toolbar toolbar;
        toolbar = findViewById(R.id.tb_endereco);

        cep = findViewById(R.id.editText_endereco_cep);
        localidade = findViewById(R.id.editText_endereco_localidade);
        logradouro = findViewById(R.id.editText_endereco_logradouro);
        numero = findViewById(R.id.editText_endereco_numero);
        complemento = findViewById(R.id.editText_endereco_complemento);
        bairro = findViewById(R.id.editText_endereco_bairro);

        cep.addTextChangedListener(new ZipCodeListener(this));
        cep.addTextChangedListener(MaskUtil.insert(cep, MaskUtil.MaskType.CEP));

        //Preparar o adaptar do Spinner para exibir as UF's do Endereço
        mSpinnerUf = findViewById(R.id.ufSpinner);
        ArrayAdapter<String> adapter_state = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_dropdown_item, getResources().getStringArray(R.array.ufEndereco));
        mSpinnerUf.setAdapter(adapter_state);

        util = new Util(this,
                R.id.editText_endereco_cep,
                R.id.editText_endereco_logradouro,
                R.id.editText_endereco_localidade,
                R.id.editText_endereco_numero,
                R.id.editText_endereco_complemento,
                R.id.editText_endereco_bairro,
                R.id.ufSpinner);

        //Receber os dados do usuário e/ou fornecedor da outra activity
        Intent i = getIntent();
        usuario = (Usuario) i.getSerializableExtra("Usuario");
        fornecedor = (Fornecedor) i.getSerializableExtra("Fornecedor");

        botaoCadastrarEndereco = findViewById(R.id.botao_finalizar_cadastro_endereco);
        botaoCadastrarEndereco.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Recuperar os campos do endereço informados pelo usuário
                endereco = new Endereco();
                endereco.setLogradouro(logradouro.getText().toString());
                endereco.setNumero(numero.getText().toString());
                endereco.setComplemento(complemento.getText().toString());
                endereco.setBairro(bairro.getText().toString());
                endereco.setLocalidade(localidade.getText().toString());
                endereco.setCep(cep.getText().toString());
                endereco.setUf(mSpinnerUf.getSelectedItem().toString());

                //Chama o método para cadastrar o usuário no banco(se o valor for "0", é um usuário consumidor
                if (usuario.getValor().equals("0")) {
                    cadastrarEnderecoUsuario();
                }
                //Chama o método para cadastrar o fornecedor no banco(se não for "0", é um fornecedor
                else{
                    cadastrarEnderecoFornecedor();
                }

            }
        });

        // Configura toolbar
        toolbar.setTitle("Cadastro de Endereço");
        toolbar.setTitleTextColor(Color.WHITE);
        toolbar.setNavigationIcon(R.drawable.ic_action_arrow_left_white);
        setSupportActionBar(toolbar);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    //Método para verificar se os campos do endereço estão em branco
    private void verificarCamposObrigatorios() throws CampoEnderecoObrAusenteException {
        if (logradouro.getText().toString().isEmpty()
            ||
            bairro.getText().toString().isEmpty()
            ||
            localidade.getText().toString().isEmpty()
            ||
            cep.getText().toString().isEmpty()
                ||
                mSpinnerUf.toString().isEmpty()
            ) {
            throw new CampoEnderecoObrAusenteException();
        }
    }

    //Método que salva os dados do usuário no banco
    private void cadastrarEnderecoUsuario() {

            try {
                usuario.setEndereco(endereco);
                this.verificarCamposObrigatorios();
                usuario.salvar();
                mToast = mToast.makeText(CadastroEnderecoActivity.this, R.string.sucesso_cadastro_Toast, Toast.LENGTH_LONG);
                mToast.show();
                salvarPreferencias("id", usuario.getId());
                abrirLoginUsuario();

            } catch(CampoEnderecoObrAusenteException e){
            mToast = mToast.makeText(CadastroEnderecoActivity.this, R.string.erro_cadastro_endereco_campos_obrigatorios_Toast, Toast.LENGTH_SHORT);
            mToast.show();
        } catch(Exception e){
            mToast = mToast.makeText(CadastroEnderecoActivity.this, R.string.erro_cadastro_endereco_campos_obrigatorios_Toast, Toast.LENGTH_SHORT);
            mToast.show();
        }
    }

    //Método que salva os dados do fornecedor no banco
    private void cadastrarEnderecoFornecedor(){
            try {
                fornecedor.setEndereco(endereco);
                this.verificarCamposObrigatorios();
                fornecedor.salvar();
                mToast = mToast.makeText(CadastroEnderecoActivity.this, R.string.sucesso_cadastro_Fornecedor, Toast.LENGTH_LONG);
                mToast.show();
                salvarPreferencias("id", fornecedor.getId());
                abrirLoginUsuario();

            } catch (CampoEnderecoObrAusenteException e) {
                mToast = mToast.makeText(CadastroEnderecoActivity.this, R.string.erro_cadastro_endereco_campos_obrigatorios_Toast, Toast.LENGTH_SHORT);
                mToast.show();
            } catch (Exception e) {
                mToast = mToast.makeText(CadastroEnderecoActivity.this, R.string.erro_cadastro_endereco_campos_obrigatorios_Toast, Toast.LENGTH_SHORT);
                mToast.show();
            }
        }


    public void abrirLoginUsuario() {
        Intent intent = new Intent(CadastroEnderecoActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

    //Método que salva o id do usuário/fornecedor nas preferências para login automático ao abrir aplicativo
    private void salvarPreferencias(String key, String value){
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, value);
        editor.commit();
    }

    //Método que trava os campos de endereço enquanto a busca pelo cep é realizada
    public void lockFields (boolean isToLock){
        util.lockFields(isToLock);
    }

    //Método que retorna o endereço completo a partir do cep informado
    public String getUriZipCode(){
        return "https://viacep.com.br/ws/"+cep.getText()+"/json/";
    }

    //Método que insere nos campos de endereço as informações obtidas pela busca (pelo cep)
    public void setDataViews (Endereco endereco){
        setFields(R.id.editText_endereco_localidade, endereco.getLocalidade());
        setFields(R.id.editText_endereco_bairro, endereco.getBairro());
        setFields(R.id.editText_endereco_logradouro, endereco.getLogradouro());
        setFields(R.id.editText_endereco_complemento, endereco.getComplemento());
        setSpinner(R.id.ufSpinner, R.array.ufEndereco, endereco.getUf());
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
}
