package library.database;

import library.settings.Preferences;

import java.sql.*;

public final class DatabaseHandler {

    private static DatabaseHandler handler = null;

    private static String pass;
    private static String user;
    private static Connection connection = null;
    private static Statement statement = null;
    private static Grants grants;


    private DatabaseHandler() {
        createConnection();
        setupBookTable();
        setupMemberTable();
        setupIssueTable();
        setGrants();
    }

    private void setGrants() {
        try {
            statement = connection.createStatement();
            DatabaseMetaData databaseMetaData = connection.getMetaData();
            String user = databaseMetaData.getUserName().toUpperCase();
            switch (user) {
                case "ADMIN":
                    grants = Grants.ADMIN;
                    break;
                case "MANAGER":
                    grants = Grants.MANAGER;
                    break;
                case "LIBRARIAN":
                    grants = Grants.LIBRARIAN;
                default:
                    grants = Grants.NON;
            }

        } catch (SQLException e) {
            System.err.println(e.getMessage() + " ... setupDatabase");
        }
    }

    public static Grants getGrants() {
        return grants;
    }

    public static DatabaseHandler getInstance() {
        if (handler == null) {
            handler = new DatabaseHandler();
        }
        return handler;
    }

    public static DatabaseHandler getInstance(String userName, String password) {
        user = userName;
        pass = password;
        return getInstance();
    }

    private void createConnection() {
        try {
            connection = DriverManager.getConnection("jdbc:mysql://".concat(Preferences.getPreferences().
                    getDatabaseAddress()).concat(":").concat(Preferences.getPreferences().getDatabasePort()).
                    concat("/").concat(Preferences.getPreferences().getDatabaseName()), user, pass);
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
                        + "id int primary key auto_increment, \n"
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

    private void setupIssueTable() {
        String tableName = "ISSUES";

        try {
            statement = connection.createStatement();

            DatabaseMetaData databaseMetaData = connection.getMetaData();
            ResultSet tables = databaseMetaData.getTables(null, null, tableName.toUpperCase(), null);

            if (tables.next()) {
                System.out.println("Table " + tableName + " already exists.");

            } else {
                statement.execute("CREATE TABLE " + tableName + "(" +
                        "id int primary key auto_increment, \n" +
                        "issueTime timestamp default CURRENT_TIMESTAMP,\n" +
                        "renewCount int default 0,\n" + "BookID int,\n" +
                        "FOREIGN KEY (BookID) REFERENCES books(id),\n" +
                        "MemberID int,\n" + "isReturned boolean default false, \n" +
                        "FOREIGN KEY (MemberID) REFERENCES MEMBERS(id))");
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
