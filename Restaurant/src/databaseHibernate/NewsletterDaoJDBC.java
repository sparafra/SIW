package databaseHibernate;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import daoInterface.NewsletterDAO;
import model.Newsletter;




public class NewsletterDaoJDBC implements NewsletterDAO {
	private DBConnection dbConnection;

	public NewsletterDaoJDBC(DBConnection dbConnection) {
		this.dbConnection = dbConnection;
	}

	public void save(Newsletter newsletter) {
		
		if ( (newsletter.getMail() == null) ){
			throw new PersistenceException("Mail non presente");
		}
		Connection connection = this.dbConnection.getConnection();
		try {
			 
			String insert = "insert into newsletter(Mail, idLocale) values (?, ?)";
			PreparedStatement statement = connection.prepareStatement(insert);
			statement.setString(1, newsletter.getMail());
			statement.setLong(2, newsletter.getIdLocale());

			//connection.setAutoCommit(false);
			//serve in caso gli studenti non siano stati salvati. Il DAO studente apre e chiude una transazione nuova.
			//connection.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);			
			statement.executeUpdate();
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
		
	}  


	/* 
	 * versione con Lazy Load
	 */
	public Newsletter findByPrimaryKey(String mail) {
		
		Connection connection = this.dbConnection.getConnection();
		Newsletter newsletter = null;
		try {
			PreparedStatement statement;
			String query = "select * from newsletter where Mail = ?";
			statement = connection.prepareStatement(query);
			statement.setString(1, mail);
			ResultSet result = statement.executeQuery();
			if (result.next()) {
				newsletter = new Newsletter(result.getString("Mail"), result.getLong("idLocale"));
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
		
		return newsletter;
		
	}

	public List<Newsletter> findAll() {
		
		Connection connection = this.dbConnection.getConnection();
		List<Newsletter> newsletters = new ArrayList<>();
		try {			
			Newsletter newsletter;
			PreparedStatement statement;
			String query = "select * from newsletter";
			statement = connection.prepareStatement(query);
			ResultSet result = statement.executeQuery();
			while (result.next()) {
				newsletter = new Newsletter(result.getString("Mail"), result.getLong("idLocale"));
				newsletters.add(newsletter);
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
		return newsletters;
		
		
	}

	public void update(Newsletter product) {
		/*
		Connection connection = this.dataSource.getConnection();
		try {
			String update = "update corso SET nome = ? WHERE codice = ?";
			PreparedStatement statement = connection.prepareStatement(update);
			statement.setString(1, corso.getNome());
			statement.setLong(2, corso.getCodice());

			//connection.setAutoCommit(false);
			//connection.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);			
			statement.executeUpdate();
			this.updateStudenti(corso, connection); // se abbiamo deciso di propagare gli update seguendo il riferimento
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

	public void delete(Newsletter product) {
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
