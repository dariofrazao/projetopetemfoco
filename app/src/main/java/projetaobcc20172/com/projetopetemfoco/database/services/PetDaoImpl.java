package projetaobcc20172.com.projetopetemfoco.database.services;

import android.content.Context;
import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;

import java.util.List;

import projetaobcc20172.com.projetopetemfoco.R;
import projetaobcc20172.com.projetopetemfoco.config.ConfiguracaoFirebase;
import projetaobcc20172.com.projetopetemfoco.model.Pet;
import projetaobcc20172.com.projetopetemfoco.utils.Utils;

/**
 * Created by dario on 11/12/2017.
 */

public class PetDaoImpl implements PetDao{


    private DatabaseReference mFirebase;
    private  final Context contexto;

    public PetDaoImpl(Context contexto){
        this.mFirebase = ConfiguracaoFirebase.getFirebase();
        this.contexto = contexto;
    }

    @Override
    public void inserir(Pet pet, String idUsuarioLogado) {
        mFirebase = mFirebase.child("usuarios").child(idUsuarioLogado);

        //O método push() cria uma chave exclusiva para cada mPet cadastrado
        mFirebase.child("pets").push().setValue(pet).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Utils.mostrarMensagemCurta(getContexto(), getContexto().getString(R.string.sucesso_cadastro_Pet));
                }
                else{
                    Utils.mostrarMensagemLonga(getContexto(), getContexto().getString(R.string.erro_ao_cadastrar));
                    try {
                        throw  task.getException();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    /*
    @Override
    public void remover(Pet pet, String idFornecedor) {
        referenciaFornecedor = referenciaFirebase.child("usuarios").child(idFornecedor);
        referenciaFornecedor.child(String.format("%s/%s", "pets", pet.getId()))
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
    }*/

    /*
    @Override
    public void atualizar(Pet pet, String idFornecedor) {
        referenciaFornecedor = referenciaFirebase.child("usuarios").child(idFornecedor);
        referenciaFornecedor.child(String.format("%s/%s", "servicos", pet.getId()))
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
    }*/

    @Override
    public List<Pet> buscarPorNome(String nome) {
        return null;
    }

    @Override
    public List<Pet> buscarTodosFornecedor(String idFornecedor) {
        return null;
    }


    private Context getContexto(){
        return this.contexto;
    }

}
