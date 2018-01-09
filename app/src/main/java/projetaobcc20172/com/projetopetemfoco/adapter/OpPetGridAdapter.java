package projetaobcc20172.com.projetopetemfoco.adapter;

import android.content.Context;
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

public class OpPetGridAdapter extends BaseAdapter {
    private ArrayList<String> mTiposPet;
    private Context mContext;
    private static LayoutInflater inflater=null;

    public OpPetGridAdapter(@NonNull Context context, ArrayList<String> tiposPet) {
        this.mContext = context;
        this.mTiposPet = tiposPet;
        inflater = ( LayoutInflater )context.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return this.mTiposPet.size();
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
        OpPetGridAdapter.Holder holder=new OpPetGridAdapter.Holder();
        View rowView;
        rowView = inflater.inflate(R.layout.grid_op_pets, null);
        holder.tv = rowView.findViewById(R.id.tvGridTextPet);
        holder.img = rowView.findViewById(R.id.gridImgPet);
        holder.tv.setText(this.mTiposPet.get(position));
        holder.img.setImageResource(Utils.escolherIconPet(this.mTiposPet.get(position),rowView));
        if(this.mTiposPet.get(position).equals("Todos")){
            holder.img.setImageResource(R.drawable.tipo_pet_todos_check);
        }
        return rowView;
    }


}
