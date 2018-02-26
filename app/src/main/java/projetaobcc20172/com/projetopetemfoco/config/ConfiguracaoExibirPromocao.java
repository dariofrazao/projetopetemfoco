package projetaobcc20172.com.projetopetemfoco.config;


import projetaobcc20172.com.projetopetemfoco.utils.Enumerates;


/**
 * Created by alexsandro on 23/02/2018.
 * Classe responsavel por guardar as configurações de exibição
 *das promoções
 *  */

public class ConfiguracaoExibirPromocao {
    private static Enumerates.Promocao sPromocao;
    private static RaioPromocao sRaio;

    public static void setsOpcoesPromocao(Enumerates.Promocao sProm) {
        sPromocao = sProm;
    }

    public static Enumerates.Promocao getsOpcoesPromocao() {
        return sPromocao;
    }

    public static void inicializar(){
        sPromocao = Enumerates.Promocao.PADRAO;
        sRaio = new RaioPromocao();
    }

    public static RaioPromocao getRaio() {
        return sRaio;
    }

}
