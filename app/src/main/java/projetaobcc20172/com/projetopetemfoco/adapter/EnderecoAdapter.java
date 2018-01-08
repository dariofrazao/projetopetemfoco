package projetaobcc20172.com.projetopetemfoco.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;

import projetaobcc20172.com.projetopetemfoco.R;
import projetaobcc20172.com.projetopetemfoco.activity.EditarEnderecoActivity;
import projetaobcc20172.com.projetopetemfoco.database.services.EnderecoDaoImpl;
import projetaobcc20172.com.projetopetemfoco.model.Endereco;
import projetaobcc20172.com.projetopetemfoco.utils.Utils;

/**
 * Created by dario on 03/01/2018.
 */

public class EnderecoAdapter extends ArrayAdapter<Endereco> {

    private ArrayList<Endereco> mEnderecos;
    private Context mContext;

    public EnderecoAdapter(Context c, ArrayList<Endereco> objects) {
        super(c, 0, objects);
        this.mContext = c;
        this.mEnderecos = objects;
    }

    @NonNull
    @Override
    public View getView(final int position, final View convertView, @NonNull final ViewGroup parent) {

        View view = null;

        // Verifica se a lista está preenchida
        if (mEnderecos != null) {

            // inicializar objeto para montagem da view
            final LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            // Monta view a partir do xml
            assert inflater != null;
            view = inflater.inflate(R.layout.lista_itens, parent, false);

            // recupera elemento para exibição
            TextView mNome = view.findViewById(R.id.tvTitulo);
            TextView mTipo = view.findViewById(R.id.tvSubtitulo);
            ImageButton mRemoverEndereco = view.findViewById(R.id.ibtnRemover);
            final ImageButton mEditarEndereco = view.findViewById(R.id.ibtnEditar);

            final Endereco endereco = mEnderecos.get(position);
            mNome.setText(endereco.getBairro());
            mTipo.setText(endereco.getLocalidade());

            //Recuperar id do usuário logado
            final String idUsuarioLogado;
            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getContext());
            idUsuarioLogado = preferences.getString("id", "");

            //Ação do ícone para remover um endereço
            mRemoverEndereco.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    final DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            switch (which){
                                case DialogInterface.BUTTON_POSITIVE:
                                    // Botão sim foi clicado
                                    EnderecoDaoImpl usuarioDao = new EnderecoDaoImpl(getContext());
                                    //Chamada do DAO para remover endereço do banco
                                    usuarioDao.removerEndereco(endereco, idUsuarioLogado);
                                    mEnderecos.remove(position);
                                    notifyDataSetChanged();
                                    break;

                                case DialogInterface.BUTTON_NEGATIVE:
                                    // Botão não foi clicado
                                    break;
                                default:
                                    break;
                            }
                        }
                    };

                    //Exibe pergunta se o usuário realmente deseja remover um endereço
                    Utils.mostrarPerguntaSimNao(getContext(), mContext.getString(R.string.atencao),
                            mContext.getString(R.string.pergunta_confirma_remocao_endereco), dialogClickListener,
                            dialogClickListener);
                }
            });

            //Ação do ícone para editar um endereço
            mEditarEndereco.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    //Enviar para a Activity de Edição do endereço seus atuais dados salvos para exibição
                    Intent intent = new Intent(getContext(), EditarEnderecoActivity.class);
                    Log.i("VER", endereco.getId());
                    intent.putExtra("idEndereco", endereco.getId());
                    intent.putExtra("logradouroEndereco", endereco.getLogradouro());
                    intent.putExtra("numeroEndereco", endereco.getNumero());
                    intent.putExtra("complementoEndereco", endereco.getComplemento());
                    intent.putExtra("bairroEndereco", endereco.getBairro());
                    intent.putExtra("localidadeEndereco", endereco.getLocalidade());
                    intent.putExtra("ufEndereco", endereco.getUf());
                    intent.putExtra("cepEndereco", endereco.getCep());
                    getContext().startActivity(intent);
                }
            });

        }
        return view;
    }
}
