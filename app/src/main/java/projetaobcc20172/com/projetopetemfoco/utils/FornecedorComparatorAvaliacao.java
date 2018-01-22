package projetaobcc20172.com.projetopetemfoco.utils;

import java.util.Comparator;

import projetaobcc20172.com.projetopetemfoco.model.Fornecedor;

/**
 * Created by Master on 22/01/2018.
 */

public class FornecedorComparatorAvaliacao  implements Comparator<Fornecedor> {
    @Override
    public int compare(Fornecedor fornecedor, Fornecedor t1) {
        return Double.compare(fornecedor.getNota(),t1.getNota());
    }
}

