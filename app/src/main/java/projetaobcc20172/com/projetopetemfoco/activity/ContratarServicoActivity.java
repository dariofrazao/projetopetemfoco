package projetaobcc20172.com.projetopetemfoco.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ProgressBar;

import com.cooltechworks.creditcarddesign.CardEditActivity;
import com.cooltechworks.creditcarddesign.CreditCardUtils;

import java.util.Calendar;

import projetaobcc20172.com.projetopetemfoco.R;
import projetaobcc20172.com.projetopetemfoco.fragment.ResumoContratacaoFragment;
import projetaobcc20172.com.projetopetemfoco.model.Fornecedor;
import projetaobcc20172.com.projetopetemfoco.utils.Utils;

import static android.support.v4.content.PermissionChecker.PERMISSION_GRANTED;

public class ContratarServicoActivity extends AppCompatActivity {

    private WebView mWebView;
    private ProgressBar mProgressBar;
    private Button mAgendar, mContratarServico, mVoltar;
    private Fornecedor mFornecedor;
    private String[] mServico;

    final int GET_NEW_CARD = 2;
    public static final String SERVICE = "SERVICE";
    public static final String ESTABELECIMENTO = "ESTABELECIMENTO";
    public static final String TIPOPET = "TIPOPET";
    public static final String AMOUNT = "AMOUNT";
    public static final String TOTAL = "TOTAL";
    public static final String TOTAL_VALUE = "TOTAL_VALUE";
    public static final String ID_SERVICO = "ID_SERVICO";


