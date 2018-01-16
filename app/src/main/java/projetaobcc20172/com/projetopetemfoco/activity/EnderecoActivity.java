package projetaobcc20172.com.projetopetemfoco.activity;

import android.support.v4.app.Fragment;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import projetaobcc20172.com.projetopetemfoco.R;
import projetaobcc20172.com.projetopetemfoco.adapter.EnderecoAdapter;
import projetaobcc20172.com.projetopetemfoco.config.ConfiguracaoFirebase;
import projetaobcc20172.com.projetopetemfoco.model.Endereco;

public class EnderecoActivity extends Fragment {

    private ArrayList<Endereco> mEnderecos;
    private ArrayAdapter<Endereco> mAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //returning our layout file
        //change R.layout.yourlayoutfilename for each of your fragments
        return inflater.inflate(R.layout.activity_endereco, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //you can set the title for your toolbar here for different fragments different titles
        getActivity().setTitle("Endereço");

        //Recuperar id do usuário logado
        String idUsuarioLogado;
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        idUsuarioLogado = preferences.getString("id", "");

        ImageButton mCadastrarEndereco; //Botão de cadastrar o endereço


        mCadastrarEndereco = getView().findViewById(R.id.btnCadastrarEndereco);
        ListView mListView;
        mListView = getView().findViewById(R.id.lv_enderecos);

        // Monta listview e mAdapter
        mEnderecos = new ArrayList<>();
        mAdapter = new EnderecoAdapter(getActivity(), mEnderecos);
        mListView.setAdapter(mAdapter);

        // Recuperar endereços do Firebase
        DatabaseReference mFirebase;
        mFirebase = ConfiguracaoFirebase.getFirebase().child("usuarios").child(idUsuarioLogado).child("enderecos");

        ValueEventListener mValueEventListenerEndereco;
        mValueEventListenerEndereco = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                mEnderecos.clear();

                // Recupera endereços
                for (DataSnapshot dados : dataSnapshot.getChildren()) {
                    Endereco endereco = dados.getValue(Endereco.class);
                    mEnderecos.add(endereco);
                }
                //Notificar o adaptador que exibe a lista de endereços se houver alteração no banco
                mAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(getActivity(), "Erro na leitura do banco de dados", Toast.LENGTH_SHORT).show();
            }
        };

        mCadastrarEndereco.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), CadastroEnderecoActivity.class);
                startActivity(intent);
            }
        });
        mFirebase.addValueEventListener(mValueEventListenerEndereco);

    }



}
