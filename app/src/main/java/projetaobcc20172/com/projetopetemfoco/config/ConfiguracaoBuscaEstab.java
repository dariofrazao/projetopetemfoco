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
    private static Enumerates.TipoFornecedor sTipoFornecedor;
    private static Enumerates.Estado sEstado;
    private static Raio sRaio;

    public static String getsNomeEstabelecimento() {
        return sNomeEstabelecimento;
    }

    public static Enumerates.Filtro getsFiltro() {
        return sFiltro;
    }

    public static Enumerates.TipoFornecedor getsTipoFornecedor() {
        return sTipoFornecedor;
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

    public static void setsTipoFornecedor(Enumerates.TipoFornecedor sTipoFornecedor) {
        ConfiguracaoBuscaEstab.sTipoFornecedor = sTipoFornecedor;
    }

    public static void inicializar(){
        sEstado = Enumerates.Estado.DEFAULT;
        sNomeEstabelecimento = null;
        sFiltro = Enumerates.Filtro.DISTANCIA;
        sTipoFornecedor = Enumerates.TipoFornecedor.TODOS;
        sRaio = new Raio();
    }

    public static void filtrar(Activity act, ArrayList<Fornecedor> resultados){
        ArrayList<Fornecedor> resultadoFiltrados = new ArrayList<>();
        double [] posicaoAtual = Localizacao.getCurrentLocation(act);

        if(sTipoFornecedor.equals(Enumerates.TipoFornecedor.TODOS)) {
            for (Fornecedor forn : resultados) {
                double dist = Localizacao.distanciaEntreDoisPontos(act, posicaoAtual[0], posicaoAtual[1], forn.getEndereco().getmLatitude(), forn.getEndereco().getmLongitude());
                if (dist > 0 && dist <= sRaio.getRaioReal()) {//Só add a lista se possuir uma distância válida e estiver dentro do raio
                    forn.setDistancia(dist);
                    resultadoFiltrados.add(forn);
                }
            }
            resultados.clear();
            resultados.addAll(resultadoFiltrados);
        }
        if(sTipoFornecedor.equals(Enumerates.TipoFornecedor.AUTONOMO)){
            for(Fornecedor forn:resultados){
                if(forn.getTipo().equals("Autônomo")){//Só add a lista se for autonomo
                    resultadoFiltrados.add(forn);
                }
            }
            resultados.clear();
            resultados.addAll(resultadoFiltrados);
        }

        if(sTipoFornecedor.equals(Enumerates.TipoFornecedor.ESTABELECIMENTO)){
            for(Fornecedor forn:resultados){
                if(forn.getTipo().equals("Estabeleciemnto")){//Só add a lista se for Estabelecimento
                    resultadoFiltrados.add(forn);
                }
            }
            resultados.clear();
            resultados.addAll(resultadoFiltrados);
        }

        //Ordena por distância
        if(sFiltro.equals(Enumerates.Filtro.DISTANCIA)){
            Collections.sort(resultados, new FornecedorComparatorDist());
        }


        //Ordena por avaliação
        else if(sFiltro.equals(Enumerates.Filtro.AVALICAO)){
            Collections.sort(resultados, new FornecedorComparatorAvaliacao());
            Collections.reverse(resultados);
        }
        sEstado = Enumerates.Estado.DEFAULT;
    }


    public static Enumerates.Estado getsEstado(){
        return sEstado;
    }

    public static void setsEstado(Enumerates.Estado sEstado) {
        ConfiguracaoBuscaEstab.sEstado = sEstado;
    }


}
