package projetaobcc20172.com.projetopetemfoco.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.VisibleForTesting;
import android.support.v4.app.ActivityCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import projetaobcc20172.com.projetopetemfoco.R;
import projetaobcc20172.com.projetopetemfoco.database.services.FavoritoDaoImpl;
import projetaobcc20172.com.projetopetemfoco.model.Favorito;
import projetaobcc20172.com.projetopetemfoco.utils.Utils;

/**
 * Created by LuizAlberes on 12/01/2018.
 */

public class FavoritoAdapter extends ArrayAdapter<Favorito> {

    @SuppressLint("WrongConstant")
    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    //permite que essa variavel seja vista pela classe de teste
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

            ImageButton mRemoverFavorito = view.findViewById(R.id.bt_remover_favorito);
            ImageButton mLigarFavorito = view.findViewById(R.id.bt_ligar_favorito);

            final Favorito favorito = mFavoritos.get(position);
            mNome.setText(favorito.getNome());
            mTelefone.setText(favorito.getTelefone());

            final String idUsuarioLogado;
            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getContext());
            idUsuarioLogado = preferences.getString("id", "");

            mRemoverFavorito.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    final DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            switch (which) {
                                case DialogInterface.BUTTON_POSITIVE:
                                    // Botão sim foi clicado
                                    FavoritoDaoImpl favoritoDao = new FavoritoDaoImpl(getContext());
                                    favoritoDao.remover(favorito, idUsuarioLogado);
                                    mFavoritos.remove(position);
                                    notifyDataSetChanged();
                                    break;

                                case DialogInterface.BUTTON_NEGATIVE:
                                    // Botão não foi clicado
                                    break;
                                default:
                                    break;
                            }
                        }
                    };

                    Utils.mostrarPerguntaSimNao(getContext(), mContext.getString(R.string.atencao),
                            mContext.getString(R.string.pergunta_confirma_remocao_favorito), dialogClickListener,
                            dialogClickListener);
                }
            });

            mLigarFavorito.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    final DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            switch (which) {
                                case DialogInterface.BUTTON_POSITIVE:
                                    Intent intent = new Intent(Intent.ACTION_CALL);
                                    intent.setData(Uri.parse("tel:" + favorito.getTelefone()));
                                    if (ActivityCompat.checkSelfPermission(mContext, android.Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                                        ActivityCompat.requestPermissions((Activity) mContext, new String[]{android.Manifest.permission.CALL_PHONE}, 1);
                                        return;
                                    }
                                    Toast.makeText(mContext, R.string.ligando_favorito, Toast.LENGTH_SHORT).show();
                                    mContext.startActivity(intent);

                                    break;
                                case DialogInterface.BUTTON_NEGATIVE:
                                    // Botão não foi clicado
                                    break;
                                default:
                                    break;
                            }
                        }
                    };

                    Utils.mostrarPerguntaSimNao(getContext(), mContext.getString(R.string.atencao),
                            mContext.getString(R.string.pergunta_confirma_ligacao_favorito), dialogClickListener,
                            dialogClickListener);
                }
            });
        }
            return view;
    }
}
