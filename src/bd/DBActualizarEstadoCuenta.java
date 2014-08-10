package bd;

import conexion.Conexion;
import java.sql.*;
import javax.swing.JOptionPane;
import javax.swing.*;

import javax.swing.JOptionPane;

public class DBActualizarEstadoCuenta {
	Conexion cn;
	Statement cmd;
	public boolean actualizarEstadoCuenta(String sConsulta, String sClaveTramite)
	{
		cn = new Conexion();
		String sqrt = "UPDATE estadocuenta SET " + sConsulta + " WHERE idTramite = '" + sClaveTramite +"' ";
		
		System.out.println("SQRT " + sqrt);
		
		boolean bBandera = false;
		try
		{
			cmd = cn.getConnection().createStatement();
			cn.getConnection().setAutoCommit(false);
			cmd.executeUpdate(sqrt);
			
			cn.getConnection().commit();
            cn.getConnection().setAutoCommit(true);
			
			System.out.println("Los datos EDO CUENTA han sido actualizados correctamente");
			bBandera = true;
			
		}
		catch(Exception e)
		{
			System.out.println("e.getMessage " + e.getMessage());
			System.out.println(e);
			
			return bBandera;
		}
		finally
		{
			try
			{
				cmd.close();
				cn.desconectar();
			}
			catch(Exception e)
			{
				//JOptionPane.showMessageDialog(this, "Los Datos Han Sido Guardados Exitosamente!!", "Dato Insertados", JOptionPane.INFORMATION_MESSAGE );
				
			}
			
		}
		return bBandera;
	}
}
