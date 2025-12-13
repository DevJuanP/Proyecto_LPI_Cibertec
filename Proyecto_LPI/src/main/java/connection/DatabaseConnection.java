package connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public final class DatabaseConnection {
	private static final String host = "6ra5fa.h.filess.io";
	private static final String port = "3307";
	private static final String databaseName = "bibliotecadb_growthwar";
	private static final String user = "bibliotecadb_growthwar";
	private static final String password = "0236195001c413d0e74a09260f24c743dc137858";
	private static final String url = "jdbc:mysql://"+host+":"+port+"/"+databaseName+"?serverTimezone=America/Lima";
	
	public static Connection obtenerConexion() throws SQLException, ClassNotFoundException {
		Class.forName("com.mysql.cj.jdbc.Driver");
		return DriverManager.getConnection(url, user, password);
	}
}