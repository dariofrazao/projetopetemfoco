package projetaobcc20172.com.projetopetemfoco.activity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.preference.PreferenceManager;
import android.support.annotation.RequiresApi;
import android.support.annotation.VisibleForTesting;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Calendar;

import projetaobcc20172.com.projetopetemfoco.R;
import projetaobcc20172.com.projetopetemfoco.database.services.VacinaDao;
import projetaobcc20172.com.projetopetemfoco.database.services.VacinaDaoImpl;
import projetaobcc20172.com.projetopetemfoco.model.Vacina;

public class CadastroVacinaActivity extends AppCompatActivity {

    EditText mData;
    EditText mDescricao;
    Button mCadastrarVacina;
    DatePickerDialog mDatePickerDialog;
    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE) //permite que essa variavel seja vista pela classe de teste
    private Toast mToast;
    private boolean mEditavel;


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_vacina);

        mDescricao = (EditText) findViewById(R.id.etDescricao);
        mData = (EditText) findViewById(R.id.etData);
        mData.setShowSoftInputOnFocus(false);
        mData.setInputType(InputType.TYPE_NULL);
        mData.setFocusable(false);

        mCadastrarVacina = (Button) findViewById(R.id.btnCadastroVacina);

        Toolbar toolbar;
        toolbar = findViewById(R.id.tb_main);

        // Configura toolbar
        toolbar.setTitle("Cadatro de vacinas");
        toolbar.setTitleTextColor(Color.WHITE);
        toolbar.setNavigationIcon(R.drawable.ic_action_arrow_left_white);
        setSupportActionBar(toolbar);


        final String petId = getIntent().getStringExtra("petId");
        //funcao responsavel por preencher o campo de mData com a mData selecionada do datapicker
        getDateFromActivityListener();

        verificarEdição();

        final String idUsuarioLogado;
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(CadastroVacinaActivity.this);
        idUsuarioLogado = preferences.getString("id", "");

        mCadastrarVacina.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                VacinaDao vacinaDao = new VacinaDaoImpl(CadastroVacinaActivity.this);

                if(mEditavel){
                    Vacina vacina = (Vacina) getIntent().getSerializableExtra("vacina");
                    vacina.setmDescricao(mDescricao.getText().toString());
                    vacina.setmData(mData.getText().toString());

                    vacinaDao.atualizar(vacina,idUsuarioLogado,petId);

                }else{
                    Vacina vacina = new Vacina();
                    vacina.setmDescricao(mDescricao.getText().toString());
                    vacina.setmData(mData.getText().toString());
                    vacinaDao.inserir(vacina,idUsuarioLogado,petId);
                }

                mToast = mToast.makeText(CadastroVacinaActivity.this, "Cadastro de vacina realizado com sucesso", Toast.LENGTH_LONG);
                mToast.show();

                abrirCalendario();
            }
        });
    }

    private void verificarEdição(){
        mEditavel = getIntent().getBooleanExtra("editar",false);
        if(mEditavel){
            Vacina vacina = (Vacina) getIntent().getSerializableExtra("vacina");
            mDescricao.setText(vacina.getmDescricao());
            mData.setText(vacina.getmData());
            mCadastrarVacina.setText("Editar");
        }
    }

    private void getDateFromActivityListener(){
        final Calendar c = Calendar.getInstance();
        final int mYear = c.get(Calendar.YEAR); // current year
        final int mMonth = c.get(Calendar.MONTH); // current month
        final int mDay = c.get(Calendar.DAY_OF_MONTH); // current day

        //inicia o campo com a mData atual
        mData.setText(String.format("%d/%d/%d", mDay, mMonth + 1, mYear));

        mData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDatePickerDialog = new DatePickerDialog(CadastroVacinaActivity.this,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {

                                // set day of month , month and year value in the edit text
                                mData.setText(String.format("%d/%d/%d", dayOfMonth, monthOfYear + 1, year));
                            }
                        }, mYear, mMonth, mDay);

                mDatePickerDialog.show();
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    private void abrirCalendario(){
        finish();
    }
}
