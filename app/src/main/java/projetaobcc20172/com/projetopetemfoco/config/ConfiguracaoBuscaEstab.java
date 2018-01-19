package projetaobcc20172.com.projetopetemfoco.config;

import projetaobcc20172.com.projetopetemfoco.utils.Enumerates;

/**
 * Created by raul1 on 18/01/2018.
 */

public class ConfiguracaoBuscaEstab {

    private static String sNomeEstabelecimento;
    private static Enumerates.Filtro sFiltro;
    private static byte sRaio;

    public static String getsNomeEstabelecimento() {
        return sNomeEstabelecimento;
    }

    public static Enumerates.Filtro getsFiltro() {
        return sFiltro;
    }

    public static byte getsRaio() {
        return sRaio;
    }

    public static void setsNomeEstabelecimento(String sNomeEstabelecimento) {
        ConfiguracaoBuscaEstab.sNomeEstabelecimento = sNomeEstabelecimento;
    }

    public static void setsFiltro(Enumerates.Filtro sFiltro) {
        ConfiguracaoBuscaEstab.sFiltro = sFiltro;
    }

    public static void setsRaio(byte sRaio) {
        ConfiguracaoBuscaEstab.sRaio = sRaio;
    }

    public static void inicializar(){
        sFiltro = Enumerates.Filtro.DISTANCIA;
        sRaio = 10;
    }
}
