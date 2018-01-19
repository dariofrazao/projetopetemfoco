package projetaobcc20172.com.projetopetemfoco.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RatingBar;
import android.widget.TextView;
import java.util.ArrayList;
import projetaobcc20172.com.projetopetemfoco.R;
import projetaobcc20172.com.projetopetemfoco.model.Avaliacao;

/**
 * Created by Alexsandro on 07/01/2018.
 */

public class ListaAvaliacoesAdapterView extends ArrayAdapter<Avaliacao> {
    private ArrayList<Avaliacao> mAvaliacoes;
    private Context mContext;


    public ListaAvaliacoesAdapterView(@NonNull Context context, ArrayList<Avaliacao> resultados) {
        super(context, 0,resultados);
        this.mContext = context;
        this.mAvaliacoes = resultados;
    }

    @NonNull
    @Override
    public View getView(final int position, final View convertView, @NonNull final ViewGroup parent) {
        View view = null;
        if(this.mAvaliacoes !=null){
            // inicializar objeto para montagem da view
            final LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            // Monta view a partir do xml
            assert inflater != null;
            view = inflater.inflate(R.layout.avaliacao, parent, false);

            TextView mNomeUsuarioAvaliador = view.findViewById(R.id.tvNomeUsuarioAvaliacaoes);
            RatingBar mEstrelaAvaliacoes = view.findViewById(R.id.rbEstrelaAvaliacoes);
            TextView mComentario = view.findViewById(R.id.etComentarioAvaliacoes);

            mNomeUsuarioAvaliador.setText(mAvaliacoes.get(position).getmNomeUsuario());
            mEstrelaAvaliacoes.setRating(Float.parseFloat(mAvaliacoes.get(position).getEstrelas()));
            mComentario.setText(mAvaliacoes.get(position).getCometario());
        }else{
            // inicializar objeto para montagem da view
            final LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            // Monta view a partir do xml
            assert inflater != null;
            view = inflater.inflate(R.layout.avaliacao_vazia, parent, false);
        }
        return view;
    }
}
