package projetaobcc20172.com.projetopetemfoco.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import projetaobcc20172.com.projetopetemfoco.R;
import projetaobcc20172.com.projetopetemfoco.adapter.OpPetGridAdapter;
import projetaobcc20172.com.projetopetemfoco.utils.Utils;

/**
 * Created by raul1 on 05/01/2018.
 */

public class TabPetOpcoesFragment extends Fragment {
    private OpPetGridAdapter mPetAdapter;
    private static ArrayList<String> sOpcaosSelecionada = new ArrayList<>();
    private String opcaoTodos = "Todos";
    private ArrayList<String> tiposPets;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.tab_opcoes_pet_fragment, container, false);
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        final GridView gridView = getActivity().findViewById(R.id.gridOpPet);
        this.tiposPets = Utils.recuperaArrayR(getActivity(),R.array.tiposPetBusca);
        sOpcaosSelecionada.add(this.opcaoTodos);
        ArrayList<String> tiposServico =  Utils.recuperaArrayR(getActivity(),R.array.tiposPetBusca);
        mPetAdapter = new OpPetGridAdapter(getActivity(),tiposServico);
        gridView.setAdapter(mPetAdapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                TextView opcaoSelecionada = view.findViewById(R.id.tvGridTextPet);
                ImageView im = view.findViewById(R.id.gridImgPet);
                addOpcoes(opcaoSelecionada.getText().toString(),im,gridView);
                for(String s: sOpcaosSelecionada){
                    System.out.println("opcoes "+s);
                }
                System.out.println("_______________");
            }
        });
        mPetAdapter.notifyDataSetChanged();
    }

    public static ArrayList<String> getOpcaosSelecionada(){
        return sOpcaosSelecionada;
    }


    private void addOpcoes(String selecionado,ImageView im,GridView grid){
        if(selecionado.equals(this.opcaoTodos)){
            sOpcaosSelecionada.clear();
            this.desmarcarTodos(grid);
            sOpcaosSelecionada.add(this.opcaoTodos);
            this.marcarImg(this.opcaoTodos,im);
            System.out.println("aki1");
        }
        else if(sOpcaosSelecionada.contains(this.opcaoTodos)){
            sOpcaosSelecionada.remove(this.opcaoTodos);
            sOpcaosSelecionada.add(selecionado);
            this.desmarcarTodos(grid);
            this.marcarImg(selecionado,im);
            System.out.println("aki2");
        }
        else if(sOpcaosSelecionada.contains(selecionado)){
            sOpcaosSelecionada.remove(selecionado);
            this.desmarcarImg(selecionado,im);
            System.out.println("aki3");
        }
        else{
            sOpcaosSelecionada.add(selecionado);
            this.marcarImg(selecionado,im);
            System.out.println("aki4");
        }

    }

    private void marcarImg(String selecionado, ImageView im){
        if(selecionado.equals(tiposPets.get(0))){
            im.setImageResource(R.drawable.tipo_pet_todos_check);
        }
        else if(selecionado.equals(tiposPets.get(1))){
            im.setImageResource(R.drawable.tipo_pet_cachorro_check);
        }
        else if(selecionado.equals(tiposPets.get(2))){
            im.setImageResource(R.drawable.tipo_pet_gato_check);
        }
    }

    private void desmarcarImg(String selecionado, ImageView im){
        if(selecionado.equals(tiposPets.get(0))){
            im.setImageResource(R.drawable.tipo_pet_todos);
        }
        else if(selecionado.equals(tiposPets.get(1))){
            im.setImageResource(R.drawable.tipo_pet_cachorro);
        }
        else if(selecionado.equals(tiposPets.get(2))){
            im.setImageResource(R.drawable.tipo_pet_gato);
        }
    }

    private void desmarcarTodos(GridView grid){
        for(int i = 0;i<grid.getCount();i++){
            TextView tv = grid.getChildAt(i).findViewById(R.id.tvGridTextPet);
            ImageView iv = grid.getChildAt(i).findViewById(R.id.gridImgPet);
            this.desmarcarImg(tv.getText().toString(),iv);
        }
    }

}
