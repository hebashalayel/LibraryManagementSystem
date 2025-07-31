/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DataBase;

import Model.Category;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javax.swing.JOptionPane;

/**
 *
 * @author HP
 */
public class CategoryDatabaseHandler {
        public static ObservableList<Category> getCategoriesData(){
        Connection con = DatabaseConnction.getInstance();
        ObservableList<Category> tempCategories = FXCollections.observableArrayList();
        try {
            PreparedStatement ps = (PreparedStatement) con.prepareStatement("select * from categories");
            ResultSet rs = ps.executeQuery();
           while(rs.next()){
           tempCategories.add(new Category(rs.getInt("id"),rs.getString("name")));
           }
        } catch (SQLException ex) {
            Logger.getLogger(CategoryDatabaseHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        return tempCategories;
    }
    public static void addCategory(Category category){
          Connection con = DatabaseConnction.getInstance();
           try {
            String query="INSERT INTO categories (name)VALUES (?)";
            PreparedStatement ps = (PreparedStatement) con.prepareStatement(query);
            ps.setString(1, category.getName());            
            ps.executeUpdate();          
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null,"Insertion Failed: " + ex.getMessage());
            Logger.getLogger(CategoryDatabaseHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }    
}
