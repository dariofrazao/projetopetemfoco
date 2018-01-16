package projetaobcc20172.com.projetopetemfoco.pettests;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.test.InstrumentationRegistry;
import android.support.test.rule.ActivityTestRule;
import android.support.test.uiautomator.UiDevice;
import android.support.test.uiautomator.UiObjectNotFoundException;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import projetaobcc20172.com.projetopetemfoco.R;
import projetaobcc20172.com.projetopetemfoco.TestTools;
import projetaobcc20172.com.projetopetemfoco.activity.LoginActivity;
import projetaobcc20172.com.projetopetemfoco.database.services.PetDaoImpl;
import projetaobcc20172.com.projetopetemfoco.logintests.LoginActivityTest;
import projetaobcc20172.com.projetopetemfoco.model.Pet;

import static android.support.test.InstrumentationRegistry.getInstrumentation;

/**
 * Created by dario on 07/12/17.
 * Essa classe é responsavel por executar os testes automatizados
 * Para a remoção de um pet
 */

public class RemocaoPetActivityTest {

    private static String sNomePet = "Raulf";
    private static String sRaca = "Vira-lata";
    private static String sGenero = "Macho";
    private String mIdUsuarioLogado;
    private UiDevice mDevice;

    @Rule
    public ActivityTestRule<LoginActivity> loginActivityRule = new ActivityTestRule<>(LoginActivity.class);

    @Before
    public void setUp() throws Exception {

        Context context = getInstrumentation().getTargetContext();
        mDevice = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation());

        try{

            TestTools.clicarItemNavigationMenu(R.id.drawer_layout, R.id.nav_busca, R.id.nav_sair);

        }catch (Exception e){
            e.getMessage();
        }

        Pet pet = new Pet(sNomePet, sRaca);
        pet.setGenero(sGenero);
        PetDaoImpl petDao =  new PetDaoImpl(context);

        //Recuperar id do usuário logado
        mIdUsuarioLogado = getPreferences("id", context);

        //Chamada do DAO para salvar um pet no banco para fazer o teste de remoção
        petDao.inserir(pet, mIdUsuarioLogado);

        Thread.sleep(4000);
        LoginActivityTest log = new LoginActivityTest();
        log.testeLoginComSucesso();
        Thread.sleep(4000);
        TestTools.clicarItemNavigationMenu(R.id.drawer_layout, R.id.nav_busca, R.id.nav_pets);
        TestToolsPet.clicariconeExcluir();
    }

    //Teste que simula a remoção de um pet confirmando a ação
    @Test
    public void testeRemoverPetConfirmando() throws UiObjectNotFoundException, InterruptedException {
        TestTools.clicarSimDialog();
        Thread.sleep(1000);
        TestTools.checarToast(R.string.sucesso_remocao_Pet);
    }

    //Teste que simula a remoção de um pet cancelando a ação
    @Test
    public void testeRemoverPetCancelando() throws UiObjectNotFoundException, InterruptedException {
        TestTools.clicarNaoDialog();
    }

    //Método que recupera o id do usuário logado, para salvar o pet no nó do usuário que o está cadastrando
    public static String getPreferences(String key, Context context) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.getString(key, null);
    }
}
