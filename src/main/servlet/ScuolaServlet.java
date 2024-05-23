package main.servlet;

import main.librerie.ManageDb;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class ScuolaServlet extends HttpServlet {
    private ManageDb mioDB;
    //   private static final int RECORDS_PER_PAGE = 10;
    String usernameCorrente;
    Integer userProfile;
    // String passwordCorrente = null;
    PrintWriter writer;
    boolean isConnected;


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
        HttpSession session = request.getSession(); // Obtain the session

        String formTypeScelta = request.getParameter("formType");
        if (formTypeScelta.equals("login")) {
            login(request, response);
        } else if (formTypeScelta.equals("newPerson")) {
            inserimentoPersona(request, response);
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

                        session.setAttribute("currentUser", usernameCorrente); // Set session attribute
                        session.setAttribute("currentProfile", userProfile);
                        request.setAttribute("currentUser", usernameCorrente);
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

    private void inserimentoPersona(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String personType = request.getParameter("personType");

        String nomeTabellaPersona = dammiNomeTabella(personType);


        String codiceFiscale = request.getParameter("codiceFiscale");
        String nome = request.getParameter("nome");
        String cognome = request.getParameter("cognome");
        String sesso = request.getParameter("sesso");
        String statoNascita = request.getParameter("statoNascita");
        String provinciaNascita = request.getParameter("provinciaNascita");
        String comuneNascita = request.getParameter("comuneNascita");
        String dataNascita = request.getParameter("dataNascita"); // YYYY-MM-DD
        String email = request.getParameter("email");
        LocalDate date = LocalDate.parse(dataNascita);
        //new obj persona abilitato è 1 by default in DB
        Persona persona = new Persona(codiceFiscale, nome, cognome, sesso, statoNascita, provinciaNascita, comuneNascita, date, email);

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
                    "Stato di Nascita: " + statoNascita + "\\n" +
                    "Provincia di Nascita: " + provinciaNascita + "\\n" +
                    "Comune di Nascita: " + comuneNascita + "\\n" +
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

        response.setContentType("text/html");
        writer = response.getWriter();
        String nomeTabellaPlurale = nomeTabellaPersona.substring(0, nomeTabellaPersona.length() - 1) + "i";
        writer.println("<html><head><title>Lista di " + nomeTabellaPlurale + "</title>");
        writer.println("<style>");
        writer.println("table { font-family: arial, sans-serif; border-collapse: collapse; width: 100%; margin-bottom: 20px; }");
        writer.println("td, th { border: 1px solid #dddddd; text-align: left; padding: 8px; }");
        writer.println("tr:nth-child(even) { background-color: #dddddd; }");
        writer.println(".back-button { display: flex; justify-content: center; margin: 20px 0; }");
        writer.println(".back-button button { margin: 0 5px; padding: 10px 15px; background-color: #4CAF50; color: white; border: none; cursor: pointer; }");
        writer.println(".back-button button:hover { background-color: #45a049; }");
        writer.println("</style>");
        writer.println("</head><body>");

        writer.println("<h2>Lista di " + nomeTabellaPlurale + "</h2>");
        writer.println("<table>");
        writer.println("<tr><th>CF</th><th>Nome</th><th>Cognome</th><th>Sesso</th><th>Stato nascita</th><th>Provincia nascita</th><th>Comune nascita</th><th>Data nascita</th><th>Email</th><th>Abilitato</th></tr>");
        for (Persona element : persone) {
            writer.println("<tr>");
            writer.println("<td>" + element.getCf() + "</td>");
            writer.println("<td>" + element.getNome() + "</td>");
            writer.println("<td>" + element.getCognome() + "</td>");
            writer.println("<td>" + element.getSesso() + "</td>");
            writer.println("<td>" + element.getStatoNascita() + "</td>");
            writer.println("<td>" + element.getProvinciaNascita() + "</td>");
            writer.println("<td>" + element.getComuneNascita() + "</td>");
            writer.println("<td>" + element.getDataNascita() + "</td>");
            writer.println("<td>" + element.getEmail() + "</td>");
            writer.println("<td>" + element.getAbilitato() + "</td>");
            writer.println("</tr>");
        }
        writer.println("</table>");

        // Add button to go back to welcome page
        writer.println("<div class='back-button'>");
        writer.println("<a href='" + request.getContextPath() + "/welcome.jsp'><button>Torna alla Menu</button></a>");
        writer.println("</div>");

        writer.println("</body></html>");
        writer.flush();
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
        PrintWriter writer = null;
        try {
            writer = response.getWriter();
            request.getRequestDispatcher(sNomePagina).include(request, response);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ServletException e) {
            throw new RuntimeException(e);
        }
        writer.close();
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

}