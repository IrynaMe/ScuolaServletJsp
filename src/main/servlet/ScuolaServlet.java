package main.servlet;

import main.librerie.ManageDb;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ScuolaServlet extends HttpServlet {
    private ManageDb mioDB;
    private static final int RECORDS_PER_PAGE = 10;
    String usernameCorrente = null;
    String passwordCorrente = null;
    PrintWriter writer;


    /**
     * this life-cycle method is invoked when this servlet is first accessed
     * by the client
     */
    public void init(ServletConfig config) {
        System.out.println("Servlet is being initialized");
        mioDB = new ManageDb();
        boolean bRet = mioDB.Connect("localhost", 8889, "gestione_scuola", "root", "root");
        if (bRet) {
            System.out.println("********** Connessione al DB avvenuta correttamente ***********");
        } else {
            System.out.println("Non ho connesso con db");
        }
    }

    /**
     * handles HTTP GET request
     */
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String action = request.getParameter("action");
        String personType = request.getParameter("personType");
        String submitAction = request.getParameter("submitAction");
        try {
            if ("cerca".equals(submitAction)) {
                cercaPersonaPerCf(request, response, personType, true);
            } else if ("cambiaStato".equals(submitAction)) {
                cambiaStatoPersona(request, response, personType);
            } else if ("stampaLista".equals(action)) {
                stampaListaPersone(request, response, personType);
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
    public void destroy() {
        System.out.println("Servlet is being destroyed");
        writer.close();
        mioDB.disconnect();
    }

    private void login(HttpServletRequest request, HttpServletResponse response) throws IOException {
        ResultSet resultSet = null;
        //take params from form or if not-use inserted before from class properties
        if (request.getParameter("username") != null && request.getParameter("password") != null) {
            usernameCorrente = request.getParameter("username");
            passwordCorrente = request.getParameter("password");
        }
        if (usernameCorrente != null & passwordCorrente != null) {
            try {
                String sqlQuery = "SELECT * FROM utente WHERE username='" + usernameCorrente + "' AND password='" + passwordCorrente + "' AND abilitato=1";
                resultSet = mioDB.readInDb(sqlQuery);
                if (resultSet.next()) {
                    RequestDispatcher dispatcher = request.getRequestDispatcher("/welcome.html");
                    dispatcher.forward(request, response);
                } else {
                    writer = response.getWriter();
                    writer.println("<html><body><h2>Non trovo utente con " + usernameCorrente + " e password inserito!</h2>");
                    writer.println("<p>Utente NON autorizzato</p>");
                    writer.println("</body></html>");
                    writer.flush();
                }
            } catch (ServletException | SQLException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("User non definito");
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

        writer.println("window.location.href = '" + request.getContextPath() + "/welcome.html';");
        writer.println("</script>");
        writer.flush();
    }


    private void stampaListaPersone(HttpServletRequest request, HttpServletResponse response, String personType) throws IOException, ServletException {
        ArrayList<Persona> persone = new ArrayList<>();
        int page = 1;
        int recordsPerPage = RECORDS_PER_PAGE;

        if (request.getParameter("page") != null) {
            page = Integer.parseInt(request.getParameter("page"));
        }

        String nomeTabellaPersona = dammiNomeTabella(personType);


        if (nomeTabellaPersona == null) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Tipo di persona non valido");
            return;
        }

        int start = (page - 1) * recordsPerPage;
        String sqlQuery = "SELECT SQL_CALC_FOUND_ROWS * FROM " + nomeTabellaPersona + " LIMIT " + start + ", " + recordsPerPage;

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

        // Fetch the total number of records
        int totalRecords = 0;
        try (ResultSet resultSet = mioDB.readInDb("SELECT FOUND_ROWS()")) {
            if (resultSet.next()) {
                totalRecords = resultSet.getInt(1);
            }
        } catch (SQLException e) {
            throw new ServletException("Database error", e);
        }

        int totalPages = (int) Math.ceil(totalRecords * 1.0 / recordsPerPage);
        response.setContentType("text/html");
        writer = response.getWriter();
        String nomeTabellaPlurale = nomeTabellaPersona.substring(0, nomeTabellaPersona.length() - 1) + "i";
        writer.println("<html><head><title>Lista di " + nomeTabellaPlurale + "</title>");
        writer.println("<style>");
        writer.println("table { font-family: arial, sans-serif; border-collapse: collapse; width: 100%; margin-bottom: 20px; }");
        writer.println("td, th { border: 1px solid #dddddd; text-align: left; padding: 8px; }");
        writer.println("tr:nth-child(even) { background-color: #dddddd; }");
        writer.println(".pagination, .back-button { display: flex; justify-content: center; margin: 20px 0; }");
        writer.println(".pagination button, .back-button button { margin: 0 5px; padding: 10px 15px; background-color: #4CAF50; color: white; border: none; cursor: pointer; }");
        writer.println(".pagination button:hover, .back-button button:hover { background-color: #45a049; }");
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

        // Add pagination controls
        writer.println("<div class='pagination'>");
        if (page > 1) {
            writer.println("<a href='ScuolaServlet?action=stampaLista&personType=" + personType + "&page=" + (page - 1) + "'><button>Previous</button></a>");
        }
        for (int i = 1; i <= totalPages; i++) {
            if (i == page) {
                writer.println("<span><button disabled>" + i + "</button></span>");
            } else {
                writer.println("<a href='ScuolaServlet?action=stampaLista&personType=" + personType + "&page=" + i + "'><button>" + i + "</button></a>");
            }
        }
        if (page < totalPages) {
            writer.println("<a href='ScuolaServlet?action=stampaLista&personType=" + personType + "&page=" + (page + 1) + "'><button>Next</button></a>");
        }
        writer.println("</div>");

        // Add button to go back to welcome page
        writer.println("<div class='back-button'>");
        writer.println("<a href='" + request.getContextPath() + "/welcome.html'><button>Torna alla Menu</button></a>");
        writer.println("</div>");

        writer.println("</body></html>");
        writer.flush();
    }

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
                if (isRedirectNeeded) {
                    writer.println("window.location.href = '" + request.getContextPath() + "/welcome.html';");
                }
                writer.println("</script>");
            } else {
                // Persona non trovata
                writer.println("<script>");
                writer.println("alert('Persona con CF " + cf + " non trovata!');");
                if (isRedirectNeeded) {
                    writer.println("window.location.href = '" + request.getContextPath() + "/welcome.html';");
                }
                writer.println("</script>");

            }
        } catch (SQLException e) {
            throw new ServletException("Database error", e);
        }
        writer.flush();
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
            writer.println("window.location.href = '" + request.getContextPath() + "/welcome.html';");
            writer.println("</script>");
            writer.flush();
        } else {
            writer.println("<script>");
            writer.println("alert('Errore nel cambiare lo stato della persona.');");
            writer.println("window.location.href = '" + request.getContextPath() + "/welcome.html';");
            writer.println("</script>");
            writer.flush();
        }
    }



}