package bd;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import conexion.Conexion;

public class DBConsultarCasos 
{
	Conexion cn;
	PreparedStatement pstm;
	public Exception excepcion;
	String strsql = "";		
	public Object [][] consultarCasos(String cTipoConsulta, String sWhere)
	{ 
		cn = new Conexion();
		int registros = 0;
		try {
			String sqrCount = "";
			
			if(cTipoConsulta == "Tramite")
			{
				sqrCount = "SELECT count(1) as cont FROM casos";
				strsql   = "SELECT * FROM casos ";
				
			}
			else
			{
				sqrCount = "SELECT count(1) as cont FROM casos where " + sWhere;
				strsql   = "SELECT * FROM casos where " + sWhere;
			}
			System.out.println("count " + sqrCount );
			System.out.println("strsql " + strsql );
			pstm = cn.getConnection().prepareStatement(sqrCount);
			
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
		Object [][] data = new Object[registros][4];
		if (registros != 0 )
		{
			int idCaso = 0;
			float fprecio;
			String sNombreCaso          = "";
			String sDescripcion         = "";
			
			try {
				
				//strsql = "SELECT * FROM casos ";
				pstm = cn.getConnection().prepareStatement(strsql);
				//pstm.setString(1, sClaveCliente);
				ResultSet res = pstm.executeQuery();
				
				cn.desconectar();
				
				int i = 0;
				while(res.next())
				{
					idCaso = res.getInt("idCaso");
					sNombreCaso          = res.getString("nombreCaso");
					fprecio = (float) res.getDouble("precio");
					sDescripcion = res.getString("descripcion");
					System.out.println("idCaso" + idCaso);				
					data[i][0] = idCaso;
					data[i][1] = sNombreCaso;
					data[i][2] = fprecio;
					data[i][3] = sDescripcion;
					
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
	public Object [][] consultarNombreCaso(String sCount, String sWhere)
	{ 
		cn = new Conexion();
		int registros = 0;
		try {
			
			System.out.println("count " + sCount );
			
			pstm = cn.getConnection().prepareStatement(sCount);
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
		Object [][] data = new Object[registros][4];
		if (registros != 0 )
		{
			int idCaso = 0;
			String sNombreCaso          = "";
			
			
			try {
				
				//strsql = "SELECT * FROM casos ";
				pstm = cn.getConnection().prepareStatement(sWhere);
				//pstm.setString(1, sClaveCliente);
				ResultSet res = pstm.executeQuery();
				
				cn.desconectar();
				
				int i = 0;
				while(res.next())
				{
					idCaso = res.getInt("idCaso");
					sNombreCaso          = res.getString("nombreCaso");
					
					System.out.println("idCaso" + idCaso);				
					data[i][0] = idCaso;
					data[i][1] = sNombreCaso;
					
					
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
