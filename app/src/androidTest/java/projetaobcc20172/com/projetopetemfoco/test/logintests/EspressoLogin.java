package projetaobcc20172.com.projetopetemfoco.test.logintests;

import android.support.test.espresso.Espresso;
import android.support.test.espresso.action.ViewActions;
import android.support.test.espresso.matcher.ViewMatchers;

import projetaobcc20172.com.projetopetemfoco.R;

/**
 * Created by raul on 08/11/17.
 * Classe que armazena os métodos que interagem com a tela de login para realizar os teste.
 */
public class EspressoLogin {


    public static void preencher(String login,String senha){
        //preenche o campo de e-mail com o texto do "loginInvalido"
        Espresso.onView(ViewMatchers.withId(R.id.editText_email)).perform(ViewActions.typeText(login));
        //Fecha o teclado
        Espresso.closeSoftKeyboard();
        Espresso.onView(ViewMatchers.withId(R.id.editText_senha)).perform(ViewActions.typeText(senha));
        Espresso.closeSoftKeyboard();
    }

    public static void clicarLogin(){
        Espresso.onView(ViewMatchers.withId(R.id.botao_login)).perform(ViewActions.click());
    }


}