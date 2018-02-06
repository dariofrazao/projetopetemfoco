package projetaobcc20172.com.projetopetemfoco.database.services;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import projetaobcc20172.com.projetopetemfoco.R;
import projetaobcc20172.com.projetopetemfoco.config.ConfiguracaoFirebase;
import projetaobcc20172.com.projetopetemfoco.model.Favorito;
import projetaobcc20172.com.projetopetemfoco.utils.Utils;

/**
 * Created by LuizAlberes on 12/01/2018.
 */

public class FavoritoDaoImpl implements FavoritoDao {

    private DatabaseReference mReferenciaFirebase;
    private Context mContexto;

    public FavoritoDaoImpl(Context contexto) {
        this.mReferenciaFirebase = ConfiguracaoFirebase.getFirebase();
        this.mContexto = contexto;

    }

//    public void checharBancoVazio(final Favorito favorito, final String idUsuarioLogado){
//
//        mReferenciaFirebase.addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//
//                    favorito.setmIdUsuario(idUsuarioLogado);
//                    favorito.setChecar("0");
//
//                    mReferenciaFirebase = mReferenciaFirebase.child("favoritos").push();
//                    final String id = mReferenciaFirebase.getKey();
//                    favorito.setIdFavorito(id);
//
//
//                    mReferenciaFirebase.setValue(favorito).addOnCompleteListener(new OnCompleteListener<Void>() {
//                        @Override
//                        public void onComplete(@NonNull Task<Void> task) {
//                            if (task.isSuccessful()) {
//                                Utils.mostrarMensagemCurta(getContexto(), getContexto().getString(R.string.sucesso_cadastro));
//                            } else {
//                                Utils.mostrarMensagemCurta(getContexto(), getContexto().getString(R.string.erro_ao_cadastrar));
//                                try {
//                                    throw task.getException();
//                                } catch (Exception e) {
//                                    e.printStackTrace();
//                                }
//                            }
//                        }
//                    });
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//
//            }
//        });
//        Intent intent = new Intent(getContexto(), AcessoInformacoesEstabelecimentoActivity.class);
//        intent.putExtra("Favoritos", favorito);
//        getContexto().getApplicationContext().startActivity(intent);
//    }
//
//
//    @Override
//    public void inserir(final Favorito favorito, final String idUsuarioLogado) {
//
//        mReferenciaFirebase.child("favoritos").orderByChild("mIdUsuario")
//        .equalTo(idUsuarioLogado).addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//
//                for (DataSnapshot child: dataSnapshot.getChildren()) {
//                    String idFavorito = child.getKey();
//                    child.child("f");
//                    Log.d("User key", child.getKey());
//                    Log.d("User ref", child.getRef().toString());
//                    Log.d("User val", child.child("idFornecedor").getValue().toString());
//
//                    String idFornecedorBanco = child.child("idFornecedor").getValue().toString();
//                    String idUsuarioBanco = child.child("mIdUsuario").getValue().toString();
//
//                    Log.d("aqq", idFornecedorBanco);
//                    Log.d("aqq", favorito.getIdFornecedor());
//
//                    if(idFornecedorBanco.contains(favorito.getIdFornecedor())
//                            && idUsuarioBanco.contains(idUsuarioLogado)) {
//                        Log.d("User key", "Você já favoritou este estabelecimento");
//                    }
//
//                    else{
//
//                        favorito.setmIdUsuario(idUsuarioLogado);
//                        favorito.setChecar("1");
//
//                        mReferenciaFirebase = mReferenciaFirebase.child("favoritos").push();
//                        final String id = mReferenciaFirebase.getKey();
//                        favorito.setIdFavorito(id);
//
//
//                        mReferenciaFirebase.setValue(favorito).addOnCompleteListener(new OnCompleteListener<Void>() {
//                            @Override
//                            public void onComplete(@NonNull Task<Void> task) {
//                                if (task.isSuccessful()) {
//                                    Utils.mostrarMensagemCurta(getContexto(), getContexto().getString(R.string.sucesso_cadastro));
//                                } else {
//                                    Utils.mostrarMensagemCurta(getContexto(), getContexto().getString(R.string.erro_ao_cadastrar));
//                                    try {
//                                        throw task.getException();
//                                    } catch (Exception e) {
//                                        e.printStackTrace();
//                                    }
//                                }
//                            }
//                        });
//                    }
//                }
//
//
//                }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//
//            }
//        });
//}

