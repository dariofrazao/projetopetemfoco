package projetaobcc20172.com.projetopetemfoco.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.VisibleForTesting;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseNetworkException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;

import projetaobcc20172.com.projetopetemfoco.R;
import projetaobcc20172.com.projetopetemfoco.config.ConfiguracaoFirebase;
import projetaobcc20172.com.projetopetemfoco.excecoes.CampoObrAusenteException;
import projetaobcc20172.com.projetopetemfoco.excecoes.SenhasDiferentesException;
import projetaobcc20172.com.projetopetemfoco.excecoes.ValidacaoException;
import projetaobcc20172.com.projetopetemfoco.helper.Base64Custom;
import projetaobcc20172.com.projetopetemfoco.utils.MaskUtil;
import projetaobcc20172.com.projetopetemfoco.model.Fornecedor;
import projetaobcc20172.com.projetopetemfoco.model.Usuario;
import projetaobcc20172.com.projetopetemfoco.utils.Utils;
import projetaobcc20172.com.projetopetemfoco.utils.VerificadorDeObjetos;

/**
 * Created by renat on 02/12/2017.
 */

/**
 * Activity de cadastro de fornecedor
 */
public class CadastroFornecedorActivity extends AppCompatActivity {

    private EditText nome, email, senha, senha2, telefone, cpf_cnpj;
    private Spinner mSpinnerHorarios;
    private Button botaoCadastrar;
    private Fornecedor fornecedor;
    private Usuario usuario;
    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)//permite que essa variavel seja vista pela classe de teste
    private Toast mToast;
    private FirebaseAuth mAutenticacao;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_fornecedor);

        Toolbar toolbar;
        toolbar = findViewById(R.id.tb_cadastro_fornecedor);
        nome = findViewById(R.id.etCadastroNomeFornecedor);
        email = findViewById(R.id.etCadastroEmailFornecedor);
        telefone = findViewById(R.id.etCadastroTelefoneFornecedor);
        cpf_cnpj = findViewById(R.id.etCadastroCpfCnpjFornecedor);
        cpf_cnpj.addTextChangedListener(MaskUtil.insert(cpf_cnpj, MaskUtil.MaskType.CEP));
        senha = findViewById(R.id.etCadastroSenhaFornecedor);
        senha2 = findViewById(R.id.etCadastroSenha2Fornecedor);
        botaoCadastrar = findViewById(R.id.botao_cadastrar_fornecedor);

        //Preparar o adaptar do Spinner para exibir os horários de atendimento do fornecedor
        mSpinnerHorarios = findViewById(R.id.horariosSpinner);
        ArrayAdapter<String> adapter_state = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_dropdown_item, getResources().getStringArray(R.array.horariosFornecedor));
        mSpinnerHorarios.setAdapter(adapter_state);

        botaoCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fornecedor = new Fornecedor();
                usuario = new Usuario();
                fornecedor.setNome( nome.getText().toString() );
                fornecedor.setEmail(email.getText().toString());
                fornecedor.setTelefone(telefone.getText().toString());
                fornecedor.setCpf_cnpj(cpf_cnpj.getText().toString());
                fornecedor.setHorarios(mSpinnerHorarios.getSelectedItem().toString());
                fornecedor.setSenha(senha.getText().toString());
                fornecedor.setSenha2(senha2.getText().toString());
                cadastrarFornecedor();
            }
        });

        // Configura toolbar
        toolbar.setTitle("Cadastro de Fornecedor");
        toolbar.setTitleTextColor(Color.WHITE);
        toolbar.setNavigationIcon(R.drawable.ic_action_arrow_left_white);
        setSupportActionBar(toolbar);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    //Método para cadastrar o fornecedor no FirebaseAuthentication
    private void cadastrarFornecedor() {
        try {
            VerificadorDeObjetos.vDadosFornecedor(fornecedor, this);
            mAutenticacao = ConfiguracaoFirebase.getFirebaseAutenticacao();
            mAutenticacao.createUserWithEmailAndPassword(
                    fornecedor.getEmail(),
                    fornecedor.getSenha()
            ).addOnCompleteListener(CadastroFornecedorActivity.this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {

                    if (task.isSuccessful()) {
                        String identificadorFornecedor = Base64Custom.codificarBase64(fornecedor.getEmail());
                        fornecedor.setId(identificadorFornecedor);
                        Toast mToast = Toast.makeText(CadastroFornecedorActivity.this, R.string.sucesso_cadastro_proxima_etapa_Toast, Toast.LENGTH_LONG);
                        mToast.show();
                        usuario.setValor("1");
                        //Aqui será chamado a continuação do cadastro do fornecedor, levando-o ao cadastro do endereço
                        abrirCadastroEndereco(usuario, fornecedor);
                    } else {

                        String erro = "";
                        try {
                            throw task.getException();
                        } catch (FirebaseAuthWeakPasswordException e) {
                            erro = getResources().getString(R.string.erro_cadastro_fornecedor_senha_invalida_Toast);
                        } catch (FirebaseAuthInvalidCredentialsException e) {
                            erro = getResources().getString(R.string.erro_cadastro_fornecedor_email_invalido_Toast);
                        } catch (FirebaseAuthUserCollisionException e) {
                            erro = getResources().getString(R.string.erro_cadastro_fornecedor_email_usado_Toast);
                        } catch (FirebaseNetworkException e) {
                            erro = getResources().getString(R.string.erro_cadastro_fornecedor_sem_conexao);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                        Toast mToast = Toast.makeText(CadastroFornecedorActivity.this, erro, Toast.LENGTH_SHORT);
                        mToast.show();
                    }

                }
            });
        } catch (ValidacaoException e) {
            e.printStackTrace();
            Utils.mostrarMensagemCurta(this, e.getMessage());
        }
    }

    //Método que chama a activity para cadastrar o endereço, passando os dados básicos aqui cadastrados
    public void abrirCadastroEndereco(Usuario usuario, Fornecedor fornecedor){
        mAutenticacao.signOut();
        Intent intent = new Intent(CadastroFornecedorActivity.this, CadastroEnderecoActivity.class);
        intent.putExtra("Usuario", usuario);
        intent.putExtra("Fornecedor", fornecedor);
        startActivity(intent);
        finish();
    }

}
