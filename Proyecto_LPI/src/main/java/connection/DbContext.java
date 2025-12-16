package connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Contexto de base de datos que maneja el ciclo de vida de una conexión.
 * Se registra como SCOPED - una instancia por HTTP request.
 */
public class DbContext implements AutoCloseable {
    
    private static final String HOST = "6ra5fa.h.filess.io";
    private static final String PORT = "3307";
    private static final String DATABASE_NAME = "bibliotecadb_growthwar";
    private static final String USER = "bibliotecadb_growthwar";
    private static final String PASSWORD = "0236195001c413d0e74a09260f24c743dc137858";
    private static final String URL = "jdbc:mysql://" + HOST + ":" + PORT + "/" + 
                                     DATABASE_NAME + "?serverTimezone=UTC";
    
    private Connection connection;
    private boolean inTransaction = false;
    
    /**
     * Constructor - carga el driver JDBC
     */
    public DbContext() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("MySQL JDBC Driver no encontrado", e);
        }
    }
    
    /**
     * Obtiene la conexión actual (lazy initialization)
     * Crea la conexión solo cuando se necesita
     */
    public Connection getConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
            connection.setAutoCommit(true);
        }
        return connection;
    }
    
    /**
     * Inicia una transacción
     * Útil cuando necesitas realizar múltiples operaciones atómicamente
     */
    public void beginTransaction() throws SQLException {
        if (inTransaction) {
            throw new IllegalStateException("Ya hay una transacción activa");
        }
        
        getConnection().setAutoCommit(false);
        inTransaction = true;
    }
    
    /**
     * Confirma la transacción actual
     */
    public void commit() throws SQLException {
        if (!inTransaction) {
            throw new IllegalStateException("No hay transacción activa");
        }
        
        connection.commit();
        connection.setAutoCommit(true);
        inTransaction = false;
    }
    
    /**
     * Revierte la transacción actual
     */
    public void rollback() {
        if (!inTransaction) {
            return;
        }
        
        try {
            if (connection != null && !connection.isClosed()) {
                connection.rollback();
                connection.setAutoCommit(true);
            }
        } catch (SQLException e) {
            System.err.println("Error durante rollback: " + e.getMessage());
        } finally {
            inTransaction = false;
        }
    }
    
    /**
     * Cierra la conexión (llamado automáticamente al final del scope)
     */
    @Override
    public void close() {
        if (inTransaction) {
            System.err.println("⚠️  Transacción no confirmada - haciendo rollback automático");
            rollback();
        }
        
        if (connection != null) {
            try {
                if (!connection.isClosed()) {
                    connection.close();
                }
            } catch (SQLException e) {
                System.err.println("Error cerrando conexión DB: " + e.getMessage());
            } finally {
                connection = null;
            }
        }
    }
    
    /**
     * Verifica si hay una transacción activa
     */
    public boolean isInTransaction() {
        return inTransaction;
    }
}