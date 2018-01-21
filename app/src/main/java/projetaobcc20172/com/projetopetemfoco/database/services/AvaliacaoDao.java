package projetaobcc20172.com.projetopetemfoco.database.services;

import java.util.ArrayList;
import projetaobcc20172.com.projetopetemfoco.model.Avaliacao;
import projetaobcc20172.com.projetopetemfoco.model.Fornecedor;

/**
 * Created by Alexsandro on 08/01/2018.
 */

public interface AvaliacaoDao {
    void inserir(Avaliacao avaliacao, Fornecedor fornecedor);
    void inserirNota(String idFornecedor, float nota);
}
