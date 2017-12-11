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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import projetaobcc20172.com.projetopetemfoco.R;
import projetaobcc20172.com.projetopetemfoco.excecoes.CampoObrAusenteException;
import projetaobcc20172.com.projetopetemfoco.helper.Base64Custom;
import projetaobcc20172.com.projetopetemfoco.model.Endereco;
import projetaobcc20172.com.projetopetemfoco.model.Usuario;
import projetaobcc20172.com.projetopetemfoco.utils.MaskUtil;
import projetaobcc20172.com.projetopetemfoco.utils.VerificadorDeObjetos;

public class CadastroEnderecoActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private EditText mLogradouro, mNumero, mComplemento, mBairro, mCidade, mCep;
    private Spinner mUf;
    private Endereco mEndereco;

    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    //permite que essa variavel seja vista pela classe de teste
    private Toast mToast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_endereco);

        Toolbar toolbar;
        toolbar = findViewById(R.id.tb_endereco);

        mLogradouro = findViewById(R.id.editText_endereco_logradouro);
        mNumero = findViewById(R.id.editText_endereco_numero);
        mComplemento = findViewById(R.id.editText_endereco_complemento);
        mBairro = findViewById(R.id.editText_endereco_bairro);
        mCidade = findViewById(R.id.editText_endereco_cidade);
        mCep = findViewById(R.id.editText_endereco_cep);
        mCep.addTextChangedListener(MaskUtil.insert(mCep, MaskUtil.MaskType.CEP));
        mUf = findViewById(R.id.spinner_endereco_uf);
        final String[] array_spinner = {"AC", "AL", "AP", "AM", "BA", "CE", "DF",
                "ES", "GO", "MA", "MG", "MS", "MG", "PA", "PB", "PA", "PE", "PI",
                "RJ", "RN", "RS", "RO", "RR", "SC", "SP", "SE", "TO"};

        ArrayAdapter adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, array_spinner);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        mUf.setAdapter(adapter);
        mUf.setOnItemSelectedListener(this);

        Button mBtnCadastrarEndereco = findViewById(R.id.botao_finalizar_cadastro_endereco);
        mBtnCadastrarEndereco.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Recuperar os campos do endereço informados pelo usuário
                mEndereco = new Endereco();
                mEndereco.setLogradouro(mLogradouro.getText().toString());
                mEndereco.setNumero(mNumero.getText().toString());
                mEndereco.setComplemento(mComplemento.getText().toString());
                mEndereco.setBairro(mBairro.getText().toString());
                mEndereco.setCidade(mCidade.getText().toString());
                mEndereco.setCep(mCep.getText().toString());
                mEndereco.setUf(array_spinner[(int) mUf.getSelectedItemId()]);
                //Chama o método para cadastrar o usuário
                cadastrarEndereco();
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


    //Método que recupera os dados básicos do usuário, adicionando o endereço para salvar no banco
    private void cadastrarEndereco() {
        try {
            VerificadorDeObjetos.vDadosObrEndereco(mEndereco);
            Intent i = getIntent();
            Usuario usuario = (Usuario) i.getSerializableExtra("Usuario");
            usuario.setEndereco(mEndereco);
            usuario.salvar();
            mToast = mToast.makeText(CadastroEnderecoActivity.this, R.string.sucesso_cadastro_Toast, Toast.LENGTH_LONG);
            mToast.show();
            String identificadorUsuario = Base64Custom.codificarBase64(usuario.getEmail());
            usuario.setId(identificadorUsuario);
            salvarPreferencias("id", identificadorUsuario);
            abrirLoginUsuario();

        } catch (CampoObrAusenteException e) {
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

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        mUf.getItemAtPosition(position);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        mToast = mToast.makeText(CadastroEnderecoActivity.this, R.string.erro_cadastro_endereco_campos_obrigatorios_Toast, Toast.LENGTH_SHORT);
        mToast.show();
    }

    //Método que salva o id do usuário nas preferências para login automático ao abrir aplicativo
    private void salvarPreferencias(String key, String value) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, value);
        editor.commit();
    }
}
