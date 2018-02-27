package projetaobcc20172.com.projetopetemfoco.activity;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.cooltechworks.creditcarddesign.CardEditActivity;
import com.cooltechworks.creditcarddesign.CreditCardUtils;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import projetaobcc20172.com.projetopetemfoco.R;
import projetaobcc20172.com.projetopetemfoco.config.ConfiguracaoFirebase;
import projetaobcc20172.com.projetopetemfoco.fragment.ResumoContratacaoFragment;
import projetaobcc20172.com.projetopetemfoco.model.Endereco;
import projetaobcc20172.com.projetopetemfoco.model.Fornecedor;
import projetaobcc20172.com.projetopetemfoco.model.Servico;

public class InfoServicoActivity extends AppCompatActivity {

    private static final int PERMISSION_GRANTED = 1;
    private TextView mTvNome, mTvNomeFornecedor, mTvValor, mTvTipoAnimalServico;
    private ImageView mImagemDetalhesServico;
    private String[] mServico;
    private Fornecedor fornecedor;

    final int GET_NEW_CARD = 2;
    public static final String SERVICE = "SERVICE";
    public static final String ESTABELECIMENTO = "ESTABELECIMENTO";
    public static final String TIPOPET = "TIPOPET";
    public static final String AMOUNT = "AMOUNT";
    public static final String TOTAL = "TOTAL";
    public static final String TOTAL_VALUE = "TOTAL_VALUE";

