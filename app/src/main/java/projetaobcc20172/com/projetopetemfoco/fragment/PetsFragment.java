package projetaobcc20172.com.projetopetemfoco.fragment;

import android.app.Fragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
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
import projetaobcc20172.com.projetopetemfoco.helper.Preferencias;
import projetaobcc20172.com.projetopetemfoco.model.Pet;

//Classe que monta um Fragmente (pedaço de tela para exibir os pets)
//Sua utilização é útil para dividir uma mesma tela em mais partes.
public class PetsFragment extends Fragment {

    private ListView listView;
    private ArrayAdapter<Pet> adapter;
    private ArrayList<Pet> pets;
    private DatabaseReference firebase;
    private ValueEventListener valueEventListenerPets;

    public PetsFragment() {
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.activity_pets_fragment, container, false);

        // Monta listview e adapter
        pets = new ArrayList<>();
        listView = view.findViewById(R.id.lv_pets);
        adapter = new PetAdapter(getActivity(), pets);
        listView.setAdapter(adapter);

        //Listener que "ouve" o banco de dados
        valueEventListenerPets = new ValueEventListener() {
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

            }
        };
        return view;
    }

    //Ao iniciar a tela, recupera os pets do banco pelo listener
    @Override
    public void onStart() {
        super.onStart();
        firebase.addValueEventListener(valueEventListenerPets);
    }

    //Ao finalizar a tela, remover o listener que "ouve" o banco
    @Override
    public void onStop() {
        super.onStop();
        firebase.removeEventListener(valueEventListenerPets);
    }
}
