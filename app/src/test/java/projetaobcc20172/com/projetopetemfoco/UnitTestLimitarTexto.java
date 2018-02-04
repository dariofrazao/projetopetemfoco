package projetaobcc20172.com.projetopetemfoco;

import org.junit.Test;

import projetaobcc20172.com.projetopetemfoco.utils.Utils;

import static junit.framework.Assert.assertEquals;


/**
 * Created by raul1 on 04/02/2018.
 */
public class UnitTestLimitarTexto {

    public String textoTesteMaior = "123456789" ;
    public String textoTesteMenor = "1234" ;
    public int quantidade = 6;


    @Test
    public void testStringMaior() {
        String resultado = Utils.limitarTexto(textoTesteMaior,quantidade);
        assertEquals(quantidade+3,resultado.length());
    }

    @Test
    public void testStringMenor() {
        String resultado = Utils.limitarTexto(textoTesteMenor,quantidade);
        assertEquals(resultado.length(),textoTesteMenor.length());
    }
}
