package database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import daoInterface.IngredientDAO;
import model.Ingredient;
import model.ReviewLocal;
import model.ReviewProduct;



public class ReviewLocalDaoJDBC {
	private DBConnection dbConnection;

	public ReviewLocalDaoJDBC(DBConnection dbConnection) {
		this.dbConnection = dbConnection;
	}

	public void save(ReviewLocal Review) {
		if ( (Review.getVoto() < 0 || Review.getVoto() >5 )){
			throw new PersistenceException("Recensione non memorizzata: una recensione deve avere un voto compreso tra 0 e 5");
		}
		Connection connection = this.dbConnection.getConnection();
		try {
			
			if(findByPrimaryKeyJoin(Review.getIdLocale(), Review.getNumeroTelefono()) == null)
			{

				String insert = "insert into recensionelocaleutente(idLocale, NumeroTelefono, Voto, Recensione, DataOra) values (?,?,?,?,?)";
				PreparedStatement statement = connection.prepareStatement(insert);
				statement.setLong(1, Review.getIdLocale());
				statement.setString(2, Review.getNumeroTelefono());
				statement.setInt(3, Review.getVoto());
				statement.setString(4, Review.getRecensione());
	            SimpleDateFormat datetime = new SimpleDateFormat("yyyy-MM-dd HH:mm");            
				statement.setString(5, datetime.format(Review.getDataOra()));

				//connection.setAutoCommit(false);
				//serve in caso gli studenti non siano stati salvati. Il DAO studente apre e chiude una transazione nuova.
				//connection.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);			
				statement.executeUpdate();
				// salviamo anche tutti gli studenti del gruppo in CASACATA
				//connection.commit();
			}
			else
			{
				update(Review);
			}
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

	private void updateStudenti(Ingredient corso, Connection connection) throws SQLException {
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
		*/
	}

	private void removeForeignKeyFromStudente(Ingredient corso, Connection connection) throws SQLException {
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
	public ReviewLocal findByPrimaryKeyJoin(Long idLocale, String NumeroTelefono) {
		Connection connection = this.dbConnection.getConnection();
		ReviewLocal review = null;
		try {
			PreparedStatement statement;
			String query = "SELECT idLocale, NumeroTelefono, Voto, Recensione, DataOra " + 
					"FROM recensionelocaleutente " + 
					"WHERE idLocale = ? AND NumeroTelefono = ?";
					
			statement = connection.prepareStatement(query);
			statement.setLong(1, idLocale);
			statement.setString(2, NumeroTelefono);
			ResultSet result = statement.executeQuery();
			boolean primaRiga = true;
			while (result.next()) {
				if (primaRiga) {
					review = new ReviewLocal();
					review.setIdLocale(result.getLong("idLocale"));
					review.setNumeroTelefono(result.getString("NumeroTelefono"));				
					review.setVoto(result.getInt("Voto"));
					review.setRecensione(result.getString("Recensione"));
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
		return review;
	}

	public List<Ingredient> findByProductJoin(Long id) {
		Connection connection = this.dbConnection.getConnection();
		List<Ingredient> list = null;
		Ingredient ingredient = null;
		try {
			PreparedStatement statement;
			String query = "SELECT ingrediente.idIngrediente, Nome, Costo " + 
					"FROM ingrediente, prodottiingredienti " + 
					"WHERE prodottiingredienti.idProdotto = ? AND ingrediente.idIngrediente = prodottiingredienti.idIngrediente";
					
			statement = connection.prepareStatement(query);
			statement.setLong(1, id);
			ResultSet result = statement.executeQuery();
			list = new ArrayList<>();
			while (result.next()) {				
					ingredient = new Ingredient();
					ingredient.setId(result.getLong("idIngrediente"));
					ingredient.setNome(result.getString("Nome"));				
					ingredient.setPrezzo(result.getFloat("Costo"));
					list.add(ingredient);

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
		return list;
	}

	/* 
	 * versione con Lazy Load
	 */
	public Ingredient findByPrimaryKey(Long id) {
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

	public List<Ingredient> findAll() {
		/*
		
		Connection connection = this.dbConnection.getConnection();
		List<Ingredient> restaurants = new ArrayList<>();
		try {			
			Ingredient restaurant;
			PreparedStatement statement;
			String query = "select * from locali";
			statement = connection.prepareStatement(query);
			ResultSet result = statement.executeQuery();
			while (result.next()) {
				restaurant = findByPrimaryKeyJoin(result.getLong("codice"));
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
		*/
		return null;
	}

	public void update(ReviewLocal Review) {
		
		Connection connection = this.dbConnection.getConnection();
		try {
			String update = "update recensionelocaleutente SET Voto = ?, DataOra = ?, Recensione = ? WHERE idLocale = ? AND NumeroTelefono = ?";
			PreparedStatement statement = connection.prepareStatement(update);
			statement.setLong(4, Review.getIdLocale());
			statement.setString(5, Review.getNumeroTelefono());
			statement.setInt(1, Review.getVoto());
            SimpleDateFormat datetime = new SimpleDateFormat("yyyy-MM-dd HH:mm");            
			statement.setString(2, datetime.format(Review.getDataOra()));
			statement.setString(3, Review.getRecensione());

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

	public void delete(Ingredient corso) {
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
