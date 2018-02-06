package projetaobcc20172.com.projetopetemfoco.avaliacaoservicotest;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;
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

@LargeTest
@RunWith(AndroidJUnit4.class)
public class AvaliacaoActivityTest {
    @Parameterized.Parameter(0)
    public String mNomeServico;
    @Parameterized.Parameter(1)
    public String mTipoAnimal;

    @Rule
    public ActivityTestRule<LoginActivity> mActivityTestRule = new ActivityTestRule<>(LoginActivity.class);

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
    private static String mComentario[] = {"Muito Bom",""};
    private static float  mNota[] = {4,0};

    @Before
    public void setUp() throws Exception {
        //Desloga caso já esteja logado.
        //Evita erros nos testes
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
        TestToolsAvaliacaoServico.selecionarServico("Banho");
        Thread.sleep(4000);
        TestTools.clicarEmITemListView(R.id.lvEstaServicoBusca, 0);
        Thread.sleep(4000);
        TestTools.clicarBotao(R.id.btnAvaliarServico);
        Thread.sleep(4000);
        TestTools.editarTextoComScroll(R.id.etComentarioAvaliacaoServico);
        Thread.sleep(4000);
        TestTools.clicarBotao(R.id.botao_avaliar_servico);
    }

    @Test
    public void avaliacaoActivityTest() throws InterruptedException {
        TestToolsAvaliacaoServico.avaliarServico(mNomeServico);
    }
}
