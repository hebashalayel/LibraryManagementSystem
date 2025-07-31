/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import DataBase.UserDatabaseHandler;
import Model.User;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.event.ActionEvent;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.scene.control.cell.PropertyValueFactory;
import static scenebuilderfxlibrarymanagmentsystem.SceneBuilderFxLibraryManagmentSystem.Admins;
import static scenebuilderfxlibrarymanagmentsystem.SceneBuilderFxLibraryManagmentSystem.Librirans;
import static scenebuilderfxlibrarymanagmentsystem.SceneBuilderFxLibraryManagmentSystem.UserStatisticStage;
import static scenebuilderfxlibrarymanagmentsystem.SceneBuilderFxLibraryManagmentSystem.Users;
import static scenebuilderfxlibrarymanagmentsystem.SceneBuilderFxLibraryManagmentSystem.UsersList;

public class UserStatisticController implements Initializable {

    String role = "";
    @FXML
    private Button clearBtn;
    @FXML
    private TableColumn<User, String> emailcol;
    @FXML
    private TableColumn<User, String> fullnamecol;
    @FXML
    private TableColumn<User, String> passwordcol;
    @FXML
    private TableColumn<User, String> phonecol;
    @FXML
    private TableColumn<User, String> rolecol;

    @FXML
    private TextField searchtxtfield;
    @FXML
    private TableColumn<User, ImageView> userimagecol;
    @FXML
    private TableColumn<User, String> usernamecol;

    @FXML
    private TableView<User> usersTabel;

    private ObservableList<User> originalUserList = FXCollections.observableArrayList();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //-----------------------set data to cols-------------------------------
        fullnamecol.setCellValueFactory(new PropertyValueFactory<>("fullName"));
        usernamecol.setCellValueFactory(new PropertyValueFactory<>("userName"));
        passwordcol.setCellValueFactory(new PropertyValueFactory<>("password"));
        emailcol.setCellValueFactory(new PropertyValueFactory<>("email"));
        phonecol.setCellValueFactory(new PropertyValueFactory<>("phone"));
        rolecol.setCellValueFactory(new PropertyValueFactory<>("role"));
        userimagecol.setCellValueFactory(cellData -> {
            ImageView userImage = new ImageView(cellData.getValue().getProfileImagePath());
            userImage.setFitWidth(40);
            userImage.setFitHeight(40);
            return new SimpleObjectProperty<>(userImage);
        });
        // -------------------apply filter-----------------
        searchtxtfield.textProperty().addListener((observable, oldValue, newValue) -> {
            applyFilter(newValue);
        });
    }

    public void setRole(String role) {
        this.role = role;
        if ("Admin".equals(role)) {
            Admins.setAll(UserDatabaseHandler.get("Admin"));
            usersTabel.setItems(Admins);
        } else if ("Libriran".equals(role)) {
            Librirans.setAll(UserDatabaseHandler.get("Libriran"));
            usersTabel.setItems(Librirans);
        } else if ("User".equals(role)) {
            UsersList.setAll(UserDatabaseHandler.get("User"));
            usersTabel.setItems(UsersList);
        } else {
            usersTabel.setItems(Users);
        }
        originalUserList.setAll(usersTabel.getItems());
    }

    private void applyFilter(String filter) {
        if (filter == null || filter.isEmpty()) {
            usersTabel.setItems(originalUserList);
            return;
        }
        ObservableList<User> filteredList = FXCollections.observableArrayList();
        String lowerCaseFilter = filter.toLowerCase();
        for (User user : originalUserList) {
            if (user.getFullName().toLowerCase().contains(lowerCaseFilter)
                    || user.getUserName().toLowerCase().contains(lowerCaseFilter)
                    || user.getEmail().toLowerCase().contains(lowerCaseFilter)
                    || user.getPhone().toLowerCase().contains(lowerCaseFilter)
                    || user.getRole().toLowerCase().contains(lowerCaseFilter)) {
                filteredList.add(user);
            }
        }
        usersTabel.setItems(filteredList);
    }

    @FXML
    void Clear(ActionEvent event) {
        searchtxtfield.setText("");
    }

    @FXML
    void logout(MouseEvent event) {
        UserStatisticStage.close();
    }
}
