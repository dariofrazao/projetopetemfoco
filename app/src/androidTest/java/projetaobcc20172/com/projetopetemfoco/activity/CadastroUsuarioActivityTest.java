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
 * Essa classe é responsavel por executar os testes automatizados
 * Para o cadastro de um usuário
 */
public class CadastroUsuarioActivityTest {
    //Essas variaveis guardam valores que são preenchidos nos campos
    //Existentes na tela de cadastro
    private String nome = "Teste"; //nome do usuário
    private String nomeInvalido = "*?dadsa21312";//nome que será utilizado no teste de nome inválido
    private String emailJaUtilizado = "raulpedrouag@gmail.com";
    private String emailInvalido = "teste@231234gemeiul.com.br2";
    private String email = "luar.pedro@yahoo.com.br";
    private String senha = "12345e";
    private String senha2 = "12345ew1";

    //Atributo que identifca qual activity seŕa testada
    @Rule
    public ActivityTestRule<CadastroUsuarioActivity> cadastroActivityRule = new ActivityTestRule<>(CadastroUsuarioActivity.class);

    //método que executa algo antes de iniciar os testes.
    @Before
    public void setUp() throws Exception {
    }

    //Método que apaga os text box preenchidos para deixa-los limpos
    //para a execução de 2 ou mais testes consecutivos
    @Test
    public void limparCampos(){
        Espresso.onView(ViewMatchers.withId(R.id.editText_nome)).perform(clearText());//apaga o texto no campo de nome
        Espresso.onView(ViewMatchers.withId(R.id.editText_email)).perform(clearText());
        Espresso.onView(ViewMatchers.withId(R.id.editText_senha)).perform(clearText());
        Espresso.onView(ViewMatchers.withId(R.id.editText_nome)).perform(clearText());
        Espresso.onView(ViewMatchers.withId(R.id.editText_senha2)).perform(clearText());
        cadastroActivityRule.getActivity().mToast.cancel();
    }
    //Teste simula um cadastro com algum campo ausente
    @Test
    public void testeCadastroCampoAusente(){
        //Escreve no campo de e-mail o conteúdo do atributo "emailJaUtilizado"
        Espresso.onView(ViewMatchers.withId(R.id.editText_email)).perform(ViewActions.typeText(this.emailJaUtilizado));
        Espresso.closeSoftKeyboard();//Fecha o teclado
        Espresso.onView(ViewMatchers.withId(R.id.botao_cadastrar)).perform(ViewActions.click());//Clica no botão cadastrar
        //Verifica se a mensagem que aparece no Toast é a correta
        Espresso.onView(ViewMatchers.withText(R.string.erro_cadastro_campos_obrigatorios_Toast)).inRoot(withDecorView(not(is(cadastroActivityRule.getActivity().getWindow().getDecorView())))).check(matches(isDisplayed()));
        this.limparCampos();
    }
    //Teste que simula um cadastro com e-mail Inválido
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
    //Teste que simula um cadastro com senha e senha de confirmação diferentes
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
    //Esse Método executa todos os testes criados anteriormente
    //Validando o funcionamento total da tela de cadastro
    @Test
    public void realizarTestes(){
        this.testeCadastrarSenhasDif();
        this.testeCadastroCampoAusente();
        //this.testeCadastroEmailInvalido();
        this.testeEmailJaCadastrado();
        //this.testeCadastrar();
    }
    //Método que é executado após terminar os teste.
    @After
    public void tearDown() throws Exception {
        //this.limparCampos();
        //deixa o app parado por 5 segundos
        //Para acomponhar se a mudança de tela ocorreu corretamente
        Thread.sleep(5000);
    }


}