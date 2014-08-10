package entidades;
import javax.swing.table.DefaultTableModel;

import bd.DbInsertar;
import conexion.Conexion;

public class clientes 
{
	private String  sNombre, sApellidoPaterno , sApellidoMaterno, sCurp,
	   sDireccion, sAnioNacimiento, sEmail, sCelular, sTelefono, sObservaciones;
	
	private DefaultTableModel sModelo;
	
	public Exception excepcion;	
	public clientes(String nombre,String apellidoPaterno, String apellidoMaterno, String curp,
					String direccion, String anioNacimiento, String email, /*String celular, String telefonoCasa,*/ String observaciones, DefaultTableModel modelo )
	{
		this.sNombre          = nombre;
		this.sApellidoPaterno = apellidoPaterno;
		this.sApellidoMaterno = apellidoMaterno;
		this.sCurp			  = curp;
		this.sDireccion       = direccion;
		this.sAnioNacimiento  = anioNacimiento;
		this.sEmail 		  = email;
		/*this.sCelular		  = celular;
		this.sTelefono        = telefonoCasa;*/
		this.sObservaciones   = observaciones;
		this.sModelo		  = modelo;
	}
	
	public clientes( )
	{
		
	}
	
	public boolean crearCliente()
	{
		boolean bBanderaCorrecto;
		String vDatosClientes[] = new String[8];
		
		vDatosClientes[0] = sNombre;
		vDatosClientes[1] = sApellidoPaterno;
		vDatosClientes[2] = sApellidoMaterno;
		vDatosClientes[3] = sCurp;
		vDatosClientes[4] = sDireccion;
		vDatosClientes[5] = sAnioNacimiento;
		vDatosClientes[6] = sEmail;
		/*vDatosClientes[7] = sCelular;
		vDatosClientes[8] = sTelefono;*/
		vDatosClientes[7] = sObservaciones;
		
		DbInsertar objInsertar = new DbInsertar();
		bBanderaCorrecto = objInsertar.insertarCliente(vDatosClientes,sModelo);
		
		excepcion = objInsertar.excepcion;
		
		return bBanderaCorrecto;
	}
	public String getNombre()
	{
		return sNombre;
	}
	
	public void setRollback()
	{
		DbInsertar objInsertar = new DbInsertar();
		objInsertar.deshacer();
	}
	
	public void setCommit()
	{
		DbInsertar objInsertar = new DbInsertar();
		objInsertar.confirmar();
	}
	
}
