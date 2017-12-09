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
import projetaobcc20172.com.projetopetemfoco.excecoes.CampoEnderecoObrAusenteException;
import projetaobcc20172.com.projetopetemfoco.helper.Base64Custom;
import projetaobcc20172.com.projetopetemfoco.model.Endereco;
import projetaobcc20172.com.projetopetemfoco.model.Usuario;
import projetaobcc20172.com.projetopetemfoco.utils.MaskUtil;

public class CadastroEnderecoActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private EditText logradouro, numero, complemento, bairro, cidade, cep;
    private Spinner uf;
    private Button botaoCadastrarEndereco;
    private Endereco endereco;

    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE) //permite que essa variavel seja vista pela classe de teste
    private Toast mToast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_endereco);

        Toolbar toolbar;
        toolbar = findViewById(R.id.tb_endereco);

        logradouro = findViewById(R.id.editText_endereco_logradouro);
        numero = findViewById(R.id.editText_endereco_numero);
        complemento = findViewById(R.id.editText_endereco_complemento);
        bairro = findViewById(R.id.editText_endereco_bairro);
        cidade = findViewById(R.id.editText_endereco_cidade);
        cep = findViewById(R.id.editText_endereco_cep);
        cep.addTextChangedListener(MaskUtil.insert(cep, MaskUtil.MaskType.CEP));
        uf = findViewById(R.id.spinner_endereco_uf);
        final String[] array_spinner = {"AC", "AL", "AP", "AM", "BA", "CE", "DF",
            "ES", "GO", "MA", "MG", "MS", "MG", "PA", "PB", "PA", "PE", "PI",
            "RJ", "RN", "RS", "RO", "RR", "SC", "SP", "SE", "TO"};

        ArrayAdapter adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, array_spinner);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        uf.setAdapter(adapter);
        uf.setOnItemSelectedListener(this);

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
                endereco.setCidade(cidade.getText().toString());
                endereco.setCep(cep.getText().toString());
                endereco.setUf(array_spinner[(int) uf.getSelectedItemId()]);
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

    //Método para verificar se os campos do endereço estão em branco
    private void verificarCamposObrigatorios() throws CampoEnderecoObrAusenteException {
        if (logradouro.getText().toString().isEmpty()
            ||
            bairro.getText().toString().isEmpty()
            ||
            cidade.getText().toString().isEmpty()
            ||
            cep.getText().toString().isEmpty()
            ) {
            throw new CampoEnderecoObrAusenteException();
        }
    }

    //Método que recupera os dados básicos do usuário, adicionando o endereço para salvar no banco
    private void cadastrarEndereco() {
        try {
            this.verificarCamposObrigatorios();
            Intent i = getIntent();
            Usuario usuario = (Usuario) i.getSerializableExtra("Usuario");
            String identificadorUsuario = Base64Custom.codificarBase64(usuario.getEmail());
            usuario.setId(identificadorUsuario);
            salvarPreferencias("id", identificadorUsuario);
            usuario.setEndereco(endereco);
            usuario.salvar();
            mToast = mToast.makeText(CadastroEnderecoActivity.this, R.string.sucesso_cadastro_Toast, Toast.LENGTH_LONG);
            mToast.show();
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

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        uf.getItemAtPosition(position);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        mToast = mToast.makeText(CadastroEnderecoActivity.this, R.string.erro_cadastro_endereco_campos_obrigatorios_Toast, Toast.LENGTH_SHORT);
        mToast.show();
    }

    //Método que salva o id do usuário nas preferências para login automático ao abrir aplicativo
    private void salvarPreferencias(String key, String value){
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, value);
        editor.commit();
    }
}
