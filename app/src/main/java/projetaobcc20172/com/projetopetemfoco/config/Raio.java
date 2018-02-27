package projetaobcc20172.com.projetopetemfoco.config;


import projetaobcc20172.com.projetopetemfoco.helper.Preferencias;

import static com.facebook.FacebookSdk.getApplicationContext;

/**
 * Created by raul1 on 19/01/2018.
 * Classe que representa o raio de busca do app. Possui o range e o valor inicial.
 * Classe foi pensado para ser representada através do seekbar por isso inicia com valor 0(que no seebar será 1)
 */

public class Raio {

    private static int sRange = 5;
    private static int sInicial = 5;
    private int raioAtual = 5;//usado no seekbar
    private int raioReal = 5;//raio real em km que o usuário visualiza
    private Preferencias pref;

    public Raio(){
        pref = new Preferencias(getApplicationContext());
        raioAtual = pref.getRaio();
        setRaioReal();
    }

    public  int getRange() {
        return sRange;
    }

    public int getInicial() {
        return sInicial;
    }

    public int getRaioAtual() {
        return raioAtual;
    }

    public void setRaioAtual(int raioAtual) {
        this.raioAtual = raioAtual;
        setRaioReal();
        pref.salvarRaio(this.raioAtual);
    }

    private void setRaioReal(){
        raioReal = raioAtual+sInicial;
    }

    public int getRaioReal() {
        return raioReal;
    }
}
