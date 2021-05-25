package library.settings;

import com.google.gson.Gson;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import library.UserNotFoundException;
import org.apache.commons.codec.digest.DigestUtils;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Preferences {
    public static final String CONFIG_FILE = "config";

    private int lendTime, renewalsNumber;
    private String databaseAddress, databasePort, databaseName;
    private Map<String, String> users;

    public Preferences() {
    }

    public Preferences(String admin, String password, String host, String port, String databaseName) {
        users = new HashMap<>();
        users.put(admin, DigestUtils.sha1Hex(password));
        lendTime = 30;
        renewalsNumber = 2;
        databaseAddress = host;
        databasePort = port;
        this.databaseName = databaseName;
    }

    public static void initConfig(Preferences preferences) {
        Writer writer = null;
        try {
            Gson gson = new Gson();
            writer = new FileWriter(CONFIG_FILE);
            gson.toJson(preferences, writer);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                writer.close();
            } catch (NullPointerException | IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static Preferences getPreferences() {
        Gson gson = new Gson();
        Preferences preferences = new Preferences();
        try {
            preferences = gson.fromJson(new FileReader(CONFIG_FILE), Preferences.class);
        } catch (FileNotFoundException e) {
            loadSetup();
            e.printStackTrace();
        }
        return preferences;
    }

    public static void writePreferences(Preferences preferences) {
        Writer writer = null;
        try {
            Gson gson = new Gson();
            writer = new FileWriter(CONFIG_FILE);
            gson.toJson(preferences, writer);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                writer.close();
            } catch (NullPointerException | IOException e) {
                e.printStackTrace();
            }
        }
    }

    public int getLendTime() {
        return lendTime;
    }

    public void setLendTime(int lendTime) {
        this.lendTime = lendTime;
    }

    public int getRenewalsNumber() {
        return renewalsNumber;
    }

    public void setRenewalsNumber(int renewalsNumber) {
        this.renewalsNumber = renewalsNumber;
    }

    public String getDatabaseAddress() {
        return databaseAddress;
    }

    public void setDatabaseAddress(String databaseAddress) {
        this.databaseAddress = databaseAddress;
    }

    public String getDatabasePort() {
        return databasePort;
    }

    public void setDatabasePort(String databasePort) {
        this.databasePort = databasePort;
    }

    public String getDatabaseName() {
        return databaseName;
    }

    public void setDatabaseName(String databaseName) {
        this.databaseName = databaseName;
    }

    public void addNewUser(String name, String password) {
        users.put(name, password);
    }

    public void deleteUser(String name) {
        users.remove(name);
    }

    public void editUser(String name, String password) throws UserNotFoundException {
        if (!checkUser(name, password)) {
            throw new UserNotFoundException("User ".concat(name).concat(" not found!"));
        } else {
            deleteUser(name);
            addNewUser(name, password);
        }
    }

    public boolean checkUser(String name, String password) {
        return users.containsKey(name) && users.containsValue(password);
    }

    private static void loadSetup() {
        try {
            Parent parent = FXMLLoader.load(Objects.requireNonNull(Preferences.class.getResource("/library/ui/setup/setup_layout.fxml")));

            Stage stage = new Stage(StageStyle.DECORATED);
            stage.setTitle("Library Management System Setup");
            stage.setScene(new Scene(parent));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
