package bd;

import java.sql.*;

import conexion.Conexion;

public class DbUsuario {
	Conexion cn;
	Exception excepcion;
	Statement cmd;
	public DbUsuario() {
		cn = new Conexion();
	}
	public Object[][] UsuarioAcceder(String login, String password)
	{
		int registros = 0;
		try {
			PreparedStatement pstm = cn.getConnection().prepareStatement("SELECT count(1) as cont" +
			" FROM tm_usuario");
			ResultSet res = pstm.executeQuery();
			res.next();
			registros = res.getInt("cont");
			res.close();
		}
		catch(SQLException e){
			System.out.println("Error1 " +e);
		}
		Object [][] data = new Object[registros][4];
		if (login.length() != 0 && password.length() != 0){
			int usu_id;
			int usu_tipo;
			String usu_login = "";
			String usu_password = "";
			
			try {
				String strsql = "";
				strsql = "SELECT * FROM tm_usuario WHERE tm_login = '" + login + "' AND tm_password = '" + password + "'";
				PreparedStatement pstm = cn.getConnection().prepareStatement(strsql);
				ResultSet res = pstm.executeQuery();
				int i = 0;
				while(res.next()){
					usu_id = res.getInt("tm_idusuario");
					usu_tipo = res.getInt("tm_tipousuario");
					usu_login = res.getString("tm_login");
					usu_password = res.getString("tm_password");
					System.out.println("password " + usu_password);
					data[i][0] = usu_id;
					data[i][1] = usu_login;
					data[i][2] = usu_password;
					data[i][3] = usu_tipo;
					
					i++;
				}
				res.close();
			}
			catch(Exception excepcion){
				System.out.println("Error2 " + excepcion.getMessage());
			}
		}
		return data;
	}
	
	public boolean cambiarPassword(String claveUsuario, String nuevaContrasenia)
	{
		String sqrt = "UPDATE  tm_usuario SET tm_password = '" + nuevaContrasenia + "' WHERE tm_login = '" + claveUsuario +"' ";
		
		System.out.println("SQRT " + sqrt);
		
		boolean bBandera = false;
		try
		{
			cmd = cn.getConnection().createStatement();
			cmd.executeUpdate(sqrt);			
			
			
			System.out.println("La contraseña ha sido actualizada correctamente");
			bBandera = true;
			
			
		}
		catch(Exception exception)
		{
			System.out.println("e.getMessage " + exception.getMessage());
			System.out.println(exception);
			
			return bBandera;
		}
		finally
		{
			try
			{
				System.out.println("try finally dbUsuario");
				cmd.close();
				cn.desconectar();
			}
			catch(Exception exception)
			{
				System.out.println("e.getMessage " + exception.getMessage());
				bBandera = false;
				return bBandera;
				//JOptionPane.showMessageDialog(this, "Los Datos Han Sido Guardados Exitosamente!!", "Dato Insertados", JOptionPane.INFORMATION_MESSAGE );
				
			}
			
		}
		return bBandera;

		
	}
	
	public boolean insertarUsuario(String claveUsuario, String Contrasenia, String idTipoUsuario)
	{
		String sqrt = "insert into tm_usuario(tm_login,tm_password,tm_tipousuario) values ('"+claveUsuario+"','"+Contrasenia+"','"+idTipoUsuario+"')";
		
		System.out.println("SQRT " + sqrt);
		
		
		
		boolean bBandera = false;
		try
		{
			
			cmd = cn.getConnection().createStatement();
			cmd.executeUpdate(sqrt);			
			
			
			System.out.println("La contraseña ha sido actualizada correctamente");
			bBandera = true;
			
			
		}
		catch(Exception exception)
		{
			System.out.println("e.getMessage " + exception.getMessage());
			System.out.println(exception);
			
			return bBandera;
		}
		finally
		{
			try
			{
				System.out.println("try finally dbUsuario");
				cmd.close();
				cn.desconectar();
			}
			catch(Exception exception)
			{
				System.out.println("e.getMessage " + exception.getMessage());
				bBandera = false;
				return bBandera;
				//JOptionPane.showMessageDialog(this, "Los Datos Han Sido Guardados Exitosamente!!", "Dato Insertados", JOptionPane.INFORMATION_MESSAGE );
				
			}
			
		}
		return bBandera;

		
	}
	
	public Object[][] tipoUsuario()
	{
		int registros = 0;
		try {
			PreparedStatement pstm = cn.getConnection().prepareStatement("SELECT count(1) as cont" +
			" FROM tipousuario");
			ResultSet res = pstm.executeQuery();
			res.next();
			registros = res.getInt("cont");
			res.close();
		}
		catch(SQLException e){
			System.out.println("Error1 " +e);
		}
		Object [][] data = new Object[registros][3];
		
		int idTipoUsuario;
		String nombreTipo = "";
	    String descripcion = "";
			
			try {
				String strsql = "";
				strsql = "SELECT * FROM tipousuario";
				PreparedStatement pstm = cn.getConnection().prepareStatement(strsql);
				ResultSet res = pstm.executeQuery();
				int i = 0;
				while(res.next()){
					idTipoUsuario = res.getInt("idTipoUsuario");
					nombreTipo = res.getString("nombreTipoUsuario");
					descripcion = res.getString("descripcion");
					
					System.out.println("idTipoUsuario " + idTipoUsuario);
					data[i][0] = idTipoUsuario;
					data[i][1] = nombreTipo;
					data[i][2] = descripcion;
										
					i++;
				}
				res.close();
			}
			catch(Exception excepcion){
				System.out.println("Error2 " + excepcion.getMessage());
			}
		
		return data;
	}
	
	public Object[][] nombretipoUsuario(int idTipo)
	{
		int registros = 0;
		try {
			PreparedStatement pstm = cn.getConnection().prepareStatement("SELECT count(1) as cont" +
			" FROM tipousuario");
			ResultSet res = pstm.executeQuery();
			res.next();
			registros = res.getInt("cont");
			res.close();
		}
		catch(SQLException e){
			System.out.println("Error1 " +e);
		}
		Object [][] data = new Object[registros][3];
		
		int idTipoUsuario;
		String nombreTipo = "";
	    String descripcion = "";
			
			try {
				String strsql = "";
				strsql = "SELECT * FROM tipousuario where idTipoUsuario = " + idTipo;
				PreparedStatement pstm = cn.getConnection().prepareStatement(strsql);
				ResultSet res = pstm.executeQuery();
				int i = 0;
				while(res.next()){
					idTipoUsuario = res.getInt("idTipoUsuario");
					nombreTipo = res.getString("nombreTipoUsuario");
					descripcion = res.getString("descripcion");
					
					System.out.println("idTipoUsuario " + idTipoUsuario);
					data[i][0] = idTipoUsuario;
					data[i][1] = nombreTipo;
					data[i][2] = descripcion;
										
					i++;
				}
				res.close();
			}
			catch(Exception excepcion){
				System.out.println("Error2 " + excepcion.getMessage());
			}
		
		return data;
	}
	
	public Exception getException()
	{
		return excepcion;
	}
}
