package projetaobcc20172.com.projetopetemfoco.model;

import java.io.Serializable;

public class Usuario implements Serializable {

    //Atributos do usuário
    private String mId;
    private String mNome;
    private String mEmail;
    private String mEnderecoUsuario = "0";

    public Usuario(String idUsuario, String nome, String email){
        this.mId = idUsuario;
        this.mNome = nome;
        this.mEmail = email;
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

    public String getEnderecoUsuario() {
        return mEnderecoUsuario;
    }

    public void setEnderecoUsuario(String enderecoUsuario) {
        this.mEnderecoUsuario = enderecoUsuario;
    }
}