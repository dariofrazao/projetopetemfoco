package projetaobcc20172.com.projetopetemfoco.test.cadastropettest;

import android.support.test.espresso.Espresso;
import android.support.test.espresso.intent.Intents;
import android.support.test.rule.ActivityTestRule;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import projetaobcc20172.com.projetopetemfoco.R;
import projetaobcc20172.com.projetopetemfoco.activity.CadastroPetActivity;
import projetaobcc20172.com.projetopetemfoco.activity.LoginActivity;
import projetaobcc20172.com.projetopetemfoco.activity.MainActivity;
import projetaobcc20172.com.projetopetemfoco.test.TestTools;
import projetaobcc20172.com.projetopetemfoco.test.logintests.LoginActivityTest;

/**
 * Created by dario on 07/12/17.
 * Essa classe é responsavel por executar os testes automatizados
 * Para o cadastro de um pet
 */

public class CadastroPetActivityTest{

    private static String sNomePet = "Billie";
    private static String sraca = "Bulldog";

    /*
    @Rule
    public ActivityTestRule<CadastroPetActivity> cadPetActivityRule = new ActivityTestRule<>(CadastroPetActivity.class);
    */
    @Rule
    public ActivityTestRule <LoginActivity> loginActivityRule = new ActivityTestRule<>(LoginActivity.class);

    @Before
    public void setUp() throws Exception {
        try{
            TestTools.clicarBotao(R.id.botao_sair);
        }catch (Exception e){
            e.getMessage();
        }

        LoginActivityTest log = new LoginActivityTest();
        log.testeUsuarioCadastrado();
        TestTools.clicarBotao(R.id.botao_cadastrar_pet);
        Espresso.closeSoftKeyboard();
        Thread.sleep(3000);
    }

    //Teste que simula um cadastro do pet com o campo nome em branco
    @Test
    public void testeNomeEmBranco(){
        TestToolsCadPet.preencherEClicar("",sraca);
        TestTools.clicarBotao(R.id.botao_cadastrar_pet);
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
    public void testeCadastrarPet(){
        Intents.init();
        TestToolsCadPet.preencherEClicar(sNomePet, sraca);
        TestTools.checarToast(R.string.sucesso_cadastro_Pet);
        TestTools.verificarMudancaActivity(MainActivity.class.getName());
    }

    @After
    public void tearDown() throws Exception {
        Thread.sleep(4000);
    }
}
