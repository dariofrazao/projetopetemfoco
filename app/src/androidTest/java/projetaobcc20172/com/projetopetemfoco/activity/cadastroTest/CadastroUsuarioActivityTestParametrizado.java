
package projetaobcc20172.com.projetopetemfoco.activity.cadastroTest;

import android.support.test.espresso.Espresso;
import android.support.test.espresso.matcher.ViewMatchers;

import org.hamcrest.CoreMatchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import java.util.Arrays;
import java.util.Collection;
import projetaobcc20172.com.projetopetemfoco.R;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.RootMatchers.withDecorView;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;

/**
 * Created by raul on 11/11/17.
 * Essa classe é responsavel por executar os testes automatizados
 * Para o cadastro de um usuário
 */
/*
@RunWith(Parameterized.class)
public class CadastroUsuarioActivityTestParametrizado extends CadastroUsuarioTestMae {
    //Essas variaveis guardam valores que são preenchidos nos campos
    //Existentes na tela de cadastro
    private String nome = "Teste"; //nome do usuário
    @Parameterized.Parameter(0)
    public String nomeInvalido;
*/

    /*Pametros que são serão utilizados nos teste. Cada parametro desse
    faz com o teste seja executado uma vez.
    */
    /*
    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][]{{"*nome"}, {"?nome"}, {"$nome"}, {"&nome"}, {"(nome"}, {"{nome"}});
    }

    @Test
    public void testeNomeInvalido(){
        preencherEClicarCadastro(this.nomeInvalido,this.email,this.senha,this.senha);
        Espresso.onView(ViewMatchers.withText(R.string.erro_cadastro_nome_usuario_Toast)).inRoot(withDecorView(not(CoreMatchers.is(cadastroActivityRule.getActivity().getWindow().getDecorView())))).check(matches(isDisplayed()));
    }



}
    */