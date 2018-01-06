package projetaobcc20172.com.projetopetemfoco.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import projetaobcc20172.com.projetopetemfoco.R;
import projetaobcc20172.com.projetopetemfoco.utils.Utils;


/**
 * Created by raul1 on 05/01/2018.
 */

public class ServicoAdapterListView extends ArrayAdapter<String[]> {
    private ArrayList<String[]> mResuBusca;
    private Context mContext;

    public ServicoAdapterListView(@NonNull Context context, ArrayList<String[]> resultados) {
        super(context, 0,resultados);
        this.mContext = context;
        this.mResuBusca = resultados;
    }

    @NonNull
    @Override
    public View getView(final int position, final View convertView, @NonNull final ViewGroup parent) {
        View view = null;
        if(this.mResuBusca !=null){
            // inicializar objeto para montagem da view
            final LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            // Monta view a partir do xml
            assert inflater != null;
            view = inflater.inflate(R.layout.lista_servicos_busca, parent, false);

            TextView nomeServico = view.findViewById(R.id.tvExibirServico);
            TextView estabelecimento = view.findViewById(R.id.tvEstabServico);
            TextView valor = view.findViewById(R.id.tvExibirServicoValor);
            ImageView img = view.findViewById(R.id.ivPetServicoBusca);
            String[] valores = mResuBusca.get(position);
            nomeServico.setText(valores[0]);
            estabelecimento.setText(valores[1]);
            valor.setText(valores[2]);
            img.setImageResource(Utils.escolherIconPet(valores[3],view));

        }
        return view;

    }


}
