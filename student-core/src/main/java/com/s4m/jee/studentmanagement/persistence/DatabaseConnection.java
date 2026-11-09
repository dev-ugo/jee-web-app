package com.s4m.jee.studentmanagement.persistence;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Properties;

/**
 * Manages the shared JDBC connection, statement and result set.
 * Connection settings come from DB_URL, DB_USER and DB_PASSWORD environment variables.
 * Callers must serialize access to this shared state.
 */
public class DatabaseConnection {

    /**
     * Creates an instance; operations use the existing static API.
     */
    public DatabaseConnection() {
    }

    private static final String databaseUrl = System.getenv("DB_URL");
    private static final String databaseUser = System.getenv("DB_USER");
    private static final String databasePassword = System.getenv("DB_PASSWORD");
    private static Connection connection = null;
    private static Statement statement = null;
    private static ResultSet resultSet = null;
    private static int errorCode;

    /**
     * Loads the JDBC driver and opens the shared connection and statement.
     *
     * @return null on success, or the exception raised while opening the connection
     */
    public static Exception connect() {
        try {
            if (databaseUrl == null || databaseUrl.isBlank() || databaseUser == null
                    || databaseUser.isBlank() || databasePassword == null || databasePassword.isEmpty()) {
                throw new IllegalStateException("DB_URL, DB_USER et DB_PASSWORD requis.");
            }
            Class.forName("com.mysql.jdbc.Driver");
            statement = null;
            connection = null;
            Properties settings = new Properties();
            settings.setProperty("user", databaseUser);
            settings.setProperty("password", databasePassword);
            settings.setProperty("connectTimeout", "5000");
            settings.setProperty("socketTimeout", "15000");
            connection = DriverManager.getConnection(databaseUrl, settings);
            statement = connection.createStatement();
            return null;
        } catch (Exception e) {
            System.err.println("Connexion a la base impossible (configuration ou disponibilite).");
            return e;
        }
    }

    /**
     * Closes the shared statement and connection, logging any cleanup exception.
     *
     * @return the existing cleanup status, which defaults to zero
     */
    public static int close() {
        try {
            if (statement != null) statement.close();
            if (connection != null) connection.close();
        } catch (Exception e) {
            System.err.println("Echec de fermeture de la connexion JDBC.");
        }

        return errorCode;
    }

    /**
     * Returns the shared JDBC connection.
     *
     * @return the shared JDBC connection
     */
    public static Connection getConnection() {
        return connection;
    }

    /**
     * Sets the shared JDBC connection.
     *
     * @param connection the shared JDBC connection
     */
    public static void setConnection(Connection connection) {
        DatabaseConnection.connection = connection;
    }

    /**
     * Returns the shared JDBC statement.
     *
     * @return the shared JDBC statement
     */
    public static Statement getStatement() {
        return statement;
    }

    /**
     * Sets the shared JDBC statement.
     *
     * @param statement the shared JDBC statement
     */
    public static void setStatement(Statement statement) {
        DatabaseConnection.statement = statement;
        if (statement != null) {
            try { statement.setQueryTimeout(10); }
            catch (java.sql.SQLException e) { throw new IllegalStateException("Delai JDBC indisponible."); }
        }
    }

    /**
     * Returns the shared JDBC result set.
     *
     * @return the shared JDBC result set
     */
    public static ResultSet getResultSet() {
        return resultSet;
    }

    /**
     * Sets the shared JDBC result set.
     *
     * @param resultSet the shared JDBC result set
     */
    public static void setResultSet(ResultSet resultSet) {
        DatabaseConnection.resultSet = resultSet;
    }
}
