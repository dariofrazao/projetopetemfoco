package projetaobcc20172.com.projetopetemfoco.database.services;

import projetaobcc20172.com.projetopetemfoco.model.Vacina;

/**
 * Created by Cloves on 07/01/2018.
 */

public interface VacinaDao {

    void inserir(Vacina vacina, String idUsuario,String petId);
    void remover(Vacina vacina,  String idUsuario);
    void atualizar(Vacina vacina,  String idUsuario,String petId);
}
