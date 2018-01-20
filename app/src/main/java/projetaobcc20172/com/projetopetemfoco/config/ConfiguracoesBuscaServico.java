package projetaobcc20172.com.projetopetemfoco.config;

import android.app.Activity;

import java.util.ArrayList;
import java.util.Collections;

import projetaobcc20172.com.projetopetemfoco.model.Fornecedor;
import projetaobcc20172.com.projetopetemfoco.utils.Enumerates;
import projetaobcc20172.com.projetopetemfoco.utils.FornecedorComparatorDist;
import projetaobcc20172.com.projetopetemfoco.utils.Localizacao;
import projetaobcc20172.com.projetopetemfoco.utils.ServicoFornecedorComparatorDist;
import projetaobcc20172.com.projetopetemfoco.utils.ServicoFornecedorComparatorPreco;

/**
 * Created by raul1 on 18/01/2018.
 * Classe responsavel por guardar as configurações de busca.
 * Como qual servico está sendo procurado,quais os tipos de pet
 */

public class ConfiguracoesBuscaServico {

    private static ArrayList<String> sOpcaosPet;
    private static Enumerates.Filtro sFiltro;
    private static String sOpcaoServico;
    private static Enumerates.Estado sEstado;
    private static String sOpTodos = "Todos";
    private static Raio sRaio;

    public static ArrayList<String> getsOpcaosPet() {
        return sOpcaosPet;
    }

    public static String getsOpcaoServico() {
        return sOpcaoServico;
    }

    public static void setsOpcaosPet(ArrayList<String> sOpcaosPet) {
        sEstado = Enumerates.Estado.ALTERADO;
        ConfiguracoesBuscaServico.sOpcaosPet = sOpcaosPet;
    }

    public static void setsOpcaoServico(String sOpcaoServico) {
        sEstado = Enumerates.Estado.ALTERADO;
        ConfiguracoesBuscaServico.sOpcaoServico = sOpcaoServico;
    }

    public static void setsFiltro(Enumerates.Filtro sFiltro) {
        sEstado = Enumerates.Estado.ALTERADO;
        ConfiguracoesBuscaServico.sFiltro = sFiltro;
    }

    public static Enumerates.Filtro getsFiltro() {
        return sFiltro;
    }

    public static void inicializar(){
        sEstado = Enumerates.Estado.DEFAULT;
        sFiltro = Enumerates.Filtro.DISTANCIA;
        sOpcaoServico = sOpTodos;
        sRaio = new Raio();
        sOpcaosPet = new ArrayList<>();
        sOpcaosPet.add(sOpTodos);
    }

    public static Enumerates.Estado getsEstado(){
        return sEstado;
    }

    public static Raio getRaio() {
        return sRaio;
    }

    public static void filtrar(Activity act, ArrayList<String[]> resultados){
        if(sFiltro.equals(Enumerates.Filtro.DISTANCIA)){
            ArrayList<String[]> resultadoFiltrados = new ArrayList<>();
            double [] posicaoAtual = Localizacao.getCurrentLocation(act);
            for(String[] valores:resultados){
                double dist = Localizacao.distanciaEntreDoisPontos(act,posicaoAtual[0],posicaoAtual[1],Double.parseDouble(valores[4]),Double.parseDouble(valores[5]));
                if(dist>0 && dist<=sRaio.getRaioReal()){//Só add a lista se possuir uma distância válida e estiver dentro do raio
                    valores[6] = dist+"";
                    resultadoFiltrados.add(valores);
                }
            }
            resultados.clear();
            resultados.addAll(resultadoFiltrados);
            Collections.sort(resultadoFiltrados, new ServicoFornecedorComparatorDist());
        }
        else if(sFiltro.equals(Enumerates.Filtro.PRECO)){
            Collections.sort(resultados, new ServicoFornecedorComparatorPreco());
        }

    }
}