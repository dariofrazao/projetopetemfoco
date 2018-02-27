package projetaobcc20172.com.projetopetemfoco.config;

import projetaobcc20172.com.projetopetemfoco.helper.Preferencias;

import static com.facebook.FacebookSdk.getApplicationContext;

/**
 * Created by alexsandro on 23/02/2018.
 * Classe que representa o raio de busca das promoções. Possui o range e o valor inicial.
 * Classe foi pensado para ser representada através do seekbar por isso inicia com valor 0(que no seebar será 1)
 */

public class RaioPromocao {

    private static int sRange = 1000;
    private static int sInicial = 100;
    private int raioAtual;//usado no seekbar
    private Preferencias pref;

    public RaioPromocao(){
        pref = new Preferencias(getApplicationContext());
        raioAtual = pref.getRaioPromocao();
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
        pref.salvarRaio(this.raioAtual);
    }

}
