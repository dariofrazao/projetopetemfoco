package projetaobcc20172.com.projetopetemfoco.avaliacaoservicotest;

import android.widget.GridView;
import android.widget.TextView;

import projetaobcc20172.com.projetopetemfoco.R;
import projetaobcc20172.com.projetopetemfoco.TestTools;

/**
 * Created by Alexsandro on 05/02/2018.
 */

public class TestToolsAvaliacaoServico {
    protected static void avaliarServico(String servico) throws InterruptedException {
        GridView gridServico = TestTools.activityAtual().findViewById(R.id.gridServicos);
        for(int i=0;i<gridServico.getCount();i++){
            TextView t = gridServico.getChildAt(i).findViewById(R.id.gridTextServico);
            String text = t.getText().toString();
            if(text.equals(servico)){
                TestTools.clicarEmITemListView(R.id.gridServicos,i);
                TestTools.clicarBotao(R.id.btnAvaliarServico);
                break;
            }
        }
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


//    protected static void validarListaServicos(String servico,String pet){
//        TestTools.checarListViewComTextView(TestTools.activityAtual(),R.id.lvEstaServicoBusca,R.id.tvExibirServico,servico,5);
//    }

}
