package projetaobcc20172.com.projetopetemfoco.model;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Exclude;

import projetaobcc20172.com.projetopetemfoco.config.ConfiguracaoFirebase;

/**
 * Created by renat on 06/12/2017.
 */

public class Fornecedor {
    //Atributos do usuário
    private String id;
    private String nome;
    private String email;
    private String telefone;
    private String cpf_cnpj;
    private String horarios;
    private String senha;



    public Fornecedor(){

    }
    public void salvar(){ //Método para salvar usuário no banco de dados do Firebase
        DatabaseReference referenciaFirebase = ConfiguracaoFirebase.getFirebase(); //Chama a referência do Firebase
        referenciaFirebase.child("fornecedor").child( getId() ).setValue( this ); //Cria os nós dos usuário no banco de dados
    }

    @Exclude
    public String getId() { return id; }

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

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getCpf_cnpj() {
        return cpf_cnpj;
    }

    public void setCpf_cnpj(String cpf_cnpj) {
        this.cpf_cnpj = cpf_cnpj;
    }

    public String getHorarios() {
        return horarios;
    }

    public void setHorarios(String horarios) {
        this.horarios = horarios;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }


}
