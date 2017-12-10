package projetaobcc20172.com.projetopetemfoco.test.cadastrarservicotest;

import android.support.test.espresso.intent.Intents;
import android.support.test.rule.ActivityTestRule;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import projetaobcc20172.com.projetopetemfoco.R;
import projetaobcc20172.com.projetopetemfoco.activity.CadastroServicoActivity;
import projetaobcc20172.com.projetopetemfoco.activity.LoginActivity;
import projetaobcc20172.com.projetopetemfoco.test.TestTools;
import projetaobcc20172.com.projetopetemfoco.test.logintests.LoginActivityTest;

/**
 * Created by raul on 10/12/17.
 */

public class CadastrarServicoActivityTest {
    private String nomeServico = "Banho";
    private String descricao = "Deixa seu pet cheiroso";
    private String valor = "60,00";


    public ActivityTestRule<CadastroServicoActivity> cadServicoActivityRule = new ActivityTestRule<>(CadastroServicoActivity.class);

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
        Intents.init();
        TestTools.clicarBotao(R.id.btnCadastroServico);
        TestToolsCadServico.preencherEClicar(this.nomeServico,this.valor,this.descricao);
        TestTools.clicarBotao(R.id.botao_salvar_servico);
        TestTools.checarToast(R.string.sucesso_cadastro);
    }

    @Test
    public void testeCamposEmBranco(){
        TestTools.clicarBotao(R.id.botao_salvar_servico);
        TestTools.checarToast(cadServicoActivityRule.getActivity().msgErro);
    }
    @Test
    public void testeCampoObgAusenteNome(){
        TestToolsCadServico.preencherEClicar("",this.valor,this.descricao);
        TestTools.clicarBotao(R.id.botao_salvar_servico);
        TestTools.checarToast(cadServicoActivityRule.getActivity().msgErro);
    }

    @Test
    public void testeCamposObgAusenteValor(){
        TestToolsCadServico.preencherEClicar(this.nomeServico,"",this.descricao);
        TestTools.clicarBotao(R.id.botao_salvar_servico);
        TestTools.checarToast(cadServicoActivityRule.getActivity().msgErro);
    }

    @Test
    public void testeCamposObgAusenteDescricao(){
        TestToolsCadServico.preencherEClicar(this.nomeServico,this.valor,"");
        TestTools.clicarBotao(R.id.botao_salvar_servico);
        TestTools.checarToast(cadServicoActivityRule.getActivity().msgErro);
    }

    @After
    public void tearDown() throws Exception {
        Thread.sleep(2000);
    }

}
