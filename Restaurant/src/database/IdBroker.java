package database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class IdBroker {

	// Standard SQL (queste stringhe andrebbero scritte in un file di configurazione
	// private static final String query = "SELECT NEXT VALUE FOR SEQ_ID AS id";
	// postgresql
	private static String query = "SELECT AUTO_INCREMENT " + 
			"From information_schema.TABLES " + 
			"WHERE TABLE_SCHEMA = 'ristorante' " + 
			"AND TABLE_NAME = ";

	public static Long getId(Connection connection, String idColumn, String DBTable) {
		Long id = null;
		query = "SELECT MAX("+ idColumn +") as id From "+ DBTable;
		System.out.println(query);
		try {
			PreparedStatement statement = connection.prepareStatement(query);
			ResultSet result = statement.executeQuery();
			result.next();
			id = result.getLong("id") + 1;
		} catch (SQLException e) {
			throw new PersistenceException(e.getMessage());
		}
		return id;
	}
}