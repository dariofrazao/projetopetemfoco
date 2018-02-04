package projetaobcc20172.com.projetopetemfoco.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

import projetaobcc20172.com.projetopetemfoco.R;
import projetaobcc20172.com.projetopetemfoco.activity.VisualizarPromocaoActivity;
import projetaobcc20172.com.projetopetemfoco.model.Promocao;
import projetaobcc20172.com.projetopetemfoco.utils.Utils;

/**
 * Created by raul1 on 03/02/2018.
 */

public class PromocoesRecyclerViewAdapter extends RecyclerView.Adapter<PromocoesRecyclerViewAdapter.PromocaoHolder> {

    public static class PromocaoHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        protected TextView titulo;
        protected TextView valor;
        protected TextView descricao;
        protected TextView forncedor;
        protected List<Promocao> promocaoList;
        protected Activity activityAtual;

        //protected ImageView imgAnuncio;

        public PromocaoHolder(View itemView) {
            super(itemView);
            valor = itemView.findViewById(R.id.tv_valor);
            itemView.setOnClickListener(this);
            titulo =  itemView.findViewById(R.id.tv_titulo);
            //imgAnuncio  = itemView.findViewById(R.id.im_anuncio);
            descricao = itemView.findViewById(R.id.tv_descricao);
            forncedor = itemView.findViewById(R.id.tv_fornedor_promo);
        }

        @Override
        public void onClick(View view) {
            Animation animation1 = new AlphaAnimation(0.3f, 1.0f);
            animation1.setDuration(1000);
            view.startAnimation(animation1);
            exibirDetalhes(getAdapterPosition());
        }

        private void exibirDetalhes(int pos){
            Intent intent = new Intent(activityAtual, VisualizarPromocaoActivity.class);
            System.out.println("promo2 "+promocaoList.get(pos).getTitulo());
            intent.putExtra("promocao", promocaoList.get(pos));
            activityAtual.startActivity(intent);
        }

    }

    private List<Promocao> promocaoList;
    private Context context;
    private Activity activityAtual;
    private int tamanhoDescricao = 35;

    public PromocoesRecyclerViewAdapter(Context context, List<Promocao> itemList,Activity activityAtual) {
        this.promocaoList = itemList;
        this.context = context;
        this.activityAtual = activityAtual;
    }

    @Override
    public PromocaoHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.lista_itens_promocao, null);
        PromocaoHolder rcv = new PromocaoHolder(layoutView);
        rcv.promocaoList = this.promocaoList;
        rcv.activityAtual = activityAtual;
        return rcv;
    }

    @Override
    public void onBindViewHolder(PromocaoHolder holder, int position) {
        holder.titulo.setText(promocaoList.get(position).getTitulo());
        holder.descricao.setText(Utils.limitarTexto(promocaoList.get(position).getDescricao(),tamanhoDescricao));
        holder.valor.setText(promocaoList.get(position).getValor());
        holder.forncedor.setText(promocaoList.get(position).getmFornecedor().getNome());
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


