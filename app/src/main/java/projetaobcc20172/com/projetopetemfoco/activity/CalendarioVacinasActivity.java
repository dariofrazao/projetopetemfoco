package projetaobcc20172.com.projetopetemfoco.activity;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
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
import projetaobcc20172.com.projetopetemfoco.adapter.VacinaAdapter;
import projetaobcc20172.com.projetopetemfoco.config.ConfiguracaoFirebase;
import projetaobcc20172.com.projetopetemfoco.model.Vacina;

public class CalendarioVacinasActivity extends AppCompatActivity {

    private ArrayList<Vacina> mVacinas;
    private ArrayAdapter<Vacina> mAdapter;
    private ListView mListView;
    private String idPet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendario_vacinas);

        //Recupera o id do pet
        idPet = getIntent().getStringExtra("idPet");

        ImageButton cadastrar; //Botão de cadastrar uma vacina

        Toolbar toolbar;
        toolbar = findViewById(R.id.tb_main);

        // Configura toolbar
        toolbar.setTitle(R.string.tb_calendario_vacina);
        toolbar.setTitleTextColor(Color.WHITE);
        toolbar.setNavigationIcon(R.drawable.ic_action_arrow_left_white);
        setSupportActionBar(toolbar);

        cadastrar = findViewById(R.id.btnCadastrar);
        //ListView listView;
        mListView = findViewById(R.id.lv_vacinas);

        // Monta listview e mAdapter
        mVacinas = new ArrayList<>();
        mAdapter = new VacinaAdapter(CalendarioVacinasActivity.this, mVacinas);
        mListView.setAdapter(mAdapter);

        carregarVacinas();

        this.chamarInfoVacinaListener();

        //Ação do botão de cadastrar uma vacina, que abre a tela para o seu cadastro
        cadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CalendarioVacinasActivity.this, CadastroVacinaActivity.class);
                intent.putExtra("idPet", idPet);
                startActivity(intent);
            }

        });
    }

    public void chamarInfoVacinaListener() {
        this.mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent intent = new Intent(CalendarioVacinasActivity.this, InfoVacinaActivity.class);
                Vacina vacina = mVacinas.get(position);
                intent.putExtra("Vacina", vacina);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });
    }

    public void carregarVacinas(){
        //Recuperar vacinas do Firebase
        Query query = ConfiguracaoFirebase.getFirebase().child("vacinas").orderByChild("idPet").equalTo(idPet);

        ValueEventListener mValueEventListenerVacina;
        mValueEventListenerVacina = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                mVacinas.clear();

                // Recupera vacinas
                for (DataSnapshot dados : dataSnapshot.getChildren()) {
                    Vacina vacina = dados.getValue(Vacina.class);
                    mVacinas.add(vacina);
                }
                //Notificar o adaptar que exibe a lista de vacinas se houver alteração no banco
                mAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(CalendarioVacinasActivity.this, "Erro na leitura do banco de dados", Toast.LENGTH_SHORT).show();
            }
        };

        query.addValueEventListener(mValueEventListenerVacina);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
