package projetaobcc20172.com.projetopetemfoco.activity;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
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

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import projetaobcc20172.com.projetopetemfoco.R;
import projetaobcc20172.com.projetopetemfoco.database.services.PetDaoImpl;
import projetaobcc20172.com.projetopetemfoco.excecoes.CampoObrAusenteException;
import projetaobcc20172.com.projetopetemfoco.model.Pet;
import projetaobcc20172.com.projetopetemfoco.utils.Utils;
import projetaobcc20172.com.projetopetemfoco.utils.VerificadorDeObjetos;

/**
 * Activity de cadastro de pets
 */
public class CadastroPetActivity extends AppCompatActivity {

    private Spinner mSpinnerTipo, mSpinnerPorte, mSpinnerIdade;
    private Button mCadastrarPet;
    private EditText mNome, mRaca;
    private RadioGroup mRadioGroup;
    private Button mFoto;
    private Pet mPet;
    public static final int GET_FROM_GALLERY = 1;
    private String mIdUsuarioLogado;
    private Uri imagemSelecionada;
    private byte[] imagemCompressed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_pet);

        Toolbar toolbar;
        toolbar = findViewById(R.id.tb_cadastro_pet);

        // Configura toolbar
        toolbar.setTitle(R.string.tb_cadastro_pet);
        toolbar.setTitleTextColor(Color.WHITE);
        toolbar.setNavigationIcon(R.drawable.ic_action_arrow_left_white);
        setSupportActionBar(toolbar);

        //Preparar o adaptar do Spinner para exibir os tipos dos pets
        mSpinnerTipo = findViewById(R.id.tipoSpinner);
        ArrayAdapter<String> adapter_state = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_dropdown_item, getResources().getStringArray(R.array.tipoPet));
        mSpinnerTipo.setAdapter(adapter_state);

        //Preparar o adaptar do Spinner para exibir os portes dos pets
        mSpinnerPorte = findViewById(R.id.porteSpinner);
        ArrayAdapter<String> adapter_state2 = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_dropdown_item, getResources().getStringArray(R.array.portePet));
        mSpinnerPorte.setAdapter(adapter_state2);

        //Preparar o adaptar do Spinner para exibir as idades dos pets
        mSpinnerIdade = findViewById(R.id.idadeSpinner);
        ArrayAdapter<String> adapter_state3 = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_dropdown_item, getResources().getStringArray(R.array.idadePet));
        mSpinnerIdade.setAdapter(adapter_state3);

        mNome = findViewById(R.id.etCadastroNomePet);
        mRaca = findViewById(R.id.etCadastroRaçaPet);

        mFoto = findViewById(R.id.bFoto);
        mFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult(new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.INTERNAL_CONTENT_URI), GET_FROM_GALLERY);
            }
        });

        mCadastrarPet = findViewById(R.id.botao_cadastrar_pet);
        mCadastrarPet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Chamar o método para salvar o pet no banco
                salvarPet();
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == GET_FROM_GALLERY && resultCode == Activity.RESULT_OK) {
            //Caso o usuário tenha escolhido uma foto do pet, mudar o texto do botão
            mFoto.setText("  Foto Selecionada ");
            imagemSelecionada = data.getData();

            Bitmap bitmap;
            try {
                bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imagemSelecionada);
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                //Converter a imagem do pet para ocupar menos espaço
                bitmap.compress(Bitmap.CompressFormat.JPEG, 30, baos);
                imagemCompressed = baos.toByteArray();

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    private boolean verificarCamposPreenchidos(){
        return (!mNome.getText().toString().isEmpty() ||
                !mRaca.getText().toString().isEmpty());
    }

    //Método que salva o pet no banco
    private boolean salvarPet(){
        try {

            //Recuperar id do usuário logado
            mIdUsuarioLogado = getPreferences("id", CadastroPetActivity.this);

            //Recuperar dados do pet informados pelo usuário
            mRadioGroup = findViewById(R.id.rgGenero);
            //Recupera o texto do item selecionado no gênero do pet
            String itemSelecionado = ((RadioButton) findViewById(mRadioGroup.getCheckedRadioButtonId())).getText().toString();

            mPet = new Pet();
            mPet.setNome(mNome.getText().toString());
            mPet.setTipo(mSpinnerTipo.getSelectedItem().toString());
            mPet.setIdade(mSpinnerIdade.getSelectedItem().toString());
            mPet.setPorte(mSpinnerPorte.getSelectedItem().toString());
            mPet.setRaça(mRaca.getText().toString());
            mPet.setGenero(itemSelecionado);

            VerificadorDeObjetos.vDadosPet(mPet);

            //Chamada do DAO para salvar no banco
            PetDaoImpl petDao =  new PetDaoImpl(this);
            petDao.inserir(mPet, mIdUsuarioLogado, imagemCompressed);

            abrirTelaPets();

        } catch (CampoObrAusenteException e) {
            Utils.mostrarMensagemCurta(getApplicationContext(), getApplicationContext().getString(R.string.erro_cadastro_campos_obrigatorios_Pet));
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
        else CadastroPetActivity.super.onBackPressed();
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
                        CadastroPetActivity.super.onBackPressed();
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

    //Método que recupera o id do usuário logado, para salvar o pet no nó do usuário que o está cadastrando
    public static String getPreferences(String key, Context context) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.getString(key, null);
    }

    private void abrirTelaPets() {
        finish();
    }

}
