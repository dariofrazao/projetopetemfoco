package projetaobcc20172.com.projetopetemfoco.buscaservicotest;


import android.widget.GridView;
import android.widget.TextView;

import projetaobcc20172.com.projetopetemfoco.R;
import projetaobcc20172.com.projetopetemfoco.TestTools;


/**
 * Created by raul1 on 06/01/2018.
 */

public class TestToolsBuscaServico {
    protected static void clicarBuscaServico() {
        TestTools.clicarItemNavigationMenu(R.id.drawer_layout, R.id.nav_busca, R.id.nav_servicos);
    }

    protected static void selecionarServico(String servico) throws InterruptedException {
        GridView gridServico = TestTools.activityAtual().findViewById(R.id.gridServicos);
        for(int i=0;i<gridServico.getCount();i++){
            TextView t = gridServico.getChildAt(i).findViewById(R.id.gridTextServico);
            String text = t.getText().toString();
            if(text.equals(servico)){
                TestTools.clicarEmITemListView(R.id.gridServicos,i);
                break;
            }
        }
    }

    protected static void selecionarPet(String pet) throws InterruptedException {
        TestTools.moverAbaDireita(R.id.container);
        Thread.sleep(1000);
        GridView gridPet = TestTools.activityAtual().findViewById(R.id.gridOpPet);
        for(int i=0;i<gridPet.getCount();i++){
            TextView t = gridPet.getChildAt(i).findViewById(R.id.tvGridTextPet);
            if(t.getText().toString().equals(pet)){
                TestTools.clicarEmITemListView(R.id.gridOpPet,i);
                break;
            }
        }
        TestTools.moverAbaEsquerda(R.id.container);
    }

    protected static void validarListaServicos(String servico,String pet){
        TestTools.checarListViewComTextView(TestTools.activityAtual(),R.id.lvEstaServicoBusca,R.id.tvExibirServico,servico,5);
    }

}
