package projetaobcc20172.com.projetopetemfoco.database.services;

import java.util.List;

import projetaobcc20172.com.projetopetemfoco.model.Pet;

/**
 * Created by dario on 11/12/2017.
 */

public interface PetDao {

    void inserir(Pet pet, String idFornecedor);
    //void remover(Pet pet,  String idFornecedor);
    //void atualizar(Pet pet,  String idFornecedor);
    List<Pet> buscarPorNome(String nome);
    List<Pet> buscarTodosFornecedor(String idFornecedor);

}
