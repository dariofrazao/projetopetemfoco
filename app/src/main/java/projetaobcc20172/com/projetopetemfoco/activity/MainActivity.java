package projetaobcc20172.com.projetopetemfoco.activity;

import android.support.v4.app.Fragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import projetaobcc20172.com.projetopetemfoco.R;
import projetaobcc20172.com.projetopetemfoco.config.ConfiguracaoFirebase;

public class MainActivity extends Fragment {

    private TextView mTvTitulo, mTvSubtitulo;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //returning our layout file
        //change R.layout.yourlayoutfilename for each of your fragments
        return inflater.inflate(R.layout.activity_main, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //you can set the title for your toolbar here for different fragments different titles
        getActivity().setTitle("Meus Dados");


        mTvTitulo = getView().findViewById(R.id.tvTituloConsumidor);
        mTvSubtitulo = getView().findViewById(R.id.tvSubtituloConsumidor);

        //Recuperar id do usuário logado
        final String idUsuarioLogado;
        idUsuarioLogado = getPreferences("id", getActivity());

        // Recuperar usuários do Firebase
        DatabaseReference mFirebase;
        mFirebase = ConfiguracaoFirebase.getFirebase().child("usuarios").child(idUsuarioLogado);

        mFirebase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String nome = (String)dataSnapshot.child("nome").getValue();
                String email = (String)dataSnapshot.child("email").getValue();
                //Setar texto com o nome e o email do usuário logado
                mTvTitulo.setText("Bem-vindo, " + nome);
                mTvSubtitulo.setText("E-mail: " + email);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }

    //Método que recupera o id do usuário logado
    public static String getPreferences(String key, Context context) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.getString(key, null);
    }

}