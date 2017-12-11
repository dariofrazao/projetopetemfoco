package projetaobcc20172.com.projetopetemfoco.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import projetaobcc20172.com.projetopetemfoco.R;
import projetaobcc20172.com.projetopetemfoco.adapter.PetAdapter;
import projetaobcc20172.com.projetopetemfoco.config.ConfiguracaoFirebase;
import projetaobcc20172.com.projetopetemfoco.model.Pet;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth mAutenticacao;
    private DatabaseReference mFirebase;
    private ArrayList<Pet> mPets;
    private ArrayAdapter<Pet> mAdapter;
    private ValueEventListener mValueEventListenerPet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAutenticacao = ConfiguracaoFirebase.getFirebaseAutenticacao();

        //Recuperar id do usuário logado
        String idUsuarioLogado;
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        idUsuarioLogado = preferences.getString("id", "");

        Button cadastrarPet; //Botão de cadastrar o pet
        Button sair; //Botão de Logout do usuário
        mAutenticacao = ConfiguracaoFirebase.getFirebaseAutenticacao();

        Toolbar toolbar;
        toolbar = findViewById(R.id.tb_main);

        // Configura toolbar
        toolbar.setTitle("Pet Em Foco");
        toolbar.setTitleTextColor(Color.WHITE);
        setSupportActionBar(toolbar);

        sair = findViewById(R.id.botao_sair);
        cadastrarPet = findViewById(R.id.botao_cadastrar_pet);
        ListView listView;
        listView = findViewById(R.id.lv_pets);

        // Monta listview e mAdapter
        mPets = new ArrayList<>();
        mAdapter = new PetAdapter(MainActivity.this, mPets);
        listView.setAdapter(mAdapter);

        // Recuperar mPets do Firebase
        mFirebase = ConfiguracaoFirebase.getFirebase().child("usuarios").child(idUsuarioLogado).child("mPets");

        mValueEventListenerPet = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                mPets.clear();

                // Recupera mPets
                for (DataSnapshot dados : dataSnapshot.getChildren()) {
                    Pet pet = dados.getValue(Pet.class);
                    mPets.add(pet);
                }
                //Notificar o adaptar que exibe a lista de mPets se houver alteração no banco
                mAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(MainActivity.this, "Erro na leitura do banco de dados", Toast.LENGTH_SHORT).show();
            }
        };
        mFirebase.addValueEventListener(mValueEventListenerPet);

        //Ação do botão de deslogar o usuário
        sair.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deslogarUsuario();
            }
        });

        //Ação do botão de cadastrar o pet, que abre a tela para o seu cadastro
        cadastrarPet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, CadastroPetActivity.class);
                startActivity(intent);
            }
        });

        Button cadastroServico;
        cadastroServico =  findViewById(R.id.btnCadastroServico);
        cadastroServico.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(MainActivity.this, CadastroServicoActivity.class);
                startActivity(intent);
            }
        });
    }

    //Método para deslogar usuário da aplicação e retornar a tela de Login
    private void deslogarUsuario(){
        mFirebase.removeEventListener(mValueEventListenerPet);
        mAutenticacao.signOut();
        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

}
