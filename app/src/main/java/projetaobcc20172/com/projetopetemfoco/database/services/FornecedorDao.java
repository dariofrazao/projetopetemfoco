package projetaobcc20172.com.projetopetemfoco.database.services;

import java.util.List;

import projetaobcc20172.com.projetopetemfoco.model.Fornecedor;

/**
 * Created by dario on 11/12/2017.
 */

public interface FornecedorDao {
    List<Fornecedor> buscarPorNome(String nome);
    List<Fornecedor> buscarTodosFornecedor(String idFornecedor);

}
