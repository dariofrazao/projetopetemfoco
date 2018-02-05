package projetaobcc20172.com.projetopetemfoco.contratarservicotest;

import android.support.test.InstrumentationRegistry;
import android.support.test.rule.ActivityTestRule;
import android.support.test.uiautomator.UiDevice;
import android.support.test.uiautomator.UiObject;
import android.support.test.uiautomator.UiObjectNotFoundException;
import android.support.test.uiautomator.UiSelector;
import android.widget.Button;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import projetaobcc20172.com.projetopetemfoco.R;
import projetaobcc20172.com.projetopetemfoco.TestTools;
import projetaobcc20172.com.projetopetemfoco.activity.LoginActivity;
import projetaobcc20172.com.projetopetemfoco.logintests.LoginActivityTest;

/**
 * Created by dario on 04/02/2018.
 */

public class ContratarServicoActivityTest {

    private UiDevice mDevice;

    @Rule
    public ActivityTestRule<LoginActivity> loginActivityRule = new ActivityTestRule<>(LoginActivity.class);

    @Before
    public void setUp() throws Exception {

        mDevice = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation());

        try{

            TestTools.clicarItemNavigationMenu(R.id.drawer_layout, R.id.nav_busca, R.id.nav_sair);

        }catch (Exception e){
            e.getMessage();
        }

        LoginActivityTest log = new LoginActivityTest();
        log.testeLoginComSucesso();
        Thread.sleep(4000);
        TestTools.clicarItemNavigationMenu(R.id.drawer_layout, R.id.nav_busca, R.id.nav_servicos);
        Thread.sleep(4000);
        TestToolsContratarServico.selecionarServico("Banho");
        Thread.sleep(4000);
        TestTools.clicarEmITemListView(R.id.lvEstaServicoBusca, 0);
        Thread.sleep(4000);
        TestTools.clicarBotao(R.id.btnAgendaEstabelecimento);
        Thread.sleep(4000);
        TestTools.clicarBotao(R.id.btnAgendar);
    }

    @Test
    public void solicitarContratacao() throws UiObjectNotFoundException, InterruptedException {
        Thread.sleep(4000);
        UiObject buttonInput = mDevice.findObject(new UiSelector().instance(0).className(Button.class));
        buttonInput.click();
        mDevice.pressBack();
        TestTools.checarToast(R.string.solicitacao_enviada);
    }

    //@Test
    public void cancelarContratacao() throws UiObjectNotFoundException {
        mDevice.pressBack();
        UiObject buttonInput = mDevice.findObject(new UiSelector().instance(0).className(Button.class));
        buttonInput.click();
    }

}
