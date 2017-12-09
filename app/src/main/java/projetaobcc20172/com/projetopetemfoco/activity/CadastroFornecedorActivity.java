package projetaobcc20172.com.projetopetemfoco.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.VisibleForTesting;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
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

import java.util.ArrayList;

import projetaobcc20172.com.projetopetemfoco.R;
import projetaobcc20172.com.projetopetemfoco.config.ConfiguracaoFirebase;
import projetaobcc20172.com.projetopetemfoco.excecoes.CampoObrAusenteException;
import projetaobcc20172.com.projetopetemfoco.excecoes.SenhasDiferentesException;
import projetaobcc20172.com.projetopetemfoco.helper.Base64Custom;
import projetaobcc20172.com.projetopetemfoco.helper.Preferencias;
import projetaobcc20172.com.projetopetemfoco.model.Fornecedor;
import projetaobcc20172.com.projetopetemfoco.model.Usuario;

/**
 * Created by renat on 02/12/2017.
 */

public class CadastroFornecedorActivity extends AppCompatActivity {
    private EditText nome, email, senha, senha2, telefone, cpf_cnpj;
    private String item_selecionado;
    private Spinner meus_horarios;
    private Button botaoCadastrar;
    private Fornecedor fornecedor;
    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)//permite que essa variavel seja vista pela classe de teste
    private Toast mToast;
    private FirebaseAuth autenticacao;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_fornecedor);

        Toolbar toolbar;
        toolbar = findViewById(R.id.tb_cadastro_fornecedor);
        nome = findViewById(R.id.editText_nome_fornecedor);
        email = findViewById(R.id.editText_email_fornecedor);
        telefone = findViewById(R.id.Telefone_fornecedor);
        cpf_cnpj = findViewById(R.id.CPF_CNPJ_fornecedor);
        meus_horarios = (Spinner) findViewById(R.id.Spinner_horarios_funcionamento);
        senha = findViewById(R.id.editText_senha_fornecedor);
        senha2 = findViewById(R.id.editText_senha2_fornecedor);
        botaoCadastrar = findViewById(R.id.botao_cadastrar_fornecedor);

        ArrayAdapter adapter = ArrayAdapter.createFromResource(this, R.array.horarios, android.R.layout.simple_spinner_item);
        meus_horarios.setAdapter(adapter);
        final AdapterView.OnItemSelectedListener escolha = new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                item_selecionado = meus_horarios.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        };
        meus_horarios.setOnItemSelectedListener(escolha);

        botaoCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fornecedor = new Fornecedor();
                fornecedor.setNome( nome.getText().toString() );
                fornecedor.setEmail(email.getText().toString());
                fornecedor.setTelefone(telefone.getText().toString());
                fornecedor.setCpf_cnpj(cpf_cnpj.getText().toString());
                fornecedor.setHorarios(item_selecionado);
                fornecedor.setSenha(senha.getText().toString());
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
    //Verifica se a senha 1 é igual a senha 2
    private void verificarSenha() throws SenhasDiferentesException {
        if(!senha.getText().toString().equals(senha2.getText().toString())){
            throw new SenhasDiferentesException();
        }
    }

    private void verificarCamposObrigatorios() throws CampoObrAusenteException {
        if(     nome.getText().toString().isEmpty()
                || senha.getText().toString().isEmpty()
                || senha2.getText().toString().isEmpty()
                || telefone.getText().toString().isEmpty()
                || cpf_cnpj.getText().toString().isEmpty()
                || item_selecionado.toString().isEmpty()){
            throw  new CampoObrAusenteException();
        }
    }
    private void cadastrarFornecedor() {
        try {
            this.verificarCamposObrigatorios();
            this.verificarSenha();
            autenticacao = ConfiguracaoFirebase.getFirebaseAutenticacao();
            autenticacao.createUserWithEmailAndPassword(
                    fornecedor.getEmail(),
                    fornecedor.getSenha()
            ).addOnCompleteListener(CadastroFornecedorActivity.this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {

                    if (task.isSuccessful()) {

                        String identificadorFornecedor = Base64Custom.codificarBase64(fornecedor.getEmail());
                        fornecedor.setId(identificadorFornecedor);
                        fornecedor.salvar();

                        Preferencias preferencias = new Preferencias(CadastroFornecedorActivity.this);
                        preferencias.salvarDados(identificadorFornecedor, fornecedor.getNome());

                        mToast = mToast.makeText(CadastroFornecedorActivity.this, R.string.sucesso_cadastro_Toast, Toast.LENGTH_LONG);
                        mToast.show();

                        abrirLoginFornecedor();

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

                        mToast = mToast.makeText(CadastroFornecedorActivity.this, erro, Toast.LENGTH_SHORT);
                        mToast.show();
                    }

                }
            });
        } catch (SenhasDiferentesException e) {
            mToast = mToast.makeText(CadastroFornecedorActivity.this, R.string.erro_cadastro_fornecedor_senhas_diferentes_Toast, Toast.LENGTH_SHORT);
            mToast.show();
        } catch (CampoObrAusenteException e) {
            mToast = mToast.makeText(CadastroFornecedorActivity.this, R.string.erro_cadastro_fornecedor_campos_obrigatorios_Toast, Toast.LENGTH_SHORT);
            mToast.show();
        } catch (Exception e){
            mToast = mToast.makeText(CadastroFornecedorActivity.this, R.string.erro_cadastro_fornecedor_campos_obrigatorios_Toast, Toast.LENGTH_SHORT);
            mToast.show();
        }
    }

    public void abrirLoginFornecedor(){
        Intent intent = new Intent(CadastroFornecedorActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }
}
