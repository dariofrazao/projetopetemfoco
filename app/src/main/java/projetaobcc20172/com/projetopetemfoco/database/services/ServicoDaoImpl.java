package projetaobcc20172.com.projetopetemfoco.database.services;

import android.content.Context;

import com.google.firebase.database.DatabaseReference;

import java.util.List;


import projetaobcc20172.com.projetopetemfoco.config.ConfiguracaoFirebase;
import projetaobcc20172.com.projetopetemfoco.model.Servico;

/**
 * Created by Felipe Oliveira on 07/12/17.
 * <flpdias14@gmail.com>
 */

public class ServicoDaoImpl implements ServicoDao{


    private DatabaseReference mReferenciaFirebase;
    private final Context mContexto;

    public ServicoDaoImpl(Context contexto){
        this.mReferenciaFirebase = ConfiguracaoFirebase.getFirebase();
        this.mContexto = contexto;
    }

    @Override
    public List<Servico> buscarPorNome(String nome) {
        return null;
    }

    @Override
    public List<Servico> buscarTodosFornecedor(String idFornecedor) {
        return null;
    }


    private Context getContexto(){
        return this.mContexto;
    }

}
