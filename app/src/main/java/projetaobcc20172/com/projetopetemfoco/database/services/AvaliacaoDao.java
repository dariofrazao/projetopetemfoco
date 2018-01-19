package projetaobcc20172.com.projetopetemfoco.database.services;

import java.util.ArrayList;
import java.util.List;
import projetaobcc20172.com.projetopetemfoco.model.Avaliacao;
import projetaobcc20172.com.projetopetemfoco.model.Fornecedor;

/**
 * Created by Alexsandro on 08/01/2018.
 */

public interface AvaliacaoDao {
    void inserir(Avaliacao avaliacao, Fornecedor fornecedor);
//   void remover(Avaliacao avaliacao,  String idUsuario, String idFornecedor);
    void atualizar(Avaliacao avaliacao,  String idUsuario, String idFornecedor);
    ArrayList<Avaliacao> buscarPorFornecedor(Fornecedor fornecedor);
    //List<Avaliacao> buscarPorUsuario(String idUsuario);
}
