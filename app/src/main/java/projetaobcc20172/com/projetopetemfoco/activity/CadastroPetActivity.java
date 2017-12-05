package projetaobcc20172.com.projetopetemfoco.activity;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Toast;

import projetaobcc20172.com.projetopetemfoco.R;

public class CadastroPetActivity extends AppCompatActivity implements OnItemSelectedListener{

    private Spinner tipoSpinner, porteSpinner;
    private String[] stateTipo = {"Cão", "Gato", "Pássaro", "Peixe"}; //Array com os tipos de animais
    private String[] statePorte = {"Pequeno", "Médio", "Grande", "Gigante"}; //Array com os portes dos animais

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_pet);

        Toolbar toolbar;
        toolbar = (Toolbar) findViewById(R.id.tb_cadastro_pet);

        // Configura toolbar
        toolbar.setTitle("Cadastro de Pet");
        toolbar.setTitleTextColor(Color.WHITE);
        toolbar.setNavigationIcon(R.drawable.ic_action_arrow_left_white);
        setSupportActionBar(toolbar);

        //Preparar o adaptar do Spinner para exibir os tipos de animais
        tipoSpinner = findViewById(R.id.tipoSpinner);
        ArrayAdapter<String> adapter_state = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, stateTipo);
        adapter_state.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        tipoSpinner.setAdapter(adapter_state);
        tipoSpinner.setOnItemSelectedListener(this);

        //Preparar o adaptar do Spinner para exibir os portes dos animais
        porteSpinner = findViewById(R.id.porteSpinner);
        ArrayAdapter<String> adapter_state2 = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, statePorte);
        adapter_state2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        porteSpinner.setAdapter(adapter_state2);
        porteSpinner.setOnItemSelectedListener(this);
    }

    //Método do ícone para voltar
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    //Método para pegar o item selecionado nos Spinners
    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        tipoSpinner.getItemAtPosition(i);
        porteSpinner.getItemAtPosition(i);
    }

    //Método que exibe mensagem se um item do Spinner não for exibido
    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
        Toast.makeText(this, "Nada foi selecionado no tipo ou porte do pet", Toast.LENGTH_SHORT).show();
    }
}
