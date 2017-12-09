package projetaobcc20172.com.projetopetemfoco.test.cadastrousuariotest;

import android.support.test.espresso.Espresso;
import android.support.test.espresso.action.ViewActions;
import android.support.test.espresso.matcher.ViewMatchers;

import java.util.Random;

import projetaobcc20172.com.projetopetemfoco.R;
import projetaobcc20172.com.projetopetemfoco.test.TestTools;


/**
 * Created by raul on 16/11/17.
 * Classe que existe para ser herdade pelas outras classes de teste para cadastrp
 * ele guarda os atributos que são utlizados assim como também os métodos after e before
 */

public class TestToolsCadUser {

    //Metodo que preenche os campos e clica no botao de cadastrar
    protected static void preencherEclicar(String nome, String email, String senha1, String senha2){
        Espresso.onView(ViewMatchers.withId(R.id.editText_nome)).perform(ViewActions.typeText(nome));
        Espresso.closeSoftKeyboard();
        Espresso.onView(ViewMatchers.withId(R.id.editText_email)).perform(ViewActions.typeText(email));
        Espresso.closeSoftKeyboard();
        Espresso.onView(ViewMatchers.withId(R.id.editText_senha)).perform(ViewActions.typeText(senha1));
        Espresso.closeSoftKeyboard();
        Espresso.onView(ViewMatchers.withId(R.id.editText_senha2)).perform(ViewActions.typeText(senha2));
        Espresso.closeSoftKeyboard();
        TestTools.clicarBotao(R.id.botao_cadastrar_endereco);
    }


   //Gera um email aleatorio para realizar cadastro
   protected static String gerarEmailTeste(int num){
       int x;
       Random ran = new Random();
       String emailInicio = "teste";
       String emailFim = "@gmail.com";
       for(int i=0;i<=num;i=i+1){
           x = ran.nextInt(50);
           emailInicio = emailInicio + x;
       }
       return emailInicio+emailFim;
   }
}
