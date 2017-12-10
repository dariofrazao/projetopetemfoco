package projetaobcc20172.com.projetopetemfoco.helper;

/**
 * Created by Alexsandro on 06/12/2017.
 */

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

//Classe que especifica máscaras a serem utilizadas nos cadastros
public class MaskUtil {
    private static final String CPFMask = "###.###.###-##";
    private static final String CNPJMask = "##.###.###/####-##";
    private static final String CEPMask = "#####-###";

    private static String unmask(String s) {
        return s.replaceAll("[^0-9]*", "");
    }

    private static String getDefaultMask(String str) {
        String defaultMask = CPFMask;
        if (str.length() == 14){
            defaultMask = CNPJMask;
        }else if(str.length() == 8){
            defaultMask = CEPMask;
        }
        return defaultMask;
    }

    public static TextWatcher insert(final EditText editText, final MaskType maskType) {
        return new TextWatcher() {

            boolean isUpdating;
            String oldValue = "";

            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String value = MaskUtil.unmask(s.toString());
                String mask;
                switch (maskType) {
                    case CPF:
                        mask = CPFMask;
                        break;
                    case CNPJ:
                        mask = CNPJMask;
                        break;
                    case CEP:
                        mask = CEPMask;
                        break;
                    default:
                        mask = getDefaultMask(value);
                        break;
                }

                String maskAux = "";
                if (isUpdating) {
                    oldValue = value;
                    isUpdating = false;
                    return;
                }
                int i = 0;
                for (char m : mask.toCharArray()) {
                    if ((m != '#' && value.length() > oldValue.length()) || (m != '#' && value.length() < oldValue.length() && value.length() != i)) {
                        maskAux += m;
                        continue;
                    }

                    try {
                        maskAux += value.charAt(i);
                    } catch (Exception e) {
                        break;
                    }
                    i++;
                }
                isUpdating = true;
                editText.setText(maskAux);
                editText.setSelection(maskAux.length());
            }

            public void beforeTextChanged(CharSequence s, int start, int count,int after) {}
            public void afterTextChanged(Editable s) {}
        };
    }

    public enum MaskType{
        CPF,CNPJ,CEP
    }
}