package projetaobcc20172.com.projetopetemfoco.activity;


import android.support.test.espresso.DataInteraction;
import android.support.test.espresso.ViewInteraction;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import static android.support.test.InstrumentationRegistry.getInstrumentation;
import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.Espresso.pressBack;
import static android.support.test.espresso.Espresso.openActionBarOverflowOrOptionsMenu;
import static android.support.test.espresso.action.ViewActions.*;
import static android.support.test.espresso.assertion.ViewAssertions.*;
import static android.support.test.espresso.matcher.ViewMatchers.*;

import projetaobcc20172.com.projetopetemfoco.R;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.hamcrest.core.IsInstanceOf;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.anything;
import static org.hamcrest.Matchers.is;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class AvaliacaoActivityTest {

    @Rule
    public ActivityTestRule<LoginActivity> mActivityTestRule = new ActivityTestRule<>(LoginActivity.class);

    @Test
    public void avaliacaoActivityTest() {
         // Added a sleep statement to match the app's execution delay.
 // The recommended way to handle such scenarios is to use Espresso idling resources:
  // https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html
try {
 Thread.sleep(60000);
 } catch (InterruptedException e) {
 e.printStackTrace();
 }
        
        ViewInteraction appCompatButton = onView(
allOf(withId(R.id.btnLoginGoogle), withText("Login com o Google"),
childAtPosition(
childAtPosition(
withClassName(is("android.widget.RelativeLayout")),
0),
3),
isDisplayed()));
        appCompatButton.perform(click());
        
         // Added a sleep statement to match the app's execution delay.
 // The recommended way to handle such scenarios is to use Espresso idling resources:
  // https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html
try {
 Thread.sleep(3589188);
 } catch (InterruptedException e) {
 e.printStackTrace();
 }
        
        ViewInteraction appCompatButton2 = onView(
allOf(withId(android.R.id.button2), withText("Não"),
childAtPosition(
allOf(withClassName(is("com.android.internal.widget.ButtonBarLayout")),
childAtPosition(
withClassName(is("android.widget.LinearLayout")),
3)),
2),
isDisplayed()));
        appCompatButton2.perform(click());
        
         // Added a sleep statement to match the app's execution delay.
 // The recommended way to handle such scenarios is to use Espresso idling resources:
  // https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html
try {
 Thread.sleep(3590277);
 } catch (InterruptedException e) {
 e.printStackTrace();
 }
        
        DataInteraction linearLayout = onData(anything())
.inAdapterView(allOf(withId(R.id.gridServicos),
childAtPosition(
withClassName(is("android.widget.LinearLayout")),
0)))
.atPosition(1);
        linearLayout.perform(click());
        
         // Added a sleep statement to match the app's execution delay.
 // The recommended way to handle such scenarios is to use Espresso idling resources:
  // https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html
try {
 Thread.sleep(3590133);
 } catch (InterruptedException e) {
 e.printStackTrace();
 }
        
        DataInteraction relativeLayout = onData(anything())
.inAdapterView(allOf(withId(R.id.lvEstaServicoBusca),
childAtPosition(
withClassName(is("android.widget.RelativeLayout")),
2)))
.atPosition(0);
        relativeLayout.perform(click());
        
         // Added a sleep statement to match the app's execution delay.
 // The recommended way to handle such scenarios is to use Espresso idling resources:
  // https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html
try {
 Thread.sleep(3590369);
 } catch (InterruptedException e) {
 e.printStackTrace();
 }
        
        ViewInteraction appCompatButton3 = onView(
allOf(withId(R.id.btnAvaliarServico), withText("Avaliar Serviço"),
childAtPosition(
childAtPosition(
withClassName(is("android.widget.RelativeLayout")),
1),
12),
isDisplayed()));
        appCompatButton3.perform(click());
        
         // Added a sleep statement to match the app's execution delay.
 // The recommended way to handle such scenarios is to use Espresso idling resources:
  // https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html
try {
 Thread.sleep(3592663);
 } catch (InterruptedException e) {
 e.printStackTrace();
 }
        
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
        appCompatEditText2.perform(scrollTo(), replaceText("fhjjkkk"), closeSoftKeyboard());
        
        ViewInteraction appCompatEditText3 = onView(
allOf(withId(R.id.etComentarioAvaliacaoServico), withText("fhjjkkk"),
childAtPosition(
childAtPosition(
withId(R.id.scrollView),
0),
0)));
        appCompatEditText3.perform(scrollTo(), click());
        
        ViewInteraction appCompatEditText4 = onView(
allOf(withId(R.id.etComentarioAvaliacaoServico), withText("fhjjkkk"),
childAtPosition(
childAtPosition(
withId(R.id.scrollView),
0),
0)));
        appCompatEditText4.perform(scrollTo(), replaceText("ggjjjjjj"));
        
        ViewInteraction appCompatEditText5 = onView(
allOf(withId(R.id.etComentarioAvaliacaoServico), withText("ggjjjjjj"),
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
        
         // Added a sleep statement to match the app's execution delay.
 // The recommended way to handle such scenarios is to use Espresso idling resources:
  // https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html
try {
 Thread.sleep(3534645);
 } catch (InterruptedException e) {
 e.printStackTrace();
 }
        
        ViewInteraction appCompatButton6 = onView(
allOf(withId(R.id.btnAvaliacoesServico), withText("AVALIAÇÕES DO SERVIÇO"),
childAtPosition(
childAtPosition(
withClassName(is("android.widget.RelativeLayout")),
1),
13),
isDisplayed()));
        appCompatButton6.perform(click());
        
         // Added a sleep statement to match the app's execution delay.
 // The recommended way to handle such scenarios is to use Espresso idling resources:
  // https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html
try {
 Thread.sleep(3590620);
 } catch (InterruptedException e) {
 e.printStackTrace();
 }
        
        pressBack();
        
         // Added a sleep statement to match the app's execution delay.
 // The recommended way to handle such scenarios is to use Espresso idling resources:
  // https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html
try {
 Thread.sleep(3594640);
 } catch (InterruptedException e) {
 e.printStackTrace();
 }
        
        pressBack();
        
         // Added a sleep statement to match the app's execution delay.
 // The recommended way to handle such scenarios is to use Espresso idling resources:
  // https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html
try {
 Thread.sleep(3596825);
 } catch (InterruptedException e) {
 e.printStackTrace();
 }
        
        pressBack();
        
         // Added a sleep statement to match the app's execution delay.
 // The recommended way to handle such scenarios is to use Espresso idling resources:
  // https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html
try {
 Thread.sleep(3597863);
 } catch (InterruptedException e) {
 e.printStackTrace();
 }
        
        pressBack();
        
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
                        && view.equals(((ViewGroup)parent).getChildAt(position));
            }
        };
    }
    }
