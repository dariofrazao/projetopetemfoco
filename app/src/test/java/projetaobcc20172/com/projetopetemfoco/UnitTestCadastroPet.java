package projetaobcc20172.com.projetopetemfoco;

import org.junit.Test;

import projetaobcc20172.com.projetopetemfoco.activity.CadastroPetActivity;
import projetaobcc20172.com.projetopetemfoco.activity.CadastroServicoActivity;
import projetaobcc20172.com.projetopetemfoco.excecoes.CampoObrAusenteException;
import projetaobcc20172.com.projetopetemfoco.excecoes.ValidacaoException;
import projetaobcc20172.com.projetopetemfoco.model.Pet;
import projetaobcc20172.com.projetopetemfoco.utils.VerificadorDeObjetos;


public class UnitTestCadastroPet {

    @Test(expected=CampoObrAusenteException.class)
    public void testCampoObg() throws CampoObrAusenteException  {
        Pet petTest = new Pet("","shitzu");
        VerificadorDeObjetos.vDadosPet(petTest);
    }


}
