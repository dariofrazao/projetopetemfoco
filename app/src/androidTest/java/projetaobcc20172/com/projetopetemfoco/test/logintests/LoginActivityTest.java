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

    private static String loginInvalido = "joaoNaoCadastrado@gmail.com";
    private static String senhaInvalida = "senhaInvalida";
    private static String loginValido = "raulpedrouag@gmail.com";
    private static String senhaValida = "123456";

    @Rule
    public ActivityTestRule <LoginActivity> loginActivityRule = new ActivityTestRule<>(LoginActivity.class);

    //Teste que simula uma tentatica de login com um cadastro não existente
    @Test
    public void testeLoginNaoCadastrado() {
        TestToolsLogin.preencherEclicar(this.loginInvalido,this.senhaValida);
        TestTools.checarToast(R.string.erro_login_invalido_Toast);
    }

    //Teste que simula um login com todos os campos de texto em branco
    @Test
    public void testeCamposEmBranco(){
        TestToolsLogin.preencherEclicar("","");
        TestTools.checarToast(R.string.erro_login_invalido_Toast);
    }
    //Teste que simula um login com o campo de senha em branco
    @Test
    public void testeSenhaEmBranco(){
        TestToolsLogin.preencherEclicar(this.loginValido,"");
        TestTools.checarToast(R.string.erro_login_invalido_Toast);
    }

    @Test
    public void testeSenhaInvalida(){
        TestToolsLogin.preencherEclicar(this.loginValido,this.senhaInvalida);
        TestTools.checarToast(R.string.erro_login_invalido_Toast);
    }

    @Test
    public void testeUsuarioCadastrado(){
        Intents.init();//Para realizar teste de mudança dela deve-se sempre add esse método no começo do método
        TestToolsLogin.preencherEclicar(this.loginValido,this.senhaValida);
        TestTools.verificarMudancaActivity(MainActivity.class.getName());
        TestTools.checarToast(R.string.sucesso_login_Toast);
        TestTools.clicarBotao(R.id.botao_sair);
    }

    @After
    public void tearDown() throws Exception {
        Thread.sleep(2000);
    }
}