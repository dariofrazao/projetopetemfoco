package projetaobcc20172.com.projetopetemfoco.test.cadastroconsumidortest;

import java.util.Random;

import projetaobcc20172.com.projetopetemfoco.R;
import projetaobcc20172.com.projetopetemfoco.test.TestTools;


/**
 * Created by raul on 16/11/17.
 * Classe que existe para ser herdade pelas outras classes de teste para cadastrp
 * ele guarda os atributos que são utlizados assim como também os métodos after e before
 */

public class TestToolsCadUser {

    //Metodo que preenche os campos e clica no botao de cadastrar
    protected static void preencherEclicar(String nome, String email, String senha1, String senha2){
        TestTools.digitarCampo(R.id.etCadastroNomeUsuario,nome);
        TestTools.digitarCampo(R.id.etCadastroEmailUsuario,email);
        TestTools.digitarCampo(R.id.etCadastroSenhaUsuario,senha1);
        TestTools.digitarCampo(R.id.etCadastroSenha2Usuario,senha2);
        TestTools.clicarBotao(R.id.botao_cadastrar_endereco);
    }

}
