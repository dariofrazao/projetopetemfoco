package projetaobcc20172.com.projetopetemfoco.test.buscaservicotest;

import android.support.test.espresso.Espresso;
import android.support.test.espresso.action.ViewActions;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.rule.ActivityTestRule;

import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import projetaobcc20172.com.projetopetemfoco.R;
import projetaobcc20172.com.projetopetemfoco.activity.LoginActivity;
import projetaobcc20172.com.projetopetemfoco.test.TestTools;
import projetaobcc20172.com.projetopetemfoco.test.logintests.LoginActivityTest;


/**
 * Created by raul1 on 06/01/2018.
 */
@RunWith(Parameterized.class)
public class TestBuscaServicoTestActivityParam {
    @Parameterized.Parameter(0)
    public String mNomeServico;
    @Parameterized.Parameter(1)
    public String mTipoAnimal;

    @Rule
    public ActivityTestRule<LoginActivity> loginActivityRule = new ActivityTestRule<>(LoginActivity.class);

    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][]{
                {"Banho","Gato"},
                {"Tosa","Gato"},
                {"Hospedagem","Gato"},
                {"Passeio","Gato"},
                {"Vacinação","Gato"},
                {"Banho","Cachorro"},
                {"Tosa","Cachorro"},
                {"Hospedagem","Cachorro"},
                {"Passeio","Cachorro"},
                {"Vacinação","Cachorro"}
        });
    }

    @Before
    public void setUp() throws Exception {
        try{
            TestTools.clicarBotao(R.id.btnSair);
        }catch (Exception e){
            e.getMessage();
        }
        LoginActivityTest log = new LoginActivityTest();
        log.testeUsuarioCadastrado();
        TestTools.clicarBotao(R.id.btnBuscarServicos);
        TestToolsBuscaServico.clicarBuscaServico();
    }

    @Test
    public void buscarServco() throws InterruptedException {
        TestToolsBuscaServico.selecionarPet(mTipoAnimal);
        TestToolsBuscaServico.selecionarServico(mNomeServico);
        Thread.sleep(2000);
        TestToolsBuscaServico.validarListaServicos(mNomeServico,mTipoAnimal);
    }


}
