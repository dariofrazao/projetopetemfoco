package projetaobcc20172.com.projetopetemfoco.utils;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;

/**
 * Classe de Rotinas úteis.
 * Criada por Felipe Oliveira em 05/12/17.
 */

public class Utils {

    /**
     * Construtor privado, todos os métodos são estáticos
     */
    private Utils(){}

    /**
     * Checa se o dispositivo tem conexão com a internet.
     * @param activity Activity atual
     * @return {true} se há conectividade
     */
    public static boolean checarConexaoInternet(Activity activity){
        ConnectivityManager connectivityManager = (ConnectivityManager) activity.getSystemService(Context.CONNECTIVITY_SERVICE);

        return (connectivityManager != null) &&
                (connectivityManager.getActiveNetworkInfo() != null) &&
                (connectivityManager.getActiveNetworkInfo().isAvailable()) &&
                (connectivityManager.getActiveNetworkInfo().isConnected());

    }
}
