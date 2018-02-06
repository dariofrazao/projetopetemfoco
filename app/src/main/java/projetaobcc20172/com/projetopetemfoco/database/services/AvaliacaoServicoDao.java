package projetaobcc20172.com.projetopetemfoco.database.services;

import projetaobcc20172.com.projetopetemfoco.model.Avaliacao;
import projetaobcc20172.com.projetopetemfoco.model.Servico;

/**
 * Created by Alexsandro on 24/01/2018.
 */

public interface AvaliacaoServicoDao {
    void inserir(Avaliacao avaliacao, String idServico);
    void inserirNota(String idServico, String nota);
}
