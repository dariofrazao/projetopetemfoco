package projetaobcc20172.com.projetopetemfoco.database.services;

/**
 * Created by Alexsandro on 08/01/2018.
 */

import android.content.Context;
import android.support.annotation.NonNull;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;
import projetaobcc20172.com.projetopetemfoco.R;
import projetaobcc20172.com.projetopetemfoco.config.ConfiguracaoFirebase;
import projetaobcc20172.com.projetopetemfoco.model.Avaliacao;
import projetaobcc20172.com.projetopetemfoco.model.Fornecedor;
import projetaobcc20172.com.projetopetemfoco.utils.Utils;


public class AvaliacaoDaoImpl implements AvaliacaoDao {
    private ArrayList<Avaliacao> mAvaliacoes = new ArrayList<>();
    private DatabaseReference mReferenciaFirebase;
    private Context mContexto;

    public AvaliacaoDaoImpl(Context contexto) {
        this.mReferenciaFirebase = ConfiguracaoFirebase.getFirebase();
        this.mContexto = contexto;
    }

    private Context getContexto() {
        return this.mContexto;
    }

    @Override
    public void inserir(Avaliacao avaliacao, Fornecedor fornecedor) {
        final String idFornecedor = fornecedor.getId();
        //O método push() cria uma chave exclusiva para cada mAvaliacao cadastrada
        DatabaseReference mFireBase;
        mFireBase = mReferenciaFirebase.child("fornecedor").child(fornecedor.getId()).child("avaliacao").push();
        avaliacao.setId(mReferenciaFirebase.getKey());
        mFireBase.setValue(avaliacao).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Utils.mostrarMensagemCurta(getContexto(), getContexto().getString(R.string.sucesso_avaliacao_estabelecimento));
                    atualizaNota(idFornecedor);
                } else {
                    Utils.mostrarMensagemCurta(getContexto(), getContexto().getString(R.string.erro_avaliacao_estabelecimento));
                    try {
                        throw task.getException();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    public void atualizaNota(final String idFornecedor) {
        //Buscar avaliações do estabelecimento selecionado
        DatabaseReference mFireBase;
        mFireBase = ConfiguracaoFirebase.getFirebase().child("fornecedor").child(idFornecedor).child("avaliacao");
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
                inserirNota(idFornecedor, mNota);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                assert true;
            }
        });
    }

    @Override
    public void inserirNota(final String idFornecedor, final float mNota) {
        //Adicionar um listener no nó do fornecedor que será editado
        //O método orderByChild ordena os fornecedores pelo seu id e o equalTo busca o id do forncedor que será editado
        final DatabaseReference mFireBase = mReferenciaFirebase;
        mFireBase.child("fornecedor").child(idFornecedor).orderByChild("id").equalTo(idFornecedor).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                mFireBase.child("fornecedor").child(idFornecedor).child("nota").setValue(mNota).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        //Se a edição foi feita com sucesso
                        if (task.isSuccessful()) {
                            Utils.mostrarMensagemCurta(getContexto(), getContexto().getString(R.string.sucesso_atualizacao_nota));
                        }
                        //Senão
                        else {
                            Utils.mostrarMensagemCurta(getContexto(), getContexto().getString(R.string.erro_atualizacao_nota));
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

            }
        });

    }
}
