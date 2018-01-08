package projetaobcc20172.com.projetopetemfoco.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.facebook.login.LoginManager;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import projetaobcc20172.com.projetopetemfoco.R;
import projetaobcc20172.com.projetopetemfoco.config.ConfiguracaoFirebase;

public class MainActivity extends AppCompatActivity {

    private TextView mTvTitulo, mTvSubtitulo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button sair;
        Button meusPets;
        Button btnBuscarServ;
        //Button meusFavoritos;

        Toolbar toolbar;
        toolbar = findViewById(R.id.tb_main);

        // Configura toolbar
        toolbar.setTitle("Pet Em Foco");
        toolbar.setTitleTextColor(Color.WHITE);
        toolbar.showOverflowMenu();
        setSupportActionBar(toolbar);
        toolbar.inflateMenu(R.menu.main_menu);

        sair = findViewById(R.id.btnSair);
        meusPets =  findViewById(R.id.btnMeusPets);
        btnBuscarServ = findViewById(R.id.btnBuscarServicos);
        //meusFavoritos =  findViewById(R.id.botao_meus_favoritos);

        mTvTitulo = findViewById(R.id.tvTituloConsumidor);
        mTvSubtitulo = findViewById(R.id.tvSubtituloConsumidor);

        //Recuperar id do usuário logado
        final String idUsuarioLogado;
        idUsuarioLogado = getPreferences("id", this);

        // Recuperar usuários do Firebase
        DatabaseReference mFirebase;
        mFirebase = ConfiguracaoFirebase.getFirebase().child("usuarios").child(idUsuarioLogado);

        mFirebase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String nome = (String)dataSnapshot.child("nome").getValue();
                String email = (String)dataSnapshot.child("email").getValue();
                //Setar texto com o nome e o email do usuário logado
                mTvTitulo.setText("Bem-vindo, " + nome);
                mTvSubtitulo.setText("E-mail: " + email);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        //Ação do botão para abrir a tela dos Pets
        meusPets.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(MainActivity.this, PetsActivity.class);
                startActivity(intent);
            }
        });

        btnBuscarServ.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                abrirBuscas();
            }
        });

        //Ação do botão de deslogar o fornecedor
        sair.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deslogarUsuario();
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        Drawable drawable = menu.findItem(R.id.menu_cadastrar_endereco).getIcon();
        if(drawable != null) {
            drawable.mutate();
            drawable.setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_ATOP);
        }

        return true;
    }

    //Método que abre a tela de endereços ao clicar no item do menu
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.menu_cadastrar_endereco){
            Intent intent = new Intent(MainActivity.this, EnderecoActivity.class);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    //Método para deslogar usuário da aplicação e retornar a tela de Login
    private void deslogarUsuario(){
        FirebaseAuth.getInstance().signOut();
        LoginManager.getInstance().logOut();
        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    private void abrirBuscas(){
        Intent intent = new Intent(MainActivity.this, NavigatorMenu.class);
        startActivity( intent );
        finish();
    }


    //Método que recupera o id do usuário logado
    public static String getPreferences(String key, Context context) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.getString(key, null);
    }

}
