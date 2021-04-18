package databaseHibernate;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import daoInterface.AnalyticDAO;
import daoInterface.LogDAO;
import daoInterface.UserDAO;
import model.Analytic;
import model.Ingredient;
import model.Log;
import model.Product;
import model.Type;
import model.User;



public class TypeDaoJDBC {
	private DBConnection dbConnection;

	public TypeDaoJDBC(DBConnection dbConnection) {
		this.dbConnection = dbConnection;
	}

	public void save(Log log) {
		/*
		if ( (log.getIdLocale() == null) ){
			throw new PersistenceException("Log non memorizzato: un utente deve avere almeno un numero di telefono e una mail");
		}
		Connection connection = this.dbConnection.getConnection();
		try {
			
			String insert = "insert into log(Evento, NumeroTelefono, idLocale, DataOra) values (?,?,?,?)";
			PreparedStatement statement = connection.prepareStatement(insert);
			statement.setString(1, log.getEvento());
			statement.setString(2, log.getNumeroTelefono());
			statement.setLong(3, log.getIdLocale());

            SimpleDateFormat datetime = new SimpleDateFormat("yyyy-MM-dd HH:mm");            
			statement.setString(4, datetime.format(log.getDataOra()));
			
			
			//connection.setAutoCommit(false);
			//serve in caso gli studenti non siano stati salvati. Il DAO studente apre e chiude una transazione nuova.
			//connection.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);			
			statement.executeUpdate();
			
			
			// salviamo anche tutti gli studenti del gruppo in CASACATA
			//this.updateUser(user, connection);
			//connection.commit();
		} catch (SQLException e) {
			if (connection != null) {
				try {
					connection.rollback();
				} catch(SQLException excep) {
					throw new PersistenceException(e.getMessage());
				}
			} 
		} finally {
			try {
				connection.close();
			} catch (SQLException e) {
				throw new PersistenceException(e.getMessage());
			}
		}
		*/
	}  

	

	/* 
	 * versione con Join
	 */
	public Type findByPrimaryKeyJoin(String Tipo) {
		Connection connection = this.dbConnection.getConnection();
		Type type = null;
		try {
			PreparedStatement statement;
			String query = "select * from tipologia where Tipo=?";
					
			statement = connection.prepareStatement(query);
			statement.setString(1, Tipo);
			ResultSet result = statement.executeQuery();
			boolean primaRiga = true;
			while (result.next()) {
				if (primaRiga) {
					type = new Type(result.getString("Tipo"));
					
					primaRiga = false;
					
				}
			}
		} catch (SQLException e) {
			throw new PersistenceException(e.getMessage());
		} finally {
			try {
				connection.close();
			} catch (SQLException e) {
				throw new PersistenceException(e.getMessage());
			}
		}	
		return type;
	}
	

	public List<Type> findAll() {
		
		Connection connection = this.dbConnection.getConnection();
		List<Type> types = new ArrayList<>();
		try {			
			Type type;
			PreparedStatement statement;
			String query = "select * from tipologia ";
			statement = connection.prepareStatement(query);
			ResultSet result = statement.executeQuery();
			while (result.next()) {
				type = new Type(result.getString("Tipo"));
				
				types.add(type);
			}
		} catch (SQLException e) {
			throw new PersistenceException(e.getMessage());
		}	 finally {
			try {
				connection.close();
			} catch (SQLException e) {
				throw new PersistenceException(e.getMessage());
			}
		}
		return types;
		
		
	}

	

	public void delete(User user) {
		/*
		Connection connection = this.dataSource.getConnection();
		try {
			String delete = "delete FROM corso WHERE codice = ? ";
			PreparedStatement statement = connection.prepareStatement(delete);
			statement.setLong(1, corso.getCodice());

			/* 
			 * rimuoviamo gli studenti dal gruppo (ma non dal database) 
			 * potevano esserci soluzioni diverse (ad esempio rimuovere tutti gli studenti dal database
			 * (ma in questo caso non avrebbe senso)
			 * La scelta dipende dalla semantica delle operazioni di dominio
			 * 
			 * 
			connection.setAutoCommit(false);
			connection.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);			
			this.removeForeignKeyFromStudente(corso, connection);     			
			/* 
			 * ora rimuoviamo il gruppo
			 * 
			 * 
			statement.executeUpdate();
			connection.commit();
		} catch (SQLException e) {
			throw new PersistenceException(e.getMessage());
		} finally {
			try {
				connection.close();
			} catch (SQLException e) {
				throw new PersistenceException(e.getMessage());
			}
		}
	*/	
	}
}
