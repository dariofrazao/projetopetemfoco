package projetaobcc20172.com.projetopetemfoco.activity;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.provider.CalendarContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ProgressBar;

import java.util.Calendar;

import projetaobcc20172.com.projetopetemfoco.R;
import projetaobcc20172.com.projetopetemfoco.model.Fornecedor;
import projetaobcc20172.com.projetopetemfoco.utils.Utils;

public class ContratarServicoActivity extends AppCompatActivity {

    private WebView mWebView;
    private ProgressBar mProgressBar;
    private Button mAgendar;
    private Fornecedor mFornecedor;
    private String[] mServico;

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

        mWebView = findViewById(R.id.activity_main_webview);
        mWebView.getSettings().setJavaScriptEnabled(true);


        mWebView.setWebViewClient(new HelloWebViewClient());

        mWebView.loadUrl("https://calendar.google.com/calendar/embed?src="+emailSplit+"&ctz=America%2FRecife");

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
            mAgendar.setVisibility(View.VISIBLE);

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
