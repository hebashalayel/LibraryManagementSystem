/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import DataBase.BookDatabaseHandler;
import DataBase.UserDatabaseHandler;
import Model.Book;
import Model.Category;
import Model.User;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javax.imageio.ImageIO;
import scenebuilderfxlibrarymanagmentsystem.SceneBuilderFxLibraryManagmentSystem;
import static scenebuilderfxlibrarymanagmentsystem.SceneBuilderFxLibraryManagmentSystem.AdminStage;
import static scenebuilderfxlibrarymanagmentsystem.SceneBuilderFxLibraryManagmentSystem.Books;
import static scenebuilderfxlibrarymanagmentsystem.SceneBuilderFxLibraryManagmentSystem.CategoriesDB;
import static scenebuilderfxlibrarymanagmentsystem.SceneBuilderFxLibraryManagmentSystem.CategoryStage;
import static scenebuilderfxlibrarymanagmentsystem.SceneBuilderFxLibraryManagmentSystem.LoginStage;
import static scenebuilderfxlibrarymanagmentsystem.SceneBuilderFxLibraryManagmentSystem.RegisterStage;
import static scenebuilderfxlibrarymanagmentsystem.SceneBuilderFxLibraryManagmentSystem.UserProfileStage;
import static scenebuilderfxlibrarymanagmentsystem.SceneBuilderFxLibraryManagmentSystem.UserStatisticStage;
import static scenebuilderfxlibrarymanagmentsystem.SceneBuilderFxLibraryManagmentSystem.Users;
import static scenebuilderfxlibrarymanagmentsystem.SceneBuilderFxLibraryManagmentSystem.setStageData;
import static scenebuilderfxlibrarymanagmentsystem.SceneBuilderFxLibraryManagmentSystem.userLogin;

/**
 * FXML Controller class
 *
 * @author HP
 */
public class AdminDashboardController implements Initializable {

    @FXML
    private Label sideBarHome;
    @FXML
    private Label sideBarUserManagment;
    @FXML
    private Label sideBarBookManagment;
    @FXML
    private AnchorPane AnchorPaneHome;
    @FXML
    private AnchorPane AnchorPaneUserManagment;
    @FXML
    private AnchorPane AnchorPaneBookManagment;
    @FXML
    private ImageView userProfileImage;
    @FXML
    private Label userProfileFullName;
    @FXML
    private ImageView imageView;
    @FXML
    private Label userPictureLabelError;
    @FXML
    private TextField fullNameTextField;
    @FXML
    private Label fullNameLabelError;
    @FXML
    private ComboBox<String> roleComboBox;
    @FXML
    private Label roleLabelError;
    @FXML
    private TextField userNameTextField;
    @FXML
    private Label userNameLabelError;
    @FXML
    private TextField passwordTextField;
    @FXML
    private Label passwordLabelError;
    @FXML
    private TextField emailTextField;
    @FXML
    private Label emailLabelError;
    @FXML
    private TextField phoneTextField;
    @FXML
    private Label phoneLabelError;
    @FXML
    private ComboBox<String> roleCB;
    @FXML
    private TableView<User> tableViewUser;
    @FXML
    private TableColumn<User, String> fullNameCol;
    @FXML
    private TableColumn<User, String> roleCol;
    @FXML
    private TableColumn<User, String> userNameCol;
    @FXML
    private TableColumn<User, String> passwordCol;
    @FXML
    private TableColumn<User, String> emailCol;
    @FXML
    private TableColumn<User, String> phoneCol;
    @FXML
    private TableColumn<User, ImageView> ivCol;

