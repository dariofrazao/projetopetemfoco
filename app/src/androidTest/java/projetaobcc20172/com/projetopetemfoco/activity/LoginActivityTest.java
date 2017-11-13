package projetaobcc20172.com.projetopetemfoco.activity;

import android.support.test.espresso.Espresso;
import android.support.test.espresso.action.ViewActions;
import android.support.test.espresso.intent.Intents;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.rule.ActivityTestRule;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import projetaobcc20172.com.projetopetemfoco.R;

import static android.support.test.espresso.action.ViewActions.clearText;
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
public class LoginActivityTest{

    private String loginInvalido = "joaoNaoCadastrado@gmail.com";
    private String senhaInvalida = "senhaInvalida";
    private String loginValido = "raulpedrouag@gmail.com";
    private String senhaValida = "123456e";


    @Rule
    public ActivityTestRule<LoginActivity> loginActivityRule = new ActivityTestRule<>(LoginActivity.class);

    @Before
    public void setUp() throws Exception {

    }
    //método que apaga o conteudo das caixas de texto
    //Não é um teste é só pra facilitar a execução dos teste sequencialmente
    @Test
    public void limparCampos(){
        Espresso.onView(ViewMatchers.withId(R.id.editText_email)).perform(clearText());
        Espresso.closeSoftKeyboard();
        Espresso.onView(ViewMatchers.withId(R.id.editText_senha)).perform(clearText());
        Espresso.closeSoftKeyboard();
        loginActivityRule.getActivity().getToast().cancel();
    }
    //Teste que simula uma tentatica de login com um cadastro não existente
    @Test
    public void testeLoginNaoCadastrado() {
        //preenche o campo de e-mail com o texto do "loginInvalido"
        Espresso.onView(ViewMatchers.withId(R.id.editText_email)).perform(ViewActions.typeText(this.loginInvalido));
        //Fecha o teclado
        Espresso.closeSoftKeyboard();
        Espresso.onView(ViewMatchers.withId(R.id.editText_senha)).perform(ViewActions.typeText(this.senhaInvalida));
        Espresso.closeSoftKeyboard();
        Espresso.onView(ViewMatchers.withId(R.id.botao_login)).perform(ViewActions.click());
        Espresso.onView(ViewMatchers.withText(R.string.erro_login_invalido_Toast)).inRoot(withDecorView(not(is(loginActivityRule.getActivity().getWindow().getDecorView())))).check(matches(isDisplayed()));
        this.limparCampos();
    }
    //Teste que simula um login com todos os campos de texto em branco
    @Test
    public void testeCamposEmBranco(){
        Espresso.onView(ViewMatchers.withId(R.id.botao_login)).perform(ViewActions.click());
        Espresso.onView(ViewMatchers.withText(R.string.erro_login_invalido_Toast)).inRoot(withDecorView(not(is(loginActivityRule.getActivity().getWindow().getDecorView())))).check(matches(isDisplayed()));
        this.limparCampos();
    }
    //Teste que simula um login com o campo de senha em branco
    @Test
    public void testeSenhaEmBranco(){
        Espresso.onView(ViewMatchers.withId(R.id.editText_email)).perform(ViewActions.typeText(this.loginValido));
        Espresso.closeSoftKeyboard();
        Espresso.onView(ViewMatchers.withId(R.id.botao_login)).perform(ViewActions.click());
        Espresso.onView(ViewMatchers.withText(R.string.erro_login_invalido_Toast)).inRoot(withDecorView(not(is(loginActivityRule.getActivity().getWindow().getDecorView())))).check(matches(isDisplayed()));
        this.limparCampos();
    }

    @Test
    public void testeSenhaInvalida(){
        Espresso.onView(ViewMatchers.withId(R.id.editText_email)).perform(ViewActions.typeText(this.loginValido));
        Espresso.closeSoftKeyboard();
        Espresso.onView(ViewMatchers.withId(R.id.editText_senha)).perform(ViewActions.typeText(this.senhaInvalida));
        Espresso.closeSoftKeyboard();
        Espresso.onView(ViewMatchers.withId(R.id.botao_login)).perform(ViewActions.click());
        Espresso.onView(ViewMatchers.withText(R.string.erro_login_invalido_Toast)).inRoot(withDecorView(not(is(loginActivityRule.getActivity().getWindow().getDecorView())))).check(matches(isDisplayed()));
        this.limparCampos();
    }
    @Test
    public void testeUsuarioCadastrado(){
        Intents.init();
        Espresso.onView(ViewMatchers.withId(R.id.editText_email)).perform(ViewActions.typeText(this.loginValido));
        Espresso.closeSoftKeyboard();
        Espresso.onView(ViewMatchers.withId(R.id.editText_senha)).perform(ViewActions.typeText(this.senhaValida));
        Espresso.closeSoftKeyboard();
        Espresso.onView(ViewMatchers.withId(R.id.botao_login)).perform(ViewActions.click());
        Espresso.onView(ViewMatchers.withText(R.string.sucesso_login_Toast)).inRoot(withDecorView(not(is(loginActivityRule.getActivity().getWindow().getDecorView())))).check(matches(isDisplayed()));
        //Verifica se houve mudança de activity(mudou a tela)
        intended(hasComponent(MainActivity.class.getName()));
    }

    //Executa todos os testes criados anteriormente
    @Test
    public void realizarTestes(){
        this.testeLoginNaoCadastrado();
        this.testeCamposEmBranco();
        this.testeSenhaEmBranco();
        this.testeSenhaInvalida();
        this.testeUsuarioCadastrado();
    }

    @After
    public void tearDown() throws Exception {
        Thread.sleep(5000);
    }

}