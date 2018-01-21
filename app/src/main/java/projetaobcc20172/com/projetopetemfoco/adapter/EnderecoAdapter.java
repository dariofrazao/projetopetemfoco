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
import projetaobcc20172.com.projetopetemfoco.model.Endereco;

/**
 * Created by dario on 03/01/2018.
 */

public class EnderecoAdapter extends ArrayAdapter<Endereco> {

    private ArrayList<Endereco> mEnderecos;
    private Context mContext;

    public EnderecoAdapter(Context c, ArrayList<Endereco> objects) {
        super(c, 0, objects);
        this.mContext = c;
        this.mEnderecos = objects;
    }

    @NonNull
    @Override
    public View getView(final int position, final View convertView, @NonNull final ViewGroup parent) {

        View view = null;

        // Verifica se a lista está preenchida
        if (mEnderecos != null) {

            // inicializar objeto para montagem da view
            final LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            // Monta view a partir do xml
            assert inflater != null;
            view = inflater.inflate(R.layout.lista_itens_endereco, parent, false);

            // recupera elemento para exibição
            TextView mNome = view.findViewById(R.id.tvTitulo);
            TextView mTipo = view.findViewById(R.id.tvSubtitulo);

            final Endereco endereco = mEnderecos.get(position);
            mNome.setText(endereco.getBairro());
            mTipo.setText(endereco.getLocalidade());

        }

        return view;
    }
}
