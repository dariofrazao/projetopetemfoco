package projetaobcc20172.com.projetopetemfoco.test.cadastropettest;

import projetaobcc20172.com.projetopetemfoco.R;
import projetaobcc20172.com.projetopetemfoco.test.TestTools;

/**
 * Created by raul on 09/12/17.
 */

public class TestToolsCadPet {

    protected static void preencherEClicar(String nomePet,String raca){
        //preenche o campo de nome com o texto do "nomePet"
        TestTools.digitarCampo(R.id.editText_nome_pet,nomePet);
        TestTools.digitarCampo(R.id.editText_raca_pet,raca);
        TestTools.clicarBotao(R.id.botao_cadastrar_pet);
    }

}
