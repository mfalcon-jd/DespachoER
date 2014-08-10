package bd;

import java.sql.Statement;
import conexion.Conexion;

public class DbEliminarAgenda 
{
	Conexion  cn;
	Statement cmd;
	String    sWhereTelefono ;
	public Exception exception;
	
	public boolean eliminarContacto(int iClaveContacto)
	{
		cn = new Conexion();
		String sqrt  = "DELETE from agenda     WHERE idContacto = '" + iClaveContacto +"' ";
		//String sqrt2 = "DELETE from telefonos  WHERE idContacto = '" + iClaveContacto +"' ";
		
		System.out.println("SQRT " + sqrt);
		
		boolean bBandera = false;
		try
		{
			cmd = cn.getConnection().createStatement();
			cmd.executeUpdate(sqrt);			
			//cmd.executeUpdate(sqrt2);	
			
			System.out.println("Los datos han sido actualizados correctamente");
			bBandera = true;
			
			
		}
		catch(Exception e)
		{
			exception = e;
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
				exception = e;
				//JOptionPane.showMessageDialog(this, "Los Datos Han Sido Guardados Exitosamente!!", "Dato Insertados", JOptionPane.INFORMATION_MESSAGE );
				
			}
			
		}
		return bBandera;
	}
	
	public boolean eliminarTelefono(int iClaveTelefono)
	{
		cn = new Conexion();
		String sqrt  = "DELETE from telefonos     WHERE id = '" + iClaveTelefono +"' ";
		//String sqrt2 = "DELETE from telefonos  WHERE idContacto = '" + iClaveContacto +"' ";
		
		System.out.println("SQRT " + sqrt);
		
		boolean bBandera = false;
		try
		{
			cmd = cn.getConnection().createStatement();
			cmd.executeUpdate(sqrt);			
			//cmd.executeUpdate(sqrt2);	
			
			System.out.println("Los datos han sido actualizados correctamente");
			bBandera = true;
			
			
		}
		catch(Exception e)
		{
			exception = e;
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
				exception = e;
				//JOptionPane.showMessageDialog(this, "Los Datos Han Sido Guardados Exitosamente!!", "Dato Insertados", JOptionPane.INFORMATION_MESSAGE );
				
			}
			
		}
		return bBandera;
	}
	
}
