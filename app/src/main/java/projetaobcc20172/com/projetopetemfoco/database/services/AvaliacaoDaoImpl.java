package projetaobcc20172.com.projetopetemfoco.database.services;

/**
 * Created by Alexsandro on 08/01/2018.
 */

import android.content.Context;
import android.support.annotation.NonNull;
import android.widget.ArrayAdapter;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import projetaobcc20172.com.projetopetemfoco.R;
import projetaobcc20172.com.projetopetemfoco.config.ConfiguracaoFirebase;
import projetaobcc20172.com.projetopetemfoco.model.Avaliacao;
import projetaobcc20172.com.projetopetemfoco.model.Fornecedor;
import projetaobcc20172.com.projetopetemfoco.utils.Utils;


    public class AvaliacaoDaoImpl implements AvaliacaoDao{
        private ArrayList<Avaliacao> mAvaliacoes = new ArrayList<>();
        private ArrayAdapter<Avaliacao> mAdapter;

        private DatabaseReference mReferenciaFirebase;
        private Context mContexto;

        public AvaliacaoDaoImpl(Context contexto){
            this.mReferenciaFirebase = ConfiguracaoFirebase.getFirebase();
            this.mContexto = contexto;

        }

        private Context getContexto(){
            return this.mContexto;
        }

        @Override
        public void inserir(Avaliacao avaliacao, Fornecedor fornecedor) {
          //O método push() cria uma chave exclusiva para cada mAvaliacao cadastrada
            mReferenciaFirebase = mReferenciaFirebase.child("fornecedor").child(fornecedor.getId()).child("avaliacao").push();
            avaliacao.setId(mReferenciaFirebase.getKey());
            mReferenciaFirebase.setValue(avaliacao).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if(task.isSuccessful()){
                        Utils.mostrarMensagemCurta(getContexto(), getContexto().getString(R.string.sucesso_avaliacao_estabelecimento));
                    }
                    else{
                        Utils.mostrarMensagemCurta(getContexto(), getContexto().getString(R.string.erro_avaliacao_estabelecimento));
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
        public void atualizar(Avaliacao avaliacao, String idUsuario, String idFornecedor) {

            mReferenciaFirebase = mReferenciaFirebase.child("fornecedor").child(idFornecedor);
            mReferenciaFirebase.child(String.format("%s/%s", "avaliacao", avaliacao.getId()))
                    .setValue(avaliacao).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()) {
                        Utils.mostrarMensagemLonga(getContexto(), getContexto().getString(R.string.sucesso_atualizacao));
                    } else {
                        Utils.mostrarMensagemLonga(getContexto(), getContexto().getString(R.string.falha_atualizacao));
                        try {
                            throw task.getException();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            });

        }

        public ArrayList<Avaliacao> buscarPorFornecedor(final Fornecedor fornecedor) {
            //Buscar avaliações do estabelecimento selecionado
            DatabaseReference mFireBase;
            mFireBase = ConfiguracaoFirebase.getFirebase().child("fornecedor").child(fornecedor.getId()).child("avaliacao");
            mFireBase.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    for (DataSnapshot dados : dataSnapshot.getChildren()) {
                        Avaliacao avaliacao = dados.getValue(Avaliacao.class);
                        mAvaliacoes.add((avaliacao));
                    }
                    mAdapter.notifyDataSetChanged();
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    assert true;
                }
            });
            return  mAvaliacoes;
        }
    }
