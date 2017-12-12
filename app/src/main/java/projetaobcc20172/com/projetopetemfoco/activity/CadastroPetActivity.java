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

/**
 * Activity de cadastro de pets
 */
public class CadastroPetActivity extends AppCompatActivity {

    private Spinner mSpinnerTipo, mSpinnerPorte, mSpinnerIdade;
    private Button mCadastrarPet;
    private EditText mNome, mRaca;
    private RadioGroup mRadioGroup;
    private Pet mPet;
    private String mIdUsuarioLogado;

    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    //permite que essa variavel seja vista pela classe de teste
    private Toast mToast;

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

        mCadastrarPet = findViewById(R.id.botao_cadastrar_pet);
        mCadastrarPet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Chamar o método para salvar o pet no banco
                salvarPet();
            }
        });
    }

    private boolean verificarCamposPreenchidos(){
        return (!mNome.getText().toString().isEmpty() ||
                !mRaca.getText().toString().isEmpty());
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

    //Método que salva o pet no banco
    private boolean salvarPet(){

        //Recuperar id do usuário logado
        mIdUsuarioLogado = getPreferences("id", CadastroPetActivity.this);

        //Recuperar dados do pet informados pelo usuário
        mRadioGroup = findViewById(R.id.rgGenero);
        //Recupera o texto do item selecionado no gênero do pet
        String itemSelecionado = ((RadioButton) findViewById(mRadioGroup.getCheckedRadioButtonId())).getText().toString();
        mPet = new Pet(mNome.getText().toString(), mSpinnerTipo.getSelectedItem().toString(),
                mSpinnerIdade.getSelectedItem().toString(),mSpinnerPorte.getSelectedItem().toString(),
                mRaca.getText().toString(), itemSelecionado);

        try {
            VerificadorDeObjetos.vDadosPet(mPet);
            PetDaoImpl petDao =  new PetDaoImpl(this);
            //Chamada do DAO para salvar no banco
            petDao.inserir(mPet, mIdUsuarioLogado);
            mToast = Toast.makeText(CadastroPetActivity.this, R.string.sucesso_cadastro_Pet, Toast.LENGTH_SHORT);
            mToast.show();
            abrirTelaPrincipal();

        } catch (CampoObrAusenteException e) {
            mToast = Toast.makeText(CadastroPetActivity.this, R.string.erro_cadastro_campos_obrigatorios_Pet, Toast.LENGTH_SHORT);
            mToast.show();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;



    }

    private void abrirTelaPrincipal() {
        Intent intent = new Intent(CadastroPetActivity.this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
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

}