    private double total;
    private String preco;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_servico);

        final int callbackId = 42;
        checkPermissions(callbackId, Manifest.permission.READ_CALENDAR, Manifest.permission.WRITE_CALENDAR);

        mImagemDetalhesServico = findViewById(R.id.ivDetalhesServico);
        mTvNome = findViewById(R.id.tvNome);
        mTvNomeFornecedor = findViewById(R.id.tvNomeFornecedor);
        mTvValor = findViewById(R.id.tvValor);
        mTvTipoAnimalServico = findViewById(R.id.tvTipoAnimalServico);

        mServico = (String[]) getIntent().getSerializableExtra("Servico");

        Toolbar toolbar;
        toolbar = findViewById(R.id.tb_main);

        // Configura toolbar
        toolbar.setTitle(R.string.tb_detalhes_servico);
        toolbar.setTitleTextColor(Color.WHITE);
        toolbar.setNavigationIcon(R.drawable.ic_action_arrow_left_white);
        setSupportActionBar(toolbar);

        preencherCampos();

        Button mEstabelecimento;
        mEstabelecimento = findViewById(R.id.btnEstabelecimento);
        mEstabelecimento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                exibirEstabelecimento();

            }
        });

        Button mAvaliarServico;
        mAvaliarServico = findViewById(R.id.btnAvaliarServico);
        mAvaliarServico.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                exibirAvaliarServico();
            }
        });

        Button mAvaliacoesServico;
        mAvaliacoesServico = findViewById(R.id.btnAvaliacoesServico);
        mAvaliacoesServico.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                exibirAvaliacoesServico();
            }
        });

        Button mAgendaEstabelecimento;
        mAgendaEstabelecimento = findViewById(R.id.btnAgendaEstabelecimento);
        mAgendaEstabelecimento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Query query = ConfiguracaoFirebase.getFirebase().child("fornecedor").orderByChild("nome").equalTo(mServico[1]);
                query.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot dados : dataSnapshot.getChildren()) {
                            float nota = 0;
                            if (dados.child("nota").getValue(float.class) != null) {
                                nota = dados.child("nota").getValue(float.class);
                            }
                            fornecedor = new Fornecedor(dados.child("nome").getValue(String.class), dados.child("email").getValue(String.class), dados.child("cpfCnpj").getValue(String.class)
                                    , dados.child("horarios").getValue(String.class), nota, dados.child("telefone").getValue(String.class),
                                    dados.child("endereco").getValue(Endereco.class),dados.child("tipo").getValue(String.class));
                            fornecedor.setId(dados.getKey());
                            Intent intent = new Intent(InfoServicoActivity.this, ContratarServicoActivity.class);
                            intent.putExtra("Fornecedor", fornecedor);
                            intent.putExtra("Servico", mServico);
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

    private void exibirAvaliacoesServico() {
        Intent intent = new Intent(InfoServicoActivity.this, ExibiAvalicoesServicosActivity.class);
        intent.putExtra("Servico", mServico);// id serviço
        startActivity(intent);
    }

    private void exibirAvaliarServico() {
        Intent intent = new Intent(InfoServicoActivity.this, AvaliarServicoActivity.class);
        intent.putExtra("Servico", mServico[9]);// id serviço
        startActivity(intent);
    }

    private void checkPermissions(int callbackId, String... permissionsId) {
        boolean permissions = true;
        for (String p : permissionsId) {
            permissions = permissions && ContextCompat.checkSelfPermission(this, p) == PERMISSION_GRANTED;
        }

        if (!permissions)
            ActivityCompat.requestPermissions(this, permissionsId, callbackId);
    }

    public void preencherCampos() {

        mImagemDetalhesServico.setImageResource(imagemServico(mServico[0]));
        mTvNome.setText("Serviço: " + mServico[0]);
        mTvNomeFornecedor.setText("Estabelecimento: " + mServico[1]);
        mTvTipoAnimalServico.setText("Tipo de Animal: " + mServico[3]);
        mTvValor.setText("Valor: " + mServico[2]);

    }

    //Método que chama a activity para exibir informações do estabelecimento
    public void exibirEstabelecimento() {

        //Buscar servicos do estabelecimento selecionado
        Query query = ConfiguracaoFirebase.getFirebase().child("fornecedor").orderByChild("nome").equalTo(mServico[1]);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot dados : dataSnapshot.getChildren()) {

                    float nota = 0;
                    if (dados.child("nota").getValue(float.class) != null) {
                        nota = dados.child("nota").getValue(float.class);
                    }
                    fornecedor = new Fornecedor(dados.child("nome").getValue(String.class), dados.child("email").getValue(String.class), dados.child("cpfCnpj").getValue(String.class)
                            , dados.child("horarios").getValue(String.class), nota, dados.child("telefone").getValue(String.class),
                            dados.child("endereco").getValue(Endereco.class),dados.child("tipo").getValue(String.class));
                    fornecedor.setId(dados.getKey());
                    exibirServicos();

                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                //Método com corpo vazio
            }
        });


    }

    private void exibirServicos(){

        //Buscar servicos do estabelecimento selecionado
        Query query = ConfiguracaoFirebase.getFirebase().child("servicos").orderByChild("idFornecedor").equalTo(mServico[8]);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ArrayList<Servico> servicos = new ArrayList<>();
                for (DataSnapshot dados : dataSnapshot.getChildren()) {
                    Servico servico = dados.getValue(Servico.class);
                    servicos.add(servico);

                }
                fornecedor.setServicos(servicos);
                Intent intent = new Intent(InfoServicoActivity.this, ExibiInformacoesEstabelecimentoActivity.class);
                intent.putExtra("Fornecedor", fornecedor);
                startActivity(intent);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                //Método com corpo vazio
            }
        });
    }

    public static int imagemServico(String nomeServico){
        if(nomeServico.equalsIgnoreCase("Banho")){
           return (R.drawable.servico_banho);
        }else if(nomeServico.equalsIgnoreCase("Tosa")){
            return (R.drawable.servico_tosa);
        }else if(nomeServico.equalsIgnoreCase("Hospedagem")){
            return (R.drawable.servico_hospedagem);
        }else if(nomeServico.equalsIgnoreCase("Passeio")){
            return (R.drawable.servico_passeio);
        }else if(nomeServico.equalsIgnoreCase("Vacinação")){
            return (R.drawable.servico_vacinacao);
        }else{
            return (R.drawable.servico_todos);
        }
    }

    //Método do botão voltar
    @Override
    public void onBackPressed() {
        InfoServicoActivity.super.onBackPressed();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
