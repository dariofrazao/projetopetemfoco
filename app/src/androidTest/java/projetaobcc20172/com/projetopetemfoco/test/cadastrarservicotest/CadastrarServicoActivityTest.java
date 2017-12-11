package projetaobcc20172.com.projetopetemfoco.test.cadastrarservicotest;

import android.support.test.espresso.Espresso;
import android.support.test.rule.ActivityTestRule;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import projetaobcc20172.com.projetopetemfoco.R;
import projetaobcc20172.com.projetopetemfoco.activity.LoginActivity;
import projetaobcc20172.com.projetopetemfoco.test.TestTools;
import projetaobcc20172.com.projetopetemfoco.test.logintests.LoginActivityTest;

/**
 * Created by raul on 10/12/17.
 * Esse teste eh um pouco diferente dos outro,pois, neste ele primeiro realiza um login para depois
 * realizar o teste de cadastrar servico. Foi feito assim para simular uma interação mais próximo a ação
 * do usuário além de evitar erros causados por executar activity sem um usuário associado
 */

public class CadastrarServicoActivityTest {
    private String mNomeServico = "Banho";
    private String mDescricao = "Deixa seu pet cheiroso";
    private String mValor = "60,00";
/*
    @Rule
    public ActivityTestRule<CadastroServicoActivity> cadServicoActivityRule = new ActivityTestRule<>(CadastroServicoActivity.class);
*/
    @Rule
    public ActivityTestRule <LoginActivity> loginActivityRule = new ActivityTestRule<>(LoginActivity.class);

    @Before
    public void setUp() throws Exception {
        try{
            TestTools.clicarBotao(R.id.botao_sair);
        }catch (Exception e){
            e.getMessage();
        }

        LoginActivityTest log = new LoginActivityTest();
        log.testeUsuarioCadastrado();

    }
    @Test
    public void testeCadServico(){
        TestTools.clicarBotao(R.id.btnCadastroServico);
        TestToolsCadServico.preencherEClicar(this.mNomeServico,this.mValor,this.mDescricao);
        TestTools.checarToast(R.string.sucesso_cadastro);
    }

    @Test
    public void testeCamposEmBranco(){
        TestTools.clicarBotao(R.id.btnCadastroServico);
        Espresso.closeSoftKeyboard();//Fecha o teclado.Por alguma razão nesta tela ele sempre começa aberto
        TestTools.clicarBotao(R.id.btnSalvarServico);
        TestTools.checarToast(R.string.preencha_campo_nome);
    }
    @Test
    public void testeCampoObgAusenteNome(){
        TestTools.clicarBotao(R.id.btnCadastroServico);
        TestToolsCadServico.preencherEClicar("",this.mValor,this.mDescricao);
        TestTools.checarToast(R.string.preencha_campo_nome);
    }

    @Test
    public void testeCamposObgAusenteValor(){
        TestTools.clicarBotao(R.id.btnCadastroServico);
        TestToolsCadServico.preencherEClicar(this.mNomeServico,"",this.mDescricao);
        TestTools.checarToast(R.string.preencha_campo_valor);
    }

    @Test
    public void testeCamposObgAusenteDescricao(){
        TestTools.clicarBotao(R.id.btnCadastroServico);
        TestToolsCadServico.preencherEClicar(this.mNomeServico,this.mValor,"");
        TestTools.checarToast(R.string.preencha_campo_descricao);
    }

    @After
    public void tearDown() throws Exception {
        Thread.sleep(4000);
    }

}
