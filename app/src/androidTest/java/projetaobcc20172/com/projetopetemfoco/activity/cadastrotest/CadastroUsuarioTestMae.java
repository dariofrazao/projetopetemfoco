package projetaobcc20172.com.projetopetemfoco.activity.cadastrotest;

import android.support.test.espresso.Espresso;
import android.support.test.espresso.action.ViewActions;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.rule.ActivityTestRule;

import org.junit.Rule;

import projetaobcc20172.com.projetopetemfoco.R;
import projetaobcc20172.com.projetopetemfoco.activity.CadastroUsuarioActivity;


/**
 * Created by raul on 16/11/17.
 * Classe que existe para ser herdade pelas outras classes de teste para cadastrp
 * ele guarda os atributos que são utlizados assim como também os métodos after e before
 */

public class CadastroUsuarioTestMae {
    //Essas variaveis guardam valores que são preenchidos nos campos
    //Existentes na tela de cadastro
    protected String nome = "Teste"; //nome do usuário
    protected String emailJaUtilizado = "raulpedrouag@gmail.com";
    protected String emailInvalido = "teste@231234gemeiul.com.br2";
    protected String email = "luar.pedro@yahoo.com.br";
    protected String senha = "12345e";
    protected String senha2 = "12345ew1";

    //Atributo que identifca qual activity seŕa testada
    @Rule
    public ActivityTestRule<CadastroUsuarioActivity> cadastroActivityRule = new ActivityTestRule<>(CadastroUsuarioActivity.class);

    //Metodo que preenche os campos e clica no botao de cadastrar
    public void preencherEClicarCadastro(String nome,String email,String senha1,String senha2){
        Espresso.onView(ViewMatchers.withId(R.id.editText_nome)).perform(ViewActions.typeText(nome));
        Espresso.closeSoftKeyboard();
        Espresso.onView(ViewMatchers.withId(R.id.editText_email)).perform(ViewActions.typeText(email));
        Espresso.closeSoftKeyboard();
        Espresso.onView(ViewMatchers.withId(R.id.editText_senha)).perform(ViewActions.typeText(senha1));
        Espresso.closeSoftKeyboard();
        Espresso.onView(ViewMatchers.withId(R.id.editText_senha2)).perform(ViewActions.typeText(senha2));
        Espresso.closeSoftKeyboard();
        Espresso.onView(ViewMatchers.withId(R.id.botao_cadastrar)).perform(ViewActions.click());
    }
    //método que executa algo antes de iniciar os testes.
   /*
    @Before
    public void setUp() throws Exception {

    }

    @After
    public void tearDown() throws Exception {
        //deixa o app parado por 5 segundos
        //Para acomponhar se a mudança de tela ocorreu corretamente
        //Thread.sleep(1000);
    }
    */
}
