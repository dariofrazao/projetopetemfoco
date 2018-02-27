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
import projetaobcc20172.com.projetopetemfoco.model.Favorito;

/**
 * Created by LuizAlberes on 12/01/2018.
 */

public class FavoritoAdapter extends ArrayAdapter<Favorito>{

    private ArrayList<Favorito> mFavoritos;
    private Context mContext;

    public FavoritoAdapter(Context c, ArrayList<Favorito> objects) {
        super(c, 0, objects);
        this.mContext = c;
        this.mFavoritos = objects;
    }

    @NonNull
    @Override
    public View getView(final int position, final View convertView, @NonNull final ViewGroup parent) {

        View view = null;

        // Verifica se a lista está preenchida
        if (mFavoritos != null) {

            // inicializar objeto para montagem da view
            final LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            // Monta view a partir do xml
            assert inflater != null;
            view = inflater.inflate(R.layout.lista_favoritos, parent, false);

            // recupera elemento para exibição
            TextView mNome = view.findViewById(R.id.tvTituloMeusFavoritos);
            TextView mTelefone = view.findViewById(R.id.tvSubtituloMeusFavoritos);

            final Favorito favorito = mFavoritos.get(position);
            mNome.setText(favorito.getNome());
            mTelefone.setText(favorito.getTelefone());

        }
        return view;
    }

}
