package database;
import java.sql.*; 

public final class DBConnection {
	 
	private static final String dbURI = "jdbc:mysql://localhost:3306/ristorante?useSSL=false&useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";;
	private static final String Username = "root";;
	private static final String Password = "sparafra"; ;
	
	private static DBConnection dbconnection;
	private static Connection connection;
	
	public DBConnection()
	{
		 
	}
	
	public static DBConnection getInstance()
	{
		if(dbconnection == null)
			dbconnection = new DBConnection();
		return dbconnection;
	}
	
	public static Connection getConnection() 
	{
		try {
			if(connection == null || connection.isClosed())
			{
				try
				{  
					Class.forName("com.mysql.jdbc.Driver");  
					connection = DriverManager.getConnection(dbURI, Username, Password);  
					
				}
				catch(Exception e){ System.out.println(e);}  
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return connection;
	}
	

}
