package projetaobcc20172.com.projetopetemfoco.utils;

import android.util.Log;

import java.util.Comparator;

/**
 * Created by Alexsandro on 21/01/2018.
 */

public class ServicoFornecedorComparatorAvaliacao implements Comparator<String[]>{
    @Override
    public int compare(String[] strings, String[] t1) {
        if(strings[7]!=null) {
            return Double.compare(Double.parseDouble(strings[7]), Double.parseDouble(t1[7]));
        }else{
            return Double.compare(0.0, Double.parseDouble(t1[7]));
        }
    }
}
