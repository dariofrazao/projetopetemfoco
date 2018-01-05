package projetaobcc20172.com.projetopetemfoco.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;

import projetaobcc20172.com.projetopetemfoco.R;


/**
 * Created by raul1 on 05/01/2018.
 */

public class ServicoAdapterGrid extends ArrayAdapter {
    private ArrayList<String> mTiposServicos;
    private Context mContext;

    public ServicoAdapterGrid(@NonNull Context context, int resource) {
        super(context, resource);
        this.mContext = context;
        this.mTiposServicos = (ArrayList<String>) Arrays.asList(context.getResources().getStringArray(R.array.servicos));
    }

    @NonNull
    @Override
    public View getView(final int position, final View convertView, @NonNull final ViewGroup parent) {
        View view = null;
        if(mTiposServicos!=null){
            // inicializar objeto para montagem da view
            final LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            // Monta view a partir do xml
            assert inflater != null;
            view = inflater.inflate(R.layout.grid_servicos, parent, false);
            TextView nomeServico = view.findViewById(R.id.gridTextServico);
            nomeServico.setText(this.mTiposServicos.get(position));
        }
        return view;
    }
}
