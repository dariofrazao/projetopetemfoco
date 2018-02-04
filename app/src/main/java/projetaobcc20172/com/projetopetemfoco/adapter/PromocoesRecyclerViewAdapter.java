package projetaobcc20172.com.projetopetemfoco.adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import java.util.List;

import projetaobcc20172.com.projetopetemfoco.R;
import projetaobcc20172.com.projetopetemfoco.model.Promocao;

/**
 * Created by raul1 on 03/02/2018.
 */

public class PromocoesRecyclerViewAdapter extends RecyclerView.Adapter<PromocaoHolder> {

    private List<Promocao> promocaoList;
    private Context context;

    public PromocoesRecyclerViewAdapter(Context context, List<Promocao> itemList) {
        this.promocaoList = itemList;
        this.context = context;
    }

    @Override
    public PromocaoHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.lista_itens_promocao, null);
        PromocaoHolder rcv = new PromocaoHolder(layoutView);
        return rcv;
    }

    @Override
    public void onBindViewHolder(PromocaoHolder holder, int position) {
        holder.titulo.setText(promocaoList.get(position).getTitulo());
        holder.descricao.setText(promocaoList.get(position).getDescricao());
        holder.valor.setText(promocaoList.get(position).getValor());
//        if(position%2==0)
//            holder.imgAnuncio.setImageResource(R.drawable.tipo_pet_gato);
//        else{
//            holder.imgAnuncio.setImageResource(R.drawable.servico_banho);
       // }
        // holder.countryPhoto.setImageResource(promocaoList.get(position).getPhoto());
    }

    @Override
    public int getItemCount() {
        return this.promocaoList.size();
    }

}


