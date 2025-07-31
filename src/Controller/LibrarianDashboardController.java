package Controller;

import DataBase.BookDatabaseHandler;
import DataBase.BorrowBookDatabaseHandler;
import DataBase.BorrowBookDetailsDatabaseHandler;
import Model.Book;
import Model.BorrowBookDetails;
import Model.User_Book;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import scenebuilderfxlibrarymanagmentsystem.SceneBuilderFxLibraryManagmentSystem;
import static scenebuilderfxlibrarymanagmentsystem.SceneBuilderFxLibraryManagmentSystem.AllBookInSystemStage;
import static scenebuilderfxlibrarymanagmentsystem.SceneBuilderFxLibraryManagmentSystem.BookStatisticStage;
import static scenebuilderfxlibrarymanagmentsystem.SceneBuilderFxLibraryManagmentSystem.Books;
import static scenebuilderfxlibrarymanagmentsystem.SceneBuilderFxLibraryManagmentSystem.BorrowedBooks;
import static scenebuilderfxlibrarymanagmentsystem.SceneBuilderFxLibraryManagmentSystem.BorrowedBooksDetails;
import static scenebuilderfxlibrarymanagmentsystem.SceneBuilderFxLibraryManagmentSystem.LibrarianStage;
import static scenebuilderfxlibrarymanagmentsystem.SceneBuilderFxLibraryManagmentSystem.LoginStage;
import static scenebuilderfxlibrarymanagmentsystem.SceneBuilderFxLibraryManagmentSystem.UserProfileStage;
import static scenebuilderfxlibrarymanagmentsystem.SceneBuilderFxLibraryManagmentSystem.setStageData;
import static scenebuilderfxlibrarymanagmentsystem.SceneBuilderFxLibraryManagmentSystem.userLogin;

/**
 * FXML Controller class
 *
 * @author HP
 */
public class LibrarianDashboardController implements Initializable {

    public String imageName = null;
    Image[] profileImage = {null};

    @FXML
    private Label sideBarHome;
    @FXML
    private Label sideBarBookManagment;
    @FXML
    private AnchorPane AnchorPaneHome;
    @FXML
    private AnchorPane AnchorPaneBookManagment;
    @FXML
    private ImageView userProfileImage;
    @FXML
    private Label userProfileFullName;
    @FXML
    private Label sideBarStatistics;
    @FXML
    private ComboBox<String> statuseCB;
    @FXML
    private TableColumn<BorrowBookDetails, Integer> userIdCol;
    @FXML
    private TableColumn<BorrowBookDetails, String> userNameCol;
    @FXML
    private TableColumn<BorrowBookDetails, ImageView> userImageCol;
    @FXML
    private TableColumn<BorrowBookDetails, Integer> bookIdCol;
    @FXML
    private TableColumn<BorrowBookDetails, String> bookNameCol;
    @FXML
    private TableColumn<BorrowBookDetails, ImageView> bookImageCol;
    @FXML
    private TableColumn<BorrowBookDetails, String> borrowStatusCol;
    @FXML
    private TableView<BorrowBookDetails> tableBorrowBooksDetails;
    @FXML
    private TableColumn<BorrowBookDetails, HBox> ActionsCol;
    @FXML
    private Label approvedBookCountLabel;
    @FXML
    private Label borrowedBookCountLabel;
    @FXML
    private Label pendingBookCountLabel;
    @FXML
    private Label sideBarLogout;
    @FXML
    private Label totalBookCountLabel;
    @FXML
    private AnchorPane StatisticAnchorePane;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // ---------------Set count for all labels in statistics------------------
        totalBookCountLabel.setText(Books.size() + "");
        pendingBookCountLabel.setText(BorrowBookDetailsDatabaseHandler.get("Pending").size() + "");
        approvedBookCountLabel.setText(BorrowBookDetailsDatabaseHandler.get("Approved").size() + "");
        borrowedBookCountLabel.setText(BorrowBookDetailsDatabaseHandler.getBorrowBooksDetailsData().size() + "");

        // Update login user data
        LibrarianStage.setOnShowing(e -> {
            userProfileFullName.setText(userLogin.getFullName());
            imageName = userLogin.getProfileImagePath();
            profileImage[0] = new Image(SceneBuilderFxLibraryManagmentSystem.class.getResourceAsStream(userLogin.getProfileImagePath()));
            userProfileImage.setImage(profileImage[0]);
        });

        // ---------------Set sidebar styles--------------------------
        sideBarHome.getStyleClass().add("sideBar-label");
        sideBarBookManagment.getStyleClass().add("sideBar-label");
        SetSelectedSideBar(sideBarHome, AnchorPaneHome);

