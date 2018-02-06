package projetaobcc20172.com.projetopetemfoco.activity;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import projetaobcc20172.com.projetopetemfoco.R;
import projetaobcc20172.com.projetopetemfoco.adapter.PromocoesRecyclerViewAdapter;
import projetaobcc20172.com.projetopetemfoco.config.ConfiguracaoFirebase;
import projetaobcc20172.com.projetopetemfoco.model.Fornecedor;
import projetaobcc20172.com.projetopetemfoco.model.Promocao;

/**
 * Created by raul1 on 03/02/2018.
 */

public class FeedPromocoesFragment  extends Fragment implements Serializable {

    private List<Promocao> mPromocoes;
    private PromocoesRecyclerViewAdapter mAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_feed_promocoes, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        final RecyclerView recyclerView = getActivity().findViewById(R.id.recyclerView);
        getActivity().setTitle(R.string.tv_promocoes);
        recyclerView.setHasFixedSize(false);
        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(3, 1);
        recyclerView.setLayoutManager(layoutManager);
        this.mPromocoes = new ArrayList<>();
        this.mAdapter = new PromocoesRecyclerViewAdapter(this.mPromocoes,getActivity());
        buscaPromocoes();
        recyclerView.setAdapter(this.mAdapter);

    }

    private void buscaPromocoes(){
        ConfiguracaoFirebase.getFirebase().child("promocoes").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot dado : dataSnapshot.getChildren()){
                    Promocao promo = dado.getValue(Promocao.class);
                    buscaFornecedor(promo);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                //vazio
            }
        });
    }

    private void buscaFornecedor(final Promocao promo) {
        ConfiguracaoFirebase.getFirebase().child("fornecedor").child(promo.getFornecedorId()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Fornecedor f = dataSnapshot.getValue(Fornecedor.class);
                if(f!=null) {
                    promo.setmFornecedor(f);
                    mPromocoes.add(promo);
                    mAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                //vazio
            }
        });
    }



}
