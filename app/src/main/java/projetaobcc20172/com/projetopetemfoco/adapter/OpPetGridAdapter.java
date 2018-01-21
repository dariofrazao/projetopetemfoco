package projetaobcc20172.com.projetopetemfoco.adapter;

import android.app.Activity;
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
    private final ArrayList<String> mTiposPetPadrao;
    private ArrayList<String> mTiposPet;
    private static LayoutInflater inflater=null;

    public OpPetGridAdapter(@NonNull Activity context, ArrayList<String> tiposPet) {
        Activity mContext = context;
        this.mTiposPet = tiposPet;
        this.mTiposPetPadrao = Utils.recuperaArrayR(mContext,R.array.tiposPetBusca);
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

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub

        View rowView = inflater.inflate(R.layout.grid_op_pets, null);
        TextView tv = rowView.findViewById(R.id.tvGridTextPet);
        ImageView img = rowView.findViewById(R.id.gridImgPet);
            tv.setText(this.mTiposPetPadrao.get(position));
            img.setImageResource(Utils.escolherIconPet(this.mTiposPet.get(position), rowView));
            if (this.mTiposPet.get(position).equals("Todos")) {
                img.setImageResource(R.drawable.tipo_pet_todos_check);
            }
            else if(this.mTiposPet.get(position).equals("Todos_uncheck")){
                img.setImageResource(R.drawable.tipo_pet_todos);
        }

        return rowView;
    }



}
