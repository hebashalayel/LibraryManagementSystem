/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import DataBase.BookDatabaseHandler;
import DataBase.BorrowBookDatabaseHandler;
import DataBase.BorrowBookDetailsDatabaseHandler;
import Model.Book;
import Model.BorrowBookDetails;
import Model.Category;
import Model.User_Book;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import scenebuilderfxlibrarymanagmentsystem.SceneBuilderFxLibraryManagmentSystem;
import static scenebuilderfxlibrarymanagmentsystem.SceneBuilderFxLibraryManagmentSystem.Books;
import static scenebuilderfxlibrarymanagmentsystem.SceneBuilderFxLibraryManagmentSystem.BorrowedBooks;
import static scenebuilderfxlibrarymanagmentsystem.SceneBuilderFxLibraryManagmentSystem.BorrowedBooksDetails;
import static scenebuilderfxlibrarymanagmentsystem.SceneBuilderFxLibraryManagmentSystem.CategoriesDB;
import static scenebuilderfxlibrarymanagmentsystem.SceneBuilderFxLibraryManagmentSystem.LoginStage;
import static scenebuilderfxlibrarymanagmentsystem.SceneBuilderFxLibraryManagmentSystem.UserProfileStage;
import static scenebuilderfxlibrarymanagmentsystem.SceneBuilderFxLibraryManagmentSystem.UserStage;
import static scenebuilderfxlibrarymanagmentsystem.SceneBuilderFxLibraryManagmentSystem.setStageData;
import static scenebuilderfxlibrarymanagmentsystem.SceneBuilderFxLibraryManagmentSystem.userLogin;

/**
 * FXML Controller class
 *
 * @author HP
 */
public class UserDashboardController implements Initializable {

