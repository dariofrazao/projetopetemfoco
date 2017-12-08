package projetaobcc20172.com.projetopetemfoco.cadastrotest;

import android.support.test.espresso.Espresso;
import android.support.test.espresso.action.ViewActions;
import android.support.test.espresso.intent.Intents;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.rule.ActivityTestRule;

import org.junit.After;
import org.junit.Rule;
import org.junit.Test;

import projetaobcc20172.com.projetopetemfoco.R;
import projetaobcc20172.com.projetopetemfoco.activity.CadastroPetActivity;
import projetaobcc20172.com.projetopetemfoco.activity.MainActivity;

import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static android.support.test.espresso.matcher.RootMatchers.withDecorView;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;

/**
 * Created by dario on 07/12/17.
 * Essa classe é responsavel por executar os testes automatizados
 * Para o cadastro de um pet
 */

public class CadastroPetActivityTest{

    private String nomePet = "Billie";
    private String raça = "Bulldog";

    @Rule
    public ActivityTestRule<CadastroPetActivity> cadastroPetActivityRule = new ActivityTestRule<>(CadastroPetActivity.class);

    public void preencherEClicar(String nomePet,String raça){
        //preenche o campo de nome com o texto do "nomePet"
        Espresso.onView(ViewMatchers.withId(R.id.editText_nome_pet)).perform(ViewActions.typeText(nomePet));
        //Fecha o teclado
        Espresso.closeSoftKeyboard();
        Espresso.onView(ViewMatchers.withId(R.id.editText_raca_pet)).perform(ViewActions.typeText(raça));
        Espresso.closeSoftKeyboard();
        Espresso.onView(ViewMatchers.withId(R.id.botao_cadastrar_pet)).perform(ViewActions.click());
    }

    //Teste que simula um cadastro do pet com o campo nome em branco
    @Test
    public void testeNomeEmBranco(){
        Espresso.onView(ViewMatchers.withId(R.id.editText_raca_pet)).perform(ViewActions.typeText(this.nomePet));
        Espresso.closeSoftKeyboard();
        Espresso.onView(ViewMatchers.withId(R.id.botao_cadastrar_pet)).perform(ViewActions.click());
        Espresso.onView(ViewMatchers.withText(R.string.erro_cadastro_campos_obrigatorios_Pet)).inRoot(withDecorView(not(is(cadastroPetActivityRule.getActivity().getWindow().getDecorView())))).check(matches(isDisplayed()));
    }

    //Teste que simula um cadastro do pet com os campos em branco
    @Test
    public void testeCamposEmBranco(){
        Espresso.onView(ViewMatchers.withId(R.id.botao_cadastrar_pet)).perform(ViewActions.click());
        Espresso.onView(ViewMatchers.withText(R.string.erro_cadastro_campos_obrigatorios_Pet)).inRoot(withDecorView(not(is(cadastroPetActivityRule.getActivity().getWindow().getDecorView())))).check(matches(isDisplayed()));
    }

    //Teste que simula um cadastro do pet com todos os campos preenchidos
    @Test
    public void testeCadastrarPet(){
        Intents.init();
        this.preencherEClicar(this.nomePet, this.raça);
        Espresso.onView(ViewMatchers.withText(R.string.sucesso_cadastro_Pet)).inRoot(withDecorView(not(is(cadastroPetActivityRule.getActivity().getWindow().getDecorView())))).check(matches(isDisplayed()));
        //Verifica se houve mudança de activity(mudou a tela)
        intended(hasComponent(MainActivity.class.getName()));
        Intents.release();
    }

    @After
    public void tearDown() throws Exception {
        Thread.sleep(2000);
    }
}
