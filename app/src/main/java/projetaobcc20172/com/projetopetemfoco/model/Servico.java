package projetaobcc20172.com.projetopetemfoco.model;

import com.google.firebase.database.Exclude;

/**
 * Created by Felipe Oliveira on 05/12/17.
 *
 */

public class Servico {
    private String mId;
    private String mNome;
    private String mDescricao;
    private String mValor;
    private String mTipoPet;

    public Servico() {}

    public Servico(String nome, String valor,String tipoPet){
        this.mNome = nome;
        this.mValor = valor;
        this.mTipoPet = tipoPet;
    }

    @Exclude
    public String getId() {
        return mId;
    }

    public String getNome() {
        return mNome;
    }

    public void setNome(String nome) {
        this.mNome = nome;
    }

    public String getDescricao() {
        return mDescricao;
    }

    public void setDescricao(String descricao) {
        this.mDescricao = descricao;
    }

    public String getValor() {
        return mValor;
    }

    public void setValor(String Valor) {
        this.mValor = Valor;
    }

    public String getTipoPet() {
        return mTipoPet;
    }

    public void setTipoPet(String mTipoPet) {
        this.mTipoPet = mTipoPet;
    }
}
