package bd;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import conexion.Conexion;

public class DBConsultarEtapas 
{
	Conexion cn;
	PreparedStatement pstm;
	public Exception excepcion;
	
	public int idEtapa = 0;
	public String etapaSiguiente = "";
	private int idUltimaEtapa = 0;
	
	String strsql = "";		
	public Object [][] consultarEtapas(String sWhere, String sCount)
	{ 
		cn = new Conexion();
		int registros = 0;
		try 
		{
			String sqrCount = "";
			
			/*sqrCount = "SELECT count(1) as cont FROM etapas " + sWhere;
			strsql   = "SELECT * FROM etapas " + sWhere;*/
			sqrCount = sCount;
			strsql = sWhere;
									
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
			int idEtapa = 0;
			int idCaso = 0;
			String sNombreEtapa          = "";
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
					sNombreEtapa          = res.getString("nombreEtapa");
					idEtapa =  res.getInt("idEtapa");
					sDescripcion = res.getString("descripcionEtapa");
					
					System.out.println("idCaso" + idCaso);				
					data[i][0] = idCaso;
					data[i][1] = idEtapa;
					data[i][2] = sNombreEtapa;
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
	public void restaurarTabla()
	{
		
	}
	public void consultaEtapaSiguiente(String sConsulta)
	{
		try 
		{
			cn = new Conexion();
			String strsql = sConsulta;
									
			System.out.println("strsql consulta etapa siguiente " + strsql );
			
			pstm = cn.getConnection().prepareStatement(strsql);
			ResultSet res = pstm.executeQuery();
			
			res.next();
			idEtapa = res.getInt("idEtapa");
			etapaSiguiente = res.getString("nombreEtapa");
			System.out.println("idEtapa " + idEtapa);
			res.close();
		}
		catch(Exception e){
			excepcion = e;
			System.out.println("e.toString() " + e.getMessage());
			System.out.println(e);
		}
		
	}
	
	public void consultaUltimaEtapaCaso(String sConsulta)
	{
		try 
		{
			cn = new Conexion();
			String strsql = sConsulta;
									
			System.out.println("strsql consulta etapa ultima " + strsql );
			
			pstm = cn.getConnection().prepareStatement(strsql);
			ResultSet res = pstm.executeQuery();
			
			if(res.first())
			{
				idUltimaEtapa = res.getInt("idEtapa");
			}
			else idUltimaEtapa = 0;
			System.out.println("idEtapa Ultima " + idEtapa);
			res.close();
		}
		catch(Exception e){
			excepcion = e;
			System.out.println("e.toString() " + e.getMessage());
			System.out.println(e);
		}
		
	}
	
	public int getIdEtapaUltimoCaso()
	{
		return idUltimaEtapa;
	}
	public int getIdEtapasiguiente()
	{
		return idEtapa;
	}
	
	public String getNombreEtapaSiguiente()
	{
		return etapaSiguiente;
	}
}
