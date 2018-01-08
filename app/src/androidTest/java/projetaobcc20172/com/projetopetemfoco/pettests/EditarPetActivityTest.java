package projetaobcc20172.com.projetopetemfoco.pettests;

import android.support.test.rule.ActivityTestRule;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import projetaobcc20172.com.projetopetemfoco.R;
import projetaobcc20172.com.projetopetemfoco.activity.LoginActivity;
import projetaobcc20172.com.projetopetemfoco.TestTools;
import projetaobcc20172.com.projetopetemfoco.logintests.LoginActivityTest;

/**
 * Created by dario on 07/12/17.
 * Essa classe é responsavel por executar os testes automatizados
 * Para a edição de um pet
 */

public class EditarPetActivityTest {

    private static String sNomePet = "Raulf";
    private static String sraca = "Vira-lata";

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
        TestTools.clicarBotao(R.id.btnMeusPets);
        Thread.sleep(4000);
        TestTools.clicarBotao(R.id.ibtnEditar);
    }

    //Teste que simula uma edição do pet com o campo nome em branco
    @Test
    public void testeNomeEmBranco() throws InterruptedException {
        TestTools.apagarCampo(R.id.etEditarNomePet);
        Thread.sleep(2000);
        TestTools.clicarBotaoComScroll(R.id.botao_editar_pet);
        TestTools.checarToast(R.string.erro_atualizacao_campos_obrigatorios_Pet);
    }

    //Teste que simula uma edição do pet com os campos em branco
    @Test
    public void testeCamposEmBranco() throws InterruptedException {
        TestTools.apagarCampo(R.id.etEditarNomePet);
        TestTools.apagarCampo(R.id.etEditarRaçaPet);
        Thread.sleep(2000);
        TestTools.clicarBotaoComScroll(R.id.botao_editar_pet);
        TestTools.checarToast(R.string.erro_atualizacao_campos_obrigatorios_Pet);
    }

    //Teste que simula uma edição do pet com todos os campos preenchidos
    @Test
    public void testeEditarPet() throws InterruptedException {
        TestTools.apagarCampo(R.id.etEditarNomePet);
        TestTools.apagarCampo(R.id.etEditarRaçaPet);
        Thread.sleep(2000);
        TestToolsPet.preencherEdicao(sNomePet, sraca);
        Thread.sleep(2000);
        TestTools.clicarBotaoComScroll(R.id.botao_editar_pet);
        TestTools.checarToast(R.string.sucesso_atualizacao_Pet);
    }
}
