package projetaobcc20172.com.projetopetemfoco.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
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
import projetaobcc20172.com.projetopetemfoco.utils.Enumerates;
import projetaobcc20172.com.projetopetemfoco.utils.Utils;

/**
 * Created by raul1 on 05/01/2018.
 */

public class ListaEstabServicoActivity extends AppCompatActivity {

    private ArrayList<String[]> mResultado;
    private ArrayAdapter<String[]> mAdapter;
    private ListView mListView;
    private LocationManager locationManager;
    private ProgressBar mProgresso;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_servicos);
        Toolbar toolbar = findViewById(R.id.tbBuscaServicos);
        ImageView mFiltro = findViewById(R.id.ivFiltro);
        mProgresso = findViewById(R.id.pbProgressoBuscaServico);

        // Configura toolbar
        toolbar.setTitle("Serviços");
        toolbar.setTitleTextColor(Color.WHITE);
        toolbar.setNavigationIcon(R.drawable.ic_action_arrow_left_white);
        setSupportActionBar(toolbar);

        mListView = findViewById(R.id.lvEstaServicoBusca);

        mResultado = new ArrayList<String[]>();
        mAdapter = new ServicoAdapterListView(ListaEstabServicoActivity.this,mResultado);
        mListView.setAdapter(mAdapter);
        mFiltro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(ListaEstabServicoActivity.this, FiltroServicoDialog.class);
                startActivity(intent);
            }
        });

        verificarGPS();
        mAdapter.notifyDataSetChanged();

        this.chamarInfoServicoListener();

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
                                    dado.child("latitude").getValue(String.class), dado.child("longitude").getValue(String.class), "0" , dado.child("nota").getValue(String.class), dado.child("idFornecedor").getValue(String.class),dado.getKey()};
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

    //Método que passa as informações de um endereço para a Activity que exibe seus detalhes
    public void chamarInfoServicoListener() {
        this.mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent intent = new Intent(ListaEstabServicoActivity.this, InfoServicoActivity.class);
                String[] servico = mResultado.get(position);
                intent.putExtra("Servico", servico);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });
    }

}
