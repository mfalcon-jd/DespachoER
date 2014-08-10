package bd;

import java.sql.*;
import conexion.Conexion;

public class DbAlumnoActualizar {
	Conexion con;
	
	public DbAlumnoActualizar()
	{
		con = new Conexion();
		try
		{
			Statement cmd = con.getConnection().createStatement();
			cmd.executeUpdate("UPDATE datos SET nombre = 'liliana' WHERE id = '3' ");
			con.desconectar();
			System.out.println("Los datos han sido actualizados correctamente");
			
		}
		catch(SQLException e)
		{
			System.out.println(e);
		}
	}
	
	public static void main(String mlfl[])
	{
		DbAlumnoActualizar  upd = new DbAlumnoActualizar();
	}
}
