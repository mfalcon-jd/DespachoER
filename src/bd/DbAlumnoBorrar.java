package bd;

import java.sql.*;
import conexion.Conexion;

public class DbAlumnoBorrar {
	Conexion cn;
	
	public DbAlumnoBorrar()
	{
		cn = new Conexion();
		try
		{
			Statement cmd = cn.getConnection().createStatement();
			cmd.executeUpdate("DELETE FROM datos WHERE id = '2'");
			cn.desconectar();
			System.out.println("Los datos han sido eliminados con éxito");
		}
		catch(SQLException e)
		{
			System.out.println(e);
		}
	}
	public static void main(String mlfl[])
	{
	DbAlumnoBorrar del = new DbAlumnoBorrar();	
	}
}
