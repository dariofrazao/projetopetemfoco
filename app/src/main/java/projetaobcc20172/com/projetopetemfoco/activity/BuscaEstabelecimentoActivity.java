package projetaobcc20172.com.projetopetemfoco.activity;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import projetaobcc20172.com.projetopetemfoco.R;
import projetaobcc20172.com.projetopetemfoco.adapter.EstabelecimentoAdapter;
import projetaobcc20172.com.projetopetemfoco.config.ConfiguracaoFirebase;
import projetaobcc20172.com.projetopetemfoco.model.Endereco;
import projetaobcc20172.com.projetopetemfoco.model.Fornecedor;
import projetaobcc20172.com.projetopetemfoco.model.Servico;

/**
 * Created by raul1 on 03/01/2018.
 */

public class BuscaEstabelecimentoActivity extends Fragment {
    private ArrayList<Fornecedor> mForncedores;
    private ArrayAdapter<Fornecedor> mAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //returning our layout file
        //change R.layout.yourlayoutfilename for each of your fragments
        return inflater.inflate(R.layout.activity_busca_estabelecimento, container, false);
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //you can set the title for your toolbar here for different fragments different titles
        getActivity().setTitle("Busca por nome");
        ListView listView = (ListView) getView().findViewById(R.id.lvBuscaEsta);
        SearchView buscaEst = getView().findViewById(R.id.svBusca);
        // Monta listview e mAdapter
        mForncedores = new ArrayList<>();
        mAdapter = new EstabelecimentoAdapter(getActivity(), mForncedores);
        listView.setAdapter(mAdapter);
        //Realiza a busca por texto
        buscaEst.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {//Quando clicar no botão de pesquisar
                buscarEstabelecimentos(s);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });
    }

    private void buscarEstabelecimentos(String nome){
        Query query1 =  ConfiguracaoFirebase.getFirebase().child("fornecedor").orderByChild("nome").startAt(nome);
        query1.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                System.out.println("nome aki");
                mForncedores.clear();
                for(DataSnapshot dados : dataSnapshot.getChildren()){
                    Fornecedor forn;
                    try {
                        forn = dados.getValue(Fornecedor.class);
                        mForncedores.add(forn);
                        System.out.println(mForncedores.get(0).getNome());
                        mAdapter.notifyDataSetChanged();
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
                        mForncedores.add(forn);

                    }
                }
                mAdapter.notifyDataSetChanged();
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                //Se ocorrer um erro
            }
        });
    }

}