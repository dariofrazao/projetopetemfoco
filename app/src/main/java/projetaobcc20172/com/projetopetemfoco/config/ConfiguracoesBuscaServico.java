package projetaobcc20172.com.projetopetemfoco.config;

import android.app.Activity;

import java.util.ArrayList;
import java.util.Collections;

import projetaobcc20172.com.projetopetemfoco.utils.Enumerates;
import projetaobcc20172.com.projetopetemfoco.utils.Localizacao;
import projetaobcc20172.com.projetopetemfoco.utils.ServicoFornecedorComparatorAvaliacao;
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
        ConfiguracoesBuscaServico.sOpcaosPet = sOpcaosPet;
    }

    public static void setsOpcaoServico(String sOpcaoServico) {
        ConfiguracoesBuscaServico.sOpcaoServico = sOpcaoServico;
    }

    public static void setsFiltro(Enumerates.Filtro sFiltro) {
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

    public static void setsEstado(Enumerates.Estado sEstado) {
        ConfiguracoesBuscaServico.sEstado = sEstado;
    }

    public static void filtrar(Activity act, ArrayList<String[]> resultados){
        ArrayList<String[]> resultadoFiltrados = new ArrayList<>();
        double [] posicaoAtual = Localizacao.getCurrentLocation(act);
        for(String[] valores:resultados){
            try {//Evita qualquer erro relacioanado com distância ou a latitude e longitude
                double dist = Localizacao.distanciaEntreDoisPontos(act, posicaoAtual[0], posicaoAtual[1], Double.parseDouble(valores[4]), Double.parseDouble(valores[5]));
                if (dist > 0 && dist <= sRaio.getRaioReal()) {//Só add a lista se possuir uma distância válida e estiver dentro do raio
                    valores[6] = dist + "";
                    resultadoFiltrados.add(valores);
                }
            }catch (Exception e){
                continue;
            }
        }
        resultados.clear();
        resultados.addAll(resultadoFiltrados);
        //Ordena por distância
        if(sFiltro.equals(Enumerates.Filtro.DISTANCIA)){
            Collections.sort(resultados, new ServicoFornecedorComparatorDist());
        }
        //Ordena por preço
        else if(sFiltro.equals(Enumerates.Filtro.PRECO)){
            Collections.sort(resultados, new ServicoFornecedorComparatorPreco());
        }
        //Ordena por avaliação
        else if(sFiltro.equals(Enumerates.Filtro.AVALICAO)){
            Collections.sort(resultados, new ServicoFornecedorComparatorAvaliacao());
            Collections.reverse(resultados);
        }
        sEstado = Enumerates.Estado.DEFAULT;
    }
}
