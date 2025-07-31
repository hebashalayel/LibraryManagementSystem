/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Model.User;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import scenebuilderfxlibrarymanagmentsystem.SceneBuilderFxLibraryManagmentSystem;
import static scenebuilderfxlibrarymanagmentsystem.SceneBuilderFxLibraryManagmentSystem.AdminStage;

import static scenebuilderfxlibrarymanagmentsystem.SceneBuilderFxLibraryManagmentSystem.LibrarianStage;
import static scenebuilderfxlibrarymanagmentsystem.SceneBuilderFxLibraryManagmentSystem.LoginStage;
import static scenebuilderfxlibrarymanagmentsystem.SceneBuilderFxLibraryManagmentSystem.RegisterStage;
import static scenebuilderfxlibrarymanagmentsystem.SceneBuilderFxLibraryManagmentSystem.UserStage;

import static scenebuilderfxlibrarymanagmentsystem.SceneBuilderFxLibraryManagmentSystem.setStageData;

/**
 * FXML Controller class
 *
 * @author HP
 */
public class LoginController implements Initializable {

    @FXML
    private ImageView loginImageView;

    @FXML
    private Label passwordLabelError;

    @FXML
    private TextField passwordTextField;

    @FXML
    private Label userNameLabelError;

    @FXML
    private TextField userNameTextField;

    @FXML
    void LoginUser(ActionEvent event) throws IOException {
        userNameLabelError.setText("");
        passwordLabelError.setText("");
        String userName = userNameTextField.getText();
        String password = passwordTextField.getText();
        User user = validateUser(userName, password);
        if (user == null) {
            if (userName.isEmpty()) {
                userNameLabelError.setText("UserName is required!");
            } else if (password.isEmpty()) {
                passwordLabelError.setText("Password is required!");
            } else {
                userNameLabelError.setText("Invalid userName or password!");
            }
        }else{            
            SceneBuilderFxLibraryManagmentSystem.userLogin = user;
            System.out.println("From Controller "+SceneBuilderFxLibraryManagmentSystem.userLogin.getId());
            SceneBuilderFxLibraryManagmentSystem.userLogin.setId(user.getId());
            if (user.getRole().equals("Admin")) {
                System.out.println("Welcom Admin");
                Parent AdminStageRoot = FXMLLoader.load(getClass().getResource("/View/AdminDashboard.fxml"));
                Scene AdminScene = new Scene(AdminStageRoot);
                setStageData(AdminStage, AdminScene, "log.jpg", "AdminDashboard", 50, 50);
                LoginStage.hide();
                AdminStage.show();
            } else if (user.getRole().equals("Libriran")) {
                Parent LibrarianStageRoot = FXMLLoader.load(getClass().getResource("/View/LibrarianDashboard.fxml"));
                Scene LibrarianScene = new Scene(LibrarianStageRoot);
                setStageData(LibrarianStage, LibrarianScene, "log.jpg", "LibriranDashboard", 150, 50);
                LoginStage.hide();
                LibrarianStage.show();
            }else {
                Parent UserStageRoot = FXMLLoader.load(getClass().getResource("/View/UserDashboard.fxml"));
                Scene UserScene = new Scene(UserStageRoot);
                setStageData(UserStage, UserScene, "log.jpg", "UserDashboard", 150, 50);
                LoginStage.hide();
                UserStage.show();
            }
        }
    }

    @FXML
    void ShowRegisterPage(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/View/Register.fxml"));
        Scene registerScene = new Scene(root);
        setStageData(RegisterStage, registerScene, "log.jpg", "Register", 700, 250);
        RegisterStage.show();
        LoginStage.close();
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
       
    }
    //------Helper functions--------------
    public User validateUser(String userName, String password) {
        for (User user : SceneBuilderFxLibraryManagmentSystem.Users) {
            if (user.getUserName().equals(userName) && user.getPassword().equals(password)) {
                return user;
            }
        }
        return null;
    }

}