    @Override
    public void inserir(final Favorito favorito, final String idUsuario) {

        favorito.setIdUsuario(idUsuario);
        mReferenciaFirebase = mReferenciaFirebase.child("favoritos").push();
        final String id = mReferenciaFirebase.getKey();
        favorito.setIdFavorito(id);


//        final String mGroupId = mReferenciaFirebase.getKey();
//        mReferenciaFirebase.child(mGroupId).setValue(new ChatGroup());


        mReferenciaFirebase.setValue(favorito).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Utils.mostrarMensagemCurta(getContexto(), getContexto().getString(R.string.favoritado));
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

        /*if (favorito.getChecar() == "0") {

            favorito.setmIdUsuario(idUsuarioLogado);
            favorito.setChecar("1");

            mReferenciaFirebase = mReferenciaFirebase.child("favoritos").push();
            final String id = mReferenciaFirebase.getKey();
            favorito.setIdFavorito(id);


            mReferenciaFirebase.setValue(favorito).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()) {
                        Utils.mostrarMensagemCurta(getContexto(), getContexto().getString(R.string.sucesso_cadastro));
                    } else {
                        Utils.mostrarMensagemCurta(getContexto(), getContexto().getString(R.string.erro_ao_cadastrar));
                        try {
                            throw task.getException();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            });


        }
        else{
            mReferenciaFirebase.child("favoritos").child(favorito.getIdFavorito()).child("checar")
                    .setValue("1");
        }*/


    public void compararInserir(final Favorito favorito, final String idUsuario) {

        mReferenciaFirebase.child("favoritos").orderByChild("idUsuario").equalTo(idUsuario).
                addListenerForSingleValueEvent(new ValueEventListener() {

                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        //Favorito favoritos = dataSnapshot.getValue(Favorito.class);
                        for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                            //Log.d("User key12", (postSnapshot.child("idFornecedor").getValue()).toString());
                            //Log.d("User key13", favorito.getIdFornecedor());
                            if(postSnapshot.child("idFornecedor").getValue().toString().equals(favorito.getIdFornecedor())){
                                Utils.mostrarMensagemCurta(getContexto(), getContexto().getString(R.string.existente));
                                favorito.setConfirma("1");
                                break;
                            }
                        }
                        if(favorito.getConfirma().equals("0")){
                            inserir(favorito, idUsuario);
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
    }

    public void compararRemover(final Favorito favorito, final String idUsuario) {

        mReferenciaFirebase.child("favoritos").orderByChild("idUsuario").equalTo(idUsuario).
                addListenerForSingleValueEvent(new ValueEventListener() {

                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                            if(postSnapshot.child("idFornecedor").getValue().toString().equals(favorito.getIdFornecedor())){
                                Utils.mostrarMensagemCurta(getContexto(), getContexto().getString(R.string.existente));
                                //favorito.setConfirma("1");
                                //remover(favorito, idUsuario);
                                break;
                            }
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
    }



    @Override
    public void remover(final Favorito favorito, final String idUsuario) {


        //Adicionar um listener no nó do pet que será removido
        //O método orderByChild ordena os pets pelo seu id e o equalTo busca o id do pet que será removido
        mReferenciaFirebase.child("favoritos").child(favorito.getIdFavorito()).orderByChild(idUsuario).
                equalTo(favorito.getIdFavorito()).addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                mReferenciaFirebase.child("favoritos").child(favorito.getIdFavorito()).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        //Se a remoçao foi feita com sucesso
                        if(task.isSuccessful()){
                            Utils.mostrarMensagemCurta(getContexto(), getContexto().getString(R.string.desfavoritado));
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


    private Context getContexto(){
        return this.mContexto;
    }

}
