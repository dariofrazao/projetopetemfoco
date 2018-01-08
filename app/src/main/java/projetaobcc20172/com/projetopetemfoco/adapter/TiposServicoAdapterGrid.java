package projetaobcc20172.com.projetopetemfoco.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;


import java.util.ArrayList;


import projetaobcc20172.com.projetopetemfoco.R;
import projetaobcc20172.com.projetopetemfoco.utils.Utils;


/**
 * Created by raul1 on 05/01/2018.
 */

public class TiposServicoAdapterGrid extends BaseAdapter {
    private ArrayList<String> mTiposServicos;
    private Context mContext;
    private static LayoutInflater inflater=null;
    public TiposServicoAdapterGrid(@NonNull Context context, ArrayList<String> tiposServicos) {
        this.mContext = context;
        this.mTiposServicos = tiposServicos;
        inflater = ( LayoutInflater )context.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return this.mTiposServicos.size();
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    public class Holder
    {
        TextView tv;
        ImageView img;
    }
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        Holder holder=new Holder();
        View rowView;
        rowView = inflater.inflate(R.layout.grid_servicos, null);
        holder.tv = rowView.findViewById(R.id.gridTextServico);
        holder.img= rowView.findViewById(R.id.gridImgServico);
        holder.tv.setText(this.mTiposServicos.get(position));
        holder.img.setImageResource(Utils.escolherIconServico(this.mTiposServicos.get(position),rowView));

        return rowView;
    }


}
