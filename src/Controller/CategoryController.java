/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;
import DataBase.CategoryDatabaseHandler;
import Model.Category;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import static scenebuilderfxlibrarymanagmentsystem.SceneBuilderFxLibraryManagmentSystem.AdminStage;
import static scenebuilderfxlibrarymanagmentsystem.SceneBuilderFxLibraryManagmentSystem.Categories;
import static scenebuilderfxlibrarymanagmentsystem.SceneBuilderFxLibraryManagmentSystem.CategoriesDB;
import static scenebuilderfxlibrarymanagmentsystem.SceneBuilderFxLibraryManagmentSystem.CategoryStage;

/**
 * FXML Controller class
 *
 * @author HP
 */
public class CategoryController implements Initializable {

    @FXML
    private TextField categoryTxtFielf;
    @FXML
    private Label categoryLabelError;
    private ComboBox<String> categoryCb;
    private ComboBox<String> categoryComboBoxForm;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }
    @FXML
    private void AddCategory(ActionEvent event) {
        categoryLabelError.setText("");
        if (categoryTxtFielf.getText().isEmpty()) {
            categoryLabelError.setText("Category is Required");
        } else {
            if (Categories.contains(categoryTxtFielf.getText())) {
                categoryLabelError.setText("This category already exist");
            } else {
                Category category = new Category(categoryTxtFielf.getText());
                CategoriesDB.add(category);
                Categories.add(category.getName());
                CategoryDatabaseHandler.addCategory(category);
                Alert alert = new Alert(Alert.AlertType.INFORMATION, "Category Added.");
                alert.showAndWait();
            }
        }
    }

    @FXML
    private void CancelCategory(ActionEvent event) throws IOException {
        CategoryStage.close();
        if (categoryCb != null && categoryComboBoxForm != null) {
            categoryCb.getItems().clear();
            categoryCb.getItems().add("All");
            for (Category cat : CategoriesDB) {
                categoryCb.getItems().add(cat.getName());
            }
            categoryComboBoxForm.getItems().clear();
            for (Category cat : CategoriesDB) {
                categoryComboBoxForm.getItems().add(cat.getName());
            }

        }
        AdminStage.show();
    }

    public void setCategoryComboBoxes(ComboBox<String> categoryCb, ComboBox<String> categoryComboBoxForm) {
        this.categoryCb = categoryCb;
        this.categoryComboBoxForm = categoryComboBoxForm;
    }
}

