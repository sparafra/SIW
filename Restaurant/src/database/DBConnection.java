package database;
import java.sql.*; 

public class DBConnection {
	 
	String dbURI;
	String Username;
	String Password;
	
	public DBConnection(String dbURI, String User, String Password) 
	{
		this.dbURI = dbURI;
		this.Username = User;
		this.Password = Password; 
			  
	}
	public DBConnection()
	{
		//this.dbURI = "jdbc:mysql://remotemysql.com:3306/kZEBaP1qWY?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
		//this.dbURI = "jdbc:mysql://localhost:3306/ristorante?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
		this.dbURI = "jdbc:mysql://remotemysql.com:3306/C25GmPKXXu?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
		//this.Username = "kZEBaP1qWY";
		//this.Username = "test";
		this.Username = "C25GmPKXXu";
		//this.Password = "ykk6AASyPW"; 
		//this.Password = "test"; 
		this.Password = "2jzyvEoPzj";
	}
	
	public Connection getConnection()
	{
		Connection con = null;
		try
		{  
			Class.forName("com.mysql.jdbc.Driver");  
			con=DriverManager.getConnection(dbURI, Username, Password);  
			
		}
		catch(Exception e){ System.out.println(e);}  
		
		return con;
	}
	
	
	
	
	

}
