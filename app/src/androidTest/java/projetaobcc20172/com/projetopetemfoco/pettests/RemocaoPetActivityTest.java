package projetaobcc20172.com.projetopetemfoco.pettests;

import android.support.test.InstrumentationRegistry;
import android.support.test.rule.ActivityTestRule;
import android.support.test.uiautomator.UiDevice;
import android.support.test.uiautomator.UiObject;
import android.support.test.uiautomator.UiObjectNotFoundException;
import android.support.test.uiautomator.UiSelector;

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
        Thread.sleep(4000);
        TestTools.clicarBotao(R.id.btnMeusPets);
        Thread.sleep(4000);
        TestTools.clicarBotao(R.id.ibtnRemover);
    }

    //Teste que simula a remoção de um pet confirmando a ação
    @Test
    public void testeRemoverPetConfirmando() throws UiObjectNotFoundException, InterruptedException {
        UiObject buttonInput2 = mDevice.findObject(new UiSelector().text("SIM"));
        buttonInput2.click();
        Thread.sleep(1000);
        TestTools.checarToast(R.string.sucesso_remocao_Pet);
    }

    //Teste que simula a remoção de um pet cancelando a ação
    @Test
    public void testeRemoverPetCancelando() throws UiObjectNotFoundException, InterruptedException {
        UiObject buttonInput2 = mDevice.findObject(new UiSelector().text("NÃO"));
        buttonInput2.click();
    }
}
