package databaseHibernate;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.json.JSONArray;

import daoInterface.OrderDAO;
import model.Ingredient;
import model.Order;
import model.Product;



public class OrderDaoJDBC implements OrderDAO {
	private DBConnection dbConnection;

	public OrderDaoJDBC(DBConnection dbConnection) {
		this.dbConnection = dbConnection;
	}

	public void save(Order order) {
		
		/*
		if ( (order.getListProducts() == null) 
				|| order.getListProducts().isEmpty()){
			throw new PersistenceException("Ordine non memorizzato: un ordine deve avere almeno un prodotto");
		}*/
		Connection connection = this.dbConnection.getConnection();
		try {
			Long id = IdBroker.getId(connection, "idOrdine", "ordine");
			order.setId(id); 
			String insert = "insert into ordine(idOrdine, Stato, Asporto, NumeroTelefono, DataOra, idLocale, Costo, Pagato) values (?,?,?,?,?,?,?,?)";
			PreparedStatement statement = connection.prepareStatement(insert);
			statement.setLong(1, order.getId());
			statement.setString(2, order.getStato());
			//statement.setInt(3, 1);
			System.out.println(order.getAsporto());
			
			if(order.getAsporto())
				statement.setInt(3, 1);
			else
				statement.setInt(3, 0);
			
			statement.setString(4, order.getNumeroTelefono());
			
			
			//Date currentTime = Calendar.getInstance().getTime();
            SimpleDateFormat datetime = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            
            //order.setDateTime(currentTime);
            
            statement.setString(5, datetime.format(order.getDateTime()));
			statement.setLong(6, order.getIdLocale());
			statement.setFloat(7, order.getTotaleCosto());
			if(order.getPagato())
				statement.setInt(8, 1);
			else
				statement.setInt(8, 0);
            
			//connection.setAutoCommit(false);
			//serve in caso gli studenti non siano stati salvati. Il DAO studente apre e chiude una transazione nuova.
			//connection.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);			
			statement.executeUpdate();
			// salviamo anche tutti gli studenti del gruppo in CASACATA
			this.updateOrderProduct(order, connection);
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

	public void saveOrderProduct(Long idOrdine, Long idProduct, int Quantita)  {

		Connection connection = this.dbConnection.getConnection();

		try {

			String query = "insert into prodottiordini(idProdotto, idOrdine, Quantita) values (?,?,?)";
			PreparedStatement statementIscrivi = connection.prepareStatement(query);
			statementIscrivi.setLong(1, idProduct);
			statementIscrivi.setLong(2, idOrdine);
			statementIscrivi.setInt(3, Quantita);
			statementIscrivi.executeUpdate();
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
	private void updateOrderProduct(Order order, Connection connection) throws SQLException {
		
		if(order.getListProducts() != null)
		{
			for (Product product : order.getListProducts()) {
						
					String query = "insert into prodottiordini(idProdotto, idOrdine, Quantita) values (?,?,?)";
					PreparedStatement statementIscrivi = connection.prepareStatement(query);
					statementIscrivi.setLong(1, product.getId());
					statementIscrivi.setLong(2, order.getId());
					statementIscrivi.setInt(3, product.getQuantita());
					statementIscrivi.executeUpdate();
				
			}
		}
		
	}
	
	public void updateOrderProduct(Long idOrdine, Long idProduct, int Quantita)
	{
		Connection connection = this.dbConnection.getConnection();
		try {
			String update = "update prodottiordini SET Quantita = ? WHERE idOrdine = ? AND idProdotto = ?";
			PreparedStatement statement = connection.prepareStatement(update);
			statement.setInt(1, Quantita);
			statement.setLong(2, idOrdine);
			statement.setLong(3, idProduct);
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

	/* 
	 * versione con Join
	 */
	public Order findByPrimaryKeyJoin(Long id) {
		
		Connection connection = this.dbConnection.getConnection();
		Order order = null;
		try {
			PreparedStatement statement;
			String query = "SELECT idOrdine, Stato, Asporto, NumeroTelefono, DataOra, idLocale, Costo, Pagato " + 
					"FROM ordine " + 
					"WHERE idOrdine = ? ";
					
			
			statement = connection.prepareStatement(query);
			statement.setLong(1, id);
			ResultSet result = statement.executeQuery();
			boolean primaRiga = true;
			while (result.next()) {
				if (primaRiga) {
					order = new Order();
					order.setId(result.getLong("idOrdine"));
					order.setAsporto(result.getBoolean("Asporto"));
					System.out.println(result.getDate("DataOra"));
					SimpleDateFormat datetime = new SimpleDateFormat("yyyy-MM-dd HH:mm");  
					
					try {
						order.setDateTime(datetime.parse(result.getString("DataOra")));
					} catch (ParseException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					try {
						System.out.println(datetime.parse(result.getString("DataOra")));
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					/*
					Date d = new Date();
					d.setDate(result.getDate("DataOra"));
					d.setTime(result.getTime("DataOra"));
					*/
					//order.setDateTime(result.getDate("DataOra"));				
					order.setNumeroTelefono(result.getString("NumeroTelefono"));
					order.setStato(result.getString("Stato"));
					order.setIdLocale(result.getLong("idLocale"));
					order.setCosto(result.getFloat("Costo"));
					order.setPagato(result.getBoolean("Pagato"));
					
					query = "SELECT prodotto.idProdotto, prodotto.Nome, Prezzo, idLocale, ImageURL, Tipo, Quantita " + 
							"FROM prodotto, prodottiordini, prodottitipologia " + 
							"WHERE prodotto.idProdotto = prodottiordini.idProdotto AND prodotto.idProdotto = prodottitipologia.idProdotto AND prodottiordini.idOrdine = ?";
					statement = connection.prepareStatement(query);
					statement.setLong(1, order.getId());
					ResultSet res = statement.executeQuery();
					List<Product> listP = new ArrayList<>();
					while (res.next()) {
						Product product = new Product();
						product.setNome(res.getString("Nome"));
						product.setId(res.getLong("idProdotto"));				
						product.setPrezzo(res.getFloat("Prezzo"));
						product.setTipo(res.getString("Tipo"));
						product.setImageURL(res.getString("ImageURL"));
						product.setQuantita(res.getInt("Quantita"));
						
						query = "SELECT ingrediente.idIngrediente, Nome, Costo " + 
								"FROM ingrediente, prodottiingredienti " + 
								"WHERE prodottiingredienti.idProdotto = ? AND ingrediente.idIngrediente = prodottiingredienti.idIngrediente";
						statement = connection.prepareStatement(query);
						statement.setLong(1, res.getLong("idProdotto"));
						ResultSet res1 = statement.executeQuery();
						List<Ingredient> listI = new ArrayList<>();
						while (res1.next()) {
							Ingredient I = new Ingredient();
							I.setId(res1.getLong("idIngrediente"));
							I.setNome(res1.getString("Nome"));
							I.setPrezzo(res1.getFloat("Costo"));
							listI.add(I);
						}
						product.setListIngredienti(listI);
						
						listP.add(product);
					}
					order.setListProducts(listP);				
						
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
		return order;
		
	}
	
	public List<Order> findByUserJoin(String NumeroTelefonoUser) {
		
		Connection connection = this.dbConnection.getConnection();
		List<Order> list = null;
		Order order = null;
		try {
			PreparedStatement statement;
			String query = "SELECT idOrdine, Stato, Asporto, NumeroTelefono, DataOra, idLocale, Pagato, Costo " + 
					"FROM ordine " + 
					"WHERE NumeroTelefono = ? "+
					"ORDER BY DataOra desc";
			
			statement = connection.prepareStatement(query);
			statement.setString(1, NumeroTelefonoUser);
			ResultSet result = statement.executeQuery();
			list = new ArrayList<>(); 
			while (result.next()) {
					order = new Order();
					order.setId(result.getLong("idOrdine"));
					order.setAsporto(result.getBoolean("Asporto"));
					SimpleDateFormat datetime = new SimpleDateFormat("yyyy-MM-dd HH:mm");  
					
					try {
						order.setDateTime(datetime.parse(result.getString("DataOra")));
					} catch (ParseException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					try {
						System.out.println(datetime.parse(result.getString("DataOra")));
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					//order.setDateTime(result.getDate("DataOra"));				
					order.setNumeroTelefono(result.getString("NumeroTelefono"));
					order.setStato(result.getString("Stato"));
					order.setIdLocale(result.getLong("idLocale"));
					order.setPagato(result.getBoolean("Pagato"));
					order.setCosto(result.getFloat("Costo"));
					
					query = "SELECT prodotto.idProdotto, prodotto.Nome, Prezzo, idLocale, ImageURL, Tipo, Quantita " + 
							"FROM prodotto, prodottiordini, prodottitipologia " + 
							"WHERE prodotto.idProdotto = prodottiordini.idProdotto AND prodotto.idProdotto = prodottitipologia.idProdotto AND prodottiordini.idOrdine = ?";
					statement = connection.prepareStatement(query);
					statement.setLong(1, order.getId());
					ResultSet res = statement.executeQuery();
					List<Product> listP = new ArrayList<>();
					while (res.next()) {
						Product product = new Product();
						product.setNome(res.getString("Nome"));
						product.setId(res.getLong("idProdotto"));				
						product.setPrezzo(res.getFloat("Prezzo"));
						product.setTipo(res.getString("Tipo"));
						product.setImageURL(res.getString("ImageURL"));
						product.setQuantita(res.getInt("Quantita"));
						
						query = "SELECT ingrediente.idIngrediente, Nome, Costo " + 
								"FROM ingrediente, prodottiingredienti " + 
								"WHERE prodottiingredienti.idProdotto = ? AND ingrediente.idIngrediente = prodottiingredienti.idIngrediente";
						statement = connection.prepareStatement(query);
						statement.setLong(1, res.getLong("idProdotto"));
						ResultSet res1 = statement.executeQuery();
						List<Ingredient> listI = new ArrayList<>();
						while (res1.next()) {
							Ingredient I = new Ingredient();
							I.setId(res1.getLong("idIngrediente"));
							I.setNome(res1.getString("Nome"));
							I.setPrezzo(res1.getFloat("Costo"));
							listI.add(I);
						}
						product.setListIngredienti(listI);
						
						listP.add(product);
					}
					order.setListProducts(listP);					
					
					list.add(order);
					
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

	public List<Order> findByUserLocalJoin(String NumeroTelefonoUser, Long idLocal) {
		
		Connection connection = this.dbConnection.getConnection();
		List<Order> list = null;
		Order order = null;
		try {
			PreparedStatement statement;
			String query = "SELECT idOrdine, Stato, Asporto, NumeroTelefono, DataOra, idLocale, Pagato, Costo " + 
					"FROM ordine " + 
					"WHERE NumeroTelefono = ? AND idLocale = ? "+
					"ORDER BY DataOra desc";
			
			statement = connection.prepareStatement(query);
			statement.setString(1, NumeroTelefonoUser);
			statement.setLong(2, idLocal);
			ResultSet result = statement.executeQuery();
			list = new ArrayList<>(); 
			while (result.next()) {
					order = new Order();
					order.setId(result.getLong("idOrdine"));
					order.setAsporto(result.getBoolean("Asporto"));
					SimpleDateFormat datetime = new SimpleDateFormat("yyyy-MM-dd HH:mm");  
					
					try {
						order.setDateTime(datetime.parse(result.getString("DataOra")));
					} catch (ParseException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					try {
						System.out.println(datetime.parse(result.getString("DataOra")));
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

					//order.setDateTime(result.getDate("DataOra"));				
					order.setNumeroTelefono(result.getString("NumeroTelefono"));
					order.setStato(result.getString("Stato"));
					order.setIdLocale(result.getLong("idLocale"));
					order.setPagato(result.getBoolean("Pagato"));
					order.setCosto(result.getFloat("Costo"));
					
					query = "SELECT prodotto.idProdotto, prodotto.Nome, Prezzo, idLocale, ImageURL, Tipo, Quantita " + 
							"FROM prodotto, prodottiordini, prodottitipologia " + 
							"WHERE prodotto.idProdotto = prodottiordini.idProdotto AND prodotto.idProdotto = prodottitipologia.idProdotto AND prodottiordini.idOrdine = ?";
					statement = connection.prepareStatement(query);
					statement.setLong(1, order.getId());
					ResultSet res = statement.executeQuery();
					List<Product> listP = new ArrayList<>();
					while (res.next()) {
						Product product = new Product();
						product.setNome(res.getString("Nome"));
						product.setId(res.getLong("idProdotto"));				
						product.setPrezzo(res.getFloat("Prezzo"));
						product.setTipo(res.getString("Tipo"));
						product.setImageURL(res.getString("ImageURL"));
						product.setQuantita(res.getInt("Quantita"));
						
						query = "SELECT ingrediente.idIngrediente, Nome, Costo " + 
								"FROM ingrediente, prodottiingredienti " + 
								"WHERE prodottiingredienti.idProdotto = ? AND ingrediente.idIngrediente = prodottiingredienti.idIngrediente";
						statement = connection.prepareStatement(query);
						statement.setLong(1, res.getLong("idProdotto"));
						ResultSet res1 = statement.executeQuery();
						List<Ingredient> listI = new ArrayList<>();
						while (res1.next()) {
							Ingredient I = new Ingredient();
							I.setId(res1.getLong("idIngrediente"));
							I.setNome(res1.getString("Nome"));
							I.setPrezzo(res1.getFloat("Costo"));
							listI.add(I);
						}
						product.setListIngredienti(listI);
						
						listP.add(product);
					}
					order.setListProducts(listP);					
					
					list.add(order);
					
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
	
	public List<Order> findByUserLocalStateJoin(String NumeroTelefonoUser, Long idLocal, String Stato) {
		
		Connection connection = this.dbConnection.getConnection();
		List<Order> list = null;
		Order order = null;
		try {
			PreparedStatement statement;
			String query = "SELECT idOrdine, Stato, Asporto, NumeroTelefono, DataOra, idLocale, Pagato, Costo " + 
					"FROM ordine " + 
					"WHERE NumeroTelefono = ? AND idLocale = ? AND Stato = ? "+
					"ORDER BY DataOra desc";
			
			statement = connection.prepareStatement(query);
			statement.setString(1, NumeroTelefonoUser);
			statement.setLong(2, idLocal);
			statement.setString(3, Stato);

			ResultSet result = statement.executeQuery();
			list = new ArrayList<>(); 
			while (result.next()) {
					order = new Order();
					order.setId(result.getLong("idOrdine"));
					order.setAsporto(result.getBoolean("Asporto"));
					SimpleDateFormat datetime = new SimpleDateFormat("yyyy-MM-dd HH:mm");  
					
					try {
						order.setDateTime(datetime.parse(result.getString("DataOra")));
					} catch (ParseException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					try {
						System.out.println(datetime.parse(result.getString("DataOra")));
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					//order.setDateTime(result.getDate("DataOra"));				
					order.setNumeroTelefono(result.getString("NumeroTelefono"));
					order.setStato(result.getString("Stato"));
					order.setIdLocale(result.getLong("idLocale"));
					order.setPagato(result.getBoolean("Pagato"));
					order.setCosto(result.getFloat("Costo"));
					
					query = "SELECT prodotto.idProdotto, prodotto.Nome, Prezzo, idLocale, ImageURL, Tipo, Quantita " + 
							"FROM prodotto, prodottiordini, prodottitipologia " + 
							"WHERE prodotto.idProdotto = prodottiordini.idProdotto AND prodotto.idProdotto = prodottitipologia.idProdotto AND prodottiordini.idOrdine = ?";
					statement = connection.prepareStatement(query);
					statement.setLong(1, order.getId());
					ResultSet res = statement.executeQuery();
					List<Product> listP = new ArrayList<>();
					while (res.next()) {
						Product product = new Product();
						product.setNome(res.getString("Nome"));
						product.setId(res.getLong("idProdotto"));				
						product.setPrezzo(res.getFloat("Prezzo"));
						product.setTipo(res.getString("Tipo"));
						product.setImageURL(res.getString("ImageURL"));
						product.setQuantita(res.getInt("Quantita"));
						
						query = "SELECT ingrediente.idIngrediente, Nome, Costo " + 
								"FROM ingrediente, prodottiingredienti " + 
								"WHERE prodottiingredienti.idProdotto = ? AND ingrediente.idIngrediente = prodottiingredienti.idIngrediente";
						statement = connection.prepareStatement(query);
						statement.setLong(1, res.getLong("idProdotto"));
						ResultSet res1 = statement.executeQuery();
						List<Ingredient> listI = new ArrayList<>();
						while (res1.next()) {
							Ingredient I = new Ingredient();
							I.setId(res1.getLong("idIngrediente"));
							I.setNome(res1.getString("Nome"));
							I.setPrezzo(res1.getFloat("Costo"));
							listI.add(I);
						}
						product.setListIngredienti(listI);
						
						listP.add(product);
					}
					order.setListProducts(listP);					
					
					list.add(order);
					
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
	
	/* 
	 * versione con Lazy Load
	 */
	public Order findByPrimaryKey(Long id) {
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

	public List<Order> findAll(Long idLocal) {
		
		Connection connection = this.dbConnection.getConnection();
		List<Order> list = null;
		Order order = null;
		try {
			PreparedStatement statement;
			String query = "SELECT idOrdine, Stato, Asporto, NumeroTelefono, DataOra, idLocale, Costo, Pagato " + 
					"FROM ordine " + 
					"WHERE idLocale = ? "+
					"ORDER BY DataOra desc";
			
			statement = connection.prepareStatement(query);
			statement.setLong(1, idLocal);

			ResultSet result = statement.executeQuery();
			list = new ArrayList<>(); 
			while (result.next()) {
					order = new Order();
					order.setId(result.getLong("idOrdine"));
					order.setAsporto(result.getBoolean("Asporto"));
					SimpleDateFormat datetime = new SimpleDateFormat("yyyy-MM-dd HH:mm");  
					
					try {
						order.setDateTime(datetime.parse(result.getString("DataOra")));
					} catch (ParseException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					try {
						System.out.println(datetime.parse(result.getString("DataOra")));
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					//order.setDateTime(result.getDate("DataOra"));				
					order.setNumeroTelefono(result.getString("NumeroTelefono"));
					order.setStato(result.getString("Stato"));
					order.setIdLocale(result.getLong("idLocale"));
					order.setPagato(result.getBoolean("Pagato"));

					if(result.getFloat("Costo") != 0)
						order.setCosto(result.getFloat("Costo"));
					
					query = "SELECT prodotto.idProdotto, prodotto.Nome, Prezzo, idLocale, ImageURL, Tipo, Quantita " + 
							"FROM prodotto, prodottiordini, prodottitipologia " + 
							"WHERE prodotto.idProdotto = prodottiordini.idProdotto AND prodotto.idProdotto = prodottitipologia.idProdotto AND prodottiordini.idOrdine = ?";
					statement = connection.prepareStatement(query);
					statement.setLong(1, order.getId());
					ResultSet res = statement.executeQuery();
					List<Product> listP = new ArrayList<>();
					while (res.next()) {
						Product product = new Product();
						product.setNome(res.getString("Nome"));
						product.setId(res.getLong("idProdotto"));				
						product.setPrezzo(res.getFloat("Prezzo"));
						product.setTipo(res.getString("Tipo"));
						product.setImageURL(res.getString("ImageURL"));
						product.setQuantita(res.getInt("Quantita"));
						
						query = "SELECT ingrediente.idIngrediente, Nome, Costo " + 
								"FROM ingrediente, prodottiingredienti " + 
								"WHERE prodottiingredienti.idProdotto = ? AND ingrediente.idIngrediente = prodottiingredienti.idIngrediente";
						statement = connection.prepareStatement(query);
						statement.setLong(1, res.getLong("idProdotto"));
						ResultSet res1 = statement.executeQuery();
						List<Ingredient> listI = new ArrayList<>();
						while (res1.next()) {
							Ingredient I = new Ingredient();
							I.setId(res1.getLong("idIngrediente"));
							I.setNome(res1.getString("Nome"));
							I.setPrezzo(res1.getFloat("Costo"));
							listI.add(I);
						}
						product.setListIngredienti(listI);
						
						listP.add(product);
					}
					order.setListProducts(listP);					
					
					list.add(order);
					
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

	/*
	public List<Order> findAllbyRating(Long idLocal) {
		
		Connection connection = this.dbConnection.getConnection();
		List<Order> list = null;
		Order order = null;
		try {
			PreparedStatement statement;
			String query = "SELECT idOrdine, Stato, Asporto, NumeroTelefono, DataOra, idLocale " + 
					"FROM ordine " + 
					"WHERE idLocale = ? "+
					"ORDER BY DataOra desc";
			
			statement = connection.prepareStatement(query);
			statement.setLong(1, idLocal);

			ResultSet result = statement.executeQuery();
			list = new ArrayList<>(); 
			while (result.next()) {
					order = new Order();
					order.setId(result.getLong("idOrdine"));
					order.setAsporto(result.getBoolean("Asporto"));
					order.setDateTime(result.getDate("DataOra"));				
					order.setNumeroTelefono(result.getString("NumeroTelefono"));
					order.setStato(result.getString("Stato"));
					order.setIdLocale(result.getLong("idLocale"));

					
					query = "SELECT prodotto.idProdotto, prodotto.Nome, Prezzo, idLocale, ImageURL, Tipo, Quantita " + 
							"FROM prodotto, prodottiordini, prodottitipologia " + 
							"WHERE prodotto.idProdotto = prodottiordini.idProdotto AND prodotto.idProdotto = prodottitipologia.idProdotto AND prodottiordini.idOrdine = ?";
					statement = connection.prepareStatement(query);
					statement.setLong(1, order.getId());
					ResultSet res = statement.executeQuery();
					List<Product> listP = new ArrayList<>();
					while (res.next()) {
						Product product = new Product();
						product.setNome(res.getString("Nome"));
						product.setId(res.getLong("idProdotto"));				
						product.setPrezzo(res.getFloat("Prezzo"));
						product.setTipo(res.getString("Tipo"));
						product.setImageURL(res.getString("ImageURL"));
						product.setQuantita(res.getInt("Quantita"));
						
						query = "SELECT ingrediente.idIngrediente, Nome, Costo " + 
								"FROM ingrediente, prodottiingredienti " + 
								"WHERE prodottiingredienti.idProdotto = ? AND ingrediente.idIngrediente = prodottiingredienti.idIngrediente";
						statement = connection.prepareStatement(query);
						statement.setLong(1, res.getLong("idProdotto"));
						ResultSet res1 = statement.executeQuery();
						List<Ingredient> listI = new ArrayList<>();
						while (res1.next()) {
							Ingredient I = new Ingredient();
							I.setId(res1.getLong("idIngrediente"));
							I.setNome(res1.getString("Nome"));
							I.setPrezzo(res1.getFloat("Costo"));
							listI.add(I);
						}
						product.setListIngredienti(listI);
						
						listP.add(product);
					}
					order.setListProducts(listP);					
					
					list.add(order);
					
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
	*/
	public List<Order> findAllByState(Long idLocal, String Stato)
	{
		Connection connection = this.dbConnection.getConnection();
		List<Order> list = null;
		Order order = null;
		try {
			PreparedStatement statement;
			String query = "SELECT idOrdine, Stato, Asporto, NumeroTelefono, DataOra, idLocale, Pagato, Costo " + 
					"FROM ordine " + 
					"WHERE idLocale = ? AND Stato = ? "+
					"ORDER BY DataOra desc";
			
			statement = connection.prepareStatement(query);
			statement.setLong(1, idLocal);
			statement.setString(2, Stato);

			ResultSet result = statement.executeQuery();
			list = new ArrayList<>(); 
			while (result.next()) {
					order = new Order();
					order.setId(result.getLong("idOrdine"));
					order.setAsporto(result.getBoolean("Asporto"));
					SimpleDateFormat datetime = new SimpleDateFormat("yyyy-MM-dd HH:mm");  
					
					try {
						order.setDateTime(datetime.parse(result.getString("DataOra")));
					} catch (ParseException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					try {
						System.out.println(datetime.parse(result.getString("DataOra")));
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					//order.setDateTime(result.getDate("DataOra"));				
					order.setNumeroTelefono(result.getString("NumeroTelefono"));
					order.setStato(result.getString("Stato"));
					order.setIdLocale(result.getLong("idLocale"));
					order.setPagato(result.getBoolean("Pagato"));
					order.setCosto(result.getFloat("Costo"));
					
					query = "SELECT prodotto.idProdotto, prodotto.Nome, Prezzo, idLocale, ImageURL, Tipo, Quantita " + 
							"FROM prodotto, prodottiordini, prodottitipologia " + 
							"WHERE prodotto.idProdotto = prodottiordini.idProdotto AND prodotto.idProdotto = prodottitipologia.idProdotto AND prodottiordini.idOrdine = ?";
					statement = connection.prepareStatement(query);
					statement.setLong(1, order.getId());
					ResultSet res = statement.executeQuery();
					List<Product> listP = new ArrayList<>();
					while (res.next()) {
						Product product = new Product();
						product.setNome(res.getString("Nome"));
						product.setId(res.getLong("idProdotto"));				
						product.setPrezzo(res.getFloat("Prezzo"));
						product.setTipo(res.getString("Tipo"));
						product.setImageURL(res.getString("ImageURL"));
						product.setQuantita(res.getInt("Quantita"));
						
						query = "SELECT ingrediente.idIngrediente, Nome, Costo " + 
								"FROM ingrediente, prodottiingredienti " + 
								"WHERE prodottiingredienti.idProdotto = ? AND ingrediente.idIngrediente = prodottiingredienti.idIngrediente";
						statement = connection.prepareStatement(query);
						statement.setLong(1, res.getLong("idProdotto"));
						ResultSet res1 = statement.executeQuery();
						List<Ingredient> listI = new ArrayList<>();
						while (res1.next()) {
							Ingredient I = new Ingredient();
							I.setId(res1.getLong("idIngrediente"));
							I.setNome(res1.getString("Nome"));
							I.setPrezzo(res1.getFloat("Costo"));
							listI.add(I);
						}
						product.setListIngredienti(listI);
						
						listP.add(product);
					}
					order.setListProducts(listP);					
					
					list.add(order);
					
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
	
	public List<Order> findAllByProduct(Long idLocal, Long idProduct)
	{
		Connection connection = this.dbConnection.getConnection();
		List<Order> list = null;
		Order order = null;
		try {
			PreparedStatement statement;
			String query = "SELECT idOrdine, Stato, Asporto, NumeroTelefono, DataOra, idLocale, Pagato " + 
					"FROM ordine " + 
					"WHERE idLocale = ? "+
					"ORDER BY DataOra desc";
			
			statement = connection.prepareStatement(query);
			statement.setLong(1, idLocal);

			ResultSet result = statement.executeQuery();
			list = new ArrayList<>(); 
			while (result.next()) {
					order = new Order();
					order.setId(result.getLong("idOrdine"));
					order.setAsporto(result.getBoolean("Asporto"));
					SimpleDateFormat datetime = new SimpleDateFormat("yyyy-MM-dd HH:mm");  
					
					try {
						order.setDateTime(datetime.parse(result.getString("DataOra")));
					} catch (ParseException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					try {
						System.out.println(datetime.parse(result.getString("DataOra")));
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					//order.setDateTime(result.getDate("DataOra"));				
					order.setNumeroTelefono(result.getString("NumeroTelefono"));
					order.setStato(result.getString("Stato"));
					order.setIdLocale(result.getLong("idLocale"));
					order.setPagato(result.getBoolean("Pagato"));

					
					query = "SELECT prodotto.idProdotto, prodotto.Nome, Prezzo, idLocale, ImageURL, Tipo, Quantita " + 
							"FROM prodotto, prodottiordini, prodottitipologia " + 
							"WHERE prodotto.idProdotto = prodottiordini.idProdotto AND prodotto.idProdotto = prodottitipologia.idProdotto AND prodottiordini.idOrdine = ?";
					statement = connection.prepareStatement(query);
					statement.setLong(1, order.getId());

					boolean idoneo = false;
					ResultSet res = statement.executeQuery();
					List<Product> listP = new ArrayList<>();
					while (res.next()) {
						Product product = new Product();
						product.setNome(res.getString("Nome"));
						product.setId(res.getLong("idProdotto"));				
						product.setPrezzo(res.getFloat("Prezzo"));
						product.setTipo(res.getString("Tipo"));
						product.setImageURL(res.getString("ImageURL"));
						product.setQuantita(res.getInt("Quantita"));
						if(product.getId() == idProduct)
							idoneo=true;
						
						query = "SELECT ingrediente.idIngrediente, Nome, Costo " + 
								"FROM ingrediente, prodottiingredienti " + 
								"WHERE prodottiingredienti.idProdotto = ? AND ingrediente.idIngrediente = prodottiingredienti.idIngrediente";
						statement = connection.prepareStatement(query);
						statement.setLong(1, res.getLong("idProdotto"));
						ResultSet res1 = statement.executeQuery();
						List<Ingredient> listI = new ArrayList<>();
						while (res1.next()) {
							Ingredient I = new Ingredient();
							I.setId(res1.getLong("idIngrediente"));
							I.setNome(res1.getString("Nome"));
							I.setPrezzo(res1.getFloat("Costo"));
							listI.add(I);
						}
						product.setListIngredienti(listI);
						
						listP.add(product);
					}
					order.setListProducts(listP);					
					
					if(idoneo)
						list.add(order);
					
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
	
	public void update(Order order) {
		
		Connection connection = this.dbConnection.getConnection();
		try {
			String update = "update ordine SET Stato = ?, Asporto = ?, NumeroTelefono = ?, DataOra = ?, idLocale = ?, Costo = ?, Pagato = ? WHERE idOrdine = ?";
			PreparedStatement statement = connection.prepareStatement(update);
			statement.setString(1, order.getStato());
			statement.setBoolean(2, order.getAsporto());
			statement.setString(3, order.getNumeroTelefono());
			SimpleDateFormat datetime = new SimpleDateFormat("yyyy-MM-dd HH:mm");            
			statement.setString(4, datetime.format(order.getDateTime()));
			statement.setLong(5, order.getIdLocale());
			statement.setFloat(6, order.getTotaleCosto());
			statement.setBoolean(7, order.getPagato());
			statement.setLong(8, order.getId());
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

	public void delete(Long idOrdine) {
		
		Connection connection = this.dbConnection.getConnection();
		try {
			String delete = "delete FROM prodottiordini WHERE idOrdine = ? ";
			PreparedStatement statement = connection.prepareStatement(delete);
			statement.setLong(1, idOrdine);
			statement.executeUpdate();
			//connection.commit();
			
			delete = "delete FROM ordine WHERE idOrdine = ? ";
			statement = connection.prepareStatement(delete);
			statement.setLong(1, idOrdine);

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
			 * */
			statement.executeUpdate();
			//connection.commit();
		} catch (SQLException e) {
			throw new PersistenceException(e.getMessage());
		} finally {
			try {
				connection.close();
			} catch (SQLException e) {
				throw new PersistenceException(e.getMessage());
			}
		}
	}
	
	public void deleteOrderProduct(Long idOrdine, Long idProduct)
	{
		Connection connection = this.dbConnection.getConnection();
		try {
			String delete = "delete FROM prodottiordini WHERE idOrdine = ? AND idProdotto = ?";
			PreparedStatement statement = connection.prepareStatement(delete);
			statement.setLong(1, idOrdine);
			statement.setLong(2, idProduct);
			//connection.commit();
			
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
			 * */
			statement.executeUpdate();
			//connection.commit();
		} catch (SQLException e) {
			throw new PersistenceException(e.getMessage());
		} finally {
			try {
				connection.close();
			} catch (SQLException e) {
				throw new PersistenceException(e.getMessage());
			}
		}
	}

	@Override
	public void delete(Order corso) {
		// TODO Auto-generated method stub
		
	}
}
