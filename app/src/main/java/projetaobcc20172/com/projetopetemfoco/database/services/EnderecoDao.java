package projetaobcc20172.com.projetopetemfoco.database.services;

import java.util.List;

import projetaobcc20172.com.projetopetemfoco.model.Endereco;
import projetaobcc20172.com.projetopetemfoco.model.Usuario;

/**
 * Created by dario on 11/12/2017.
 */

public interface EnderecoDao {

    void inserirUsuario(Usuario usuario, String idUsuario);
    void inserirEndereco(Endereco endereco, String idUsuario);
    void removerEndereco(Endereco endereco,  String idUsuario);
    void atualizarEndereco(Endereco endereco,  String idUsuario);
    List<Usuario> buscarPorNome(String nome);
    List<Usuario> buscarTodosUsuario(String idUsuario);

}