    @FXML
    private Label bookPictureLabelError;
    @FXML
    private ComboBox<String> categoryComboBoxForm;
    @FXML
    private Label categoryLabelError;
    @FXML
    private ComboBox<String> languageComboBox;
    @FXML
    private Label languageLabelError;
    @FXML
    private TextField titleTextField;
    @FXML
    private TextField isbnTextField;
    @FXML
    private Label isbnLabelError;
    @FXML
    private TextField pageCountTextField;
    @FXML
    private Label pageCountLabelError;
    @FXML
    private TextField copyCountTextField;
    @FXML
    private Label copyCountLabelError;
    @FXML
    private TextField publisherTextField;
    @FXML
    private Label publisherLabelError;
    @FXML
    private ComboBox<String> categoryCb;
    @FXML
    private TableView<Book> tableViewBooks;
    @FXML
    private TableColumn<Book, String> titleCol;
    @FXML
    private TableColumn<Book, String> autherCol;
    @FXML
    private TableColumn<Book, String> categoryCol;
    @FXML
    private TableColumn<Book, String> languageCol;
    @FXML
    private TableColumn<Book, Integer> copyCountCol;
    @FXML
    private TableColumn<Book, Integer> pageCountCol;
    @FXML
    private ImageView imageViewBook;
    @FXML
    private TableColumn<Book, ImageView> ivColBook;
    @FXML
    private TableColumn<Book, String> isbnCol;
    String imageNameBook = null;
    Image[] profileImageBook = {null};
    @FXML
    private Label titleLabelError;
    @FXML
    private Label autherLabelError;
    @FXML
    private Label publishDateLabelError;
    @FXML
    private TextField publishDateTextField;
    @FXML
    private TextField autherTextField;
    public String imageName = null;
    Image[] profileImage = {null};
    @FXML
    private Label sideBarLogout;
    @FXML
    private Label sideBarStatistic;

