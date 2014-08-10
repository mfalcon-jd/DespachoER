package bd;
import java.sql.*;
import conexion.Conexion;

public class DBNotaPago {
	Conexion cn;
	public static PreparedStatement cpstm, wpstm;
	public static ResultSet cres, wres;
	
	public Object [][] generarNotaPago(String sCount, String sWhere)
	{
		cn = new Conexion();
		int registro = 0;
		try
		{
			System.out.println("Count en Consulta de Nota de Pago " + sCount);
			cpstm = cn.getConnection().prepareStatement(sCount);
			cres = cpstm.executeQuery();
			cres.next();
			registro = cres.getInt("cont");
			cres.close();
		}
		catch(Exception ex)
		{
			System.out.println("Error " + ex.getMessage());
			System.out.println(ex);
		}
		
		Object [][] data = new Object[registro][15];
		if(sWhere.length() != 0)
		{
			String sCurp			= "";
			String sNombre			= "";
			String sApellidoPaterno	= "";
			String sApellidoMaterno	= "";
			int iNoTramite			=  0;
			Date dtFechaCreacion;
			int iPagado;
			String sTipoPago		= "";
			int idCaso				= 0;
			String sNombreCaso		= "";
			double dPrecio			= 0.0;
			double dAbono			= 0.0;
			double dImporteFaltante	= 0.0;
			double dPagoFinal		= 0.0;
			Date dtFechaPagoFinal;
			
			try
			{
				System.out.println("Where de Nota de Pago " + sWhere);
				wpstm = cn.getConnection().prepareStatement(sWhere);
				wres = wpstm.executeQuery();
				cn.desconectar();
				
				int i = 0;
				while(wres.next())
				{
					sCurp				= wres.getString("curp");
					sNombre				= wres.getString("nombre");
					sApellidoPaterno	= wres.getString("apellidoPaterno");
					sApellidoMaterno 	= wres.getString("apellidoMaterno");
					iNoTramite			= wres.getInt("NoTramite");
					dtFechaCreacion		= wres.getDate("fechaCreacion");
					iPagado				= wres.getInt("pagado");
					sTipoPago			= wres.getString("tipoPago");
					idCaso				= wres.getInt("idCaso");
					sNombreCaso			= wres.getString("nombreCaso");
					dPrecio				= wres.getDouble("precio");
					dAbono				= wres.getDouble("abono");
					dImporteFaltante	= wres.getDouble("importeFaltante");
					dPagoFinal			= wres.getDouble("pagoFinal");
					dtFechaPagoFinal	= wres.getDate("fechaPagoFinal");
										
					//Comparar los tipos de pagos dPagoFinal, dtFechaPagoFinal
					
					data[i][0] = sCurp;
					data[i][1] = sNombre;
					data[i][2] = sApellidoPaterno;
					data[i][3] = sApellidoMaterno;
					data[i][4] = iNoTramite;
					data[i][5] = dtFechaCreacion;
					data[i][6] = iPagado;
					data[i][7] = sTipoPago;
					data[i][8] = idCaso;
					data[i][9] = sNombreCaso;
					data[i][10] = dPrecio;
					data[i][11] = dAbono;
					data[i][12] = dImporteFaltante;
					data[i][13] = dPagoFinal;
					data[i][14] = dtFechaPagoFinal;					
				}
				wres.close();
			}
			catch(Exception ex)
			{
				System.out.println("Mensaje " + ex.getMessage());				
				System.out.println(ex);
			}
			finally
			{
				try
				{
					cn.desconectar();
				}
				catch(Exception ex)
				{
					System.out.println("Error " + ex.getMessage());
				}
			}
		}
		
		return data;
	}

}
