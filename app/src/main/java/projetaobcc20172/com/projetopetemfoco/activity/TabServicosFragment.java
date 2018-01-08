package projetaobcc20172.com.projetopetemfoco.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;

import java.util.ArrayList;

import projetaobcc20172.com.projetopetemfoco.R;
import projetaobcc20172.com.projetopetemfoco.adapter.TiposServicoAdapterGrid;
import projetaobcc20172.com.projetopetemfoco.utils.Utils;

/**
 * Created by raul1 on 05/01/2018.
 */

public class TabServicosFragment extends Fragment {
    private TiposServicoAdapterGrid mServAdpGrid;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //returning our layout file
        //change R.layout.yourlayoutfilename for each of your fragments
        return inflater.inflate(R.layout.tab_servico_fragment, container, false);
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //you can set the title for your toolbar here for different fragments different titles
        //getActivity().setTitle("Busca por serviço");
        final GridView gridView = getActivity().findViewById(R.id.gridServicos);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                TextView servicoClicado =  view.findViewById(R.id.gridTextServico);
                irParaListaEstabelecimentos(servicoClicado.getText().toString(),TabPetOpcoesFragment.getOpcaosSelecionada());
            }
        });
        ArrayList<String> tiposServico =  Utils.recuperaArrayR(getActivity(),R.array.servicos);
        mServAdpGrid = new TiposServicoAdapterGrid(getActivity(),tiposServico);
        gridView.setAdapter(mServAdpGrid);
        mServAdpGrid.notifyDataSetChanged();
    }

    private void irParaListaEstabelecimentos(String servico,ArrayList<String> tipoPet){
        Intent intent = new Intent(getActivity(), ListaEstabServicoActivity.class);
        intent.putExtra("servico",servico);
        intent.putStringArrayListExtra("pets",tipoPet);
        startActivity(intent);
    }


}
