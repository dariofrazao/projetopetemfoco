package projetaobcc20172.com.projetopetemfoco.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import projetaobcc20172.com.projetopetemfoco.R;
import projetaobcc20172.com.projetopetemfoco.model.Pet;

//Classe que monta uma View para exibir os pets cadastrados do usuário
public class PetAdapter extends ArrayAdapter<Pet>{

    private ArrayList<Pet> pets;
    private Context context;

    public PetAdapter(Context c, ArrayList<Pet> objects) {
        super(c, 0, objects);
        this.context = c;
        this.pets = objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View view = null;

        // Verifica se a lista está preenchida
        if( pets != null ){

            // inicializar objeto para montagem da view
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);

            // Monta view a partir do xml
            view = inflater.inflate(R.layout.lista_pets, parent, false);

            // recupera elemento para exibição
            TextView nome = view.findViewById(R.id.tv_titulo);
            TextView tipo = view.findViewById(R.id.tv_subtitulo);

            Pet pet = pets.get(position);
            nome.setText(pet.getNome());
            tipo.setText(pet.getTipo());

        }

        return view;
    }
}