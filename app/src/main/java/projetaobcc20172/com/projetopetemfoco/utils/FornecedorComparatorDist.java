package projetaobcc20172.com.projetopetemfoco.utils;

import java.util.Comparator;

import projetaobcc20172.com.projetopetemfoco.model.Fornecedor;

/**
 * Created by raul1 on 19/01/2018.
 */

public class FornecedorComparatorDist implements Comparator<Fornecedor> {

    @Override
    public int compare(Fornecedor fornecedor, Fornecedor t1) {
        return Double.compare(fornecedor.getDistancia(),t1.getDistancia());
    }

}

