package projetaobcc20172.com.projetopetemfoco.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.annotation.VisibleForTesting;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import projetaobcc20172.com.projetopetemfoco.R;
import projetaobcc20172.com.projetopetemfoco.adapter.FavoritoAdapter;
import projetaobcc20172.com.projetopetemfoco.config.ConfiguracaoFirebase;
import projetaobcc20172.com.projetopetemfoco.model.Endereco;
import projetaobcc20172.com.projetopetemfoco.model.Favorito;
import projetaobcc20172.com.projetopetemfoco.model.Fornecedor;
import projetaobcc20172.com.projetopetemfoco.model.Servico;

public class MeusFavoritosActivity extends Fragment {


    @SuppressLint("WrongConstant")
    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    //permite que essa variavel seja vista pela classe de teste

    private ArrayList<Favorito> mFavoritos;
    private ArrayAdapter<Favorito> mAdapter;
    private ListView mListView;
    private String mIdUsuarioLogado;
    private Fornecedor mFornecedor;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //returning our layout file
        //change R.layout.yourlayoutfilename for each of your fragments
        return inflater.inflate(R.layout.activity_meus_favoritos, container, false);
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        getActivity().setTitle("Meus Favoritos");

        //Recuperar id do usuário logado
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        mIdUsuarioLogado = preferences.getString("id", "");


        mListView = getView().findViewById(R.id.lv_meus_favoritos);

        // Monta listview e mAdapter
        mFavoritos = new ArrayList<>();
        mAdapter = new FavoritoAdapter(getActivity(), mFavoritos);
        mListView.setAdapter(mAdapter);

        carregarFavoritos();

        this.chamarInfoFavoritosListener();
    }

    //Método que passa as informações de um favorito
    public void chamarInfoFavoritosListener() {
        this.mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                carregarFornecedor(mFavoritos.get(position));
            }
        });
    }

    // Recuperar fornecedor do Firebase e trocar de acticity
    private void carregarFornecedor(final Favorito favorito){
        Query query = ConfiguracaoFirebase.getFirebase().child("fornecedor").orderByChild("cpfCnpj").equalTo(favorito.getCpfCnpj());
        ValueEventListener mValueEventListenerFornecedor;
        mValueEventListenerFornecedor = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot dados : dataSnapshot.getChildren()) {
                    Fornecedor fornecedor = dados.getValue(Fornecedor.class);

                    float nota = 0;
                    if (dados.child("nota").getValue(float.class) != null) {
                        nota = dados.child("nota").getValue(float.class);
                    }

                    fornecedor = new Fornecedor(dados.child("nome").getValue(String.class), dados.child("email").getValue(String.class), dados.child("cpfCnpj").getValue(String.class)
                            , dados.child("horarios").getValue(String.class), nota, dados.child("telefone").getValue(String.class),
                            dados.child("endereco").getValue(Endereco.class),dados.child("tipo").getValue(String.class));
                    fornecedor.setId(dados.getKey());
                    carregarServicos(fornecedor);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                //vazio
            }
        };
        query.addValueEventListener(mValueEventListenerFornecedor);
    }

    // Recuperar servicos do Firebase
    private void carregarServicos(final Fornecedor fornecedor){
        Query query = ConfiguracaoFirebase.getFirebase().child("servicos").orderByChild("idFornecedor").equalTo(fornecedor.getId());
        ValueEventListener mValueEventListenerFornecedor;
        mValueEventListenerFornecedor = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ArrayList<Servico> servicos = new ArrayList<>();
                for (DataSnapshot dados : dataSnapshot.getChildren()) {
                    Servico servico = dados.getValue(Servico.class);
                    servicos.add(servico);
                }
                fornecedor.setServicos(servicos);
                Intent intent = new Intent(getActivity(), ExibiInformacoesEstabelecimentoActivity.class);
                intent.putExtra("Fornecedor", fornecedor);
                getActivity().startActivity(intent);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                //vazio
            }
        };
        query.addValueEventListener(mValueEventListenerFornecedor);
    }

    // Recuperar favoritos do Firebase
    private void carregarFavoritos(){

        Query query = ConfiguracaoFirebase.getFirebase().child("favoritos").orderByChild("idUsuario").equalTo(mIdUsuarioLogado);

        ValueEventListener mValueEventListenerFavoritos;
        mValueEventListenerFavoritos = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                mFavoritos.clear();

                // Recupera favoritos
                for (DataSnapshot dados : dataSnapshot.getChildren()) {
                    Favorito favorito = dados.getValue(Favorito.class);
                    mFavoritos.add(favorito);

                }
                //Notificar o adaptador que exibe a lista de favoritos se houver alteração no banco
                mAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(getActivity(), "Erro na leitura do banco de dados", Toast.LENGTH_SHORT).show();
            }
        };
        query.addValueEventListener(mValueEventListenerFavoritos);
    }
}





