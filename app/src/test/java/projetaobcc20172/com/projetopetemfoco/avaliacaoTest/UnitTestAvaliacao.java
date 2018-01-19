package projetaobcc20172.com.projetopetemfoco.avaliacaoTest;

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
public class UnitTestAvaliacao {

    @RunWith(Parameterized.class)
    public class UnitTestCadastroEndereco {

        @Parameterized.Parameter
        public Avaliacao endTest;

        @Parameterized.Parameters
        public Iterable<?> data() {

            String mIdUsuario = "05252878745878745" ;
            String mNomeUsuario = "MMMMMMM GGGGGG DDDD" ;
            String mEstrelas = "5" ;
            String mCometario = "Excelente Estabelecimento" ;

            return Arrays.asList(
                    new Avaliacao("", "", "", ""),
                    new Avaliacao(mIdUsuario, mNomeUsuario, "", ""),
                    new Avaliacao(mIdUsuario, mNomeUsuario, mEstrelas, ""),
                    new Avaliacao(mIdUsuario, mNomeUsuario, mEstrelas, mCometario),
                    new Avaliacao(mIdUsuario, "", mEstrelas, ""),
                    new Avaliacao(mIdUsuario, "", mEstrelas, mCometario),
                    new Avaliacao(mIdUsuario, "", "", mCometario),
                    new Avaliacao("", mNomeUsuario, "", ""),
                    new Avaliacao("", mNomeUsuario, mEstrelas, ""),
                    new Avaliacao("", mNomeUsuario, mEstrelas, mCometario),
                    new Avaliacao("", "", mEstrelas, ""),
                    new Avaliacao("", "", mEstrelas, mCometario),
                    new Avaliacao("", "", "", mCometario),
                    new Avaliacao("", mNomeUsuario, "", mCometario)
                    );
        }

        @Test(expected = CampoObrAusenteException.class)
        public void testCampoObg() throws CampoObrAusenteException {
            VerificadorDeObjetos.vDadosObjAvaliacao(endTest);
        }

    }
}