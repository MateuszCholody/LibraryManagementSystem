package library.ui.listbook;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableArray;
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

public class BookListController implements Initializable {

    @FXML
    private AnchorPane rootView;
    @FXML
    private TableView<Book> tableView;
    @FXML
    private TableColumn<Book, String> titleColumn;
    @FXML
    private TableColumn<Book, String> idColumn;
    @FXML
    private TableColumn<Book, String> authorColumn;
    @FXML
    private TableColumn<Book, String> publisherColumn;
    @FXML
    private TableColumn<Book, String> isbnColumn;
    @FXML
    private TableColumn<Book, Boolean> availableColumn;

    private ObservableList<Book> list = FXCollections.observableArrayList();


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initializeColumns();

        loadData();
    }

    private void loadData() {
        DatabaseHandler databaseHandler = DatabaseHandler.getInstance();


        String query = "SELECT * FROM BOOKS";
        ResultSet resultSet = databaseHandler.execQuery(query);
        try {
            System.out.println("title.get()");

            while (resultSet.next()) {
                String idDB = resultSet.getString("id");
                String titleDB = resultSet.getString("title");
                String authorDB = resultSet.getString("author");
                String publisherDB = resultSet.getString("publisher");
                String isbnDB = resultSet.getString("isbn");
                boolean availableDB = resultSet.getBoolean("isavailable");
                list.add(new Book(titleDB, idDB, authorDB, publisherDB, isbnDB, availableDB));
            }
        } catch (SQLException e) {

        }

        tableView.getItems().setAll(list);
    }

    private void initializeColumns() {
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        authorColumn.setCellValueFactory(new PropertyValueFactory<>("author"));
        publisherColumn.setCellValueFactory(new PropertyValueFactory<>("publisher"));
        isbnColumn.setCellValueFactory(new PropertyValueFactory<>("isbn"));
        availableColumn.setCellValueFactory(new PropertyValueFactory<>("available"));

    }

    public static class Book {
        private final SimpleStringProperty title;
        private final SimpleStringProperty id;
        private final SimpleStringProperty author;
        private final SimpleStringProperty publisher;
        private final SimpleStringProperty isbn;
        private final SimpleBooleanProperty available;

        public Book(String title, String id, String author, String publisher, String isbn, Boolean available) {
            this.title = new SimpleStringProperty(title);
            this.id = new SimpleStringProperty(id);
            this.author = new SimpleStringProperty(author);
            this.publisher = new SimpleStringProperty(publisher);
            this.isbn = new SimpleStringProperty(isbn);
            this.available = new SimpleBooleanProperty(available);

        }

        public String getTitle() {
            return title.get();
        }

        public String getId() {
            return id.get();
        }

        public String getAuthor() {
            return author.get();
        }

        public String getPublisher() {
            return publisher.get();
        }

        public String getIsbn() {
            return isbn.get();
        }

        public boolean isAvailable() {
            return available.get();
        }
    }
}
