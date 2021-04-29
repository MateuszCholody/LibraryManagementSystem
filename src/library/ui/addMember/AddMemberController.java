package library.ui.addMember;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import library.database.DatabaseHandler;

import java.net.URL;
import java.util.ResourceBundle;

public class AddMemberController implements Initializable {
    @FXML
    public StackPane rootPane;
    @FXML
    public AnchorPane mainContainer;
    @FXML
    public TextField firstName;
    @FXML
    public TextField surname;
    @FXML
    public TextField phoneNumber;
    @FXML
    public TextField email;
    @FXML
    public Button saveButton;
    @FXML
    public Button resetButton;
    @FXML
    public Button cancelButton1;

    DatabaseHandler databaseHandler;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        databaseHandler = DatabaseHandler.getInstance();
    }

    public void addMember(ActionEvent actionEvent) {
        String memberName = firstName.getText();
        String memberSurname = surname.getText();
        String memberPhoneNumber = phoneNumber.getText();
        String memberEmail = email.getText();

        if (memberName.isEmpty() || memberSurname.isEmpty() || memberPhoneNumber.isEmpty() || memberEmail.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Enter all fields");
            alert.show();
        } else {
            String query = "INSERT INTO MEMBERS (NAME, SURNAME, PHONENUMBER, EMAIL) VALUES ("
                    + "'" + memberName + "',"
                    + "'" + memberSurname + "',"
                    + "'" + memberPhoneNumber + "',"
                    + "'" + memberEmail + "'"
                    + ")";
            if (databaseHandler.execAction(query)) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setHeaderText(null);
                alert.setContentText("Member added");
                alert.show();
                clearTextFields();
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText(null);
                alert.setContentText("Member adding failed");
                alert.show();
            }
        }

    }

    public void reset(ActionEvent actionEvent) {
        clearTextFields();
    }

    public void cancel(ActionEvent actionEvent) {
        Stage stage = (Stage) rootPane.getScene().getWindow();
        stage.close();
    }


    private void clearTextFields() {
        firstName.clear();
        surname.clear();
        phoneNumber.clear();
        email.clear();
    }
}
