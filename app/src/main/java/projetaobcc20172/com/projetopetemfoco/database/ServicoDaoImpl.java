package projetaobcc20172.com.projetopetemfoco.database;

import android.content.Context;
import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;

import java.util.List;

import projetaobcc20172.com.projetopetemfoco.R;
import projetaobcc20172.com.projetopetemfoco.config.ConfiguracaoFirebase;
import projetaobcc20172.com.projetopetemfoco.database.services.ServicoDao;
import projetaobcc20172.com.projetopetemfoco.model.Servico;
import projetaobcc20172.com.projetopetemfoco.utils.Utils;

/**
 * Created by Felipe Oliveira on 07/12/17.
 * <flpdias14@gmail.com>
 */

public class ServicoDaoImpl implements ServicoDao{


    private DatabaseReference referenciaFirebase;
    private DatabaseReference referenciaFornecedor;
    private  final Context contexto;

    public ServicoDaoImpl(Context contexto){
        this.referenciaFirebase = ConfiguracaoFirebase.getFirebase();
        this.contexto = contexto;
    }

    @Override
    public void inserir(Servico servico, String idFornecedor) {
        referenciaFornecedor = referenciaFirebase.child("fornecedor").child(idFornecedor);
        referenciaFornecedor.child("servicos").push().setValue(servico).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Utils.mostrarMensagemLonga(getContexto(), getContexto().getString(R.string.sucesso_cadastro));
                }
                else{
                    Utils.mostrarMensagemLonga(getContexto(), getContexto().getString(R.string.sucesso_cadastro));
                    try {
                        throw  task.getException();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    @Override
    public void remover(Servico servico, String idFornecedor) {
        referenciaFornecedor = referenciaFirebase.child("fornecedor").child(idFornecedor);
        referenciaFornecedor.child(String.format("%s/%s", "servicos", servico.getId()))
                .setValue(null).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Utils.mostrarMensagemLonga(getContexto(), getContexto().getString(R.string.sucesso_remocao));
                }
                else{
                    Utils.mostrarMensagemLonga(getContexto(), getContexto().getString(R.string.falha_remocao));
                    try {
                        throw  task.getException();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    @Override
    public void atualizar(Servico servico, String idFornecedor) {
        referenciaFornecedor = referenciaFirebase.child("fornecedor").child(idFornecedor);
        referenciaFornecedor.child(String.format("%s/%s", "servicos", servico.getId()))
                .setValue(null).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Utils.mostrarMensagemLonga(getContexto(), getContexto().getString(R.string.sucesso_atualizacao));
                }
                else{
                    Utils.mostrarMensagemLonga(getContexto(), getContexto().getString(R.string.falha_atualizacao));
                    try {
                        throw  task.getException();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });
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
        return this.contexto;
    }

}
