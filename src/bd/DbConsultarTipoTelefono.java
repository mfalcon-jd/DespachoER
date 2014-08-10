package bd;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import conexion.Conexion;

public class DbConsultarTipoTelefono 
{
	Conexion cn;
	PreparedStatement pstm;
	public Exception excepcion;
		
	public Object [][] consultarTipoTelefono()
	{ 
		cn = new Conexion();
		int registros = 0;
		try {
			pstm = cn.getConnection().prepareStatement("SELECT count(1) as cont FROM tipotelefonos ");
			ResultSet res = pstm.executeQuery();
			//pstm.setString(1, sClaveCliente);
			res.next();
			registros = res.getInt("cont");
			System.out.println("contador 1 " + registros);
			res.close();
		}
		catch(Exception e){
			excepcion = e;
			System.out.println("e.toString() " + e.getMessage());
			System.out.println(e);
		}
		System.out.println("registros " + registros);
		Object [][] data = new Object[registros][2];
		if (registros != 0 )
		{
			int idTipo          = 0;
			String sDescripcion = "";
			
			try {
				String strsql = "";
				strsql = "SELECT * FROM tipotelefonos ";
				pstm = cn.getConnection().prepareStatement(strsql);
				//pstm.setString(1, sClaveCliente);
				ResultSet res = pstm.executeQuery();
				
				cn.desconectar();
				
				int i = 0;
				while(res.next())
				{
					idTipo = res.getInt("idTipo");
					sDescripcion          = res.getString("descripcion");
					
					System.out.println("idCaso" + idTipo);				
					data[i][0] = idTipo;
					data[i][1] = sDescripcion;
									
					i++;
				}
				res.close();
			}
			catch(Exception e){
				excepcion = e;
				System.out.println("e message " + e.getMessage());
				System.out.println(e);
			}
			finally
	        {
	        	try
				{
	        		pstm.close();
					cn.desconectar();
					
				}
				catch(Exception e)
				{
					excepcion = e;
					//JOptionPane.showMessageDialog(this, "Los Datos Han Sido Guardados Exitosamente!!", "Dato Insertados", JOptionPane.INFORMATION_MESSAGE );
					
				}
				
	        }
		}
		 
		return data;		
	}


}
