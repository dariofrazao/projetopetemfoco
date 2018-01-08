package projetaobcc20172.com.projetopetemfoco.test.buscaestabelecimentotest;

import android.support.test.espresso.Espresso;
import android.support.test.rule.ActivityTestRule;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import projetaobcc20172.com.projetopetemfoco.R;
import projetaobcc20172.com.projetopetemfoco.activity.LoginActivity;
import projetaobcc20172.com.projetopetemfoco.test.TestTools;
import projetaobcc20172.com.projetopetemfoco.test.logintests.LoginActivityTest;

/**
 * Created by raul1 on 06/01/2018.
 */

public class TestBuscaEstabelecimentoActivity {
    private String mEstabelecimento = "pets";
    private String mEstabelecimentoNaoExiste = "2224431ssssxctt44545*";
    @Rule
    public ActivityTestRule<LoginActivity> loginActivityRule = new ActivityTestRule<>(LoginActivity.class);

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
        Espresso.closeSoftKeyboard();
    }

    @Test
    public void testBuscarEstabelecimento() throws InterruptedException {
        TestToolsBuscaEstabelecimento.digitarBuscar(this.mEstabelecimento);
        TestToolsBuscaEstabelecimento.realizarPesquisa();
        Thread.sleep(4000);
        TestToolsBuscaEstabelecimento.avaliarListaEstabelecimentos(TestTools.activityAtual(),this.mEstabelecimento);
    }

    @Test
    public void testBuscarEstabeleciment() throws InterruptedException {
        TestToolsBuscaEstabelecimento.digitarBuscar("");
        TestToolsBuscaEstabelecimento.realizarPesquisa();
        Thread.sleep(4000);
        TestToolsBuscaEstabelecimento.checarTamanhoList(0);
    }

    @Test
    public void testBuscarEstabelecimentNaoExiste() throws InterruptedException {
        TestToolsBuscaEstabelecimento.digitarBuscar(this.mEstabelecimentoNaoExiste);
        TestToolsBuscaEstabelecimento.realizarPesquisa();
        Thread.sleep(4000);
        TestToolsBuscaEstabelecimento.checarTamanhoList(0);
    }





}
