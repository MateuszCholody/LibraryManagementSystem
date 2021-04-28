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
        System.out.println("jfahsk");

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
            System.out.println("vjsnk");
            return true;
        } catch (SQLException e) {
            System.out.println("jfahsk");

            System.out.println("Exception at execQuery: " + e.getLocalizedMessage());
            return false;
        }
    }
}
