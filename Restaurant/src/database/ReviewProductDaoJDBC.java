package database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import daoInterface.ReviewProductDAO;
import model.Ingredient;
import model.ReviewProduct;



public class ReviewProductDaoJDBC implements ReviewProductDAO {
	private DBConnection dbConnection;

	public ReviewProductDaoJDBC(DBConnection dbConnection) {
		this.dbConnection = dbConnection;
	}

	public void save(ReviewProduct Review) {
		
		if ( (Review.getVoto() < 0 || Review.getVoto() >5 )){
			throw new PersistenceException("Recensione non memorizzata: una recensione deve avere un voto compreso tra 0 e 5");
		}
		Connection connection = this.dbConnection.getConnection();
		try {
			
			if(findByPrimaryKeyJoin(Review.getIdProduct(), Review.getNumeroTelefono()) == null)
			{

				String insert = "insert into recensioneprodottoutente(idProdotto, NumeroTelefono, Voto, DataOra) values (?,?,?,?)";
				PreparedStatement statement = connection.prepareStatement(insert);
				statement.setLong(1, Review.getIdProduct());
				statement.setString(2, Review.getNumeroTelefono());
				statement.setInt(3, Review.getVoto());
	            SimpleDateFormat datetime = new SimpleDateFormat("yyyy-MM-dd HH:mm");            
				statement.setString(4, datetime.format(Review.getDataOra()));

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
	public ReviewProduct findByPrimaryKeyJoin(Long idProdotto, String NumeroTelefono) {
		Connection connection = this.dbConnection.getConnection();
		ReviewProduct review = null;
		try {
			PreparedStatement statement;
			String query = "SELECT idProdotto, NumeroTelefono, Voto, DataOra " + 
					"FROM recensioneprodottoutente " + 
					"WHERE idProdotto = ? AND NumeroTelefono = ?";
					
			statement = connection.prepareStatement(query);
			statement.setLong(1, idProdotto);
			statement.setString(2, NumeroTelefono);
			ResultSet result = statement.executeQuery();
			boolean primaRiga = true;
			while (result.next()) {
				if (primaRiga) {
					review = new ReviewProduct();
					review.setIdProduct(result.getLong("idProdotto"));
					review.setNumeroTelefono(result.getString("NumeroTelefono"));				
					review.setVoto(result.getInt("Voto"));
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

	public List<ReviewProduct> findByProductJoin(Long idProdotto) {
		Connection connection = this.dbConnection.getConnection();
		List<ReviewProduct> list = null;
		ReviewProduct review = null;
		try {
			PreparedStatement statement;
			String query = "SELECT idProdotto, NumeroTelefono, Voto, DataOra " + 
					"FROM recensioneprodottoutente " + 
					"WHERE idProdotto = ?";
					
			statement = connection.prepareStatement(query);
			statement.setLong(1, idProdotto);
			ResultSet result = statement.executeQuery();
			list = new ArrayList<>();
			while (result.next()) {				
					review = new ReviewProduct();
					review.setIdProduct(result.getLong("idProdotto"));
					review.setNumeroTelefono(result.getString("NumeroTelefono"));				
					review.setVoto(result.getInt("Voto"));
					review.setDataOra(result.getDate("DataOra"));
					
					list.add(review);

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

	public List<ReviewProduct> findAll() {
		
		Connection connection = this.dbConnection.getConnection();
		List<ReviewProduct> list = new ArrayList<>();
		try {			
			ReviewProduct review;
			PreparedStatement statement;
			String query = "select * from recensioneprodottoutente";
			statement = connection.prepareStatement(query);
			ResultSet result = statement.executeQuery();
			while (result.next()) {
				review = findByPrimaryKeyJoin(result.getLong("idProdotto"), result.getString("NumeroTelefono"));
				list.add(review);
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
		return list;
		
		
	}

	public void update(ReviewProduct Review) {
		
		Connection connection = this.dbConnection.getConnection();
		try {
			String update = "update recensioneprodottoutente SET Voto = ?, DataOra = ? WHERE idProdotto = ? AND NumeroTelefono = ?";
			PreparedStatement statement = connection.prepareStatement(update);
			statement.setLong(3, Review.getIdProduct());
			statement.setString(4, Review.getNumeroTelefono());
			statement.setInt(1, Review.getVoto());
            SimpleDateFormat datetime = new SimpleDateFormat("yyyy-MM-dd HH:mm");            
			statement.setString(2, datetime.format(Review.getDataOra()));

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

	public void delete(ReviewProduct rev) {
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
