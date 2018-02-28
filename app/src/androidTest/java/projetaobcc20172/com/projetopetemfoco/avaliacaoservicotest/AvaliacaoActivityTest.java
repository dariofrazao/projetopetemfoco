package projetaobcc20172.com.projetopetemfoco.avaliacaoservicotest;

import android.support.test.InstrumentationRegistry;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.test.uiautomator.UiDevice;
import android.test.suitebuilder.annotation.LargeTest;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import projetaobcc20172.com.projetopetemfoco.R;
import projetaobcc20172.com.projetopetemfoco.TestTools;
import projetaobcc20172.com.projetopetemfoco.activity.LoginActivity;
import projetaobcc20172.com.projetopetemfoco.logintests.LoginActivityTest;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class AvaliacaoActivityTest {

    private UiDevice mDevice;

    @Rule
    public ActivityTestRule<LoginActivity> mActivityTestRule = new ActivityTestRule<>(LoginActivity.class);


//    public static String[][] tipo = {
//                {"Banho","Gato"},
//                {"Tosa","Gato"},
//                {"Hospedagem","Gato"},
//                {"Passeio","Gato"},
//                {"Vacinação","Gato"},
//                {"Banho","Cachorro"},
//                {"Tosa","Cachorro"},
//                {"Hospedagem","Cachorro"},
//                {"Passeio","Cachorro"},
//                {"Vacinação","Cachorro"}
//    }

    private static String mNomeServico = "Banho";
    private static String mComentario[] = {"Muito Bom",""};
    // private static float  mNota[] = {4,0};

    @Before
    public void setUp() throws Exception {

        mDevice = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation());

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
        TestToolsAvaliacaoServico.selecionarServico(mNomeServico);
        Thread.sleep(4000);
        TestTools.clicarEmITemListView(R.id.lvEstaServicoBusca, 0);
        Thread.sleep(4000);
        TestTools.clicarBotao(R.id.btnAvaliarServico);

    }

    @Test
    public void avaliarComSucesso() throws InterruptedException {
        TestTools.digitarCampo(R.id.etComentarioAvaliacaoServico , mComentario[0]);
        Thread.sleep(4000);
        TestTools.clicarBotao(R.id.botao_avaliar_servico);
        TestTools.checarToast(R.string.sucesso_avaliacao_servico);
    }

    @Test
    public void cancelarAvaliacao() throws InterruptedException {
        TestTools.digitarCampo(R.id.etComentarioAvaliacaoServico , mComentario[0]);
        Thread.sleep(4000);
        mDevice.pressBack();
    }
}
