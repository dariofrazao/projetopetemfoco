package projetaobcc20172.com.projetopetemfoco.pettests;

import android.support.test.espresso.Espresso;
import android.support.test.rule.ActivityTestRule;

import org.junit.After;
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
 * Para o cadastro de um pet
 */

public class CadastroPetActivityTest{

    private static String sNomePet = "Billie";
    private static String sraca = "Bulldog";

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
        TestToolsPet.clicarMeusPets();
        Thread.sleep(4000);
        TestToolsPet.clicarCadastrarPet();
        Espresso.closeSoftKeyboard();
    }

    //Teste que simula um cadastro do pet com o campo nome em branco
    @Test
    public void testeNomeEmBranco(){
        TestToolsPet.preencherCadastro("",sraca);
        TestTools.clicarBotaoComScroll(R.id.botao_cadastrar_pet);
        TestTools.checarToast(R.string.erro_cadastro_campos_obrigatorios_Pet);
    }

    //Teste que simula um cadastro do pet com os campos em branco
    @Test
    public void testeCamposEmBranco(){
        TestTools.clicarBotao(R.id.botao_cadastrar_pet);
        TestTools.checarToast(R.string.erro_cadastro_campos_obrigatorios_Pet);
    }

    //Teste que simula um cadastro do pet com todos os campos preenchidos
    @Test
    public void testeCadastrarPet() throws InterruptedException {
        TestToolsPet.preencherCadastro(sNomePet, sraca);
        TestTools.clicarBotaoComScroll(R.id.botao_cadastrar_pet);
        Thread.sleep(1000);
        TestTools.checarToast(R.string.sucesso_cadastro_Pet);
    }

    @After
    public void tearDown() throws Exception {
        Thread.sleep(4000);
    }
}
