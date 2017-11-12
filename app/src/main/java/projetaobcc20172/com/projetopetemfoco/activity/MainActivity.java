package projetaobcc20172.com.projetopetemfoco.activity;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);
    }

    @Override
    public void onBackPressed() {
        this.deslogarTelaLogin();
    }

    private void deslogarTelaLogin(){
        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        startActivity( intent );
        LoginActivity.setLoginAutomatico(false);
        finish();
    }

}
