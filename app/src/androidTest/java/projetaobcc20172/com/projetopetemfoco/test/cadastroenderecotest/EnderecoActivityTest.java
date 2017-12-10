package projetaobcc20172.com.projetopetemfoco.test.cadastroenderecotest;

import android.support.test.espresso.intent.Intents;
import android.support.test.rule.ActivityTestRule;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import projetaobcc20172.com.projetopetemfoco.R;
import projetaobcc20172.com.projetopetemfoco.activity.CadastroEnderecoActivity;
import projetaobcc20172.com.projetopetemfoco.activity.MainActivity;
import projetaobcc20172.com.projetopetemfoco.test.TestTools;

/**
 * Created by raul on 09/12/17.
 */

public class EnderecoActivityTest {

    private static String logradouro = "setor norte";
    private static String numero = "42";
    private static String complemento = "prox. palacio de naboo";
    private static String bairro = "gunga";
    private static String cidade = "Naboo";
    private static String uf = "PE";
    private static String cep = "55299-510";
    private static int botaoCadEnd = R.id.botao_finalizar_cadastro_endereco;

    @Rule
    public ActivityTestRule<CadastroEnderecoActivity> cadEndActivityRule = new ActivityTestRule<>(CadastroEnderecoActivity.class);

    @Before
    public void setUp() throws Exception {
        Thread.sleep(2000);
    }

    private void clicarEVerificarCorreto(){
        TestTools.clicarBotaoComScroll(this.botaoCadEnd);
        TestTools.checarToast(R.string.sucesso_cadastro_Toast);
        TestTools.verificarMudancaActivity(MainActivity.class.getName());
        TestTools.clicarBotao(R.id.botao_sair);
    }

    public void testeEnderecoCorreto(){
        Intents.init();
        TestToolsCadEndereco.preencher(this.logradouro,this.numero,this.complemento,this.bairro,
                this.cidade,this.uf,this.cep);
        this.clicarEVerificarCorreto();
    }

    public void testeEnderecoCorretoSemNumero(){
        Intents.init();
        TestToolsCadEndereco.preencher(this.logradouro,"",this.complemento,this.bairro,
                this.cidade,this.uf,this.cep);
        this.clicarEVerificarCorreto();
    }

    public void testeEnderecoCorretoSemCompl(){
        Intents.init();
        TestToolsCadEndereco.preencher(this.logradouro,this.numero,"",this.bairro,
                this.cidade,this.uf,this.cep);
        this.clicarEVerificarCorreto();
    }

    @Test
    public void testeEnderecoCamposEmBranco(){
        TestTools.clicarBotaoComScroll(this.botaoCadEnd);
        TestTools.checarToast(R.string.erro_cadastro_endereco_campos_obrigatorios_Toast);
    }

    @Test
    public void testeEnderecoCampoObgLogradouro(){
        TestToolsCadEndereco.preencher("",this.numero,this.complemento,this.bairro,
                this.cidade,this.uf,this.cep);
        TestTools.clicarBotaoComScroll(this.botaoCadEnd);
        TestTools.checarToast(R.string.erro_cadastro_endereco_campos_obrigatorios_Toast);
    }

    @Test
    public void testeEnderecoCampoObgBairro(){
        TestToolsCadEndereco.preencher(this.logradouro,this.numero,this.complemento,"",
                this.cidade,this.uf,this.cep);
        TestTools.clicarBotaoComScroll(this.botaoCadEnd);
        TestTools.checarToast(R.string.erro_cadastro_endereco_campos_obrigatorios_Toast);
    }

    @Test
    public void testeEnderecoCampoObgCidade(){
        TestToolsCadEndereco.preencher(this.logradouro,this.numero,this.complemento,this.bairro,
                "",this.uf,this.cep);
        TestTools.clicarBotaoComScroll(this.botaoCadEnd);
        TestTools.checarToast(R.string.erro_cadastro_endereco_campos_obrigatorios_Toast);
    }


    @Test
    public void testeEnderecoCampoObgCep(){
        TestToolsCadEndereco.preencher(this.logradouro,this.numero,this.complemento,this.bairro,
                this.cidade,this.uf,"");
        TestTools.clicarBotao(this.botaoCadEnd);
        TestTools.checarToast(R.string.erro_cadastro_endereco_campos_obrigatorios_Toast);
    }
}
