package library.ui.main;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MainController implements Initializable {

    @FXML
    public StackPane rootView;
    @FXML
    public TextField bookIDField;
    @FXML
    public Text bookTitle;
    @FXML
    public Text bookAuthor;
    @FXML
    public Text bookAvailable;
    @FXML
    public TextField memberIDField;
    @FXML
    public Text memberName;
    @FXML
    public Text memberPhoneNumber;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    @FXML
    public void loadAddMember(ActionEvent actionEvent) {
        loadWindow("/library/ui/addMember/add_member_layout.fxml", "Add Member");
    }

    @FXML
    public void loadAddBook(ActionEvent actionEvent) {
        loadWindow("/library/ui/addMember/add_member_layout.fxml", "Add Book");

    }

    @FXML
    public void loadMemberList(ActionEvent actionEvent) {
        loadWindow("/library/ui/listmember/member_list_layout.fxml", "Member List");

    }

    @FXML
    public void loadBookList(ActionEvent actionEvent) {
        loadWindow("/library/ui/listbook/book_list_layout.fxml", "Book List");

    }

    private void loadWindow(String location, String title) {
        try {
            Parent parent = FXMLLoader.load(getClass().getResource(location));
            Stage stage = new Stage(StageStyle.DECORATED);
            stage.setTitle(title);
            stage.setScene(new Scene(parent));
            stage.show();
        } catch (IOException e) {

        }

    }
}
