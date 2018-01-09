package projetaobcc20172.com.projetopetemfoco.enderecotests;

import android.support.test.InstrumentationRegistry;
import android.support.test.rule.ActivityTestRule;
import android.support.test.uiautomator.UiDevice;
import android.support.test.uiautomator.UiObjectNotFoundException;

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
        CadastroEnderecoActivityTest cadEnd = new CadastroEnderecoActivityTest();
        log.testeLoginComSucesso();
        Thread.sleep(2000);
        TestToolsEndereco.clicarMenuEndereco();
        TestToolsEndereco.clicarBtnCadastrarEnd();
        cadEnd.testeCadastrarEndereco();
        Thread.sleep(2000);
        TestToolsEndereco.clicarIconeExcluir();
    }

    //Teste que simula a remoção de um endereço cancelando a ação
    @Test
    public void testeRemoverEnderecoCancelando() throws UiObjectNotFoundException, InterruptedException {
        TestTools.clicarNaoDialog();
    }

    //Teste que simula a remoção de um endereço confirmando a ação
    @Test
    public void testeRemoverEnderecoConfirmando() throws UiObjectNotFoundException, InterruptedException {
        TestTools.clicarSimDialog();
        Thread.sleep(1000);
        TestTools.checarToast(R.string.sucesso_remocao_endereco);
    }
}
