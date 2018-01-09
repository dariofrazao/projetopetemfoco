package projetaobcc20172.com.projetopetemfoco.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;

import projetaobcc20172.com.projetopetemfoco.R;
import projetaobcc20172.com.projetopetemfoco.activity.CadastroVacinaActivity;
import projetaobcc20172.com.projetopetemfoco.database.services.PetDaoImpl;
import projetaobcc20172.com.projetopetemfoco.database.services.VacinaDao;
import projetaobcc20172.com.projetopetemfoco.database.services.VacinaDaoImpl;
import projetaobcc20172.com.projetopetemfoco.model.Vacina;
import projetaobcc20172.com.projetopetemfoco.utils.Utils;

/**
 * Created by Cloves on 07/01/2018.
 */

public class VacinaAdapter extends ArrayAdapter<Vacina>{

    private ArrayList<Vacina> mVacina;
    private Context mContext;
    private String mPetId;

    public VacinaAdapter(Context c, ArrayList<Vacina> objects, String petId){
        super(c,0,objects);
        this.mContext = c;
        this.mVacina = objects;
        this.mPetId = petId;
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
            view = inflater.inflate(R.layout.lista_itens,parent,false);

            // recupera elemento para exibição
            TextView titulo = view.findViewById(R.id.tvTitulo);
            TextView subtitulo = view.findViewById(R.id.tvSubtitulo);
            ImageButton remover = view.findViewById(R.id.ibtnRemover);
            ImageButton editar = view.findViewById(R.id.ibtnEditar);

            final Vacina vacina = mVacina.get(position);
            titulo.setText(vacina.getmDescricao());
            subtitulo.setText(vacina.getmData());

            //Recuperar id do usuário logado
            final String idUsuarioLogado;
            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getContext());
            idUsuarioLogado = preferences.getString("id", "");

            editar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getContext(), CadastroVacinaActivity.class);
                    intent.putExtra("vacina",vacina);
                    intent.putExtra("petId",mPetId);
                    intent.putExtra("editar",true);
                    getContext().startActivity(intent);
                }
            });

            remover.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    final DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            switch (which){
                                case DialogInterface.BUTTON_POSITIVE:
                                    // Botão sim foi clicado
                                    VacinaDao vacinaDao = new VacinaDaoImpl(getContext());
                                    vacinaDao.remover(vacina, idUsuarioLogado,mPetId);
                                    mVacina.remove(position);
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
                            mContext.getString(R.string.pergunta_confirma_remocao_pet), dialogClickListener,
                            dialogClickListener);
                }
            });
        }

        return view;
    }
}


