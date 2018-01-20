package projetaobcc20172.com.projetopetemfoco.utils;

import java.util.Comparator;

/**
 * Created by raul1 on 20/01/2018.
 */

public class ServicoFornecedorComparatorDist implements Comparator<String[]> {
    @Override
    public int compare(String[] strings, String[] t1) {
        return Double.compare(Double.parseDouble(strings[6]),Double.parseDouble(t1[6]));
    }
}
