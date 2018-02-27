package projetaobcc20172.com.projetopetemfoco.favoritostests;

import projetaobcc20172.com.projetopetemfoco.R;
import projetaobcc20172.com.projetopetemfoco.TestTools;

/**
 * Created by LuizAlberes on 09/02/2018.
 */

public class TestToolsFavoritos {

    public static void acessarMeusFavoritos(){
        TestTools.clicarItemNavigationMenu(R.id.drawer_layout, R.id.nav_busca, R.id.nav_favoritos);
    }

    public static void acessarEstabelecimentos(){
        TestTools.clicarItemNavigationMenu(R.id.drawer_layout, R.id.nav_busca, R.id.nav_estabelecimentos);
    }

    public static void botaoFavoritar(){
        TestTools.clicarBotao(R.id.bt_salvar_favorito);
    }
}
