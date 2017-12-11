
package projetaobcc20172.com.projetopetemfoco;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;

import projetaobcc20172.com.projetopetemfoco.activity.CadastroServicoActivity;

import projetaobcc20172.com.projetopetemfoco.excecoes.ValidacaoException;
import projetaobcc20172.com.projetopetemfoco.model.Servico;
import projetaobcc20172.com.projetopetemfoco.utils.VerificadorDeObjetos;


@RunWith(Parameterized.class)
public class UnitTestCadastroServico {

    @Parameterized.Parameter
    public Servico servicoTest;

    @Parameterized.Parameters
    public static Iterable<? extends Object> data() {
        return Arrays.asList(
                new Servico("",0,""),
                new Servico("teste",0,""),
                new Servico("teste",30,""),
                new Servico("teste",0,"descricao bacana"),
                new Servico("",20,"descricao bacana"),
                new Servico("",0,"descricao bacana"));
    }

    @Test(expected=ValidacaoException.class)
    public void testCampoObg() throws ValidacaoException {
        CadastroServicoActivity test = new CadastroServicoActivity();
        VerificadorDeObjetos.vDadosServico(servicoTest,test);
    }

}
