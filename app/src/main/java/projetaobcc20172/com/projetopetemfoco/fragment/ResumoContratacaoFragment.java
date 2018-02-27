package projetaobcc20172.com.projetopetemfoco.fragment;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.cooltechworks.creditcarddesign.CreditCardUtils;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import br.com.uol.pslibs.checkouttransparent.PSCheckout;
import br.com.uol.pslibs.checkouttransparent.vo.PSCheckoutRequest;
import br.com.uol.pslibs.checkouttransparent.vo.PSCheckoutResponse;
import br.com.uol.pslibs.checkouttransparent.vo.SellerVO;
import projetaobcc20172.com.projetopetemfoco.activity.ContratarServicoActivity;
import projetaobcc20172.com.projetopetemfoco.activity.InfoServicoActivity;
import projetaobcc20172.com.projetopetemfoco.R;
import projetaobcc20172.com.projetopetemfoco.config.ConfiguracaoFirebase;
import projetaobcc20172.com.projetopetemfoco.model.Cupom;

import static projetaobcc20172.com.projetopetemfoco.activity.InfoServicoActivity.imagemServico;


public class ResumoContratacaoFragment extends Fragment{

    //Configuração Seller
    /**
     * Seller Email
     */
    private static final String SELLER_EMAIL = "email do vendedor";

    /**
     * Seller Token
     * Este token deve ser obtido no ibanking do PagSeguro
     * www.pagseguro.com.br
     * -> Mais informações consulte a documentação.
     */
    private static final String SELLER_TOKEN = "token do vendedor";


    public static final String MESSAGE = "MESSAGE";
    public static final String IS_SUCCESS = "IS_SUCCESS";

    private ProgressDialog progress;

    private String cardName;
    private String cardNumber;
    private String cardExpiry;
    private String cardCvv;

    private String servico;
    private String estabelecimento;
    private String tipoPet;
    private String precoUnitario;
    private String idServico;
    private Double totalValue;
    private Boolean usouCupom = false;
    private TextView tvCupom, tvTotal;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

