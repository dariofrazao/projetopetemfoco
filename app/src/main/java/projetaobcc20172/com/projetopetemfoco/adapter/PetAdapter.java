package projetaobcc20172.com.projetopetemfoco.adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.bumptech.glide.signature.StringSignature;
import com.firebase.ui.storage.images.FirebaseImageLoader;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

import projetaobcc20172.com.projetopetemfoco.R;
import projetaobcc20172.com.projetopetemfoco.model.Pet;

//Classe que monta uma View para exibir os pets cadastrados do usuário
public class PetAdapter extends ArrayAdapter<Pet> {

    private ArrayList<Pet> mPets;
    private Context mContext;
    private FirebaseStorage mStorage;
    private StorageReference mStorageReference;
    private String mIdUsuarioLogado;

    public PetAdapter(Context c, ArrayList<Pet> objects) {
        super(c, 0, objects);
        this.mContext = c;
        this.mPets = objects;
    }

    @NonNull
    @Override
    public View getView(final int position, final View convertView, @NonNull final ViewGroup parent) {

        View view = null;

        // Verifica se a lista está preenchida
        if (mPets != null) {

            // inicializar objeto para montagem da view
            final LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            // Monta view a partir do xml
            assert inflater != null;
            view = inflater.inflate(R.layout.lista_itens_pet, parent, false);

            // recupera elemento para exibição
            final ImageView mFoto = view.findViewById(R.id.ivFotoPet);
            TextView mNome = view.findViewById(R.id.tvTitulo);
            TextView mTipo = view.findViewById(R.id.tvSubtitulo);

            final Pet pet = mPets.get(position);
            mNome.setText(pet.getNome());
            mTipo.setText(pet.getTipo());

            //Recuperar id do usuário logado
            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getContext());
            mIdUsuarioLogado = preferences.getString("id", "");

            mStorage = FirebaseStorage.getInstance();
            mStorageReference = mStorage.getReference();
            StorageReference filePath = mStorageReference.child("imagensPets").
                    child(mIdUsuarioLogado).child(pet.getIdPet()).child(pet.getIdPet());

            //Método que exibe as fotos dos pets na lista de pets, através do Glide
            try {

                Glide.with(getContext())
                        .using(new FirebaseImageLoader())
                        .load(filePath).asBitmap()
                        .signature(new StringSignature(String.valueOf(System.currentTimeMillis() + "")))
                        .skipMemoryCache(true)
                        .diskCacheStrategy(DiskCacheStrategy.NONE)
                        .error(R.drawable.ic_action_meus_pets)
                        .into(new BitmapImageViewTarget(mFoto) {

                            @Override
                            protected void setResource(Bitmap resource) {
                                //Transforma a foto em formato circular
                                RoundedBitmapDrawable circularBitmapDrawable =
                                        RoundedBitmapDrawableFactory.create(getContext().getResources(), resource);
                                circularBitmapDrawable.setCircular(true);
                                mFoto.setImageDrawable(circularBitmapDrawable);
                            }
                        });

            } catch (Exception e) {
                e.printStackTrace();
            }

        }

        return view;
    }
}
