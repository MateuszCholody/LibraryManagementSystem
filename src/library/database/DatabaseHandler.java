package library.database;

import java.sql.*;

public final class DatabaseHandler {

    private DatabaseHandler handler;

    private static final String DATABASE_URL = "jdbc:mysql://localhost:3306/library";
    private static final String DATABASE_PASS = "enudle12!";
    private static final String DATABASE_USER = "root";
    private static Connection connection = null;
    private static Statement statement = null;


    public DatabaseHandler() {
        createConnection();
        setupBookTable();
        setupMemberTable();
    }

    private void createConnection() {
        try {
            //Class.forName().newInstance();
            connection = DriverManager.getConnection(DATABASE_URL, DATABASE_USER, DATABASE_PASS);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setupBookTable() {
        String tableName = "BOOKS";

        try {
            statement = connection.createStatement();

            DatabaseMetaData databaseMetaData = connection.getMetaData();
            ResultSet tables = databaseMetaData.getTables(null, null, tableName.toUpperCase(), null);

            if (tables.next()) {
                System.out.println("Table " + tableName + " already exists.");

            } else {
                statement.execute("CREATE TABLE " + tableName + "("
                        + "id varchar(200) primary key, \n"
                        + "title varchar(200),\n"
                        + "author varchar(200),\n"
                        + "publisher varchar(100),\n"
                        + "isbn varchar(100), \n"
                        + "isAvailable boolean default true)");
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage() + " ... setupDatabase");
        }
    }

    private void setupMemberTable() {
        String tableName = "MEMBERS";

        try {
            statement = connection.createStatement();

            DatabaseMetaData databaseMetaData = connection.getMetaData();
            ResultSet tables = databaseMetaData.getTables(null, null, tableName.toUpperCase(), null);

            if (tables.next()) {
                System.out.println("Table " + tableName + " already exists.");

            } else {
                statement.execute("CREATE TABLE " + tableName + "("
                        + "id int primary key auto_increment, \n"
                        + "name varchar(200),\n"
                        + "surname varchar(200),\n"
                        + "phoneNumber varchar(100),\n"
                        + "email varchar(100))");
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage() + " ... setupDatabase");
        }
    }

    public ResultSet execQuery(String query) {
        ResultSet result;
        try {
            statement = connection.createStatement();
            result = statement.executeQuery(query);
        } catch (SQLException e) {
            System.out.println("Exception at execQuery: " + e.getLocalizedMessage());
            return null;
        }
        return result;
    }

    public boolean execAction(String query) {
        try {
            statement = connection.createStatement();
            statement.execute(query);
            return true;
        } catch (SQLException e) {
            System.out.println("Exception at execQuery: " + e.getLocalizedMessage());
            return false;
        }
    }
}