        // ----------------Set status combobox data---------------
        statuseCB.getItems().addAll("All", "Pending", "Approved");

        // --------------Set data to table------------------------
        userIdCol.setCellValueFactory(new PropertyValueFactory<>("userId"));
        userNameCol.setCellValueFactory(new PropertyValueFactory<>("userName"));
        userImageCol.setCellValueFactory(cellData -> {
            ImageView userImage = new ImageView(cellData.getValue().getUserImage());
            userImage.setFitWidth(40);
            userImage.setFitHeight(40);
            return new SimpleObjectProperty<>(userImage);
        });
        bookIdCol.setCellValueFactory(new PropertyValueFactory<>("bookId"));
        bookNameCol.setCellValueFactory(new PropertyValueFactory<>("bookTitle"));
        bookImageCol.setCellValueFactory(cellData -> {
            ImageView bookImage = new ImageView(cellData.getValue().getBookImage());
            bookImage.setFitWidth(40);
            bookImage.setFitHeight(40);
            return new SimpleObjectProperty<>(bookImage);
        });
        borrowStatusCol.setCellValueFactory(new PropertyValueFactory<>("status"));

        // ----------------------Set status column as label----------------------
        borrowStatusCol.setCellFactory(column -> new TableCell<BorrowBookDetails, String>() {
            @Override
            protected void updateItem(String status, boolean empty) {
                super.updateItem(status, empty);
                if (empty || status == null) {
                    setGraphic(null);
                    return;
                }
                Label statuslbl = new Label(status);
                statuslbl.setMinWidth(100);
                statuslbl.setAlignment(Pos.CENTER);
                if (status.equals("Pending")) {
                    statuslbl.getStyleClass().add("PendingLabel");
                } else if (status.equals("Approved")) {
                    statuslbl.getStyleClass().add("ApprovedLabel");
                }
                setGraphic(statuslbl);
            }
        });
        ActionsCol.setCellFactory(column -> new TableCell<BorrowBookDetails, HBox>() {
            private int lastIndex = -1;

            @Override
            protected void updateItem(HBox item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || getIndex() >= getTableView().getItems().size() || getIndex() < 0) {
                    setGraphic(null);
                    return;
                }
                if (lastIndex == getIndex()) {
                    return;
                }
                lastIndex = getIndex();
                BorrowBookDetails detailsBorrow = getTableView().getItems().get(getIndex());
                User_Book user_book = new User_Book(detailsBorrow.getUserId(), detailsBorrow.getBookId());
                if ("Pending".equals(detailsBorrow.getStatus())) {
                    Button approveBtn = new Button("Approve");
                    approveBtn.getStyleClass().add("approveBtn");
                    Button rejectBtn = new Button("Reject");
                    rejectBtn.getStyleClass().add("rejectBtn");
                    approveBtn.setOnAction(event -> {
                        executeApproveBtn(detailsBorrow);
                        getTableView().refresh();
                    });
                    rejectBtn.setOnAction(event -> {
                        executeRejectBtn(detailsBorrow, user_book);
                        getTableView().refresh();
                    });
                    HBox hbox = new HBox(approveBtn, rejectBtn);
                    hbox.setSpacing(10);
                    setGraphic(hbox);
                } else if ("Approved".equals(detailsBorrow.getStatus())) {
                    Button approvedBtn = new Button("Approved");
                    approvedBtn.getStyleClass().add("approvedBtn");
                    approvedBtn.setDisable(true);
                    HBox hbox = new HBox(approvedBtn);
                    setGraphic(hbox);
                } else {
                    setGraphic(null);
                }
            }
        });
        tableBorrowBooksDetails.setItems(BorrowedBooksDetails);
    }

    @FXML
    private void ShowSideBarHome(MouseEvent event) {
        SetSelectedSideBar(sideBarHome, AnchorPaneHome);
    }

    @FXML
    private void Logout(MouseEvent event) {
        LibrarianStage.close();
        LoginStage.show();
    }

    @FXML
    private void ShowUserProfileStage(MouseEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/View/UserProfile.fxml"));
        Scene userProfileScene = new Scene(root);
        setStageData(UserProfileStage, userProfileScene, "log.jpg", "User  Profile", 600, 250);
        UserProfileStage.show();
        LibrarianStage.close();
    }

    @FXML
    private void ShowSideBarBookManagment(MouseEvent event) {
        SetSelectedSideBar(sideBarBookManagment, AnchorPaneBookManagment);
    }

    @FXML
    private void ShowSideBarStatistics(MouseEvent event) {
        SetSelectedSideBar(sideBarStatistics, StatisticAnchorePane);
    }

    @FXML
    void showApprovedStatistic(MouseEvent event) throws IOException {
        loadStatistic("Approved");
    }

    @FXML
    void showBorrowedStatistic(MouseEvent event) throws IOException {
        loadStatistic("");
    }

    @FXML
    void showPendingStatistic(MouseEvent event) throws IOException {
        loadStatistic("Pending");
    }

    @FXML
    void showAllBooksInSystem(MouseEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/AllBooksInSystem.fxml"));
        Parent root = loader.load();
        Scene allBooksScene = new Scene(root);
        setStageData(AllBookInSystemStage, allBooksScene, "log.jpg", "Books Stage", 600, 250);
        AllBookInSystemStage.show();
    }

    //------------------------Helper Functions---------------------
    public void executeRejectBtn(BorrowBookDetails detailsBorrow, User_Book user_book) {
        BorrowedBooks.remove(user_book);
        BorrowBookDatabaseHandler.deleteUser_Book(user_book);
        BorrowedBooksDetails.remove(detailsBorrow);
        BorrowBookDetailsDatabaseHandler.deleteBorrowBookDetails(detailsBorrow);
        BorrowedBooksDetails.setAll(BorrowBookDetailsDatabaseHandler.getBorrowBooksDetailsData());
        updateStatisticsLabels();
        tableBorrowBooksDetails.refresh();
    }

    public void executeApproveBtn(BorrowBookDetails detailsBorrow) {
        for (Book book : Books) {
            if (book.getId() == detailsBorrow.getBookId()) {
                if (book.getCopyCount() > 0) {
                    LocalDate deliveryDate = LocalDate.now().plusDays(30);
                    book.setCopyCount(book.getCopyCount() - 1);
                    BookDatabaseHandler.updateBook(book);
                    detailsBorrow.setStatus("Approved");
                    detailsBorrow.setDeliveryDate(deliveryDate);
                    BorrowBookDetailsDatabaseHandler.updateBorrowBookDetails(detailsBorrow, "Approved");
                    BorrowedBooksDetails.setAll(BorrowBookDetailsDatabaseHandler.getBorrowBooksDetailsData());
                    updateStatisticsLabels();
                    tableBorrowBooksDetails.refresh();
                } else {
                    Alert alert = new Alert(Alert.AlertType.ERROR, "There is no copy count enough");
                    alert.showAndWait();
                }
            }
        }
    }

    public void SetSelectedSideBar(Label selectedLabel, AnchorPane selectedAnchorPane) {
        tableBorrowBooksDetails.refresh();
        sideBarHome.getStyleClass().remove("selected");
        sideBarBookManagment.getStyleClass().remove("selected");
        sideBarStatistics.getStyleClass().remove("selected");
        selectedLabel.getStyleClass().add("selected");
        AnchorPaneHome.setVisible(false);
        AnchorPaneBookManagment.setVisible(false);
        StatisticAnchorePane.setVisible(false);
        selectedAnchorPane.setVisible(true);
    }

    public void loadStatistic(String status) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/BookStatistic.fxml"));
        Parent root = loader.load();
        BookStatisticController controller = loader.getController();
        controller.setStatus(status);
        Scene StatisticScene = new Scene(root);
        setStageData(BookStatisticStage, StatisticScene, "log.jpg", "Book Statistic Stage", 600, 250);
        BookStatisticStage.show();
    }

    private void updateStatisticsLabels() {
        totalBookCountLabel.setText(Books.size() + "");
        pendingBookCountLabel.setText(BorrowBookDetailsDatabaseHandler.get("Pending").size() + "");
        approvedBookCountLabel.setText(BorrowBookDetailsDatabaseHandler.get("Approved").size() + "");
        borrowedBookCountLabel.setText(BorrowBookDetailsDatabaseHandler.getBorrowBooksDetailsData().size() + "");
    }
    @FXML
    private void sideBarlogout(MouseEvent event) {
        LibrarianStage.close();
        LoginStage.show();
    }
    @FXML
    private void ApplyStatusFiltter(ActionEvent event) {
        String status = statuseCB.getValue();
        ObservableList<BorrowBookDetails> filteredBorrowBooks = FXCollections.observableArrayList();
        if (status == null || status.equalsIgnoreCase("All")) {
            tableBorrowBooksDetails.setItems(BorrowedBooksDetails);
        } else {
            for (BorrowBookDetails bbd : BorrowedBooksDetails) {
                if (bbd.getStatus().equalsIgnoreCase(status)) {
                    filteredBorrowBooks.add(bbd);
                }
            }
            tableBorrowBooksDetails.setItems(filteredBorrowBooks);
            tableBorrowBooksDetails.refresh();
        }
    }

}
