package projetaobcc20172.com.projetopetemfoco.model;

import com.google.firebase.database.Exclude;

public class Endereco {

    //Atributos do endereço
    private String mId;
    private String mLogradouro;
    private String mNumero;
    private String mComplemento;
    private String mBairro;
    private String mLocalidade;
    private String mUf;
    private String mCep;

    public Endereco(String logradouro,String bairro,String localidade,String uf) {
        this.mLogradouro = logradouro;
        this.mBairro = bairro;
        this.mLocalidade = localidade;
        this.mUf = uf;
    }

    public Endereco(){}

    @Exclude
    public String getId() {
        return mId;
    }

    public void setId(String id) {
        this.mId = id;
    }

    public String getLogradouro() {
        return mLogradouro;
    }

    public void setLogradouro(String logradouro) {
        this.mLogradouro = logradouro;
    }

    public String getNumero() {
        return mNumero;
    }

    public void setNumero(String numero) {
        this.mNumero = numero;
    }

    public String getComplemento() {
        return mComplemento;
    }

    public void setComplemento(String complemento) {
        this.mComplemento = complemento;
    }

    public String getBairro() {
        return mBairro;
    }

    public void setBairro(String bairro) {
        this.mBairro = bairro;
    }

    public String getLocalidade() {
        return localidade;
    }

    public void setLocalidade(String localidade) {
        this.localidade = localidade;
    }

    public String getUf() {
        return mUf;
    }

    public void setUf(String uf) {
        this.mUf = uf;
    }

    public String getCep() {
        return mCep;
    }

    public void setCep(String cep) {
        this.mCep = cep;
    }
}