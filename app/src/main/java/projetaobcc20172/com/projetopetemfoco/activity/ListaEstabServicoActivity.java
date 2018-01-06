package projetaobcc20172.com.projetopetemfoco.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import projetaobcc20172.com.projetopetemfoco.R;
import projetaobcc20172.com.projetopetemfoco.adapter.ServicoAdapterListView;
import projetaobcc20172.com.projetopetemfoco.config.ConfiguracaoFirebase;

/**
 * Created by raul1 on 05/01/2018.
 */

public class ListaEstabServicoActivity extends AppCompatActivity {
    private ArrayList<String[]> mResultado;
    private ArrayAdapter<String[]> mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_servicos);
        Intent intent = getIntent();
        Toolbar toolbar = findViewById(R.id.tbBuscaServicos);
        // Configura toolbar
        toolbar.setTitle("Serviços");
        toolbar.setTitleTextColor(Color.WHITE);
        toolbar.setNavigationIcon(R.drawable.ic_action_arrow_left_white);
        setSupportActionBar(toolbar);
        ListView listView = findViewById(R.id.lvEstaServicoBusca);
        mResultado = new ArrayList<>();
        mAdapter = new ServicoAdapterListView(ListaEstabServicoActivity.this,mResultado);
        listView.setAdapter(mAdapter);
        buscarServico(intent.getStringExtra("servico") ,intent.getStringExtra("pet"));
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        finish();
        return true;
    }

    private void buscarServico(String servico,String pet){
        Query query;
        if(servico.equals("Todos") && pet.equals("Todos")){
            query = ConfiguracaoFirebase.getFirebase().child("servico_fornecedor").orderByChild("nome_tipoPet");
            System.out.println("valors 1"+servico+" "+pet);
        }
        else if(servico.equals("Todos")){
            query = ConfiguracaoFirebase.getFirebase().child("servico_fornecedor").orderByChild("pet").equalTo(pet);
            System.out.println("valors 2"+servico+" "+pet);
        }
        else if(pet.equals("Todos")){
            query = ConfiguracaoFirebase.getFirebase().child("servico_fornecedor").orderByChild("servico").equalTo(servico);
            System.out.println("valors 3"+servico+" "+pet);
        }
        else{
            query = ConfiguracaoFirebase.getFirebase().child("servico_fornecedor").orderByChild("nome_tipoPet").equalTo(servico+"_"+pet);
            System.out.println("valors 4"+servico+" "+pet);
        }
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                mResultado.clear();
                for(DataSnapshot dado: dataSnapshot.getChildren()){
                   String tipoServico = dado.child("nome_tipoPet").getValue(String.class).split("_")[0];
                   System.out.println(tipoServico);
                   String [] resultado = {tipoServico,dado.child("nomeFornecedor").getValue(String.class),dado.child("valor").getValue(String.class),dado.child("pet").getValue(String.class)};
                   mResultado.add(resultado);

                }
                mAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
