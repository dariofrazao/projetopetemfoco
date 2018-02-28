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
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import projetaobcc20172.com.projetopetemfoco.R;
import projetaobcc20172.com.projetopetemfoco.adapter.ListaInformacoesAdapterView;
import projetaobcc20172.com.projetopetemfoco.config.ConfiguracaoFirebase;
import projetaobcc20172.com.projetopetemfoco.database.services.FavoritoDaoImpl;
import projetaobcc20172.com.projetopetemfoco.model.Endereco;
import projetaobcc20172.com.projetopetemfoco.model.Favorito;
import projetaobcc20172.com.projetopetemfoco.model.Fornecedor;
import projetaobcc20172.com.projetopetemfoco.model.Servico;

public class ExibiInformacoesEstabelecimentoActivity extends AppCompatActivity implements Serializable {

    private Fornecedor mFornecedor;
    private Favorito mFavorito;
    private String mIdFavorito;
    private String mIdUsuarioLogado;
    private String mConfirma = "0";
    private String mKey;
    private MapView mapView;

    private Map<String, Integer> mImagens = new HashMap<String, Integer>();

    //private ArrayList<String[]> mResultado;
    private ListView mExibeListaServicos;


    @SuppressLint("WrongConstant")
    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    //permite que essa variavel seja vista pela classe de teste

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_acesso_informacoes_estabelecimento);


        mImagens.put("Autônomo",R.drawable.ic_action_autonomo);
        mImagens.put("Estabelecimento",R.drawable.ic_action_estabelecimentos);

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

        mExibeListaServicos = findViewById(R.id.lvListaServicos);
        ImageView img = findViewById(R.id.ivFotoDetalhesPet);
        img.setImageResource(mImagens.get(mFornecedor.getTipo()));


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
        ArrayAdapter<Servico> mAdapter;
        mAdapter = new ListaInformacoesAdapterView(this, mFornecedor.getServicos());
        mExibeListaServicos.setAdapter(mAdapter);

        //Recuperar id do usuário logado
        mIdUsuarioLogado = getPreferences("id", this);

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

        mFavorito = new Favorito(mIdFavorito, mFornecedor.getId(), mFornecedor.getNome(), mFornecedor.getTelefone(), mConfirma, mFornecedor.getCpfCnpj());

        // botão para controlar troca de imagens do favorito
        final ImageButton mSalvarFavorito = findViewById(R.id.bt_salvar_favorito);
        mSalvarFavorito.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mFavorito.getConfirma().equals("1")) {
                    removerFavorito();
                    mFavorito.setConfirma("0");
                    mSalvarFavorito.setBackgroundResource(R.drawable.ic_action_favoritar_false);
                } else{
                    salvarFavorito();
                    mSalvarFavorito.setBackgroundResource(R.drawable.ic_action_favoritar_true);
                }
            }
        });

        carregarFavorito(mFornecedor, mSalvarFavorito);

        this.chamarInfoServicosListener();

        Button mAgendaEstabelecimento;
        mAgendaEstabelecimento = findViewById(R.id.btnAgendaFornecedor);
        mAgendaEstabelecimento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Query query = ConfiguracaoFirebase.getFirebase().child("fornecedor").orderByChild("nome");
                query.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot dados : dataSnapshot.getChildren()) {
                            float nota = 0;
                            if (dados.child("nota").getValue(float.class) != null) {
                                nota = dados.child("nota").getValue(float.class);
                            }
                            mFornecedor = new Fornecedor(dados.child("nome").getValue(String.class), dados.child("email").getValue(String.class), dados.child("cpfCnpj").getValue(String.class)
                                    , dados.child("horarios").getValue(String.class), nota, dados.child("telefone").getValue(String.class),
                                    dados.child("endereco").getValue(Endereco.class),dados.child("tipo").getValue(String.class));
                            mFornecedor.setId(dados.getKey());
                            Intent intent = new Intent(ExibiInformacoesEstabelecimentoActivity.this, ContratarServicoActivity.class);
                            intent.putExtra("Fornecedor", mFornecedor);
                            //intent.putExtra("Servico", mServico);
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(intent);
                        }

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        //vazio
                    }
                });

            }

        });

    }

    //Método do botão voltar
    @Override
    public void onBackPressed() {
        ExibiInformacoesEstabelecimentoActivity.super.onBackPressed();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    //Método que passa as informações de um servico
    public void chamarInfoServicosListener() {
        mExibeListaServicos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                carregarKeyServico(mFornecedor.getServicos().get(position));
            }
        });
    }

    private void carregarKeyServico(final Servico servico){

        Query query = ConfiguracaoFirebase.getFirebase().child("servicos").orderByChild("idFornecedor").equalTo(mFornecedor.getId());

        ValueEventListener mValueEventListenerServico;
        mValueEventListenerServico = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Recupera o servico
                for (DataSnapshot dados : dataSnapshot.getChildren()){
                    if (dados.child("nome").getValue().equals(servico.getNome()) &
                            dados.child("tipoPet").getValue().equals(servico.getTipoPet()) &
                            dados.child("valor").getValue().equals(servico.getValor()) &
                            dados.child("descricao").getValue().equals(servico.getDescricao())){
                        mKey = dados.getKey();
                        break;
                    }
                }
                Intent intent = new Intent(ExibiInformacoesEstabelecimentoActivity.this, InfoServicoActivity.class);
                String nome = servico.getNome();
                String nomeFornecedor = mFornecedor.getNome();
                String valor = servico.getValor();
                String animal = servico.getTipoPet();
                String idF = mFornecedor.getId();
                String [] resultado = {nome, nomeFornecedor, valor, animal, null, null, null, null, idF, mKey};
                intent.putExtra("Servico", resultado);
                startActivity(intent);
                finish();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                //vazio
            }
        };
        query.addValueEventListener(mValueEventListenerServico);
    }


    // Recuperar favorito do Firebase para saber se ele é existente
    private void carregarFavorito(final Fornecedor fornecedor, final ImageButton mSalvarFavorito){

        Query query = ConfiguracaoFirebase.getFirebase().child("favoritos").orderByChild("idUsuario").equalTo(mIdUsuarioLogado);

        ValueEventListener mValueEventListenerFavorito;
        mValueEventListenerFavorito = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Recupera o favorito
                for (DataSnapshot dados : dataSnapshot.getChildren()) {
                    if(dados.child("idFornecedor").getValue().toString().equals(fornecedor.getId())) {
                        mFavorito.setConfirma("1");
                        break;
                    }
                }
                if(mFavorito.getConfirma().equals("1")) {
                    mSalvarFavorito.setBackgroundResource(R.drawable.ic_action_favoritar_true);
                    mSalvarFavorito.setVisibility(View.VISIBLE);
                } else{
                    mSalvarFavorito.setBackgroundResource(R.drawable.ic_action_favoritar_false);
                    mSalvarFavorito.setVisibility(View.VISIBLE);
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                //vazio
            }
        };
        query.addValueEventListener(mValueEventListenerFavorito);
    }

    //Método que salva o favorito no banco--LuizAlberes
    private boolean salvarFavorito(){
        try {
            //Toast.makeText(AcessoInformacoesEstabelecimentoActivity.this, mFavorito.getIdFavorito(), Toast.LENGTH_SHORT).show();

            //Chamada do DAO para salvar no banco
            FavoritoDaoImpl favoritoDao =  new FavoritoDaoImpl(this);
            favoritoDao.compararInserir(mFavorito, mIdUsuarioLogado);

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;

    }

    //Método que remove o favorito no banco--LuizAlberes
    private boolean removerFavorito(){
        try {
            //Chamada do DAO para salvar no banco
            FavoritoDaoImpl favoritoDao =  new FavoritoDaoImpl(this);
            favoritoDao.compararRemover(mFavorito, mIdUsuarioLogado);

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    //Método que chama a activity para realizar a avaliação
    public void avaliar(Fornecedor fornecedor) {
        Intent intent = new Intent(ExibiInformacoesEstabelecimentoActivity.this, AvaliarEstabelecimentoActivity.class);
        intent.putExtra("Fornecedor", fornecedor);
        startActivity(intent);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putAll(outState);
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }

    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
    }
    //Método que chama a activity para exibir as avaliações
    public void avaliacoes(Fornecedor fornecedor) {
        Intent intent = new Intent(ExibiInformacoesEstabelecimentoActivity.this, ExibiAvalicoesEstabelecimentoActivity.class);
        intent.putExtra("Fornecedor", fornecedor);
        startActivity(intent);
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

