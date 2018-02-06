package projetaobcc20172.com.projetopetemfoco.activity;
/**
 * Created by Alexsandro on 03/12/17.
 */


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.VisibleForTesting;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import java.io.Serializable;
import projetaobcc20172.com.projetopetemfoco.R;
import projetaobcc20172.com.projetopetemfoco.adapter.ServicoAdapterListaViewInformacoes;
import projetaobcc20172.com.projetopetemfoco.database.services.FavoritoDaoImpl;
import projetaobcc20172.com.projetopetemfoco.model.Favorito;
import projetaobcc20172.com.projetopetemfoco.adapter.ListaInformacoesAdapterView;
import projetaobcc20172.com.projetopetemfoco.model.Fornecedor;
import projetaobcc20172.com.projetopetemfoco.model.Servico;

public class AcessoInformacoesEstabelecimentoActivity extends AppCompatActivity implements Serializable {
    private Fornecedor mFornecedor;
    private Favorito mFavorito;
    private Button salvar_favorito, remover_favorito;
    private String mIdFavorito;
    private String mIdUsuarioLogado;
    private MapView mapView;

    @SuppressLint("WrongConstant")
    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    //permite que essa variavel seja vista pela classe de teste

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_acesso_informacoes_estabelecimento);

        // lista de serviços pertencente ao fornecedor
        ArrayAdapter<Servico> mAdapter;

        // Receber os dados do estabelecimento da outra activity
        Intent i = getIntent();
        mFornecedor = (Fornecedor) i.getSerializableExtra("Fornecedor");

        // Associa os componetes ao layout XML
        Toolbar toolbar = findViewById(R.id.tb_acesso_infomacoes_estabelecimento);
        TextView mExibeNomeEstabelecimento = findViewById(R.id.tvExibeNomeEstabelecimento);
        TextView mExibeEmailEstabelecimentor = findViewById(R.id.tvEmail);
        TextView mExibeTelefoneEstabelecimento = findViewById(R.id.tvTelefone);
        //TextView mExibeCpfCnpjEstabelecimento = findViewById(R.id.tvExibeCpfCnpjEstabelecimento);
        TextView mExibeHorarioEstabelecimento = findViewById(R.id.tvHorario);
        TextView mExibeEnderecoEstabelecimento = findViewById(R.id.tvEnderecoEstabelecimentoCombinado);
        ListView mExibeListaServicos = findViewById(R.id.lvListaServicos);
        mapView = findViewById(R.id.map_view);

        // Configura toolbar
        toolbar.setTitle(R.string.tb_acesso_estabelecimento);
        toolbar.setTitleTextColor(Color.WHITE);
        toolbar.setNavigationIcon(R.drawable.ic_action_arrow_left_white);
        setSupportActionBar(toolbar);

        // Inicializa o maps
        MapsInitializer.initialize(this);

        // Gets to GoogleMap from the MapView and does initialization stuff
        mapView.getMapAsync(new OnMapReadyCallback() {

            @Override
            public void onMapReady(GoogleMap googleMap) {
                setUpMap(googleMap);
            }
        });
        mapView.onCreate(savedInstanceState);

        mExibeNomeEstabelecimento.setText(mFornecedor.getNome());
        mExibeEmailEstabelecimentor.setText("E-mail: " + mFornecedor.getEmail());
        mExibeTelefoneEstabelecimento.setText("Fone: " + mFornecedor.getTelefone());
        //mExibeCpfCnpjEstabelecimento.setText(mFornecedor.getCpfCnpj());
        mExibeHorarioEstabelecimento.setText("Horário de Funcionamento: " + mFornecedor.getHorarios());
        mExibeEnderecoEstabelecimento.setText(mFornecedor.getmEnderecoCombinado());

        // Monta listview e mAdapter
        mAdapter = new ListaInformacoesAdapterView(this, mFornecedor.getServicos());
        mExibeListaServicos.setAdapter(mAdapter);

        //Recuperar id do usuário logado
        mIdUsuarioLogado = getPreferences("id", AcessoInformacoesEstabelecimentoActivity.this);

        mFavorito = new Favorito(mIdFavorito, mFornecedor.getId(), mFornecedor.getNome(), mFornecedor.getTelefone());


        Log.d("User key", mFavorito.getIdFornecedor());

        Button mBotaoAvaliarEstabelecimento = findViewById(R.id.botao_avaliar_estabelecimento);
        // Exibe a tela para avaliar
        ImageButton mBotaoAvaliarEstabelecimento = findViewById(R.id.botao_avaliar_estabelecimento);
        mBotaoAvaliarEstabelecimento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                avaliar(mFornecedor);
            }
        });

        // Exibe a tela com as avaliações
        ImageButton mBotaoExibirAvaliacoesEstabelecimento = findViewById(R.id.botao_mostrar_avaliacoes_estabelecimento);
        mBotaoExibirAvaliacoesEstabelecimento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                avaliacoes(mFornecedor);
            }
        });


        salvar_favorito = findViewById(R.id.bt_salvar_favorito);
        salvar_favorito.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(AcessoInformacoesEstabelecimentoActivity.this, mFavorito.getIdFornecedor(), Toast.LENGTH_SHORT).show();
                salvarFavorito();
            }
        });

        remover_favorito = findViewById(R.id.bt_remover_favorito);
        remover_favorito.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                removerFavorito();
            }
        });

    }


    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    //Método que salva o favorito no banco--LuizAlberes
    private boolean salvarFavorito(){
        try {
            //Toast.makeText(AcessoInformacoesEstabelecimentoActivity.this, mFavorito.getIdFavorito(), Toast.LENGTH_SHORT).show();

            //Chamada do DAO para salvar no banco
            FavoritoDaoImpl favoritoDao =  new FavoritoDaoImpl(this);
            favoritoDao.comparar(mFavorito, mIdUsuarioLogado);
            favoritoDao.inserir(mFavorito, mIdUsuarioLogado);
            salvar_favorito.setVisibility(View.INVISIBLE);
            remover_favorito.setVisibility(View.VISIBLE);

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;

    }

    //Método que remove o favorito no banco --LuizAlberes
    private boolean removerFavorito(){
        try {

            //Chamada do DAO para remover no banco
            FavoritoDaoImpl favoritoDao =  new FavoritoDaoImpl(AcessoInformacoesEstabelecimentoActivity.this);
            favoritoDao.remover(mFavorito, mIdUsuarioLogado);
            remover_favorito.setVisibility(View.INVISIBLE);
            salvar_favorito.setVisibility(View.VISIBLE);

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    //Método que chama a activity para realizar a avaliação
    public void avaliar(Fornecedor fornecedor) {
        Intent intent = new Intent(AcessoInformacoesEstabelecimentoActivity.this, AvaliarEstabelecimentoActivity.class);
        intent.putExtra("Fornecedor", fornecedor);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }

    //Método que chama a activity para exibir as avaliações
    public void avaliacoes(Fornecedor fornecedor) {
        Intent intent = new Intent(AcessoInformacoesEstabelecimentoActivity.this, AvalicoesEstabelecimentoActivity.class);
        intent.putExtra("Fornecedor", fornecedor);
        startActivity(intent);
        finish();
    }
    //Método que recupera o id do usuário logado, para salvar o pet no nó do favorito que o está cadastrando--LuizAlberes
    public static String getPreferences(String key, Context context) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.getString(key, null);
    }

    private void setUpMap(GoogleMap googleMap) {
        // Localização as coordenadas geograficas do estabelecimento
        LatLng latLng = new LatLng(mFornecedor.getEndereco().getmLatitude(),mFornecedor.getEndereco().getmLongitude());

        // Configura o visial do Mapa
        googleMap.setMapType(googleMap.MAP_TYPE_NORMAL);
        googleMap.getUiSettings().setZoomControlsEnabled(true);
        googleMap.getUiSettings().setCompassEnabled(true);
        googleMap.getUiSettings().setRotateGesturesEnabled(true);
        googleMap.getUiSettings().setTiltGesturesEnabled(true);
        googleMap.getUiSettings().setScrollGesturesEnabled(true);
        googleMap.getUiSettings().setTiltGesturesEnabled(true);
        googleMap.getUiSettings().setZoomControlsEnabled(true);
        googleMap.getUiSettings().setZoomGesturesEnabled(true);

        // Adiciona no map a localização do estabelecimento
        googleMap.addMarker(new MarkerOptions().position(latLng).title(mFornecedor.getNome()));
        CameraUpdate yourLocation = CameraUpdateFactory.newLatLngZoom(latLng, 13);

        // Direciona a camera para a local do estabelecimento
        googleMap.moveCamera(yourLocation);
    }


    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }
    @Override
    protected void onResume() {
        super.onResume();
        mapView.onResume();
    }
    @Override
    protected void onPause() {
        super.onPause();
        mapView.onPause();
    }
}

}

