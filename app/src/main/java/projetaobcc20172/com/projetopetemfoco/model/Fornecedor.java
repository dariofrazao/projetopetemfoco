package projetaobcc20172.com.projetopetemfoco.model;

import com.google.firebase.database.Exclude;

import java.io.Serializable;


/**
 * Created by renat on 06/12/2017.
 */

public class Fornecedor implements Serializable {

    //Atributos do fornecedor
    private String mId;
    private String mNome;
    private String mEmail;
    private String mTelefone;
    private String mCpfCnpj;
    private String mHorarios;
    private String mSenha;
    private String mSenha2;
    private String mValor = "1";
    private Endereco mEndereco;


    public Fornecedor(){

    }

    @Exclude
    public String getId() { return mId; }

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

    public String getTelefone() {
        return mTelefone;
    }

    public void setTelefone(String telefone) {
        this.mTelefone = telefone;
    }

    public String getCpf_cnpj() {
        return mCpfCnpj;
    }

    public void setCpf_cnpj(String cpf_cnpj) {
        this.mCpfCnpj = cpf_cnpj;
    }

    public String getHorarios() {
        return mHorarios;
    }

    public void setHorarios(String horarios) {
        this.mHorarios = horarios;
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

    public String getValor() {
        return mValor;
    }

    public void setValor(String valor) {
        this.mValor = valor;
    }
}
