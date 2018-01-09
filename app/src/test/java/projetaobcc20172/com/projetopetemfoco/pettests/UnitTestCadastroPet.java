package projetaobcc20172.com.projetopetemfoco.pettests;

import org.junit.Test;

import projetaobcc20172.com.projetopetemfoco.excecoes.CampoObrAusenteException;
import projetaobcc20172.com.projetopetemfoco.model.Pet;
import projetaobcc20172.com.projetopetemfoco.utils.VerificadorDeObjetos;


public class UnitTestCadastroPet {

    @Test(expected=CampoObrAusenteException.class)
    public void testCampoObg() throws CampoObrAusenteException  {
        Pet petTest = new Pet("","shitzu");
        VerificadorDeObjetos.vDadosPet(petTest);
    }


}
