package projetaobcc20172.com.projetopetemfoco.model;

public class Pet {
    private String mIdPet;
    private String mNome;
    private String mTipo;
    private String mIdade;
    private String mPorte;
    private String mRaca;
    private String mGenero;

    public Pet(){
    }

    public Pet(String idPet, String nome, String tipo, String idade, String porte, String raça, String genero){
        this.mIdPet = idPet;
        this.mNome = nome;
        this.mTipo = tipo;
        this.mIdade = idade;
        this.mPorte = porte;
        this.mRaca = raça;
        this.mGenero = genero;
    }

    public Pet(String nome,String raca){
        this.mNome = nome;
        this.mRaca = raca;
    }

    public String getNome() {
        return mNome;
    }

    public void setNome(String nome) {
        this.mNome = nome;
    }

    public String getTipo() {
        return mTipo;
    }

    public void setTipo(String tipo) {
        this.mTipo = tipo;
    }

    public String getPorte() {
        return mPorte;
    }

    public void setPorte(String porte) {
        this.mPorte = porte;
    }

    public String getGenero() {
        return mGenero;
    }

    public void setGenero(String genero) {
        this.mGenero = genero;
    }

    public String getIdade() {
        return mIdade;
    }

    public void setIdade(String idade) {
        this.mIdade = idade;
    }

    public String getRaça() {
        return mRaca;
    }

    public void setRaça(String raça) {
        this.mRaca = raça;
    }

    public String getIdPet() {
        return mIdPet;
    }

    public void setIdPet(String idPet) {
        this.mIdPet = idPet;
    }

}
