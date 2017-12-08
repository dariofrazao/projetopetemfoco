package projetaobcc20172.com.projetopetemfoco.cadastrotest;

import android.support.test.espresso.Espresso;
import android.support.test.espresso.action.ViewActions;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.rule.ActivityTestRule;

import org.junit.After;
import org.junit.Rule;
import org.junit.Test;

import projetaobcc20172.com.projetopetemfoco.R;
import projetaobcc20172.com.projetopetemfoco.activity.CadastroServicoActivity;

import static junit.framework.Assert.fail;

/**
 * Classe que testa a tela de cadastro de serviços.
 * Created by Felipe Oliveira on 08/12/17.
 * <flpdias14@gmail.com>
 */

public class CadastroServicoActivityTest {

    private String mNome = "Tosa Canina";
    private String mValor = "50";
    private String mDescricao = "Tosa para cães de todos os portes e raças.";

    @Rule
    public ActivityTestRule<CadastroServicoActivity> cadastroServicoActivityRule = new ActivityTestRule<>(CadastroServicoActivity.class);

    /**
     * Faz o preenchimento dos campos na view, e clica no botão de salvar.
     * @param nome nome a ser atribuído ao serviço.
     * @param valor valor a ser atribuído ao serviço.
     * @param descicao descricao a ser atribuída ao serviço.
     */
    @Test
    public void preencherEClicarCadastro(String nome,String valor,String descicao){
        Espresso.onView(ViewMatchers.withId(R.id.etCadastroNomeServico)).perform(ViewActions.typeText(nome));
        Espresso.closeSoftKeyboard();
        Espresso.onView(ViewMatchers.withId(R.id.etCadastroValorServico)).perform(ViewActions.typeText(valor));
        Espresso.closeSoftKeyboard();
        Espresso.onView(ViewMatchers.withId(R.id.etCadastroDescricaoServico)).perform(ViewActions.typeText(descicao));
        Espresso.closeSoftKeyboard();
        Espresso.onView(ViewMatchers.withId(R.id.btnSalvarServico)).perform(ViewActions.click());

    }

    @After
    public void tearDown() throws Exception {
        Thread.sleep(5000);
    }

    public void testeCampoNomeNaoInformado(){
        preencherEClicarCadastro("", this.mValor, this.mDescricao);
        fail("Não implementado");
    }

    public void testeTodosCamposInformados() {
        preencherEClicarCadastro(this.mNome, this.mValor, this.mDescricao);
        fail("Não implementado");
    }

}
