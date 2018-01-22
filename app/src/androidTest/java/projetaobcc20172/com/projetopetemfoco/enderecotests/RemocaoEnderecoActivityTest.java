package projetaobcc20172.com.projetopetemfoco.enderecotests;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.test.rule.ActivityTestRule;
import android.support.test.uiautomator.UiObjectNotFoundException;

import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import projetaobcc20172.com.projetopetemfoco.R;
import projetaobcc20172.com.projetopetemfoco.TestTools;
import projetaobcc20172.com.projetopetemfoco.activity.LoginActivity;
import projetaobcc20172.com.projetopetemfoco.database.services.EnderecoDaoImpl;
import projetaobcc20172.com.projetopetemfoco.logintests.LoginActivityTest;
import projetaobcc20172.com.projetopetemfoco.model.Endereco;

import static android.support.test.InstrumentationRegistry.getInstrumentation;

/**
 * Created by dario on 07/12/17.
 * Essa classe é responsavel por executar os testes automatizados
 * Para a remoção de um endereço
 */

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class RemocaoEnderecoActivityTest {

    private static String sLogradouro = "setor norte";
    private static String sBairro = "gunga";
    private static String sCidade = "Naboo";
    private static String sUf = "SP";

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

        Endereco endereco = new Endereco(sLogradouro, sBairro, sCidade, sUf);
        EnderecoDaoImpl enderecoDao =  new EnderecoDaoImpl(context);

        //Recuperar id do usuário logado
        String mIdUsuarioLogado;
        mIdUsuarioLogado = getPreferences("id", context);

        //Chamada do DAO para salvar um endereço no banco para fazer o teste de remoção
        enderecoDao.inserir(endereco, mIdUsuarioLogado);

        Thread.sleep(4000);

        LoginActivityTest log = new LoginActivityTest();
        log.testeLoginComSucesso();
        Thread.sleep(2000);
        TestTools.clicarItemNavigationMenu(R.id.drawer_layout, R.id.nav_busca, R.id.nav_endereco);
        TestToolsEndereco.clicarIconeExcluir();
    }

    //Teste que simula a remoção de um endereço cancelando a ação
    @Test
    public void testeRemoverEnderecoCancelando() throws UiObjectNotFoundException, InterruptedException {
        TestTools.clicarNaoDialog();
    }

    //Teste que simula a remoção de um endereço confirmando a ação
    @Test
    public void testeRemoverEnderecoConfirmando() throws UiObjectNotFoundException, InterruptedException {
        TestTools.clicarSimDialog();
        Thread.sleep(1000);
        TestTools.checarToast(R.string.sucesso_remocao_endereco);
    }

    //Método que recupera o id do usuário logado, para salvar o endereço no nó do usuário que o está cadastrando
    public static String getPreferences(String key, Context context) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.getString(key, null);
    }

}
