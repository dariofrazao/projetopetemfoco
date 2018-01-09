package projetaobcc20172.com.projetopetemfoco.enderecotests;

import android.support.test.rule.ActivityTestRule;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import projetaobcc20172.com.projetopetemfoco.R;
import projetaobcc20172.com.projetopetemfoco.TestTools;
import projetaobcc20172.com.projetopetemfoco.activity.LoginActivity;
import projetaobcc20172.com.projetopetemfoco.logintests.LoginActivityTest;

/**
 * Created by dario on 07/12/17.
 * Essa classe é responsavel por executar os testes automatizados
 * Para a edição de um endereço
 */

public class EditarEnderecoActivityTest {

    private static String sLogradouro = "setor norte";
    private static String sNumero = "42";
    private static String sComplemento = "prox. palacio";
    private static String sBairro = "gunga";
    private static String sCidade = "Naboo";
    private static String sUf = "SP";
    private static String sCep = "55290-000";
    private static int sBotaoEdicEnd = R.id.botao_editar_endereco;

    @Rule
    public ActivityTestRule<LoginActivity> loginActivityRule = new ActivityTestRule<>(LoginActivity.class);

    @Before
    public void setUp() throws Exception {
        try{
            TestTools.clicarBotao(R.id.btnSair);
        }catch (Exception e){
            e.getMessage();
        }

        LoginActivityTest log = new LoginActivityTest();
        log.testeLoginComSucesso();
        Thread.sleep(4000);
        TestTools.clicarItemMenu("Endereço");
        Thread.sleep(4000);
        TestToolsEndereco.clicarIconeEditar();
    }

    @Test
    public void testeEnderecoCamposEmBranco() throws InterruptedException {
        TestToolsEndereco.apagarCampos();
        Thread.sleep(2000);
        TestTools.clicarBotaoComScroll(sBotaoEdicEnd);
        TestTools.checarToast(R.string.erro_atualizacao_campos_obrigatorios_endereco);
    }

    @Test
    public void testeEnderecoCampoObgLogradouro() throws InterruptedException {
        TestToolsEndereco.preencherEdicao("", sNumero, sComplemento, sBairro,
                sCidade, sUf, sCep);
        Thread.sleep(2000);
        TestTools.apagarCampo(R.id.etEditarLogradouroEndereco);
        Thread.sleep(2000);
        TestTools.clicarBotaoComScroll(sBotaoEdicEnd);
        TestTools.checarToast(R.string.erro_atualizacao_campos_obrigatorios_endereco);
    }

    @Test
    public void testeEnderecoCampoObgBairro() throws InterruptedException {
        TestToolsEndereco.preencherEdicao(sLogradouro, sNumero, sComplemento,"",
                sCidade, sUf, sCep);
        Thread.sleep(2000);
        TestTools.apagarCampo(R.id.etEditarBairroEndereco);
        Thread.sleep(2000);
        TestTools.clicarBotaoComScroll(sBotaoEdicEnd);
        TestTools.checarToast(R.string.erro_atualizacao_campos_obrigatorios_endereco);
    }

    @Test
    public void testeEnderecoCampoObgCidade() throws InterruptedException {
        TestToolsEndereco.preencherEdicao(sLogradouro, sNumero, sComplemento, sBairro,
                "", sUf, sCep);
        Thread.sleep(2000);
        TestTools.apagarCampo(R.id.etEditarLocalidadeEndereco);
        Thread.sleep(2000);
        TestTools.clicarBotaoComScroll(sBotaoEdicEnd);
        TestTools.checarToast(R.string.erro_atualizacao_campos_obrigatorios_endereco);
    }

    @Test
    public void testeEnderecoCampoObgCep() throws InterruptedException {
        TestToolsEndereco.preencherEdicao(sLogradouro, sNumero, sComplemento, sBairro,
                sCidade, sUf,"");
        Thread.sleep(2000);
        TestTools.editarTextoComScroll(R.id.etEditarCepEndereco);
        Thread.sleep(2000);
        TestTools.apagarCampo(R.id.etEditarCepEndereco);
        Thread.sleep(2000);
        TestTools.clicarBotaoComScroll(sBotaoEdicEnd);
        TestTools.checarToast(R.string.erro_atualizacao_campos_obrigatorios_endereco);
    }

    @Test
    public void testeEditarEndereco() throws InterruptedException {
        TestToolsEndereco.preencherEdicao(sLogradouro, sNumero, sComplemento, sBairro,
                sCidade, sUf,sCep);
        Thread.sleep(2000);
        TestTools.clicarBotao(sBotaoEdicEnd);
        TestTools.checarToast(R.string.sucesso_atualizacao_endereco);
    }
}
