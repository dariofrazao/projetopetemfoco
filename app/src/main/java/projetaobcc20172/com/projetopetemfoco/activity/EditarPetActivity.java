package projetaobcc20172.com.projetopetemfoco.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.preference.PreferenceManager;
import android.support.annotation.VisibleForTesting;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import projetaobcc20172.com.projetopetemfoco.R;
import projetaobcc20172.com.projetopetemfoco.database.services.PetDaoImpl;
import projetaobcc20172.com.projetopetemfoco.excecoes.CampoObrAusenteException;
import projetaobcc20172.com.projetopetemfoco.model.Pet;
import projetaobcc20172.com.projetopetemfoco.utils.Utils;
import projetaobcc20172.com.projetopetemfoco.utils.VerificadorDeObjetos;

public class EditarPetActivity extends AppCompatActivity {

    private Spinner mSpinnerTipo, mSpinnerPorte, mSpinnerIdade;
    private Button mEditarPet;
    private EditText mNome, mRaca;
    private RadioGroup mRadioGroup;
    private Pet mPet;
    private String mIdUsuarioLogado;
    private String midPet;

    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    //permite que essa variavel seja vista pela classe de teste
    private Toast mToast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_pet);

        Intent intent = getIntent();

        Toolbar toolbar;
        toolbar = findViewById(R.id.tb_editar_pet);

        // Configura toolbar
        toolbar.setTitle(R.string.tb_editar_pet);
        toolbar.setTitleTextColor(Color.WHITE);
        toolbar.setNavigationIcon(R.drawable.ic_action_arrow_left_white);
        setSupportActionBar(toolbar);

        //Preparar o adaptar do Spinner para exibir os tipos dos pets
        mSpinnerTipo = findViewById(R.id.tipoEditarSpinner);
        ArrayAdapter<String> adapter_state = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_dropdown_item, getResources().getStringArray(R.array.tipoPet));
        mSpinnerTipo.setAdapter(adapter_state);
        String tipo = intent.getStringExtra("tipoPet");
        int posicaoTipo = adapter_state.getPosition(tipo);
        mSpinnerTipo.setSelection(posicaoTipo);

        //Preparar o adaptar do Spinner para exibir os portes dos pets
        mSpinnerPorte = findViewById(R.id.porteEditarSpinner);
        ArrayAdapter<String> adapter_state2 = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_dropdown_item, getResources().getStringArray(R.array.portePet));
        mSpinnerPorte.setAdapter(adapter_state2);
        String porte = intent.getStringExtra("portePet");
        int posicaoPorte = adapter_state2.getPosition(porte);
        mSpinnerPorte.setSelection(posicaoPorte);

        //Preparar o adaptar do Spinner para exibir as idades dos pets
        mSpinnerIdade = findViewById(R.id.idadeEditarSpinner);
        ArrayAdapter<String> adapter_state3 = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_dropdown_item, getResources().getStringArray(R.array.idadePet));
        mSpinnerIdade.setAdapter(adapter_state3);
        String idade = intent.getStringExtra("idadePet");
        int posicaoIdade = adapter_state3.getPosition(idade);
        mSpinnerIdade.setSelection(posicaoIdade);

        mNome = findViewById(R.id.etEditarNomePet);
        mRaca = findViewById(R.id.etEditarRaçaPet);

        midPet = intent.getStringExtra("idPet");
        mNome.setText(intent.getStringExtra("nomePet"));
        mRaca.setText(intent.getStringExtra("raçaPet"));

        String mGenero = intent.getStringExtra("generoPet");
        RadioButton rb1 = findViewById(R.id.rbEditarMacho);
        RadioButton rb2 = findViewById(R.id.rbEditarFemea);
        if(mGenero.equals("Macho"))
            rb1.setChecked(true);
        else
            rb2.setChecked(true);

        mEditarPet = findViewById(R.id.botao_editar_pet);
        mEditarPet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Chamar o método para editar o pet no banco
                editarPet();
            }
        });
    }

    private boolean verificarCamposPreenchidos(){
        return (!mNome.getText().toString().isEmpty() ||
                !mRaca.getText().toString().isEmpty());
    }

    //Método que edita o pet no banco
    private boolean editarPet(){
        try {

            //Recuperar id do usuário logado
            mIdUsuarioLogado = getPreferences("id", EditarPetActivity.this);
            //Recuperar dados do pet informados pelo usuário
            mRadioGroup = findViewById(R.id.rgEditarGenero);
            //Recupera o texto do item selecionado no gênero do pet
            String itemSelecionado = ((RadioButton) findViewById(mRadioGroup.getCheckedRadioButtonId())).getText().toString();

            mPet = new Pet(midPet, mNome.getText().toString(), mSpinnerTipo.getSelectedItem().toString(),
                    mSpinnerIdade.getSelectedItem().toString(),mSpinnerPorte.getSelectedItem().toString(),
                    mRaca.getText().toString(), itemSelecionado);

            VerificadorDeObjetos.vDadosPet(mPet);
            //Chamada do DAO para editar no banco
            PetDaoImpl petDao =  new PetDaoImpl(this);
            petDao.atualizar(mPet, mIdUsuarioLogado);
            abrirTelaPets();

        } catch (CampoObrAusenteException e) {
            mToast = Toast.makeText(EditarPetActivity.this, R.string.erro_atualizacao_campos_obrigatorios_Pet, Toast.LENGTH_SHORT);
            mToast.show();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    //Método do ícone para voltar
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    //Método do botão voltar
    @Override
    public void onBackPressed() {
        if (verificarCamposPreenchidos()) confirmarSaida();
        else EditarPetActivity.super.onBackPressed();
    }

    //Método que exibe pergunta de confirmação ao usuário caso ele clique no botão de voltar com as
    //informações do pet inseridas nos campos
    public void confirmarSaida(){
        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which){
                    case DialogInterface.BUTTON_POSITIVE:
                        // Botão sim foi clicado
                        EditarPetActivity.super.onBackPressed();
                        break;

                    case DialogInterface.BUTTON_NEGATIVE:
                        // Botão não foi clicado
                        break;
                    default:
                        break;
                }
            }
        };

        Utils.mostrarPerguntaSimNao(this, getString(R.string.atencao),
                getString(R.string.pergunta_confirma_dados_serao_perdidos), dialogClickListener,
                dialogClickListener);
    }

    //Método que recupera o id do usuário logado, para editar o pet no nó do usuário que o está cadastrando
    public static String getPreferences(String key, Context context) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.getString(key, null);
    }

    private void abrirTelaPets() {
        //Intent intent = new Intent(CadastroPetActivity.this, MainActivity.class);
        //intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        //startActivity(intent);
        finish();
    }


}
