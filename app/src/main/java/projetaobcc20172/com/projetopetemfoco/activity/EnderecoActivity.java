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
import projetaobcc20172.com.projetopetemfoco.adapter.EnderecoAdapter;
import projetaobcc20172.com.projetopetemfoco.config.ConfiguracaoFirebase;
import projetaobcc20172.com.projetopetemfoco.model.Endereco;

public class EnderecoActivity extends AppCompatActivity {

    private ArrayList<Endereco> mEnderecos;
    private ArrayAdapter<Endereco> mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_endereco);

        //Recuperar id do usuário logado
        String idUsuarioLogado;
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        idUsuarioLogado = preferences.getString("id", "");

        ImageButton mCadastrarEndereco; //Botão de cadastrar o endereço

        Toolbar toolbar;
        toolbar = findViewById(R.id.tb_main);

        // Configura toolbar
        toolbar.setTitle(R.string.endereco);
        toolbar.setTitleTextColor(Color.WHITE);
        toolbar.setNavigationIcon(R.drawable.ic_action_arrow_left_white);
        setSupportActionBar(toolbar);

        mCadastrarEndereco = findViewById(R.id.btnCadastrarEndereco);
        ListView mListView;
        mListView = findViewById(R.id.lv_enderecos);

        // Monta listview e mAdapter
        mEnderecos = new ArrayList<>();
        mAdapter = new EnderecoAdapter(EnderecoActivity.this, mEnderecos);
        mListView.setAdapter(mAdapter);

        // Recuperar endereços do Firebase
        DatabaseReference mFirebase;
        mFirebase = ConfiguracaoFirebase.getFirebase().child("usuarios").child(idUsuarioLogado).child("enderecos");

        ValueEventListener mValueEventListenerEndereco;
        mValueEventListenerEndereco = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                mEnderecos.clear();

                // Recupera endereços
                for (DataSnapshot dados : dataSnapshot.getChildren()) {
                    Endereco endereco = dados.getValue(Endereco.class);
                    mEnderecos.add(endereco);
                }
                //Notificar o adaptador que exibe a lista de endereços se houver alteração no banco
                mAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(EnderecoActivity.this, "Erro na leitura do banco de dados", Toast.LENGTH_SHORT).show();
            }
        };

        mCadastrarEndereco.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(EnderecoActivity.this, CadastroEnderecoActivity.class);
                startActivity(intent);
            }
        });
        mFirebase.addValueEventListener(mValueEventListenerEndereco);

    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
