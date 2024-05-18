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
    String paramUsername = null;
    String paramPassword = null;

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
        String personType = request.getParameter("personType"); // Extracting personType
        try {
            if ("cercaPersona".equals(action)) {
                cercaPersonaPerCf(request, response, personType); // Passing personType to cercaPersonaPerCf
            } else if ("stampaLista".equals(action)) {
                stampaListaPersone(request, response, personType); // Passing personType to stampaListaPersone
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
        mioDB.disconnect();
    }

    private void login(HttpServletRequest request, HttpServletResponse response )throws IOException {
        ResultSet resultSet = null;
        //take params from form or if not-use inserted before from class properties
        if (request.getParameter("username")!=null&&request.getParameter("password")!=null){
            paramUsername = request.getParameter("username");
            paramPassword = request.getParameter("password");
        }
        try {
            String sqlQuery = "SELECT * FROM utente WHERE username='" + paramUsername + "' AND password='" + paramPassword + "' AND abilitato=1";
            resultSet = mioDB.readInDb(sqlQuery);
            if (resultSet.next()) {
                RequestDispatcher dispatcher = request.getRequestDispatcher("/welcome.html");
                dispatcher.forward(request, response);
            } else {
                PrintWriter writer = response.getWriter();
                writer.println("<html><body><h2>Non trovo utente con " + paramUsername + " e password inserito!</h2>");
                writer.println("<p>Utente NON autorizzato</p>");
                writer.println("</body></html>");
                writer.flush();
            }
        } catch (ServletException | SQLException e) {
            e.printStackTrace();
        }
    }
    private String dammiNomeTabella(String personType) {
        switch (personType) {
            case "allievo":
                return "allievo";
            case "docente":
                return "docente";
            case "amministrativo":
                return "amministrativo";
            default:
                return null;
        }
    }
    private void inserimentoPersona(HttpServletRequest request, HttpServletResponse response)throws IOException {
        String personType = request.getParameter("personType");
        String nomeTabella = dammiNomeTabella(personType);

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

        Persona persona = new Persona(codiceFiscale, nome, cognome, sesso, statoNascita, provinciaNascita, comuneNascita, date, email);

        String sqlInsert = "INSERT INTO " + nomeTabella + " (cf, nome, cognome, sesso, stato_nascita, provincia_nascita, comune_nascita, data_nascita, email) VALUES ('"
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

        PrintWriter writer = response.getWriter();
        String welcomeUrl = request.getContextPath() + "/welcome.html";
        writer.println("<script>");
        if (isInserted) {
            writer.println("alert('Nuovo " + nomeTabella + " inserito con successo!\\n" +
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
            writer.println("alert('Errore di inserimento di un nuovo " + nomeTabella + " in database!');");
        }
        writer.println("window.location.href = '" + welcomeUrl + "';");
        writer.println("</script>");
        writer.close();
    }

    private Persona cercaPersonaPerCf(HttpServletRequest request, HttpServletResponse response, String personType) throws IOException, ServletException {
        // String personType = request.getParameter("personType");
        String cf = request.getParameter("cf");  // Assuming cf is passed as a URL parameter

        String nomeTabella = dammiNomeTabella(personType);
        if (nomeTabella == null) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid person type.");
            return null; // Return null indicating failure
        }

        String sqlQuery = "SELECT * FROM " + nomeTabella + " WHERE cf = '" + cf + "'";
        try (ResultSet resultSet = mioDB.readInDb(sqlQuery)) {
            String welcomeUrl = request.getContextPath() + "/welcome.html";
            if (resultSet.next()) {
                // Fetching persona details
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

                // Printing persona details as an alert message
                PrintWriter writer = response.getWriter();
                writer.println("<script>");
                writer.println("alert('Dettagli " + nomeTabella + ":\\n" +
                        "Codice Fiscale: " + persona.getCf() + "\\n" +
                        "Nome: " + persona.getNome() + "\\n" +
                        "Cognome: " + persona.getCognome() + "\\n" +
                        "Sesso: " + persona.getSesso() + "\\n" +
                        "Stato di Nascita: " + persona.getStatoNascita() + "\\n" +
                        "Provincia di Nascita: " + persona.getProvinciaNascita() + "\\n" +
                        "Comune di Nascita: " + persona.getComuneNascita() + "\\n" +
                        "Data di Nascita: " + persona.getDataNascita() + "\\n" +
                        "Email: " + persona.getEmail() + "');");
                writer.println("window.location.href = '" + welcomeUrl + "';");
                writer.println("</script>");

                return persona; // Return the fetched Persona object
            } else {
                // No persona found
                PrintWriter writer = response.getWriter();
                writer.println("<script>alert('Persona con CF " + cf + " non trovata!');");
                writer.println("window.location.href = '" + welcomeUrl + "';");
                writer.println("</script>");
                return null; // Return null indicating failure
            }
        } catch (SQLException e) {
            throw new ServletException("Database error", e);
        }
    }
    private void stampaListaPersone(HttpServletRequest request, HttpServletResponse response, String personType) throws IOException, ServletException {
        ArrayList<Persona> persone = new ArrayList<>();
        int page = 1;
        int recordsPerPage = RECORDS_PER_PAGE;

        if (request.getParameter("page") != null) {
            page = Integer.parseInt(request.getParameter("page"));
        }

        String nomeTabella = dammiNomeTabella(personType);
        if (nomeTabella == null) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid person type.");
            return;
        }

        int start = (page - 1) * recordsPerPage;
        String sqlQuery = "SELECT SQL_CALC_FOUND_ROWS * FROM " + nomeTabella + " LIMIT " + start + ", " + recordsPerPage;

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
            throw new ServletException("Database error", e);
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

        PrintWriter writer = response.getWriter();
        String nomeTabellaPlurale=nomeTabella.substring(0,nomeTabella.length()-1)+"i";
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
    }









}