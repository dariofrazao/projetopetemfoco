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

    protected static void clicariconeExcluir(){
        TestTools.clicarEmItemDentroListView(R.id.lv_pets,0,R.id.ibtnRemover);
    }


    public static void clicarIconeEditar(){
        TestTools.clicarEmItemDentroListView(R.id.lv_pets,0,R.id.ibtnEditar);
    }

    public static void clicarMeusPets(){
        TestTools.clicarItemNavigationMenu(R.id.drawer_layout, R.id.nav_busca, R.id.nav_pets);
    }
    public static void clicarCadastrarPet(){
        TestTools.clicarBotao(R.id.btnCadastrarPet);
    }
}
