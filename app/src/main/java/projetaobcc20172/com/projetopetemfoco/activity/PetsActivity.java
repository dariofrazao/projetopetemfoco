package projetaobcc20172.com.projetopetemfoco.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import projetaobcc20172.com.projetopetemfoco.R;
import projetaobcc20172.com.projetopetemfoco.adapter.PetAdapter;
import projetaobcc20172.com.projetopetemfoco.config.ConfiguracaoFirebase;
import projetaobcc20172.com.projetopetemfoco.model.Pet;

public class PetsActivity extends Fragment {

    private ArrayList<Pet> mPets;
    private ArrayAdapter<Pet> mAdapter;
    private ListView mListView;
    private String mIdUsuarioLogado;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        return inflater.inflate(R.layout.activity_pets, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        getActivity().setTitle(R.string.tb_meus_pets);

        //Recuperar id do usuário logado
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        mIdUsuarioLogado = preferences.getString("id", "");

        ImageButton mCadastrarPet; //Botão de cadastrar o pet

        mCadastrarPet = getView().findViewById(R.id.btnCadastrarPet);

        mListView = getView().findViewById(R.id.lv_pets);

        // Monta listview e mAdapter
        mPets = new ArrayList<>();
        mAdapter = new PetAdapter(getActivity(), mPets);
        mListView.setAdapter(mAdapter);

        carregarPets();

        this.chamarInfoPetListener();

        //Ação do botão de cadastrar o pet, que abre a tela para o seu cadastro
        mCadastrarPet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), CadastroPetActivity.class);
                startActivity(intent);
            }
        });

    }

    //Método que passa as informações de um pet para a Activity que exibe seus detalhes
    public void chamarInfoPetListener() {
        this.mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent intent = new Intent(getActivity(), InfoPetActivity.class);
                Pet pet = mPets.get(position);
                intent.putExtra("Pet", pet);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });
    }

    // Recuperar pets do Firebase
    private void carregarPets(){

        Query query = ConfiguracaoFirebase.getFirebase().child("pets").orderByChild("idUsuario").equalTo(mIdUsuarioLogado);

        Log.i("NN", query.getRef().toString());
        ValueEventListener mValueEventListenerPet;
        mValueEventListenerPet = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                mPets.clear();

                // Recupera pets
                for (DataSnapshot dados : dataSnapshot.getChildren()) {
                    Pet pet = dados.getValue(Pet.class);
                    mPets.add(pet);
                }
                //Notificar o adaptador que exibe a lista de pets se houver alteração no banco
                mAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(getActivity(), "Erro na leitura do banco de dados", Toast.LENGTH_SHORT).show();
            }
        };

        query.addValueEventListener(mValueEventListenerPet);
    }

}












