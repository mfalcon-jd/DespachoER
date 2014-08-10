package bd;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import conexion.Conexion;


public class DbConsultarTelefonos
{
	Conexion cn;
	PreparedStatement pstm;
	String tipoConsulta;
	Object [][] data;
	public Exception excepcion;
	
	public ResultSet consultarTelefonos(String sConsulta)
    {
        ResultSet rs = null;
        try
        {
        	cn = new Conexion();
        	pstm = cn.getConnection().prepareStatement(sConsulta);
			rs = pstm.executeQuery();
            // Se realiza la consulta. Los resultados se guardan en el
            // ResultSet rs
			System.out.println("sConsulta telefonos " + sConsulta);
			
            
        } catch (Exception e)
        {
        	System.out.println("excepcion telefonos " + e.getMessage());
            e.printStackTrace();
        }
        finally
        {
        	try
			{
        		/*pstm.close();
				cn.desconectar();
				*/
			}
			catch(Exception e)
			{
				excepcion = e;
				System.out.println("excepcion telefonos finally " + e.getMessage());
				//JOptionPane.showMessageDialog(this, "Los Datos Han Sido Guardados Exitosamente!!", "Dato Insertados", JOptionPane.INFORMATION_MESSAGE );
				
			}
			
        }
        return rs;
    }

}
