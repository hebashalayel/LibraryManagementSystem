/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DataBase;

import Model.User_Book;
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
public class BorrowBookDatabaseHandler {
    public static ObservableList<User_Book> getBorrowBooksData() {
        Connection con = DatabaseConnction.getInstance();
        ObservableList<User_Book> tempBorrowBooks = FXCollections.observableArrayList();
        try {
            PreparedStatement ps = (PreparedStatement) con.prepareStatement("select * from user_book");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                tempBorrowBooks.add(new User_Book(rs.getInt("user_id"), rs.getInt("book_id")));
            }
        } catch (SQLException ex) {
            Logger.getLogger(BorrowBookDatabaseHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        return tempBorrowBooks;
    }
    public static void addUser_Book(User_Book user_book){
        Connection con = DatabaseConnction.getInstance();
        try {
            String query = "INSERT INTO user_book (user_id,book_id)VALUES (?,?)";
            PreparedStatement ps = (PreparedStatement) con.prepareStatement(query);
            ps.setInt(1, user_book.getUserId());
            ps.setInt(2, user_book.getBookId());
            ps.executeUpdate();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Insertion Failed: " + ex.getMessage());
            Logger.getLogger(BorrowBookDatabaseHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public  static void deleteUser_Book(User_Book user_book){
        Connection con = DatabaseConnction.getInstance();
        try {
            String query = "DELETE FROM user_book  WHERE user_id=? and book_id=?";
            PreparedStatement ps = (PreparedStatement) con.prepareStatement(query);
            ps.setInt(1, user_book.getUserId());
            ps.setInt(2, user_book.getBookId());
            ps.executeUpdate();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Deletion Failed: " + ex.getMessage());
            Logger.getLogger(BorrowBookDatabaseHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
