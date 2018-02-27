package projetaobcc20172.com.projetopetemfoco.promocaotest;


import android.support.test.rule.ActivityTestRule;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import projetaobcc20172.com.projetopetemfoco.R;
import projetaobcc20172.com.projetopetemfoco.TestTools;
import projetaobcc20172.com.projetopetemfoco.activity.AcessoInformacoesEstabelecimentoActivity;
import projetaobcc20172.com.projetopetemfoco.activity.MainActivity;
import projetaobcc20172.com.projetopetemfoco.activity.VisualizarPromocaoActivity;
import projetaobcc20172.com.projetopetemfoco.model.Promocao;


/**
 * Created by raul1 on 04/02/2018.
 */

public class FeedPromocoesTest {

    @Rule
    public ActivityTestRule<MainActivity> mainActivityRule = new ActivityTestRule<>(MainActivity.class);

    @Before
    public void setUp() throws Exception {

        TestTools.clicarItemNavigationMenu(R.id.drawer_layout, R.id.nav_busca, R.id.nav_promocoes);
        Thread.sleep(5000);
    }

    @Test
    public void clicarPromocao(){
        TestToolsPromocao.clickPromocao(0);
        TestTools.verificarMudancaActivity(VisualizarPromocaoActivity.class.getName());
        TestToolsPromocao.checkTitulo();
    }

    @Test
    public void verEstabelecimentoTest() throws InterruptedException {
        this.clicarPromocao();
        Promocao promocao = ((VisualizarPromocaoActivity) TestTools.activityAtual()).mPromocao;
        Thread.sleep(2000);
        TestToolsPromocao.clicarEstb();
        Thread.sleep(2000);
        TestTools.verificarMudancaActivity(AcessoInformacoesEstabelecimentoActivity.class.getName());
        TestTools.checkText(R.id.tvExibeNomeEstabelecimento,promocao.getmFornecedor().getNome());

    }
}
