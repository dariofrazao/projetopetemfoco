package projetaobcc20172.com.projetopetemfoco.pettests;

import projetaobcc20172.com.projetopetemfoco.R;
import projetaobcc20172.com.projetopetemfoco.TestTools;

/**
 * Created by raul on 09/12/17.
 */

public class TestToolsPet {

    protected static void preencherCadastro(String nomePet,String raca){
        TestTools.digitarCampo(R.id.etCadastroNomePet,nomePet);
        TestTools.digitarCampo(R.id.etCadastroRaçaPet,raca);
    }

    protected static void preencherEdicao(String nomePet,String raca){
        TestTools.digitarCampo(R.id.etEditarNomePet,nomePet);
        TestTools.digitarCampo(R.id.etEditarRaçaPet,raca);
    }

}
