package main.servlet;

import main.librerie.ManageDb;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;


import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

public class ScuolaServlet extends HttpServlet {
    private ManageDb mioDB;
    //   private static final int RECORDS_PER_PAGE = 10;
    private String usernameCorrente;
    private Integer userProfile;
    private String cfUser;
    private String materiaScelta;
    private String allievoCf;
    private String allievoNome;
    private String allievoCognome;
    private String statoScelto;
    private String provinciaScelta;
    private String comuneScelta;
    private String personType; // object of interest
    // String passwordCorrente = null;
    PrintWriter writer;
    boolean isConnected;

    private void login(HttpServletRequest request, HttpServletResponse response) throws IOException {
        ResultSet resultSet = null;

        HttpSession session = request.getSession(false); // prendo session se gia esiste, else null

        if (session == null) {
            session = request.getSession(); // Creo nuova session
        } else {
            // controllo se user gia logged in prima
            if (session.getAttribute("currentUser") != null) {
                sendHtmlPage("welcome.jsp", request, response);
                return; // esco dal method
            }
        }

        String usernameCorrente = request.getParameter("username");
        String passwordCorrente = request.getParameter("password");


        if (usernameCorrente != null && passwordCorrente != null) {
            if (isConnected) {
                try {
                    String sqlQuery = "SELECT * FROM utente WHERE username='" + usernameCorrente + "' AND password='" + passwordCorrente + "' AND abilitato=1";
                    resultSet = mioDB.readInDb(sqlQuery);
                    if (resultSet.next()) {
                        usernameCorrente = resultSet.getString("username");
                        userProfile = resultSet.getInt("profilo");
                        //prendo cf
                        if (userProfile == 1) {
                            cfUser = resultSet.getString("cf_amministrativo");
                        } else if (userProfile == 2) {
                            cfUser = resultSet.getString("cf_docente");
                        } else if (userProfile == 3) {
                            cfUser = resultSet.getString("cf_allievo");
                        } else {
                            PrintWriter writer = response.getWriter();
                            writer.println("<script>");
                            writer.println("alert('Problema di corrispondenza cf al profilo');");
                            writer.println("</script>");
                            writer.flush();
                            sendHtmlPage("index.html", request, response);
                        }

                        session.setAttribute("currentUser", usernameCorrente); // Set session attribute
                        session.setAttribute("currentProfile", userProfile);
                        session.setAttribute("userCf", cfUser);
                        //!!!!no need for request setAttr?
                        request.setAttribute("currentUser", usernameCorrente);
                        request.setAttribute("userCf", cfUser);
                        sendHtmlPage("welcome.jsp", request, response);
                    } else {
                        PrintWriter writer = response.getWriter();
                        writer.println("<script>");
                        writer.println("alert('Utente con username e password inseriti NON TROVATO');");
                        writer.println("</script>");
                        writer.flush();
                        sendHtmlPage("index.html", request, response);
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            } else {
                PrintWriter writer = response.getWriter();
                writer.println("<script>");
                writer.println("alert('Problema di connessione con Database!');");
                writer.println("</script>");
                writer.flush();
                sendHtmlPage("index.html", request, response);
            }
        } else {
            PrintWriter writer = response.getWriter();
            writer.println("<script>");
            writer.println("alert('Non hai inserito username o/e password. Riprova');");
            writer.println("</script>");
            writer.flush();
            sendHtmlPage("index.html", request, response);
        }
    }


    /**
     * this life-cycle method is invoked when this servlet is first accessed
     * by the client
     */
    @Override
    public void init(ServletConfig config) {
        System.out.println("Servlet is being initialized");
        mioDB = new ManageDb();
        usernameCorrente = null;
        isConnected = mioDB.Connect("localhost", 8889, "gestione_scuola", "root", "root");
        //isConnected = mioDB.Connect("localhost", 3306, "gestione_scuola", "user_scuola", "scuola123");
        if (isConnected) {
            System.out.println("********** Connessione al DB avvenuta correttamente ***********");
        } else {
            System.out.println("Non ho connesso con db");
        }
    }

    /**
     * handles HTTP GET request
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(); // prendo sessione che gia esiste

        String action = request.getParameter("action");
        String personType = request.getParameter("personType");
        String submitAction = request.getParameter("submitAction");

        try {
            if ("logout".equals(action)) {
                logout(request, response);
            } else if ("cerca".equals(submitAction)) {
                //form da inserire cf->new action button cerca->mostra persona con alert
                cercaPersonaPerCf(request, response, personType, true);
            } else if ("cambiaStato".equals(submitAction)) {
                //form da inserire cf->new action button cambia stato->cambia stato in db+alert
                cambiaStatoPersona(request, response, personType);
            } else if ("stampaLista".equals(action)) {
                //stampa tabella con dati di persone con writer.println
                stampaListaPersone(request, response, personType);
            } else if ("scegliClasse".equals(action)) {
                //prepare select ->form InputClasseSezione
                cercaClasse(request, response);
            } else if ("stampaClasse".equals(action)) {
                //pagina stampaClasse con dati di allievo con parametri da frorm InputClasseSezione
                stampaAllieviDiClasse(request, response);
            } else if ("scegliMateria".equals(action)) {
                //prepare select materia ->form inputProva
                scegliMateria(request, response);
            } else if ("confirmMateria".equals(action)) {
                //prepare select allievo->form inputProva
                dammiAllieviInClasse(request, response);
            } else if ("confirmAllievo".equals(action)) {
                String allievoInfo = request.getParameter("allievo");
                String[] allievoParts = allievoInfo.split("_");
                allievoCf = allievoParts[0];
                allievoNome = allievoParts[1];
                allievoCognome = allievoParts[2];
                sendHtmlPage("inputProva.jsp?step=provaInput", request, response);
            } else if ("confermaStato".equals(action)) {
                //prepare select province ->form inserimentoPersona
                dammiProvince(request, response);
            } else if ("confermaProvincia".equals(action)) {
                //prepare select comuni ->form inserimentoPersona
                dammiComuni(request, response);
            } else if ("confermaComune".equals(action)) {
                comuneScelta = request.getParameter("comuneNascita");
                sendHtmlPage("inserimentoPersona2.jsp?step=datiPersona", request, response);
            } else {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid action");
            }
        } catch (ServletException e) {
            System.out.println(e);
        }
    }

    /**
     * handles HTTP POST request
     */
    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession(); // prendo session

        String formTypeScelta = request.getParameter("formType");
        // String submitction= request.getParameter("submitaction");
        if (formTypeScelta.equals("login")) {
            login(request, response);
        } else if (formTypeScelta.equals("newPerson")) {
            inserimentoPersona(request, response);
        } else if (formTypeScelta.equals("newProva")) {
            inserimentoProva(request, response);
        }
    }

    //da chiamare in cercaClasse
    private ArrayList<String> dammiAnniScolastici() {
        ArrayList<String> anniScolastici = new ArrayList<>();
        String sqlQuery = "SELECT DISTINCT anno_scolastico FROM allievo_in_classe";

        try (ResultSet resultSet = mioDB.readInDb(sqlQuery)) {
            while (resultSet.next()) {
                anniScolastici.add(resultSet.getString("anno_scolastico"));
            }
        } catch (SQLException e) {
            System.out.println("Database error: " + e);
        }
        return anniScolastici;
    }

    //da chiamare in cercaClasse
    private ArrayList<Integer> dammiLivello() {
        ArrayList<Integer> livelli = new ArrayList<>();
        String sqlQuery = "SELECT DISTINCT livello_classe FROM allievo_in_classe";

        try (ResultSet resultSet = mioDB.readInDb(sqlQuery)) {
            while (resultSet.next()) {
                livelli.add(resultSet.getInt("livello_classe"));
            }
        } catch (SQLException e) {
            System.out.println("Database error: " + e);
        }
        return livelli;
    }


    //da chiamare in cercaClasse
    private ArrayList<String> dammiSezione() {
        ArrayList<String> sezioni = new ArrayList<>();
        String sqlQuery = "SELECT DISTINCT sezione_classe FROM allievo_in_classe";

        try (ResultSet resultSet = mioDB.readInDb(sqlQuery)) {
            while (resultSet.next()) {
                sezioni.add(resultSet.getString("sezione_classe"));
            }
        } catch (SQLException e) {
            System.out.println("Database error: " + e);
        }
        return sezioni;
    }

    //prepare select province ->form inserimentoPersona
    public void dammiProvince(HttpServletRequest request, HttpServletResponse response) throws IOException {
        System.out.println("sono in dammiProvince");
        Set<String> province = new TreeSet<>();
        personType = request.getParameter("personType");
        statoScelto = request.getParameter("statoNascita");
        String url;
        if (statoScelto.equalsIgnoreCase("Italia")) {
            try {
                FileInputStream file = new FileInputStream(new File("C:\\Users\\183360\\IdeaProjects\\ServletProva\\ServletIntellij4\\src\\main\\webapp\\files\\comuni.xls"));
                System.out.println("sono in try block");
                HSSFWorkbook workbook = new HSSFWorkbook(file);
                HSSFSheet sheet = workbook.getSheetAt(0); // province in the first sheet

                Iterator rows = sheet.rowIterator();
                boolean firstRow = true;

                while (rows.hasNext()) {
                    HSSFRow row = (HSSFRow) rows.next();
                    if (firstRow) {
                        firstRow = false;
                        continue; // Salto prima riga con titolo
                    }

                    HSSFCell provinciaCell = row.getCell(0); // provincia column is at index 0
                    if (provinciaCell != null) {
                        province.add(provinciaCell.getStringCellValue());
                    }
                }

                workbook.close();
                file.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

            // Condivido l'arraylist con pagina JSP
            request.setAttribute("listaProvince", province);
            url = "inserimentoPersona2.jsp?step=datiProvincia&personType=" + personType;
        } else {
            comuneScelta = "Estero";
            provinciaScelta = "Estero";
            url = "inserimentoPersona2.jsp?step=datiPersona&personType=" + personType;
        }

        sendHtmlPage(url, request, response);
    }

    //prepare select comuni ->form inserimentoPersona
    public void dammiComuni(HttpServletRequest request, HttpServletResponse response) throws IOException {
        System.out.println("sono in dammiComuni");

        provinciaScelta = request.getParameter("provinciaNascita");

        List<String> comuni = new ArrayList<>();
        List<String> province = new ArrayList<>();
        try {
            FileInputStream file = new FileInputStream(new File("C:\\Users\\183360\\IdeaProjects\\ServletProva\\ServletIntellij4\\src\\main\\webapp\\files\\comuni.xls"));
            System.out.println("sono in try block");
            HSSFWorkbook workbook = new HSSFWorkbook(file);
            HSSFSheet sheet = workbook.getSheetAt(0); // comuni are in the first sheet

            Iterator rows = sheet.rowIterator();
            while (rows.hasNext()) {
                HSSFRow row = (HSSFRow) rows.next();
                HSSFCell provinciaCell = row.getCell(0); // provincia column is at index 0
                if (provinciaCell != null && provinciaCell.getStringCellValue().equals(provinciaScelta)) {
                    HSSFCell comuneCell = row.getCell(1); // comune name is in the second column
                    if (comuneCell != null) {
                        comuni.add(comuneCell.getStringCellValue());
                    }
                }
            }
            workbook.close();
            file.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        for (String comune : comuni) {
            System.out.println(comune);
        }
        // Condivido l'arraylist con pagina JSP
        request.setAttribute("listaComuni", comuni);

        // mando alla paggina
        String nextPage = "inserimentoPersona2.jsp?step=datiComune";
        sendHtmlPage(nextPage, request, response);
    }

    //prepare select materia ->form inputProva
    private void scegliMateria(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession(false); // Retrieve the existing session
        if (session == null) {
            System.out.println("Session is null");
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Session not found");
            return;
        }

        cfUser = (String) session.getAttribute("userCf");
        userProfile = (Integer) session.getAttribute("currentProfile");

        System.out.println("in scegliMateria");
        ArrayList<String> materie = new ArrayList<>();

        String sqlQuery = "SELECT DISTINCT nome_materia FROM docente_classe WHERE cf_docente='" + cfUser + "'";
        System.out.println(sqlQuery);
        try (ResultSet resultSet = mioDB.readInDb(sqlQuery)) {
            while (resultSet.next()) {
                materie.add(resultSet.getString("nome_materia"));
            }
        } catch (SQLException e) {
            System.out.println("Database error: " + e);
        }


        // Condivido l'arraylist con pagina JSP
        request.setAttribute("listaMaterie", materie);

        // mando alla paggina
        String nextPage = "inputProva.jsp?step=materiaSelect";
        sendHtmlPage(nextPage, request, response);

    }

    //da chiamare in inserimentoProva
    private void dammiAllieviInClasse(HttpServletRequest request, HttpServletResponse response) throws IOException {
        System.out.println("sono in dammiAllievoInClasse");
        HttpSession session = request.getSession(false); // Retrieve the existing session
        if (session == null) {
            System.out.println("Session is null");
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Session not found");
            return;
        }

        cfUser = (String) session.getAttribute("userCf");
        userProfile = (Integer) session.getAttribute("currentProfile");
        materiaScelta = request.getParameter("materia");
        // String selectedMateria =  (String) session.getAttribute("materiaScelta");

        Map<Persona, String> allieviInClasse = new HashMap<>();

        String sqlQuery = "SELECT DISTINCT allievo.nome, allievo.cognome, allievo.cf, allievo.email, allievo_in_classe.livello_classe,allievo_in_classe.sezione_classe " +
                "FROM allievo INNER JOIN allievo_in_classe ON allievo.cf = allievo_in_classe.cf_allievo " +
                "INNER JOIN docente_classe ON docente_classe.livello_classe = allievo_in_classe.livello_classe " +
                "AND docente_classe.sezione_classe = allievo_in_classe.sezione_classe " +
                "WHERE docente_classe.cf_docente = '" + cfUser + "' AND docente_classe.nome_materia = '" + materiaScelta + "'";

        System.out.println("query allievo in classe:" + sqlQuery);

        try (ResultSet resultSet = mioDB.readInDb(sqlQuery)) {
            while (resultSet.next()) {
                Persona allievo = new Persona(
                        resultSet.getString("cf"),
                        resultSet.getString("nome"),
                        resultSet.getString("cognome"),
                        resultSet.getString("email")
                );
                String classeSezione = resultSet.getString("livello_classe") + resultSet.getString("sezione_classe");
                allieviInClasse.put(allievo, classeSezione);

            }
        } catch (SQLException e) {
            System.out.println("Database error: " + e);
        }

        // Condivido l'arraylist con pagina JSP
        request.setAttribute("mappaAllieviInClasse", allieviInClasse);

        // mando alla paggina
        String nextPage = "inputProva.jsp?step=allievoSelect";
        sendHtmlPage(nextPage, request, response);
    }

    //per preparare il form InputclasseSezione
    private void cercaClasse(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        // Preparo anni scolastici esistenti nella DB
        ArrayList<String> anniScolastici = dammiAnniScolastici();
        ArrayList<Integer> livelli = dammiLivello();
        ArrayList<String> sezioni = dammiSezione();

        // Condivido l'arraylist con pagina JSP
        request.setAttribute("listaAnniScolastici", anniScolastici);
        request.setAttribute("listaLivelli", livelli);
        request.setAttribute("listaSezioni", sezioni);

        // mando alla paggina
        sendHtmlPage("InputClasseSezione.jsp", request, response);
    }
    private String dammiNomeTabella(String personType) {
        String nomeTabellaPersona = null;
        switch (personType) {
            case "amministrativo":
                nomeTabellaPersona = "amministrativo";
                break;
            case "allievo":
                nomeTabellaPersona = "allievo";
                break;
            case "docente":
                nomeTabellaPersona = "docente";
                break;
            default:
                System.out.println("Tabella non definita");
                return null;
        }
        return nomeTabellaPersona;
    }

    private void inserimentoProva(HttpServletRequest request, HttpServletResponse response) throws IOException {
        System.out.println("sono in inserimento prova");
        HttpSession session = request.getSession(false);
        LocalDateTime dataora = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formattedDateTime = dataora.format(formatter);

        String cfDocente = cfUser;
        //  String cfAllievo= (String) session.getAttribute("cfAllievo");

        // String cfAllievo=request.getParameter("cfAllievo");


        Integer voto = (Integer.parseInt(request.getParameter("voto")));
        String sqlQuery = "INSERT INTO prova (data_ora, cf_allievo, cf_docente, nome_materia, voto) " +
                "VALUES ('" + formattedDateTime + "', '" + allievoCf + "', '" + cfDocente + "', '" + materiaScelta + "', " + voto + ");";
        System.out.println(sqlQuery);
        boolean isInserito = mioDB.writeInDb(sqlQuery);
        System.out.println("inserimento: " + isInserito);
        writer = response.getWriter();
        writer.println("<script>");
        if (isInserito) {
            writer.println("alert('Prova inserita: " + allievoCognome + " " + allievoNome + " " + materiaScelta + " " + voto + "');");

        } else {
            writer.println("alert('Errore nel inserimento prova');");
        }
        //  writer.println("window.location.href = '" + request.getContextPath() + "/welcome.jsp';");
        writer.println("</script>");
        writer.flush();


        sendHtmlPage("welcome.jsp", request, response);
    }


    private void inserimentoPersona(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String personType = request.getParameter("personType");

        String nomeTabellaPersona = dammiNomeTabella(personType);


        String codiceFiscale = request.getParameter("codiceFiscale");
        String nome = request.getParameter("nome");
        String cognome = request.getParameter("cognome");
        String sesso = request.getParameter("sesso");

        String dataNascita = request.getParameter("dataNascita"); // YYYY-MM-DD
        String email = request.getParameter("email");
        LocalDate date = LocalDate.parse(dataNascita);

        //new obj persona abilitato è 1 by default in DB
        Persona persona = new Persona(codiceFiscale, nome, cognome, sesso, statoScelto, provinciaScelta, comuneScelta, date, email);

        String sqlInsert = "INSERT INTO " + nomeTabellaPersona + " (cf, nome, cognome, sesso, stato_nascita, provincia_nascita, comune_nascita, data_nascita, email) VALUES ('"
                + persona.getCf() + "', '"
                + persona.getNome() + "', '"
                + persona.getCognome() + "', '"
                + persona.getSesso() + "', '"
                + persona.getStatoNascita() + "', '"
                + persona.getProvinciaNascita() + "', '"
                + persona.getComuneNascita() + "', '"
                + persona.getDataNascita() + "', '"
                + persona.getEmail() + "')";

        boolean isInserted = mioDB.writeInDb(sqlInsert);
        response.setContentType("text/html");
        //stampo dati inseriti in browser come alert
        writer = response.getWriter();

        writer.println("<script>");
        if (isInserted) {
            writer.println("alert('Nuovo " + nomeTabellaPersona + " inserito con successo!\\n" +
                    "Codice Fiscale: " + codiceFiscale + "\\n" +
                    "Nome: " + nome + "\\n" +
                    "Cognome: " + cognome + "\\n" +
                    "Sesso: " + sesso + "\\n" +
                    "Stato di Nascita: " + statoScelto + "\\n" +
                    "Provincia di Nascita: " + provinciaScelta + "\\n" +
                    "Comune di Nascita: " + comuneScelta + "\\n" +
                    "Data di Nascita: " + dataNascita + "\\n" +
                    "Email: " + email + "');");
        } else {
            writer.println("alert('Errore di inserimento di un nuovo " + nomeTabellaPersona + " in database!');");
        }

        // writer.println("window.location.href = '" + request.getContextPath() + "/welcome.jsp';");
        writer.println("</script>");
        writer.flush();
        sendHtmlPage("welcome.jsp", request, response);
    }

    private void stampaListaPersone(HttpServletRequest request, HttpServletResponse response, String personType) throws IOException, ServletException {
        ArrayList<Persona> persone = new ArrayList<>();
        String nomeTabellaPersona = dammiNomeTabella(personType);

        if (nomeTabellaPersona == null) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Tipo di persona non valido");
            return;
        }

        String sqlQuery = "SELECT * FROM " + nomeTabellaPersona;

        try (ResultSet resultSet = mioDB.readInDb(sqlQuery)) {
            while (resultSet.next()) {
                Persona persona = new Persona(
                        resultSet.getString("cf"),
                        resultSet.getString("nome"),
                        resultSet.getString("cognome"),
                        resultSet.getString("sesso"),
                        resultSet.getString("stato_nascita"),
                        resultSet.getString("provincia_nascita"),
                        resultSet.getString("comune_nascita"),
                        resultSet.getDate("data_nascita").toLocalDate(),
                        resultSet.getString("email")
                );
                persona.setAbilitato(resultSet.getInt("abilitato"));
                persone.add(persona);
            }
        } catch (SQLException e) {
            System.out.println("Database error" + e);
        }

        // Set the list of personas as a request attribute
        request.setAttribute("persone", persone);
        request.setAttribute("personType", personType);

        // Forward the request to the JSP page
        // RequestDispatcher dispatcher = request.getRequestDispatcher("listaPersone.jsp");
        // dispatcher.forward(request, response);
        sendHtmlPage("listaPersone.jsp", request, response);
    }




    private void stampaAllieviDiClasse(HttpServletRequest request, HttpServletResponse response) {
        ArrayList<Persona> miaListaAllievi = new ArrayList<>();

        Persona allievo = null;
        String annoScolastico = request.getParameter("annoScolastico");
        Integer livello = Integer.parseInt(request.getParameter("livello"));
        String sezione = request.getParameter("sezione");
        //prendo allievi dalla DB
        String sqlQuery = "SELECT cf, nome, cognome, email " +
                "FROM allievo, allievo_in_classe " +
                "WHERE allievo.cf = allievo_in_classe.cf_allievo " +
                "AND allievo_in_classe.anno_scolastico = '" + annoScolastico + "' " +
                "AND allievo_in_classe.livello_classe = " + livello + " " +
                "AND allievo_in_classe.sezione_classe = '" + sezione + "'";

        try (ResultSet resultSet = mioDB.readInDb(sqlQuery)) {
            while (resultSet.next()) {
                allievo = new Persona(
                        resultSet.getString("cf"),
                        resultSet.getString("nome"),
                        resultSet.getString("cognome"),
                        resultSet.getString("email")
                );
                miaListaAllievi.add(allievo);
            }
        } catch (SQLException e) {
            System.out.println("Database error" + e);
        }

        //condivido l'arraylist con paggina jsp
        request.setAttribute("listaAllievi", miaListaAllievi);
        request.setAttribute("annoScolastico", annoScolastico);
        request.setAttribute("livello", livello.toString());
        request.setAttribute("sezione", sezione);
        sendHtmlPage("stampaClasse.jsp", request, response);
    }

    private void sendHtmlPage(String sNomePagina, HttpServletRequest request, HttpServletResponse response) {
        response.setContentType("text/html");
        try {
            request.getRequestDispatcher(sNomePagina).include(request, response);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ServletException e) {
            throw new RuntimeException(e);
        }
    }


    //si usa per stampare dati di persona (boolean isRedirectNeeded=true) e cambiare stato(isRedirectNeeded=false)
    private Persona cercaPersonaPerCf(HttpServletRequest request, HttpServletResponse response, String personType, boolean isRedirectNeeded) throws IOException, ServletException {
        String cf = request.getParameter("cf");

        String nomeTabellaPersona = dammiNomeTabella(personType);

        Persona persona = null;
        if (nomeTabellaPersona == null) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Tipo di persona non valido");
            return null;
        }
        String sqlQuery = "SELECT * FROM " + nomeTabellaPersona + " WHERE cf = '" + cf + "'";
        response.setContentType("text/html");
        writer = response.getWriter();
        try (ResultSet resultSet = mioDB.readInDb(sqlQuery)) {
            if (resultSet.next()) {
                // Fetching persona details
                persona = new Persona(
                        resultSet.getString("cf"),
                        resultSet.getString("nome"),
                        resultSet.getString("cognome"),
                        resultSet.getString("sesso"),
                        resultSet.getString("stato_nascita"),
                        resultSet.getString("provincia_nascita"),
                        resultSet.getString("comune_nascita"),
                        resultSet.getDate("data_nascita").toLocalDate(),
                        resultSet.getString("email"),
                        resultSet.getInt("abilitato")
                );

                // Print dettagli persona come alert
                writer.println("<script>");
                writer.println("alert('Dettagli " + nomeTabellaPersona + ":\\n" +
                        "Codice Fiscale: " + persona.getCf() + "\\n" +
                        "Nome: " + persona.getNome() + "\\n" +
                        "Cognome: " + persona.getCognome() + "\\n" +
                        "Sesso: " + persona.getSesso() + "\\n" +
                        "Stato di Nascita: " + persona.getStatoNascita() + "\\n" +
                        "Provincia di Nascita: " + persona.getProvinciaNascita() + "\\n" +
                        "Comune di Nascita: " + persona.getComuneNascita() + "\\n" +
                        "Data di Nascita: " + persona.getDataNascita() + "\\n" +
                        "Email: " + persona.getEmail() + "\\n" +
                        "Abilitato: " + persona.getAbilitato() + "');");
                writer.println("</script>");
                writer.flush();
                if (isRedirectNeeded) {
                    //se solo sercare persona per cf
                    sendHtmlPage("welcome.jsp", request, response);
                    return persona;
                }
            } else {
                // Persona non trovata
                writer.println("<script>");
                writer.println("alert('Persona con CF " + cf + " non trovata!');");
                writer.println("</script>");
                writer.flush();
                if (isRedirectNeeded) {
                    sendHtmlPage("welcome.jsp", request, response);
                    return null;
                    // writer.println("window.location.href = '" + request.getContextPath() + "/welcome.jsp';");
                }
            }
        } catch (SQLException e) {
            throw new ServletException("Database error", e);
        }
        // writer.flush();
        return persona;
    }

    //attivare-disattivare persona
    private void cambiaStatoPersona(HttpServletRequest request, HttpServletResponse response, String personType) throws IOException, ServletException {
        String nomeTabellaPersona = dammiNomeTabella(personType);

        Persona persona = cercaPersonaPerCf(request, response, personType, false);
        response.setContentType("text/html;charset=UTF-8");
        writer = response.getWriter();

        if (persona != null) {
            int nuovoStato = (persona.getAbilitato() == 0) ? 1 : 0;

            String sqlQuery = "UPDATE " + nomeTabellaPersona + " SET abilitato = " + nuovoStato + " WHERE cf = '" + persona.getCf() + "'";
            boolean isUpdated = mioDB.writeInDb(sqlQuery);

            writer.println("<script>");
            if (isUpdated) {
                writer.println("alert('Stato di " + nomeTabellaPersona + " è cambiato:\\n" +
                        "Stato impostato: " + nuovoStato + "');");
            } else {
                writer.println("alert('Errore nel cambiare lo stato della persona.');");
            }
            //  writer.println("window.location.href = '" + request.getContextPath() + "/welcome.jsp';");
            writer.println("</script>");
            writer.flush();
            sendHtmlPage("welcome.jsp", request, response);

        } else {
            writer.println("<script>");
            writer.println("alert('Errore nel cambiare lo stato della persona.');");
            //  writer.println("window.location.href = '" + request.getContextPath() + "/welcome.jsp';");
            writer.println("</script>");
            writer.flush();
            sendHtmlPage("welcome.jsp", request, response);
        }
    }

    /**
     * this life-cycle method is invoked when the application or the server
     * is shutting down
     */
    @Override
    public void destroy() {
        System.out.println("Servlet is being destroyed");
        writer.close();
        mioDB.disconnect();
    }

    private void logout(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
        //  response.sendRedirect("index.html");
        sendHtmlPage("index.html", request, response);

    }


}