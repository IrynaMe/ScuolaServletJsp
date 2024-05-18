package main.librerie;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.sql.Statement;

public class ManageDb {
	public Connection myConn;
	Statement statement;
	ResultSet resultSet;


	public boolean Connect(String sIpServer, int iPort, String sNomeDb, String sNomeUtente, String sPass)
	{
		String sConnectString;

		sConnectString = "jdbc:mysql://" + sIpServer + ":" + iPort + "/" + sNomeDb +
				"?user=" + sNomeUtente + "&password=" + sPass + "&serverTimezone=Europe/Amsterdam";
		try {
			myConn = DriverManager.getConnection(sConnectString);
			return true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

	public boolean writeInDb(String sSqlQuery) {
		boolean isExecuted = false;
		try {
			statement = myConn.createStatement();
			int rowsAffected = statement.executeUpdate(sSqlQuery);
			isExecuted = rowsAffected > 0;
			System.out.println("Inserito in database con successo");
		} catch (SQLException e) {
			System.out.println("Inserimento in db non è andato con successo: " + e);
		}
		return isExecuted;
	}


	public ResultSet readInDb(String sSqlQuery) {
		try {
			statement = myConn.createStatement();
			resultSet = statement.executeQuery(sSqlQuery);
			if (resultSet == null) System.out.println("Non ci sono records con i parametri cercati");
		} catch (SQLException e) {
			System.out.println("Tentativo di leggere in db non è andato con successo" + e);
		}
		return resultSet;
	}

	public boolean disconnect() {
		try {
			myConn.close();
			System.out.println("Connessione con db è chiusa");
			return true;
		} catch (SQLException e) {
			System.out.println("Non posso chiudere connessione db" + e);
		}
		return false;
	}
	
	
}
