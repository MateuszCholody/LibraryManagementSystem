package library.ui.setup;

import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.skin.CellSkinBase;
import library.settings.Preferences;


public class SetupController {
    public PasswordField adminPassword;
    public TextField adminName;
    public TextField databaseHost;
    public TextField databasePort;
    public TextField databaseName;

    public void handleTestConnectionOperation(ActionEvent actionEvent) {
        //TODO
    }

    public void handleSaveOperation(ActionEvent actionEvent) {
        String admin = adminName.getText();
        String password = adminPassword.getText();
        String host = databaseHost.getText();
        String port = databasePort.getText();
        String database = databaseName.getText();

        if ((admin.isBlank() || password.isBlank() || host.isBlank() || port.isBlank() || database.isBlank())) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Missing credentials!");
            alert.setHeaderText(null);
            alert.setContentText("Enter all credentials");
            alert.show();
        } else {
            Preferences.initConfig(new Preferences(admin, password, host, port, database));
        }
    }

    public void handleCancelOperation(ActionEvent actionEvent) {
    }
}
