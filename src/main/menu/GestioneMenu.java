package main.menu;

import java.util.*;

public class GestioneMenu {


    //opzioni con allievo
    OpzioneMenu opzioneMenu1 = new OpzioneMenu("Aggiungi Allievo", "inserimentoPersona.html?personType=allievo","Operazioni con allievo");
    OpzioneMenu opzioneMenu2 = new OpzioneMenu("Cerca Allievo", "inputCf.jsp?personType=allievo","Operazioni con allievo");
    OpzioneMenu opzioneMenu3 = new OpzioneMenu("Cerca/Cambia stato Allievo", "inputCf.jsp?personType=allievo","Operazioni con allievo");
    OpzioneMenu opzioneMenu4 = new OpzioneMenu("Stampa lista di tutti Allievi", "ScuolaServlet?action=stampaLista&personType=allievo","Operazioni con allievo");
    OpzioneMenu opzioneMenu5 = new OpzioneMenu("Stampa Allievi di una classe", "ScuolaServlet?method=get&action=scegliClasse","Operazioni con allievo");
    OpzioneMenu opzioneMenu13 = new OpzioneMenu("Aggiungi prova di Allievo", "ScuolaServlet?method=get&action=scegliMateria","Operazioni con allievo");
    //opzioni con docente
    OpzioneMenu opzioneMenu6 = new OpzioneMenu("Aggiungi Docente", "inserimentoPersona.html?personType=docente","Operazioni con docente");
    OpzioneMenu opzioneMenu7 = new OpzioneMenu("Cerca Docente", "inserimentoPersona.html?personType=docente","Operazioni con docente");
    OpzioneMenu opzioneMenu8 = new OpzioneMenu("Cerca/Cambia stato Docente", "inputCf.jsp?personType=docente","Operazioni con docente");
    OpzioneMenu opzioneMenu9 = new OpzioneMenu("Stampa lista Docenti", "ScuolaServlet?action=stampaLista&personType=docente","Operazioni con docente");


    //opzioni con amministrativo
    OpzioneMenu opzioneMenu10 = new OpzioneMenu("Aggiungi Amministrativo", "inserimentoPersona.html?personType=amministrativo","Operazioni con Amministrativo");
    OpzioneMenu opzioneMenu11 = new OpzioneMenu("Cerca/Cambia stato Amministrativo", "inserimentoPersona.html?personType=amministrativo","Operazioni con Amministrativo");
    OpzioneMenu opzioneMenu12 = new OpzioneMenu("Stampa lista Amministrativi", "ScuolaServlet?action=stampaLista&personType=amministrativo","Operazioni con Amministrativo");
    private List<OpzioneMenu> opzioniAmministrativo = new ArrayList<>();
    private List<OpzioneMenu> opzioniDocente = new ArrayList<>();
    private List<OpzioneMenu> opzioniAllievo = new ArrayList<>();

    public GestioneMenu() {

            // definisco opzioni amministrativo
            Collections.addAll(opzioniAmministrativo,
                    opzioneMenu1, opzioneMenu3, opzioneMenu4,
                    opzioneMenu5, opzioneMenu6, opzioneMenu7, opzioneMenu8,
                    opzioneMenu9, opzioneMenu10, opzioneMenu11, opzioneMenu12);
            // definisco opzioni docente
            Collections.addAll(opzioniDocente,
                    opzioneMenu2, opzioneMenu4,
                    opzioneMenu5, opzioneMenu7,
                    opzioneMenu9,opzioneMenu13);
            // definisco opzioni allievo
            Collections.addAll(opzioniAllievo,
                    opzioneMenu2, opzioneMenu4,
                    opzioneMenu5);
        }


