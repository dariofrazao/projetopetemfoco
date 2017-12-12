package projetaobcc20172.com.projetopetemfoco.database.services;

import android.content.Context;
import android.support.annotation.NonNull;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import java.util.List;
import projetaobcc20172.com.projetopetemfoco.R;
import projetaobcc20172.com.projetopetemfoco.activity.CadastroUsuarioActivity;
import projetaobcc20172.com.projetopetemfoco.config.ConfiguracaoFirebase;
import projetaobcc20172.com.projetopetemfoco.excecoes.CampoObrAusenteException;
import projetaobcc20172.com.projetopetemfoco.excecoes.SenhasDiferentesException;
import projetaobcc20172.com.projetopetemfoco.model.Endereco;
import projetaobcc20172.com.projetopetemfoco.model.Usuario;
import projetaobcc20172.com.projetopetemfoco.utils.Utils;

/**
 * Created by dario on 11/12/2017.
 */

public class UsuarioDaoImpl implements UsuarioDao{

    private DatabaseReference referenciaFirebase;
    private  final Context contexto;

    public UsuarioDaoImpl(Context contexto){
        this.referenciaFirebase = ConfiguracaoFirebase.getFirebase();
        this.contexto = contexto;
    }

    //Método para salvar usuário no banco de dados do Firebase
    @Override
    public void inserir(Usuario usuario, String idUsuario) {

        //Chama a referência do Firebase e Cria os nós dos usuário no banco de dados
        referenciaFirebase.child("usuarios").child(usuario.getId()).setValue(usuario).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Utils.mostrarMensagemCurta(getContexto(), getContexto().getString(R.string.sucesso_cadastro_Toast));
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
    /*
    @Override
    public void remover(Usuario usuario, String idFornecedor) {
        referenciaFornecedor = referenciaFirebase.child("usuarios");
        referenciaFornecedor.child(String.format("%s/%s", "servicos", usuario.getId()))
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
    }

    @Override
    public void atualizar(Usuario usuario, String idFornecedor) {
        referenciaFornecedor = referenciaFirebase.child("usuarios");
        referenciaFornecedor.child(String.format("%s/%s", "servicos", usuario.getId()))
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
    }
    */

    @Override
    public List<Usuario> buscarPorNome(String nome) {
        return null;
    }

    @Override
    public List<Usuario> buscarTodosUsuario(String idFornecedor) {
        return null;
    }

    private Context getContexto(){
        return this.contexto;
    }

}
