package projetaobcc20172.com.projetopetemfoco.config;

import java.util.ArrayList;

import projetaobcc20172.com.projetopetemfoco.utils.Enumerates;

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
}
