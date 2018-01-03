package projetaobcc20172.com.projetopetemfoco.database.services;

import android.content.Context;
import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import projetaobcc20172.com.projetopetemfoco.R;
import projetaobcc20172.com.projetopetemfoco.config.ConfiguracaoFirebase;
import projetaobcc20172.com.projetopetemfoco.model.Pet;
import projetaobcc20172.com.projetopetemfoco.utils.Utils;

/**
 * Created by dario on 11/12/2017.
 */

public class PetDaoImpl implements PetDao{

    private DatabaseReference mReferenciaFirebase;
    private Context mContexto;

    public PetDaoImpl(Context contexto){
        this.mReferenciaFirebase = ConfiguracaoFirebase.getFirebase();
        this.mContexto = contexto;

    }


    @Override
    public void inserir(Pet pet, final String idUsuarioLogado) {

        //O método push() cria uma chave exclusiva para cada mPet cadastrado
        mReferenciaFirebase = mReferenciaFirebase.child("usuarios").child(idUsuarioLogado).child("pets").push();
        pet.setIdPet(mReferenciaFirebase.getKey());

        mReferenciaFirebase.setValue(pet).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Utils.mostrarMensagemCurta(getContexto(), getContexto().getString(R.string.sucesso_cadastro_Pet));
                }
                else{
                    Utils.mostrarMensagemCurta(getContexto(), getContexto().getString(R.string.erro_ao_cadastrar));
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
    public void remover(final Pet pet, final String idUsuarioLogado) {

        //Adicionar um listener no nó do pet que será removido
        //O método orderByChild ordena os pets pelo seu id e o equalTo busca o id do pet que será removido
        mReferenciaFirebase.child("usuarios").child(idUsuarioLogado).child("pets").orderByChild("idPet").
                equalTo(pet.getIdPet()).addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //O método getKey() retorna o id do pet
                mReferenciaFirebase.child("usuarios").child(idUsuarioLogado).child("pets").
                        child(pet.getIdPet()).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                //Se a remoçao foi feita com sucesso
                                if(task.isSuccessful()){
                                    Utils.mostrarMensagemCurta(getContexto(), getContexto().getString(R.string.sucesso_remocao_Pet));
                                }
                                //Senão
                                else{
                                    Utils.mostrarMensagemCurta(getContexto(), getContexto().getString(R.string.erro_ao_remover));
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
            public void onCancelled(DatabaseError databaseError) {
                //vazio
            }
        });
    }

    @Override
    public void atualizar(final Pet pet, final String idUsuarioLogado) {

        //Adicionar um listener no nó do pet que será editado
        //O método orderByChild ordena os pets pelo seu id e o equalTo busca o id do pet que será editado
        mReferenciaFirebase.child("usuarios").child(idUsuarioLogado).child("pets").orderByChild("idPet").
                equalTo(pet.getIdPet()).addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                mReferenciaFirebase.child("usuarios").child(idUsuarioLogado).child("pets").
                        child(pet.getIdPet()).setValue(pet).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                //Se a edição foi feita com sucesso
                                if(task.isSuccessful()){
                                    Utils.mostrarMensagemCurta(getContexto(), getContexto().getString(R.string.sucesso_atualizacao_Pet));
                                    }
                                //Senão
                                else{
                                    Utils.mostrarMensagemCurta(getContexto(), getContexto().getString(R.string.erro_ao_atualizar));
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
            public void onCancelled(DatabaseError databaseError) {
                //vazio
            }

        });
    }

    //@Override
    //public List<Pet> buscarPorNome(String nome) {
     //   return null;
    //}

    //@Override
    //public List<Pet> buscarTodosFornecedor(String idFornecedor) {
       // return null;
    //}

    private Context getContexto(){
        return this.mContexto;
    }

}
