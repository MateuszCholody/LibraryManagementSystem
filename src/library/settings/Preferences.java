package library.settings;

import com.google.gson.Gson;
import org.apache.commons.codec.digest.DigestUtils;

import java.io.*;
import java.lang.reflect.Type;

public class Preferences {
    public static final String CONFIG_FILE = "config.txt";

    private int lendTime, renewalsNumber;
    private String databaseAddress, databasePassword, databaseUserName;

    public Preferences() {
        lendTime = 30;
        renewalsNumber = 2;
        databaseAddress = "jdbc:mysql://localhost:3306/library";
        databaseUserName = "root";
        databasePassword = "enudle12!";
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

    public String getDatabasePassword() {
        return databasePassword;
    }

    public void setDatabasePassword(String databasePassword) {
        this.databasePassword = DigestUtils.sha1Hex(databasePassword);
    }

    public String getDatabaseUserName() {
        return databaseUserName;
    }

    public void setDatabaseUserName(String databaseUserName) {
        this.databaseUserName = databaseUserName;
    }

    public static void initConfig() {
        Writer writer = null;
        try {
            Preferences preferences = new Preferences();
            Gson gson = new Gson();
            writer = new FileWriter(CONFIG_FILE);
            gson.toJson(preferences, writer);
        } catch (IOException e) {

        } finally {
            try {
                writer.close();
            } catch (IOException e) {

            }
        }
    }

    public static Preferences getPreferences() {
        Gson gson = new Gson();
        Preferences preferences = new Preferences();
        try {
            preferences = gson.fromJson(new FileReader(CONFIG_FILE), Preferences.class);
        } catch (FileNotFoundException e) {
            initConfig();
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

        } finally {
            try {
                writer.close();
            } catch (IOException e) {

            }
        }
    }
}
