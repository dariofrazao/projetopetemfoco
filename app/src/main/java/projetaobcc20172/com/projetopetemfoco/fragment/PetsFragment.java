package projetaobcc20172.com.projetopetemfoco.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import projetaobcc20172.com.projetopetemfoco.R;
import projetaobcc20172.com.projetopetemfoco.adapter.PetAdapter;
import projetaobcc20172.com.projetopetemfoco.config.ConfiguracaoFirebase;
import projetaobcc20172.com.projetopetemfoco.model.Pet;

//Classe que monta um Fragmente (pedaço de tela para exibir os pets)
//Sua utilização é útil para dividir uma mesma tela em mais partes.
public class PetsFragment extends Fragment {

    private ListView listView;
    private ArrayAdapter<Pet> adapter;
    private ArrayList<Pet> pets;
    private DatabaseReference mFirebase;

    public PetsFragment() {
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.activity_fragment, container, false);

        // Monta listview e adapter
        pets = new ArrayList<>();
        listView = view.findViewById(R.id.lv_pets);
        adapter = new PetAdapter(getActivity(), pets);
        listView.setAdapter(adapter);

        //Listener que "ouve" o banco de dados
        ValueEventListener valueEventListenerPets = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                pets.clear();
                for (DataSnapshot dados : dataSnapshot.getChildren()) {
                    Pet pet = dados.getValue(Pet.class);
                    pets.add(pet);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // vazio
            }
        };
        mFirebase.addValueEventListener(valueEventListenerPets);
        return view;
    }

}