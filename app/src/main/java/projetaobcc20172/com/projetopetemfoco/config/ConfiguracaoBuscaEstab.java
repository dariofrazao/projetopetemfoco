package projetaobcc20172.com.projetopetemfoco.config;

import android.app.Activity;

import java.util.ArrayList;
import java.util.Collections;

import projetaobcc20172.com.projetopetemfoco.model.Fornecedor;
import projetaobcc20172.com.projetopetemfoco.utils.Enumerates;
import projetaobcc20172.com.projetopetemfoco.utils.FornecedorComparatorAvaliacao;
import projetaobcc20172.com.projetopetemfoco.utils.FornecedorComparatorDist;
import projetaobcc20172.com.projetopetemfoco.utils.Localizacao;

/**
 * Created by raul1 on 18/01/2018.
 */

public class ConfiguracaoBuscaEstab {

    private static String sNomeEstabelecimento;
    private static Enumerates.Filtro sFiltro;
    private static Raio sRaio;

    public static String getsNomeEstabelecimento() {
        return sNomeEstabelecimento;
    }

    public static Enumerates.Filtro getsFiltro() {
        return sFiltro;
    }

    public static Raio getRaio() {
        return sRaio;
    }

    public static void setsNomeEstabelecimento(String sNomeEstabelecimento) {
        ConfiguracaoBuscaEstab.sNomeEstabelecimento = sNomeEstabelecimento;
    }

    public static void setsFiltro(Enumerates.Filtro sFiltro) {
        ConfiguracaoBuscaEstab.sFiltro = sFiltro;
    }

    public static void inicializar(){
        sNomeEstabelecimento = null;
        sFiltro = Enumerates.Filtro.DISTANCIA;
        sRaio = new Raio();
    }

    public static void filtrar(Activity act, ArrayList<Fornecedor> resultados){
        ArrayList<Fornecedor> resultadoFiltrados = new ArrayList<>();
        double [] posicaoAtual = Localizacao.getCurrentLocation(act);
        for(Fornecedor forn:resultados){
            double dist = Localizacao.distanciaEntreDoisPontos(act,posicaoAtual[0],posicaoAtual[1],forn.getEndereco().getmLatitude(),forn.getEndereco().getmLongitude());
            if(dist>0 && dist<=sRaio.getRaioReal()){//Só add a lista se possuir uma distância válida e estiver dentro do raio
                forn.setDistancia(dist);
                resultadoFiltrados.add(forn);
            }
        }
        resultados.clear();
        resultados.addAll(resultadoFiltrados);
        //Ordena por distância

        if(sFiltro.equals(Enumerates.Filtro.DISTANCIA)){
            Collections.sort(resultadoFiltrados, new FornecedorComparatorDist());
        }
        //Ordena por avaliação
        else if(sFiltro.equals(Enumerates.Filtro.AVALICAO)){
            Collections.sort(resultados, new FornecedorComparatorAvaliacao());
            Collections.reverse(resultados);
        }
    }
}
