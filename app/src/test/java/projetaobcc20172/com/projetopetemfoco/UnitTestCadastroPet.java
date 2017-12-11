package projetaobcc20172.com.projetopetemfoco;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;

import projetaobcc20172.com.projetopetemfoco.excecoes.CampoObrAusenteException;
import projetaobcc20172.com.projetopetemfoco.model.Pet;
import projetaobcc20172.com.projetopetemfoco.utils.VerificadorDeObjetos;


public class UnitTestCadastroPet {

    @Test(expected=CampoObrAusenteException.class)
    public void testCampoObg() throws CampoObrAusenteException {
        Pet petTest = new Pet("","shitzu");
        VerificadorDeObjetos.vDadosPet(petTest);
    }

}
