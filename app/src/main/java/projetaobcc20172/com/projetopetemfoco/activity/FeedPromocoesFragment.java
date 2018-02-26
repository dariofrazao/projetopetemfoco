package projetaobcc20172.com.projetopetemfoco.activity;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import projetaobcc20172.com.projetopetemfoco.R;
import projetaobcc20172.com.projetopetemfoco.adapter.PromocoesRecyclerViewAdapter;
import projetaobcc20172.com.projetopetemfoco.config.ConfiguracaoExibirPromocao;
import projetaobcc20172.com.projetopetemfoco.config.ConfiguracaoFirebase;
import projetaobcc20172.com.projetopetemfoco.model.Fornecedor;
import projetaobcc20172.com.projetopetemfoco.model.Promocao;
import projetaobcc20172.com.projetopetemfoco.utils.Enumerates;
import projetaobcc20172.com.projetopetemfoco.utils.Localizacao;

/**
 * Created by raul1 on 03/02/2018.
 */

public class FeedPromocoesFragment  extends Fragment implements Serializable {

    private List<Promocao> mPromocoes;
    private PromocoesRecyclerViewAdapter mAdapter;
    private RecyclerView recyclerView ;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_feed_promocoes, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle(R.string.tv_promocoes);

        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(3, 1);
        recyclerView = getActivity().findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(false);
        recyclerView.setLayoutManager(layoutManager);
        mPromocoes = new ArrayList<>();
        mAdapter = new PromocoesRecyclerViewAdapter(mPromocoes,getActivity());

        recyclerView.setAdapter(this.mAdapter);


        ImageView mFiltro = getActivity().findViewById(R.id.ivOpcoesPromocao);
        mFiltro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(getActivity(), OpcoesPromocoes.class);
                startActivity(intent);
            }
        });
    }

    public void onResume(){
        super.onResume();
        buscaPromocoes();
    }

    private void buscaPromocoes(){
        mPromocoes.clear();
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
            double [] posicaoAtual = Localizacao.getCurrentLocation(getActivity());
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Fornecedor f = dataSnapshot.getValue(Fornecedor.class);
                if(f!=null) {
                    promo.setmFornecedor(f);
                    if(ConfiguracaoExibirPromocao.getsOpcoesPromocao().equals(Enumerates.Promocao.PADRAO)){
                        mPromocoes.add(promo);
                        recyclerView.requestLayout();
                    }else if(ConfiguracaoExibirPromocao.getsOpcoesPromocao().equals(Enumerates.Promocao.PROXIMO)){
                        try {//Evita qualquer erro relacioanado com distância ou a latitude e longitude
                            double dist = Localizacao.distanciaEntreDoisPontos(getActivity(), posicaoAtual[0], posicaoAtual[1], f.getEndereco().getmLatitude(), f.getEndereco().getmLongitude());
                            if (dist > 0 && dist <= (double)ConfiguracaoExibirPromocao.getRaio().getRaioAtual()/1000) {//Só add a lista se possuir uma distância válida e estiver dentro do raio
                                mPromocoes.add(promo);
                                recyclerView.requestLayout();
                            }
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    }
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                //vazio
            }
        });
        recyclerView.requestLayout();
    }
}
