package projetaobcc20172.com.projetopetemfoco.model;

/**
 * Created by renat on 22/02/2018.
 */

public class Cupom {
    private String mDataInicio, mDataVencimento, mValor, mNome;

    public Cupom(String nome, String valor, String dataInicio, String dataVencimento) {
        mNome = nome;
        mValor = valor;
        mDataInicio = dataInicio;
        mDataVencimento = dataVencimento;
    }

    public String getmDataInicio() {
        return mDataInicio;
    }

    public void setmDataInicio(String mDataInicio) {
        this.mDataInicio = mDataInicio;
    }

    public String getmDataVencimento() {
        return mDataVencimento;
    }

    public void setmDataVencimento(String mDataVencimento) {
        this.mDataVencimento = mDataVencimento;
    }

    public String getmValor() {
        return mValor;
    }

    public void setmValor(String mValor) {
        this.mValor = mValor;
    }

    public String getmNome() {
        return mNome;
    }

    public void setmNome(String mNome) {
        this.mNome = mNome;
    }
}
