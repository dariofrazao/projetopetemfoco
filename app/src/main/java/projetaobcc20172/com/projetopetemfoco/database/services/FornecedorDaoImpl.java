package projetaobcc20172.com.projetopetemfoco.database.services;

import android.content.Context;

import android.widget.ArrayAdapter;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;

import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import projetaobcc20172.com.projetopetemfoco.config.ConfiguracaoFirebase;
import projetaobcc20172.com.projetopetemfoco.model.Endereco;
import projetaobcc20172.com.projetopetemfoco.model.Fornecedor;

import projetaobcc20172.com.projetopetemfoco.model.Servico;


/**
 * Created by dario on 11/12/2017.
 */

public class FornecedorDaoImpl implements FornecedorDao{

    private DatabaseReference mReferenciaFirebase;
    private  final Context mContexto;

    public FornecedorDaoImpl(Context contexto){
        this.mReferenciaFirebase = ConfiguracaoFirebase.getFirebase();
        this.mContexto = contexto;
    }

    @Override
    public ArrayList<Fornecedor> buscarPorNome(String nome, final ArrayAdapter<Fornecedor> adp) {
        final ArrayList<Fornecedor> resultado = new ArrayList<>();
        Query query1 =  this.mReferenciaFirebase.child("fornecedor").orderByChild("nome").startAt(nome);
        query1.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot dados : dataSnapshot.getChildren()){
                    Fornecedor forn;
                    try {
                        forn = dados.getValue(Fornecedor.class);
                        resultado.add(forn);
                    }catch (Exception e){
                        ArrayList<Servico> servicos = new ArrayList<>();
                        forn = new Fornecedor(dados.child("nome").getValue(String.class),dados.child("email").getValue(String.class),dados.child("cpfCnpj").getValue(String.class)
                        ,dados.child("horario").getValue(String.class),dados.child("nota").getValue(float.class),dados.child("telefone").getValue(String.class),
                                dados.child("endereco").getValue(Endereco.class));
                        for(DataSnapshot ds:dados.child("servicos").getChildren()){
                            Servico serv = ds.getValue(Servico.class);
                            servicos.add(serv);
                        }
                        forn.setServicos(servicos);
                        resultado.add(forn);
                    }
                }
                adp.notifyDataSetChanged();
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                //Se ocorrer um erro
            }
        });

        return resultado;

    }

    @Override
    public List<Fornecedor> buscarTodosFornecedor() {
        return null;
    }


    private Context getContexto(){
        return this.mContexto;
    }

}
