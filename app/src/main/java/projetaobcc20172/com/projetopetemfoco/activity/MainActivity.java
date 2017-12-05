package projetaobcc20172.com.projetopetemfoco.activity;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;

import projetaobcc20172.com.projetopetemfoco.R;
import projetaobcc20172.com.projetopetemfoco.config.ConfiguracaoFirebase;

public class MainActivity extends AppCompatActivity {

     private FirebaseAuth autenticacao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button cadastrarPet; //Botão de cadastrar o pet
        Button sair; //Botão de Logout do usuário
        autenticacao = ConfiguracaoFirebase.getFirebaseAutenticacao();

        sair = findViewById(R.id.botao_sair);
        cadastrarPet = findViewById(R.id.botao_cadastrar_pet);

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
    }

    //Método para deslogar usuário da aplicação e retornar a tela de Login
    private void deslogarUsuario(){
        autenticacao.signOut();
        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }
}
