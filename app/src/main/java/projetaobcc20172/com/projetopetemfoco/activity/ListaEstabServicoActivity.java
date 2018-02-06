package projetaobcc20172.com.projetopetemfoco.activity;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;
import projetaobcc20172.com.projetopetemfoco.R;
import projetaobcc20172.com.projetopetemfoco.adapter.ServicoAdapterListView;
import projetaobcc20172.com.projetopetemfoco.config.ConfiguracaoFirebase;
import projetaobcc20172.com.projetopetemfoco.config.ConfiguracoesBuscaServico;
import projetaobcc20172.com.projetopetemfoco.model.Avaliacao;
import projetaobcc20172.com.projetopetemfoco.model.Fornecedor;
import projetaobcc20172.com.projetopetemfoco.model.Servico;
import projetaobcc20172.com.projetopetemfoco.utils.Enumerates;
import projetaobcc20172.com.projetopetemfoco.utils.Utils;

/**
 * Created by raul1 on 05/01/2018.
 */

public class ListaEstabServicoActivity extends AppCompatActivity {
    private Servico mServico;
    private ArrayList<String[]> mResultado;
    private ArrayAdapter<String[]> mAdapter;
    private LocationManager locationManager;
    private ProgressBar mProgresso;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_servicos);
        Toolbar toolbar = findViewById(R.id.tbBuscaServicos);
        Button btnFiltro = findViewById(R.id.btnFiltroServico);
        mProgresso = findViewById(R.id.pbProgressoBuscaServico);
        // Configura toolbar
        toolbar.setTitle("Serviços");
        toolbar.setTitleTextColor(Color.WHITE);
        toolbar.setNavigationIcon(R.drawable.ic_action_arrow_left_white);
        setSupportActionBar(toolbar);
        ListView listView = findViewById(R.id.lvEstaServicoBusca);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                exibirServico(position);
            }
        });

        mResultado = new ArrayList<>();
        mAdapter = new ServicoAdapterListView(ListaEstabServicoActivity.this,mResultado);
        listView.setAdapter(mAdapter);
        btnFiltro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(ListaEstabServicoActivity.this, FiltroServicoDialog.class);
                startActivity(intent);
            }
        });
        verificarGPS();
        mAdapter.notifyDataSetChanged();

    }

    @Override
    protected void onResume(){
        super.onResume();
        mProgresso.setVisibility(View.VISIBLE);
        buscarServico(ConfiguracoesBuscaServico.getsOpcaoServico(), ConfiguracoesBuscaServico.getsOpcaosPet());
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        finish();
        return true;
    }

    private void verificarGPS(){

        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)){
            perdirParaLigarGPS();
            //Toast.makeText(this, "GPS is Enabled in your devide", Toast.LENGTH_SHORT).show();
        }

    }

    private void buscarServico(String servico,ArrayList<String> pets) {
        mResultado.clear();
        verificarGPS();
        if(locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) && ConfiguracoesBuscaServico.getsEstado().equals(Enumerates.Estado.DEFAULT)) {
            //mProgresso.setVisibility(View.VISIBLE);
            ConfiguracoesBuscaServico.setsEstado(Enumerates.Estado.BUSCANDO);
            for (String pet : pets) {
                Query query;
                if ("Todos".equals(servico) && "Todos".equals(pet)) {
                    query = ConfiguracaoFirebase.getFirebase().child("servico_fornecedor").orderByChild("nome_tipoPet");
                } else if ("Todos".equals(servico)) {
                    query = ConfiguracaoFirebase.getFirebase().child("servico_fornecedor").orderByChild("pet").equalTo(pet);
                } else if ("Todos".equals(pet)) {
                    query = ConfiguracaoFirebase.getFirebase().child("servico_fornecedor").orderByChild("servico").equalTo(servico);
                } else {
                    query = ConfiguracaoFirebase.getFirebase().child("servico_fornecedor").orderByChild("nome_tipoPet").equalTo(servico + "_" + pet);
                }

                query.addListenerForSingleValueEvent(new ValueEventListener() {

                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot dado : dataSnapshot.getChildren()) {
                            String tipoServico = dado.child("nome_tipoPet").getValue(String.class).split("_")[0];
                            String[] resultado = {tipoServico, dado.child("nomeFornecedor").getValue(String.class), dado.child("valor").getValue(String.class), dado.child("pet").getValue(String.class),
                                    dado.child("latitude").getValue(String.class), dado.child("longitude").getValue(String.class), "0" , dado.child("nota").getValue(String.class), dado.getKey()};
                            mResultado.add(resultado);
                        }
                        ConfiguracoesBuscaServico.filtrar(projetaobcc20172.com.projetopetemfoco.activity.ListaEstabServicoActivity.this, mResultado);

                        //Caso não tenham sido encontrados resultados
                        if (mResultado.size() == 0) {
                            Utils.mostrarMensagemCurta(projetaobcc20172.com.projetopetemfoco.activity.ListaEstabServicoActivity.this, getString(R.string.servicos_nao_encontrado));
                        }
                        mAdapter.notifyDataSetChanged();
                        mProgresso.setVisibility(View.INVISIBLE);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        //Método com corpo vazio
                    }
                });
            }
        }
    }


    //Método que chama a activity para exibir informações do servico
    public void exibirServico(final int position) {
        String idServico  = mResultado.get(position)[8];
        Intent intent = new Intent(this, ExibiAvalicoesServicosActivity.class);
        intent.putExtra("Servico", idServico);
        startActivity(intent);
}


    private void perdirParaLigarGPS(){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage("GPS está desligado, deseja liga-lo?")
                .setCancelable(false)
                .setPositiveButton("Vá para as configurações de localização para ativa-lo",
                        new DialogInterface.OnClickListener(){
                            public void onClick(DialogInterface dialog, int id){
                                Intent callGPSSettingIntent = new Intent(
                                        android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                                startActivity(callGPSSettingIntent);
                            }
                        });
        alertDialogBuilder.setNegativeButton("Cancelar",
                new DialogInterface.OnClickListener(){
                    public void onClick(DialogInterface dialog, int id){
                        dialog.cancel();
                    }
                });
        AlertDialog alert = alertDialogBuilder.create();
        alert.show();
    }

}
