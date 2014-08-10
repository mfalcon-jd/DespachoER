package bd;

import java.sql.Statement;

import javax.swing.table.DefaultTableModel;

import conexion.Conexion;

public class DbActualizarAgenda 
{
	Conexion  cn;
	Statement cmd;
	String    sWhereTelefono ;
	public Exception exception;
	public boolean actualizarAgenda(String sConsulta, String sClaveContacto, Object[][] vDatosTelefono, DefaultTableModel modelo)
	{
		cn = new Conexion();
		String sqrt = "UPDATE agenda SET " + sConsulta + " WHERE idContacto = '" + sClaveContacto +"' ";
		
		System.out.println("SQRT " + sqrt);
		
		boolean bBandera = false;
		try
		{
			cmd = cn.getConnection().createStatement();
			cn.getConnection().setAutoCommit(false);
			cmd.executeUpdate(sqrt);			
			
			/*for(int i = 0;i < vDatosTelefono.length;i++)
            {
				sWhereTelefono = "";
				System.out.println("Dentro del for " + vDatosTelefono[i][2]);
				if(sWhereTelefono.length() == 0 ) sWhereTelefono += "telefono = '" + vDatosTelefono[i][2] + "'";
	    		else sWhereTelefono += ", telefono = '" + vDatosTelefono[i][2] + "'";
				System.out.println(vDatosTelefono[i][3]+ "tamaño "+vDatosTelefono[i].length );
				if(sWhereTelefono.length() == 0 ) sWhereTelefono += "tipo = '" + vDatosTelefono[i][3] + "'";
	    		else sWhereTelefono += ", tipo = '" + vDatosTelefono[i][3] + "'";
				
				if(sWhereTelefono.length() == 0 ) sWhereTelefono += "email = '" + vDatosTelefono[i][4] + "'";
	    		else sWhereTelefono += ", email = '" + vDatosTelefono[i][4] + "'";
				
				if(sWhereTelefono.length() == 0 ) sWhereTelefono += "fax = '" + vDatosTelefono[i][5] + "'";
	    		else sWhereTelefono += ", fax = '" + vDatosTelefono[i][5] + "'";
				System.out.println("sWhere " + sWhereTelefono);
				
            	 
     			 
            	  String sqrt2 = "UPDATE telefonos SET " + sWhereTelefono + " WHERE id = '" + vDatosTelefono[i][1] +"' ";
            	  System.out.println("Sqrt2 " + sqrt2);
            	  
            	  cmd.executeUpdate( sqrt2);
            	  
            }*/
			
			for (int i = 0; i < modelo.getRowCount(); i++)
            {
          	  String idTel =    	String.valueOf(modelo.getValueAt(i, 3));
          	  
          	  if (idTel.length() == 0)
          	  {
          		System.out.println("INSERTAR TEL AGENDA DEESDE MODIFICAR telefono " + modelo.getValueAt(i, 1));
          		System.out.println("INSERTAR TEL AGENDA DEESDE MODIFICAR tipo " + modelo.getValueAt(i, 6));
          		System.out.println("INSERTAR TEL AGENDA DEESDE MODIFICAR email " + modelo.getValueAt(i, 2));
          		System.out.println("INSERTAR TEL AGENDA DEESDE MODIFICAR fax " + modelo.getValueAt(i, 4));
          		System.out.println("INSERTAR TEL AGENDA DEESDE MODIFICAR idContacto " + modelo.getValueAt(i, 0));
          		
          		sqrt = "insert into telefonos(telefono,tipo,email,fax,idContacto) values ('" + modelo.getValueAt(i, 1) + "'," + " ' " + modelo.getValueAt(i, 6) + "'," + " '" + modelo.getValueAt(i, 2) + "'," + " ' " + modelo.getValueAt(i, 4) + "'," + " '" + modelo.getValueAt(i, 0) + "')";
          		System.out.println("INSERTAR TEL AGENDA DEESDE MODIFICAR " + sqrt);
          		cmd.executeUpdate(sqrt);
          		
          	  }
          	  else
          	  {
          		  sqrt = "UPDATE telefonos SET telefono = '"+modelo.getValueAt(i, 1)+"', tipo = '" +modelo.getValueAt(i, 6)+"', email = '" +modelo.getValueAt(i, 2)+"', fax = '" +modelo.getValueAt(i, 4)+"' where id = '"+modelo.getValueAt(i, 3)+"'";
          		System.out.println("update tel AGENDA " + sqrt);
          		  cmd.executeUpdate(sqrt);
          	  }          	  
          	  
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


}
