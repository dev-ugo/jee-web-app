package com.s4m.jee.studentmanagement.persistence;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

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

    private static final String databaseUrl = System.getenv().getOrDefault("DB_URL", "jdbc:mysql://localhost:8889/ecole");
    private static final String databaseUser = System.getenv().getOrDefault("DB_USER", "root");
    private static final String databasePassword = System.getenv().getOrDefault("DB_PASSWORD", "");
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
            Class.forName("com.mysql.jdbc.Driver");
            statement = null;
            connection = null;
            connection = DriverManager.getConnection(databaseUrl, databaseUser, databasePassword);
            statement = connection.createStatement();
            return null;
        } catch (Exception e) {
            System.out.println("ERROR in Connexion to " + databaseUrl + " :" + e.getMessage());
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
            System.out.println("ERROR in Connexion closure to " + databaseUrl + " : " + e.getMessage());
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
