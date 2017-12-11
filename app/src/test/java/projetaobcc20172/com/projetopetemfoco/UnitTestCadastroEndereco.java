package projetaobcc20172.com.projetopetemfoco;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;

import projetaobcc20172.com.projetopetemfoco.excecoes.CampoObrAusenteException;
import projetaobcc20172.com.projetopetemfoco.model.Endereco;
import projetaobcc20172.com.projetopetemfoco.utils.VerificadorDeObjetos;



/**
 * Created by Alexsandro on 07/12/2017.
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(Parameterized.class)
public class UnitTestCadastroEndereco {

    @Parameterized.Parameter
    public Endereco endTest;

    @Parameterized.Parameters
    public static Iterable<? extends Object> data() {
        String mLog = "teste";
        String mBairro = "bairro";
        String mCidade = "cidade";
        String mUf = "PE";
        String mCep = "55299-520";
        return Arrays.asList(
                new Endereco("","","","",""),
                new Endereco(mLog,"","","",""),
                new Endereco(mLog,mBairro,"","",""),
                new Endereco(mLog,mBairro,mCidade,"",""),
                new Endereco("",mBairro,mCidade,mUf,""),
                new Endereco("","",mCidade,mUf,mCep),
                new Endereco("","","",mUf,mCep),
                new Endereco("","","","",mCep));
    }

    @Test (expected=CampoObrAusenteException.class)
    public void testCampoObg() throws CampoObrAusenteException {
        VerificadorDeObjetos.vDadosObrEndereco(endTest);
    }

}