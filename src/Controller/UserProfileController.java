/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

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
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javax.imageio.ImageIO;
import scenebuilderfxlibrarymanagmentsystem.SceneBuilderFxLibraryManagmentSystem;
import static scenebuilderfxlibrarymanagmentsystem.SceneBuilderFxLibraryManagmentSystem.AdminStage;
import static scenebuilderfxlibrarymanagmentsystem.SceneBuilderFxLibraryManagmentSystem.LibrarianStage;
import static scenebuilderfxlibrarymanagmentsystem.SceneBuilderFxLibraryManagmentSystem.RegisterStage;
import static scenebuilderfxlibrarymanagmentsystem.SceneBuilderFxLibraryManagmentSystem.UserProfileStage;
import static scenebuilderfxlibrarymanagmentsystem.SceneBuilderFxLibraryManagmentSystem.UserStage;
import static scenebuilderfxlibrarymanagmentsystem.SceneBuilderFxLibraryManagmentSystem.Users;
import static scenebuilderfxlibrarymanagmentsystem.SceneBuilderFxLibraryManagmentSystem.userLogin;

public class UserProfileController implements Initializable {

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
    private Image[] profileImageUser = {null};
    @FXML
    private AnchorPane AnchorPaneColor;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        roleComboBox.getItems().addAll("User", "Libriran", "Admin");
        roleComboBox.setDisable(true);
        if (userLogin != null) {
            fullNameTextField.setText(userLogin.getFullName());
            passwordTextField.setText(userLogin.getPassword());
            userNameTextField.setText(userLogin.getUserName());
            emailTextField.setText(userLogin.getEmail());
            phoneTextField.setText(userLogin.getPhone());
            roleComboBox.setValue(userLogin.getRole());
            imageName = userLogin.getProfileImagePath();
            profileImageUser[0] = new Image(SceneBuilderFxLibraryManagmentSystem.class.getResourceAsStream(userLogin.getProfileImagePath()));
            imageView.setImage(profileImageUser[0]);
        }
        if (userLogin.getRole().equals("Admin")) {
            AnchorPaneColor.getStyleClass().add("AnchorPaneColorAdmin");
        } else if (userLogin.getRole().equals("User")) {
            AnchorPaneColor.getStyleClass().add("AnchorPaneColorUser");
        } else {
            AnchorPaneColor.getStyleClass().add("AnchorPaneColorLibrirean");
        }

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
    private void Update(ActionEvent event) {
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
        if (!hasError) {
            boolean sameUserFound = false;
            for (User user : Users) {
                if (userLogin.getUserName().equals(user.getUserName())) {
                    continue;
                }
                if (user.getUserName().equals(userNameTextField.getText())) {
                    sameUserFound = true;
                    break;
                }
            }
            if (userLogin != null) {
                if (!sameUserFound) {
                    userLogin.setFullName(fullNameTextField.getText());
                    userLogin.setUserName(userNameTextField.getText());
                    userLogin.setPassword(passwordTextField.getText());
                    userLogin.setEmail(emailTextField.getText());
                    userLogin.setPhone(phoneTextField.getText());
                    userLogin.setRole(roleComboBox.getValue());
                    userLogin.setProfileImagePath(imageName);
                    if (this.imageName != null) {
                        userLogin.setProfileImagePath(imageName);
                    }
                    Users.set(Users.indexOf(userLogin), userLogin);
                    Alert alert = new Alert(Alert.AlertType.INFORMATION, "User has been updated");
                    alert.showAndWait();
                    UserProfileStage.close();
                    if (userLogin.getRole().equals("Admin")) {
                        AdminStage.show();
                    } else if (userLogin.getRole().equals("User")) {
                        UserStage.show();
                    } else {
                        LibrarianStage.show();
                    }
                } else {
                    Alert alert = new Alert(Alert.AlertType.ERROR, "UserName already exist");
                    alert.showAndWait();
                }
            }
        }
    }

    @FXML
    private void ShowDashboard(ActionEvent event) {
        UserProfileStage.close();
        if (userLogin.getRole().equals("Admin")) {
            AdminStage.show();
        } else if (userLogin.getRole().equals("User")) {
            UserStage.show();
        } else {
            LibrarianStage.show();
        }
        
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
