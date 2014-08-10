package bd;

import java.sql.Statement;

import conexion.Conexion;

public class DBActualizarCasos 
{
	Conexion  cn;
	Statement cmd;
	public Exception exception;
	
	public boolean actualizarCaso(String sConsulta, String sClaveCaso)
	{
		cn = new Conexion();
		String sqrt = "UPDATE casos SET " + sConsulta + " WHERE idCaso = '" + sClaveCaso +"' ";
		
		System.out.println("SQRT " + sqrt);
		
		boolean bBandera = false;
		try
		{
			cmd = cn.getConnection().createStatement();
			cn.getConnection().setAutoCommit(false);
			
			cmd.executeUpdate(sqrt);			
			System.out.println("Los datos han sido actualizados correctamente");
			
			cn.getConnection().commit();
            cn.getConnection().setAutoCommit(true);
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
