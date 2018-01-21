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
    public void inserir(Vacina vacina, String idUsuario, String petId) {

        vacina.setIdUsuario(idUsuario);
        vacina.setIdPet(petId);

        //O método push() cria uma chave exclusiva para cada vacina cadastrada
        mReferenciaFirebase = mReferenciaFirebase.child("vacinas").push();
        final String id = mReferenciaFirebase.getKey();
        vacina.setId(id);

        mReferenciaFirebase.setValue(vacina).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Utils.mostrarMensagemCurta(getContexto(), getContexto().getString(R.string.sucesso_cadastro_vacina));
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
    public void remover(final Vacina vacina, final String idUsuario) {

        //Adicionar um listener no nó da vacina que será removida
        //O método orderByChild ordena as vacinas pelo seu id e o equalTo busca o id da vacina que será removida
        mReferenciaFirebase.child("vacinas").child(vacina.getId()).orderByChild(idUsuario).
                equalTo(vacina.getId()).addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                mReferenciaFirebase.child("vacinas").child(vacina.getId()).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        //Se a remoçao foi feita com sucesso
                        if(task.isSuccessful()){
                            Utils.mostrarMensagemCurta(getContexto(), getContexto().getString(R.string.sucesso_remocao_vacina));
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

        vacina.setIdUsuario(idUsuario);

        //Adicionar um listener no nó da vacina que será editada
        //O método orderByChild ordena as vacinas pelo seu id e o equalTo busca o id da vacina que será editada
        mReferenciaFirebase.child("vacinas").child(vacina.getId()).
                equalTo(vacina.getId()).addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                mReferenciaFirebase.child("vacinas").child(vacina.getId()).setValue(vacina).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        //Se a edição foi feita com sucesso
                        if(task.isSuccessful()){
                            Utils.mostrarMensagemCurta(getContexto(), getContexto().getString(R.string.sucesso_atualizacao_vacina));
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
