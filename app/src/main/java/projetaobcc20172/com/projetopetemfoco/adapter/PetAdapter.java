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
import projetaobcc20172.com.projetopetemfoco.activity.EditarPetActivity;
import projetaobcc20172.com.projetopetemfoco.database.services.PetDaoImpl;
import projetaobcc20172.com.projetopetemfoco.model.Pet;
import projetaobcc20172.com.projetopetemfoco.utils.Utils;

//Classe que monta uma View para exibir os pets cadastrados do usuário
public class PetAdapter extends ArrayAdapter<Pet> {

    private ArrayList<Pet> mPets;
    private Context mContext;

    public PetAdapter(Context c, ArrayList<Pet> objects) {
        super(c, 0, objects);
        this.mContext = c;
        this.mPets = objects;
    }

    @NonNull
    @Override
    public View getView(final int position, final View convertView, @NonNull final ViewGroup parent) {

        View view = null;

        // Verifica se a lista está preenchida
        if (mPets != null) {

            // inicializar objeto para montagem da view
            final LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            // Monta view a partir do xml
            assert inflater != null;
            view = inflater.inflate(R.layout.lista_itens, parent, false);

            // recupera elemento para exibição
            TextView nome = view.findViewById(R.id.tvTitulo);
            TextView tipo = view.findViewById(R.id.tvSubtitulo);
            ImageButton removerPet = view.findViewById(R.id.ibtnRemoverPet);
            ImageButton editarPet = view.findViewById(R.id.ibtnEditarPet);

            final Pet pet = mPets.get(position);
            nome.setText(pet.getNome());
            tipo.setText(pet.getTipo());

            //Recuperar id do usuário logado
            final String idUsuarioLogado;
            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getContext());
            idUsuarioLogado = preferences.getString("id", "");

            removerPet.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    final DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            switch (which){
                                case DialogInterface.BUTTON_POSITIVE:
                                    // Botão sim foi clicado
                                    PetDaoImpl petDao = new PetDaoImpl(getContext());
                                    petDao.remover(pet, idUsuarioLogado);
                                    mPets.remove(position);
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

            editarPet.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //Enviar para a Activity de Edição do pet seus atuais dados salvos para exibição
                    Intent intent = new Intent(getContext(), EditarPetActivity.class);
                    intent.putExtra("idPet", pet.getIdPet());
                    intent.putExtra("nomePet", pet.getNome());
                    intent.putExtra("raçaPet", pet.getRaça());
                    intent.putExtra("idadePet", pet.getIdade());
                    intent.putExtra("tipoPet", pet.getTipo());
                    intent.putExtra("portePet", pet.getPorte());
                    intent.putExtra("generoPet", pet.getGenero());
                    getContext().startActivity(intent);
                }
            });

        }
        return view;
    }







}
