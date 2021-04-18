package databaseHibernate;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import daoInterface.RestaurantDAO;
import model.Restaurant;



public class RestaurantDaoJDBC implements RestaurantDAO {
	private DBConnection dbConnection;

	public RestaurantDaoJDBC(DBConnection dbConnection) {
		this.dbConnection = dbConnection;
	}

	public void save(Restaurant Rest) {
		
		
		Connection connection = this.dbConnection.getConnection();
		try {
			//Long id = IdBroker.getId(connection, "idLocale", "locale");
			//Rest.setId(id);
			String insert = "insert into locale(idLocale, Nome, Indirizzo, NumeroTelefono, Mail, logoURL, backgroundURL, Attivo) values (?,?,?,?,?,?,?,?)";
			PreparedStatement statement = connection.prepareStatement(insert);
			statement.setLong(1, Rest.getId());
			statement.setString(2, Rest.getName());
			statement.setString(3, Rest.getAddress());
			statement.setString(4, Rest.getTelephone());
			statement.setString(5, Rest.getMail());
			statement.setString(6, Rest.getLogoURL());
			statement.setString(7, Rest.getBackgroundURL());
			statement.setBoolean(8, Rest.getActive());

			//connection.setAutoCommit(false);
			//serve in caso gli studenti non siano stati salvati. Il DAO studente apre e chiude una transazione nuova.
			//connection.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);			
			statement.executeUpdate();
			// salviamo anche tutti gli studenti del gruppo in CASACATA
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
	public Restaurant findByPrimaryKeyJoin(Long id) {
		Connection connection = this.dbConnection.getConnection();
		Restaurant restaurant = null;
		try {
			PreparedStatement statement;
			String query = "SELECT idLocale, Nome, Indirizzo, Mail, NumeroTelefono, logoURL, backgroundURL, Attivo " + 
					"FROM locale " + 
					"WHERE idLocale = ?";
					
			statement = connection.prepareStatement(query);
			statement.setLong(1, id);
			ResultSet result = statement.executeQuery();
			boolean primaRiga = true;
			while (result.next()) {
				if (primaRiga) {
					restaurant = new Restaurant();
					restaurant.setAddress(result.getString("Indirizzo"));
					restaurant.setId(result.getLong("idLocale"));				
					restaurant.setName(result.getString("Nome"));
					restaurant.setMail(result.getString("Mail"));
					restaurant.setTelephone(result.getString("NumeroTelefono"));
					restaurant.setLogoURL(result.getString("logoURL"));
					restaurant.setBackgroundURL(result.getString("backgroundURL"));
					restaurant.setActive(result.getBoolean("Attivo"));
					primaRiga = false;
				}
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
		return restaurant;
	}

	public Restaurant findByMailJoin(String Mail) {
		Connection connection = this.dbConnection.getConnection();
		Restaurant restaurant = null;
		try {
			PreparedStatement statement;
			String query = "SELECT idLocale, Nome, Indirizzo, Mail, NumeroTelefono, logoURL, backgroundURL, Attivo " + 
					"FROM locale " + 
					"WHERE Mail = ?";
					
			statement = connection.prepareStatement(query);
			statement.setString(1, Mail);
			ResultSet result = statement.executeQuery();
			boolean primaRiga = true;
			while (result.next()) {
				if (primaRiga) {
					restaurant = new Restaurant();
					restaurant.setAddress(result.getString("Indirizzo"));
					restaurant.setId(result.getLong("idLocale"));				
					restaurant.setName(result.getString("Nome"));
					restaurant.setMail(result.getString("Mail"));
					restaurant.setTelephone(result.getString("NumeroTelefono"));
					restaurant.setLogoURL(result.getString("logoURL"));
					restaurant.setBackgroundURL(result.getString("backgroundURL"));
					restaurant.setActive(result.getBoolean("Attivo"));
					primaRiga = false;
				}
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
		return restaurant;
	}


	/* 
	 * versione con Lazy Load
	 */
	public Restaurant findByPrimaryKey(Long id) {
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

	public List<Restaurant> findAll() {
		
		Connection connection = this.dbConnection.getConnection();
		List<Restaurant> restaurants = new ArrayList<>();
		try {			
			Restaurant restaurant;
			PreparedStatement statement;
			String query = "select * from locale";
			statement = connection.prepareStatement(query);
			ResultSet result = statement.executeQuery();
			while (result.next()) {
				restaurant = new Restaurant();
				restaurant.setId(result.getLong("idLocale"));
				restaurant.setAddress(result.getString("Indirizzo"));
				restaurant.setLogoURL(result.getString("logoURL"));
				restaurant.setBackgroundURL(result.getString("backgroundURL"));
				restaurant.setMail(result.getString("Mail"));
				restaurant.setName(result.getString("Nome"));
				restaurant.setTelephone(result.getString("NumeroTelefono"));
				restaurant.setActive(result.getBoolean("Attivo"));
				restaurants.add(restaurant);
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
		return restaurants;
		
		
	}

	public void update(Restaurant Rest) {
		
		Connection connection = this.dbConnection.getConnection();
		try {
			String update = "update locale SET Nome = ?, Indirizzo = ?, NumeroTelefono = ?, Mail = ?, logoURL = ?, backgroundURL = ?, Attivo = ? WHERE idLocale = ?";
			PreparedStatement statement = connection.prepareStatement(update);
			statement.setString(1, Rest.getName());
			statement.setString(2, Rest.getAddress());
			statement.setString(3, Rest.getTelephone());
			statement.setString(4, Rest.getMail());
			statement.setString(5, Rest.getLogoURL());
			statement.setString(6, Rest.getBackgroundURL());
			statement.setBoolean(7, Rest.getActive());
			statement.setLong(8, Rest.getId());

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

	public void delete(Restaurant corso) {
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
