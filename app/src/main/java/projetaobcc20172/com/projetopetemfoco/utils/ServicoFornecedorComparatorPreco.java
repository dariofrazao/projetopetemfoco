package projetaobcc20172.com.projetopetemfoco.utils;

import java.util.Comparator;

/**
 * Created by raul1 on 20/01/2018.
 */

public class ServicoFornecedorComparatorPreco implements Comparator<String[]> {
    @Override
    public int compare(String[] strings, String[] t1) {
        return Double.compare(Double.parseDouble(Utils.moedaParaNumero(strings[2])),Double.parseDouble(Utils.moedaParaNumero(t1[2])));
    }
}
