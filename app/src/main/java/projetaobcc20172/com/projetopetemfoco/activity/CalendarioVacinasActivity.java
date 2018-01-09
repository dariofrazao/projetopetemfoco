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
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import projetaobcc20172.com.projetopetemfoco.R;
import projetaobcc20172.com.projetopetemfoco.adapter.VacinaAdapter;
import projetaobcc20172.com.projetopetemfoco.config.ConfiguracaoFirebase;
import projetaobcc20172.com.projetopetemfoco.model.Vacina;

public class CalendarioVacinasActivity extends AppCompatActivity {

    private ArrayList<Vacina> mVacinas;
    private ArrayAdapter<Vacina> mAdapter;
    private ListView mListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendario_vacinas);

        //recupera a id do pet
        final String petId = getIntent().getStringExtra("petId");

        //Recuperar id do usuário logado
        String idUsuarioLogado;
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        idUsuarioLogado = preferences.getString("id", "");

        ImageButton cadastrar; //Botão de cadastrar o pet

        Toolbar toolbar;
        toolbar = findViewById(R.id.tb_main);

        // Configura toolbar
        toolbar.setTitle("Calendario");
        toolbar.setTitleTextColor(Color.WHITE);
        toolbar.setNavigationIcon(R.drawable.ic_action_arrow_left_white);
        setSupportActionBar(toolbar);

        cadastrar = findViewById(R.id.btnCadastrar);
        //ListView listView;
        mListView = findViewById(R.id.lv_vacinas);

        // Monta listview e mAdapter
        mVacinas = new ArrayList<>();
        mAdapter = new VacinaAdapter(CalendarioVacinasActivity.this, mVacinas,petId);
        mListView.setAdapter(mAdapter);

        // Recuperar pets do Firebase
        DatabaseReference mFirebase;
        mFirebase = ConfiguracaoFirebase.getFirebase().child("usuarios").child(idUsuarioLogado).child("pets").child(petId).child("calendarioVacinas");

        ValueEventListener mValueEventListenerPet;
        mValueEventListenerPet = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                mVacinas.clear();

                // Recupera mPets
                for (DataSnapshot dados : dataSnapshot.getChildren()) {
                    Vacina vacina = dados.getValue(Vacina.class);
                    vacina.setId(dados.getKey());
                    mVacinas.add(vacina);
                }
                //Notificar o adaptar que exibe a lista de mPets se houver alteração no banco
                mAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(CalendarioVacinasActivity.this, "Erro na leitura do banco de dados", Toast.LENGTH_SHORT).show();
            }
        };

        mFirebase.addValueEventListener(mValueEventListenerPet);

        //Ação do botão de cadastrar o pet, que abre a tela para o seu cadastro
        cadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CalendarioVacinasActivity.this, CadastroVacinaActivity.class);
                intent.putExtra("petId",petId);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
