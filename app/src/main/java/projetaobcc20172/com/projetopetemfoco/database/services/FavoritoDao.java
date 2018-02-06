package projetaobcc20172.com.projetopetemfoco.database.services;

import projetaobcc20172.com.projetopetemfoco.model.Favorito;

/**
 * Created by LuizAlberes on 11/12/2017.
 */

public interface FavoritoDao {

    void inserir(Favorito favorito, String idUsuario);
    void compararInserir(Favorito favorito, String idUsuario);
    void compararRemover(Favorito favorito, String idUsuario);
    void remover(Favorito favorito, String idUsuario);

}
