package projetaobcc20172.com.projetopetemfoco.logintests;

import android.support.test.espresso.Espresso;
import android.support.test.espresso.action.ViewActions;
import android.support.test.espresso.intent.Intents;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.rule.ActivityTestRule;


import org.junit.After;
import org.junit.Rule;
import org.junit.Test;

import projetaobcc20172.com.projetopetemfoco.R;
import projetaobcc20172.com.projetopetemfoco.activity.LoginActivity;
import projetaobcc20172.com.projetopetemfoco.activity.MainActivity;

import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static android.support.test.espresso.matcher.RootMatchers.withDecorView;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;

/**
 * Created by raul on 10/11/17.
 * Classe que realiza os testes automatizados para login
 */
public class LoginActivityTest {

    private String loginInvalido = "joaoNaoCadastrado@gmail.com";
    private String senhaInvalida = "senhaInvalida";
    private String loginValido = "raulpedrouag@gmail.com";
    private String senhaValida = "123456e";


    @Rule
    public ActivityTestRule<LoginActivity> loginActivityRule = new ActivityTestRule<>(LoginActivity.class);

    public void preencherEClicar(String login,String senha){
        //preenche o campo de e-mail com o texto do "loginInvalido"
        Espresso.onView(ViewMatchers.withId(R.id.editText_email)).perform(ViewActions.typeText(login));
        //Fecha o teclado
        Espresso.closeSoftKeyboard();
        Espresso.onView(ViewMatchers.withId(R.id.editText_senha)).perform(ViewActions.typeText(senha));
        Espresso.closeSoftKeyboard();
        Espresso.onView(ViewMatchers.withId(R.id.botao_login)).perform(ViewActions.click());
    }
/*
    @Before
    public void setUp() throws Exception {
        Thread.sleep(2000);
    }
    */

    //Teste que simula uma tentatica de login com um cadastro não existente
    @Test
    public void testeLoginNaoCadastrado() {
        this.preencherEClicar(this.loginInvalido,this.senhaInvalida);
        Espresso.onView(ViewMatchers.withText(R.string.erro_login_invalido_Toast)).inRoot(withDecorView(not(is(loginActivityRule.getActivity().getWindow().getDecorView())))).check(matches(isDisplayed()));
    }
    //Teste que simula um login com todos os campos de texto em branco
    @Test
    public void testeCamposEmBranco(){
        Espresso.onView(ViewMatchers.withId(R.id.botao_login)).perform(ViewActions.click());
        Espresso.onView(ViewMatchers.withText(R.string.erro_login_invalido_Toast)).inRoot(withDecorView(not(is(loginActivityRule.getActivity().getWindow().getDecorView())))).check(matches(isDisplayed()));

    }
    //Teste que simula um login com o campo de senha em branco
    @Test
    public void testeSenhaEmBranco(){
        Espresso.onView(ViewMatchers.withId(R.id.editText_email)).perform(ViewActions.typeText(this.loginValido));
        Espresso.closeSoftKeyboard();
        Espresso.onView(ViewMatchers.withId(R.id.botao_login)).perform(ViewActions.click());
        Espresso.onView(ViewMatchers.withText(R.string.erro_login_invalido_Toast)).inRoot(withDecorView(not(is(loginActivityRule.getActivity().getWindow().getDecorView())))).check(matches(isDisplayed()));

    }

    @Test
    public void testeSenhaInvalida(){
        this.preencherEClicar(this.loginValido,this.senhaInvalida);
        Espresso.onView(ViewMatchers.withText(R.string.erro_login_invalido_Toast)).inRoot(withDecorView(not(is(loginActivityRule.getActivity().getWindow().getDecorView())))).check(matches(isDisplayed()));
    }

    @Test
    public void testeUsuarioCadastrado(){
        Intents.init();
        this.preencherEClicar(this.loginValido,this.senhaValida);
        Espresso.onView(ViewMatchers.withText(R.string.sucesso_login_Toast)).inRoot(withDecorView(not(is(loginActivityRule.getActivity().getWindow().getDecorView())))).check(matches(isDisplayed()));
        //Verifica se houve mudança de activity(mudou a tela)
        intended(hasComponent(MainActivity.class.getName()));
        Intents.release();
    }

    @After
    public void tearDown() throws Exception {
        Thread.sleep(2000);
    }
}