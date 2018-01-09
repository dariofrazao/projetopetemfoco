package projetaobcc20172.com.projetopetemfoco.logintests;

import android.os.SystemClock;
import android.support.test.InstrumentationRegistry;

import android.support.test.rule.ActivityTestRule;
import android.support.test.uiautomator.UiDevice;
import android.support.test.uiautomator.UiObject;
import android.support.test.uiautomator.UiObjectNotFoundException;
import android.support.test.uiautomator.UiSelector;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import projetaobcc20172.com.projetopetemfoco.R;
import projetaobcc20172.com.projetopetemfoco.TestTools;
import projetaobcc20172.com.projetopetemfoco.activity.LoginActivity;

/**
 * Created by raul on 10/11/17.
 * Classe que executa os testes automatizados para login
 */


public class LoginActivityTest {

    private UiDevice mDevice;

    @Rule
    public ActivityTestRule <LoginActivity> loginActivityRule = new ActivityTestRule<>(LoginActivity.class);

    @Before
    public void setUp() throws Exception {
        mDevice = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation());
        //Desloga caso já esteja logado.
        //Evita erros nos testes
        try{
            TestTools.clicarBotao(R.id.btnSair);
        }catch (Exception e){
            e.getMessage();
        }
    }

    //Teste que simula uma tentativa de login pelo Google confirmando autorização
  //  @Test
    public void testeLoginComSucessoGoogle() throws UiObjectNotFoundException, InterruptedException {
        TestTools.clicarBotao(R.id.btnLoginGoogle);
        Thread.sleep(2000);
        UiObject buttonInput = mDevice.findObject(new UiSelector().instance(0).className(ListView.class));
        if(buttonInput.exists()) buttonInput.click();
        Thread.sleep(2000);
        UiObject buttonInput2 = mDevice.findObject(new UiSelector().text("NÃO"));
        if(buttonInput2.exists()) buttonInput2.click();
        Thread.sleep(1000);
        TestTools.checarToast(R.string.sucesso_login_Toast);
    }

    //Teste que simula uma tentativa de login pelo Google cancelando autorização
    //@Test
    public void testeLoginCanceladoGoogle() throws UiObjectNotFoundException {
        TestTools.clicarBotao(R.id.btnLoginGoogle);
        mDevice.pressBack();
        TestTools.checarToast(R.string.erro_login_invalido_Toast);
    }

    //Teste que simula uma tentativa de login pelo Facebook confirmando autorização
  //  @Test
    public void testeLoginComSucessoFacebook() throws UiObjectNotFoundException, InterruptedException {
        TestTools.clicarBotao(R.id.btnLoginFacebook);
        SystemClock.sleep(2000);
        UiObject buttonInput = mDevice.findObject(new UiSelector().instance(0).className(Button.class));
        buttonInput.click();
        SystemClock.sleep(2000);
        UiObject buttonInput2 = mDevice.findObject(new UiSelector().text("NÃO"));
        if(buttonInput2.exists()) buttonInput2.click();
        SystemClock.sleep(1000);
        TestTools.checarToast(R.string.sucesso_login_Toast);
    }

    //Teste que simula uma tentativa de login pelo Facebook cancelando autorização
   // @Test
    public void testeLoginCanceladoFacebook() throws UiObjectNotFoundException {
        TestTools.clicarBotao(R.id.btnLoginFacebook);
        UiObject buttonInput = mDevice.findObject(new UiSelector().instance(1).className(Button.class));
        buttonInput.click();
        TestTools.checarToast(R.string.login_cancelado);
    }

    //Teste que simula uma tentativa de login pelo Facebook preenchendo os dados e confirmando autorização
    //@Test
    public void testeLoginPreencherDadosComSucessoFacebook() throws UiObjectNotFoundException {
        TestTools.clicarBotao(R.id.btnLoginFacebook);
        UiObject input = mDevice.findObject(new UiSelector().instance(0).className(EditText.class));
        input.setText("emailusuariofacebook@gmail.com");
        UiObject input2 = mDevice.findObject(new UiSelector().instance(1).className(EditText.class));
        input2.setText("senhausuariofacebook");
        UiObject buttonInput = mDevice.findObject(new UiSelector().instance(0).className(Button.class));
        buttonInput.click();
        UiObject buttonInput2 = mDevice.findObject(new UiSelector().instance(0).className(Button.class));
        buttonInput2.click();
        TestTools.checarToast(R.string.sucesso_login_Toast);
    }

    //Teste que simula uma tentativa de login pelo Facebook preenchendo os dados e cancelando autorização
    //@Test
    public void testeLoginPreencherDadosECancelarFacebook() throws UiObjectNotFoundException {
        TestTools.clicarBotao(R.id.btnLoginFacebook);
        UiObject input = mDevice.findObject(new UiSelector().instance(0).className(EditText.class));
        input.setText("emailusuariofacebook@gmail.com");
        UiObject input2 = mDevice.findObject(new UiSelector().instance(1).className(EditText.class));
        input2.setText("senhausuariofacebook");
        UiObject buttonInput = mDevice.findObject(new UiSelector().instance(0).className(Button.class));
        buttonInput.click();
        UiObject buttonInput2 = mDevice.findObject(new UiSelector().instance(1).className(Button.class));
        buttonInput2.click();
        TestTools.checarToast(R.string.login_cancelado);
    }

    //Teste que simula uma tentativa de login pelo Google confirmando autorização
    //@Test
    public void testeLoginComSucesso() {
        TestTools.clicarBotao(R.id.btnLoginGoogle);
        TestTools.checarToast(R.string.sucesso_login_Toast);
    }


    @After
    public void tearDown() throws Exception {
        Thread.sleep(3000);
    }


}