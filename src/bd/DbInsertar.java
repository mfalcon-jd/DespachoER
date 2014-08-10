package bd;

import conexion.Conexion;
import java.sql.*;
import java.util.Vector;

import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class DbInsertar {
	Conexion cn;
	Statement comando;
	public Exception excepcion;
	
	public boolean insertarCliente(String[] vValoresCliente, DefaultTableModel modelo )
	{
		cn = new Conexion();  
		boolean bBandera = false;
        try {
        		String sWhereTelefono;
              //Connection conexion=DriverManager.getConnection("jdbc:mysql://localhost/bd1","root" ,"");
        		String consulta = "insert into clientes(fechaNacimiento,curp,nombre,apellidoPaterno,apellidoMaterno,direccion,email,observaciones" +
          		") values ('"+vValoresCliente[5]+"', '"+vValoresCliente[3]+"', '"+vValoresCliente[0]+"','"+vValoresCliente[1]+"','"+vValoresCliente[2]+"','"+vValoresCliente[4]+"'," +
          				"'"+vValoresCliente[6]+"','"+vValoresCliente[7]+"')";
        		System.out.println("consulta cliente " + consulta);
              comando = cn.getConnection().createStatement();
              cn.getConnection().setAutoCommit(false);      
              
              comando.executeUpdate(consulta);
              //cn.getConnection().setAutoCommit(false);
              System.out.println("despues de insertar cliente");
              System.out.println("numeros de telefonos " + modelo.getRowCount());
              for (int i = 0; i < modelo.getRowCount(); i++)
              {
            	  System.out.println("valor modelo " + modelo.getValueAt(i, 1) );
            	  sWhereTelefono = "insert into telefonos(telefono,tipo,curp) values ('" + modelo.getValueAt(i, 0) + "'," + " ' " + modelo.getValueAt(i, 1) + "'," + " '" + vValoresCliente[3] + "')";
            	  System.out.println("consulta telefono " + sWhereTelefono);
            	  
                  comando.executeUpdate( sWhereTelefono);
            	  
              }
                          
              cn.getConnection().commit();
              cn.getConnection().setAutoCommit(true);
              cn.desconectar();
              System.out.println("se registraron los datos");
              bBandera = true;
             
        } catch (Exception ex){
        	//excepcion = ex;
        	excepcion = new Exception("Error al Guardar");
            System.out.println("EXCEPCION AL GUARDAR " + ex.getMessage());             
        }
        finally
        {
        	try
			{
        		comando.close();
				cn.desconectar();
				return bBandera;
			}
			catch(Exception e)
			{
				//JOptionPane.showMessageDialog(this, "Los Datos Han Sido Guardados Exitosamente!!", "Dato Insertados", JOptionPane.INFORMATION_MESSAGE );
				
			}
			
        }
		return bBandera;		
	}
	
	public void deshacer()
	{
		//cn = new Conexion();
		try {			
			cn.getConnection().setAutoCommit(false);
			cn.getConnection().rollback();
			System.out.println("Rollback");
		} catch (SQLException e) {		
			e.printStackTrace();
			System.out.println("Error Rollback: " + e);
		}
	}
	
	public void confirmar()
	{
		cn = new Conexion();
		try
		{
			cn.getConnection().setAutoCommit(true);
			//cn.getConnection().commit();
		}
		catch(Exception ex)
		{
			System.out.println("Error Commit: " + ex);
		}
	}
}
