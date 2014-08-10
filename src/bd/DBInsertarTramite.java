package bd;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import conexion.Conexion;

public class DBInsertarTramite {
 Conexion cn;
 PreparedStatement pstm;
 public int idTramite = 0;
 Statement comando;
 public Exception excepcion;
	
	public boolean insertarTramite(Object[] vValoresCliente)
	{
		cn = new Conexion();  
		boolean bBandera = false;
        try {
        		pstm = cn.getConnection().prepareStatement("Select NoTramite as Total FROM tramite Order By NoTramite desc Limit 1");
        		System.out.println("antes de executeQuery");
        		ResultSet res = pstm.executeQuery();
        		
        		if(res.first())
        		{
        			System.out.println("Tiene Valor");
        			idTramite = res.getInt("Total") + 1;
            		
        		}
        		else 
        		{
        			System.out.println("NO Tiene Valor");
        			idTramite = 1;
        		}
        		
        		
        		System.out.println("idTramite " + idTramite);
        		res.close();
        		
              //Connection conexion=DriverManager.getConnection("jdbc:mysql://localhost/bd1","root" ,"");
              comando = cn.getConnection().createStatement();
              comando.executeUpdate("insert into tramite (idCaso,curp,observaciones,tipoPago,anticipo,idEtapa) values ('" +vValoresCliente[0]+"', '"+vValoresCliente[1]+"','"+vValoresCliente[2]+"','"+vValoresCliente[3]+"','"+vValoresCliente[4]+"','"+vValoresCliente[5]+"')");
              cn.desconectar();
              System.out.println("se registraron los datos");
              bBandera = true;
             
        } catch (Exception ex){
        	excepcion =ex;
        	System.out.println(ex.getMessage());
            System.out.println(ex);             
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
				excepcion = e;
				//JOptionPane.showMessageDialog(this, "Los Datos Han Sido Guardados Exitosamente!!", "Dato Insertados", JOptionPane.INFORMATION_MESSAGE );
				
			}
			
        }
		return bBandera;
	}

	public int getIdTramite()
	{
		return idTramite;
	}
}
