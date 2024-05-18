package main.servlet;

public class Classe {
    private int livello;
    private String sezione;
    private int abilitato;

    public Classe(int livello, String sezione) {
        this.livello = livello;
        this.sezione = sezione;
    }

    public int getAbilitato() {
        return abilitato;
    }

    public void setAbilitato(int abilitato) {
        this.abilitato = abilitato;
    }

    public int getLivello() {
        return livello;
    }

    public void setLivello(int livello) {
        this.livello = livello;
    }

    public String getSezione() {
        return sezione;
    }

    public void setSezione(String sezione) {
        this.sezione = sezione;
    }

    @Override
    public String toString() {
        return "Classe:" +
                "livello=" + livello +
                ", sezione='" + sezione + '\'' +
                ", abilitato=" + abilitato;
    }
}//
