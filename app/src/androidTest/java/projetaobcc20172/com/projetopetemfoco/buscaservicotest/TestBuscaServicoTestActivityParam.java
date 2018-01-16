package projetaobcc20172.com.projetopetemfoco.buscaservicotest;

import android.support.test.rule.ActivityTestRule;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

import projetaobcc20172.com.projetopetemfoco.R;
import projetaobcc20172.com.projetopetemfoco.TestTools;
import projetaobcc20172.com.projetopetemfoco.activity.LoginActivity;
import projetaobcc20172.com.projetopetemfoco.logintests.LoginActivityTest;


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
            TestTools.clicarItemNavigationMenu(R.id.drawer_layout, R.id.nav_busca, R.id.nav_sair);
        }catch (Exception e){
            e.getMessage();
        }
        LoginActivityTest log = new LoginActivityTest();
        log.testeLoginComSucesso();
        TestTools.clicarItemNavigationMenu(R.id.drawer_layout, R.id.nav_busca, R.id.nav_servicos);
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
