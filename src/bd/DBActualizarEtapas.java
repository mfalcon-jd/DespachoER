package bd;

import java.sql.Statement;

import conexion.Conexion;

public class DBActualizarEtapas 
{
	Conexion  cn;
	Statement cmd;
	String    sWhereTelefono ;
	public Exception exception;
	public boolean actualizarTodasEtapas( Object[][] vDatosEtapas)
	{
		cn = new Conexion();
		
		boolean bBandera = false;
		try
		{
			cmd = cn.getConnection().createStatement();
			cn.getConnection().setAutoCommit(false);
			
			for(int i = 0;i < vDatosEtapas.length;i++)
            {
				System.out.println("Inicio ");
				sWhereTelefono = "";
								
				if(sWhereTelefono.length() == 0 ) sWhereTelefono += "nombreEtapa = '" + vDatosEtapas[i][2] + "'";
	    		else sWhereTelefono += ", nombreEtapa = '" + vDatosEtapas[i][2] + "'";
				System.out.println(vDatosEtapas[i][3]+ "tamaño "+vDatosEtapas.length );
				if(sWhereTelefono.length() == 0 ) sWhereTelefono += "descripcionEtapa = '" + vDatosEtapas[i][3] + "'";
	    		else sWhereTelefono += ", descripcionEtapa = '" + vDatosEtapas[i][3] + "'";
				
				String sqrt2 = "UPDATE etapas SET " + sWhereTelefono + " WHERE idEtapa = '" + vDatosEtapas[i][1] +"' AND idCaso = '" + vDatosEtapas[i][0] + "'";
            	System.out.println("Sqrt2 " + sqrt2);
            	  
            	cmd.executeUpdate( sqrt2);
            	  
            }
			cn.getConnection().commit();
            cn.getConnection().setAutoCommit(true);
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
	
	public boolean actualizarEtapa( String idCaso, String idEtapa, String nombreEtapa, String descripcionEtapa)
	{
		cn = new Conexion();
		
		boolean bBandera = false;
		try
		{
			cmd = cn.getConnection().createStatement();
			cn.getConnection().setAutoCommit(false);
			System.out.println("Inicio ");
			String sWhereEtapa = "";
			
			if(sWhereEtapa.length() == 0 ) sWhereEtapa += "nombreEtapa = '" + nombreEtapa + "'";
    		else sWhereEtapa += ", nombreEtapa = '" + nombreEtapa + "'";
			
			if(sWhereEtapa.length() == 0 ) sWhereEtapa += "descripcionEtapa = '" + descripcionEtapa + "'";
    		else sWhereEtapa += ", descripcionEtapa = '" + descripcionEtapa + "'";
			
			String sqrt2 = "UPDATE etapas SET " + sWhereEtapa + " WHERE idEtapa = '" + idEtapa +"' AND idCaso = '" + idCaso + "'";
        	System.out.println("Sqrt2 " + sqrt2);
        	  
        	cmd.executeUpdate( sqrt2);
			
        	cn.getConnection().commit();
            cn.getConnection().setAutoCommit(true);
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
