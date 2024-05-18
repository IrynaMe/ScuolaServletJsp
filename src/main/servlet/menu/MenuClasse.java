package main.servlet.menu;

public enum MenuClasse implements MenuInterfaccia{
   // AGGIUNGI_CLASSE("Aggiungi classe"),
   CERCA_CLASSE("Cerca classe per livello e sezione"),
    CAMBIA_STATO_CLASSE("Cambia stato classe: abilita/disabilita"),
    //  STAMPA_LISTA_CLASSI("Stampa lista di classi"),
    ESCI("Esci");

    private final String description;
    MenuClasse(String description) {
        this.description = description;
    }
    public String getDescription() {
        return description;
    }
}
