package main.librerie;

import java.io.*;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class GestioneFile {
  //  String relativePath = "queries.sql";
    ManageDb manageDb = new ManageDb();
    ArrayList<String> queriesToInsert;


    public GestioneFile() {

        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
        // imposto orario
       // scheduler.scheduleAtFixedRate(this::runPeriodicTask, 0, 1, TimeUnit.HOURS);
        scheduler.scheduleAtFixedRate(this::runPeriodicTask, 0, 1, TimeUnit.MINUTES);
    }

    public void scriviQueryInFile(String filePath, String query) {
        //try-with-resources uatomaticamente chiude writer
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, true))) {
            writer.write(query);
            writer.newLine();
            System.out.println("i dati salvati sul file");
        } catch (IOException e) {
            System.out.println("errore di scrittura sul file: " + e.getMessage());
        }
    }
    private void runPeriodicTask() {
        // controllo connessione
        Connection connection = manageDb.Connect();
        if (connection != null) {
            // leggo queries da file
            InputStream inputStream = getClass().getClassLoader().getResourceAsStream("queries.sql");
            if (inputStream != null) {
                try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
                    //leggo queries da input stream
                    queriesToInsert = new ArrayList<>();
                    String line;
                    while ((line = reader.readLine()) != null) {
                        queriesToInsert.add(line);
                    }

                    // Insert in database
                    int rowsAffected = insertDataAndDelete(connection, queriesToInsert);
                    // aggiorno file
                    if(queriesToInsert!=null) updateFile("queries.sql", queriesToInsert);
                } catch (IOException e) {
                    System.out.println("Errore di lettura da file: " + e.getMessage());
                }
            } else {
                System.out.println("File non trovato");
            }
        } else {
            System.out.println("errore di connessione al db");
        }
    }


    // rescrivo in file queries non inseriti
    public void updateFile(String filePath, ArrayList<String> queriesToInsert) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            for (String query : queriesToInsert) {
                writer.write(query);
                writer.newLine();
            }
            System.out.println("il file è aggiornato");
        } catch (IOException e) {
            System.out.println("errore di aggiornamento del file: " + e.getMessage());
        }
    }



    public ArrayList<String> readQueriesFromFile(String filePath) {
        ArrayList<String> queriesToInsert = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                queriesToInsert.add(line);
            }
        } catch (IOException e) {
            System.out.println("Errore di lettura file: " + e.getMessage());
        }
        return queriesToInsert;
    }

    public int insertDataAndDelete(Connection connection, List<String> queries) {
        int rowsAffected = 0;
        Iterator<String> iterator = queries.iterator();
        try (Statement statement = connection.createStatement()) {
            while (iterator.hasNext()) {
                String query = iterator.next();
                try {
                    int rows = statement.executeUpdate(query);
                    rowsAffected += rows;
                    if (rows > 0) {
                        iterator.remove();
                    }
                } catch (SQLException e) {
                    // controllo che contiene message e
                    if (isConnectionError(e)) {
                        System.out.println(e);
                        break;
                    } else {
                        System.out.println(e);
                        continue;
                    }
                }
            }
        } catch (SQLException e) {

            System.out.println("Errore di connessione: " + e.getMessage());
        }
        return rowsAffected;
    }

    // Method to check if the SQL exception is a transient error
    private boolean isConnectionError(SQLException e) {
        String sqlState = e.getSQLState();
      //  int errorCode = e.getErrorCode();

        // SQL state starting with "08" indicates connection error for many databases
        return sqlState != null && sqlState.startsWith("08");
    }}