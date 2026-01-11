/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import DataBase.UserDatabaseHandler;
import Model.User;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;

import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
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
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javax.imageio.ImageIO;
import scenebuilderfxlibrarymanagmentsystem.SceneBuilderFxLibraryManagmentSystem;
import static scenebuilderfxlibrarymanagmentsystem.SceneBuilderFxLibraryManagmentSystem.RegisterStage;
import static scenebuilderfxlibrarymanagmentsystem.SceneBuilderFxLibraryManagmentSystem.Users;
public class RegisterController implements Initializable {

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
    private String imageName = null;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        roleComboBox.getItems().addAll("User", "Libriran", "Admin");
        roleComboBox.setValue("User");
        roleComboBox.setDisable(true);
    }

    @FXML
    private void UploadUserImageProfile(MouseEvent event) {
        Image[] profileImage = {null};
        imageView.setOnMouseClicked(e -> {
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
        });

    }

    @FXML
    private void Register(ActionEvent event) {
        fullNameLabelError.setText("");
        userNameLabelError.setText("");
        passwordLabelError.setText("");
        emailLabelError.setText("");
        phoneLabelError.setText("");
        userPictureLabelError.setText("");
        roleLabelError.setText("");

        boolean hasError = false;
        if (fullNameTextField.getText().isEmpty()) {
            fullNameLabelError.setText("fullName is Required");
            hasError = true;
        }
        if (userNameTextField.getText().isEmpty()) {
            userNameLabelError.setText("userName is Required");
            hasError = true;
        }
        if (passwordTextField.getText().isEmpty()) {
            passwordLabelError.setText("password is Required");
            hasError = true;
        }
        if (emailTextField.getText().isEmpty()) {
            emailLabelError.setText("email is Required");
            hasError = true;
        }
        if (phoneTextField.getText().isEmpty()) {
            phoneLabelError.setText("phone is Required");
            hasError = true;
        }
        if (this.imageName == null) {
            userPictureLabelError.setText("user picture is Required");
            hasError = true;
        }
        if (!hasError) {
            boolean isFound = userExist(userNameTextField.getText(), passwordTextField.getText());
            if (!isFound) {
                User newUser = new User(fullNameTextField.getText(), userNameTextField.getText(), passwordTextField.getText(), emailTextField.getText(), phoneTextField.getText(), roleComboBox.getValue(), this.imageName.toString());
                Users.add(newUser);
                UserDatabaseHandler.addUser(newUser);
                Users.setAll(UserDatabaseHandler.getUsersData());
                Alert alert = new Alert(Alert.AlertType.INFORMATION, "User has been registered...");
                alert.showAndWait();
                fullNameTextField.clear();
                userNameTextField.clear();
                passwordTextField.clear();
                emailTextField.clear();
                phoneTextField.clear();
                this.imageName = null;
                SceneBuilderFxLibraryManagmentSystem.LoginStage.show();
                SceneBuilderFxLibraryManagmentSystem.RegisterStage.close();
            } else {
                userNameLabelError.setText("User  is already exists with this username and password ");
            }
        }
    }

    @FXML
    private void ShowLoginPage(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/View/Login.fxml"));
        Scene loginScene = new Scene(root);
        SceneBuilderFxLibraryManagmentSystem.setStageData(SceneBuilderFxLibraryManagmentSystem.LoginStage, loginScene, "log.jpg", "Register", 600, 250);
        SceneBuilderFxLibraryManagmentSystem.LoginStage.show();
        SceneBuilderFxLibraryManagmentSystem.RegisterStage.hide();
    }
//--------------Helper functions ---------------------

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
}

