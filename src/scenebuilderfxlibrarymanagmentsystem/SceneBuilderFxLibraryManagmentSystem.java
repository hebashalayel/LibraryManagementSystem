/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scenebuilderfxlibrarymanagmentsystem;
import DataBase.BookDatabaseHandler;
import DataBase.BorrowBookDatabaseHandler;
import DataBase.BorrowBookDetailsDatabaseHandler;
import DataBase.CategoryDatabaseHandler;
import DataBase.UserDatabaseHandler;
import Model.Book;
import Model.BorrowBookDetails;
import Model.Category;
import Model.User;
import Model.User_Book;
import java.io.IOException;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class SceneBuilderFxLibraryManagmentSystem extends Application {

    public static User userLogin = null;
    public static Stage LoginStage = new Stage();
    public static Stage RegisterStage = new Stage();
    public static Stage AdminStage = new Stage();
    public static Stage UserStage = new Stage();
    public static Stage LibrarianStage = new Stage();
    public static Stage UserProfileStage = new Stage();
    public static Stage CategoryStage = new Stage();
    public static Stage UserStatisticStage = new Stage();
    public static Stage BookStatisticStage = new Stage();
    public static Stage AllBookInSystemStage = new Stage();
    public static ObservableList<User> Users = FXCollections.observableArrayList();
    public static ObservableList<String> Categories = FXCollections.observableArrayList();
    public static ObservableList<Category> CategoriesDB = FXCollections.observableArrayList();
    public static ObservableList<Book> Books = FXCollections.observableArrayList();
    public static ObservableList<User_Book> BorrowedBooks = FXCollections.observableArrayList();
    public static ObservableList<BorrowBookDetails> BorrowedBooksDetails = FXCollections.observableArrayList();
    public static ObservableList<User> Admins = FXCollections.observableArrayList();
    public static ObservableList<User> Librirans = FXCollections.observableArrayList();
    public static ObservableList<User> UsersList = FXCollections.observableArrayList();
    public static ObservableList<BorrowBookDetails> pendingBooks = FXCollections.observableArrayList();
    public static ObservableList<BorrowBookDetails> approvedBooks = FXCollections.observableArrayList();
    

    @Override
    public void start(Stage primaryStage) throws IOException {
        //-----------------load data------------------------
        Users.setAll(UserDatabaseHandler.getUsersData());
        Books.setAll(BookDatabaseHandler.getBooksData());
        CategoriesDB.setAll(CategoryDatabaseHandler.getCategoriesData());
        BorrowedBooks.setAll(BorrowBookDatabaseHandler.getBorrowBooksData());
        BorrowedBooksDetails.setAll(BorrowBookDetailsDatabaseHandler.getBorrowBooksDetailsData());
        Admins.setAll(UserDatabaseHandler.get("Admin"));
        Librirans.setAll(UserDatabaseHandler.get("Libriran"));
        UsersList.setAll(UserDatabaseHandler.get("User"));
        pendingBooks.setAll(BorrowBookDetailsDatabaseHandler.get("Pending"));
        approvedBooks.setAll(BorrowBookDetailsDatabaseHandler.get("Approved"));
        Parent root = FXMLLoader.load(getClass().getResource("/View/Login.fxml"));
        Scene loginScene = new Scene(root);
        setStageData(LoginStage, loginScene, "log.jpg", "Login", 800, 250);
        LoginStage.show();
    }

    public static void setStageImageIcon(Stage s, String logo) {
        s.getIcons().add(new Image(SceneBuilderFxLibraryManagmentSystem.class.getResourceAsStream("/images/" + logo)));
    }

    public static void setStageData(Stage s, Scene scn, String logo, String title, int sx, int sy) {
        s.setScene(scn);
        s.setTitle(title);
        s.setX(sx);
        s.setY(sy);
        setStageImageIcon(s, logo);
        s.setResizable(false);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
