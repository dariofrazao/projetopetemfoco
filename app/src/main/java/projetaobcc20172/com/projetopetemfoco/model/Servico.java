package projetaobcc20172.com.projetopetemfoco.model;

import com.google.firebase.database.Exclude;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Felipe Oliveira on 05/12/17.
 *
 */

public class Servico implements Serializable {
    private String mId;
    private String mNome;
    private String mDescricao;
    private String mValor;
    private String mTipoPet;
    private ArrayList<Avaliacao> mAvaliacoes = new ArrayList<>();

    public ArrayList<Avaliacao> getAvaliacoes() {
        return mAvaliacoes;
    }

    public void setAvaliacoes(ArrayList<Avaliacao> avaliacoes) {
        this.mAvaliacoes = avaliacoes;
    }

    public Servico(){
    }

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
