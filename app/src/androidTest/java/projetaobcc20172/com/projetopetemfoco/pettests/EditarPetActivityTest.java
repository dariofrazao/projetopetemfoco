package projetaobcc20172.com.projetopetemfoco.pettests;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.test.rule.ActivityTestRule;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import projetaobcc20172.com.projetopetemfoco.R;
import projetaobcc20172.com.projetopetemfoco.activity.LoginActivity;
import projetaobcc20172.com.projetopetemfoco.TestTools;
import projetaobcc20172.com.projetopetemfoco.database.services.PetDaoImpl;
import projetaobcc20172.com.projetopetemfoco.logintests.LoginActivityTest;
import projetaobcc20172.com.projetopetemfoco.model.Pet;

import static android.support.test.InstrumentationRegistry.getInstrumentation;

/**
 * Created by dario on 07/12/17.
 * Essa classe é responsavel por executar os testes automatizados
 * Para a edição de um pet
 */

public class EditarPetActivityTest {

    private static String sNomePet = "Raulf";
    private static String sRaca = "Vira-lata";
    private static String sGenero = "Macho";

    @Rule
    public ActivityTestRule<LoginActivity> loginActivityRule = new ActivityTestRule<>(LoginActivity.class);

    @Before
    public void setUp() throws Exception {

        Context context = getInstrumentation().getTargetContext();

        try{

            TestTools.clicarItemNavigationMenu(R.id.drawer_layout, R.id.nav_busca, R.id.nav_sair);

        }catch (Exception e){
            e.getMessage();
        }

        Pet pet = new Pet(sNomePet, sRaca);
        pet.setGenero(sGenero);
        PetDaoImpl petDao =  new PetDaoImpl(context);

        //Recuperar id do usuário logado
        String mIdUsuarioLogado;
        mIdUsuarioLogado = getPreferences("id", context);

        //Chamada do DAO para salvar um pet no banco para fazer o teste de edição
        petDao.inserir(pet, mIdUsuarioLogado);

        Thread.sleep(4000);
        LoginActivityTest log = new LoginActivityTest();
        log.testeLoginComSucesso();
        Thread.sleep(4000);
        TestTools.clicarItemNavigationMenu(R.id.drawer_layout, R.id.nav_busca, R.id.nav_pets);
        TestToolsPet.clicarIconeEditar();
    }

    //Teste que simula uma edição do pet com o campo nome em branco
    @Test
    public void testeNomeEmBranco() throws InterruptedException {
        TestTools.apagarCampo(R.id.etEditarNomePet);
        Thread.sleep(2000);
        TestTools.clicarBotaoComScroll(R.id.botao_editar_pet);
        TestTools.checarToast(R.string.erro_atualizacao_campos_obrigatorios_Pet);
    }

    //Teste que simula uma edição do pet com os campos em branco
    @Test
    public void testeCamposEmBranco() throws InterruptedException {
        TestTools.apagarCampo(R.id.etEditarNomePet);
        TestTools.apagarCampo(R.id.etEditarRaçaPet);
        Thread.sleep(2000);
        TestTools.clicarBotaoComScroll(R.id.botao_editar_pet);
        TestTools.checarToast(R.string.erro_atualizacao_campos_obrigatorios_Pet);
    }

    //Teste que simula uma edição do pet com todos os campos preenchidos
    @Test
    public void testeEditarPet() throws InterruptedException {
        TestTools.apagarCampo(R.id.etEditarNomePet);
        TestTools.apagarCampo(R.id.etEditarRaçaPet);
        Thread.sleep(2000);
        TestToolsPet.preencherEdicao(sNomePet, sRaca);
        Thread.sleep(2000);
        TestTools.clicarBotaoComScroll(R.id.botao_editar_pet);
        TestTools.checarToast(R.string.sucesso_atualizacao_Pet);
    }

    //Método que recupera o id do usuário logado, para salvar o pet no nó do usuário que o está cadastrando
    public static String getPreferences(String key, Context context) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.getString(key, null);
    }
}
