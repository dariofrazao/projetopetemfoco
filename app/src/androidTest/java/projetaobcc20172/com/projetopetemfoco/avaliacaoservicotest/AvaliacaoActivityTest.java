package projetaobcc20172.com.projetopetemfoco.avaliacaoservicotest;

import android.support.test.espresso.DataInteraction;
import android.support.test.espresso.ViewInteraction;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import projetaobcc20172.com.projetopetemfoco.R;
import projetaobcc20172.com.projetopetemfoco.TestTools;
import projetaobcc20172.com.projetopetemfoco.activity.LoginActivity;
import projetaobcc20172.com.projetopetemfoco.logintests.LoginActivityTest;
import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.Espresso.pressBack;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.replaceText;
import static android.support.test.espresso.action.ViewActions.scrollTo;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withClassName;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.anything;
import static org.hamcrest.Matchers.is;



@LargeTest
@RunWith(AndroidJUnit4.class)
public class AvaliacaoActivityTest {

    @Rule
    public ActivityTestRule<LoginActivity> mActivityTestRule = new ActivityTestRule<>(LoginActivity.class);

    @Before
    public void setUp() throws Exception {
        //Desloga caso já esteja logado.
        //Evita erros nos testes
        try{
            TestTools.clicarItemNavigationMenu(R.id.drawer_layout, R.id.nav_busca, R.id.nav_sair);
        }catch (Exception e){
            e.getMessage();
        }
    }



    @Test
    public void avaliacaoActivityTest() {
        try {
            LoginActivityTest log = new LoginActivityTest();
            log.testeLoginComSucesso();
            TestTools.clicarItemNavigationMenu(R.id.drawer_layout, R.id.nav_busca, R.id.nav_servicos);

            Thread.sleep(2000);
            DataInteraction linearLayout = onData(anything())
                    .inAdapterView(allOf(withId(R.id.gridServicos),
                            childAtPosition(
                                    withClassName(is("android.widget.LinearLayout")),
                                    0)))
                    .atPosition(1);
            linearLayout.perform(click());

            Thread.sleep(2000);
            DataInteraction relativeLayout = onData(anything())
                    .inAdapterView(allOf(withId(R.id.lvEstaServicoBusca),
                            childAtPosition(
                                    withClassName(is("android.widget.RelativeLayout")),
                                    2)))
                    .atPosition(0);
            relativeLayout.perform(click());

            Thread.sleep(2000);
            ViewInteraction appCompatButton3 = onView(
                    allOf(withId(R.id.btnAvaliarServico), withText("Avaliar Serviço"),
                            childAtPosition(
                                    childAtPosition(
                                            withClassName(is("android.widget.RelativeLayout")),
                                            1),
                                    12),
                            isDisplayed()));
            appCompatButton3.perform(click());

            Thread.sleep(2000);
            ViewInteraction appCompatEditText = onView(
                    allOf(withId(R.id.etComentarioAvaliacaoServico),
                            childAtPosition(
                                    childAtPosition(
                                            withId(R.id.scrollView),
                                            0),
                                    0)));
            appCompatEditText.perform(scrollTo(), click());

            ViewInteraction appCompatEditText2 = onView(
                    allOf(withId(R.id.etComentarioAvaliacaoServico),
                            childAtPosition(
                                    childAtPosition(
                                            withId(R.id.scrollView),
                                            0),
                                    0)));
            appCompatEditText2.perform(scrollTo(), replaceText("Comentario Novo"), closeSoftKeyboard());

            ViewInteraction appCompatEditText3 = onView(
                    allOf(withId(R.id.etComentarioAvaliacaoServico), withText("Comentario Novo"),
                            childAtPosition(
                                    childAtPosition(
                                            withId(R.id.scrollView),
                                            0),
                                    0)));
            appCompatEditText3.perform(scrollTo(), click());

            ViewInteraction appCompatEditText4 = onView(
                    allOf(withId(R.id.etComentarioAvaliacaoServico), withText("Comentario Novo"),
                            childAtPosition(
                                    childAtPosition(
                                            withId(R.id.scrollView),
                                            0),
                                    0)));
            appCompatEditText4.perform(scrollTo(), replaceText("Comentario Novo"));

            ViewInteraction appCompatEditText5 = onView(
                    allOf(withId(R.id.etComentarioAvaliacaoServico), withText("Comentario Novo"),
                            childAtPosition(
                                    childAtPosition(
                                            withId(R.id.scrollView),
                                            0),
                                    0),
                            isDisplayed()));
            appCompatEditText5.perform(closeSoftKeyboard());

            pressBack();

            ViewInteraction appCompatButton4 = onView(
                    allOf(withId(R.id.botao_avaliar_servico), withText("  Avaliar"),
                            childAtPosition(
                                    childAtPosition(
                                            withClassName(is("android.widget.LinearLayout")),
                                            0),
                                    7),
                            isDisplayed()));
            appCompatButton4.perform(click());

            ViewInteraction appCompatButton5 = onView(
                    allOf(withId(R.id.botao_avaliar_servico), withText("  Avaliar"),
                            childAtPosition(
                                    childAtPosition(
                                            withClassName(is("android.widget.LinearLayout")),
                                            0),
                                    7),
                            isDisplayed()));
            appCompatButton5.perform(click());

            Thread.sleep(2000);
            ViewInteraction appCompatButton6 = onView(
                    allOf(withId(R.id.btnAvaliacoesServico), withText("AVALIAÇÕES DO SERVIÇO"),
                            childAtPosition(
                                    childAtPosition(
                                            withClassName(is("android.widget.RelativeLayout")),
                                            1),
                                    13),
                            isDisplayed()));
            appCompatButton6.perform(click());

            Thread.sleep(2000);

            pressBack();

            Thread.sleep(2000);

            pressBack();

            Thread.sleep(2000);

            pressBack();

            Thread.sleep(2000);
            pressBack();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static Matcher<View> childAtPosition(
            final Matcher<View> parentMatcher, final int position) {

        return new TypeSafeMatcher<View>() {
            @Override
            public void describeTo(Description description) {
                description.appendText("Child at position " + position + " in parent ");
                parentMatcher.describeTo(description);
            }

            @Override
            public boolean matchesSafely(View view) {
                ViewParent parent = view.getParent();
                return parent instanceof ViewGroup && parentMatcher.matches(parent)
                        && view.equals(((ViewGroup) parent).getChildAt(position));
            }
        };
    }
}
