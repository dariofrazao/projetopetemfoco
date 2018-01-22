package projetaobcc20172.com.projetopetemfoco.utils;

import android.util.Log;

import java.util.Comparator;

/**
 * Created by Alexsandro on 21/01/2018.
 */

public class ServicoFornecedorComparatorAvaliacao implements Comparator<String[]>{
    @Override
    public int compare(String[] strings, String[] t1) {
        return Double.compare(Double.parseDouble(strings[7]),Double.parseDouble(t1[7]));
    }
}
