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
import projetaobcc20172.com.projetopetemfoco.adapter.ServicoAdapter;
import projetaobcc20172.com.projetopetemfoco.config.ConfiguracaoFirebase;
import projetaobcc20172.com.projetopetemfoco.model.Servico;

public class MainActivityFornecedor extends AppCompatActivity {

    private FirebaseAuth mAutenticacao;
    private DatabaseReference mFirebase;
    private ArrayList<Servico> mServico;
    private ArrayAdapter<Servico> mAdapter;
    private ValueEventListener mValueEventListenerServico;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_fornecedor);

        mAutenticacao = ConfiguracaoFirebase.getFirebaseAutenticacao();

        //Recuperar id do fornecedor logado
        String idUsuarioLogado;
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        idUsuarioLogado = preferences.getString("idFornecedor", "");

        Button sair;
        Button cadastroServico;

        Toolbar toolbar;
        toolbar = findViewById(R.id.tb_main);

        // Configura toolbar
        toolbar.setTitle("Pet Em Foco");
        toolbar.setTitleTextColor(Color.WHITE);
        setSupportActionBar(toolbar);

        sair = findViewById(R.id.botao_sair);
        cadastroServico =  findViewById(R.id.botao_cadastrar_serviço);
        ListView listView;
        listView = findViewById(R.id.lv_serviços);

        // Monta listview e mAdapter
        mServico = new ArrayList<>();
        mAdapter = new ServicoAdapter(MainActivityFornecedor.this, mServico);
        listView.setAdapter(mAdapter);

        // Recuperar serviços do Firebase
        mFirebase = ConfiguracaoFirebase.getFirebase().child("fornecedor").child(idUsuarioLogado).child("servicos");

        mValueEventListenerServico = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                mServico.clear();

                //Recupera serviços
                for (DataSnapshot dados : dataSnapshot.getChildren()) {
                    Servico servico = dados.getValue(Servico.class);
                    mServico.add(servico);
                }
                //Notificar o adaptar que exibe a lista de serviços se houver alteração no banco
                mAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(MainActivityFornecedor.this, "Erro na leitura do banco de dados", Toast.LENGTH_SHORT).show();
            }
        };

        cadastroServico.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(MainActivityFornecedor.this, CadastroServicoActivity.class);
                startActivity(intent);
            }
        });
        mFirebase.addValueEventListener(mValueEventListenerServico);

        //Ação do botão de deslogar o usuário
        sair.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deslogarUsuario();
            }
        });

        //Ação do botão de cadastrar o serviço, que abre a tela para o seu cadastro
        cadastroServico.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivityFornecedor.this, CadastroServicoActivity.class);
                startActivity(intent);
            }
        });
    }

    //Método para deslogar fornecedor da aplicação e retornar a tela de Login
    private void deslogarUsuario(){
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = preferences.edit();
        editor.remove("idFornecedor");
        editor.apply();
        mFirebase.removeEventListener(mValueEventListenerServico);
        mAutenticacao.signOut();
        Intent intent = new Intent(MainActivityFornecedor.this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}
