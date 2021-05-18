/**
 * CLASE QUE IMPLEMENTA EL PATR�N SINGLETON PARA OBTENER LA CONSULTA A LA BASE DE DATOS
 */
package singlenton;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class DBConnection {
	
	private static final String CONTRASENNA = "79hrqzfn";

	private static final String USUARIO = "USER08";

	private static final String JDBC_URL = "jdbc:mysql://iescristobaldemonroy.duckdns.org:33306/BDUSER08";
	
	private static Connection instance = null;//objeto
	
	/*
	 * Creacion de objeto conection a la BD desde el driveManager
	 */
	private DBConnection() throws SQLException {
		
		instance = DriverManager.getConnection(JDBC_URL, USUARIO,CONTRASENNA);
		
	}
	
	public static Connection getConnection() throws SQLException {
		if (instance == null) {
			new DBConnection();
		}
		
		return instance;
	}
	

}
