package projetaobcc20172.com.projetopetemfoco.model;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Exclude;

import java.io.Serializable;

import projetaobcc20172.com.projetopetemfoco.config.ConfiguracaoFirebase;

public class Usuario implements Serializable{

    //Atributos do usuário
    private String id;
    private String nome;
    private String email;
    private String senha;
    private Endereco endereco;

    public Usuario(){

    }

    public void salvar(){ //Método para salvar usuário no banco de dados do Firebase
        DatabaseReference referenciaFirebase = ConfiguracaoFirebase.getFirebase(); //Chama a referência do Firebase
        referenciaFirebase.child("usuarios").child( getId() ).setValue( this ); //Cria os nós dos usuário no banco de dados
    }

    @Exclude
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Exclude
    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public Endereco getEndereco() { return endereco; }

    public void setEndereco(Endereco endereco) { this.endereco = endereco; }
}