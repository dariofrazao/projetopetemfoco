package projetaobcc20172.com.projetopetemfoco.adapter;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import projetaobcc20172.com.projetopetemfoco.R;

/**
 * Created by raul1 on 03/02/2018.
 */

public class PromocaoHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

    protected TextView titulo;
    protected TextView valor;
    protected TextView descricao;
    protected TextView forncedor;
    protected ImageView imgAnuncio;

    public PromocaoHolder(View itemView) {
        super(itemView);
        itemView.setOnClickListener(this);
        valor = itemView.findViewById(R.id.tv_valor);
        titulo =  itemView.findViewById(R.id.tv_titulo);
        //imgAnuncio  = itemView.findViewById(R.id.im_anuncio);
        descricao = itemView.findViewById(R.id.tv_descricao);
        forncedor = itemView.findViewById(R.id.tv_fornedor_promo);
    }

    @Override
    public void onClick(View view) {

    }
}
