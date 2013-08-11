package qbits.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import qbits.configuration.Configuration;

/**
 * 
 * @author Topu
 */

public class MySQLDatabase {

    private Connection connection = null;
    private Statement statement;
    private String dbName = Configuration.DATABASE_NAME;
    private String password = Configuration.DATABASE_PASSWORD;
    private String userName = Configuration.DATABASE_USER_NAME;

    public MySQLDatabase() {
    }

    public MySQLDatabase(String databaseName, String databasePassword, String rootUser) {
        this.dbName = databaseName;
        this.password = databasePassword;
        this.userName = rootUser;
    }

    public MySQLDatabase(String databasePassword, String rootUser) {
        this.password = databasePassword;
        this.userName = rootUser;
    }

    public boolean setAutoCommit(boolean status) {
        try {
            connection.setAutoCommit(status);
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(MySQLDatabase.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    public boolean connect() {

        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            connection = DriverManager.getConnection("jdbc:mysql:///" + this.dbName, this.userName, this.password);
            statement = (Statement) connection.createStatement();
        } catch (Exception ex) {
            System.err.println("[Exception During Connection] " + ex.getMessage());
            return false;
        }

        return true;
    }

    public void disconnect() {

        try {
            connection.close();
            statement.close();
        } catch (SQLException ex) {
            System.err.println("[Exception During Closing Connection] " + ex.getMessage());
        }
    }

    public String formatQuery(String query) {
        return query;
    }

    public long update(String query) {

        System.out.println("Query: " + query);
        int affectedRows = 0;
        ResultSet generatedKeys = null;

        try {
            affectedRows = statement.executeUpdate(query, Statement.RETURN_GENERATED_KEYS);
            return affectedRows;
        } catch (SQLException ex) {
            System.err.println("[Exception During Data Insertion] " + ex.getMessage());
            return -1;
        }
    }

    public long delete(String query) {

        System.out.println("Query: " + query);
        int affectedRows = 0;
        ResultSet generatedKeys = null;

        try {
            affectedRows = statement.executeUpdate(query, Statement.RETURN_GENERATED_KEYS);
            return affectedRows;
        } catch (SQLException ex) {
            System.err.println("[Exception During Data Insertion] " + ex.getMessage());
            return -1;
        }
    }

    public long insert(String query) {

        System.out.println("Query: " + query);
        int affectedRows = 0;
        ResultSet generatedKeys = null;

        try {
            affectedRows = statement.executeUpdate(query, Statement.RETURN_GENERATED_KEYS);

            if (affectedRows != 0) {

                generatedKeys = statement.getGeneratedKeys();

                if (generatedKeys.next()) {
                    return generatedKeys.getLong(1);
                } else {
                    return -1;
                }
            } else {
                return -1;
            }

        } catch (SQLException ex) {
            System.err.println("[Exception During Data Insertion] " + ex.getMessage());
            return -1;
        }
    }

    public boolean commit() {
        try {
            connection.commit();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(MySQLDatabase.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    public boolean rollback() {
        try {
            connection.rollback();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(MySQLDatabase.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    public ResultSet get(String query) {
        System.out.println("Query: " + query);
        try {
            ResultSet resultSet = statement.executeQuery(query);
            return resultSet;
        } catch (SQLException ex) {
            System.err.println("[Exception During Data Fetching] " + ex.getMessage());
        }
        return null;
    }

    public static void main(String args[]) {

        MySQLDatabase mySQLDatabase = new MySQLDatabase();
        System.out.println(mySQLDatabase.connect());

    }
}
