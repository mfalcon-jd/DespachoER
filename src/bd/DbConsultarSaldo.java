package bd;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import conexion.Conexion;

public class DbConsultarSaldo 
{
	Conexion cn;
	PreparedStatement pstm;
	public Exception excepcion;
	public double idContacto = 0.0;
	String sqrt;
	public boolean lHuboCoincidencia = false;
	//public boolean insertarAgenda(String sWhereAgenda, String sWhereTelefono)
	public boolean consultarSaldo(String sTramite)
	{
		cn = new Conexion();  		
		
		boolean bBandera = false;
        try {
        	    sqrt = "SELECT importeFaltante FROM estadocuenta WHERE idTramite ='"+sTramite+"' ORDER BY importeFaltante LIMIT 1 ";
        	    System.out.println("sqrt saldo " + sqrt);
	        	pstm = cn.getConnection().prepareStatement(sqrt);
				ResultSet res = pstm.executeQuery();
				//pstm.setString(1, sClaveCliente);
				res.next();
				
				if(res.first())
				{
					idContacto = res.getDouble("importeFaltante");
					System.out.println("SALDO BIEN " + idContacto);
					lHuboCoincidencia = true;
				}
				System.out.println("SALDO DEFAULT " + idContacto);
				res.close();
				bBandera = true;
        	  
        	 
             
        } catch (Exception ex){
        	excepcion = ex;
            System.out.println("GETmESSAGE " + ex.getMessage());             
        }
        finally
        {
        	try
			{
        	
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
