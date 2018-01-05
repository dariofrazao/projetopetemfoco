package projetaobcc20172.com.projetopetemfoco.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import java.util.ArrayList;

import projetaobcc20172.com.projetopetemfoco.R;
import projetaobcc20172.com.projetopetemfoco.adapter.OpPetGridAdapter;
import projetaobcc20172.com.projetopetemfoco.adapter.ServicoAdapterGrid;
import projetaobcc20172.com.projetopetemfoco.utils.Utils;

/**
 * Created by raul1 on 05/01/2018.
 */

public class TabPetOpcoesFragment extends Fragment {
    private OpPetGridAdapter mPetAdapter;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.tab_opcoes_pet_fragment, container, false);
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        GridView gridView = getActivity().findViewById(R.id.gridOpPet);
        ArrayList<String> tiposServico =  Utils.recuperaArrayR(getActivity(),R.array.tiposPetBusca);
        mPetAdapter = new OpPetGridAdapter(getActivity(),tiposServico);
        gridView.setAdapter(mPetAdapter);
        mPetAdapter.notifyDataSetChanged();
    }

}
