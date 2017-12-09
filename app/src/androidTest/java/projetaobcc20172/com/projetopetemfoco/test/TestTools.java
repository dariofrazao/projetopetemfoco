package projetaobcc20172.com.projetopetemfoco.test;

import android.app.Activity;
import android.support.test.espresso.Espresso;
import android.support.test.espresso.intent.Intents;
import android.support.test.espresso.matcher.ViewMatchers;

import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static android.support.test.espresso.matcher.RootMatchers.withDecorView;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;

/**
 * Created by raul on 06/12/17.
 * Essa classe é responsavel por armazenar os métodos que são utilizados nos testes do espresso
 * que independem da activity que está sendo utilizada.
 */

public class TestTools {
    //Verifica se a mensagem do toast é a correta
    public static void checarToast(int rMsg,Activity act){
        Espresso.onView(ViewMatchers.withText(act.getResources().getString(rMsg))).inRoot(withDecorView(not(is(act.getWindow().getDecorView())))).check(matches(isDisplayed()));
    }
    //Verifica se a mudança de activity(tela) ocorreu como esperada
    public static void verificarMudancaActivity(String nomeActivity){
        intended(hasComponent(nomeActivity));
        Intents.release();
    }
}
