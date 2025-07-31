/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import DataBase.BorrowBookDetailsDatabaseHandler;
import Model.BorrowBookDetails;
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
import javafx.scene.input.MouseEvent;
import static scenebuilderfxlibrarymanagmentsystem.SceneBuilderFxLibraryManagmentSystem.BookStatisticStage;
import static scenebuilderfxlibrarymanagmentsystem.SceneBuilderFxLibraryManagmentSystem.BorrowedBooksDetails;
import static scenebuilderfxlibrarymanagmentsystem.SceneBuilderFxLibraryManagmentSystem.approvedBooks;
import static scenebuilderfxlibrarymanagmentsystem.SceneBuilderFxLibraryManagmentSystem.pendingBooks;

/**
 * FXML Controller class
 *
 * @author HP
 */
public class BookStatisticController implements Initializable {

    @FXML
    private TextField searchtxtfield;
    @FXML
    private Button clearBtn;
    @FXML
    private TableView<BorrowBookDetails> booksTabel;
    @FXML
    private TableColumn<BorrowBookDetails, Integer> userIdCol;
    @FXML
    private TableColumn<BorrowBookDetails, String> userNameCol;
    @FXML
    private TableColumn<BorrowBookDetails, ImageView> userImageCol;
    @FXML
    private TableColumn<BorrowBookDetails, Integer> bookIdCol;
    @FXML
    private TableColumn<BorrowBookDetails, String> bookTitleCol;
    @FXML
    private TableColumn<BorrowBookDetails, ImageView> bookImageCol;
    @FXML
    private TableColumn<BorrowBookDetails, String> borrowStatusCol;
    String status = "";
    private ObservableList<BorrowBookDetails> originalBookDetailsList = FXCollections.observableArrayList();
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        booksTabel.setItems(BorrowedBooksDetails);
        userIdCol.setCellValueFactory(new PropertyValueFactory<>("userId"));
        userNameCol.setCellValueFactory(new PropertyValueFactory<>("userName"));
        userImageCol.setCellValueFactory(cellData -> {
            ImageView userImage = new ImageView(cellData.getValue().getUserImage());
            userImage.setFitWidth(40);
            userImage.setFitHeight(40);
            return new SimpleObjectProperty<>(userImage);
        });
        bookIdCol.setCellValueFactory(new PropertyValueFactory<>("bookId"));
        bookTitleCol.setCellValueFactory(new PropertyValueFactory<>("bookTitle"));
        bookImageCol.setCellValueFactory(cellData -> {
            ImageView bookImage = new ImageView(cellData.getValue().getBookImage());
            bookImage.setFitWidth(40);
            bookImage.setFitHeight(40);
            return new SimpleObjectProperty<>(bookImage);
        });
        borrowStatusCol.setCellValueFactory(new PropertyValueFactory<>("status"));
        //----------------------------
        searchtxtfield.textProperty().addListener((observable, oldValue, newValue) -> {
            applyFilter(newValue);
        });
    }

    @FXML
    private void Clear(ActionEvent event) {
        searchtxtfield.setText("");
    }

    @FXML
    private void logout(MouseEvent event) {
        BookStatisticStage.close();
    }

    private void applyFilter(String filter) {
        if (filter == null || filter.isEmpty()) {
            booksTabel.setItems(originalBookDetailsList);
            return;
        }
        ObservableList<BorrowBookDetails> filteredList = FXCollections.observableArrayList();
        String lowerCaseFilter = filter.toLowerCase();
        for (BorrowBookDetails bbd : originalBookDetailsList) {
            if (bbd.getUserName().toLowerCase().contains(lowerCaseFilter)
                    || bbd.getBookTitle().toLowerCase().contains(lowerCaseFilter)
                    || String.valueOf(bbd.getUserId()).contains(filter)
                    || String.valueOf(bbd.getBookId()).contains(filter)
                    ||bbd.getStatus().toLowerCase().contains(lowerCaseFilter)) {
                filteredList.add(bbd);
            }
        }
        booksTabel.setItems(filteredList);
    }

    public void setStatus(String status){
        this.status = status;
        if ("Pending".equals(status)) {
            pendingBooks.setAll(BorrowBookDetailsDatabaseHandler.get("Pending"));
            booksTabel.setItems(pendingBooks);
        } else if ("Approved".equals(status)) {
            approvedBooks.setAll(BorrowBookDetailsDatabaseHandler.get("Approved"));
            booksTabel.setItems(approvedBooks);
        } else{
            booksTabel.setItems(BorrowedBooksDetails);
        }
        originalBookDetailsList.setAll(booksTabel.getItems());
    }
}
