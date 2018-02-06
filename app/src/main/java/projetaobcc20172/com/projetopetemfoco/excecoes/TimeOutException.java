package projetaobcc20172.com.projetopetemfoco.excecoes;

/**
 * Created by raul1 on 19/01/2018.
 */

public class TimeOutException extends DefaultException {

    public TimeOutException(String mensagem){
        super(mensagem);
        this.mMensagem = mensagem;
    }


}
