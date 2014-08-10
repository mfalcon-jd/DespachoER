package bd;

import java.sql.*;

import conexion.Conexion;

public class DbAlumnoInsertar {
	Conexion cn;	
	public DbAlumnoInsertar()
	{
		cn = new Conexion();                         
        try {
              //Connection conexion=DriverManager.getConnection("jdbc:mysql://localhost/bd1","root" ,"");
              Statement comando = cn.getConnection().createStatement();
              cn.getConnection().setAutoCommit(false);
              comando.executeUpdate("insert into bd(id,date) values ('7', '2009-12-14')");
              //cn.desconectar();
              System.out.println("se registraron los datos");
              cn.getConnection().rollback();
              cn.desconectar();
             
        } catch (SQLException ex){
        	
            System.out.println(ex);             
        }
	}
	
	public void DbRollback()
	{
		//cn = new Conexion();
		try {
			//cn.getConnection().setAutoCommit(false);
			cn.getConnection().rollback();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public static void main(String mlfl[])
	{
		DbAlumnoInsertar ins = new DbAlumnoInsertar();	
		//ins.DbRollback();
		
	}		
}
