package library.ui.main;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import library.database.DatabaseHandler;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Optional;
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
    public TextField bookID;
    public ListView issueList;

    DatabaseHandler handler;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        handler = DatabaseHandler.getInstance();

    }

    @FXML
    public void loadAddMember(ActionEvent actionEvent) {
        loadWindow("/library/ui/addMember/add_member_layout.fxml", "Add Member");
    }

    @FXML
    public void loadAddBook(ActionEvent actionEvent) {
        loadWindow("/library/ui/addBook/add_book_layout.fxml", "Add Book");

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


    public void loadBookInfo(ActionEvent actionEvent) {
        String id = bookIDField.getText();
        String query = "SELECT * FROM BOOKS WHERE id = '" + id + "'";
        ResultSet resultSet = handler.execQuery(query);
        try {
            boolean flag = true;
            while (resultSet.next()) {

                bookTitle.setText(resultSet.getString("title"));
                bookAuthor.setText(resultSet.getString("author"));
                bookAvailable.setText(resultSet.getBoolean("isavailable") ? "Available" : "Not Available");

                flag = false;
            }
            if (flag) {
                bookTitle.setText("NA");
                bookAuthor.setText("NA");
                bookAvailable.setText("NA");
            }

        } catch (SQLException e) {

        }
    }

    public void loadMemberInfo(ActionEvent actionEvent) {
        String id = memberIDField.getText();
        String query = "SELECT * FROM MEMBERS WHERE id = '" + id + "'";
        ResultSet resultSet = handler.execQuery(query);
        try {
            boolean flag = true;
            while (resultSet.next()) {

                memberName.setText(resultSet.getString("name"));
                memberPhoneNumber.setText(resultSet.getString("phonenumber"));
                flag = false;
            }
            if (flag) {
                memberName.setText("NA");
                memberPhoneNumber.setText("NA");
            }

        } catch (SQLException e) {

        }
    }

    public void loadIssueBookOperation(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirm Issue");
        alert.setHeaderText(null);
        alert.setContentText("Do you want to issue the book " + bookTitle.getText() + "\n to " + memberName.getText() + "?");

        Optional<ButtonType> response = alert.showAndWait();
        if (response.get() == ButtonType.OK) {
            String issueQuery = "INSERT INTO ISSUES(bookID, memberID) VALUES ( + "
                    + "'" + bookIDField.getText() + "',"
                    + "'" + memberIDField.getText() + "')";
            String alterTableQuery = "UPDATE BOOKS SET isAVAILABLE = false WHERE id = '" + bookIDField.getText() + "'";

            if (handler.execAction(issueQuery) && handler.execAction(alterTableQuery)) {
                Alert alertSuccess = new Alert(Alert.AlertType.INFORMATION);
                alertSuccess.setTitle("Issue Confirmed");
                alertSuccess.setHeaderText(null);
                alertSuccess.setContentText(bookTitle.getText() + " issued");
                alertSuccess.showAndWait();
            } else {
                Alert alertFailed = new Alert(Alert.AlertType.ERROR);
                alertFailed.setTitle("Issue Failed");
                alertFailed.setHeaderText(null);
                alertFailed.setContentText(bookTitle.getText() + " issue failed");
                alertFailed.showAndWait();
            }
        } else {
            Alert alertFailed = new Alert(Alert.AlertType.INFORMATION);
            alertFailed.setTitle("Issue Canceled");
            alertFailed.setHeaderText(null);
            alertFailed.setContentText(bookTitle.getText() + " issue canceled");
            alertFailed.showAndWait();
        }
    }

    public void loadIssuedBookInfo(ActionEvent actionEvent) {
        ObservableList<String> issueListData = FXCollections.observableArrayList();
        String mbookID = bookID.getText();
        String query = "SELECT * FROM ISSUES WHERE ID + '" + Integer.parseInt(mbookID) + "'";
        ResultSet resultSet = handler.execQuery(query);
        try {
            while (resultSet.next()) {

                String mMemberID = resultSet.getString("memberID");
                Timestamp mIssueTime = resultSet.getTimestamp("issueTime");
                int mRenewCount = resultSet.getInt("renewCount");

                issueListData.add("Issue Data and Time: " + mIssueTime.toGMTString());
                issueListData.add("Renew Count: " + mRenewCount);
                System.out.println(mbookID);

                query = "SELECT * FROM BOOKS WHERE ID = '" + mbookID + "'";
                ResultSet issueResultSet = handler.execQuery(query);
                issueListData.add("Book Information: ");
                while (issueResultSet.next()) {
                    issueListData.add("Book Title: " + issueResultSet.getString("title"));
                    issueListData.add("Book ISBN: " + issueResultSet.getString("ISBN"));
                    issueListData.add("Book Author: " + issueResultSet.getString("author"));
                    issueListData.add("Book Publisher: " + issueResultSet.getString("publisher"));
                    issueListData.add("Book ID: " + issueResultSet.getString("id"));
                }

                query = "SELECT * FROM MEMBERS WHERE ID = '" + mMemberID + "'";
                issueResultSet = handler.execQuery(query);
                issueListData.add("Member Information: ");
                while (issueResultSet.next()) {
                    issueListData.add("Member Name: " + issueResultSet.getString("name") + " " + issueResultSet.getString("surname"));
                    issueListData.add("Member Phone Number: " + issueResultSet.getString("phoneNumber"));
                    issueListData.add("Member id: " + issueResultSet.getString("id"));
                }
            }
        } catch (SQLException e) {
            System.out.println(123);

        }
        issueList.getItems().setAll(issueListData);

    }
}
