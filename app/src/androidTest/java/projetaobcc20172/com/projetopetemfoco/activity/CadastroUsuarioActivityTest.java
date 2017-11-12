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
 * Created by raul on 11/11/17.
 */
public class CadastroUsuarioActivityTest {

    private String nome = "Teste";
    private String nomeInvalido = "*?dadsa21312";
    private String emailJaUtilizado = "raulpedrouag@gmail.com";
    private String emailInvalido = "teste@231234gemeiul.com.br2";
    private String email = "luar.pedro@yahoo.com.br";
    private String senha = "12345e";
    private String senha2 = "12345ew1";


    @Rule
    public ActivityTestRule<CadastroUsuarioActivity> cadastroActivityRule = new ActivityTestRule<>(CadastroUsuarioActivity.class);

    @Before
    public void setUp() throws Exception {
    }

    @Test
    public void limparCampos(){
        Espresso.onView(ViewMatchers.withId(R.id.editText_nome)).perform(clearText());
        Espresso.onView(ViewMatchers.withId(R.id.editText_email)).perform(clearText());
        Espresso.onView(ViewMatchers.withId(R.id.editText_senha)).perform(clearText());
        Espresso.onView(ViewMatchers.withId(R.id.editText_nome)).perform(clearText());
        Espresso.onView(ViewMatchers.withId(R.id.editText_senha2)).perform(clearText());
        cadastroActivityRule.getActivity().mToast.cancel();
    }

    @Test
    public void testeCadastroCampoAusente(){
        Espresso.onView(ViewMatchers.withId(R.id.editText_email)).perform(ViewActions.typeText(this.emailJaUtilizado));
        Espresso.closeSoftKeyboard();
        Espresso.onView(ViewMatchers.withId(R.id.botao_cadastrar)).perform(ViewActions.click());
        Espresso.onView(ViewMatchers.withText(R.string.erro_cadastro_campos_obrigatorios_Toast)).inRoot(withDecorView(not(is(cadastroActivityRule.getActivity().getWindow().getDecorView())))).check(matches(isDisplayed()));
        this.limparCampos();
    }

    @Test
    public void testeCadastroEmailInvalido(){
        Espresso.onView(ViewMatchers.withId(R.id.editText_nome)).perform(ViewActions.typeText(this.nome));
        Espresso.closeSoftKeyboard();
        Espresso.onView(ViewMatchers.withId(R.id.editText_email)).perform(ViewActions.typeText(this.emailInvalido));
        Espresso.closeSoftKeyboard();
        Espresso.onView(ViewMatchers.withId(R.id.editText_senha)).perform(ViewActions.typeText(this.senha));
        Espresso.closeSoftKeyboard();
        Espresso.onView(ViewMatchers.withId(R.id.editText_senha2)).perform(ViewActions.typeText(this.senha));
        Espresso.closeSoftKeyboard();
        Espresso.onView(ViewMatchers.withId(R.id.botao_cadastrar)).perform(ViewActions.click());
        Espresso.onView(ViewMatchers.withText(R.string.erro_cadastro_email_invalido_Toast)).inRoot(withDecorView(not(is(cadastroActivityRule.getActivity().getWindow().getDecorView())))).check(matches(isDisplayed()));
        this.limparCampos();
    }


    @Test
    public void testeEmailJaCadastrado(){
        Espresso.onView(ViewMatchers.withId(R.id.editText_nome)).perform(ViewActions.typeText(this.nome));
        Espresso.closeSoftKeyboard();
        Espresso.onView(ViewMatchers.withId(R.id.editText_email)).perform(ViewActions.typeText(this.emailJaUtilizado));
        Espresso.closeSoftKeyboard();
        Espresso.onView(ViewMatchers.withId(R.id.editText_senha)).perform(ViewActions.typeText(this.senha));
        Espresso.closeSoftKeyboard();
        Espresso.onView(ViewMatchers.withId(R.id.editText_senha2)).perform(ViewActions.typeText(this.senha));
        Espresso.closeSoftKeyboard();
        Espresso.onView(ViewMatchers.withId(R.id.botao_cadastrar)).perform(ViewActions.click());
        Espresso.onView(ViewMatchers.withText(R.string.erro_cadastro_email_usado_Toast)).inRoot(withDecorView(not(is(cadastroActivityRule.getActivity().getWindow().getDecorView())))).check(matches(isDisplayed()));
        this.limparCampos();
    }

    @Test
    public void testeCadastrarSenhasDif(){
        Espresso.onView(ViewMatchers.withId(R.id.editText_nome)).perform(ViewActions.typeText(this.nome));
        Espresso.closeSoftKeyboard();
        Espresso.onView(ViewMatchers.withId(R.id.editText_email)).perform(ViewActions.typeText(this.email));
        Espresso.closeSoftKeyboard();
        Espresso.onView(ViewMatchers.withId(R.id.editText_senha)).perform(ViewActions.typeText(this.senha));
        Espresso.closeSoftKeyboard();
        Espresso.onView(ViewMatchers.withId(R.id.editText_senha2)).perform(ViewActions.typeText(this.senha2));
        Espresso.closeSoftKeyboard();
        Espresso.onView(ViewMatchers.withId(R.id.botao_cadastrar)).perform(ViewActions.click());
        Espresso.onView(ViewMatchers.withText(R.string.erro_cadastro_senhas_diferentes_Toast)).inRoot(withDecorView(not(is(cadastroActivityRule.getActivity().getWindow().getDecorView())))).check(matches(isDisplayed()));
        this.limparCampos();
    }

    @Test
    public void testeCadastrar(){
        Intents.init();
        Espresso.onView(ViewMatchers.withId(R.id.editText_nome)).perform(ViewActions.typeText(this.nome));
        Espresso.closeSoftKeyboard();
        Espresso.onView(ViewMatchers.withId(R.id.editText_email)).perform(ViewActions.typeText(this.email));
        Espresso.closeSoftKeyboard();
        Espresso.onView(ViewMatchers.withId(R.id.editText_senha)).perform(ViewActions.typeText(this.senha));
        Espresso.closeSoftKeyboard();
        Espresso.onView(ViewMatchers.withId(R.id.editText_senha2)).perform(ViewActions.typeText(this.senha));
        Espresso.closeSoftKeyboard();
        Espresso.onView(ViewMatchers.withId(R.id.botao_cadastrar)).perform(ViewActions.click());
        Espresso.onView(ViewMatchers.withText(R.string.sucesso_cadastro_Toast)).inRoot(withDecorView(not(is(cadastroActivityRule.getActivity().getWindow().getDecorView())))).check(matches(isDisplayed()));
        intended(hasComponent(LoginActivity.class.getName()));

    }
    @Test
    public void realizarTestes(){
        this.testeCadastrarSenhasDif();
        this.testeCadastroCampoAusente();
        //this.testeCadastroEmailInvalido();
        this.testeEmailJaCadastrado();
        //this.testeCadastrar();
    }

    @After
    public void tearDown() throws Exception {
        //this.limparCampos();
        Thread.sleep(5000);
    }


}