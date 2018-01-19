package projetaobcc20172.com.projetopetemfoco.config;

/**
 * Created by raul1 on 19/01/2018.
 */

public class Raio {

    private static byte sRange = 5;
    private static byte sInicial = 5;
    private byte raioAtual;

    public Raio(){
        this.raioAtual = 0;
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
    }

}
