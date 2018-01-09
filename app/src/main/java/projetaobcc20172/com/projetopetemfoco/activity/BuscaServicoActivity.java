package projetaobcc20172.com.projetopetemfoco.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import projetaobcc20172.com.projetopetemfoco.R;
import projetaobcc20172.com.projetopetemfoco.adapter.SectionsPageAdapter;


/**
 * Created by raul1 on 03/01/2018.
 */

public class BuscaServicoActivity extends Fragment{

    private ViewPager mViewPager;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //returning our layout file
        //change R.layout.yourlayoutfilename for each of your fragments
        return inflater.inflate(R.layout.activity_busca_servicos, container, false);
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //you can set the title for your toolbar here for different fragments different titles
        getActivity().setTitle("Busca por serviço");
        SectionsPageAdapter mSectionsPageAdapter = new SectionsPageAdapter(getActivity().getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = getActivity().findViewById(R.id.container);
        setupViewPager(mViewPager);
        TabLayout tabLayout = getActivity().findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);
    }

    //Permite que as abas apareçam na tela
    private void setupViewPager(ViewPager viewPager) {
        SectionsPageAdapter adapter = new SectionsPageAdapter(getChildFragmentManager());
        adapter.addFragment(new TabServicosFragment(), "Serviços");
        adapter.addFragment(new TabPetOpcoesFragment(), "Opções de Pet");
        viewPager.setAdapter(adapter);
    }
}