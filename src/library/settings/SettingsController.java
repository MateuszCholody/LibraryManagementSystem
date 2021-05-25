package library.settings;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.apache.commons.codec.digest.DigestUtils;

import java.net.URL;
import java.util.ResourceBundle;

public class SettingsController implements Initializable {


    public PasswordField databasePassword;
    public TextField lendTime;
    public TextField databaseAddress;
    public TextField renewalsNumber;
    public TextField resetField;
    public TextField userName;
    public AnchorPane rootPane;

    public void handleTestConnectionButtonAction(ActionEvent actionEvent) {
    }

    public void handleCancelButtonAction(ActionEvent actionEvent) {
        Stage stage = (Stage) rootPane.getScene().getWindow();
        stage.close();
    }

    public void handleApplyButtonAction(ActionEvent actionEvent) {
        if (resetField.getText().equalsIgnoreCase("RESET")) {
            //TODO
        }
        Preferences preferences = Preferences.getPreferences();
        preferences.setDatabaseAddress(databaseAddress.getText());
        preferences.addNewUser(userName.getText(), DigestUtils.sha1Hex(databasePassword.getText()));
        preferences.setLendTime(Integer.parseInt(lendTime.getText()));
        preferences.setRenewalsNumber(Integer.parseInt(renewalsNumber.getText()));

        Preferences.writePreferences(preferences);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initDefaultValues();
    }

    private void initDefaultValues() {
        Preferences preferences = Preferences.getPreferences();
        lendTime.setText(String.valueOf(preferences.getLendTime()));
        renewalsNumber.setText(String.valueOf(preferences.getRenewalsNumber()));
        databaseAddress.setText(String.valueOf(preferences.getDatabaseAddress()));


    }
}
