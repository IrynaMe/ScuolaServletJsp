package main.model;

public class Materia {
    private String codice;
    private String nomeMateria;
    private int abilitato;

    public Materia(String codice, String nomeMateria) {
        this.codice = codice;
        this.nomeMateria = nomeMateria;
    }

    public String getCodice() {
        return codice;
    }

    public void setCodice(String codice) {
        this.codice = codice;
    }

    public String getNomeMateria() {
        return nomeMateria;
    }

    public void setNomeMateria(String nomeMateria) {
        this.nomeMateria = nomeMateria;
    }

    public int getAbilitato() {
        return abilitato;
    }

    public void setAbilitato(int abilitato) {
        this.abilitato = abilitato;
    }


    @Override
    public String toString() {
        return "Materia:" +
                "codice='" + codice + '\'' +
                ", nomeMateria='" + nomeMateria + '\'' +
                ", abilitato=" + abilitato;
    }
}
