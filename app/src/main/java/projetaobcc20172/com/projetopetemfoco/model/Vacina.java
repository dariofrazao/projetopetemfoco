package projetaobcc20172.com.projetopetemfoco.model;

import java.io.Serializable;

public class Vacina implements Serializable{

    private String mIdUsuario;
    private String mIdPet;
    private String id;
    private String mDescricao;
    private String mData;

    public Vacina() {

    }

    public String getIdPet() {
        return mIdPet;
    }

    public void setIdPet(String idPet) {
        this.mIdPet = idPet;
    }

    public String getIdUsuario() {
        return mIdUsuario;
    }

    public void setIdUsuario(String idUsuario) {
        this.mIdUsuario = idUsuario;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getmDescricao() {
        return mDescricao;
    }

    public void setmDescricao(String mDescricao) {
        this.mDescricao = mDescricao;
    }

    public String getmData() {
        return mData;
    }

    public void setmData(String mData) {
        this.mData = mData;
    }
}
