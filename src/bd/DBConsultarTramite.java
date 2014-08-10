package bd;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import conexion.Conexion;

public class DBConsultarTramite {
	Conexion cn;
	PreparedStatement pstm;
	String tipoConsulta;
	Object [][] data;
	private int iEstatusPagado;
	public Exception excepcion;
	
	public Object [][] consultarTramite(String tipoConsulta, String sCount, String sWhere)
	{ this.tipoConsulta = tipoConsulta;
		cn = new Conexion();
		int registros = 0;
		try {
			System.out.println("sWher " + sWhere);
			
			/*String sqrt = "SELECT count(1) AS cont FROM clientes AS t1, tramite AS t2, casos AS t3"+
			" WHERE t2.idCaso = t3.idCaso AND t1.curp = t2.curp AND t2." +sWhere+"";*/
			String sqrt = sCount;
					
			System.out.println("sqrt "+ sqrt);
			pstm = cn.getConnection().prepareStatement(sqrt);
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
		
		if (sWhere.length() != 0 )
		{
			String sCurp			= "";
			String sNombre          = "";
			String sApellidoPaterno = "";
			String sApellidoMaterno = "";
			String sTipoPago		= "";
			String sObservaciones	= "";
			String sNombreCaso		= "";
			String nombreEtapa      = "";
			String estatus			= "";
			int idEtapa				= 0;
			int pagado;
			String sDescripcionCaso = "";
			int idCaso = 0;
			int iTramite			= 0;
			double dPrecio = 0.0;
			double dAnticipo    	= 0.0;
			
			//int iRegistros;
			try {
				String strsql = "";
				/*strsql = "SELECT t1.curp, t1.nombre, t1.apellidoPaterno, t1.apellidoMaterno, t2.NoTramite, t2.anticipo, t2.tipoPago, t2.observaciones," +
								" t3.nombreCaso, t3.idCaso, t3.descripcion, t3.precio" +
								" FROM clientes AS t1, tramite AS t2, casos AS t3"+
								" WHERE t2.idCaso = t3.idCaso AND t1.curp = t2.curp AND t2." +sWhere+"";*/
				strsql = sWhere;
				pstm = cn.getConnection().prepareStatement(strsql);
				//pstm.setString(1, sClaveCliente);
				ResultSet res = pstm.executeQuery();
				
				//cn.desconectar();
				if(tipoConsulta == "Cliente")
				{
					data = new Object[registros][4];
					int i = 0;
					while(res.next())
					{
						
						sNombre          = res.getString("nombre");
						sApellidoPaterno = res.getString("apellidoPaterno");
						sApellidoMaterno = res.getString("apellidoMaterno");
						sCurp		     = res.getString("curp");
						/*iTramite		 = res.getInt("NoTramite");
						dAnticipo    	 = res.getDouble("anticipo");
						sTipoPago		 = res.getString("tipoPago");;
						sObservaciones	 = res.getString("observaciones");
						sNombreCaso		 = res.getString("nombreCaso");
						sDescripcionCaso = res.getString("descripcion");
						idCaso           = res.getInt("idCaso");
						dPrecio          = res.getDouble("precio");*/
						System.out.println("nombre "+sNombre);
						data[i][0]  = sCurp;
						data[i][1]  = sNombre;
						data[i][2]  = sApellidoPaterno;
						data[i][3]  = sApellidoMaterno;
						/*data[i][4]  = iTramite;
						data[i][5]  = dAnticipo;
						data[i][6]  = sTipoPago;
						data[i][7]  = sObservaciones;
						data[i][8]  = sNombreCaso;
						data[i][9]  = sDescripcionCaso;
						data[i][10] = idCaso;
						data[i][11] = dPrecio;*/
					
						i++;
					}
					res.close();
				}
				else
				{
					if(tipoConsulta == "Pagos") data = new Object[registros][12];
					else data = new Object[registros][11];
					
					int i = 0;
					while(res.next())
					{
						
						/*sNombre          = res.getString("nombre");
						sApellidoPaterno = res.getString("apellidoPaterno");
						sApellidoMaterno = res.getString("apellidoMaterno");
						sCurp		     = res.getString("curp");*/
						iTramite		 = res.getInt("NoTramite");
						dAnticipo    	 = res.getDouble("anticipo");
						sTipoPago		 = res.getString("tipoPago");;
						sObservaciones	 = res.getString("observaciones");
						sNombreCaso		 = res.getString("nombreCaso");
						sDescripcionCaso = res.getString("descripcion");
						idCaso           = res.getInt("idCaso");
						dPrecio          = res.getDouble("precio");
						nombreEtapa      = res.getString("nombreEtapa");
						estatus			 = res.getString("estatus");
						idEtapa			 = res.getInt("idEtapa");
						
						System.out.println("nombre "+sNombre);
						if(sObservaciones == null)sObservaciones = "";
						if(sDescripcionCaso == null)sDescripcionCaso = "";
						if(estatus == null)estatus = "";
						
						
						data[i][0]  = iTramite;
						data[i][1]  = idCaso;
						data[i][2]  = sNombreCaso;
						data[i][3]  = sDescripcionCaso;
						data[i][4]  = sObservaciones;
						data[i][5]  = dPrecio;
						data[i][6]  = sTipoPago;
						data[i][7]  = dAnticipo;
						data[i][8]  = estatus;
						data[i][9]  = nombreEtapa;
						data[i][10] = idEtapa;
						
						if(tipoConsulta == "Pagos")
						{
							pagado = res.getInt("pagado");
							if(pagado == 0)
								data[i][11] = "";
							else data[i][11] = "Pagado";
							System.out.println("bandera pagado " + pagado);
								
						}
						/*data[i][8]  = sNombreCaso;
						data[i][9]  = sDescripcionCaso;
						data[i][10] = idCaso;
						data[i][11] = dPrecio;*/
					
						i++;
					}
					res.close();
				}
			}
			catch(Exception e){
				excepcion = e;
				System.out.println("eRROR message " + e.getMessage());
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
	
	public void consultarDatoEspecifico(String sWhere)
	{ 
		cn = new Conexion();
		
		try {
			System.out.println("sWher " + sWhere);
			
			/*String sqrt = "SELECT count(1) AS cont FROM clientes AS t1, tramite AS t2, casos AS t3"+
			" WHERE t2.idCaso = t3.idCaso AND t1.curp = t2.curp AND t2." +sWhere+"";*/
			String sqrt = sWhere;
					
			System.out.println("sqrt "+ sqrt);
			pstm = cn.getConnection().prepareStatement(sqrt);
			ResultSet res = pstm.executeQuery();
			//pstm.setString(1, sClaveCliente);
			res.next();
			iEstatusPagado = res.getInt("pagado");
			System.out.println("iEstatusPagado 1 " + iEstatusPagado);
			res.close();
		}
		catch(Exception e){
			excepcion = e;
			System.out.println("e.toString() " + e.getMessage());
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
	
	public int getEstatusPagado()
	{
		return iEstatusPagado;
	}

}
