package projetaobcc20172.com.projetopetemfoco.enderecotests;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.test.rule.ActivityTestRule;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

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
 * Para a edição de um endereço
 */

public class EditarEnderecoActivityTest {

    private static String sLogradouro = "setor norte";
    private static String sNumero = "42";
    private static String sComplemento = "prox. palacio";
    private static String sBairro = "gunga";
    private static String sCidade = "Naboo";
    private static String sUf = "SP";
    private static String sCep = "55290-000";
    private static int sBotaoEdicEnd = R.id.botao_editar_endereco;
    private String mIdUsuarioLogado;

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
        mIdUsuarioLogado = getPreferences("id", context);

        //Chamada do DAO para salvar um endereço no banco para fazer o teste de edição
        enderecoDao.inserirEndereco(endereco, mIdUsuarioLogado);

        Thread.sleep(4000);
        LoginActivityTest log = new LoginActivityTest();
        log.testeLoginComSucesso();
        Thread.sleep(4000);
        TestTools.clicarItemNavigationMenu(R.id.drawer_layout, R.id.nav_busca, R.id.nav_endereco);
        TestToolsEndereco.clicarIconeEditar();
    }

    @Test
    public void testeEnderecoCamposEmBranco() throws InterruptedException {
        TestToolsEndereco.apagarCampos();
        Thread.sleep(2000);
        TestTools.clicarBotaoComScroll(sBotaoEdicEnd);
        TestTools.checarToast(R.string.erro_atualizacao_campos_obrigatorios_endereco);
    }

    @Test
    public void testeEnderecoCampoObgLogradouro() throws InterruptedException {
        TestToolsEndereco.preencherEdicao("", sNumero, sComplemento, sBairro,
                sCidade, sUf, sCep);
        Thread.sleep(2000);
        TestTools.apagarCampo(R.id.etEditarLogradouroEndereco);
        Thread.sleep(2000);
        TestTools.clicarBotaoComScroll(sBotaoEdicEnd);
        TestTools.checarToast(R.string.erro_atualizacao_campos_obrigatorios_endereco);
    }

    @Test
    public void testeEnderecoCampoObgBairro() throws InterruptedException {
        TestToolsEndereco.preencherEdicao(sLogradouro, sNumero, sComplemento,"",
                sCidade, sUf, sCep);
        Thread.sleep(2000);
        TestTools.apagarCampo(R.id.etEditarBairroEndereco);
        Thread.sleep(2000);
        TestTools.clicarBotaoComScroll(sBotaoEdicEnd);
        TestTools.checarToast(R.string.erro_atualizacao_campos_obrigatorios_endereco);
    }

    @Test
    public void testeEnderecoCampoObgCidade() throws InterruptedException {
        TestToolsEndereco.preencherEdicao(sLogradouro, sNumero, sComplemento, sBairro,
                "", sUf, sCep);
        Thread.sleep(2000);
        TestTools.apagarCampo(R.id.etEditarLocalidadeEndereco);
        Thread.sleep(2000);
        TestTools.clicarBotaoComScroll(sBotaoEdicEnd);
        TestTools.checarToast(R.string.erro_atualizacao_campos_obrigatorios_endereco);
    }

    @Test
    public void testeEnderecoCampoObgCep() throws InterruptedException {
        TestToolsEndereco.preencherEdicao(sLogradouro, sNumero, sComplemento, sBairro,
                sCidade, sUf,"");
        Thread.sleep(2000);
        TestTools.editarTextoComScroll(R.id.etEditarCepEndereco);
        Thread.sleep(2000);
        TestTools.apagarCampo(R.id.etEditarCepEndereco);
        Thread.sleep(2000);
        TestTools.clicarBotaoComScroll(sBotaoEdicEnd);
        TestTools.checarToast(R.string.erro_atualizacao_campos_obrigatorios_endereco);
    }

    @Test
    public void testeEditarEndereco() throws InterruptedException {
        TestToolsEndereco.preencherEdicao(sLogradouro, sNumero, sComplemento, sBairro,
                sCidade, sUf,sCep);
        Thread.sleep(2000);
        TestTools.clicarBotao(sBotaoEdicEnd);
        TestTools.checarToast(R.string.sucesso_atualizacao_endereco);
    }

    //Método que recupera o id do usuário logado, para salvar o endereço no nó do usuário que o está cadastrando
    public static String getPreferences(String key, Context context) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.getString(key, null);
    }



}
