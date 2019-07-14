package database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import daoInterface.UserDAO;
import model.Ingredient;
import model.Product;
import model.User;



public class UserDaoJDBC implements UserDAO {
	private DBConnection dbConnection;

	public UserDaoJDBC(DBConnection dbConnection) {
		this.dbConnection = dbConnection;
	}

	public void save(User user) {
		
		if ( (user.getNumeroTelefono() == null) 
				|| user.getMail() == null){
			throw new PersistenceException("Utente non memorizzato: un utente deve avere almeno un numero di telefono e una mail");
		}
		Connection connection = this.dbConnection.getConnection();
		try {
			
			String insert = "insert into utente(NumeroTelefono, Nome, Cognome, Mail, Indirizzo, Password, Amministratore, Confermato, Disabilitato) values (?,?,?,?,?,?,?,?, ?)";
			PreparedStatement statement = connection.prepareStatement(insert);
			statement.setString(1, user.getNumeroTelefono());
			statement.setString(2, user.getNome());
			statement.setString(3, user.getCognome());
			statement.setString(4, user.getMail());
			statement.setString(5, user.getIndirizzo());
			statement.setString(6, user.getPassword());
			statement.setBoolean(7, user.getAmministratore());
			statement.setBoolean(8, user.getConfermato());
			statement.setBoolean(9, user.getDisabilitato());
			//connection.setAutoCommit(false);
			//serve in caso gli studenti non siano stati salvati. Il DAO studente apre e chiude una transazione nuova.
			//connection.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);			
			statement.executeUpdate();
			
			insert = "insert into localeutente(idLocale, NumeroTelefono) values (?, ?)";
			statement = connection.prepareStatement(insert);
			statement.setInt(1, 1);
			statement.setString(2, user.getNumeroTelefono());
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

	private void updateUser(User user, Connection connection) throws SQLException {
		/*
		StudenteDao studenteDao = new StudenteDaoJDBC(dataSource);
		for (Studente studente : corso.getStudenti()) {
			if (studenteDao.findByPrimaryKey(studente.getMatricola()) == null){
				studenteDao.save(studente);
			}
			
			String iscritto = "select id from iscritto where matricola_studente=? AND corso_codice=?";
			PreparedStatement statementIscritto = connection.prepareStatement(iscritto);
			statementIscritto.setString(1, studente.getMatricola());
			statementIscritto.setLong(2, corso.getCodice());
			ResultSet result = statementIscritto.executeQuery();
			if(result.next()){
				String update = "update studente SET corso_codice = ? WHERE id = ?";
				PreparedStatement statement = connection.prepareStatement(update);
				statement.setLong(1, corso.getCodice());
				statement.setLong(2, result.getLong("id"));
				statement.executeUpdate();
			}else{			
				String iscrivi = "insert into iscritto(id, matricola_studente, corso_codice) values (?,?,?)";
				PreparedStatement statementIscrivi = connection.prepareStatement(iscrivi);
				Long id = IdBroker.getId(connection);
				statementIscrivi.setLong(1, id);
				statementIscrivi.setString(2, studente.getMatricola());
				statementIscrivi.setLong(3, corso.getCodice());
				statementIscrivi.executeUpdate();
			}
		}
		
	}

	private void removeForeignKeyFromStudente(User user, Connection connection) throws SQLException {
		/*
		for (Studente studente : corso.getStudenti()) {
			String update = "update iscritto SET corso_codice = NULL WHERE matricola_studente = ?";
			PreparedStatement statement = connection.prepareStatement(update);
			statement.setString(1, studente.getMatricola());
			statement.executeUpdate();
		}
		*/	
	}

	/* 
	 * versione con Join
	 */
	public User findByPrimaryKeyJoin(String NumeroTelefono) {
		Connection connection = this.dbConnection.getConnection();
		User user = null;
		try {
			PreparedStatement statement;
			String query = "SELECT utente.NumeroTelefono, Nome, Cognome, Mail, Indirizzo, Password, Amministratore, Confermato, Disabilitato " + 
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
					user.setDisabilitato(result.getBoolean("Disabilitato"));
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
			String query = "SELECT utente.NumeroTelefono, Nome, Cognome, Mail, Indirizzo, Password, Amministratore, Confermato, Disabilitato " + 
					"FROM utente, localeutente " + 
					"WHERE Mail = ? AND utente.NumeroTelefono = localeutente.NumeroTelefono AND localeutente.idLocale = 1 AND Disabilitato=0";
					
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
					user.setDisabilitato(result.getBoolean("Disabilitato"));

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
			String query = "SELECT utente.NumeroTelefono, Nome, Cognome, Mail, Indirizzo, Password, Amministratore, Confermato, Disabilitato " + 
					"FROM utente, localeutente " + 
					"WHERE Mail = ? AND utente.NumeroTelefono = localeutente.NumeroTelefono AND localeutente.idLocale = ? AND Disabilitato=0";
					
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
					user.setDisabilitato(result.getBoolean("Disabilitato"));

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
			String query = "SELECT utente.NumeroTelefono, Nome, Cognome, Mail, Indirizzo, Password, Amministratore, Confermato, Disabilitato " + 
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
					user.setDisabilitato(result.getBoolean("Disabilitato"));

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

	public List<User> findAllByLocal(Long idLocale) {
		
		Connection connection = this.dbConnection.getConnection();
		List<User> users = new ArrayList<>();
		try {			
			User user;
			PreparedStatement statement;
			String query = "select utente.NumeroTelefono, Nome, Cognome, Mail, Indirizzo, Password, Amministratore, Confermato, Disabilitato, localeutente.idLocale from utente, localeutente where utente.NumeroTelefono=localeutente.NumeroTelefono AND localeutente.idLocale=? AND Disabilitato=0";
			statement = connection.prepareStatement(query);
			statement.setLong(1, idLocale);
			ResultSet result = statement.executeQuery();
			while (result.next()) {
				user = new User();
				user.setNumeroTelefono(result.getString("NumeroTelefono"));
				user.setNome(result.getString("Nome"));
				user.setCognome(result.getString("Cognome"));
				user.setMail(result.getString("Mail"));
				user.setIndirizzo(result.getString("Indirizzo"));
				user.setPassword(result.getString("Password"));
				user.setAmministratore(result.getBoolean("Amministratore"));
				user.setConfermato(result.getBoolean("Confermato"));
				user.setIdLocale(result.getLong("idLocale"));
				user.setDisabilitato(result.getBoolean("Disabilitato"));

				users.add(user);
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
		return users;
		
		
	}

	public List<User> findAllByLocalConfirm(Long idLocale, boolean Confirm) {
		
		Connection connection = this.dbConnection.getConnection();
		List<User> users = new ArrayList<>();
		try {			
			User user;
			PreparedStatement statement;
			String query = "select utente.NumeroTelefono, Nome, Cognome, Mail, Indirizzo, Password, Amministratore, Confermato, Disabilitato, localeutente.idLocale from utente, localeutente where utente.NumeroTelefono=localeutente.NumeroTelefono AND localeutente.idLocale=? AND utente.Confermato = ? AND Disabilitato=0";
			statement = connection.prepareStatement(query);
			statement.setLong(1, idLocale);
			statement.setBoolean(2, Confirm);
			ResultSet result = statement.executeQuery();
			while (result.next()) {
				user = new User();
				user.setNumeroTelefono(result.getString("NumeroTelefono"));
				user.setNome(result.getString("Nome"));
				user.setCognome(result.getString("Cognome"));
				user.setMail(result.getString("Mail"));
				user.setIndirizzo(result.getString("Indirizzo"));
				user.setPassword(result.getString("Password"));
				user.setAmministratore(result.getBoolean("Amministratore"));
				user.setConfermato(result.getBoolean("Confermato"));
				user.setIdLocale(result.getLong("idLocale"));
				user.setDisabilitato(result.getBoolean("Disabilitato"));

				users.add(user);
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
		return users;
		
		
	}
	
	public List<User> findAll() {
		
		Connection connection = this.dbConnection.getConnection();
		List<User> users = new ArrayList<>();
		try {			
			User user;
			PreparedStatement statement;
			String query = "select utente.NumeroTelefono, Nome, Cognome, Mail, Indirizzo, Password, Amministratore, Confermato Disabilitato from utente, localeutente where utente.NumeroTelefono=localeutente.NumeroTelefono AND localeutente.idLocale=1 AND Disabilitato=0";
			statement = connection.prepareStatement(query);
			ResultSet result = statement.executeQuery();
			while (result.next()) {
				user = new User();
				user.setNumeroTelefono(result.getString("NumeroTelefono"));
				user.setNome(result.getString("Nome"));
				user.setCognome(result.getString("Cognome"));
				user.setMail(result.getString("Mail"));
				user.setIndirizzo(result.getString("Indirizzo"));
				user.setPassword(result.getString("Password"));
				user.setAmministratore(result.getBoolean("Amministratore"));
				user.setConfermato(result.getBoolean("Confermato"));
				user.setDisabilitato(result.getBoolean("Disabilitato"));

				users.add(user);
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
		return users;
		
		
	}

	public void update(User user) {
		
		Connection connection = this.dbConnection.getConnection();
		try {
			String update = "update utente SET Nome = ? , Cognome = ?, Indirizzo = ?, Password = ?, Amministratore = ?, Confermato = ?, Disabilitato = ? WHERE NumeroTelefono = ? AND Mail = ?";
			PreparedStatement statement = connection.prepareStatement(update);
			statement.setString(1, user.getNome());
			statement.setString(2, user.getCognome());
			statement.setString(3, user.getIndirizzo());
			statement.setString(4, user.getPassword());
			statement.setBoolean(5, user.getAmministratore());
			statement.setBoolean(6, user.getConfermato());
			statement.setBoolean(7, user.getDisabilitato());
			statement.setString(8, user.getNumeroTelefono());
			statement.setString(9, user.getMail());

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
