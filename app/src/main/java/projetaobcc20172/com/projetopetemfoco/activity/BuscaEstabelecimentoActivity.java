package projetaobcc20172.com.projetopetemfoco.activity;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

import projetaobcc20172.com.projetopetemfoco.R;
import projetaobcc20172.com.projetopetemfoco.adapter.EstabelecimentoAdapter;
import projetaobcc20172.com.projetopetemfoco.model.Fornecedor;

/**
 * Created by raul1 on 03/01/2018.
 */

public class BuscaEstabelecimentoActivity extends Fragment {
    private ArrayList<Fornecedor> mForncedores;
    private ArrayAdapter<Fornecedor> mAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //returning our layout file
        //change R.layout.yourlayoutfilename for each of your fragments
        return inflater.inflate(R.layout.activity_busca_estabelecimento, container, false);
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //you can set the title for your toolbar here for different fragments different titles
        getActivity().setTitle("Busca por nome");
        ListView listView = (ListView) getView().findViewById(R.id.lvBuscaEsta);
        // Monta listview e mAdapter
        mForncedores = new ArrayList<>();
        mAdapter = new EstabelecimentoAdapter(getActivity(), mForncedores);
        listView.setAdapter(mAdapter);
    }


}
