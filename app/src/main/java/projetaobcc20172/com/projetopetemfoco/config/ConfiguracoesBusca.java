package projetaobcc20172.com.projetopetemfoco.config;

import java.util.ArrayList;

/**
 * Created by raul1 on 18/01/2018.
 * Classe responsavel por guardar as configurações de busca.
 * Como qual servico está sendo procurado,quais os tipos de pet
 */

public class ConfiguracoesBusca {

    public enum Filtro {
        AVALICAO,
        DISTANCIA;
    }

    public enum Estado{
        DEFAULT,
        ALTERADO;
    }

    private static ArrayList<String> sOpcaosPet;
    private static Filtro sFiltro = Filtro.DISTANCIA;
    private static String sOpcaoServico;
    private static Estado sEstado = Estado.DEFAULT;
    private static String sOpTodos = "Todos";

    public static ArrayList<String> getsOpcaosPet() {
        return sOpcaosPet;
    }

    public static String getsOpcaoServico() {
        return sOpcaoServico;
    }

    public static void setsOpcaosPet(ArrayList<String> sOpcaosPet) {
        sEstado = Estado.ALTERADO;
        ConfiguracoesBusca.sOpcaosPet = sOpcaosPet;
    }

    public static void setsOpcaoServico(String sOpcaoServico) {
        sEstado = Estado.ALTERADO;
        ConfiguracoesBusca.sOpcaoServico = sOpcaoServico;
    }

    public static void setsFiltro(Filtro sFiltro) {
        sEstado = Estado.ALTERADO;
        ConfiguracoesBusca.sFiltro = sFiltro;
    }

    public static Filtro getsFiltro() {
        return sFiltro;
    }

    public static void inicializar(){
        sEstado = Estado.DEFAULT;
        sFiltro = Filtro.DISTANCIA;
        sOpcaoServico = sOpTodos;
        sOpcaosPet = new ArrayList<>();
        sOpcaosPet.add(sOpTodos);
    }

    public static Estado getsEstado(){
        return sEstado;
    }
}
