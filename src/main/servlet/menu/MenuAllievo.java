package main.servlet.menu;

public enum MenuAllievo implements MenuInterfaccia {
    AGGIUNGI_ALLIEVO("Aggiungi allievo"),
    //MODIFICA_ALLIEVO("Modifica allievo"),
    CAMBIA_STATO_ALLIEVO("Cambia stato allievo: abilita/disabilita"),
    CERCA_ALLIEVO("Cerca allivo per cf"),
    STAMPA_LISTA_ALLIEVI("Stampa lista allievi"),
   // STAMPA_PROVE_ALLIEVO("Stampa prove allievo"),
    ESCI("Esci");


    private final String description;
    MenuAllievo(String description) {
        this.description = description;
    }
    public String getDescription() {
        return description;
    }
}
