package conexion;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
public class Conexion {   
	//static String bd = "bd_samplelogin";
	static String bd = "despachoer";   
	static String login = "root";   
	static String password = "123456";   
	static String url = "jdbc:mysql://localhost/" + bd;   
	Connection conn = null;   
	public Conexion() {      
		try {        
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(url, login, password);
		}
		catch(SQLException e){
			System.out.println(e);
		}
		catch(ClassNotFoundException e){
			System.out.println(e);
		}
	}
	public Connection getConnection(){
		return conn;
	}
	public void desconectar(){
		conn = null;
	}
}
