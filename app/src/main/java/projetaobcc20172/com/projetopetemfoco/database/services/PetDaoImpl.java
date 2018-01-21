package projetaobcc20172.com.projetopetemfoco.database.services;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

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
    private FirebaseStorage storage;
    private StorageReference storageReference;

    public PetDaoImpl(Context contexto){
        this.mReferenciaFirebase = ConfiguracaoFirebase.getFirebase();
        this.mContexto = contexto;
        this.storage = FirebaseStorage.getInstance();
        this.storageReference = storage.getReference();

    }


    @Override
    public void inserir(final Pet pet, final String idUsuario, byte[] imagem) {

        pet.setIdUsuario(idUsuario);
        //O método push() cria uma chave exclusiva para cada mPet cadastrado

        mReferenciaFirebase = mReferenciaFirebase.child("pets").push();
        final String id = mReferenciaFirebase.getKey();
        pet.setIdPet(id);

        if(imagem != null){

            //Salvar imagem do pet no Firebase Storage
            StorageReference ref = storageReference.child("imagensPets/"+ idUsuario+"/"+ pet.getIdPet()+"/"+ pet.getIdPet());
            UploadTask uploadTask = ref.putBytes(imagem);

            uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Uri downloadUrl = taskSnapshot.getDownloadUrl();
                    String stringDownload = downloadUrl.toString();
                    pet.setFoto(stringDownload);
                    mReferenciaFirebase.setValue(pet);
                }
            });
        }

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
    public void remover(final Pet pet, final String idUsuario) {

        //Remover imagem do pet do Firebase Storage
        StorageReference ref = storageReference.child("imagensPets/"+ idUsuario+"/"+ pet.getIdPet()+"/"+ pet.getIdPet());
        ref.delete();

        //Adicionar um listener no nó do pet que será removido
        //O método orderByChild ordena os pets pelo seu id e o equalTo busca o id do pet que será removido
        mReferenciaFirebase.child("pets").child(pet.getIdPet()).orderByChild(idUsuario).
                equalTo(pet.getIdPet()).addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                mReferenciaFirebase.child("pets").child(pet.getIdPet()).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
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
    public void atualizar(final Pet pet, final String idUsuario, final byte[] imagem) {

        pet.setIdUsuario(idUsuario);

        //Adicionar um listener no nó do pet que será editado
        mReferenciaFirebase.child("pets").child(pet.getIdPet()).
                equalTo(pet.getIdPet()).addListenerForSingleValueEvent(new ValueEventListener() {

                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if(imagem != null){

                        //Atualizar imagem do pet no Firebase Storage
                        StorageReference ref = storageReference.child("imagensPets/"+ idUsuario+"/"+ pet.getIdPet()+"/"+ pet.getIdPet());
                        UploadTask uploadTask = ref.putBytes(imagem);

                        uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                                Uri downloadUrl = taskSnapshot.getDownloadUrl();
                                String stringDownload = downloadUrl.toString();
                                pet.setFoto(stringDownload);
                                mReferenciaFirebase.child("pets").child(pet.getIdPet())
                                        .setValue(pet);
                            }
                        });
                    }
                    mReferenciaFirebase.child("pets").child(pet.getIdPet())
                            .setValue(pet).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            //Se a edição foi feita com sucesso
                            if (task.isSuccessful()) {
                                Utils.mostrarMensagemCurta(getContexto(), getContexto().getString(R.string.sucesso_atualizacao_Pet));
                            }
                            //Senão
                            else {
                                Utils.mostrarMensagemCurta(getContexto(), getContexto().getString(R.string.erro_ao_atualizar));
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
                    //vazio
                }

            });
    }

    //Método que remove a foto do pet apenas do Firebase Storage
    public void removerFoto(final Pet pet, final String idUsuario) {

        StorageReference ref = storageReference.child("imagensPets/" + idUsuario + "/" + pet.getIdPet() + "/" + pet.getIdPet());
        ref.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                mReferenciaFirebase.child("pets").child(pet.getIdPet()).orderByChild(idUsuario).
                        equalTo(pet.getIdPet());
                mReferenciaFirebase.child("pets").child(pet.getIdPet()).child("foto").removeValue();
            }
        });
    }


    private Context getContexto(){
        return this.mContexto;
    }

}
