package main.servlet;

public enum Entita {
    DOCENTE("Operazioni con docente"),
    ALLIEVO("Operazioni con allievo"),
    AMMINISTRATIVO("Operazioni con amministrativo"),
    MATERIA("Operazioni con materia"),
    CLASSE("Operazioni con classe"),
    PROVA("Operazioni con prova"),
    NON_DEFINITO("Esci");

    private final String description;

    Entita(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
