package database;

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
import model.User;



public class LogDaoJDBC implements LogDAO {
	private DBConnection dbConnection;

	public LogDaoJDBC(DBConnection dbConnection) {
		this.dbConnection = dbConnection;
	}

	public void save(Log log) {
		
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
		
	}  

	

	/* 
	 * versione con Join
	 */
	public User findByPrimaryKeyJoin(String NumeroTelefono) {
		Connection connection = this.dbConnection.getConnection();
		User user = null;
		try {
			PreparedStatement statement;
			String query = "SELECT utente.NumeroTelefono, Nome, Cognome, Mail, Indirizzo, Password, Amministratore, Confermato " + 
					"FROM utente, localeutente " + 
					"WHERE utente.NumeroTelefono = ? AND utente.NumeroTelefono = localeutente.NumeroTelefono AND localeutente.idLocale = 1";
					
			statement = connection.prepareStatement(query);
			statement.setString(1, NumeroTelefono);
			ResultSet result = statement.executeQuery();
			boolean primaRiga = true;
			while (result.next()) {
				if (primaRiga) {
					user = new User();
					user.setNumeroTelefono(result.getString("NumeroTelefono"));
					user.setNome(result.getString("Nome"));
					user.setCognome(result.getString("Cognome"));
					user.setMail(result.getString("Mail"));
					user.setIndirizzo(result.getString("Indirizzo"));
					user.setPassword(result.getString("Password"));
					user.setAmministratore(result.getBoolean("Amministratore"));
					user.setConfermato(result.getBoolean("Confermato"));
					
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
		return user;
	}
	
	public User findByMailJoin(String Mail) {
		
		Connection connection = this.dbConnection.getConnection();
		User user = null;
		try {
			PreparedStatement statement;
			String query = "SELECT utente.NumeroTelefono, Nome, Cognome, Mail, Indirizzo, Password, Amministratore, Confermato " + 
					"FROM utente, localeutente " + 
					"WHERE Mail = ? AND utente.NumeroTelefono = localeutente.NumeroTelefono AND localeutente.idLocale = 1";
					
			statement = connection.prepareStatement(query);
			statement.setString(1, Mail);
			ResultSet result = statement.executeQuery();
			boolean primaRiga = true;
			while (result.next()) {
				if (primaRiga) {
					user = new User();
					user.setNumeroTelefono(result.getString("NumeroTelefono"));
					user.setNome(result.getString("Nome"));
					user.setCognome(result.getString("Cognome"));
					user.setMail(result.getString("Mail"));
					user.setIndirizzo(result.getString("Indirizzo"));
					user.setPassword(result.getString("Password"));
					user.setAmministratore(result.getBoolean("Amministratore"));
					user.setConfermato(result.getBoolean("Confermato"));
					
					primaRiga = false;
					
						
				}
				//RIEMPIRE INGREDIENTI
//				if(result.getString("s_matricola")!=null){
//					Studente studente = new Studente();
//					studente.setMatricola(result.getString("s_matricola"));
//					studente.setNome(result.getString("s_nome"));
//					studente.setCognome(result.getString("s_cognome"));
//					long secs = result.getDate("s_data_nascita").getTime();
//					studente.setDataNascita(new java.util.Date(secs));
//					ScuolaDaoJDBC scuolaDao = new ScuolaDaoJDBC(dataSource);
//					studente.setScuolaDiDiploma(scuolaDao.findByPrimaryKey(result.getLong("s_scuola_id")));
//					corso.addStudente(studente);
//				}
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
		return user;
	}
	public User findByMailLocalJoin(String Mail, Long idLocal) {
		
		Connection connection = this.dbConnection.getConnection();
		User user = null;
		try {
			PreparedStatement statement;
			String query = "SELECT utente.NumeroTelefono, Nome, Cognome, Mail, Indirizzo, Password, Amministratore, Confermato " + 
					"FROM utente, localeutente " + 
					"WHERE Mail = ? AND utente.NumeroTelefono = localeutente.NumeroTelefono AND localeutente.idLocale = ?";
					
			statement = connection.prepareStatement(query);
			statement.setString(1, Mail);
			statement.setLong(2, idLocal);
			ResultSet result = statement.executeQuery();
			boolean primaRiga = true;
			while (result.next()) {
				if (primaRiga) {
					user = new User();
					user.setNumeroTelefono(result.getString("NumeroTelefono"));
					user.setNome(result.getString("Nome"));
					user.setCognome(result.getString("Cognome"));
					user.setMail(result.getString("Mail"));
					user.setIndirizzo(result.getString("Indirizzo"));
					user.setPassword(result.getString("Password"));
					user.setAmministratore(result.getBoolean("Amministratore"));
					user.setConfermato(result.getBoolean("Confermato"));
					
					primaRiga = false;
					
						
				}
				//RIEMPIRE INGREDIENTI
//				if(result.getString("s_matricola")!=null){
//					Studente studente = new Studente();
//					studente.setMatricola(result.getString("s_matricola"));
//					studente.setNome(result.getString("s_nome"));
//					studente.setCognome(result.getString("s_cognome"));
//					long secs = result.getDate("s_data_nascita").getTime();
//					studente.setDataNascita(new java.util.Date(secs));
//					ScuolaDaoJDBC scuolaDao = new ScuolaDaoJDBC(dataSource);
//					studente.setScuolaDiDiploma(scuolaDao.findByPrimaryKey(result.getLong("s_scuola_id")));
//					corso.addStudente(studente);
//				}
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
		return user;
	}
	public User findByMailTelJoin(String Mail, String NumeroTelefono) {
		Connection connection = this.dbConnection.getConnection();
		User user = null;
		try {
			PreparedStatement statement;
			String query = "SELECT utente.NumeroTelefono, Nome, Cognome, Mail, Indirizzo, Password, Amministratore, Confermato " + 
					"FROM utente, localeutente " + 
					"WHERE utente.NumeroTelefono = ? AND utente.Mail = ? AND utente.NumeroTelefono = localeutente.NumeroTelefono AND localeutente.idLocale = 1";
					
			statement = connection.prepareStatement(query);
			statement.setString(1, NumeroTelefono);
			statement.setString(2, Mail);
			ResultSet result = statement.executeQuery();
			boolean primaRiga = true;
			while (result.next()) {
				if (primaRiga) {
					user = new User();
					user.setNumeroTelefono(result.getString("NumeroTelefono"));
					user.setNome(result.getString("Nome"));
					user.setCognome(result.getString("Cognome"));
					user.setMail(result.getString("Mail"));
					user.setIndirizzo(result.getString("Indirizzo"));
					user.setPassword(result.getString("Password"));
					user.setAmministratore(result.getBoolean("Amministratore"));
					user.setConfermato(result.getBoolean("Confermato"));
					
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
		return user;
	}

	/* 
	 * versione con Lazy Load
	 */
	public User findByPrimaryKey(String NumeroTelefono) {
		/*
		Connection connection = this.dataSource.getConnection();
		Corso corso = null;
		try {
			PreparedStatement statement;
			String query = "select * from corso where codice = ?";
			statement = connection.prepareStatement(query);
			statement.setLong(1, id);
			ResultSet result = statement.executeQuery();
			if (result.next()) {
				corso = new Corso();
				corso.setCodice(result.getLong("codice"));				
				corso.setNome(result.getString("nome"));
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
		
		return corso;
		*/
		return null;
	}

	public List<Log> findAllByLocal(Long idLocale) {
		
		Connection connection = this.dbConnection.getConnection();
		List<Log> logs = new ArrayList<>();
		try {			
			Log log;
			PreparedStatement statement;
			String query = "select * from log where idLocale=?";
			statement = connection.prepareStatement(query);
			statement.setLong(1, idLocale);
			ResultSet result = statement.executeQuery();
			while (result.next()) {
				log = new Log();
				log.setIdLocale(result.getLong("idLocale"));
				log.setIdLog(result.getLong("idLog"));
				log.setNumeroTelefono(result.getString("NumeroTelefono"));
				log.setDataOra(result.getDate("DataOra"));
				log.setEvento(result.getString("Evento"));
				
				logs.add(log);
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
		return logs;
		
		
	}

	public List<Log> findAll() {
		
		Connection connection = this.dbConnection.getConnection();
		List<Analytic> analytics = new ArrayList<>();
		try {			
			Analytic analytic;
			PreparedStatement statement;
			String query = "select * from analytics ";
			statement = connection.prepareStatement(query);
			ResultSet result = statement.executeQuery();
			while (result.next()) {
				analytic = new Analytic();
				analytic.setIdLocale(result.getLong("idLocale"));
				analytic.setIdView(result.getLong("idVisita"));
				analytic.setNumeroTelefono(result.getString("NumeroTelefono"));
				analytic.setPagina(result.getString("Pagina"));
				
				analytics.add(analytic);
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
		return null;
		
		
	}

	public void update(User user) {
		
		Connection connection = this.dbConnection.getConnection();
		try {
			String update = "update utente SET Nome = ? , Cognome = ?, Indirizzo = ?, Password = ?, Amministratore = ?, Confermato = ?  WHERE NumeroTelefono = ? AND Mail = ?";
			PreparedStatement statement = connection.prepareStatement(update);
			statement.setString(1, user.getNome());
			statement.setString(2, user.getCognome());
			statement.setString(3, user.getIndirizzo());
			statement.setString(4, user.getPassword());
			statement.setBoolean(5, user.getAmministratore());
			statement.setBoolean(6, user.getConfermato());
			statement.setString(7, user.getNumeroTelefono());
			statement.setString(8, user.getMail());

			//connection.setAutoCommit(false);
			//connection.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);			
			statement.executeUpdate();
			//this.updateStudenti(corso, connection); // se abbiamo deciso di propagare gli update seguendo il riferimento
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
