package projetaobcc20172.com.projetopetemfoco.favoritostests;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.test.espresso.Espresso;
import android.support.test.rule.ActivityTestRule;
import android.support.test.uiautomator.UiObjectNotFoundException;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import projetaobcc20172.com.projetopetemfoco.R;
import projetaobcc20172.com.projetopetemfoco.TestTools;
import projetaobcc20172.com.projetopetemfoco.activity.LoginActivity;
import projetaobcc20172.com.projetopetemfoco.database.services.FavoritoDaoImpl;
import projetaobcc20172.com.projetopetemfoco.logintests.LoginActivityTest;
import projetaobcc20172.com.projetopetemfoco.model.Favorito;

import static android.support.test.InstrumentationRegistry.getInstrumentation;

/**
 * Created by LuizAlberes on 16/02/2018.
 */

public class LigarFavoritoActivityTest {

    private static String sIdFavorito;
    private static String sIdFornecedor = "bHVpenJlcEBnbWFpbC5jb20";
    private static String sTelefone = "(87)99810-7046";
    private static String sNome = "Luiz 2";
    private static String sConfirma = "0";
    private static String sCpfCnpj = "111.289.064-57";

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


        //Recuperar id do usuário logado
        String mIdUsuarioLogado;
        mIdUsuarioLogado = getPreferences("id", context);

        Favorito favorito = new Favorito(sIdFavorito, sIdFornecedor, sNome, sTelefone, sConfirma, sCpfCnpj);;
        FavoritoDaoImpl favoritoDao =  new FavoritoDaoImpl(context);

        favoritoDao.inserir(favorito, mIdUsuarioLogado);

        Thread.sleep(4000);

        LoginActivityTest log = new LoginActivityTest();
        log.testeLoginComSucesso();
        Thread.sleep(4000);
        TestToolsFavoritos.acessarMeusFavoritos();
        Thread.sleep(4000);
        Espresso.closeSoftKeyboard();
    }

    //Teste que simula a ação de remover o um estabelecimento favorito na tela de meus favoritos
    @Test
    public void testeLigarFavorito() throws UiObjectNotFoundException, InterruptedException {
        TestTools.clicarEmItemDentroListView(R.id.lv_meus_favoritos, 0, R.id.bt_ligar_favorito);
        Thread.sleep(1000);
        TestTools.clicarSimDialog();
        //Thread.sleep(2000);
        //TestTools.checarToast(R.string.ligando_favorito);
    }

    @After
    public void tearDown() throws Exception {
        Thread.sleep(4000);
    }

    //Método que recupera o id do usuário logado, para salvar o endereço no nó do usuário que o está cadastrando
    public static String getPreferences(String key, Context context) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.getString(key, null);
    }

}
