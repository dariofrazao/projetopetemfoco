package projetaobcc20172.com.projetopetemfoco.avaliacaotest;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;

import projetaobcc20172.com.projetopetemfoco.excecoes.CampoObrAusenteException;
import projetaobcc20172.com.projetopetemfoco.model.Avaliacao;
import projetaobcc20172.com.projetopetemfoco.utils.VerificadorDeObjetos;

/**
 * Created by Alexsandro on 19/01/2018.
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */

@RunWith(Parameterized.class)
public class UnitTestAvaliacao {

    @Parameterized.Parameter
    public Avaliacao endTest;

    @Parameterized.Parameters
    public static Iterable<?> data() {
        String mIdUsuario = "05252878745878745";
        String mNomeUsuario = "MMMMMMM GGGGGG DDDD";
        int mEstrelas = 5;
        String mCometario = "Excelente Estabelecimento";

        return Arrays.asList(
                new Avaliacao("", "", 0, ""),
                new Avaliacao(mIdUsuario, mNomeUsuario, 0, ""),
                new Avaliacao(mIdUsuario, mNomeUsuario, mEstrelas, ""),
                new Avaliacao(mIdUsuario, mNomeUsuario, mEstrelas, mCometario),
                new Avaliacao(mIdUsuario, "", mEstrelas, ""),
                new Avaliacao(mIdUsuario, "", mEstrelas, mCometario),
                new Avaliacao(mIdUsuario, "", 0, mCometario),
                new Avaliacao("", mNomeUsuario, 0, ""),
                new Avaliacao("", mNomeUsuario, mEstrelas, ""),
                new Avaliacao("", mNomeUsuario, mEstrelas, mCometario),
                new Avaliacao("", "", mEstrelas, ""),
                new Avaliacao("", "", mEstrelas, mCometario),
                new Avaliacao("", "", 0, mCometario),
                new Avaliacao("", mNomeUsuario, 0, mCometario));
    }

    @Test(expected = CampoObrAusenteException.class)
    public void testCampoObg() throws CampoObrAusenteException {
        VerificadorDeObjetos.vDadosObjAvaliacao(endTest);
    }
}