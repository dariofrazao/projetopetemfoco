package projetaobcc20172.com.projetopetemfoco.enderecotests;

import projetaobcc20172.com.projetopetemfoco.R;
import projetaobcc20172.com.projetopetemfoco.TestTools;

/**
 * Created by raul on 09/12/17.
 */

public class TestToolsEndereco {

        public static void preencherCadastro(String logradouro,String numero,String complemento,
                                            String bairro,String localidade,String uf,String cep){

            TestTools.digitarCampo(R.id.etCadastroCepEndereco,cep);
            TestTools.digitarCampo(R.id.etCadastroLogradouroEndereco,logradouro);
            TestTools.digitarCampo(R.id.etCadastroNumeroEndereco,numero);
            TestTools.digitarCampo(R.id.etCadastroComplementoEndereco,complemento);
            TestTools.digitarCampo(R.id.etCadastroBairroEndereco,bairro);
            TestTools.digitarCampo(R.id.etCadastroLocalidadeEndereco,localidade);
            TestToolsEndereco.selecionarEstadoCadastro(uf);
        }

    public static void preencherEdicao(String logradouro,String numero,String complemento,
                                 String bairro,String localidade,String uf,String cep){

        TestTools.digitarCampo(R.id.etEditarCepEndereco,cep);
        TestTools.digitarCampo(R.id.etEditarLogradouroEndereco,logradouro);
        TestTools.digitarCampo(R.id.etEditarNumeroEndereco,numero);
        TestTools.digitarCampo(R.id.etEditarComplementoEndereco,complemento);
        TestTools.digitarCampo(R.id.etEditarBairroEndereco,bairro);
        TestTools.digitarCampo(R.id.etEditarLocalidadeEndereco,localidade);
        TestToolsEndereco.selecionarEstadoEdicao(uf);
    }

    public static void apagarCampos(){

        TestTools.apagarCampo(R.id.etEditarCepEndereco);
        TestTools.apagarCampo(R.id.etEditarLogradouroEndereco);
        TestTools.apagarCampo(R.id.etEditarNumeroEndereco);
        TestTools.apagarCampo(R.id.etEditarBairroEndereco);
        TestTools.apagarCampo(R.id.etEditarLocalidadeEndereco);
        TestTools.apagarCampo(R.id.etEditarComplementoEndereco);
    }

        public static void selecionarEstadoCadastro(String estado){
            TestTools.selecionarItemSpinnerComScroll(R.id.ufSpinner,estado);
        }

        public static void selecionarEstadoEdicao(String estado){
            TestTools.selecionarItemSpinnerComScroll(R.id.ufEditarSpinner,estado);
        }

        public static void clicarIconeEditar(){
            TestTools.clicarEmItemDentroListView(R.id.lv_enderecos,0,R.id.ibtnEditar);
        }

        public static void clicarIconeExcluir(){
            TestTools.clicarEmItemDentroListView(R.id.lv_enderecos,0,R.id.ibtnRemover);
        }

        public static void clicarMenuEndereco(){
            TestTools.clicarItemMenu("Endereço");
        }

        public static void clicarBtnCadastrarEnd(){
            TestTools.clicarBotao(R.id.btnCadastrarEndereco);
        }
}
