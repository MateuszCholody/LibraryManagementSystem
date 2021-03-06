package library.ui.addBook;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import library.database.DatabaseHandler;

import java.awt.event.ActionEvent;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class addBookController implements Initializable {

    @FXML
    private TextField title;
    @FXML
    private TextField author;
    @FXML
    private TextField publisher;
    @FXML
    private TextField isbn;
    @FXML
    private StackPane rootPane;


    private DatabaseHandler databaseHandler;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        databaseHandler = DatabaseHandler.getInstance();
    }


    @FXML
    public void addBook(javafx.event.ActionEvent actionEvent) {
        String bookTitle = title.getText();
        String bookAuthor = author.getText();
        String bookPublisher = publisher.getText();
        String ISBN = isbn.getText();

        if (bookAuthor.isEmpty() || bookPublisher.isEmpty() || bookTitle.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Enter all fields");
            alert.show();
        } else {
            String query = "INSERT INTO BOOKS (TITLE, AUTHOR, PUBLISHER, ISBN, ISAVAILABLE) VALUES ("
                    + "'" + bookTitle + "',"
                    + "'" + bookAuthor + "',"
                    + "'" + bookPublisher + "',"
                    + "'" + ISBN + "',"
                    + "" + true + ""
                    + ")";
            if (databaseHandler.execAction(query)) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setHeaderText(null);
                alert.setContentText("Book added");
                alert.show();
                clearTextFields();
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText(null);
                alert.setContentText("Book adding failed");
                alert.show();
            }
        }
    }

    public void reset(javafx.event.ActionEvent actionEvent) {
        clearTextFields();
    }

    public void cancel(javafx.event.ActionEvent actionEvent) {
        Stage stage = (Stage) rootPane.getScene().getWindow();
        stage.close();
    }

    private void clearTextFields() {
        title.clear();
        author.clear();
        publisher.clear();
        isbn.clear();
    }
}
