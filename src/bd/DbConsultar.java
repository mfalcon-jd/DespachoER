package bd;
import conexion.Conexion;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DbConsultar {
	Conexion cn;
	
	public Object [][] consultarCliente(String sCount, String sWhere)
	{ 
		cn = new Conexion();
		
		int registros = 0;
		try 
		{
			System.out.println("Scount en consulta clientes " + sCount);
			
			PreparedStatement pstm = cn.getConnection().prepareStatement(sCount);
			cn.getConnection().setAutoCommit(false);
			ResultSet res = pstm.executeQuery();
			//pstm.setString(1, sClaveCliente);
			
			res.next();
			registros = res.getInt("cont");
			System.out.println("contador 1 " + registros);
			res.close();
		}
		catch(Exception e){
			System.out.println("e.toString() " + e.getMessage());
			System.out.println(e);
		}
		System.out.println("registros " + registros);
		Object [][] data = new Object[registros][8];
		if (sWhere.length() != 0 )
		{
			String sNombre          = "";
			String sApellidoPaterno = "";
			String sApellidoMaterno = "";
			String sCurp			= "";
			String sDireccion		= "";
			Date sAnioNacimiento;
			String sEmail			= "";
			String sCelular			= "";
			String sTelefono		= "";
			String sObservaciones   = "";
			
			Date fechaAux = null;
			int iRegistros;
			try {
				System.out.println("sWhere en consulta clientes " + sWhere);				
				PreparedStatement pstm = cn.getConnection().prepareStatement(sWhere);
				//pstm.setString(1, sClaveCliente);
				ResultSet res = pstm.executeQuery();
				
				cn.getConnection().commit();
	            cn.getConnection().setAutoCommit(true);
				cn.desconectar();
				
				int i = 0;
				while(res.next())
				{
					
					sNombre          = res.getString("nombre");
					System.out.println("Nombre " + sNombre);sApellidoPaterno = res.getString("apellidoPaterno");
					sApellidoMaterno = res.getString("apellidoMaterno");
					sCurp		     = res.getString("curp");
					sDireccion		 = res.getString("direccion");
					sAnioNacimiento	 = res.getDate("fechaNacimiento");
					sEmail			 = res.getString("email");
					/*sCelular		 = res.getString("celular");
					sTelefono		 = res.getString("telefonoCasa");*/
					sObservaciones   = res.getString("observaciones");
					
					if(sObservaciones == null) sObservaciones = "";
					if(sTelefono == null) sTelefono = "";
					if(sEmail == null) sEmail = "";
					
					/*SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd");	
					try {
						  fechaAux = formato.parse("2012-05-12");
						
					} catch (Exception e2) {
						// TODO Auto-generated catch block
						e2.printStackTrace();
					}
					if(sAnioNacimiento == null)sAnioNacimiento = new Date("2012-05-12");*/
					data[i][0] = sNombre;
					data[i][1] = sApellidoPaterno;
					data[i][2] = sApellidoMaterno;
					data[i][3] = sCurp;
					data[i][4] = sDireccion;
					data[i][5] = sAnioNacimiento;
					data[i][6] = sEmail;
					/*data[i][7] = sCelular;
					data[i][8] = sTelefono;*/
					data[i][7] = sObservaciones;
					i++;
				}
				res.close();
			}
			catch(Exception e){
				System.out.println("e message " + e.getMessage());
				System.out.println(e);
			}
			finally
			{
				try
				{
					cn.desconectar();
				}
				catch(Exception e)
				{
					//JOptionPane.showMessageDialog(this, "Los Datos Han Sido Guardados Exitosamente!!", "Dato Insertados", JOptionPane.INFORMATION_MESSAGE );
					
				}
				
			}
		}
		return data;		
	}
	
}
