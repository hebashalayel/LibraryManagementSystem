package Controller;

import Model.Book;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import static scenebuilderfxlibrarymanagmentsystem.SceneBuilderFxLibraryManagmentSystem.Books;

/**
 * FXML Controller class
 *
 * @author HP
 */
public class AllBooksInSystemController implements Initializable {
    @FXML
    private TableView<Book> tableViewBooks;
    @FXML
    private TableColumn<Book, String> titleCol;
    @FXML
    private TableColumn<Book, String> autherCol;
    @FXML
    private TableColumn<Book, String> categoryCol;
    @FXML
    private TableColumn<Book, String> isbnCol;
    @FXML
    private TableColumn<Book, String> languageCol;
    @FXML
    private TableColumn<Book, Integer> copyCountCol;
    @FXML
    private TableColumn<Book, Integer> pageCountCol;
    @FXML
    private TableColumn<Book, ImageView> ivColBook;
    @FXML
    private TextField searchtxtfield;
    @FXML
    private Button clearBtn;
    private ObservableList<Book> originalBookList = FXCollections.observableArrayList();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {        
        titleCol.setCellValueFactory(new PropertyValueFactory<>("title"));
        autherCol.setCellValueFactory(new PropertyValueFactory<>("author"));
        categoryCol.setCellValueFactory(new PropertyValueFactory<>("category"));
        isbnCol.setCellValueFactory(new PropertyValueFactory<>("isbn"));
        languageCol.setCellValueFactory(new PropertyValueFactory<>("language"));
        pageCountCol.setCellValueFactory(new PropertyValueFactory<>("pageCount"));
        copyCountCol.setCellValueFactory(new PropertyValueFactory<>("copyCount"));
        ivColBook.setCellValueFactory(cellData -> {
            ImageView bookImage = new ImageView(cellData.getValue().getBookImagePath());
            bookImage.setFitWidth(40);
            bookImage.setFitHeight(40);
            return new SimpleObjectProperty<>(bookImage);
        });
        originalBookList.setAll(Books);
        tableViewBooks.setItems(originalBookList);
        searchtxtfield.textProperty().addListener((observable, oldValue, newValue) -> {
            applyFilter(newValue);
        });
    }

    @FXML
    private void Clear(ActionEvent event) {
        searchtxtfield.setText("");
        tableViewBooks.setItems(originalBookList);
    }

    private void applyFilter(String filter) {
        if (filter == null || filter.isEmpty()) {
            tableViewBooks.setItems(originalBookList);
            return;
        }
        ObservableList<Book> filteredList = FXCollections.observableArrayList();
        String lowerCaseFilter = filter.toLowerCase();
        for (Book book : originalBookList) {
            if (book.getTitle().toLowerCase().contains(lowerCaseFilter)
                    || book.getAuthor().toLowerCase().contains(lowerCaseFilter)
                    || book.getCategory().toLowerCase().contains(lowerCaseFilter)
                    || book.getLanguage().toLowerCase().contains(lowerCaseFilter)
                    || book.getIsbn().toLowerCase().contains(lowerCaseFilter)
                    || String.valueOf(book.getCopyCount()).contains(filter)
                    || String.valueOf(book.getPageCount()).contains(filter)
                    ) {
                filteredList.add(book);
            }
        }
        tableViewBooks.setItems(filteredList);
    }
}