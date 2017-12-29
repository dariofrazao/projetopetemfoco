package projetaobcc20172.com.projetopetemfoco.database.services;

import projetaobcc20172.com.projetopetemfoco.model.Pet;

/**
 * Created by dario on 11/12/2017.
 */

public interface PetDao {

    void inserir(Pet pet, String idUsuario);
    void remover(Pet pet,  String idUsuario);
    void atualizar(Pet pet,  String idUsuario);
    //List<Pet> buscarPorNome(String nome);
    //List<Pet> buscarTodosFornecedor(String idUsuario);

}
