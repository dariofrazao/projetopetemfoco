package projetaobcc20172.com.projetopetemfoco.activity;

import android.content.Context;
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

import com.google.firebase.database.DatabaseReference;

import projetaobcc20172.com.projetopetemfoco.R;
import projetaobcc20172.com.projetopetemfoco.config.ConfiguracaoFirebase;
import projetaobcc20172.com.projetopetemfoco.excecoes.CampoObrAusenteException;
import projetaobcc20172.com.projetopetemfoco.model.Pet;
import projetaobcc20172.com.projetopetemfoco.utils.VerificadorDeObjetos;

public class CadastroPetActivity extends AppCompatActivity {

    private Spinner mSpinnerTipo, mSpinnerPorte, mSpinnerIdade;
    private String[] mStateIdade = {"Menos de 1 ano", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11",
            "12", "13", "14", "15", "16", "17", "18"}; //Array com as idades dos animais
    private String[] mStateTipo = {"Cão", "Gato", "Pássaro", "Peixe"}; //Array com os tipos de animais
    private String[] mStatePorte = {"Pequeno", "Médio", "Grande", "Gigante"}; //Array com os portes dos animais
    private Button mCadastrarPet;
    private EditText mNome, mRaca;
    private RadioGroup mRadioGroup;
    private Pet mPet;
    private DatabaseReference mFirebase;
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
        ArrayAdapter<String> adapter_state = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, mStateTipo);
        adapter_state.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpinnerTipo.setAdapter(adapter_state);

        //Preparar o adaptar do Spinner para exibir os portes dos pets
        mSpinnerPorte = findViewById(R.id.porteSpinner);
        ArrayAdapter<String> adapter_state2 = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, mStatePorte);
        adapter_state2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpinnerPorte.setAdapter(adapter_state2);

        //Preparar o adaptar do Spinner para exibir as idades dos pets
        mSpinnerIdade = findViewById(R.id.idadeSpinner);
        ArrayAdapter<String> adapter_state3 = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, mStateIdade);
        adapter_state3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpinnerIdade.setAdapter(adapter_state3);

        mNome = findViewById(R.id.editText_nome_pet);
        mRaca = findViewById(R.id.editText_raca_pet);

        mCadastrarPet = findViewById(R.id.botao_cadastrar_pet);
        mCadastrarPet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Recuperar id do usuário logado
                mIdUsuarioLogado = getPreferences("id", CadastroPetActivity.this);

                //Recuperar demais dados do mPet informados pelo usuário
                mPet = new Pet();
                mPet.setNome(mNome.getText().toString());
                mPet.setIdade(mSpinnerIdade.getSelectedItem().toString());
                mPet.setTipo(mSpinnerTipo.getSelectedItem().toString());
                mRadioGroup = findViewById(R.id.genero_radio_group);
                //Recupera o texto do item selecionado no gênero do mPet
                String itemSelecionado = ((RadioButton) findViewById(mRadioGroup.getCheckedRadioButtonId())).getText().toString();
                mPet.setGenero(itemSelecionado);
                mPet.setRaça(mRaca.getText().toString());
                mPet.setPorte(mSpinnerPorte.getSelectedItem().toString());
                //Chamar o método para salvar o mPet no banco
                salvarPet(mIdUsuarioLogado, mPet);

            }
        });
    }

    //Método do ícone para voltar
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }


    //Método que salva o mPet no banco
    private boolean salvarPet(String idRemetente, Pet pet) {
        try {
            VerificadorDeObjetos.vDadosPet(pet);
            mFirebase = ConfiguracaoFirebase.getFirebase().child("usuarios");
            mFirebase.child(idRemetente).child("pets").push().setValue(pet); //O método push() cria uma chave exclusiva para cada mPet cadastrado
            Toast.makeText(CadastroPetActivity.this, R.string.sucesso_cadastro_Pet, Toast.LENGTH_SHORT).show();
            abrirTelaPrincipal();

        } catch (CampoObrAusenteException e) {
            mToast = mToast.makeText(CadastroPetActivity.this, R.string.erro_cadastro_campos_obrigatorios_Pet, Toast.LENGTH_SHORT);
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

    //Método que recupera o id do usuário logado, para salvar o mPet no nó do usuário que o está cadastrando
    public static String getPreferences(String key, Context context) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.getString(key, null);
    }

}
