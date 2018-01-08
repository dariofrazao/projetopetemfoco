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
import projetaobcc20172.com.projetopetemfoco.model.Vacina;
import projetaobcc20172.com.projetopetemfoco.utils.Utils;

/**
 * Created by Cloves on 07/01/2018.
 */

public class VacinaDaoImpl implements VacinaDao{

    private DatabaseReference mReferenciaFirebase;
    private Context mContext;

    public VacinaDaoImpl(Context contexto){
        this.mReferenciaFirebase = ConfiguracaoFirebase.getFirebase();
        this.mContext = contexto;
    }

    @Override
    public void inserir(Vacina vacina, String idUsuario,String petId) {
        //O método push() cria uma chave exclusiva para cada mPet cadastrado
        mReferenciaFirebase = mReferenciaFirebase.child("usuarios").child(idUsuario).child("pets").child(petId).child("calendarioVacinas").push();
        vacina.setId(mReferenciaFirebase.getKey());

        mReferenciaFirebase.setValue(vacina).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Utils.mostrarMensagemCurta(getContexto(), "sucesso ao cadastrar a vacina");
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
    public void remover(final Vacina vacina, final String idUsuario, final String petId) {
        //Adicionar um listener no nó do pet que será removido
        //O método orderByChild ordena os pets pelo seu id e o equalTo busca o id do pet que será removido
        mReferenciaFirebase.child("usuarios").child(idUsuario).child("pets").child(petId).child("calendarioVacinas").
                orderByChild("id").
                equalTo(vacina.getId()).addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //O método getKey() retorna o id do pet
                mReferenciaFirebase.child("usuarios").child(idUsuario).child("pets").child(petId).
                        child("calendarioVacinas").child(vacina.getId()).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        //Se a remoçao foi feita com sucesso
                        if(task.isSuccessful()){
                            Utils.mostrarMensagemCurta(getContexto(), "vacina removida do calendario");
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
    public void atualizar(final Vacina vacina, final String idUsuario, final String petId) {
        //Adicionar um listener no nó do pet que será editado
        //O método orderByChild ordena os pets pelo seu id e o equalTo busca o id do pet que será editado
        mReferenciaFirebase.child("usuarios").child(idUsuario).child("pets").child(petId).child("calendarioVacinas").orderByChild("id").
                equalTo(vacina.getId()).addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                mReferenciaFirebase.child("usuarios").child(idUsuario).child("pets").child(petId).
                        child("calendarioVacinas").child(vacina.getId()).setValue(vacina).addOnCompleteListener(new OnCompleteListener<Void>() {
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

    private Context getContexto(){
        return this.mContext;
    }
}