    private double total;
    private String preco;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contratar_servico);

        mFornecedor = (Fornecedor) getIntent().getSerializableExtra("Fornecedor");
        mServico = (String[]) getIntent().getSerializableExtra("Servico");

        Toolbar toolbar;
        toolbar = findViewById(R.id.tb_main);

        // Configura toolbar
        toolbar.setTitle(R.string.tb_agenda_estabelecimento);
        toolbar.setTitleTextColor(Color.WHITE);
        toolbar.setNavigationIcon(R.drawable.ic_action_arrow_left_white);
        setSupportActionBar(toolbar);

        mProgressBar = findViewById(R.id.progressBar1);

        String email = mFornecedor.getEmail();
        String emailSplit = email.replace("@gmail.com", "%40gmail.com");

        if (mServico != null) {
            preco = mServico[2];
            preco = preco.substring(2);
            preco = preco.replaceAll(",", ".");
            total = Double.parseDouble(preco);
        }

        mWebView = findViewById(R.id.activity_main_webview);
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.setWebViewClient(new HelloWebViewClient());
        mWebView.loadUrl("https://calendar.google.com/calendar/embed?src="+emailSplit+"&ctz=America%2FRecife");
        botaoAgendar();
        botaoContratarServico();
        botaoVoltar();

    }

    public void botaoVoltar(){
        mVoltar = findViewById(R.id.btnBack);
        mVoltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ContratarServicoActivity.this.onBackPressed();
            }
        });
    }

    //chama botao contratar servico
    public void botaoContratarServico(){
        mContratarServico = findViewById(R.id.btnContratarServico);
        mContratarServico.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ContratarServicoActivity.this, CardEditActivity.class);
                startActivityForResult(intent,GET_NEW_CARD);
            }
        });
    }

    //chama botao agendar
    public void botaoAgendar(){
        mAgendar = findViewById(R.id.btnAgendar);
        mAgendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean checar = verificarAppInstalado("com.google.android.calendar", getApplicationContext().getPackageManager());

                if(checar) {
                    Calendar cal = Calendar.getInstance();
                    Intent intent = new Intent(Intent.ACTION_INSERT)
                            .setData(CalendarContract.Events.CONTENT_URI)
                            .putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, cal.getTimeInMillis())
                            //.putExtra(CalendarContract.EXTRA_EVENT_END_TIME, cal.getTimeInMillis()+1000*60)
                            .putExtra(CalendarContract.Events.TITLE, mServico[0] + " (" + mServico[3] + ")")
                            .putExtra(CalendarContract.Events.DESCRIPTION, mServico[1])
                            .putExtra(CalendarContract.Events.AVAILABILITY, CalendarContract.Events.AVAILABILITY_BUSY)
                            .putExtra(Intent.EXTRA_EMAIL, mFornecedor.getEmail());
                    startActivity(intent);
                    Utils.mostrarMensagemLonga(getApplicationContext(), getApplicationContext().getString(R.string.selecione_dia_horario));
                }
                else{
                    Intent marketIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=com.google.android.calendar&hl=pt"));
                    marketIntent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY | Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
                    startActivity(marketIntent);
                }

            }
        });
    }

    //auxilia no recebimento dos dados de cartão de credito e da compra
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if(resultCode == Activity.RESULT_OK) {
            Bundle bundle = new Bundle();
            bundle.putString(CreditCardUtils.EXTRA_CARD_HOLDER_NAME, data.getStringExtra(CreditCardUtils.EXTRA_CARD_HOLDER_NAME));
            bundle.putString(CreditCardUtils.EXTRA_CARD_NUMBER, data.getStringExtra(CreditCardUtils.EXTRA_CARD_NUMBER));
            bundle.putString(CreditCardUtils.EXTRA_CARD_EXPIRY, data.getStringExtra(CreditCardUtils.EXTRA_CARD_EXPIRY));
            bundle.putString(CreditCardUtils.EXTRA_CARD_CVV, data.getStringExtra(CreditCardUtils.EXTRA_CARD_CVV));
            bundle.putString(SERVICE, mServico[0]);
            bundle.putString(ESTABELECIMENTO, mServico[1]);
            bundle.putString(TIPOPET, mServico[3]);
            bundle.putString(ID_SERVICO, mServico[9]);
            bundle.putString(AMOUNT, "1");
            bundle.putString(TOTAL, mServico[2]);
            bundle.putDouble(TOTAL_VALUE, total);

            ResumoContratacaoFragment resumoContratacaoFragment = new ResumoContratacaoFragment();
            resumoContratacaoFragment.setArguments(bundle);
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.container_id, resumoContratacaoFragment);
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commitAllowingStateLoss();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void checkPermissions(int callbackId, String... permissionsId) {
        boolean permissions = true;
        for (String p : permissionsId) {
            permissions = permissions && ContextCompat.checkSelfPermission(this, p) == PERMISSION_GRANTED;
        }

        if (!permissions)
            ActivityCompat.requestPermissions(this, permissionsId, callbackId);
    }

    private class HelloWebViewClient extends WebViewClient{


        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            // TODO Auto-generated method stub
            super.onPageStarted(view, url, favicon);
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView webView, String url)
        {
            webView.loadUrl(url);
            return true;
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            // TODO Auto-generated method stub
            super.onPageFinished(view, url);

            mProgressBar.setVisibility(view.GONE);

            if (mServico == null){
                mVoltar.setVisibility(View.VISIBLE);
            }else {
                mAgendar.setVisibility(View.VISIBLE);
                mContratarServico.setVisibility(View.VISIBLE);
            }

        }

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)
    { //if back key is pressed
        if((keyCode == KeyEvent.KEYCODE_BACK)&& mWebView.canGoBack())
        {
            mWebView.goBack();
            return true;

        }

        return super.onKeyDown(keyCode, event);

    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    public static boolean verificarAppInstalado(String packageName, PackageManager packageManager) {
        try {
            return packageManager.getApplicationInfo(packageName, 0).enabled;
        }
        catch (PackageManager.NameNotFoundException e) {
            return false;
        }
    }

    @Override
    protected void onRestart() {
        mProgressBar.setVisibility(View.VISIBLE);
        mWebView.reload();
        super.onRestart();
    }

}
