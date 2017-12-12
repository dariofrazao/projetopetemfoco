
package projetaobcc20172.com.projetopetemfoco;


import android.content.Context;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import java.util.Arrays;

import projetaobcc20172.com.projetopetemfoco.excecoes.ValidacaoException;
import projetaobcc20172.com.projetopetemfoco.model.Servico;
import projetaobcc20172.com.projetopetemfoco.utils.VerificadorDeObjetos;


@RunWith(Parameterized.class)
public class UnitTestCadastroServico {

    @Rule
    public MockitoRule rule = MockitoJUnit.rule();

    @Parameterized.Parameter
    public Servico servicoTest;

    @Mock
    private Context mMockContext;

    @Parameterized.Parameters
    public static Iterable<? extends Object> data() {
        return Arrays.asList(
                new Servico("",""),
                new Servico("teste",""),
                new Servico("","12"));
    }

    @Test(expected=ValidacaoException.class)
    public void testCampoObg() throws ValidacaoException {
        VerificadorDeObjetos.vDadosServico(servicoTest, mMockContext);
    }

}
