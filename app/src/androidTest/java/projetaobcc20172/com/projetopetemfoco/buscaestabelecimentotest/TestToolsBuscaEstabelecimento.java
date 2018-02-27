package projetaobcc20172.com.projetopetemfoco.buscaestabelecimentotest;

import android.app.Activity;

import projetaobcc20172.com.projetopetemfoco.R;
import projetaobcc20172.com.projetopetemfoco.TestTools;

/**
 * Created by raul1 on 06/01/2018.
 */

public class TestToolsBuscaEstabelecimento {

    public static void digitarBuscar(String estabelecimento){
        TestTools.digitarCampo(R.id.svBusca,estabelecimento);
    }

    public static void realizarPesquisa(){
        TestTools.pressionarBuscarTeclado();
    }

    public static void avaliarListaEstabelecimentos(Activity act, String estabelecimento){
        TestTools.checarListViewComTextView(act,R.id.lvBuscaEsta,R.id.tvNomeForn,estabelecimento,15);
    }

    protected static void checarTamanhoList(int qtEsperada){
        TestTools.checarTamanhoList(TestTools.activityAtual(),R.id.lvBuscaEsta,qtEsperada);
    }
}
