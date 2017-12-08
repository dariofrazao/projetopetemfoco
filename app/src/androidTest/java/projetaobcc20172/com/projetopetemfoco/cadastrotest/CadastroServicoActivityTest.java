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
/**
 * Classe que realiza testes de cadastro de serviço.
 * Created by Felipe Oliveira on 08/12/17.
 * <flpdias14@gmail.com>
 */

public class CadastroServicoActivityTest {
    private String mNome = "Banho";
    private String mDescricao = "Banho quente para gatos que não gostam de água fria.";
    private String mValor = "30.0";
    @Rule
    public ActivityTestRule<CadastroServicoActivity> mCadastroServicoActivityTestRule =
            new ActivityTestRule<>(CadastroServicoActivity.class);

    /**
     * Faz a ação de preencher os campos e a ação de clicar no botão.
     * @param nome nome a ser atribuído ao serviço.
     * @param valor valor do serviço.
     * @param descricao descricao do servico.
     */
    private void preencherCamposEClicar(String nome, String valor, String descricao){
        //Preenche o campo de nome do serviço com o texto do nome.
        Espresso.onView(ViewMatchers.withId(R.id.etCadastroNomeServico)).perform(ViewActions.typeText(nome));
        //Comando para fechar o teclado.
        Espresso.closeSoftKeyboard();
        Espresso.onView(ViewMatchers.withId(R.id.etCadastroValorServico)).perform(ViewActions.typeText(valor));
        Espresso.closeSoftKeyboard();
        Espresso.onView(ViewMatchers.withId(R.id.etCadastroDescricaoServico)).perform(ViewActions.typeText(descricao));
        Espresso.closeSoftKeyboard();
        Espresso.onView(ViewMatchers.withId(R.id.btnSalvarServico)).perform(ViewActions.click());
    }

    @Test
    public void testeCadastroNomeAusente(){
        preencherCamposEClicar("", this.mValor, this.mDescricao);
    }

    @After
    public void tearDown() throws Exception {
        Thread.sleep(2000);
    }
}
