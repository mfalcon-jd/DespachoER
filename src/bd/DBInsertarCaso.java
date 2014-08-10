package bd;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import conexion.Conexion;

public class DBInsertarCaso 
{
	Conexion cn;
	Statement comando;
	PreparedStatement pstm;
	public int idCaso = 0;
	public Exception excepcion;
	public boolean insertarCaso(Object[] vValoresCaso)
	{
		cn = new Conexion();  
		boolean bBandera = false;
        try {
	        	pstm = cn.getConnection().prepareStatement("Select idCaso as Total FROM casos Order By idCaso desc Limit 1");
				ResultSet res = pstm.executeQuery();
				
				if(res.first())
        		{
					System.out.println("Tiene Valor");
					idCaso = res.getInt("Total") + 1;
					
        		}
				else
				{
					System.out.println("NO Tiene Valor");
					idCaso = 1;
				}
								
				System.out.println("idCaso " + idCaso);
				res.close();
				
              //Connection conexion=DriverManager.getConnection("jdbc:mysql://localhost/bd1","root" ,"");
              comando = cn.getConnection().createStatement();
              comando.executeUpdate("insert into casos(nombreCaso,precio,descripcion" + ") values ('"+vValoresCaso[0]+"', '"+vValoresCaso[1]+"', '"+vValoresCaso[2]+"')");
              cn.desconectar();
              System.out.println("se registraron los datos");
              bBandera = true;
             
        } catch (Exception ex){
        	excepcion = ex;
            System.out.println(ex.getMessage());             
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

	public int getIdCaso(){return idCaso;}

}
