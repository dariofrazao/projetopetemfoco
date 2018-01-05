package projetaobcc20172.com.projetopetemfoco.database.services;

import android.widget.ArrayAdapter;

import java.util.ArrayList;
import java.util.List;

import projetaobcc20172.com.projetopetemfoco.model.Fornecedor;

/**
 * Created by dario on 11/12/2017.
 */

public interface FornecedorDao {
    ArrayList<Fornecedor> buscarPorNome(String nome, ArrayAdapter<Fornecedor> adp);
    List<Fornecedor> buscarTodosFornecedor();

}
