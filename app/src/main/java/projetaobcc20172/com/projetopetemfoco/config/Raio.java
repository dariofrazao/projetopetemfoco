package projetaobcc20172.com.projetopetemfoco.config;


import projetaobcc20172.com.projetopetemfoco.helper.Preferencias;

import static com.facebook.FacebookSdk.getApplicationContext;

/**
 * Created by raul1 on 19/01/2018.
 * Classe que representa o raio de busca do app. Possui o range e o valor inicial.
 * Classe foi pensado para ser representada através do seekbar por isso inicia com valor 0(que no seebar será 1)
 */

public class Raio {

    private static byte sRange = 5;
    private static byte sInicial = 5;
    private byte raioAtual;//usado no seekbar
    private byte raioReal;//raio real em km que o usuário visualiza
    private Preferencias pref;

    public Raio(){
        pref = new Preferencias(getApplicationContext());
        this.raioAtual = pref.getRaio();
        this.setRaioReal();
    }

    public  byte getRange() {
        return sRange;
    }

    public byte getInicial() {
        return sInicial;
    }

    public byte getRaioAtual() {
        return raioAtual;
    }

    public void setRaioAtual(byte raioAtual) {
        this.raioAtual = raioAtual;
        this.setRaioReal();
        pref.salvarRaio(this.raioAtual);
    }

    private void setRaioReal(){
        this.raioReal = (byte)(this.raioAtual+sInicial);
    }
    public byte getRaioReal() {
        return raioReal;
    }
}
