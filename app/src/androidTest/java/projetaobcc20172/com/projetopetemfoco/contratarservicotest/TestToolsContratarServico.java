package projetaobcc20172.com.projetopetemfoco.contratarservicotest;

import android.widget.GridView;
import android.widget.TextView;

import projetaobcc20172.com.projetopetemfoco.R;
import projetaobcc20172.com.projetopetemfoco.TestTools;

/**
 * Created by dario on 04/02/2018.
 */

public class TestToolsContratarServico {

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
}
