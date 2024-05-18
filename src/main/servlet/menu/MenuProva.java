package main.servlet.menu;

public enum MenuProva implements MenuInterfaccia {
    AGGIUNGI_PROVA_ALLIEVO("Aggiungi prova di un alllievo"),
    CERCA_PROVA("Cerca prova per data e ora"),
    CAMBIA_STATO_PROVA("Cambia stato prova: abilita/disabilita"),
  //  STAMPA_LISTA_PROVE_ALLIEVO("Stampa lista di prove"),
   // STAMPA_LISTA_PEROVE_CLASSE("Stampa lista prove di classe"),
    ESCI("Esci");


    private final String description;
    MenuProva(String description) {
        this.description = description;
    }
    public String getDescription() {
        return description;
    }
}
