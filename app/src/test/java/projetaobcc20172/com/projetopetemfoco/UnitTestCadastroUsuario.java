package projetaobcc20172.com.projetopetemfoco;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;

import projetaobcc20172.com.projetopetemfoco.activity.CadastroUsuarioActivity;
import projetaobcc20172.com.projetopetemfoco.excecoes.CampoObrAusenteException;
import projetaobcc20172.com.projetopetemfoco.excecoes.SenhasDiferentesException;
import projetaobcc20172.com.projetopetemfoco.excecoes.ValidacaoException;
import projetaobcc20172.com.projetopetemfoco.model.Usuario;
import projetaobcc20172.com.projetopetemfoco.utils.VerificadorDeObjetos;

/*Dicas de como criar testes https://github.com/junit-team/junit4/wiki/parameterized-tests
* Essa classe realiza os testes unitários para o cadastro de usuarios.
* */
@RunWith(Parameterized.class)
public class UnitTestCadastroUsuario {

    @Parameterized.Parameter
    public Usuario userTest;

    /*Paramentros que serão utlizados pelo teste de campo obrigatorio
    basicamente é um produto cartesiano com as combinações possiveis de
    campos ausêntes
    */
    @Parameterized.Parameters
    public static Iterable<?> data() {
        return Arrays.asList(
                new Usuario("","","", ""),
                new Usuario("teste","","",""),
                new Usuario("teste","email","",""),
                new Usuario("teste","","senha1","senha2"),
                new Usuario("","email","senha1",""));
        }

    @Test (expected=CampoObrAusenteException.class)
    public void testCampoObgUsuario() throws CampoObrAusenteException, SenhasDiferentesException {
        VerificadorDeObjetos.vDadosUsuario(userTest);
    }

    @Test(expected = SenhasDiferentesException.class)
    public void testSenhasDiferentes() throws CampoObrAusenteException, SenhasDiferentesException {
        Usuario testSenha = new Usuario("teste","email","senha1", "senhaDiferente");
        VerificadorDeObjetos.vDadosUsuario(testSenha);
    }

}
