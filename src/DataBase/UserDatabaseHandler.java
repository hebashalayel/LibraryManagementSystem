/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DataBase;
import Model.User;
import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;
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
public class UserDatabaseHandler {
    public static ObservableList<User> getUsersData() {
        Connection con = DatabaseConnction.getInstance();
        ObservableList<User> tempUsers = FXCollections.observableArrayList();
        try {
            PreparedStatement ps = (PreparedStatement) con.prepareStatement("select * from users");
            ResultSet rs = ps.executeQuery();
           while(rs.next()){
           tempUsers.add(new User(rs.getInt("id"),rs.getString("fullName"), rs.getString("userName"),rs.getString("password") , rs.getString("email"), rs.getString("phone"), rs.getString("role"),rs.getString("profileImagePath") ));
           }
        } catch (SQLException ex) {
            Logger.getLogger(UserDatabaseHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        return tempUsers;
    }
    public static void addUser(User user){
          Connection con = DatabaseConnction.getInstance();
           try {
            String query="INSERT INTO users (fullName,userName,password,email,phone,role,profileImagePath)VALUES (?,?,?,?,?,?,?)";
            PreparedStatement ps = (PreparedStatement) con.prepareStatement(query);
            ps.setString(1, user.getFullName());
            ps.setString(2, user.getUserName());
            ps.setString(3, user.getPassword());
            ps.setString(4, user.getEmail());
            ps.setString(5, user.getPhone());
            ps.setString(6, user.getRole());
            ps.setString(7, user.getProfileImagePath());
            ps.executeUpdate();
          
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null,"Insertion Failed: " + ex.getMessage());
            Logger.getLogger(UserDatabaseHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static void updateUser(User user){
          Connection con = DatabaseConnction.getInstance();
           try {
            String query="update  users set fullName= ?, userName= ?,password= ?,email= ?,phone= ?,role= ?,profileImagePath= ? where id=?";
            PreparedStatement ps = (PreparedStatement) con.prepareStatement(query);
            ps.setString(1, user.getFullName());
            ps.setString(2, user.getUserName());
            ps.setString(3, user.getPassword());
            ps.setString(4, user.getEmail());
            ps.setString(5, user.getPhone());
            ps.setString(6, user.getRole());
            ps.setString(7, user.getProfileImagePath());
            ps.setInt(8, user.getId());
            ps.executeUpdate();         
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null,"Updated Failed: " + ex.getMessage());
            Logger.getLogger(UserDatabaseHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
     public static void deleteUser(User user){
          Connection con = DatabaseConnction.getInstance();
           try {
            String query="delete from users where id=?";
            PreparedStatement ps = (PreparedStatement) con.prepareStatement(query);
            ps.setInt(1, user.getId());
            ps.executeUpdate();         
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null,"delete user Failed: " + ex.getMessage());
            Logger.getLogger(UserDatabaseHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
     public static ObservableList<User> get(String role) {
        Connection con = DatabaseConnction.getInstance();
        ObservableList<User> tempUsers = FXCollections.observableArrayList();
        try {
            PreparedStatement ps = (PreparedStatement) con.prepareStatement("SELECT * FROM users WHERE role = ?");
            ps.setString(1, role);
            ResultSet rs = ps.executeQuery();
           while(rs.next()){
           tempUsers.add(new User(rs.getInt("id"),rs.getString("fullName"), rs.getString("userName"),rs.getString("password") , rs.getString("email"), rs.getString("phone"), rs.getString("role"),rs.getString("profileImagePath") ));
           }
        } catch (SQLException ex) {
            Logger.getLogger(UserDatabaseHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return tempUsers;
    }
}
