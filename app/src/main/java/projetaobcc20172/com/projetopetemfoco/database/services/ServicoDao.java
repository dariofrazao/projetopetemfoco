package projetaobcc20172.com.projetopetemfoco.database.services;


import java.util.List;

import projetaobcc20172.com.projetopetemfoco.model.Servico;

/**
 * Created by Felipe Oliveira on 08/12/17.
 * <flpdias14@gmail.com>
 */

public interface ServicoDao {

    List<Servico> buscarPorNome(String nome);
    List<Servico> buscarTodosFornecedor(String idFornecedor);

}
