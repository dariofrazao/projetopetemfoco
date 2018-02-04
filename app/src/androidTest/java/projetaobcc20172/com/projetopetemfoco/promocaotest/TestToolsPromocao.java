package projetaobcc20172.com.projetopetemfoco.promocaotest;

import projetaobcc20172.com.projetopetemfoco.R;
import projetaobcc20172.com.projetopetemfoco.TestTools;

import projetaobcc20172.com.projetopetemfoco.activity.VisualizarPromocaoActivity;
import projetaobcc20172.com.projetopetemfoco.model.Promocao;

/**
 * Created by raul1 on 04/02/2018.
 */

public class TestToolsPromocao {

    public static void clickPromocao(int posicao){
        TestTools.clickItemRecyclerView(R.id.recyclerView,posicao);
    }

    public static void checkTitulo(){
        Promocao promocao = ((VisualizarPromocaoActivity) TestTools.activityAtual()).mPromocao;
        TestTools.checkText(R.id.tvExibeTituloPromo,promocao.getTitulo());
    }


    public static void clicarEstb(){
        TestTools.clicarBotao(R.id.btnPromocaoEst);
    }
}
