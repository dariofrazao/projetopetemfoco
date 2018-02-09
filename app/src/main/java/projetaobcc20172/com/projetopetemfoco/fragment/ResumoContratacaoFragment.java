package projetaobcc20172.com.projetopetemfoco.fragment;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.cooltechworks.creditcarddesign.CreditCardUtils;

import br.com.uol.pslibs.checkouttransparent.PSCheckout;
import br.com.uol.pslibs.checkouttransparent.vo.PSCheckoutRequest;
import br.com.uol.pslibs.checkouttransparent.vo.PSCheckoutResponse;
import br.com.uol.pslibs.checkouttransparent.vo.SellerVO;
import projetaobcc20172.com.projetopetemfoco.activity.InfoServicoActivity;
import projetaobcc20172.com.projetopetemfoco.R;

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

    //private Servico servico;
    private String servico;
    private String estabelecimento;
    private String tipoPet;
    private String amount;
    private String precoUnitario;
    private Double totalValue;

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
            servico = bundle.getString(InfoServicoActivity.SERVICE);
            estabelecimento = bundle.getString(InfoServicoActivity.ESTABELECIMENTO);
            tipoPet = bundle.getString(InfoServicoActivity.TIPOPET);
            precoUnitario = bundle.getString(InfoServicoActivity.TOTAL);
            amount = bundle.getString(InfoServicoActivity.AMOUNT);
            totalValue = bundle.getDouble(InfoServicoActivity.TOTAL_VALUE);
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

        assert ((InfoServicoActivity)getActivity()) != null;
        ((InfoServicoActivity)getActivity()).setSupportActionBar(toolbar);
        ((InfoServicoActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ((InfoServicoActivity) getActivity()).getSupportActionBar().setDisplayShowHomeEnabled(true);

        Button btConfirm = (Button)view.findViewById(R.id.bt_confirm);
        btConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                configLib();
                pay(cardNumber, cardExpiry, cardCvv);
            }
        });

        ImageView ivServico = (ImageView) view.findViewById(R.id.iv_photo);
        TextView tvName = (TextView) view.findViewById(R.id.tv_name);
        TextView tvEstabelecimento = (TextView) view.findViewById(R.id.tv_estabelecimento);
        TextView tvTipoPet = (TextView) view.findViewById(R.id.tv_tipoPet);
        //TextView tvDescription = (TextView) view.findViewById(R.id.tv_description);
        TextView tvAmount = (TextView) view.findViewById(R.id.tv_amount);
        TextView tvPrice = (TextView) view.findViewById(R.id.tv_price);
        TextView tvTotal = (TextView) view.findViewById(R.id.tv_total);
        TextView tvCardName = (TextView) view.findViewById(R.id.tv_card_name);
        TextView tvCardNumber = (TextView) view.findViewById(R.id.tv_card_number);
        TextView tvCardValidity = (TextView) view.findViewById(R.id.tv_card_validity);

        ivServico.setImageResource(imagemServico(servico));
        tvName.setText("Serviço: " + servico);
        //tvDescription.setText(servico.getDescricao());
        tvEstabelecimento.setText("Estabelecimento: " + estabelecimento);
        tvTipoPet.setText("Tipo de Animal: " + tipoPet);
        tvAmount.setText(amount);
        tvPrice.setText(precoUnitario);
        tvTotal.setText(String.valueOf(totalValue));
        tvCardName.setText(cardName);
        tvCardNumber.setText(formatCardNumber(cardNumber));
        tvCardValidity.setText(cardExpiry);
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
