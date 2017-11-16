package projetaobcc20172.com.projetopetemfoco.activity.cadastroTest;

import android.support.test.espresso.Espresso;
import android.support.test.espresso.action.ViewActions;
import android.support.test.espresso.intent.Intents;
import android.support.test.espresso.matcher.ViewMatchers;

import org.hamcrest.CoreMatchers;
import org.junit.Test;
import projetaobcc20172.com.projetopetemfoco.R;
import projetaobcc20172.com.projetopetemfoco.activity.LoginActivity;

import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static android.support.test.espresso.matcher.RootMatchers.withDecorView;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static org.hamcrest.CoreMatchers.not;

/**
 * Created by raul on 11/11/17.
 * Essa classe é responsavel por executar os testes automatizados
 * Para o cadastro de um usuário
 */

public class CadastroUsuarioActivityTest extends CadastroUsuarioTestMae{

    @Test
    public void testeCadastroCampoAusente(){
        //Escreve no campo de e-mail o conteúdo do atributo "emailJaUtilizado"
        Espresso.onView(ViewMatchers.withId(R.id.editText_email)).perform(ViewActions.typeText(this.emailJaUtilizado));
        Espresso.closeSoftKeyboard();//Fecha o teclado
        Espresso.onView(ViewMatchers.withId(R.id.botao_cadastrar)).perform(ViewActions.click());//Clica no botão cadastrar
        //Verifica se a mensagem que aparece no Toast é a correta
        Espresso.onView(ViewMatchers.withText(R.string.erro_cadastro_campos_obrigatorios_Toast)).inRoot(withDecorView(not(CoreMatchers.is(cadastroActivityRule.getActivity().getWindow().getDecorView())))).check(matches(isDisplayed()));

    }
    //Teste que simula um cadastro com e-mail Inválido
    @Test
    public void testeCadastroEmailInvalido(){
        preencherEClicarCadastro(this.nome,this.emailInvalido,this.senha,this.senha);
        Espresso.onView(ViewMatchers.withText(R.string.erro_cadastro_email_invalido_Toast)).inRoot(withDecorView(not(CoreMatchers.is(cadastroActivityRule.getActivity().getWindow().getDecorView())))).check(matches(isDisplayed()));
    }

    @Test
    public void testeEmailJaCadastrado(){
        preencherEClicarCadastro(this.nome,this.emailJaUtilizado,this.senha,this.senha);
        Espresso.onView(ViewMatchers.withText(R.string.erro_cadastro_email_usado_Toast)).inRoot(withDecorView(not(CoreMatchers.is(cadastroActivityRule.getActivity().getWindow().getDecorView())))).check(matches(isDisplayed()));
    }
    //Teste que simula um cadastro com senha e senha de confirmação diferentes
    @Test
    public void testeCadastrarSenhasDif(){
        preencherEClicarCadastro(this.nome,this.email,this.senha,this.senha2);
        Espresso.onView(ViewMatchers.withText(R.string.erro_cadastro_senhas_diferentes_Toast)).inRoot(withDecorView(not(CoreMatchers.is(cadastroActivityRule.getActivity().getWindow().getDecorView())))).check(matches(isDisplayed()));

    }

    @Test
    public void testeCadastrar(){
        Intents.init();
        preencherEClicarCadastro(this.nome,this.email,this.senha,this.senha);
        Espresso.onView(ViewMatchers.withText(R.string.sucesso_cadastro_Toast)).inRoot(withDecorView(not(CoreMatchers.is(cadastroActivityRule.getActivity().getWindow().getDecorView())))).check(matches(isDisplayed()));
        intended(hasComponent(LoginActivity.class.getName()));

    }

}