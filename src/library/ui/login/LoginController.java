package library.ui.login;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import library.settings.Preferences;
import library.ui.main.MainController;
import org.apache.commons.codec.digest.DigestUtils;

import java.io.IOException;
import java.lang.ref.PhantomReference;
import java.net.URL;
import java.util.ResourceBundle;

public class LoginController implements Initializable {
    public TextField userName;
    public PasswordField password;
    public Label wrongCredentialsAlert;
    public AnchorPane rootPane;

    private Preferences preferences;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        preferences = Preferences.getPreferences();
    }

    public void handleLoginAction(ActionEvent actionEvent) {
        String name = userName.getText();
        String pass = DigestUtils.sha1Hex(password.getText());

        if (name.equals(preferences.getDatabaseUserName()) && pass.equals(preferences.getDatabasePassword())) {
            wrongCredentialsAlert.setVisible(false);
            closeStage();
            loadMain();
        } else {
            wrongCredentialsAlert.setVisible(true);
        }
    }

    private void closeStage() {
        ((Stage) rootPane.getScene().getWindow()).close();
    }

    public void handleCancelAction(ActionEvent actionEvent) {
        System.exit(0);
    }

    private void loadMain() {
        try {
            Parent parent = FXMLLoader.load(getClass().getResource("/library/ui/main/main_layout.fxml"));

            Stage stage = new Stage(StageStyle.DECORATED);
            stage.setTitle("Library Management System");
            stage.setScene(new Scene(parent));
            stage.show();
        } catch (IOException e) {

        }
    }
}
