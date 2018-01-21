package projetaobcc20172.com.projetopetemfoco.activity;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.preference.PreferenceManager;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import java.util.Calendar;

import projetaobcc20172.com.projetopetemfoco.R;
import projetaobcc20172.com.projetopetemfoco.database.services.VacinaDao;
import projetaobcc20172.com.projetopetemfoco.database.services.VacinaDaoImpl;
import projetaobcc20172.com.projetopetemfoco.excecoes.CampoObrAusenteException;
import projetaobcc20172.com.projetopetemfoco.model.Vacina;
import projetaobcc20172.com.projetopetemfoco.utils.Utils;
import projetaobcc20172.com.projetopetemfoco.utils.VerificadorDeObjetos;

public class CadastroVacinaActivity extends AppCompatActivity {

    private EditText mData;
    private EditText mDescricao;
    private Button mCadastrarVacina;
    private Toolbar toolbar;
    private DatePickerDialog mDatePickerDialog;
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

        toolbar = findViewById(R.id.tb_main);

        // Configura toolbar
        toolbar.setTitle(R.string.tb_cadastro_vacina);
        toolbar.setTitleTextColor(Color.WHITE);
        toolbar.setNavigationIcon(R.drawable.ic_action_arrow_left_white);
        setSupportActionBar(toolbar);

        final String petId = getIntent().getStringExtra("idPet");

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
                    try {
                        Vacina vacina = (Vacina) getIntent().getSerializableExtra("vacina");
                        vacina.setmDescricao(mDescricao.getText().toString());
                        vacina.setmData(mData.getText().toString());
                        VerificadorDeObjetos.vDadosVacina(vacina);
                        vacinaDao.atualizar(vacina, idUsuarioLogado, petId);

                        abrirCalendario();

                    }catch (CampoObrAusenteException e){
                        Utils.mostrarMensagemCurta(getApplicationContext(), "Erro ao editar vacina: Preencha o campo descrição");
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }else{
                    try {
                        Vacina vacina = new Vacina();
                        vacina.setmDescricao(mDescricao.getText().toString());
                        vacina.setmData(mData.getText().toString());
                        VerificadorDeObjetos.vDadosVacina(vacina);
                        vacinaDao.inserir(vacina, idUsuarioLogado, petId);

                        abrirCalendario();

                    }catch (CampoObrAusenteException e){
                        Utils.mostrarMensagemCurta(getApplicationContext(), "Erro ao cadastrar vacina: Preencha o campo descrição");
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    private void verificarEdição(){
        mEditavel = getIntent().getBooleanExtra("editar",false);
        if(mEditavel){
            Vacina vacina = (Vacina) getIntent().getSerializableExtra("vacina");
            toolbar.setTitle(R.string.tb_editar_vacina);
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

    public void confirmarSaida(){
        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which){
                    case DialogInterface.BUTTON_POSITIVE:
                        // Botão sim foi clicado
                        CadastroVacinaActivity.super.onBackPressed();
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

    @Override
    public void onBackPressed() {
        if (verificarCamposPreenchidos()) confirmarSaida();
        else CadastroVacinaActivity.super.onBackPressed();
    }

    private boolean verificarCamposPreenchidos(){
        return (!mDescricao.getText().toString().isEmpty() ||
                !mData.getText().toString().isEmpty());
    }

    private void abrirCalendario(){
        finish();
    }
}