        Bundle bundle = this.getArguments();
        if (bundle != null) {
            cardName = bundle.getString(CreditCardUtils.EXTRA_CARD_HOLDER_NAME);
            cardNumber = bundle.getString(CreditCardUtils.EXTRA_CARD_NUMBER);
            cardExpiry = bundle.getString(CreditCardUtils.EXTRA_CARD_EXPIRY);
            cardCvv = bundle.getString(CreditCardUtils.EXTRA_CARD_CVV);
            servico = bundle.getString(ContratarServicoActivity.SERVICE);
            idServico = bundle.getString(ContratarServicoActivity.ID_SERVICO);
            estabelecimento = bundle.getString(ContratarServicoActivity.ESTABELECIMENTO);
            tipoPet = bundle.getString(ContratarServicoActivity.TIPOPET);
            precoUnitario = bundle.getString(ContratarServicoActivity.TOTAL);
            totalValue = bundle.getDouble(ContratarServicoActivity.TOTAL_VALUE);
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_resumo_contratacao, container, false);
        initComponents(view);
        return view;
    }

    private void initComponents(View view) {
        progress = new ProgressDialog(getActivity());

        Toolbar toolbar = (Toolbar) view.findViewById(R.id.toolbar);
        toolbar.setTitle("Resumo do Pedido");

        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayShowHomeEnabled(true);

        ImageView ivServico = (ImageView) view.findViewById(R.id.iv_photo);
        TextView tvName = (TextView) view.findViewById(R.id.tv_name);
        TextView tvEstabelecimento = (TextView) view.findViewById(R.id.tv_estabelecimento);
        TextView tvTipoPet = (TextView) view.findViewById(R.id.tv_tipoPet);
        tvCupom = (TextView) view.findViewById(R.id.tv_cupom);
        TextView tvPrice = (TextView) view.findViewById(R.id.tv_price);
        tvTotal = (TextView) view.findViewById(R.id.tv_total);
        TextView tvCardName = (TextView) view.findViewById(R.id.tv_card_name);
        TextView tvCardNumber = (TextView) view.findViewById(R.id.tv_card_number);
        TextView tvCardValidity = (TextView) view.findViewById(R.id.tv_card_validity);
        final EditText mCupom = (EditText) view.findViewById(R.id.et_AdicionarCupom);

        ivServico.setImageResource(imagemServico(servico));
        tvName.setText("Serviço: " + servico);
        tvEstabelecimento.setText("Estabelecimento: " + estabelecimento);
        tvTipoPet.setText("Tipo de Animal: " + tipoPet);
        tvPrice.setText(precoUnitario);
        tvTotal.setText(precoUnitario);
        tvCardName.setText(cardName);
        tvCardNumber.setText(formatCardNumber(cardNumber));
        tvCardValidity.setText(cardExpiry);


        Button btConfirm = (Button) view.findViewById(R.id.bt_confirm);
        btConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                configLib();
                pay(cardNumber, cardExpiry, cardCvv);
            }
        });

        Button btCupom = (Button) view.findViewById(R.id.btn_validarCupom);
        btCupom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!(mCupom.getText().toString().isEmpty())){
                    lerCupom(mCupom.getText().toString());
                }


            }
        });

    }

    public void lerCupom(final String mCupom){

        Query query = ConfiguracaoFirebase.getFirebase().child("cupons").orderByChild("idServico").equalTo(idServico);

        if (usouCupom == false){
            query.addListenerForSingleValueEvent(new ValueEventListener() {
                @SuppressLint({"SetTextI18n", "NewApi"})
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    for (DataSnapshot dados : dataSnapshot.getChildren()) {
                        Cupom cupom = new Cupom(dados.child("nome").getValue(String.class), dados.child("valor").getValue(String.class), dados.child("dataInicio").getValue(String.class), dados.child("dataVencimento").getValue(String.class));
                        if (mCupom.equalsIgnoreCase(cupom.getmNome()) && cupomDataValida(cupom.getmDataInicio(), cupom.getmDataVencimento())) {
                            tvCupom.setText(cupom.getmValor());
                            totalValue = totalValue - convertStringForDouble(cupom.getmValor());
                            tvTotal.setText("R$" + convertDoubleForString(totalValue) + "0");
                            usouCupom = true;
                            Toast toast = Toast.makeText(getActivity(), "Desconto aderido com sucesso!!", Toast.LENGTH_SHORT);
                            toast.show();
                        }
                    }
                    if (usouCupom == false){
                        Toast toast = Toast.makeText(getActivity(), "Cupom Inválido!!" , Toast.LENGTH_SHORT);
                        toast.show();
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    //Método com corpo vazio
                }
            });
        }else {
            Toast toast = Toast.makeText(getActivity(), "Cupom já utilizado nesta venda!!" , Toast.LENGTH_SHORT);
            toast.show();
        }
    }

    public String convertDoubleForString(Double valor){
        String preco = String.valueOf(valor);
        return preco.replace(".", ",");
    }

    public Double convertStringForDouble(String valor){
        String preco  = valor.substring(2);
        preco = preco.replaceAll(",", ".");
        return Double.parseDouble(preco);
    }

    @SuppressLint("DefaultLocale")
    @RequiresApi(api = Build.VERSION_CODES.N)
    public boolean cupomDataValida(String dataInicio, String dataVencimento){
        String dataAtual;
        Calendar c = Calendar.getInstance();
        int mYear = c.get(Calendar.YEAR); // current year
        int mMonth = c.get(Calendar.MONTH); // current month
        int mDay = c.get(Calendar.DAY_OF_MONTH); // current day
        dataAtual = (String.format("%d/%d/%d", mDay, mMonth + 1, mYear));

        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        try {
            Date data1 = new Date(format.parse(dataAtual).getTime());
            Date data2 = new Date(format.parse(dataInicio).getTime());
            Date data3 = new Date(format.parse(dataVencimento).getTime());

            if(data1.equals(data2) || data1.equals(data3) || (data1.after(data2) && data1.before(data3))){
                return true;
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return false;
    }


    private void configLib() {
        SellerVO sellerVO = new SellerVO(SELLER_EMAIL, SELLER_TOKEN);
        PSCheckout.init(getActivity(), sellerVO);
    }

    private void pay(String number, String expiry, String cvv) {

        PSCheckoutRequest psCheckoutRequest = new PSCheckoutRequest();

        //NUMERO DO CARTAO
        psCheckoutRequest.setCreditCard(number);
        //CVV DO CARTAO
        psCheckoutRequest.setCvv(cvv);
        //MÊS DE EXPIRACAO (Ex: 03)
        psCheckoutRequest.setExpMonth(expiry.substring(0,2));
        //ANO DE EXPIRACAO, ULTIMOS 2 DIGITOS (Ex: 17)
        psCheckoutRequest.setExpYear(expiry.substring(3,5));
        //VALOR DA TRANSACAO
        psCheckoutRequest.setAmountPayment(totalValue);
        //DESCRICAO DO PRODUTO/SERVICO
        psCheckoutRequest.setDescriptionPayment(servico);

        assert (AppCompatActivity) getActivity() != null;
        PSCheckout.pay(psCheckoutRequest, psCheckoutListener, (AppCompatActivity) getActivity());
    }

    private PSCheckout.PSCheckoutListener psCheckoutListener = new PSCheckout.PSCheckoutListener() {
        @Override
        public void onSuccess(PSCheckoutResponse responseVO) {
            progress.dismiss();
            callResult(true, responseVO.getMessage());
        }

        @Override
        public void onFailure(PSCheckoutResponse responseVO) {
            progress.dismiss();
            callResult(false, responseVO.getMessage());
        }

        @Override
        public void onProcessing() {
            progress.setMessage("Realizando pagamento...");
            progress.setIndeterminate(true);
            progress.setCancelable(false);
            progress.show();
        }
    };

    private void callResult(boolean isSuccess, String message){
        FragmentManager fragmentManager = getFragmentManager();
        assert fragmentManager != null;
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        ResultadoPagamentoFragment resultadoPagamentoFragment = new ResultadoPagamentoFragment();
        Bundle bundle = new Bundle();
        bundle.putString(ResumoContratacaoFragment.MESSAGE, message);
        bundle.putBoolean(ResumoContratacaoFragment.IS_SUCCESS, isSuccess);
        resultadoPagamentoFragment.setArguments(bundle);
        fragmentTransaction.replace(R.id.container_id, resultadoPagamentoFragment).addToBackStack(null).commit();
    }

    private String formatCardNumber(String cardNumber) {
        if(cardNumber.startsWith("3"))
            return "****  ******  "+cardNumber.substring(10);
        else
            return "****  ****  ****  "+cardNumber.substring(12);
    }
}
