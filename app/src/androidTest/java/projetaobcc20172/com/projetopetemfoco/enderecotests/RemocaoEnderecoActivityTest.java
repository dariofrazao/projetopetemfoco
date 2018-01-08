package projetaobcc20172.com.projetopetemfoco.enderecotests;

import android.support.test.InstrumentationRegistry;
import android.support.test.rule.ActivityTestRule;
import android.support.test.uiautomator.UiDevice;
import android.support.test.uiautomator.UiObject;
import android.support.test.uiautomator.UiObjectNotFoundException;
import android.support.test.uiautomator.UiSelector;

import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import projetaobcc20172.com.projetopetemfoco.R;
import projetaobcc20172.com.projetopetemfoco.TestTools;
import projetaobcc20172.com.projetopetemfoco.activity.LoginActivity;
import projetaobcc20172.com.projetopetemfoco.logintests.LoginActivityTest;

/**
 * Created by dario on 07/12/17.
 * Essa classe é responsavel por executar os testes automatizados
 * Para a remoção de um endereço
 */

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class RemocaoEnderecoActivityTest {

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
        TestTools.clicarItemMenu("Endereço");
        Thread.sleep(4000);
        TestTools.clicarBotao(R.id.ibtnRemover);
    }

    //Teste que simula a remoção de um endereço cancelando a ação
    @Test
    public void testeRemoverEnderecoCancelando() throws UiObjectNotFoundException, InterruptedException {
        UiObject buttonInput2 = mDevice.findObject(new UiSelector().text("NÃO"));
        buttonInput2.click();
    }

    //Teste que simula a remoção de um endereço confirmando a ação
    @Test
    public void testeRemoverEnderecoConfirmando() throws UiObjectNotFoundException, InterruptedException {
        UiObject buttonInput2 = mDevice.findObject(new UiSelector().text("SIM"));
        buttonInput2.click();
        Thread.sleep(1000);
        TestTools.checarToast(R.string.sucesso_remocao_endereco);
    }
}
