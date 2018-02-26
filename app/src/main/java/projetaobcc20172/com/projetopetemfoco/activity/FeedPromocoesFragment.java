package projetaobcc20172.com.projetopetemfoco.activity;


import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.NotificationCompat;
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
import java.util.Iterator;
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
    private ArrayList<String> mListaPromocoes;

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
        mListaPromocoes = new ArrayList<>();
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

    public void sendNotificacao(View view) {
        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(getActivity())
                        .setAutoCancel(true)
                        .setSmallIcon(R.drawable.ic_action_promocoes)
                        .setContentTitle("Pet Em Foco")
                        .setContentText("Nova Promoção!")
                        .setDefaults(NotificationCompat.DEFAULT_ALL)
                        .setWhen(System.currentTimeMillis())
                        .setTicker("{notificação}")
                        .setContentInfo("INFO");
        NotificationManager mNotificationManager = (NotificationManager) getActivity().getSystemService(Context.NOTIFICATION_SERVICE);

        PendingIntent contentIntent = PendingIntent.getActivity(getActivity(), 0,new Intent(getActivity(), FeedPromocoesFragment.class), PendingIntent.FLAG_UPDATE_CURRENT);
        mBuilder.setContentIntent(contentIntent);

        // Builds the notification and issues it.
        mNotificationManager.notify(001, mBuilder.build());
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

    private void buscandoListaPromocoesLidas(String idPromocao){
        boolean nova = true;
        Iterator<String> iterator = mListaPromocoes.iterator();
        while (iterator.hasNext()){
            if(idPromocao.equals(iterator.next())){
                nova = false;
                break;
            }
        }
        if(nova) {
            mListaPromocoes.add(idPromocao);
            sendNotificacao(getView());
        }
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
                        buscandoListaPromocoesLidas(promo.getId());
                        recyclerView.requestLayout();
                    }else if(ConfiguracaoExibirPromocao.getsOpcoesPromocao().equals(Enumerates.Promocao.PROXIMO)){
                        try {//Evita qualquer erro relacioanado com distância ou a latitude e longitude
                            double dist = Localizacao.distanciaEntreDoisPontos(getActivity(), posicaoAtual[0], posicaoAtual[1], f.getEndereco().getmLatitude(), f.getEndereco().getmLongitude());
                            if (dist > 0 && dist <= (double)ConfiguracaoExibirPromocao.getRaio().getRaioAtual()/1000) {//Só add a lista se possuir uma distância válida e estiver dentro do raio
                                mPromocoes.add(promo);
                                buscandoListaPromocoesLidas(promo.getId());
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
