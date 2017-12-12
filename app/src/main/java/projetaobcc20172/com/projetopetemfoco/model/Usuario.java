package projetaobcc20172.com.projetopetemfoco.model;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Exclude;

import java.io.Serializable;

import projetaobcc20172.com.projetopetemfoco.config.ConfiguracaoFirebase;

public class Usuario implements Serializable {

    //Atributos do usuário
    private String mId;
    private String mNome;
    private String mEmail;
    private String mSenha;
    private String mSenha2;
    private Endereco mEndereco;
    private String mValor = "0";
    private Pet mPet;

    public Usuario(){
    }

    public Usuario(String nome,String email,String senha, String senha2){
        this.mNome = nome;
        this.mEmail = email;
        this.mSenha = senha;
        this.mSenha2 = senha2;
    }

    @Exclude
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

    @Exclude
    public String getSenha() {
        return mSenha;
    }

    public void setSenha(String senha) {
        this.mSenha = senha;
    }

    @Exclude
    public String getSenha2() {
        return mSenha2;
    }

    public void setSenha2(String senha2) {
        this.mSenha2 = senha2;
    }

    public Endereco getEndereco() {
        return mEndereco;
    }

    public void setEndereco(Endereco endereco) {
        this.mEndereco = endereco;
    }

    public Pet getPet() {
        return mPet;
    }

    public void setPet(Pet pet) {
        this.mPet = pet;
    }

    public String getValor() {
        return mValor;
    }

    public void setValor(String valor) {
        this.mValor = valor;
    }
}