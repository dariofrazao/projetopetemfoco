package projetaobcc20172.com.projetopetemfoco.model;

import java.io.Serializable;

public class Usuario implements Serializable {

    //Atributos do usuário
    private String mId;
    private String mNome;
    private String mEmail;
    private String mFoto;

    public Usuario(){}

    public Usuario(String idUsuario, String nome, String email, String foto){
        this.mId = idUsuario;
        this.mNome = nome;
        this.mEmail = email;
        this.mFoto = foto;
    }

    public String getId() {
        return mId;
    }

    public void setId(String id) {
        this.mId = id;
    }

    public String getNome() {
        return mNome;
    }

    public void setNome(String nome) {
        this.mNome = nome;
    }

    public String getEmail() {
        return mEmail;
    }

    public void setEmail(String email) {
        this.mEmail = email;
    }

    public String getmFoto() {
        return mFoto;
    }

    public void setmFoto(String mFoto) {
        this.mFoto = mFoto;
    }
}