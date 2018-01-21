package projetaobcc20172.com.projetopetemfoco.database.services;

import projetaobcc20172.com.projetopetemfoco.model.Endereco;
import projetaobcc20172.com.projetopetemfoco.model.Usuario;

/**
 * Created by dario on 11/12/2017.
 */

public interface EnderecoDao {

    void inserirUsuario(Usuario usuario, String idUsuario);
    void inserir(Endereco endereco, String idUsuario);
    void remover(Endereco endereco,  String idUsuario);
    void atualizar(Endereco endereco,  String idUsuario);

}
