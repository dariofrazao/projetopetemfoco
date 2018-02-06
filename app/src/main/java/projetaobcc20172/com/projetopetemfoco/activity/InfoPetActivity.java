package projetaobcc20172.com.projetopetemfoco.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.firebase.ui.storage.images.FirebaseImageLoader;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import projetaobcc20172.com.projetopetemfoco.R;
import projetaobcc20172.com.projetopetemfoco.database.services.PetDaoImpl;
import projetaobcc20172.com.projetopetemfoco.model.Pet;
import projetaobcc20172.com.projetopetemfoco.utils.Utils;

public class InfoPetActivity extends AppCompatActivity {

    private TextView mTvNome, mTvIdade, mTvTipoAnimal, mTvGenero, mTvRaca, mTvPorte;
    private Button mCalendarioVacinas, mEditar, mExcluir;
    private ImageView mFotoDetalhesPet;
    private Pet mPet;
    private String mIdUsuarioLogado;
    private FirebaseStorage mStorage;
    private StorageReference mStorageReference;
    private StorageReference filePath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_pet);

        mCalendarioVacinas = findViewById(R.id.btnCalendarioDeVacinas);
        mEditar = findViewById(R.id.btnEditar);
        mExcluir = findViewById(R.id.btnExcluir);

        mTvIdade = findViewById(R.id.tvIdade);
        mTvNome = findViewById(R.id.tvNome);
        mTvPorte = findViewById(R.id.tvPorte);
        mTvRaca = findViewById(R.id.tvRaca);
        mTvGenero = findViewById(R.id.tvGenero);
        mTvTipoAnimal = findViewById(R.id.tvTipoAnimal);

        mPet = (Pet) getIntent().getSerializableExtra("Pet");

        //Recuperar id do usuário logado
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        mIdUsuarioLogado = preferences.getString("id", "");

        Toolbar toolbar;
        toolbar = findViewById(R.id.tb_main);

        // Configura toolbar
        toolbar.setTitle(R.string.tb_detalhes_pet);
        toolbar.setTitleTextColor(Color.WHITE);
        toolbar.setNavigationIcon(R.drawable.ic_action_arrow_left_white);
        setSupportActionBar(toolbar);

        preencherCampos();

        editarListener();

        excluirListener();

        showCalendarioVacinaListener();
    }

    //Método que preenche os campos com as informações do pet
    public void preencherCampos() {
        mTvTipoAnimal.setText("Tipo: " + mPet.getTipo());
        mTvGenero.setText("Gênero: " + mPet.getGenero());
        if(mPet.getRaça() == ""){
            mTvRaca.setVisibility(View.INVISIBLE);
        }
        else{
            mTvRaca.setText("Raça: " + mPet.getRaça());
        }
        mTvPorte.setText("Porte: " + mPet.getPorte());
        mTvIdade.setText("Idade: " + mPet.getIdade());
        mTvNome.setText(mPet.getNome());

        mStorage = FirebaseStorage.getInstance();
        mStorageReference = mStorage.getReference();
        mFotoDetalhesPet = findViewById(R.id.ivFotoDetalhesPet);
        filePath = mStorageReference.child("imagensPets").
                child(mIdUsuarioLogado).child(mPet.getIdPet()).child(mPet.getIdPet());

        //Método que exibe a foto de um pet, através do Glide
        try {
            Glide.with(InfoPetActivity.this)
                    .using(new FirebaseImageLoader())
                    .load(filePath).asBitmap()
                    .skipMemoryCache(true)
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .error(R.drawable.ic_action_meus_pets)
                    .into(mFotoDetalhesPet);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    //Método para excluir um pet
    public void excluirListener() {
        mExcluir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case DialogInterface.BUTTON_POSITIVE:
                                // Botão sim foi clicado
                                PetDaoImpl petDao = new PetDaoImpl(InfoPetActivity.this);
                                petDao.remover(mPet, mIdUsuarioLogado);
                                InfoPetActivity.super.onBackPressed();
                                finish();
                                break;

                            case DialogInterface.BUTTON_NEGATIVE:
                                // Botão não foi clicado
                                break;
                            default:
                                break;
                        }
                    }
                };

                Utils.mostrarPerguntaSimNao(InfoPetActivity.this, getString(R.string.atencao),
                        getString(R.string.pergunta_confirma_remocao_pet), dialogClickListener,
                        dialogClickListener);
            }
        });
    }

    public void editarListener() {
        mEditar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Enviar para a Activity de Edição do pet seus atuais dados salvos para exibição
                Intent intent = new Intent(InfoPetActivity.this, EditarPetActivity.class);
                intent.putExtra("idPet", mPet.getIdPet());
                intent.putExtra("nomePet", mPet.getNome());
                intent.putExtra("raçaPet", mPet.getRaça());
                intent.putExtra("idadePet", mPet.getIdade());
                intent.putExtra("tipoPet", mPet.getTipo());
                intent.putExtra("portePet", mPet.getPorte());
                intent.putExtra("generoPet", mPet.getGenero());
                intent.putExtra("fotoPet", mPet.getFoto());
                startActivityForResult(intent,1);
                finish();
            }
        });
    }

    public void showCalendarioVacinaListener() {
        mCalendarioVacinas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(InfoPetActivity.this, CalendarioVacinasActivity.class);
                intent.putExtra("idPet", mPet.getIdPet());
                startActivity(intent);
            }
        });
    }

    //Método do botão voltar
    @Override
    public void onBackPressed() {
        InfoPetActivity.super.onBackPressed();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

}
