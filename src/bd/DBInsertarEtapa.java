package bd;

import java.sql.SQLException;
import java.sql.Statement;

import conexion.Conexion;
import java.sql.*;

public class DBInsertarEtapa 
{
	Conexion cn;
	Statement comando;
	public Exception excepcion;
	private int idEtapaAgregar = 0;
	public boolean insertarEtapa(Object[][] vValoresEtapa, int idCaso)
	{
		cn = new Conexion();  
		boolean bBandera = false;
        try {
        	String sWhereCaso ="";
              //Connection conexion=DriverManager.getConnection("jdbc:mysql://localhost/bd1","root" ,"");
              comando = cn.getConnection().createStatement();
              //comando.executeUpdate("insert into etapas(NoTramite,nombreEtapa" + ") values ('"+vValoresEtapa[0]+"', '"+vValoresEtapa[1]+"')");
              for(int i = 0;i < vValoresEtapa.length;i++)
              {
            	  int iFolioEtapa = i + 1;
            	  sWhereCaso = "insert into etapas(idEtapa,idCaso,nombreEtapa,descripcionEtapa) values ('" + iFolioEtapa + "',";
            	  
            	  sWhereCaso += "'" + idCaso + "',";
            	  
            	  sWhereCaso += " '" + vValoresEtapa[i][0] + "',";
     			 
            	  sWhereCaso += " '" + vValoresEtapa[i][1] + "')";
     			 
            	  System.out.println("sWhereCaso " + sWhereCaso);
                  //comando = cn.getConnection().createStatement();
                  comando.executeUpdate( sWhereCaso);
                  
            	  
              }
              
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
	
	public boolean agregarEtapa(int idCaso, String nombreEtapaagregar, String descripcion)
	{
		cn = new Conexion();  
		boolean bBandera = false;
		
        try {
        	String sWhereCaso ="";
              //Connection conexion=DriverManager.getConnection("jdbc:mysql://localhost/bd1","root" ,"");
              comando = cn.getConnection().createStatement();
              cn.getConnection().setAutoCommit(false);
              //comando.executeUpdate("insert into etapas(NoTramite,nombreEtapa" + ") values ('"+vValoresEtapa[0]+"', '"+vValoresEtapa[1]+"')");
              DBConsultarEtapas objonsultarEtapa = new DBConsultarEtapas();
              objonsultarEtapa.consultaUltimaEtapaCaso("select idEtapa from etapas where idcaso = '" + idCaso + "' order by idEtapa desc limit 1");
              
              idEtapaAgregar = objonsultarEtapa.getIdEtapaUltimoCaso();
              idEtapaAgregar++;
              
              sWhereCaso = "insert into etapas(idEtapa,idCaso,nombreEtapa,descripcionEtapa) values ('" + idEtapaAgregar + "',";
        	  
        	  sWhereCaso += "'" + idCaso + "',";
        	  
        	  sWhereCaso += " '" + nombreEtapaagregar + "',";
 			 
        	  sWhereCaso += " '" + descripcion + "')";
 			 
        	  System.out.println("sWhereCaso " + sWhereCaso);
              //comando = cn.getConnection().createStatement();
              comando.executeUpdate( sWhereCaso);
                                      
              cn.getConnection().commit();
              cn.getConnection().setAutoCommit(true);
              cn.desconectar();
              System.out.println("se registraron los datos");
              bBandera = true;
             
        } catch (Exception ex){
        	try {
        		
				cn.getConnection().rollback();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        	cn.desconectar();
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

	public int getidEtapaAgregar()
	{
		return idEtapaAgregar;
	}

}


