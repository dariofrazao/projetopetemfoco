package projetaobcc20172.com.projetopetemfoco.test.logintests;

import android.support.test.espresso.intent.Intents;
import android.support.test.rule.ActivityTestRule;

import org.junit.After;
import org.junit.Rule;
import org.junit.Test;

import projetaobcc20172.com.projetopetemfoco.R;
import projetaobcc20172.com.projetopetemfoco.activity.LoginActivity;
import projetaobcc20172.com.projetopetemfoco.activity.MainActivity;
import projetaobcc20172.com.projetopetemfoco.test.TestTools;

/**
 * Created by raul on 10/11/17.
 * Classe que executa os testes automatizados para login
 */


public class LoginActivityTest {

    private String loginInvalido = "joaoNaoCadastrado@gmail.com";
    private String senhaInvalida = "senhaInvalida";
    private String loginValido = "raulpedrouag@gmail.com";
    private String senhaValida = "123456e";

    @Rule
    public ActivityTestRule <LoginActivity> loginActivityRule = new ActivityTestRule<>(LoginActivity.class);

    //Teste que simula uma tentatica de login com um cadastro não existente
    @Test
    public void testeLoginNaoCadastrado() {
        EspressoLogin.preencher(this.loginInvalido,this.senhaValida);
        EspressoLogin.clicarLogin();
        TestTools.checarToast(R.string.erro_login_invalido_Toast,loginActivityRule.getActivity());
    }

    //Teste que simula um login com todos os campos de texto em branco
    @Test
    public void testeCamposEmBranco(){
        EspressoLogin.preencher("","");
        EspressoLogin.clicarLogin();
        TestTools.checarToast(R.string.erro_login_invalido_Toast,loginActivityRule.getActivity());
    }
    //Teste que simula um login com o campo de senha em branco
    @Test
    public void testeSenhaEmBranco(){
        EspressoLogin.preencher(this.loginValido,"");
        EspressoLogin.clicarLogin();
        TestTools.checarToast(R.string.erro_login_invalido_Toast,loginActivityRule.getActivity());
    }

    @Test
    public void testeSenhaInvalida(){
        EspressoLogin.preencher(this.loginValido,this.senhaInvalida);
        EspressoLogin.clicarLogin();
        TestTools.checarToast(R.string.erro_login_invalido_Toast,loginActivityRule.getActivity());
    }

    @Test
    public void testeUsuarioCadastrado(){
        Intents.init();//Para realizar teste de mudança dela deve-se sempre add esse método no começo do método
        EspressoLogin.preencher(this.loginValido,this.senhaValida);
        EspressoLogin.clicarLogin();
        TestTools.verificarMudancaActivity(MainActivity.class.getName());
    }

    @After
    public void tearDown() throws Exception {
        Thread.sleep(2000);
    }
}