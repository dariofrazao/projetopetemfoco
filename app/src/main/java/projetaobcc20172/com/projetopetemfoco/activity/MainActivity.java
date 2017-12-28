package projetaobcc20172.com.projetopetemfoco.activity;

import android.content.Context;
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
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import projetaobcc20172.com.projetopetemfoco.R;
import projetaobcc20172.com.projetopetemfoco.adapter.PetAdapter;
import projetaobcc20172.com.projetopetemfoco.config.ConfiguracaoFirebase;
import projetaobcc20172.com.projetopetemfoco.model.Pet;

public class MainActivity extends AppCompatActivity {

    private TextView mTvTitulo, mTvSubtitulo;
    private FirebaseAuth mAutenticacao;
    private DatabaseReference mFirebase;
    private static FirebaseDatabase mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAutenticacao = ConfiguracaoFirebase.getFirebaseAutenticacao();
        Button sair;
        Button meusPets;
        Button buscarServicos;
        Button meusFavoritos;

        Toolbar toolbar;
        toolbar = findViewById(R.id.tb_main);

        // Configura toolbar
        toolbar.setTitle("Pet Em Foco");
        toolbar.setTitleTextColor(Color.WHITE);
        setSupportActionBar(toolbar);

        sair = findViewById(R.id.botao_sair);
        meusPets =  findViewById(R.id.botao_meus_pets);
        buscarServicos =  findViewById(R.id.botao_buscar_servicos);
        meusFavoritos =  findViewById(R.id.botao_meus_favoritos);

        mTvTitulo = findViewById(R.id.tvTituloConsumidor);
        mTvSubtitulo = findViewById(R.id.tvSubtituloConsumidor);

        //Recuperar id do fornecedor logado
        final String idUsuarioLogado;
        idUsuarioLogado = getPreferences("id", this);

        // Recuperar serviços do Firebase
        mFirebase = ConfiguracaoFirebase.getFirebase().child("usuarios").child(idUsuarioLogado);

        mFirebase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String nome = (String)dataSnapshot.child("nome").getValue();
                String email = (String)dataSnapshot.child("email").getValue();
                mTvTitulo.setText(nome);
                mTvSubtitulo.setText("E-mail: " + email);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        meusPets.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(MainActivity.this, PetsActivity.class);
                startActivity(intent);
            }
        });

        //Ação do botão de deslogar o fornecedor
        sair.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deslogarFornecedor();
            }
        });

    }

    //Método para deslogar fornecedor da aplicação e retornar a tela de Login
    private void deslogarFornecedor(){
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = preferences.edit();
        editor.remove("idFornecedor");
        editor.apply();
        mAutenticacao.signOut();
        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    //Método que recupera o id do fornecedor logado, para salvar o endereço no nó do fornecedor que o está cadastrando
    public static String getPreferences(String key, Context context) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.getString(key, null);
    }

}
