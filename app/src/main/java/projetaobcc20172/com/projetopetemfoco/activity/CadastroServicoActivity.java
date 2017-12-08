package projetaobcc20172.com.projetopetemfoco.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Locale;

import projetaobcc20172.com.projetopetemfoco.R;
import projetaobcc20172.com.projetopetemfoco.database.ServicoDaoImpl;
import projetaobcc20172.com.projetopetemfoco.excecoes.ValidacaoException;
import projetaobcc20172.com.projetopetemfoco.model.Servico;
import projetaobcc20172.com.projetopetemfoco.utils.MascaraDinheiro;
import projetaobcc20172.com.projetopetemfoco.utils.Utils;

/**
 * Activity de cadastro de serviços
 */
public class CadastroServicoActivity extends AppCompatActivity {

    private EditText mEtNome, mEtValor, mEtDescricao;
    private Button mBtnCadastrar;
    private Toolbar mToolbar;
    private DatabaseReference mFornecedorRef;

    private Servico mServico;
    private FirebaseAuth autenticacao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_servico);
        mFornecedorRef = FirebaseDatabase.getInstance().getReference().child("fornecedor");
        inicializarComponentes();
    }

    /**
     * Inicializa todos os componentes da View.
     */
    private void inicializarComponentes() {

        mToolbar = findViewById(R.id.toolbar);
        mToolbar.setNavigationIcon(R.drawable.ic_action_arrow_left_white);
        setSupportActionBar(mToolbar);

        mEtNome =  findViewById(R.id.etCadastroNomeServico);
        mEtValor =  findViewById(R.id.etCadastroValorServico);
        mEtDescricao =  findViewById(R.id.etCadastroDescricaoServico);

        Locale mLocal = new Locale("pt", "BR");

        mEtValor.addTextChangedListener(new MascaraDinheiro(mEtValor, mLocal));

        mBtnCadastrar = findViewById(R.id.btnSalvarServico);
        mBtnCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cadastrarAtualizar();
            }
        });
    }

    private void validarCampos() throws ValidacaoException {
        String acao;
        if(verificarNovoCadastro()) acao = "cadastrar";
        else acao = "atualizar";
        if(mEtNome.getText().toString().isEmpty()){

            String msg = Utils.formatarMensagemErro(acao, getString(R.string.preencha_campo_nome));
            throw new ValidacaoException(msg);
        }
        if(mEtValor.getText().toString().isEmpty()){
            String msg = Utils.formatarMensagemErro(acao, getString(R.string.preencha_campo_valor));
            throw new ValidacaoException(msg);
        }
        if(mEtDescricao.getText().toString().isEmpty()){
            String msg = Utils.formatarMensagemErro(acao, getString(R.string.preencha_campo_descricao));
            throw new ValidacaoException(msg);
        }
    }


    /**
     * Verifica se é um novo cadastro ou uma atualização de cadastro.
     * @return {true} se for um novo cadastro.
     */
    private boolean verificarNovoCadastro(){
        return (mServico == null);
    }

    /**
     * Verifica se algum campo da view foi preenchido.
     * @return {true} se algum campo foi preenchido.
     */
    private boolean verificarCamposPreenchidos(){
        return (!mEtNome.getText().toString().isEmpty() ||
                !mEtValor.getText().toString().isEmpty()||
                !mEtDescricao.getText().toString().isEmpty());
    }

    /**
     * Cadastra ou atualiza os dados.
     */
    private void cadastrarAtualizar(){
        try {
            this.validarCampos();

            Double valor = Double.parseDouble(MascaraDinheiro.removerMascara(mEtValor));

            // Verifica se é um novo Cadastro
            if(verificarNovoCadastro()){
                mServico = new Servico(mEtNome.getText().toString(),
                        valor,
                        mEtDescricao.getText().toString());

                ServicoDaoImpl servicoDao =  new ServicoDaoImpl(this);
                servicoDao.inserir(mServico, "ZEBob3RtYWlsLmNvbQ==");
            }
            // é atualização de dados
            else{

                // TODO: Implemantar o caso de atualização de dados.
            }

        } catch (ValidacaoException e) {
            e.printStackTrace();
            Utils.mostrarMensagemLonga(this, e.getMessage());
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public void onBackPressed(){
        if (verificarCamposPreenchidos()) confirmarSaida();
        else CadastroServicoActivity.super.onBackPressed();
    }

    /**
     * Pede confirmação do usuário, informa que os dados não salvos serão perdidos.
     */
    public void confirmarSaida(){
        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which){
                    case DialogInterface.BUTTON_POSITIVE:
                        // Botão sim foi clicado
                        CadastroServicoActivity.super.onBackPressed();
                        break;

                    case DialogInterface.BUTTON_NEGATIVE:
                        // Botão não foi clicado
                        break;
                }
            }
        };

        Utils.mostrarPerguntaSimNao(this, getString(R.string.atencao),
                    getString(R.string.pergunta_confirma_dados_serao_perdidos), dialogClickListener,
                    dialogClickListener);
    }
}
