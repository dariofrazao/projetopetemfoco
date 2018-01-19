package projetaobcc20172.com.projetopetemfoco.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SearchView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.io.Serializable;
import java.util.ArrayList;

import projetaobcc20172.com.projetopetemfoco.R;
import projetaobcc20172.com.projetopetemfoco.adapter.EstabelecimentoAdapter;
import projetaobcc20172.com.projetopetemfoco.config.ConfiguracaoBuscaEstab;
import projetaobcc20172.com.projetopetemfoco.config.ConfiguracaoFirebase;
import projetaobcc20172.com.projetopetemfoco.model.Endereco;
import projetaobcc20172.com.projetopetemfoco.model.Fornecedor;
import projetaobcc20172.com.projetopetemfoco.model.Servico;
import projetaobcc20172.com.projetopetemfoco.utils.Utils;

/**
 * Created by raul1 on 03/01/2018.
 */

public class BuscaEstabelecimentoActivity extends Fragment implements Serializable {

    private ArrayList<Fornecedor> mForncedores;
    private ArrayAdapter<Fornecedor> mAdapter;
    private ProgressBar mProgresso;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        return inflater.inflate(R.layout.activity_busca_estabelecimento, container, false);
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        getActivity().setTitle("Estabelecimentos");
        final ListView listView = getView().findViewById(R.id.lvBuscaEsta);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                exibirEstabelecimento(mForncedores.get(position));
            }
        });
        ConfiguracaoBuscaEstab.inicializar();
        SearchView buscaEst = getView().findViewById(R.id.svBusca);
        mProgresso = (ProgressBar) getView().findViewById(R.id.pbProgresso);
        mProgresso.setVisibility(View.INVISIBLE);

        // Monta listview e mAdapter
        mForncedores = new ArrayList<>();
        mAdapter = new EstabelecimentoAdapter(getActivity(), mForncedores);
        listView.setAdapter(mAdapter);

        //Realiza a busca por texto
        buscaEst.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {//Quando clicar no botão de pesquisar
                mProgresso.setVisibility(View.VISIBLE);
                ConfiguracaoBuscaEstab.setsNomeEstabelecimento(s.toLowerCase());
                buscarEstabelecimentos(ConfiguracaoBuscaEstab.getsNomeEstabelecimento());
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                if(s.equals("")){//apaga os resultado quando se apaga o texto
                    mForncedores.clear();
                    mAdapter.notifyDataSetChanged();
                }
                return false;
            }
        });
    }

    //Método que chama a activity para exibir informações do estabelecimento
    public void exibirEstabelecimento(final Fornecedor fornecedor){
        //Buscar servicos do estabelecimento selecionado
        Query query = ConfiguracaoFirebase.getFirebase().child("servicos").orderByChild("idFornecedor").equalTo(fornecedor.getId());
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ArrayList<Servico> servicos = new ArrayList<>();
                for (DataSnapshot dados : dataSnapshot.getChildren()) {
                    Servico servico = dados.getValue(Servico.class);
                    servicos.add(servico);

                }
                fornecedor.setServicos(servicos);
                Intent intent = new Intent(getActivity(), AcessoInformacoesEstabelecimentoActivity.class);
                intent.putExtra("Fornecedor", fornecedor);
                getActivity().startActivity(intent);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                assert true;
            }
        });
    }

    private void buscarEstabelecimentos(final String nomeBuscado){

        final String nome = nomeBuscado;
        Query query1 = ConfiguracaoFirebase.getFirebase().child("fornecedor").orderByChild("nomeBusca").startAt(nome);
        query1.addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                mForncedores.clear();
                for (final DataSnapshot dados : dataSnapshot.getChildren()) {

                    String nomeT = dados.child("nomeBusca").getValue(String.class);

                    if(!nomeT.contains(nome)){
                        continue;
                    }

                    Fornecedor forn;
                    float nota = 0;
                    if (dados.child("nota").getValue(float.class) != null) {
                        nota = dados.child("nota").getValue(float.class);
                    }

                    forn = new Fornecedor(dados.child("nome").getValue(String.class), dados.child("email").getValue(String.class), dados.child("cpfCnpj").getValue(String.class)
                            , dados.child("horarios").getValue(String.class), nota, dados.child("telefone").getValue(String.class),
                            dados.child("endereco").getValue(Endereco.class));
                    forn.setId(dados.getKey());
                    mForncedores.add(forn);
                }
                mAdapter.notifyDataSetChanged();
                mProgresso.setVisibility(View.INVISIBLE);

                //Se a busca não retornar nada
                if(mAdapter.isEmpty()){
                    Utils.mostrarMensagemCurta(getContext(), getContext().getString(R.string.estabelecimento_nao_encontrado));
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                assert true;
            }
        });

    }


}