     public Set<String> getSectionsAmministrativo(){
        Set<String> sections=new HashSet<String>();

        for(OpzioneMenu opzioneMenu:opzioniAmministrativo){
            sections.add(opzioneMenu.getDropdownSection());
        }
        return sections;
     }
    public Set<String> getSectionsDocente(){
        Set<String> sections=new HashSet<String>();

        for(OpzioneMenu opzioneMenu:opzioniDocente){
            sections.add(opzioneMenu.getDropdownSection());
        }
        return sections;
    }
    public Set<String> getSectionsAllievo(){
        Set<String> sections=new HashSet<String>();

        for(OpzioneMenu opzioneMenu:opzioniAllievo){
            sections.add(opzioneMenu.getDropdownSection());
        }
        return sections;
    }
    public OpzioneMenu getOpzioneMenu1() {
        return opzioneMenu1;
    }

    public void setOpzioneMenu1(OpzioneMenu opzioneMenu1) {
        this.opzioneMenu1 = opzioneMenu1;
    }

    public OpzioneMenu getOpzioneMenu2() {
        return opzioneMenu2;
    }

    public void setOpzioneMenu2(OpzioneMenu opzioneMenu2) {
        this.opzioneMenu2 = opzioneMenu2;
    }

    public OpzioneMenu getOpzioneMenu3() {
        return opzioneMenu3;
    }

    public void setOpzioneMenu3(OpzioneMenu opzioneMenu3) {
        this.opzioneMenu3 = opzioneMenu3;
    }

    public OpzioneMenu getOpzioneMenu4() {
        return opzioneMenu4;
    }

    public void setOpzioneMenu4(OpzioneMenu opzioneMenu4) {
        this.opzioneMenu4 = opzioneMenu4;
    }

    public OpzioneMenu getOpzioneMenu5() {
        return opzioneMenu5;
    }

    public void setOpzioneMenu5(OpzioneMenu opzioneMenu5) {
        this.opzioneMenu5 = opzioneMenu5;
    }

    public OpzioneMenu getOpzioneMenu6() {
        return opzioneMenu6;
    }

    public void setOpzioneMenu6(OpzioneMenu opzioneMenu6) {
        this.opzioneMenu6 = opzioneMenu6;
    }

    public OpzioneMenu getOpzioneMenu7() {
        return opzioneMenu7;
    }

    public void setOpzioneMenu7(OpzioneMenu opzioneMenu7) {
        this.opzioneMenu7 = opzioneMenu7;
    }

    public OpzioneMenu getOpzioneMenu8() {
        return opzioneMenu8;
    }

    public void setOpzioneMenu8(OpzioneMenu opzioneMenu8) {
        this.opzioneMenu8 = opzioneMenu8;
    }

    public OpzioneMenu getOpzioneMenu9() {
        return opzioneMenu9;
    }

    public void setOpzioneMenu9(OpzioneMenu opzioneMenu9) {
        this.opzioneMenu9 = opzioneMenu9;
    }

    public OpzioneMenu getOpzioneMenu10() {
        return opzioneMenu10;
    }

    public void setOpzioneMenu10(OpzioneMenu opzioneMenu10) {
        this.opzioneMenu10 = opzioneMenu10;
    }

    public OpzioneMenu getOpzioneMenu11() {
        return opzioneMenu11;
    }

    public void setOpzioneMenu11(OpzioneMenu opzioneMenu11) {
        this.opzioneMenu11 = opzioneMenu11;
    }

    public OpzioneMenu getOpzioneMenu12() {
        return opzioneMenu12;
    }

    public void setOpzioneMenu12(OpzioneMenu opzioneMenu12) {
        this.opzioneMenu12 = opzioneMenu12;
    }

    public List<OpzioneMenu> getOpzioniAmministrativo() {
        return opzioniAmministrativo;
    }

    public void setOpzioniAmministrativo(List<OpzioneMenu> opzioniAmministrativo) {
        this.opzioniAmministrativo = opzioniAmministrativo;
    }

    public List<OpzioneMenu> getOpzioniDocente() {
        return opzioniDocente;
    }

    public void setOpzioniDocente(List<OpzioneMenu> opzioniDocente) {
        this.opzioniDocente = opzioniDocente;
    }

    public List<OpzioneMenu> getOpzioniAllievo() {
        return opzioniAllievo;
    }

    public void setOpzioniAllievo(List<OpzioneMenu> opzioniAllievo) {
        this.opzioniAllievo = opzioniAllievo;
    }
}


