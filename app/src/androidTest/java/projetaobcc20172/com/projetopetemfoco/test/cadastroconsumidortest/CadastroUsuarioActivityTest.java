package projetaobcc20172.com.projetopetemfoco.test.cadastroconsumidortest;

import android.support.test.espresso.intent.Intents;
import android.support.test.rule.ActivityTestRule;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import projetaobcc20172.com.projetopetemfoco.R;
import projetaobcc20172.com.projetopetemfoco.activity.CadastroEnderecoActivity;
import projetaobcc20172.com.projetopetemfoco.activity.CadastroUsuarioActivity;
import projetaobcc20172.com.projetopetemfoco.test.TestTools;
import projetaobcc20172.com.projetopetemfoco.test.cadastroenderecotest.EnderecoActivityTest;

/**
 * Created by raul on 11/11/17.
 * Essa classe é responsavel por executar os testes automatizados
 * Para o cadastro de um usuário
 */

public class CadastroUsuarioActivityTest {

    //Essas variaveis guardam valores que são preenchidos nos campos
    //Existentes na tela de cadastro
    private  String mNome = "Teste"; //mNome do usuário
    private  String mEmailJaUtilizado = "raulpedrouag@gmail.com";
    private  String mEmailInvalido = "testesdakjdkas--*@ffss";
    private  String mEmail = "luar13.pedro@yahoo.com.br";
    private  String mSenha = "12345e";
    private  String mSenha2 = "12345ew1";

    @Rule
    public ActivityTestRule<CadastroUsuarioActivity> cadUserActivityRule = new ActivityTestRule<>(CadastroUsuarioActivity.class);


    @Before
    public void setUp() throws Exception {
        //Desloga caso já esteja logado.
        //Evita erros nos testes
        try{
            TestTools.clicarBotao(R.id.botao_sair);
        }catch (Exception e){
            e.getMessage();
        }
    }

    private void preencherComInfoCorretas(){
        String email = TestToolsCadUser.gerarEmailTeste(10);
        TestToolsCadUser.preencherEclicar(this.mNome,email,this.mSenha,this.mSenha);
        TestTools.checarToast(R.string.sucesso_cadastro_proxima_etapa_Toast);
        TestTools.verificarMudancaActivity(CadastroEnderecoActivity.class.getName());
    }

    @Test
    public void testeEmailJaCadastrado(){
        TestToolsCadUser.preencherEclicar(this.mNome,this.mEmailJaUtilizado,this.mSenha,this.mSenha);
        TestTools.checarToast(R.string.erro_cadastro_email_usado_Toast);
    }

    @Test
    public void testeCadastroCamposAusentes(){
        TestTools.clicarBotao(R.id.botao_cadastrar_endereco);
        TestTools.checarToast(R.string.erro_cadastro_campos_obrigatorios_Toast);
    }

    @Test
    public void testeCadastroCampoAusenteNome(){
        TestToolsCadUser.preencherEclicar("",this.mEmail,this.mSenha,this.mSenha);
        TestTools.checarToast(R.string.erro_cadastro_campos_obrigatorios_Toast);
    }

    @Test
    public void testeCadastroCampoAusenteSenha1(){
        TestToolsCadUser.preencherEclicar(this.mNome,this.mEmail,"",this.mSenha);
        TestTools.checarToast(R.string.erro_cadastro_campos_obrigatorios_Toast);
    }

    @Test
    public void testeCadastroCampoAusenteSenha2(){
        TestToolsCadUser.preencherEclicar(this.mNome,this.mEmail,this.mSenha,"");
        TestTools.checarToast(R.string.erro_cadastro_campos_obrigatorios_Toast);
    }

    //Teste que simula um cadastro com e-mail Inválido
    @Test
    public void testeCadastroEmailInvalido(){
        TestToolsCadUser.preencherEclicar(this.mNome,this.mEmailInvalido,this.mSenha,this.mSenha);
        TestTools.checarToast(R.string.erro_cadastro_email_invalido_Toast);
    }

    //Teste que simula um cadastro com mSenha e mSenha de confirmação diferentes
    @Test
    public void testeCadastrarSenhasDif(){
        TestToolsCadUser.preencherEclicar(this.mNome,this.mEmail,this.mSenha,this.mSenha2);
        TestTools.checarToast(R.string.erro_cadastro_senhas_diferentes_Toast);

    }

    //Teste que simula um cadastro com mSenha fraca (menos que 6 caracteres)
    @Test
    public void testeCadastrarSenhaFraca(){
        String senha3 = "12345";
        TestToolsCadUser.preencherEclicar(this.mNome,this.mEmail, senha3, senha3);
        TestTools.checarToast(R.string.erro_cadastro_senha_invalida_Toast);
    }

    @Test
    public void testeCadastrar(){
        Intents.init();
        this.preencherComInfoCorretas();
        new EnderecoActivityTest().testeEnderecoCorreto();
    }

    @Test
    //Teste que avalia o cadastro de um consumidor sem
    //a informção de num no endereço
    public void testeCadastrarEndNum(){
        Intents.init();
        this.preencherComInfoCorretas();
        new EnderecoActivityTest().testeEnderecoCorretoSemNumero();
    }

    @Test
    //Teste que avalia o cadastro de um consumidor sem
    //a informção de complemento no endereço
    public void testeCadastrarComplemento(){
        Intents.init();
        this.preencherComInfoCorretas();
        new EnderecoActivityTest().testeEnderecoCorretoSemCompl();
    }

    @After
    public void tearDown() throws Exception {
        Thread.sleep(2000);
    }
}