    @FXML
    private AnchorPane AnchorPaneBorrowedBookManagment;
    @FXML
    private Label sideBarHome;
    @FXML
    private AnchorPane AnchorPaneHome;
    @FXML
    private AnchorPane AnchorPaneUserManagment;
    @FXML
    private ImageView userProfileImage;
    @FXML
    private Label userProfileFullName;
    @FXML
    private ComboBox<String> categoryCb;
    @FXML
    private ComboBox<String> bookCb;
    @FXML
    private ImageView imageViewBook;
    @FXML
    private TextField titleTextField;
    @FXML
    private TextField autherTextField;
    @FXML
    private TextField isbnTextField;
    @FXML
    private TextField publishDateTextField;
    @FXML
    private TextField copyCountTextField;
    @FXML
    private TextField pageCountTextField;
    @FXML
    private TextField publisherTextField;
    @FXML
    private TextField categoryTextField;
    public String imageName = null;
    Image[] profileImage = {null};
    ObservableList<String> booksByCategory = FXCollections.observableArrayList();
    String imageNameBook = null;
    Image[] profileImageBook = {null};
    @FXML
    private Button buttonPending;
    @FXML
    private Button clearButton;
    @FXML
    private Button borrowButton;
    @FXML
    private Label sideBarBorrowBookManagment;
    @FXML
    private Label sideBarBorrowedBookManagment;
    @FXML
    private Label sideBarLogout;
    public static int book_id = -1;
    public static int user_id = -1;
    @FXML
    private Button buttonApproved;
    @FXML
    private FlowPane boxesFlowPane;
    ObservableList<BorrowBookDetails> borrowedBooksPerUser = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //-------------------------set boxes to flowPane------------------------
        borrowedBooksPerUser.setAll(BorrowBookDetailsDatabaseHandler.getBorrowBooksDetailsDataPerUser(userLogin.getId()));
        for (BorrowBookDetails bbd : borrowedBooksPerUser) {
            createVBox(bbd);
        }
        //-----------------Update sideBar information------------------------------
        UserStage.setOnShowing(e -> {
            userProfileFullName.setText(userLogin.getFullName());
            imageName = userLogin.getProfileImagePath();
            user_id = userLogin.getId();
            profileImage[0] = new Image(SceneBuilderFxLibraryManagmentSystem.class.getResourceAsStream(userLogin.getProfileImagePath()));
            userProfileImage.setImage(profileImage[0]);
            categoryCb.getStyleClass().remove("valid");
            categoryCb.getStyleClass().remove("invalid");
            bookCb.getStyleClass().remove("invalid");
            bookCb.getStyleClass().remove("valid");
        });
        //-------------------------------side bar------------------------------------------
        sideBarHome.getStyleClass().add("sideBar-label");
        sideBarBorrowBookManagment.getStyleClass().add("sideBar-label");
        sideBarBorrowedBookManagment.getStyleClass().add("sideBar-label");
        SetSelectedSideBar(sideBarHome, AnchorPaneHome);
        //---------------------initialize category combobox values--------------------------
        for (Category cat : CategoriesDB) {
            categoryCb.getItems().add(cat.getName());
        }
        //---------------------set Booktitlecombobox----------------------------------------     
        categoryCb.valueProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                bookCb.setValue(null);
                booksByCategory = getBooks(categoryCb.getValue());
                bookCb.setItems(booksByCategory);
                System.out.println(bookCb.getItems());
            }
        });
        //-------------------set button borrow selected------------------------------------
        setSelectedButton(borrowButton);
    }

    @FXML
    private void ShowSideBarHome(MouseEvent event) {
        SetSelectedSideBar(sideBarHome, AnchorPaneHome);
    }

    @FXML
    private void ShowSideBarBorrowBookManagment(MouseEvent event) {
        SetSelectedSideBar(sideBarBorrowBookManagment, AnchorPaneUserManagment);
        categoryCb.getStyleClass().remove("valid");
        categoryCb.getStyleClass().remove("invalid");
        bookCb.getStyleClass().remove("invalid");
        bookCb.getStyleClass().remove("valid");
        categoryCb.setValue(null);
        bookCb.setValue(null);
        titleTextField.clear();
        autherTextField.clear();
        isbnTextField.clear();
        publishDateTextField.clear();
        pageCountTextField.clear();
        copyCountTextField.clear();
        publisherTextField.clear();
        categoryTextField.clear();
        this.imageNameBook = "/images/bk1.jpg";
        profileImageBook[0] = new Image(SceneBuilderFxLibraryManagmentSystem.class.getResourceAsStream(this.imageNameBook));
        imageViewBook.setImage(profileImageBook[0]);
        setSelectedButton(borrowButton);
    }

    @FXML
    private void ShowSideBarBorrowedBookManagent(MouseEvent event) {
        SetSelectedSideBar(sideBarBorrowedBookManagment, AnchorPaneBorrowedBookManagment);
    }

    @FXML
    private void Logout(MouseEvent event) {
        UserStage.close();
        LoginStage.show();
    }

    @FXML
    private void ShowUserProfileStage(MouseEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/View/UserProfile.fxml"));
        Scene userProfileScene = new Scene(root);
        setStageData(UserProfileStage, userProfileScene, "log.jpg", "User  Profile", 600, 250);
        UserProfileStage.show();
        UserStage.close();
    }

    @FXML
    private void ApplySearch(ActionEvent event) {
        setSelectedButton(borrowButton);
        //--------------------validation------------------------
        boolean hasError = false;
        if (!validateComboBox(bookCb)) {
            hasError = true;
        }
        if (!validateComboBox(categoryCb)) {
            hasError = true;
        }
        //------------------------show book information--------------------
        if (!hasError) {
            String bookName = bookCb.getValue();
            String bookData[] = bookName.split("-");
            int bookId = Integer.parseInt(bookData[0]);
            book_id = bookId;
            Book book = getBook(bookId);
            titleTextField.setText(book.getTitle());
            autherTextField.setText(book.getAuthor());
            categoryTextField.setText(book.getCategory());
            isbnTextField.setText(book.getIsbn());
            publishDateTextField.setText(book.getPublishDate());
            pageCountTextField.setText(book.getPageCount() + "");
            copyCountTextField.setText(book.getCopyCount() + "");
            publisherTextField.setText(book.getPublisher());
            profileImageBook[0] = new Image(SceneBuilderFxLibraryManagmentSystem.class.getResourceAsStream(book.getBookImagePath()));
            imageViewBook.setImage(profileImageBook[0]);
            for (BorrowBookDetails bd : BorrowedBooksDetails) {
                if (book.getId() == bd.getBookId() && userLogin.getId() == bd.getUserId()) {
                    if (bd.getStatus().equals("Pending")) {
                        setSelectedButton(buttonPending);
                    } else if (bd.getStatus().equals("Approved")) {
                        setSelectedButton(buttonApproved);
                    } else {
                        setSelectedButton(borrowButton);
                    }
                }
            }
        }
    }

    @FXML
    private void clear(ActionEvent event) {
        categoryCb.getStyleClass().remove("valid");
        categoryCb.getStyleClass().remove("invalid");
        bookCb.getStyleClass().remove("invalid");
        bookCb.getStyleClass().remove("valid");
        categoryCb.setValue(null);
        bookCb.setValue(null);
        titleTextField.clear();
        autherTextField.clear();
        isbnTextField.clear();
        publishDateTextField.clear();
        pageCountTextField.clear();
        copyCountTextField.clear();
        publisherTextField.clear();
        categoryTextField.clear();
        this.imageNameBook = "/images/bk1.jpg";
        profileImageBook[0] = new Image(SceneBuilderFxLibraryManagmentSystem.class.getResourceAsStream(this.imageNameBook));
        imageViewBook.setImage(profileImageBook[0]);
        book_id = -1;
        setSelectedButton(borrowButton);
    }

    @FXML
    private void BorrowBook(ActionEvent event) {
        Book bookToBorrow = getBook(book_id);
        //make sure there is book to borrow       
        if (bookToBorrow != null) {
            System.out.println("----------------" + user_id);
            user_id = userLogin.getId();
            User_Book ub = new User_Book(user_id, book_id);
            BorrowedBooks.add(ub);
            BorrowBookDatabaseHandler.addUser_Book(ub);
            Book b = getBook(book_id);
            if (b != null) {
                LocalDate deliveryDate = LocalDate.now();
                BorrowBookDetails bookDetails = new BorrowBookDetails(user_id, userLogin.getUserName(), userLogin.getProfileImagePath(), book_id, b.getTitle(), b.getBookImagePath(), "Pending", deliveryDate, null);
                BorrowedBooksDetails.add(bookDetails);
                BorrowBookDetailsDatabaseHandler.addBorrowBookDetails(bookDetails);
                BorrowedBooksDetails.setAll(BorrowBookDetailsDatabaseHandler.getBorrowBooksDetailsData());
                borrowedBooksPerUser.setAll(BorrowBookDetailsDatabaseHandler.getBorrowBooksDetailsDataPerUser(userLogin.getId()));
                createVBox(bookDetails);
            }
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Your Request Pending");
            alert.showAndWait();
            clearFields();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Make Search about book to borrow");
            alert.showAndWait();
        }
    }

    private void clearFields() {
        categoryCb.getStyleClass().remove("valid");
        categoryCb.getStyleClass().remove("invalid");
        bookCb.getStyleClass().remove("invalid");
        bookCb.getStyleClass().remove("valid");
        categoryCb.setValue(null);
        bookCb.setValue(null);
        titleTextField.clear();
        autherTextField.clear();
        isbnTextField.clear();
        publishDateTextField.clear();
        pageCountTextField.clear();
        copyCountTextField.clear();
        publisherTextField.clear();
        categoryTextField.clear();
        this.imageNameBook = "/images/bk1.jpg";
        profileImageBook[0] = new Image(SceneBuilderFxLibraryManagmentSystem.class.getResourceAsStream(this.imageNameBook));
        imageViewBook.setImage(profileImageBook[0]);
        book_id = -1;
        setSelectedButton(borrowButton);
    }

    public void createVBox(BorrowBookDetails bbd) {
        VBox box = new VBox(5);
        box.getStyleClass().add("box");
        ImageView borrowedBookImageView = new ImageView(new Image(bbd.getBookImage()));
        borrowedBookImageView.setFitWidth(80);
        borrowedBookImageView.setFitHeight(80);
        Label titleLabel = new Label(bbd.getBookTitle());
        Label deliveryDateLabel = new Label("Delivery Date: " + bbd.getDeliveryDate());
        String status = bbd.getStatus();
        Label statusLabel = new Label("Status: " + status);
        Button statusBtn = new Button();
        if (status.equals("Pending")) {
            statusBtn.getStyleClass().add("pendingBtn");
            statusBtn.setText("Pending");
            statusBtn.setDisable(true);
        } else if (status.equals("Approved")) {
            statusBtn.getStyleClass().remove("pendingBtn");
            statusBtn.setText("Return");
            statusBtn.setDisable(false);
        }
        //---------------------action on return because the pending is disabled-------------------
        statusBtn.setOnAction(e -> {
            boxesFlowPane.getChildren().remove(box);
            BorrowedBooksDetails.remove(bbd);
            BorrowBookDetailsDatabaseHandler.deleteBorrowBookDetails(bbd);
            try {
                BookDatabaseHandler.updateCopyCount(bbd.getBookId());
            } catch (SQLException ex) {
                Logger.getLogger(UserDashboardController.class.getName()).log(Level.SEVERE, null, ex);
            }
            User_Book ub = new User_Book(bbd.getUserId(),
                     bbd.getBookId()
            );
            BorrowedBooks.remove(ub);
            BorrowBookDatabaseHandler.deleteUser_Book(ub);
            BorrowedBooksDetails.setAll(BorrowBookDetailsDatabaseHandler.getBorrowBooksDetailsData());
            borrowedBooksPerUser.setAll(BorrowBookDetailsDatabaseHandler.getBorrowBooksDetailsDataPerUser(userLogin.getId()));
        });
        box.getChildren().addAll(borrowedBookImageView, titleLabel, deliveryDateLabel, statusLabel, statusBtn);
        boxesFlowPane.getChildren().add(box);
    }

    public void SetSelectedSideBar(Label selectedLabel, AnchorPane selectedAnchorPane) {
        sideBarHome.getStyleClass().remove("selected");
        sideBarBorrowBookManagment.getStyleClass().remove("selected");
        sideBarBorrowedBookManagment.getStyleClass().remove("selected");
        selectedLabel.getStyleClass().add("selected");
        AnchorPaneHome.setVisible(false);
        AnchorPaneUserManagment.setVisible(false);
        AnchorPaneBorrowedBookManagment.setVisible(false);
        selectedAnchorPane.setVisible(true);
    }

    public void setSelectedButton(Button selectedButton) {
        buttonPending.setVisible(false);
        buttonPending.setManaged(false);
        borrowButton.setVisible(false);
        borrowButton.setManaged(false);
        buttonApproved.setVisible(false);
        buttonApproved.setManaged(false);
        selectedButton.setVisible(true);
        selectedButton.setManaged(true);
    }

    //----------------get books according to category selected------------------------
    private ObservableList<String> getBooks(String category) {
        ArrayList<String> titles = new ArrayList<>();
        for (Book book : Books) {
            if (book.getCategory().equals(category)) {
                titles.add(book.getId() + "-" + book.getTitle());
            }
        }
        booksByCategory.setAll(titles);
        return booksByCategory;
    }

    //------------------getBook to show information to form----------------------------
    private Book getBook(int bookId) {
        for (Book book : Books) {
            if (book.getId() == bookId) {
                return book;
            }
        }
        return null;
    }

    public boolean validateComboBox(ComboBox cb) {
        boolean validate = false;
        if (cb.getValue() == null) {
            cb.getStyleClass().removeAll("valid", "invalid");
            cb.getStyleClass().add("invalid");
        } else {
            cb.getStyleClass().removeAll("valid", "invalid");
            cb.getStyleClass().add("valid");
            validate = true;
        }
        return validate;
    }

    @FXML
    private void logoutSideBar(MouseEvent event) {
        UserStage.close();
        LoginStage.show();
    }
}
