package projetaobcc20172.com.projetopetemfoco.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.os.Bundle;
import android.graphics.Color;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import projetaobcc20172.com.projetopetemfoco.R;
import projetaobcc20172.com.projetopetemfoco.database.services.PetDaoImpl;
import projetaobcc20172.com.projetopetemfoco.model.Pet;
import projetaobcc20172.com.projetopetemfoco.utils.Utils;

public class InfoPetActivity extends AppCompatActivity {

    private TextView mTvNome, mTvIdade, mTvTipoAnimal, mTvSexo, mTvRaca, mTvPorte;
    private Button mCalendarioVacinas, mEditar, mExcluir;
    private Pet mPet;
    private String mIdUsuarioLogado;

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
        mTvSexo = findViewById(R.id.tvSexo);
        mTvTipoAnimal = findViewById(R.id.tvTipoAnimal);

        mPet = (Pet) getIntent().getSerializableExtra("Pet");

        //Recuperar id do usuário logado

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        mIdUsuarioLogado = preferences.getString("id", "");

        Toolbar toolbar;
        toolbar = findViewById(R.id.tb_main);

        // Configura toolbar
        toolbar.setTitle("Detalhes do pet");
        toolbar.setTitleTextColor(Color.WHITE);
        toolbar.setNavigationIcon(R.drawable.ic_action_arrow_left_white);
        setSupportActionBar(toolbar);

        preencherCampos();

        editarListener();

        excluirListener();

        showCalendarioVacinaListener();
    }

    public void preencherCampos() {
        mTvTipoAnimal.setText("Tipo Animal: " + mPet.getTipo());
        mTvSexo.setText("Sexo: " + mPet.getGenero());
        mTvRaca.setText("Raça: " + mPet.getRaça());
        mTvPorte.setText("Porte: " + mPet.getPorte());
        mTvIdade.setText("Idade: " + mPet.getIdade());
        mTvNome.setText("Nome: " + mPet.getNome());

    }

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
                startActivityForResult(intent,1);
            }
        });
    }

    public void showCalendarioVacinaListener() {
        mCalendarioVacinas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(InfoPetActivity.this, CalendarioVacinasActivity.class);
                intent.putExtra("petId",mPet.getIdPet());
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
