package projetaobcc20172.com.projetopetemfoco.adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import java.util.ArrayList;
import projetaobcc20172.com.projetopetemfoco.R;
import projetaobcc20172.com.projetopetemfoco.model.Fornecedor;


//Classe que monta uma View para exibir os pets cadastrados do usuário
public class EstabelecimentoAdapter extends ArrayAdapter<Fornecedor> {

    private ArrayList<Fornecedor> mForncedores;
    private Context mContext;

    public EstabelecimentoAdapter(Context c, ArrayList<Fornecedor> objects) {
        super(c, 0, objects);
        this.mContext = c;
        this.mForncedores = objects;
    }

    @NonNull
    @Override
    public View getView(final int position, final View convertView, @NonNull final ViewGroup parent) {

        View view = null;
        // Verifica se a lista está preenchida
        if (mForncedores != null) {

            // inicializar objeto para montagem da view
            final LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            // Monta view a partir do xml
            assert inflater != null;
            view = inflater.inflate(R.layout.linha_list_estabelecimentos, parent, false);

            // recupera elemento para exibição
            TextView nome = view.findViewById(R.id.tvNomeForn);
            TextView nota = view.findViewById(R.id.tvNota);
            TextView distancia = view.findViewById(R.id.tvDistancia);

            //Recuperar id do usuário logado
            final String idUsuarioLogado;
            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getContext());
            idUsuarioLogado = preferences.getString("id", "");

            final Fornecedor fornecedor = mForncedores.get(position);
            nome.setText(fornecedor.getNome());
            nota.setText(fornecedor.getNota()+"");
            distancia.setText("0");
        }

        return view;
    }
}
