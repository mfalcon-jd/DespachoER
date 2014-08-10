package bd;
import conexion.Conexion;
import java.sql.*;
import javax.swing.*;

import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class DbActualizar {
	Conexion cn;
	Statement cmd;
	public Exception excepcion;
	public boolean actualizarCliente(String sConsulta, String sClaveCliente, DefaultTableModel modelo)
	{
		cn = new Conexion();
		String sqrt = "UPDATE clientes SET " + sConsulta + " WHERE curp = '" + sClaveCliente +"' ";
		
		System.out.println("SQRT " + sqrt);
		
		boolean bBandera = false;
		try
		{
			cmd = cn.getConnection().createStatement();
			cn.getConnection().setAutoCommit(false);
			cmd.executeUpdate(sqrt);
			
			for (int i = 0; i < modelo.getRowCount(); i++)
            {
          	  String idTel =    	String.valueOf(modelo.getValueAt(i, 1));
          	  
          	  if (idTel.length() == 0)
          	  {
          		sqrt = "insert into telefonos(telefono,tipo,curp) values ('" + modelo.getValueAt(i, 2) + "'," + " ' " + modelo.getValueAt(i, 3) + "'," + " '" + sClaveCliente + "')";
          		System.out.println("insertar tel cliente desde mod " + sqrt);
          		cmd.executeUpdate(sqrt);
          		
          	  }
          	  else
          	  {
          		  sqrt = "UPDATE telefonos SET telefono = '"+modelo.getValueAt(i, 2)+"', tipo = '" +modelo.getValueAt(i, 3)+"' where id = '"+modelo.getValueAt(i, 1)+"'";
          		System.out.println("update tel cliente " + sqrt);
          		  cmd.executeUpdate(sqrt);
          	  }          	  
          	  
            }
			
			System.out.println("Los datos han sido actualizados correctamente");
			bBandera = true;
			
		}
		catch(Exception e)
		{
			excepcion = e;
			System.out.println("e.getMessage " + e.getMessage());
			System.out.println(e);
			
			return bBandera;
		}
		finally
		{
			try
			{
				cn.getConnection().commit();
	            cn.getConnection().setAutoCommit(true);
				cmd.close();
				cn.desconectar();
			}
			catch(Exception e)
			{
				excepcion = e;
				//JOptionPane.showMessageDialog(this, "Los Datos Han Sido Guardados Exitosamente!!", "Dato Insertados", JOptionPane.INFORMATION_MESSAGE );
				
			}
			
		}
		return bBandera;
	}
}
