package projetaobcc20172.com.projetopetemfoco.fragment;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import projetaobcc20172.com.projetopetemfoco.R;


public class ResultadoPagamentoFragment extends Fragment {

    private boolean isSuccess;
    private String message;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_resultado_pagamento, container, false);

        Toolbar toolbar = (Toolbar) view.findViewById(R.id.toolbar);
        toolbar.setTitle("Pagamento");

        Button btBack = (Button) view.findViewById(R.id.bt_back);
        btBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                back();
            }
        });

        ImageView ivIcon = (ImageView) view.findViewById(R.id.iv_icon);
        TextView tvMessage = (TextView) view.findViewById(R.id.tv_message);

        if(isSuccess)
            ivIcon.setImageResource(R.drawable.ic_success);
        else
            ivIcon.setImageResource(R.drawable.ic_fail);
        tvMessage.setText(message);

        return view;
    }

    private void back() {
        getFragmentManager().popBackStack();
        getFragmentManager().popBackStack();
        getFragmentManager().popBackStack();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            message = bundle.getString(ResumoContratacaoFragment.MESSAGE);
            isSuccess = bundle.getBoolean(ResumoContratacaoFragment.IS_SUCCESS);
        }
    }
}
