package projetaobcc20172.com.projetopetemfoco.test.cadastroenderecotest;

import projetaobcc20172.com.projetopetemfoco.R;
import projetaobcc20172.com.projetopetemfoco.test.TestTools;

/**
 * Created by raul on 09/12/17.
 */

public class TestToolsCadEndereco {

        public static void preencher(String logradouro,String numero,String complemento,
                                            String bairro,String localidade,String uf,String cep){

            TestTools.digitarCampo(R.id.editText_endereco_logradouro,logradouro);
            TestTools.digitarCampo(R.id.editText_endereco_numero,numero);
            TestTools.digitarCampo(R.id.editText_endereco_complemento,complemento);
            TestTools.digitarCampo(R.id.editText_endereco_bairro,bairro);
            TestTools.digitarCampo(R.id.editText_endereco_localidade,localidade);
            TestTools.digitarCampo(R.id.editText_endereco_cep,cep);
            TestToolsCadEndereco.selecionarEstado(uf);
        }

        public static void selecionarEstado(String estado){
            TestTools.selecionarItemSpinnerComScroll(R.id.ufSpinner,estado);
        }
}
