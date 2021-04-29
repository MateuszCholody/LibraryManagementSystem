package library.ui.listmember;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import library.database.DatabaseHandler;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class MemberListController implements Initializable {

    @FXML
    private AnchorPane rootView;
    @FXML
    private TableView<Member> tableView;
    @FXML
    private TableColumn<Member, String> idColumn;
    @FXML
    private TableColumn<Member, String> nameColumn;
    @FXML
    private TableColumn<Member, String> surnameColumn;
    @FXML
    private TableColumn<Member, String> phoneNumberColumn;
    @FXML
    private TableColumn<Member, Boolean> emailColumn;

    private ObservableList<Member> list = FXCollections.observableArrayList();


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initializeColumns();
        loadData();
    }

    private void loadData() {
        DatabaseHandler databaseHandler = DatabaseHandler.getInstance();


        String query = "SELECT * FROM MEMBERS";
        ResultSet resultSet = databaseHandler.execQuery(query);
        try {
            while (resultSet.next()) {
                int idDB = resultSet.getInt("id");
                String nameDB = resultSet.getString("name");
                String surnameDB = resultSet.getString("surname");
                String phoneNumberDB = resultSet.getString("phoneNumber");
                String emailDB = resultSet.getString("email");

                list.add(new Member(idDB, nameDB, surnameDB, phoneNumberDB, emailDB));

            }
        } catch (SQLException e) {

        }

        tableView.getItems().setAll(list);

    }

    private void initializeColumns() {
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        surnameColumn.setCellValueFactory(new PropertyValueFactory<>("surname"));
        phoneNumberColumn.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
        emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));

    }

    public static class Member {
        private final SimpleIntegerProperty id;
        private final SimpleStringProperty name;
        private final SimpleStringProperty surname;
        private final SimpleStringProperty phoneNumber;
        private final SimpleStringProperty email;

        public Member(int id, String name, String surname, String phoneNumber, String email) {
            this.id = new SimpleIntegerProperty(id);
            this.name = new SimpleStringProperty(name);
            this.surname = new SimpleStringProperty(surname);
            this.phoneNumber = new SimpleStringProperty(phoneNumber);
            this.email = new SimpleStringProperty(email);

        }

        public String getName() {
            return name.get();
        }

        public Integer getId() {
            return id.get();
        }

        public String getSurname() {
            return surname.get();
        }

        public String getPhoneNumber() {
            return phoneNumber.get();
        }

        public String getEmail() {
            return email.get();
        }

    }
}
