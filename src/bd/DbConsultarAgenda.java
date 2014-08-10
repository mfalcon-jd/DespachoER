package bd;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import conexion.Conexion;

public class DbConsultarAgenda 
{
	Conexion cn;
	PreparedStatement pstm;
	String tipoConsulta;
	Object [][] data;
	public Exception excepcion;
	
	public Object [][] consultarAgenda(String tipoConsulta, String sCount, String sWhere)
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
			String sNota			= "";
			String sNombre          = "";
			String sApellidoPaterno = "";
			String sApellidoMaterno = "";
			String sTipoTelefono	= "";
			String sFax		    	= "";
			String sTelefono		= "";
			String sEmail  			= "";
			String sDireccion		= "";
			int idContacto			= 0;
			//int iRegistros;
			try {
				String strsql = "";
				/*strsql = "SELECT t1.curp, t1.nombre, t1.apellidoPaterno, t1.apellidoMaterno, t2.NoTramite, t2.anticipo, t2.tipoPago, t2.observaciones," +
								" t3.nombreCaso, t3.idCaso, t3.descripcion, t3.precio" +
								" FROM clientes AS t1, tramite AS t2, casos AS t3"+
								" WHERE t2.idCaso = t3.idCaso AND t1.curp = t2.curp AND t2." +sWhere+"";*/
				strsql = sWhere;
				System.out.println("sWhere agenda " + sWhere);
				pstm = cn.getConnection().prepareStatement(strsql);
				cn.getConnection().setAutoCommit(false);
				
				ResultSet res = pstm.executeQuery();
				
				
				cn.getConnection().commit();
	            cn.getConnection().setAutoCommit(true);
	            
				if(tipoConsulta == "Agenda")
				{
					data = new Object[registros][6];
					int i = 0;
					while(res.next())
					{
						
						sNombre          = res.getString("nombre");
						sApellidoPaterno = res.getString("apellidoPaterno");
						sApellidoMaterno = res.getString("apellidoMaterno");
						sNota		     = res.getString("notas");
						sDireccion		 = res.getString("direccion");							
						idContacto		 = res.getInt("idContacto");
						System.out.println("nombre "+sNombre);
						
						if(sApellidoPaterno == null)sApellidoPaterno = "";
						if(sApellidoMaterno == null)sApellidoMaterno = "";
						if(sNota == null)			sNota = "";
						if(sDireccion == null)		sDireccion = "";
						
						data[i][0]  = sNombre;
						data[i][1]  = sApellidoPaterno;
						data[i][2]  = sApellidoMaterno;
						data[i][3]  = sDireccion;
						data[i][4]  = sNota;
						data[i][5]  = idContacto;
											
						i++;
					}
					res.close();
				}
				else
				{
					data = new Object[registros][6];
					int i = 0;
					while(res.next())
					{
						int iContacto    = 0;
						int id 		     = 0;
						String telefono  = "";
						String tipo 	 = "";
						String email 	 = "";
						String fax 		 = "";
						
						idContacto = res.getInt("idContacto");
						id         = res.getInt("id");
						telefono   = res.getString("telefono");
						tipo		= res.getString("descripcion");
					    email      = res.getString("email");
					    fax			= res.getString("fax");		
					    
					    if(telefono.equals(0)) telefono = "";
					    if(fax.equals(0)) fax= "";
					    
					    data[i][0]  = idContacto;
						data[i][1]  = id;
						data[i][2]  = telefono;
						data[i][3]  = tipo;
						data[i][4]  = email;
						data[i][5]  = fax;
					    
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

}
