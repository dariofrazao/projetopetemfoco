package projetaobcc20172.com.projetopetemfoco.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

import projetaobcc20172.com.projetopetemfoco.R;
import projetaobcc20172.com.projetopetemfoco.adapter.OpPetGridAdapter;
import projetaobcc20172.com.projetopetemfoco.config.ConfiguracoesBuscaServico;
import projetaobcc20172.com.projetopetemfoco.utils.Enumerates;
import projetaobcc20172.com.projetopetemfoco.utils.Utils;

/**
 * Created by raul1 on 05/01/2018.
 * Classe que representa o filtro para a tela de busca por servico
 */

public class FiltroServicoDialog extends Activity implements View.OnClickListener {

    private OpPetGridAdapter mPetAdapter = null;
    private ArrayList<String> mOpcaosSelecionada;
    private HashMap<String,Integer> botoesClicados;//Hash que verifica se o botão está marcado ou não. 1 se estiver e 0 se não estiver
    private String opcaoTodos = "Todos";
    private ArrayList<String> tiposPets;
    private CheckBox cbProx;
    private CheckBox cbAvaliacao;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tab_opcoes_pet_fragment);
        final GridView gridView = findViewById(R.id.gridOpPet);

        Button btnSalvarFiltro = findViewById(R.id.btnSalvarFiltro);
        cbProx = findViewById(R.id.cbProx);
        cbAvaliacao = findViewById(R.id.cbAva);
        this.tiposPets = Utils.recuperaArrayR(this,R.array.tiposPetBusca);
        this.carregarFiltro();//Carrega as informações existentes no obj ConfiguracaoBuscaServico
        gridView.setAdapter(mPetAdapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                TextView opcaoSelecionada = view.findViewById(R.id.tvGridTextPet);
                ImageView im = view.findViewById(R.id.gridImgPet);
                addOpcoes(opcaoSelecionada.getText().toString(),im,gridView);
            }
        });

        this.cbAvaliacao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                cbAvaliacao.setChecked(true);
                cbProx.setChecked(false);
            }
        });

        this.cbProx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cbAvaliacao.setChecked(false);
                cbProx.setChecked(true);
            }
        });

        btnSalvarFiltro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                salvarOpcoes();
                Utils.mostrarMensagemCurta(FiltroServicoDialog.this, getString(R.string.filtro_atualizado_servico));
                finish();
            }
        });


      //  mPetAdapter.notifyDataSetChanged();
    }


    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onResume(){
        super.onResume();
        if(ConfiguracoesBuscaServico.getsFiltro().equals(Enumerates.Filtro.DISTANCIA)) {
            this.cbProx.setChecked(true);
            this.cbAvaliacao.setChecked(false);
        }
        else if(ConfiguracoesBuscaServico.getsFiltro().equals(Enumerates.Filtro.AVALICAO)){
            this.cbAvaliacao.setChecked(true);
            this.cbProx.setChecked(false);
        }
    }

    private void inicializarOpcoes(){
        mOpcaosSelecionada = ConfiguracoesBuscaServico.getsOpcaosPet();
    }

    //Método responsável por controlar os cliques nos ícones de tipos de pets
    private void addOpcoes(String selecionado,ImageView im,GridView grid){
        if(selecionado.equals(this.opcaoTodos)){
            mOpcaosSelecionada.clear();
            this.desmarcarTodos(grid);
            mOpcaosSelecionada.add(this.opcaoTodos);
            this.marcarImg(this.opcaoTodos,im);
        }
        else if(this.botoesClicados.get(this.opcaoTodos)==1){
            this.botoesClicados.put(this.opcaoTodos,0);
            mOpcaosSelecionada.remove(this.opcaoTodos);
            mOpcaosSelecionada.add(selecionado);
            this.desmarcarTodos(grid);
            this.marcarImg(selecionado,im);
        }
        //Desmarca um item marcado somente se a lista tiver mais de um item marcado
        //garante que sempre existirá pelo menos um item marcado para busca.
        else if(this.botoesClicados.get(selecionado)==1 && mOpcaosSelecionada.size()>1){
            mOpcaosSelecionada.remove(selecionado);
            this.desmarcarImg(selecionado,im);
        }//Se não está na lista add
        else if(this.botoesClicados.get(selecionado)==0){
            mOpcaosSelecionada.add(selecionado);
            this.marcarImg(selecionado,im);
        }

    }
    //coloca o marcado de "certo" na no botao
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
        this.botoesClicados.put(selecionado,1);
    }

    private void desmarcarImg(String selecionado, ImageView im){
        if(selecionado.equals(tiposPets.get(0)) ){
            im.setImageResource(R.drawable.tipo_pet_todos);
        }
        else if(selecionado.equals(tiposPets.get(1))){
            im.setImageResource(R.drawable.tipo_pet_cachorro);
        }
        else if(selecionado.equals(tiposPets.get(2))){
            im.setImageResource(R.drawable.tipo_pet_gato);
        }
        this.botoesClicados.put(selecionado,0);
    }

    private void desmarcarTodos(GridView grid){
        for(int i = 0;i<grid.getCount();i++){
            TextView tv = grid.getChildAt(i).findViewById(R.id.tvGridTextPet);
            ImageView iv = grid.getChildAt(i).findViewById(R.id.gridImgPet);
            this.desmarcarImg(tv.getText().toString(),iv);
            this.botoesClicados.put(tv.getText().toString(),0);
        }
    }

    private void inicializarHashBotoes(){
        this.botoesClicados = new HashMap<String,Integer>();
        for(String pet:this.tiposPets){
            this.botoesClicados.put(pet,0);
        }
        this.botoesClicados.put(this.opcaoTodos,1);
    }

    private void salvarOpcoes(){
        ConfiguracoesBuscaServico.setsOpcaosPet(this.mOpcaosSelecionada);
        if(this.cbAvaliacao.isChecked()){
            ConfiguracoesBuscaServico.setsFiltro(Enumerates.Filtro.AVALICAO);
        }
        else if(this.cbProx.isChecked()){
            ConfiguracoesBuscaServico.setsFiltro(Enumerates.Filtro.DISTANCIA);
        }

    }

    private void carregarFiltro(){
        this.inicializarOpcoes();
        this.inicializarHashBotoes();
        if(ConfiguracoesBuscaServico.getsEstado().equals(Enumerates.Estado.DEFAULT)) {
            mPetAdapter = new OpPetGridAdapter(this, tiposPets);
        }
        else{
            ArrayList <String> pets = new ArrayList<>();
            for(String pet:this.tiposPets){
                if(ConfiguracoesBuscaServico.getsOpcaosPet().contains(pet)){
                    if(pet.equalsIgnoreCase(this.opcaoTodos)){
                        pets.add(pet);
                    }
                    else{
                        pets.add(pet+"_check");
                    }
                    this.botoesClicados.put(pet,1);
                }
                else{
                    if(pet.equalsIgnoreCase(this.opcaoTodos)){
                        pets.add(pet+"_uncheck");
                        this.botoesClicados.put(pet,0);
                    }
                    else{
                        pets.add(pet);
                    }
                }
            }
            mPetAdapter = new OpPetGridAdapter(this, pets);
        }
    }

    @Override
    public void onClick(View v) {

    }


}
