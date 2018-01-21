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
import projetaobcc20172.com.projetopetemfoco.model.Endereco;
import projetaobcc20172.com.projetopetemfoco.model.Usuario;
import projetaobcc20172.com.projetopetemfoco.utils.Utils;

/**
 * Created by dario on 11/12/2017.
 */

public class EnderecoDaoImpl implements EnderecoDao {

    private DatabaseReference mReferenciaFirebase;
    private final Context mContexto;

    public EnderecoDaoImpl(Context contexto){
        this.mReferenciaFirebase = ConfiguracaoFirebase.getFirebase();
        this.mContexto = contexto;
    }

    //Método para salvar usuário no banco de dados do Firebase
    @Override
    public void inserirUsuario(final Usuario usuario, final String idUsuarioLogado) {
        mReferenciaFirebase.child("usuarios").child(idUsuarioLogado).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                //Verifica se o usuário já está salvo no banco de dados
                //Se já estiver, exibe apenas mensagem de login realizado com sucesso
                if(dataSnapshot.exists()){
                    Utils.mostrarMensagemCurta(getContexto(), getContexto().getString(R.string.sucesso_login_Toast));
                }
                //Senão, salva o novo usuário no banco
                else{
                    mReferenciaFirebase.child("usuarios").child(idUsuarioLogado).setValue(usuario).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                                Utils.mostrarMensagemCurta(getContexto(), getContexto().getString(R.string.sucesso_login_Toast));
                            }
                            else{
                                Utils.mostrarMensagemCurta(getContexto(), getContexto().getString(R.string.erro_login_invalido_Toast));
                            }
                        }
                    });

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                //vazio
            }
        });
    }

    //Método para salvar endereço no banco de dados do Firebase
    @Override
    public void inserir(final Endereco endereco, String idUsuarioLogado) {

        endereco.setIdUsuario(idUsuarioLogado);

        //O método push() cria uma chave exclusiva para cada endereço cadastrado

        mReferenciaFirebase = mReferenciaFirebase.child("enderecos").push();
        final String id = mReferenciaFirebase.getKey();
        endereco.setId(id);

        mReferenciaFirebase.setValue(endereco).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Utils.mostrarMensagemCurta(getContexto(), getContexto().getString(R.string.sucesso_cadastro_endereco));
                }
                else{
                    Utils.mostrarMensagemCurta(getContexto(), getContexto().getString(R.string.erro_ao_cadastrar));
                }
            }
        });
    }

    //Método para remover endereço no banco de dados do Firebase
    @Override
    public void remover(final Endereco endereco, final String idUsuarioLogado) {

        //Adicionar um listener no nó do endereço que será removido
        //O método orderByChild ordena os endereços pelo seu id e o equalTo busca o id do endereço que será removido
        mReferenciaFirebase.child("enderecos").child(endereco.getId()).orderByChild(idUsuarioLogado).
                equalTo(endereco.getId()).addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                mReferenciaFirebase.child("enderecos").child(endereco.getId())
                        .removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        //Se a remoçao foi feita com sucesso
                        if(task.isSuccessful()){
                            Utils.mostrarMensagemCurta(getContexto(), getContexto().getString(R.string.sucesso_remocao_endereco));
                        }
                        //Senão
                        else{
                            Utils.mostrarMensagemCurta(getContexto(), getContexto().getString(R.string.erro_ao_remover));
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

    //Método para editar endereço no banco de dados do Firebase
    @Override
    public void atualizar(final Endereco endereco, final String idUsuarioLogado) {

        endereco.setIdUsuario(idUsuarioLogado);

        //Adicionar um listener no nó do endereço que será editado
        //O método orderByChild ordena os endereços pelo seu id e o equalTo busca o id do endereço que será editado
        mReferenciaFirebase.child("enderecos").child(endereco.getId())
                .equalTo(endereco.getId()).addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                mReferenciaFirebase.child("enderecos").child(endereco.getId())
                        .setValue(endereco).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        //Se a edição foi feita com sucesso
                        if(task.isSuccessful()){
                            Utils.mostrarMensagemCurta(getContexto(), getContexto().getString(R.string.sucesso_atualizacao_endereco));
                        }
                        //Senão
                        else{
                            Utils.mostrarMensagemCurta(getContexto(), getContexto().getString(R.string.erro_ao_atualizar));
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
