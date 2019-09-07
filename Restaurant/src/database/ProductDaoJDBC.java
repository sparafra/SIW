package database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import daoInterface.ProductDAO;
import model.Ingredient;
import model.Product;
import model.ReviewProduct;



public class ProductDaoJDBC implements ProductDAO {
	private DBConnection dbConnection;

	public ProductDaoJDBC(DBConnection dbConnection) {
		this.dbConnection = dbConnection;
	}

	public void save(Product product) {
		
		
		Connection connection = this.dbConnection.getConnection();
		try {
			Long id = IdBroker.getId(connection, "idProdotto", "prodotto");
			product.setId(id); 
			String insert = "insert into prodotto(idProdotto, Nome, Prezzo, idLocale, ImageURL) values (?,?,?,?,?)";
			PreparedStatement statement = connection.prepareStatement(insert);
			statement.setLong(1, product.getId());
			statement.setString(2, product.getNome());
			statement.setFloat(3, product.getPrezzo());
			statement.setLong(4, product.getIdLocale());
			statement.setString(5, product.getImageURL());

			//connection.setAutoCommit(false);
			//serve in caso gli studenti non siano stati salvati. Il DAO studente apre e chiude una transazione nuova.
			//connection.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);			
			statement.executeUpdate();
			// salviamo anche tutti gli studenti del gruppo in CASACATA
			
			for(int k=0; k<product.getListIngredienti().size(); k++)
			{
				insert = "insert into prodottiingredienti(idProdotto, idIngrediente) values (?,?)";
				statement = connection.prepareStatement(insert);
				statement.setLong(1, product.getId());
				statement.setLong(2, product.getListIngredienti().get(k).getId());
				
	
				//connection.setAutoCommit(false);
				//serve in caso gli studenti non siano stati salvati. Il DAO studente apre e chiude una transazione nuova.
				//connection.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);			
				statement.executeUpdate();
			}
			
			insert = "insert into prodottitipologia(idProdotto, Tipo) values (?,?)";
			statement = connection.prepareStatement(insert);
			statement.setLong(1, product.getId());
			statement.setString(2, product.getTipo());
			

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

	public void PartialSave(Product product)
	{
		Connection connection = this.dbConnection.getConnection();
		try {
			Long id = IdBroker.getId(connection, "idProdotto", "prodotto");
			product.setId(id); 
			String insert = "insert into prodotto(idProdotto, Nome, Prezzo, idLocale, ImageURL) values (?,?,?,?,?)";
			PreparedStatement statement = connection.prepareStatement(insert);
			statement.setLong(1, product.getId());
			statement.setString(2, product.getNome());
			statement.setFloat(3, product.getPrezzo());
			statement.setLong(4, product.getIdLocale());
			statement.setString(5, product.getImageURL());

			//connection.setAutoCommit(false);
			//serve in caso gli studenti non siano stati salvati. Il DAO studente apre e chiude una transazione nuova.
			//connection.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);			
			statement.executeUpdate();
			// salviamo anche tutti gli studenti del gruppo in CASACATA
			
			
			insert = "insert into prodottitipologia(idProdotto, Tipo) values (?,?)";
			statement = connection.prepareStatement(insert);
			statement.setLong(1, product.getId());
			statement.setString(2, product.getTipo());
			

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

	public void updateIngredientiProdotto(Long idProdotto, Long idIngrediente)
	{
		Connection connection = this.dbConnection.getConnection();
		try {


			String insert = "insert into prodottiingredienti(idProdotto, idIngrediente) values (?,?)";
			PreparedStatement statement = connection.prepareStatement(insert);

			statement = connection.prepareStatement(insert);
			statement.setLong(1, idProdotto);
			statement.setLong(2, idIngrediente);
			

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
	
	private void updateStudenti(Product product, Connection connection) throws SQLException {
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

	private void removeForeignKeyFromStudente(Product product, Connection connection) throws SQLException {
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
	public Product findByPrimaryKeyJoin(Long id) {
		Connection connection = this.dbConnection.getConnection();
		Product product = null;
		try {
			PreparedStatement statement;
			String query = "SELECT prodotto.idProdotto, Nome, Prezzo, idLocale, ImageURL, Tipo " + 
					"FROM prodotto, prodottitipologia " + 
					"WHERE prodotto.idProdotto = ? AND prodotto.idProdotto = prodottitipologia.idProdotto";
					
			statement = connection.prepareStatement(query);
			statement.setLong(1, id);
			ResultSet result = statement.executeQuery();
			boolean primaRiga = true;
			while (result.next()) {
				if (primaRiga) {
					product = new Product();
					product.setNome(result.getString("Nome"));
					product.setId(result.getLong("idProdotto"));				
					product.setPrezzo(result.getFloat("Prezzo"));
					product.setTipo(result.getString("Tipo"));
					product.setImageURL(result.getString("ImageURL"));
					primaRiga = false;
					
					query = "SELECT ingrediente.idIngrediente, Nome, Costo " + 
							"FROM ingrediente, prodottiingredienti " + 
							"WHERE prodottiingredienti.idProdotto = ? AND ingrediente.idIngrediente = prodottiingredienti.idIngrediente";
					statement = connection.prepareStatement(query);
					statement.setLong(1, id);
					ResultSet res = statement.executeQuery();
					List<Ingredient> list = new ArrayList<>();
					while (res.next()) {
						Ingredient I = new Ingredient();
						I.setId(res.getLong("idIngrediente"));
						I.setNome(res.getString("Nome"));
						I.setPrezzo(res.getFloat("Costo"));
						list.add(I);
					}
					product.setListIngredienti(list);
					
					query = "SELECT idProdotto, NumeroTelefono, Voto, DataOra " + 
							"FROM recensioneprodottoutente " + 
							"WHERE recensioneprodottoutente.idProdotto = ?";
					statement = connection.prepareStatement(query);
					statement.setLong(1, id);
					ResultSet res2 = statement.executeQuery();
					List<ReviewProduct> listReview = new ArrayList<>();
					while (res.next()) {
						ReviewProduct review = new ReviewProduct();
						review.setIdProduct(res.getLong("idProdotto"));
						review.setNumeroTelefono(res.getString("NumeroTelefono"));
						review.setVoto(res.getInt("Voto"));
						review.setDataOra(result.getDate("DataOra"));
						listReview.add(review);
					}
					product.setListReview(listReview);
						
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
		return product;
	}
	
	public List<Product> findByOrderJoin(Long id) {
		Connection connection = this.dbConnection.getConnection();
		List<Product> list = null;
		Product product = null;
		try {
			PreparedStatement statement;
			String query = "SELECT prodotto.idProdotto, Nome, Prezzo, idLocale, ImageURL, Tipo, Quantita " + 
					"FROM prodotto, prodottiordini, prodottitipologia " + 
					"WHERE prodotto.idProdotto = prodottiordini.idProdotto AND prodotto.idProdotto = prodottitipologia.idProdotto AND prodottiordini.idOrdine = ?";
					
			statement = connection.prepareStatement(query);
			statement.setLong(1, id);
			ResultSet result = statement.executeQuery();
			list = new ArrayList<>(); 
			while (result.next()) {
				product = new Product();
				product.setNome(result.getString("Nome"));
				product.setId(result.getLong("idProdotto"));				
				product.setPrezzo(result.getFloat("Prezzo"));
				product.setTipo(result.getString("Tipo"));
				product.setImageURL(result.getString("ImageURL"));
				product.setQuantita(1);
				
				query = "SELECT ingrediente.idIngrediente, Nome, Costo " + 
						"FROM ingrediente, prodottiingredienti " + 
						"WHERE prodottiingredienti.idProdotto = ? AND ingrediente.idIngrediente = prodottiingredienti.idIngrediente";
				statement = connection.prepareStatement(query);
				statement.setLong(1, result.getLong("idProdotto"));
				ResultSet res = statement.executeQuery();
				List<Ingredient> listI = new ArrayList<>();
				while (res.next()) {
					Ingredient I = new Ingredient();
					I.setId(res.getLong("idIngrediente"));
					I.setNome(res.getString("Nome"));
					I.setPrezzo(res.getFloat("Costo"));
					listI.add(I);
				}
				product.setListIngredienti(listI);
				
				query = "SELECT idProdotto, NumeroTelefono, Voto, DataOra " + 
						"FROM recensioneprodottoutente " + 
						"WHERE recensioneprodottoutente.idProdotto = ?";
				statement = connection.prepareStatement(query);
				statement.setLong(1, product.getId());
				ResultSet res2 = statement.executeQuery();
				List<ReviewProduct> listReview = new ArrayList<>();
				while (res2.next()) {
					ReviewProduct review = new ReviewProduct();
					review.setIdProduct(res2.getLong("idProdotto"));
					review.setNumeroTelefono(res2.getString("NumeroTelefono"));
					review.setVoto(res2.getInt("Voto"));
					review.setDataOra(res2.getDate("DataOra"));
					listReview.add(review);
				}
				product.setListReview(listReview);
				
				list.add(product);
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
	
	public List<Product> findAllJoin() {
		Connection connection = this.dbConnection.getConnection();
		List<Product> list = null;
		Product product = null;
		try {
			PreparedStatement statement;
			String query = "SELECT prodotto.idProdotto, Nome, Prezzo, idLocale, ImageURL, Tipo " + 
					"FROM prodotto, prodottitipologia " + 
					"WHERE prodotto.idProdotto = prodottitipologia.idProdotto AND Nome != 'Personalizzata'"
					+ "ORDER BY ImageURL DESC";
					
			statement = connection.prepareStatement(query);
			
			ResultSet result = statement.executeQuery();
			list = new ArrayList<>(); 
			while (result.next()) {
				product = new Product();
				product.setNome(result.getString("Nome"));
				product.setId(result.getLong("idProdotto"));				
				product.setPrezzo(result.getFloat("Prezzo"));
				product.setTipo(result.getString("Tipo"));
				product.setImageURL(result.getString("ImageURL"));
				product.setQuantita(1);
				
				query = "SELECT ingrediente.idIngrediente, Nome, Costo " + 
						"FROM ingrediente, prodottiingredienti " + 
						"WHERE prodottiingredienti.idProdotto = ? AND ingrediente.idIngrediente = prodottiingredienti.idIngrediente";
				statement = connection.prepareStatement(query);
				statement.setLong(1, result.getLong("idProdotto"));
				ResultSet res = statement.executeQuery();
				List<Ingredient> listI = new ArrayList<>();
				while (res.next()) {
					Ingredient I = new Ingredient();
					I.setId(res.getLong("idIngrediente"));
					I.setNome(res.getString("Nome"));
					I.setPrezzo(res.getFloat("Costo"));
					listI.add(I);
				}
				product.setListIngredienti(listI);
				
				query = "SELECT idProdotto, NumeroTelefono, Voto, DataOra " + 
						"FROM recensioneprodottoutente " + 
						"WHERE recensioneprodottoutente.idProdotto = ?";
				statement = connection.prepareStatement(query);
				statement.setLong(1, product.getId());
				ResultSet res2 = statement.executeQuery();
				List<ReviewProduct> listReview = new ArrayList<>();
				while (res2.next()) {
					ReviewProduct review = new ReviewProduct();
					review.setIdProduct(res2.getLong("idProdotto"));
					review.setNumeroTelefono(res2.getString("NumeroTelefono"));
					review.setVoto(res2.getInt("Voto"));
					review.setDataOra(res2.getDate("DataOra"));
					listReview.add(review);
				}
				product.setListReview(listReview);
				
				list.add(product);
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
	public List<Product> findByTypeJoin(String Tipo) {
		Connection connection = this.dbConnection.getConnection();
		List<Product> list = null;
		Product product = null;
		try {
			PreparedStatement statement;
			String query = "SELECT prodotto.idProdotto, Nome, Prezzo, idLocale, ImageURL, Tipo " + 
					"FROM prodotto, prodottitipologia " + 
					"WHERE prodottitipologia.Tipo = ? AND prodotto.idProdotto = prodottitipologia.idProdotto AND prodotto.Nome != 'Personalizzata'";
					
			statement = connection.prepareStatement(query);
			statement.setString(1, Tipo);
			ResultSet result = statement.executeQuery();
			list = new ArrayList<>(); 
			while (result.next()) {
				product = new Product();
				product.setNome(result.getString("Nome"));
				product.setId(result.getLong("idProdotto"));				
				product.setPrezzo(result.getFloat("Prezzo"));
				product.setTipo(result.getString("Tipo"));
				product.setImageURL(result.getString("ImageURL"));
				product.setQuantita(1);
				
				query = "SELECT ingrediente.idIngrediente, Nome, Costo " + 
						"FROM ingrediente, prodottiingredienti " + 
						"WHERE prodottiingredienti.idProdotto = ? AND ingrediente.idIngrediente = prodottiingredienti.idIngrediente";
				statement = connection.prepareStatement(query);
				statement.setLong(1, result.getLong("idProdotto"));
				ResultSet res = statement.executeQuery();
				List<Ingredient> listI = new ArrayList<>();
				while (res.next()) {
					Ingredient I = new Ingredient();
					I.setId(res.getLong("idIngrediente"));
					I.setNome(res.getString("Nome"));
					I.setPrezzo(res.getFloat("Costo"));
					listI.add(I);
				}
				product.setListIngredienti(listI);
				
				query = "SELECT idProdotto, NumeroTelefono, Voto, DataOra " + 
						"FROM recensioneprodottoutente " + 
						"WHERE recensioneprodottoutente.idProdotto = ?";
				statement = connection.prepareStatement(query);
				statement.setLong(1, product.getId());
				ResultSet res2 = statement.executeQuery();
				List<ReviewProduct> listReview = new ArrayList<>();
				while (res2.next()) {
					ReviewProduct review = new ReviewProduct();
					review.setIdProduct(res2.getLong("idProdotto"));
					review.setNumeroTelefono(res2.getString("NumeroTelefono"));
					review.setVoto(res2.getInt("Voto"));
					review.setDataOra(res2.getDate("DataOra"));
					listReview.add(review);
				}
				product.setListReview(listReview);
				
				list.add(product);
					
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
		return list;
	}
	public List<Product> findByTypeLocalJoin(String Tipo, Long idLocale) {
		Connection connection = this.dbConnection.getConnection();
		List<Product> list = null;
		Product product = null;
		try {
			PreparedStatement statement;
			String query = "SELECT prodotto.idProdotto, Nome, Prezzo, idLocale, ImageURL, Tipo " + 
					"FROM prodotto, prodottitipologia " + 
					"WHERE prodottitipologia.Tipo = ? AND prodotto.idLocale = ? AND prodotto.idProdotto = prodottitipologia.idProdotto AND prodotto.Nome != 'Personalizzata'";
					
			statement = connection.prepareStatement(query);
			statement.setString(1, Tipo);
			statement.setLong(2, idLocale);
			ResultSet result = statement.executeQuery();
			list = new ArrayList<>(); 
			while (result.next()) {
				product = new Product();
				product.setNome(result.getString("Nome"));
				product.setId(result.getLong("idProdotto"));				
				product.setPrezzo(result.getFloat("Prezzo"));
				product.setTipo(result.getString("Tipo"));
				product.setImageURL(result.getString("ImageURL"));
				product.setQuantita(1);
				
				query = "SELECT ingrediente.idIngrediente, Nome, Costo " + 
						"FROM ingrediente, prodottiingredienti " + 
						"WHERE prodottiingredienti.idProdotto = ? AND ingrediente.idIngrediente = prodottiingredienti.idIngrediente";
				statement = connection.prepareStatement(query);
				statement.setLong(1, result.getLong("idProdotto"));
				ResultSet res = statement.executeQuery();
				List<Ingredient> listI = new ArrayList<>();
				while (res.next()) {
					Ingredient I = new Ingredient();
					I.setId(res.getLong("idIngrediente"));
					I.setNome(res.getString("Nome"));
					I.setPrezzo(res.getFloat("Costo"));
					listI.add(I);
				}
				product.setListIngredienti(listI);
				
				query = "SELECT idProdotto, NumeroTelefono, Voto, DataOra " + 
						"FROM recensioneprodottoutente " + 
						"WHERE recensioneprodottoutente.idProdotto = ?";
				statement = connection.prepareStatement(query);
				statement.setLong(1, product.getId());
				ResultSet res2 = statement.executeQuery();
				List<ReviewProduct> listReview = new ArrayList<>();
				while (res2.next()) {
					ReviewProduct review = new ReviewProduct();
					review.setIdProduct(res2.getLong("idProdotto"));
					review.setNumeroTelefono(res2.getString("NumeroTelefono"));
					review.setVoto(res2.getInt("Voto"));
					review.setDataOra(res2.getDate("DataOra"));
					listReview.add(review);
				}
				product.setListReview(listReview);
				
				list.add(product);
					
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
		return list;
	}

	public List<Product> findByRestaurant(Long idLocale) {
		Connection connection = this.dbConnection.getConnection();
		List<Product> list = null;
		Product product = null;
		try {
			PreparedStatement statement;
			String query = "SELECT prodotto.idProdotto, Nome, Prezzo, idLocale, ImageURL, Tipo " + 
					"FROM prodotto, prodottitipologia " + 
					"WHERE prodotto.idLocale = ? AND prodotto.idProdotto = prodottitipologia.idProdotto AND Nome != 'Personalizzata'"+
					"ORDER BY ImageURL DESC";
					
			statement = connection.prepareStatement(query);
			statement.setLong(1, idLocale);
			ResultSet result = statement.executeQuery();
			list = new ArrayList<>(); 
			while (result.next()) {
					product = new Product();
					product.setNome(result.getString("Nome"));
					product.setId(result.getLong("idProdotto"));				
					product.setPrezzo(result.getFloat("Prezzo"));
					product.setTipo(result.getString("Tipo"));
					product.setImageURL(result.getString("ImageURL"));
					product.setQuantita(1);
					
					query = "SELECT ingrediente.idIngrediente, Nome, Costo " + 
							"FROM ingrediente, prodottiingredienti " + 
							"WHERE prodottiingredienti.idProdotto = ? AND ingrediente.idIngrediente = prodottiingredienti.idIngrediente";
					statement = connection.prepareStatement(query);
					statement.setLong(1, result.getLong("idProdotto"));
					ResultSet res = statement.executeQuery();
					List<Ingredient> listI = new ArrayList<>();
					while (res.next()) {
						Ingredient I = new Ingredient();
						I.setId(res.getLong("idIngrediente"));
						I.setNome(res.getString("Nome"));
						I.setPrezzo(res.getFloat("Costo"));
						listI.add(I);
					}
					product.setListIngredienti(listI);
					
					query = "SELECT idProdotto, NumeroTelefono, Voto, DataOra " + 
							"FROM recensioneprodottoutente " + 
							"WHERE recensioneprodottoutente.idProdotto = ?";
					statement = connection.prepareStatement(query);
					statement.setLong(1, product.getId());
					ResultSet res2 = statement.executeQuery();
					List<ReviewProduct> listReview = new ArrayList<>();
					while (res2.next()) {
						ReviewProduct review = new ReviewProduct();
						review.setIdProduct(res2.getLong("idProdotto"));
						review.setNumeroTelefono(res2.getString("NumeroTelefono"));
						review.setVoto(res2.getInt("Voto"));
						review.setDataOra(res2.getDate("DataOra"));
						listReview.add(review);
					}
					product.setListReview(listReview);
					
					list.add(product);
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
	public Product findByPrimaryKey(Long id) {
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

	public List<Product> findAll() {
		
		Connection connection = this.dbConnection.getConnection();
		List<Product> products = new ArrayList<>();
		try {			
			Product product;
			PreparedStatement statement;
			String query = "select * from locali";
			statement = connection.prepareStatement(query);
			ResultSet result = statement.executeQuery();
			while (result.next()) {
				product = findByPrimaryKeyJoin(result.getLong("codice"));
				products.add(product);
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
		return products;
		
		
	}

	
	
	public void update(Product product) {
		
		Connection connection = this.dbConnection.getConnection();
		try {
			String update = "update prodotto SET Nome = ?, Prezzo = ?, ImageURL = ? WHERE idProdotto = ?";
			PreparedStatement statement = connection.prepareStatement(update);
			statement.setString(1, product.getNome());
			statement.setFloat(2, product.getPrezzo());
			statement.setString(3,product.getImageURL());
			statement.setLong(4, product.getId());
			//connection.setAutoCommit(false);
			//connection.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);			
			statement.executeUpdate();
			
			update = "update prodottitipologia SET Tipo = ? WHERE idProdotto = ?";
			statement = connection.prepareStatement(update);
			statement.setString(1, product.getTipo());
			statement.setLong(2, product.getId());
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

	public void delete(Product product) {
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
