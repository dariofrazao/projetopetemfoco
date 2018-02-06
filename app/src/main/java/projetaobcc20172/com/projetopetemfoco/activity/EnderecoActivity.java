package projetaobcc20172.com.projetopetemfoco.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import projetaobcc20172.com.projetopetemfoco.R;
import projetaobcc20172.com.projetopetemfoco.adapter.EnderecoAdapter;
import projetaobcc20172.com.projetopetemfoco.config.ConfiguracaoFirebase;
import projetaobcc20172.com.projetopetemfoco.model.Endereco;

public class EnderecoActivity extends Fragment {

    private ArrayList<Endereco> mEnderecos;
    private ArrayAdapter<Endereco> mAdapter;
    private ListView mListView;
    private String mIdUsuarioLogado;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        return inflater.inflate(R.layout.activity_endereco, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle(R.string.endereco);
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        //Recuperar id do usuário logado
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        mIdUsuarioLogado = preferences.getString("id", "");

        ImageButton mCadastrarEndereco; //Botão de cadastrar o endereço


        mCadastrarEndereco = getView().findViewById(R.id.btnCadastrarEndereco);
        mListView = getView().findViewById(R.id.lv_enderecos);

        // Monta listview e mAdapter
        mEnderecos = new ArrayList<>();
        mAdapter = new EnderecoAdapter(getActivity(), mEnderecos);
        mListView.setAdapter(mAdapter);

        carregarEnderecos();

        this.chamarInfoEnderecoListener();

        mCadastrarEndereco.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), CadastroEnderecoActivity.class);
                startActivity(intent);
            }
        });
    }

    //Método que passa as informações de um endereço para a Activity que exibe seus detalhes
    public void chamarInfoEnderecoListener() {
        this.mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent intent = new Intent(getContext(), InfoEnderecoActivity.class);
                Endereco endereco = mEnderecos.get(position);
                intent.putExtra("Endereco", endereco);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });
    }

    public void carregarEnderecos(){
        //Recuperar endereços do Firebase

        Query query = ConfiguracaoFirebase.getFirebase().child("enderecos").orderByChild("idUsuario").equalTo(mIdUsuarioLogado);

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
                //Notificar o adaptar que exibe a lista de endereços se houver alteração no banco
                mAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(getContext(), "Erro na leitura do banco de dados", Toast.LENGTH_SHORT).show();
            }
        };

        query.addValueEventListener(mValueEventListenerEndereco);
    }

}
