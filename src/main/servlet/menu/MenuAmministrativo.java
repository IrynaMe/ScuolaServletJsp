package main.servlet.menu;

public enum MenuAmministrativo implements MenuInterfaccia {
    AGGIUNGI_AMMINISTRATIVO("Aggiungi amministrativo"),
    //MODIFICA_AMMINISTRATIVO("Modifica amministrativo"),
    CERCA_AMMINISTRATIVO("Cerca amministrativo per cf"),
    CAMBIA_STATO_AMMINISTRATIVO("Cambia stato amministrativo: abilita/disabilita"),
    STAMPA_LISTA_AMMINISTRATIVO("Stampa lista amministrativo"),
    ESCI("Esci");


    private final String description;
    MenuAmministrativo(String description) {
        this.description = description;
    }
    public String getDescription() {
        return description;
    }
}
