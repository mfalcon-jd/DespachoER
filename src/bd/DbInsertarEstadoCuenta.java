package bd;

import java.sql.Statement;

import conexion.Conexion;

public class DbInsertarEstadoCuenta
{
	Conexion cn;
	Statement comando;
	public Exception excepcion;
	
	//public boolean insertarEstadoCuenta(Object[] vValoresEstadoCuenta)
	public boolean insertarEstadoCuenta(String sCampos, String sValores)
	{
		cn = new Conexion();  
		boolean bBandera = false;
        try {
        	String sqrt = "insert into estadocuenta " +sCampos + "values " + sValores;
              //Connection conexion=DriverManager.getConnection("jdbc:mysql://localhost/bd1","root" ,"");
              System.out.println("estado cuenta insertar " + sqrt);
        	  comando = cn.getConnection().createStatement();
              comando.executeUpdate(sqrt);
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

}
