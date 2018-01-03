package projetaobcc20172.com.projetopetemfoco.database.services;

import android.content.Context;
import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;

import java.util.List;

import projetaobcc20172.com.projetopetemfoco.R;
import projetaobcc20172.com.projetopetemfoco.config.ConfiguracaoFirebase;
import projetaobcc20172.com.projetopetemfoco.model.Fornecedor;
import projetaobcc20172.com.projetopetemfoco.utils.Utils;

/**
 * Created by dario on 11/12/2017.
 */

public class FornecedorDaoImpl implements FornecedorDao{

    private DatabaseReference mReferenciaFirebase;
    private  final Context mContexto;

    public FornecedorDaoImpl(Context contexto){
        this.mReferenciaFirebase = ConfiguracaoFirebase.getFirebase();
        this.mContexto = contexto;
    }

    @Override
    public List<Fornecedor> buscarPorNome(String nome) {
        return null;
    }

    @Override
    public List<Fornecedor> buscarTodosFornecedor(String idFornecedor) {
        return null;
    }


    private Context getContexto(){
        return this.mContexto;
    }
}
