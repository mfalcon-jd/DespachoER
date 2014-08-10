package bd;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import conexion.Conexion;

public class DbInsertarAgenda {
	
	Conexion cn;
	Statement comando;
	PreparedStatement pstm;
	public Exception excepcion;
	String sWhereTelefono;
	//public boolean insertarAgenda(String sWhereAgenda, String sWhereTelefono)
	public boolean insertarAgenda(String sWhereAgenda, Object [][] vDatosTelefono)
	{
		cn = new Conexion();  		
		int idContacto;
		boolean bBandera = false;
        try {
	        	pstm = cn.getConnection().prepareStatement("Select idContacto as Total FROM agenda Order By idContacto desc Limit 1");
				ResultSet res = pstm.executeQuery();
				//pstm.setString(1, sClaveCliente);
				//res.next();
				
				
				if(res.first())
        		{
					idContacto = res.getInt("Total") + 1;
					System.out.println("contador 1 " + idContacto);
            		
        		}
        		else 
        		{
        			System.out.println("NO Tiene Valor");
        			idContacto = 1;
        		}
				
				res.close();
        	  
        	  String sqrt =  sWhereAgenda;
        	  System.out.println("sqrt " + sqrt);
              //Connection conexion=DriverManager.getConnection("jdbc:mysql://localhost/bd1","root" ,"");
              comando = cn.getConnection().createStatement();
              comando.executeUpdate(sqrt);
              //cn.desconectar();
              
              for(int i = 0;i < vDatosTelefono.length;i++)
              {
            	  sWhereTelefono = "insert into telefonos(telefono,tipo,email,fax,idContacto) values ('" + vDatosTelefono[i][1] + "',";
            	  
            	  if(vDatosTelefono[i][0].equals("")) sWhereTelefono += " '" + 0 + "',";
            	  else sWhereTelefono += " '" + vDatosTelefono[i][0] + "',";
     			 
            	  sWhereTelefono += " '" + vDatosTelefono[i][3] + "',";
     			 
            	  if(vDatosTelefono[i][2].equals("")) sWhereTelefono += " '" + 0 + "',";
            	  else sWhereTelefono += " '" + vDatosTelefono[i][2] + "',";
     			 
            	  sWhereTelefono += " '" + idContacto + "')";
            	  
            	  System.out.println("sWhereTelefono " + sWhereTelefono);
                  //comando = cn.getConnection().createStatement();
                  comando.executeUpdate( sWhereTelefono);
            	  
              }
              
              cn.desconectar();
              
              System.out.println("se registraron los datos");
              bBandera = true;
             
        } catch (Exception ex){
        	excepcion = ex;
            System.out.println("GETmESSAGE " + ex.getMessage());             
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
	


}
