package database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import daoInterface.NewsletterDAO;
import model.Newsletter;
import model.Notice;




public class NoticeDaoJDBC {
	private DBConnection dbConnection;

	public NoticeDaoJDBC(DBConnection dbConnection) {
		this.dbConnection = dbConnection;
	}

	public void save(Notice notice) {
		
		if ( (notice.getCreatoDa() == null) ){
			throw new PersistenceException("CreatoDa non presente");
		}
		Connection connection = this.dbConnection.getConnection();
		try {
			 
			String insert = "insert into avviso(idAvviso, Stato, CreatoDa, Messaggio, idLocale, RicevutoDa, Tipo) values (?,?,?,?,?,?,?)";
			PreparedStatement statement = connection.prepareStatement(insert);
			Long id = IdBroker.getId(connection, "idAvviso", "avviso");
			statement.setLong(1, id);
			
			if(notice.getStato())
				statement.setInt(2, 1);
			else
				statement.setInt(2, 0);
			
			statement.setString(3, notice.getCreatoDa());
			statement.setString(4, notice.getMessaggio());
			statement.setLong(5, notice.getIdLocale());
			statement.setString(6, notice.getRicevutoDa());
			statement.setString(7, notice.getTipo());


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
	public Notice findByPrimaryKeyJoin(Long idAvviso) {
		
		Connection connection = this.dbConnection.getConnection();
		Notice notice = null;
		try {
			PreparedStatement statement;
			String query = "select * from avviso where idAvviso = ?";
			statement = connection.prepareStatement(query);
			statement.setLong(1, idAvviso);
			ResultSet result = statement.executeQuery();
			if (result.next()) {
				notice = new Notice();
				notice.setCreatoDa(result.getString("CreatoDa"));
				notice.setIdAvviso(Long.valueOf(result.getString("idAvviso")));
				notice.setIdLocale(Long.valueOf(result.getString("CreatoDa")));
				notice.setMessaggio(result.getString("Messaggio"));
				notice.setRicevutoDa(result.getString("RicevutoDa"));
				notice.setStato(Boolean.valueOf(result.getString("Stato")));
				notice.setTipo(result.getString("Tipo"));
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
		
		return notice;
		
	}

	public List<Notice> findByRicevutoDa(String NumeroTelefono, Boolean Stato) {
		
		Connection connection = this.dbConnection.getConnection();
		ArrayList<Notice> notices = new ArrayList<>();
		try {
			PreparedStatement statement;
			String query = "select * from avviso where RicevutoDa = ? AND Stato = ?";
			statement = connection.prepareStatement(query);
			statement.setString(1, NumeroTelefono);
			statement.setBoolean(2, Stato);
			ResultSet result = statement.executeQuery();
			while (result.next()) {

				Notice notice = new Notice();
				notice.setCreatoDa(result.getString("CreatoDa"));
				notice.setIdAvviso(Long.valueOf(result.getString("idAvviso")));
				notice.setIdLocale(Long.valueOf(result.getString("CreatoDa")));
				notice.setMessaggio(result.getString("Messaggio"));
				notice.setRicevutoDa(result.getString("RicevutoDa"));
				notice.setStato(Boolean.valueOf(result.getString("Stato")));
				notice.setTipo(result.getString("Tipo"));
				notices.add(notice);
				
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
		
		return notices;
		
	}
	
	public List<Notice> findAll() {
		
		Connection connection = this.dbConnection.getConnection();
		List<Notice> notices = new ArrayList<>();
		try {			
			Notice notice;
			PreparedStatement statement;
			String query = "select * from avviso";
			statement = connection.prepareStatement(query);
			ResultSet result = statement.executeQuery();
			while (result.next()) {
				notice = new Notice();
				notice.setCreatoDa(result.getString("CreatoDa"));
				notice.setIdAvviso(Long.valueOf(result.getString("idAvviso")));
				notice.setIdLocale(Long.valueOf(result.getString("CreatoDa")));
				notice.setMessaggio(result.getString("Messaggio"));
				notice.setRicevutoDa(result.getString("RicevutoDa"));
				notice.setStato(Boolean.valueOf(result.getString("Stato")));
				notice.setTipo(result.getString("Tipo"));
				notices.add(notice);
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
		return notices;
		
		
	}

	public void update(Notice notice) {
		
		Connection connection = this.dbConnection.getConnection();
		try {
			String update = "update avviso SET Stato = ?, CreatoDa = ?, Messaggio = ?, RicevutoDa = ?, Tipo = ?  WHERE idAvviso = ?";
			PreparedStatement statement = connection.prepareStatement(update);
			if(notice.getStato())
				statement.setInt(1, 1);
			else
				statement.setInt(1, 0);
			statement.setString(2, notice.getCreatoDa());
			statement.setString(3, notice.getMessaggio());
			//statement.setLong(4, notice.getIdLocale());
			statement.setString(4, notice.getRicevutoDa());
			statement.setString(5, notice.getTipo());
			statement.setLong(6, notice.getIdAvviso());
			//connection.setAutoCommit(false);
			//connection.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);			
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

	public void delete(Notice notice) {
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
