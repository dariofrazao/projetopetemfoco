package projetaobcc20172.com.projetopetemfoco.database.services;

import java.util.List;

import projetaobcc20172.com.projetopetemfoco.model.Endereco;
import projetaobcc20172.com.projetopetemfoco.model.Pet;
import projetaobcc20172.com.projetopetemfoco.model.Usuario;

/**
 * Created by dario on 11/12/2017.
 */

public interface UsuarioDao {

    void inserir(Usuario usuario, String idUsuario);
    //void remover(Usuario usuario,  String idUsuario);
    //void atualizar(Usuario usuario,  String idUsuario);
    List<Usuario> buscarPorNome(String nome);
    List<Usuario> buscarTodosUsuario(String idUsuario);

}
