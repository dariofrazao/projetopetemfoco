package projetaobcc20172.com.projetopetemfoco.database.services;

/**
 * Created by Alexsandro on 24/01/2018.
 */

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import projetaobcc20172.com.projetopetemfoco.R;
import projetaobcc20172.com.projetopetemfoco.config.ConfiguracaoFirebase;
import projetaobcc20172.com.projetopetemfoco.model.Avaliacao;
import projetaobcc20172.com.projetopetemfoco.utils.Utils;


public class AvaliacaoServicoDaoImpl implements AvaliacaoServicoDao {
    private DatabaseReference mReferenciaFirebase;
    private Context mContexto;

    public AvaliacaoServicoDaoImpl(Context contexto) {
        this.mReferenciaFirebase = ConfiguracaoFirebase.getFirebase();
        this.mContexto = contexto;
    }

    private Context getContexto() {
        return this.mContexto;
    }

    @Override
    public void inserir(Avaliacao avaliacao, final String idServico) {
        //O método push() cria uma chave exclusiva para cada mAvaliacao cadastrada
        DatabaseReference mFireBase;
        mFireBase = mReferenciaFirebase.child("servico_fornecedor").child(idServico).child("avaliacao").push();
        avaliacao.setId(mReferenciaFirebase.getKey());
        mFireBase.setValue(avaliacao).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Utils.mostrarMensagemCurta(getContexto(), getContexto().getString(R.string.sucesso_avaliacao_servico));
                    atualizaNota(idServico);
                } else {
                    Utils.mostrarMensagemCurta(getContexto(), getContexto().getString(R.string.erro_avaliacao_servico));
                    try {
                        throw task.getException();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    public void atualizaNota(final String idServico) {
        //Buscar avaliações do serviço selecionado
        DatabaseReference mFireBase;
        mFireBase = ConfiguracaoFirebase.getFirebase().child("servico_fornecedor").child(idServico).child("avaliacao");
        mFireBase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                float mNota = 0;
                int contador = 0;
                int estrelas = 0;
                for (DataSnapshot dados : dataSnapshot.getChildren()) {
                    Avaliacao avaliacao = dados.getValue(Avaliacao.class);
                    estrelas = estrelas + avaliacao.getEstrelas();
                    contador++;
                    mNota = (float) estrelas / contador;
                }
                inserirNota(idServico, String.valueOf(mNota));
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // vazio
            }
        });
    }

    @Override
    public void inserirNota(final String idServico, final String mNota) {
        //Adicionar um listener no nó do servico que será editado
        //O método orderByChild ordena os serviços pelo seu id e o equalTo busca o id do serviço que será editado
        final DatabaseReference mFireBase = mReferenciaFirebase;
        mFireBase.child("servico_fornecedor").child(idServico).orderByChild("id").equalTo(idServico).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                mFireBase.child("servico_fornecedor").child(idServico).child("nota").setValue(mNota).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        //Se a edição foi feita com sucesso
                        if (task.isSuccessful()) {
                            Log.i("Debug", "Suceso ao atualizar nota ");
                        }
                        //Senão
                        else {
                            Log.i("Debug", "Erro ao atualizar nota ");
                            try {
                                throw task.getException();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }
                });

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                //Vazio
            }
        });

    }
}
