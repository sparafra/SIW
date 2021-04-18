package databaseHibernate;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import daoInterface.IngredientDAO;
import model.Ingredient;



public class IngredientDaoJDBC implements IngredientDAO {
	private DBConnection dbConnection;

	public IngredientDaoJDBC(DBConnection dbConnection) {
		this.dbConnection = dbConnection;
	}

	public void save(Ingredient ing) {
		
		Connection connection = this.dbConnection.getConnection();
		try {
			Long id = IdBroker.getId(connection, "idIngrediente", "ingrediente");
			ing.setId(id); 
			String insert = "insert into ingrediente(idIngrediente, Nome, Costo) values (?,?,?)";
			PreparedStatement statement = connection.prepareStatement(insert);
			statement.setLong(1, ing.getId());
			statement.setString(2, ing.getNome());
			statement.setFloat(3, ing.getPrezzo());
			
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
	public Ingredient findByPrimaryKeyJoin(Long id) {
		Connection connection = this.dbConnection.getConnection();
		Ingredient ingredient = null;
		try {
			PreparedStatement statement;
			String query = "SELECT idIngrediente, Nome, Costo " + 
					"FROM ingrediente " + 
					"WHERE idIngrediente = ?";
					
			statement = connection.prepareStatement(query);
			statement.setLong(1, id);
			ResultSet result = statement.executeQuery();
			boolean primaRiga = true;
			while (result.next()) {
				if (primaRiga) {
					ingredient = new Ingredient();
					ingredient.setId(result.getLong("idIngrediente"));
					ingredient.setNome(result.getString("Nome"));				
					ingredient.setPrezzo(result.getFloat("Costo"));
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
		return ingredient;
	}
	public Ingredient findByNameJoin(String Nome) {
		Connection connection = this.dbConnection.getConnection();
		Ingredient ingredient = null;
		try {
			PreparedStatement statement;
			String query = "SELECT idIngrediente, Nome, Costo " + 
					"FROM ingrediente " + 
					"WHERE Nome = ?";
					
			statement = connection.prepareStatement(query);
			statement.setString(1, Nome);
			ResultSet result = statement.executeQuery();
			boolean primaRiga = true;
			while (result.next()) {
				if (primaRiga) {
					ingredient = new Ingredient();
					ingredient.setId(result.getLong("idIngrediente"));
					ingredient.setNome(result.getString("Nome"));				
					ingredient.setPrezzo(result.getFloat("Costo"));
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
		return ingredient;
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

	public List<Ingredient> findAll(Long idLocal) {
		
		Connection connection = this.dbConnection.getConnection();
		List<Ingredient> ingredients = new ArrayList<>();
		try {			
			Ingredient ingredient;
			PreparedStatement statement;
			String query = "SELECT ingrediente.idIngrediente, Nome, Costo FROM ingrediente, ingredientilocale "
					+ "WHERE ingredientilocale.idLocale = ? AND ingrediente.idIngrediente = ingredientilocale.idIngrediente"; //DA METTERE COME ingredienti JOIN ingredientilocale
			statement = connection.prepareStatement(query);
			statement.setLong(1, idLocal);
			ResultSet result = statement.executeQuery();
			while (result.next()) {
				ingredient = new Ingredient();
				ingredient.setId(result.getLong("idIngrediente"));
				ingredient.setNome(result.getString("Nome"));
				ingredient.setPrezzo(result.getFloat("Costo"));
				ingredients.add(ingredient);
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
		return ingredients;
		
		
	}

	public void update(Ingredient ing) {
		
		Connection connection = this.dbConnection.getConnection();
		try {
			String update = "update ingrediente SET Nome = ?, Costo = ? WHERE idIngrediente = ?";
			PreparedStatement statement = connection.prepareStatement(update);
			statement.setString(1, ing.getNome());
			statement.setFloat(2, ing.getPrezzo());
			statement.setLong(3, ing.getId());
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
