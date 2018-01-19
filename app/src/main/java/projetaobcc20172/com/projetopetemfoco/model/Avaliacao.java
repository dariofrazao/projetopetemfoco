package projetaobcc20172.com.projetopetemfoco.model;


/**
 * Created by Alexsandro on 08/01/2018.
 */

public class Avaliacao {
    private String mId;
    private String mIdUsuario;
    private String mNomeUsuario;
    private String mEstrelas;
    private String mCometario;


    public Avaliacao() {
    }

    public Avaliacao(String mIdUsuario, String mNomeUsuario, String mEstrelas, String mCometario) {
        this.mIdUsuario = mIdUsuario;
        this.mEstrelas = mEstrelas;
        this.mCometario = mCometario;
        this.mNomeUsuario = mNomeUsuario;
    }

    public String getIdUsuario() {

        return mIdUsuario;
    }

    public String getId() {
        return mId;
    }

    public void setId(String mId) {
        this.mId = mId;
    }
    public void setIdUsuario(String mIdUsuario) {
        this.mIdUsuario = mIdUsuario;
    }

    public String getEstrelas() {
        return mEstrelas;
    }

    public void setEstrelas(String mEstrelas) {
        this.mEstrelas = mEstrelas;
    }

    public String getCometario() {
        return mCometario;
    }

    public void setCometario(String mCometario) {
        this.mCometario = mCometario;
    }

    public String getmNomeUsuario() {
        return mNomeUsuario;
    }

    public void setmNome(String mNomeUsuario) {
        this.mNomeUsuario = mNomeUsuario;
    }
}
