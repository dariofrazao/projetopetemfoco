package projetaobcc20172.com.projetopetemfoco.enderecotests;

import android.support.test.rule.ActivityTestRule;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import projetaobcc20172.com.projetopetemfoco.R;
import projetaobcc20172.com.projetopetemfoco.activity.LoginActivity;
import projetaobcc20172.com.projetopetemfoco.TestTools;
import projetaobcc20172.com.projetopetemfoco.logintests.LoginActivityTest;

/**
 * Created by raul on 09/12/17.
 */

public class CadastroEnderecoActivityTest {

    private static String sLogradouro = "setor norte";
    private static String sNumero = "42";
    private static String sComplemento = "prox. palacio";
    private static String sBairro = "gunga";
    private static String sCidade = "Naboo";
    private static String sUf = "PE";
    private static String sCep = "55290-000";
    private static int sBotaoCadEnd = R.id.botao_finalizar_cadastro_endereco;

    @Rule
    public ActivityTestRule <LoginActivity> loginActivityRule = new ActivityTestRule<>(LoginActivity.class);

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
        TestTools.clicarBotao(R.id.btnCadastrarEndereco);
    }


    @Test
    public void testeEnderecoCamposEmBranco(){
        TestTools.clicarBotaoComScroll(sBotaoCadEnd);
        TestTools.checarToast(R.string.erro_cadastro_endereco_campos_obrigatorios_Toast);
    }

    @Test
    public void testeEnderecoCampoObgLogradouro() throws InterruptedException {
        TestToolsCadEndereco.preencherCadastro("", sNumero, sComplemento, sBairro,
                sCidade, sUf, sCep);
        Thread.sleep(2000);
        TestTools.apagarCampo(R.id.etCadastroLogradouroEndereco);
        Thread.sleep(2000);
        TestTools.clicarBotaoComScroll(sBotaoCadEnd);
        TestTools.checarToast(R.string.erro_cadastro_endereco_campos_obrigatorios_Toast);
    }

    @Test
    public void testeEnderecoCampoObgBairro() throws InterruptedException {
        TestToolsCadEndereco.preencherCadastro(sLogradouro, sNumero, sComplemento,"",
                sCidade, sUf, sCep);
        Thread.sleep(2000);
        TestTools.apagarCampo(R.id.etCadastroBairroEndereco);
        Thread.sleep(2000);
        TestTools.clicarBotaoComScroll(sBotaoCadEnd);
        TestTools.checarToast(R.string.erro_cadastro_endereco_campos_obrigatorios_Toast);
    }

    @Test
    public void testeEnderecoCampoObgCidade() throws InterruptedException {
        TestToolsCadEndereco.preencherCadastro(sLogradouro, sNumero, sComplemento, sBairro,
                "", sUf, sCep);
        Thread.sleep(2000);
        TestTools.apagarCampo(R.id.etCadastroLocalidadeEndereco);
        Thread.sleep(2000);
        TestTools.clicarBotaoComScroll(sBotaoCadEnd);
        TestTools.checarToast(R.string.erro_cadastro_endereco_campos_obrigatorios_Toast);
    }

    @Test
    public void testeEnderecoCampoObgCep() throws InterruptedException {
        TestToolsCadEndereco.preencherCadastro(sLogradouro, sNumero, sComplemento, sBairro,
                sCidade, sUf,"");
        Thread.sleep(2000);
        TestTools.clicarBotao(sBotaoCadEnd);
        TestTools.checarToast(R.string.erro_cadastro_endereco_campos_obrigatorios_Toast);
    }

    @Test
    public void testeCadastrarEndereco() throws InterruptedException {
        TestToolsCadEndereco.preencherCadastro(sLogradouro, sNumero, sComplemento, sBairro,
                sCidade, sUf,sCep);
        Thread.sleep(2000);
        TestTools.clicarBotao(sBotaoCadEnd);
        TestTools.checarToast(R.string.sucesso_cadastro_endereco);
    }
}
