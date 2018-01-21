package projetaobcc20172.com.projetopetemfoco.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import projetaobcc20172.com.projetopetemfoco.R;
import projetaobcc20172.com.projetopetemfoco.model.Vacina;

/**
 * Created by Cloves on 07/01/2018.
 */

public class VacinaAdapter extends ArrayAdapter<Vacina>{

    private ArrayList<Vacina> mVacina;
    private Context mContext;

    public VacinaAdapter(Context c, ArrayList<Vacina> objects){
        super(c,0,objects);
        this.mContext = c;
        this.mVacina = objects;
    }

    @NonNull
    @Override
    public View getView(final int position, final View convertView, @NonNull final ViewGroup parent){
        View view  = null;

        if(mVacina != null){

            // inicializar objeto para montagem da view
            final LayoutInflater inflater = (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            // Monta view a partir do xml
            assert inflater != null;
            view = inflater.inflate(R.layout.lista_itens_vacina,parent,false);

            // recupera elemento para exibição
            TextView titulo = view.findViewById(R.id.tvTitulo);
            TextView subtitulo = view.findViewById(R.id.tvSubtitulo);

            final Vacina vacina = mVacina.get(position);
            titulo.setText(vacina.getmDescricao());
            subtitulo.setText(vacina.getmData());

        }

        return view;
    }
}


