package projetaobcc20172.com.projetopetemfoco.favoritostests;

import android.support.test.espresso.Espresso;
import android.support.test.rule.ActivityTestRule;
import android.support.test.uiautomator.UiObjectNotFoundException;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import projetaobcc20172.com.projetopetemfoco.R;
import projetaobcc20172.com.projetopetemfoco.TestTools;
import projetaobcc20172.com.projetopetemfoco.activity.LoginActivity;
import projetaobcc20172.com.projetopetemfoco.buscaestabelecimentotest.TestToolsBuscaEstabelecimento;
import projetaobcc20172.com.projetopetemfoco.logintests.LoginActivityTest;

/**
 * Created by LuizAlberes on 09/02/2018.
 */

public class SalvarFavoritoActivityTest {

    private String sEstabelecimento = "Cloves Cabral";

    @Rule
    public ActivityTestRule<LoginActivity> loginActivityRule = new ActivityTestRule<>(LoginActivity.class);

    @Before
    public void setUp() throws Exception {
        try{
            TestTools.clicarItemNavigationMenu(R.id.drawer_layout, R.id.nav_busca, R.id.nav_sair);
        }catch (Exception e){
            e.getMessage();
        }

        LoginActivityTest log = new LoginActivityTest();
        log.testeLoginComSucesso();
        Thread.sleep(4000);
        TestToolsFavoritos.acessarEstabelecimentos();
        Thread.sleep(4000);
        TestToolsBuscaEstabelecimento.digitarBuscar(this.sEstabelecimento);
        Thread.sleep(4000);
        TestToolsBuscaEstabelecimento.realizarPesquisa();
        Thread.sleep(8000);
        TestTools.clicarEmITemListView(R.id.lvBuscaEsta, 0);
        Thread.sleep(4000);
        Espresso.closeSoftKeyboard();
    }

    //Teste que simula a ação de favoritar um estabelecimento
    @Test
    public void testeSalvarFavorito() throws UiObjectNotFoundException, InterruptedException {
        TestToolsFavoritos.botaoFavoritar();
        Thread.sleep(1000);
        TestTools.checarToast(R.string.estabelecimento_favoritado);
    }

    @After
    public void tearDown() throws Exception {
        Thread.sleep(4000);
    }

}
