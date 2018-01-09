package projetaobcc20172.com.projetopetemfoco.pettests;

import android.support.test.InstrumentationRegistry;
import android.support.test.rule.ActivityTestRule;
import android.support.test.uiautomator.UiDevice;
import android.support.test.uiautomator.UiObjectNotFoundException;

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
 * Para a remoção de um pet
 */

public class RemocaoPetActivityTest {

    private UiDevice mDevice;

    @Rule
    public ActivityTestRule<LoginActivity> loginActivityRule = new ActivityTestRule<>(LoginActivity.class);

    @Before
    public void setUp() throws Exception {
        mDevice = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation());
        try{
            TestTools.clicarBotao(R.id.btnSair);
        }catch (Exception e){
            e.getMessage();
        }

        LoginActivityTest log = new LoginActivityTest();
        log.testeLoginComSucesso();
        Thread.sleep(2000);
        TestToolsPet.clicarMeusPets();
        Thread.sleep(1000);
        TestToolsPet.clicarCadastrarPet();
        new CadastroPetActivityTest().testeCadastrarPet();
        Thread.sleep(1500);
        TestToolsPet.clicariconeExcluir();
    }

    //Teste que simula a remoção de um pet confirmando a ação
    @Test
    public void testeRemoverPetConfirmando() throws UiObjectNotFoundException, InterruptedException {
        TestTools.clicarSimDialog();
        Thread.sleep(1000);
        TestTools.checarToast(R.string.sucesso_remocao_Pet);
    }

    //Teste que simula a remoção de um pet cancelando a ação
    @Test
    public void testeRemoverPetCancelando() throws UiObjectNotFoundException, InterruptedException {
        TestTools.clicarNaoDialog();
    }
}
