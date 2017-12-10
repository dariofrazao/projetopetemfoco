package projetaobcc20172.com.projetopetemfoco.test.cadastrarservicotest;

import projetaobcc20172.com.projetopetemfoco.test.TestTools;
import projetaobcc20172.com.projetopetemfoco.R;
/**
 * Created by raul on 10/12/17.
 */

public class TestToolsCadServico {

    public static void preencherEClicar(String nomeServico, String valor, String descricao ){
        TestTools.digitarCampo(R.id.etCadastroNomeServico,nomeServico);
        TestTools.digitarCampo(R.id.etCadastroValorServico,valor);
        TestTools.digitarCampo(R.id.etCadastroDescricaoServico,descricao);
    }
}
