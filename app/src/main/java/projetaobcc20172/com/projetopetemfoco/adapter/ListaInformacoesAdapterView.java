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
import projetaobcc20172.com.projetopetemfoco.model.Servico;
import projetaobcc20172.com.projetopetemfoco.utils.Utils;

/**
 * Created by Alexsandro on 07/01/2018.
 */

public class ListaInformacoesAdapterView extends ArrayAdapter<Servico> {
    private ArrayList<Servico> mServicos;
    private Context mContext;

    public ListaInformacoesAdapterView(@NonNull Context context, ArrayList<Servico> resultados) {
        super(context, 0,resultados);
        this.mContext = context;
        this.mServicos = resultados;
    }

    @NonNull
    @Override
    public View getView(final int position, final View convertView, @NonNull final ViewGroup parent) {
        View view = null;
        if(this.mServicos !=null){
            // inicializar objeto para montagem da view
            final LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            // Monta view a partir do xml
            assert inflater != null;
            view = inflater.inflate(R.layout.lista_servicos_estabelecimento, parent, false);

            TextView nomeServico = view.findViewById(R.id.tvExibirServicoEstabelecimentoNome);
            TextView valor = view.findViewById(R.id.tvExibirServicoEstabelecimentoValor);
            ImageView img = view.findViewById(R.id.ivPetServicoTipo);

            nomeServico.setText(mServicos.get(position).getNome());
            valor.setText(mServicos.get(position).getValor());
            img.setImageResource(Utils.escolherIconPet(mServicos.get(position).getTipoPet(),view));
        }
        return view;
    }
}