    @FXML
    private AnchorPane StatisticAnchorePane;
    @FXML
    private Label adminsCountLabel;
    @FXML
    private Label libriransCountLabel;
    @FXML
    private Label totalUsersCountLabel;
    @FXML
    private Label usersCountLabel;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //---------------------------set count for each label in statistic-------------------
        adminsCountLabel.setText(UserDatabaseHandler.get("Admin").size() + "");
        libriransCountLabel.setText(UserDatabaseHandler.get("Libriran").size() + "");
        usersCountLabel.setText(UserDatabaseHandler.get("User").size() + "");
        totalUsersCountLabel.setText(UserDatabaseHandler.getUsersData().size() + "");
        //--------------------update sideBar information-----------------------------------------
        AdminStage.setOnShowing(e -> {
            userProfileFullName.setText(userLogin.getFullName());
            imageName = userLogin.getProfileImagePath();
            profileImage[0] = new Image(SceneBuilderFxLibraryManagmentSystem.class.getResourceAsStream(userLogin.getProfileImagePath()));
            userProfileImage.setImage(profileImage[0]);
        });
        //-----------------Formate page count and copy count--------------------------------------
        copyCountTextField.setTextFormatter(new TextFormatter<>(change
                -> change.getControlNewText().matches("^[0-9]*$") ? change : null
        ));
        pageCountTextField.setTextFormatter(new TextFormatter<>(change
                -> change.getControlNewText().matches("^[0-9]*$") ? change : null
        ));
        //-------------------------------side bar-------------------------------------------------
        sideBarHome.getStyleClass().add("sideBar-label");
        sideBarUserManagment.getStyleClass().add("sideBar-label");
        sideBarBookManagment.getStyleClass().add("sideBar-label");
        sideBarStatistic.getStyleClass().add("sideBar-label");
        sideBarLogout.getStyleClass().add("sideBar-label");
        SetSelectedSideBar(sideBarHome, AnchorPaneHome);
        //----------------------------set values to user combobos---------------------------------
        roleComboBox.getItems().addAll("User", "Libriran", "Admin");
        roleCB.getItems().addAll("All", "User", "Libriran", "Admin");
        //----------------------------set values table user cols---------------------------------
        fullNameCol.setCellValueFactory(new PropertyValueFactory<>("fullName"));
        userNameCol.setCellValueFactory(new PropertyValueFactory<>("userName"));
        passwordCol.setCellValueFactory(new PropertyValueFactory<>("password"));
        emailCol.setCellValueFactory(new PropertyValueFactory<>("email"));
        phoneCol.setCellValueFactory(new PropertyValueFactory<>("phone"));
        roleCol.setCellValueFactory(new PropertyValueFactory<>("role"));
        ivCol.setCellValueFactory(cellData -> {
            ImageView userImage = new ImageView(cellData.getValue().getProfileImagePath());
            userImage.setFitWidth(40);
            userImage.setFitHeight(40);
            return new SimpleObjectProperty<>(userImage);
        });
        tableViewUser.setItems(Users);
        //----------------------------set values book combobox---------------------------------   
        categoryCb.getItems().add("All");
        for (Category cat : CategoriesDB) {
            categoryCb.getItems().add(cat.getName());
        }
        for (Category cat : CategoriesDB) {
            categoryComboBoxForm.getItems().add(cat.getName());
        }
        if (languageComboBox.getItems().isEmpty()) {
            languageComboBox.getItems().addAll("Ar", "En");
        }
        //----------------------------set values table book cols---------------------------------
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
        tableViewBooks.setItems(Books);

    }

    @FXML
    private void ShowAddCategoryStage(MouseEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/Category.fxml"));
        Parent root = loader.load();
        CategoryController categoryController = loader.getController();
        categoryController.setCategoryComboBoxes(categoryCb, categoryComboBoxForm);
        Scene CategoryScene = new Scene(root);
        setStageData(CategoryStage, CategoryScene, "log.jpg", "Category Stage", 600, 250);
        CategoryStage.show();
        AdminStage.close();
    }

    @FXML
    private void ShowSideBarHome(MouseEvent event) {
        SetSelectedSideBar(sideBarHome, AnchorPaneHome);
    }

    @FXML
    private void ShowSideBarUserManagment(MouseEvent event) {
        SetSelectedSideBar(sideBarUserManagment, AnchorPaneUserManagment);
    }

    @FXML
    private void ShowSideBarBookManagent(MouseEvent event) {
        SetSelectedSideBar(sideBarBookManagment, AnchorPaneBookManagment);
    }

    public void SetSelectedSideBar(Label selectedLabel, AnchorPane selectedAnchorPane) {
        sideBarHome.getStyleClass().remove("selected");
        sideBarUserManagment.getStyleClass().remove("selected");
        sideBarBookManagment.getStyleClass().remove("selected");
        sideBarStatistic.getStyleClass().remove("selected");
        sideBarLogout.getStyleClass().remove("selected");
        selectedLabel.getStyleClass().add("selected");
        AnchorPaneHome.setVisible(false);
        AnchorPaneUserManagment.setVisible(false);
        AnchorPaneBookManagment.setVisible(false);
        StatisticAnchorePane.setVisible(false);
        selectedAnchorPane.setVisible(true);
    }

    @FXML
    private void Logout(MouseEvent event) {
        AdminStage.close();
        LoginStage.show();
    }

    @FXML
    private void UploadUserImageProfile(MouseEvent event) {
        FileChooser fileChooser = new FileChooser();
        File file = fileChooser.showOpenDialog(RegisterStage);
        if (file != null) {
            profileImage[0] = new Image(file.toURI().toString());
            imageView.setImage(profileImage[0]);
            this.imageName = "/images/" + file.getName();
            try {
                saveImage(profileImage[0], file.getName());
            } catch (IOException ex) {
                Logger.getLogger(SceneBuilderFxLibraryManagmentSystem.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @FXML
    private void AddUser(ActionEvent event) {
        fullNameLabelError.setText("");
        userNameLabelError.setText("");
        passwordLabelError.setText("");
        emailLabelError.setText("");
        phoneLabelError.setText("");
        userPictureLabelError.setText("");
        roleLabelError.setText("");
        boolean hasError = false;

        if (fullNameTextField.getText().isEmpty()) {
            fullNameLabelError.setText("Full Name is Required");
            hasError = true;
        }
        if (userNameTextField.getText().isEmpty()) {
            userNameLabelError.setText("Username is Required");
            hasError = true;
        }
        if (passwordTextField.getText().isEmpty()) {
            passwordLabelError.setText("Password is Required");
            hasError = true;
        }
        if (emailTextField.getText().isEmpty()) {
            emailLabelError.setText("Email is Required");
            hasError = true;
        }
        if (phoneTextField.getText().isEmpty()) {
            phoneLabelError.setText("Phone is Required");
            hasError = true;
        }
        if (this.imageName == null) {
            userPictureLabelError.setText("User picture is Required");
            hasError = true;
        }
        if (this.roleComboBox.getValue() == null) {
            roleLabelError.setText("Role is Required");
            hasError = true;
        }
        if (!hasError) {
            boolean isFound = userExist(userNameTextField.getText(), passwordTextField.getText());
            if (!isFound) {
                User newUser = new User(
                        fullNameTextField.getText(),
                        userNameTextField.getText(),
                        passwordTextField.getText(),
                        emailTextField.getText(),
                        phoneTextField.getText(),
                        roleComboBox.getValue(),
                        this.imageName
                );
                Users.add(newUser);
                UserDatabaseHandler.addUser(newUser);
                refreshStatisticsLabels();
                Alert alert = new Alert(Alert.AlertType.INFORMATION, "User has been registered...");
                alert.showAndWait();
                fullNameTextField.clear();
                userNameTextField.clear();
                passwordTextField.clear();
                emailTextField.clear();
                phoneTextField.clear();
                roleComboBox.setValue("");
                this.imageName = null;
                imageView.setImage(new Image(SceneBuilderFxLibraryManagmentSystem.class.getResourceAsStream("/images/login3.jpg")));
            } else {
                userNameLabelError.setText("User already exists with this username and password");
            }
        }
        tableViewUser.refresh();
    }

    @FXML
    private void DeleteUser(ActionEvent event) {
        User selectedUser = tableViewUser.getSelectionModel().getSelectedItem();
        if (selectedUser != null) {
            for (User user : Users) {
                if (user.getUserName().equals(selectedUser.getUserName())) {
                    Users.remove(user);
                    UserDatabaseHandler.deleteUser(user);
                    refreshStatisticsLabels();
                    fullNameTextField.clear();
                    userNameTextField.clear();
                    passwordTextField.clear();
                    emailTextField.clear();
                    phoneTextField.clear();
                    this.imageName = null;
                    if (selectedUser.equals(userLogin)) {
                        AdminStage.close();
                        LoginStage.show();
                    }
                    break;
                }
            }
            applyRoleFilter(roleCB.getValue());
            tableViewUser.refresh();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Please Select user to delete. ");
            alert.showAndWait();
        }
    }

    @FXML
    private void UpdateUser(ActionEvent event) {
        User selectedUser = tableViewUser.getSelectionModel().getSelectedItem();
        if (selectedUser != null) {
            for (User user : Users) {
                if (user.getUserName().equals(selectedUser.getUserName())) {
                    selectedUser = user;
                    break;
                }
            }
            boolean sameUserFound = false;
            for (User user : Users) {
                if (selectedUser.getUserName().equals(userNameTextField.getText())) {
                    continue;
                }
                if (user.getUserName().equals(userNameTextField.getText())) {
                    sameUserFound = true;
                    break;
                }
            }
            if (selectedUser != null) {
                fullNameLabelError.setText("");
                userNameLabelError.setText("");
                passwordLabelError.setText("");
                emailLabelError.setText("");
                phoneLabelError.setText("");
                userPictureLabelError.setText("");
                roleLabelError.setText("");
                boolean hasError = false;
                if (fullNameTextField.getText().isEmpty()) {
                    fullNameLabelError.setText("Full Name is Required");
                    hasError = true;
                }
                if (userNameTextField.getText().isEmpty()) {
                    userNameLabelError.setText("Username is Required");
                    hasError = true;
                }
                if (passwordTextField.getText().isEmpty()) {
                    passwordLabelError.setText("Password is Required");
                    hasError = true;
                }
                if (emailTextField.getText().isEmpty()) {
                    emailLabelError.setText("Email is Required");
                    hasError = true;
                }
                if (phoneTextField.getText().isEmpty()) {
                    phoneLabelError.setText("Phone is Required");
                    hasError = true;
                }
                if (this.imageName == null) {
                    userPictureLabelError.setText("User picture is Required");
                    hasError = true;
                }
                if (!hasError) {
                    if (!sameUserFound) {
                        selectedUser.setFullName(fullNameTextField.getText());
                        selectedUser.setUserName(userNameTextField.getText());
                        selectedUser.setPassword(passwordTextField.getText());
                        selectedUser.setEmail(emailTextField.getText());
                        selectedUser.setPhone(phoneTextField.getText());
                        selectedUser.setRole(roleComboBox.getValue());
                        // Check if imageName is null and retain the existing path if it is
                        if (imageName != null) {
                            selectedUser.setProfileImagePath(imageName);
                        }
                        applyRoleFilter(roleCB.getValue());
                        UserDatabaseHandler.updateUser(selectedUser);
                        refreshStatisticsLabels();
                        tableViewUser.refresh();
                        Alert alert = new Alert(Alert.AlertType.INFORMATION, "User Updated.");
                        alert.showAndWait();
                    } else {
                        Alert alert = new Alert(Alert.AlertType.ERROR, "This userName is used by another user");
                        alert.showAndWait();
                    }
                    if (userLogin.equals(selectedUser)) {
                        userProfileFullName.setText(userLogin.getFullName());
                        profileImage[0] = new Image(SceneBuilderFxLibraryManagmentSystem.class.getResourceAsStream(userLogin.getProfileImagePath()));
                        userProfileImage.setImage(profileImage[0]);
                    }
                }
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Please Select user to update ");
            alert.showAndWait();
        }
    }

    @FXML
    private void CancelUser(ActionEvent event) {
        fullNameLabelError.setText("");
        userNameLabelError.setText("");
        passwordLabelError.setText("");
        emailLabelError.setText("");
        phoneLabelError.setText("");
        userPictureLabelError.setText("");
        roleLabelError.setText("");
        fullNameTextField.clear();
        userNameTextField.clear();
        passwordTextField.clear();
        emailTextField.clear();
        phoneTextField.clear();
        roleComboBox.setValue("");
        this.imageName = "/images/login3.jpg";
        profileImage[0] = new Image(SceneBuilderFxLibraryManagmentSystem.class.getResourceAsStream(this.imageName));
        imageView.setImage(profileImage[0]);
    }

    @FXML
    private void SetSelectedUserToForm(MouseEvent event) {
        fullNameLabelError.setText("");
        userNameLabelError.setText("");
        passwordLabelError.setText("");
        emailLabelError.setText("");
        phoneLabelError.setText("");
        userPictureLabelError.setText("");
        roleLabelError.setText("");
        User user = tableViewUser.getSelectionModel().getSelectedItem();
        if (user != null) {
            fullNameTextField.setText(user.getFullName());
            passwordTextField.setText(user.getPassword());
            userNameTextField.setText(user.getUserName());
            emailTextField.setText(user.getEmail());
            phoneTextField.setText(user.getPhone());
            roleComboBox.setValue(user.getRole());
            imageName = user.getProfileImagePath();
            profileImage[0] = new Image(SceneBuilderFxLibraryManagmentSystem.class.getResourceAsStream(user.getProfileImagePath()));
            imageView.setImage(profileImage[0]);
        }
    }

    @FXML
    private void ApplyUserFiltter(ActionEvent event) {
        String role = roleCB.getValue();
        ObservableList<User> filteredUsers = FXCollections.observableArrayList();
        if (role == null || role.equalsIgnoreCase("All")) {
            tableViewUser.setItems(Users);
        } else {
            for (User user : Users) {
                if (user.getRole().equalsIgnoreCase(role)) {
                    filteredUsers.add(user);
                }
            }
            tableViewUser.setItems(filteredUsers);
            tableViewUser.refresh();
        }
    }

    @FXML
    private void UploadBookImage(MouseEvent event) {
        FileChooser fileChooser = new FileChooser();
        File file = fileChooser.showOpenDialog(AdminStage);
        if (file != null) {
            profileImageBook[0] = new Image(file.toURI().toString());
            imageViewBook.setImage(profileImageBook[0]);
            this.imageNameBook = "/images/" + file.getName();
            try {
                saveImage(profileImageBook[0], file.getName());
            } catch (IOException ex) {
                Logger.getLogger(SceneBuilderFxLibraryManagmentSystem.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @FXML
    private void AddBook(ActionEvent event) {
        titleLabelError.setText("");
        autherLabelError.setText("");
        categoryLabelError.setText("");
        isbnLabelError.setText("");
        publishDateLabelError.setText("");
        languageLabelError.setText("");
        pageCountLabelError.setText("");
        copyCountLabelError.setText("");
        publisherLabelError.setText("");
        bookPictureLabelError.setText("");
        boolean hasError = false;
        if (this.imageNameBook == null) {
            bookPictureLabelError.setText("Book Picture is Required");
            hasError = true;
        }
        if (titleTextField.getText().isEmpty()) {
            titleLabelError.setText("Title is Required");
            hasError = true;
        }

        if (autherTextField.getText().isEmpty()) {
            autherLabelError.setText("Author is Required");
            hasError = true;
        }

        if (categoryComboBoxForm.getValue() == null || categoryComboBoxForm.getValue().isEmpty()) {
            categoryLabelError.setText("Category is Required");
            hasError = true;
        }

        if (isbnTextField.getText().isEmpty()) {
            isbnLabelError.setText("ISBN is Required");
            hasError = true;
        }

        if (publishDateTextField.getText().isEmpty()) {
            publishDateLabelError.setText("Publish Date is Required");
            hasError = true;
        }

        if (languageComboBox.getValue() == null || languageComboBox.getValue().isEmpty()) {
            languageLabelError.setText("Language is Required");
            hasError = true;
        }

        if (pageCountTextField.getText().isEmpty()) {
            pageCountLabelError.setText("Page Count is Required");
            hasError = true;
        } else {
            try {
                Integer.parseInt(pageCountTextField.getText());
            } catch (NumberFormatException ex) {
                pageCountLabelError.setText("Invalid page count. Please enter a valid number.");
                hasError = true;
            }
        }

        if (copyCountTextField.getText().isEmpty()) {
            copyCountLabelError.setText("Copy Count is Required");
            hasError = true;
        } else {
            try {
                Integer.parseInt(copyCountTextField.getText());
            } catch (NumberFormatException ex) {
                copyCountLabelError.setText("Invalid copy count. Please enter a valid number.");
                hasError = true;
            }
        }

        if (publisherTextField.getText().isEmpty()) {
            publisherLabelError.setText("Publisher is Required");
            hasError = true;
        }

        for (Book book : Books) {
            if (book.getIsbn().equalsIgnoreCase(isbnTextField.getText())) {
                isbnLabelError.setText("A book with this isbn already exists");
                hasError = true;
                break;
            }
        }
        if (!hasError) {
            Book newBook = new Book(
                    titleTextField.getText(),
                    autherTextField.getText(),
                    categoryComboBoxForm.getValue(),
                    isbnTextField.getText(),
                    publishDateTextField.getText(),
                    languageComboBox.getValue(),
                    this.imageNameBook,
                    Integer.parseInt(pageCountTextField.getText()),
                    Integer.parseInt(copyCountTextField.getText()),
                    publisherTextField.getText()
            );
            Books.add(newBook);
            BookDatabaseHandler.addBook(newBook);
            Books.setAll(BookDatabaseHandler.getBooksData());
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Book has been registered...");
            alert.showAndWait();
            titleTextField.clear();
            autherTextField.clear();
            isbnTextField.clear();
            publishDateTextField.clear();
            pageCountTextField.clear();
            copyCountTextField.clear();
            publisherTextField.clear();
            languageComboBox.setValue("");
            categoryComboBoxForm.setValue("");
            this.imageNameBook = null;
            imageViewBook.setImage((new Image(SceneBuilderFxLibraryManagmentSystem.class.getResourceAsStream("/images/bk1.jpg"))));
            tableViewBooks.setItems(Books);
        } else {
            
        }
    }

    @FXML
    private void DeleteBook(ActionEvent event) {
        Book selectedBook = tableViewBooks.getSelectionModel().getSelectedItem();
        if (selectedBook != null) {
            Books.remove(selectedBook);
            BookDatabaseHandler.deleteBook(selectedBook);
            titleTextField.clear();
            autherTextField.clear();
            isbnTextField.clear();
            publishDateTextField.clear();
            pageCountTextField.clear();
            copyCountTextField.clear();
            publisherTextField.clear();
            this.imageNameBook = null;
            applyCategoryFilter(categoryCb.getValue());
            tableViewBooks.refresh();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Please Select book to delete. ");
            alert.showAndWait();
        }
    }
    @FXML
    private void UpdateBook(ActionEvent event) {
        Book selectedBook = tableViewBooks.getSelectionModel().getSelectedItem();
        if (selectedBook != null) {
            for (Book book : Books) {
                if (book.getIsbn().equals(selectedBook.getIsbn())) {
                    selectedBook = book;
                    break;
                }
            }
            boolean sameBookFound = false;
            for (Book book : Books) {
                if (selectedBook.getIsbn().equals(isbnTextField.getText())) {
                    continue;
                }
                if (book.getIsbn().equals(isbnTextField.getText())) {
                    sameBookFound = true;
                    break;
                }
            }
            if (selectedBook != null) {
                titleLabelError.setText("");
                autherLabelError.setText("");
                categoryLabelError.setText("");
                isbnLabelError.setText("");
                publishDateLabelError.setText("");
                languageLabelError.setText("");
                pageCountLabelError.setText("");
                copyCountLabelError.setText("");
                publisherLabelError.setText("");
                bookPictureLabelError.setText("");
                boolean hasError = false;

                if (titleTextField.getText().isEmpty()) {
                    titleLabelError.setText("Title is Required");
                    hasError = true;
                }

                if (autherTextField.getText().isEmpty()) {
                    autherLabelError.setText("Author is Required");
                    hasError = true;
                }

                if (categoryComboBoxForm.getValue() == null || categoryComboBoxForm.getValue().isEmpty()) {
                    categoryLabelError.setText("Category is Required");
                    hasError = true;
                }

                if (isbnTextField.getText().isEmpty()) {
                    isbnLabelError.setText("ISBN is Required");
                    hasError = true;
                }

                if (publishDateTextField.getText().isEmpty()) {
                    publishDateLabelError.setText("Publish Date is Required");
                    hasError = true;
                }

                if (languageComboBox.getValue() == null || languageComboBox.getValue().isEmpty()) {
                    languageLabelError.setText("Language is Required");
                    hasError = true;
                }

                if (pageCountTextField.getText().isEmpty()) {
                    pageCountLabelError.setText("Page Count is Required");
                    hasError = true;
                } else {
                    try {
                        Integer.parseInt(pageCountTextField.getText());
                    } catch (NumberFormatException ex) {
                        pageCountLabelError.setText("Invalid page count. Please enter a valid number.");
                        hasError = true;
                    }
                }

                if (copyCountTextField.getText().isEmpty()) {
                    copyCountLabelError.setText("Copy Count is Required");
                    hasError = true;
                } else {
                    try {
                        Integer.parseInt(copyCountTextField.getText());
                    } catch (NumberFormatException ex) {
                        copyCountLabelError.setText("Invalid copy count. Please enter a valid number.");
                        hasError = true;
                    }
                }
                if (publisherTextField.getText().isEmpty()) {
                    publisherLabelError.setText("Publisher is Required");
                    hasError = true;
                }
                if (!hasError) {
                    if (!sameBookFound) {
                        selectedBook.setTitle(titleTextField.getText());
                        selectedBook.setAuthor(autherTextField.getText());
                        selectedBook.setCategory(categoryComboBoxForm.getValue());
                        selectedBook.setIsbn(isbnTextField.getText());
                        selectedBook.setPublishDate(publishDateTextField.getText());
                        selectedBook.setLanguage(languageComboBox.getValue());
                        selectedBook.setPageCount(Integer.parseInt(pageCountTextField.getText()));
                        selectedBook.setCopyCount(Integer.parseInt(copyCountTextField.getText()));
                        selectedBook.setPublisher(publisherTextField.getText());
                        if (imageNameBook != null) {
                            selectedBook.setBookImagePath(imageNameBook);
                        }
                        applyCategoryFilter(categoryCb.getValue());
                        BookDatabaseHandler.updateBook(selectedBook);
                        tableViewBooks.refresh();
                        Alert alert = new Alert(Alert.AlertType.INFORMATION, "Book Updated.");
                        alert.showAndWait();
                    } else {
                        Alert alert = new Alert(Alert.AlertType.ERROR, "This Isbn to another Book");
                        alert.showAndWait();
                    }
                }
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Please Select Book to update ");
            alert.showAndWait();
        }
    }

    @FXML
    private void CancelBook(ActionEvent event) {
        titleLabelError.setText("");
        autherLabelError.setText("");
        categoryLabelError.setText("");
        isbnLabelError.setText("");
        publishDateLabelError.setText("");
        languageLabelError.setText("");
        pageCountLabelError.setText("");
        copyCountLabelError.setText("");
        publisherLabelError.setText("");
        bookPictureLabelError.setText("");
        titleTextField.clear();
        autherTextField.clear();
        isbnTextField.clear();
        publishDateTextField.clear();
        pageCountTextField.clear();
        copyCountTextField.clear();
        publisherTextField.clear();
        languageComboBox.setValue("");
        categoryComboBoxForm.setValue("");
        this.imageNameBook = "/images/bk1.jpg";
        profileImageBook[0] = new Image(SceneBuilderFxLibraryManagmentSystem.class.getResourceAsStream(this.imageNameBook));
        imageViewBook.setImage(profileImageBook[0]);
    }

    @FXML
    private void ApplyCategoryFiltter(ActionEvent event) {
        String category = categoryCb.getValue();
        ObservableList<Book> filteredBooks = FXCollections.observableArrayList();
        if (category == null || category.equalsIgnoreCase("All")) {
            tableViewBooks.setItems(Books);
        } else {
            for (Book book : Books) {
                if (book.getCategory().equalsIgnoreCase(category)) {
                    filteredBooks.add(book);
                }
            }
            tableViewBooks.setItems(filteredBooks);
            tableViewBooks.refresh();
        }
    }

    @FXML
    private void SetSelectedBookToForm(MouseEvent event) {
        titleLabelError.setText("");
        autherLabelError.setText("");
        categoryLabelError.setText("");
        isbnLabelError.setText("");
        publishDateLabelError.setText("");
        languageLabelError.setText("");
        pageCountLabelError.setText("");
        copyCountLabelError.setText("");
        publisherLabelError.setText("");
        bookPictureLabelError.setText("");
        Book book = tableViewBooks.getSelectionModel().getSelectedItem();
        if (book != null) {
            titleTextField.setText(book.getTitle());
            autherTextField.setText(book.getAuthor());
            categoryComboBoxForm.setValue(book.getCategory());
            isbnTextField.setText(book.getIsbn());
            publishDateTextField.setText(book.getPublishDate());
            languageComboBox.setValue(book.getLanguage());
            pageCountTextField.setText(book.getPageCount() + "");
            copyCountTextField.setText(book.getCopyCount() + "");
            publisherTextField.setText(book.getPublisher());
            profileImageBook[0] = new Image(SceneBuilderFxLibraryManagmentSystem.class.getResourceAsStream(book.getBookImagePath()));
            imageViewBook.setImage(profileImageBook[0]);
        }
    }

    @FXML
    private void ShowUserProfileStage(MouseEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/View/UserProfile.fxml"));
        Scene userProfileScene = new Scene(root);
        setStageData(UserProfileStage, userProfileScene, "log.jpg", "User Profile", 600, 250);
        UserProfileStage.show();
        AdminStage.close();
    }

    @FXML
    void ShowSideBarStatistic(MouseEvent event) {
        SetSelectedSideBar(sideBarStatistic, StatisticAnchorePane);
    }

    @FXML
    void logoutSideBar(MouseEvent event) {
        AdminStage.close();
        LoginStage.show();
    }

    @FXML
    void showAdminsStatistic(MouseEvent event) throws IOException {
        loadStatistic("Admin");
    }

    @FXML
    void showAllUserStatistic(MouseEvent event) throws IOException {
        loadStatistic("");
    }

    @FXML
    void showLibriranStatistic(MouseEvent event) throws IOException {
        loadStatistic("Libriran");
    }

    @FXML
    void showUsersStatistic(MouseEvent event) throws IOException {
        loadStatistic("User");
    }
    
    //----------helper functions------------
    public boolean userExist(String userName, String password) {
        boolean isFount = false;
        for (User user : Users) {
            if (user.getUserName().equals(userName) && user.getPassword().equals(password)) {
                isFount = true;
            }
        }
        return isFount;
    }

    public void saveImage(Image image, String name) throws IOException {
        String projectPath = System.getProperty("user.dir");
        String imageFolderPath = projectPath + "/src/images";
        File imageFolder = new File(imageFolderPath);
        if (!imageFolder.exists()) {
            imageFolder.mkdir();
        }
        String fullFilePath = imageFolderPath + "/" + name;
        File file = new File(fullFilePath);
        BufferedImage bi = SwingFXUtils.fromFXImage(image, null);
        ImageIO.write(bi, "jpg", file);
    }

    public void applyRoleFilter(String role) {
        ObservableList<User> filteredUsers = FXCollections.observableArrayList();
        if (role == null || role.equalsIgnoreCase("All")) {
            tableViewUser.setItems(Users);
        } else {
            for (User user : Users) {
                if (user.getRole().equalsIgnoreCase(role)) {
                    filteredUsers.add(user);
                }
            }
            tableViewUser.setItems(filteredUsers);
            tableViewUser.refresh();
        }
    }

    public void applyCategoryFilter(String category) {
        ObservableList<Book> filteredBooks = FXCollections.observableArrayList();
        if (category == null || category.equalsIgnoreCase("All")) {
            tableViewBooks.setItems(Books);
        } else {
            for (Book book : Books) {
                if (book.getCategory().equalsIgnoreCase(category)) {
                    filteredBooks.add(book);
                }
            }
            tableViewBooks.setItems(filteredBooks);
            tableViewBooks.refresh();
        }
    }

    public void loadStatistic(String role) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/UserStatisitic.fxml"));
        Parent root = loader.load();
        UserStatisticController controller = loader.getController();
        controller.setRole(role);
        Scene StatisticScene = new Scene(root);
        setStageData(UserStatisticStage, StatisticScene, "log.jpg", "User Statistic Stage", 600, 250);
        UserStatisticStage.show();
    }
private void refreshStatisticsLabels() {
    adminsCountLabel.setText(UserDatabaseHandler.get("Admin").size() + "");
    libriransCountLabel.setText(UserDatabaseHandler.get("Libriran").size() + "");
    usersCountLabel.setText(UserDatabaseHandler.get("User ").size() + "");
    totalUsersCountLabel.setText(UserDatabaseHandler.getUsersData().size() + "");
}
}
