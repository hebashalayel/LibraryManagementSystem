/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DataBase;

import Model.Book;
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
public class BookDatabaseHandler {

    public static ObservableList<Book> getBooksData() {
        Connection con = DatabaseConnction.getInstance();
        ObservableList<Book> tempBooks = FXCollections.observableArrayList();
        try {
            PreparedStatement ps = (PreparedStatement) con.prepareStatement("select * from books");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                tempBooks.add(new Book(rs.getInt("id"), rs.getString("title"), rs.getString("author"), rs.getString("category"), rs.getString("isbn"), rs.getDate("publishDate").toString(), rs.getString("language"), rs.getString("bookImagePath"), rs.getInt("pageCount"), rs.getInt("copyCount"), rs.getString("publisher")));
            }
        } catch (SQLException ex) {
            Logger.getLogger(BookDatabaseHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        return tempBooks;
    }

    public static void addBook(Book book) {
        Connection con = DatabaseConnction.getInstance();
        try {
            String query = "INSERT INTO books (title,author,category,isbn,publishDate,language,bookImagePath,pageCount,copyCount,publisher)VALUES (?,?,?,?,?,?,?,?,?,?)";
            PreparedStatement ps = (PreparedStatement) con.prepareStatement(query);
            ps.setString(1, book.getTitle());
            ps.setString(2, book.getAuthor());
            ps.setString(3, book.getCategory());
            ps.setString(4, book.getIsbn());
            ps.setString(5, book.getPublishDate());
            ps.setString(6, book.getLanguage());
            ps.setString(7, book.getBookImagePath());
            ps.setInt(8, book.getPageCount());
            ps.setInt(9, book.getCopyCount());
            ps.setString(10, book.getPublisher());
            ps.executeUpdate();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Insertion Failed: " + ex.getMessage());
            Logger.getLogger(BookDatabaseHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void updateBook(Book book) {
        Connection con = DatabaseConnction.getInstance();
        try {
            String query = "update books set title=?,author=?,category=?,isbn=?,publishDate=?,language=?,bookImagePath=?,pageCount=?,copyCount=?,publisher=? where id=?";
            PreparedStatement ps = (PreparedStatement) con.prepareStatement(query);
            ps.setString(1, book.getTitle());
            ps.setString(2, book.getAuthor());
            ps.setString(3, book.getCategory());
            ps.setString(4, book.getIsbn());
            ps.setString(5, book.getPublishDate());
            ps.setString(6, book.getLanguage());
            ps.setString(7, book.getBookImagePath());
            ps.setInt(8, book.getPageCount());
            ps.setInt(9, book.getCopyCount());
            ps.setString(10, book.getPublisher());
            ps.setInt(11, book.getId());
            ps.executeUpdate();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Updated Failed: " + ex.getMessage());
            Logger.getLogger(BookDatabaseHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
     public static void updateCopyCount(int bookId) throws SQLException{
         Connection con = DatabaseConnction.getInstance();
         Book book=getBook(bookId);
         String query="UPDATE books set copyCount=? where id=?";
         PreparedStatement ps = (PreparedStatement) con.prepareStatement(query);
         ps.setInt(1, book.getCopyCount()+1);
         ps.setInt(2, bookId);
         ps.executeUpdate();
     }

    public static void deleteBook(Book book) {
        Connection con = DatabaseConnction.getInstance();
        try {
            String query = "delete from books where id=?";
            PreparedStatement ps = (PreparedStatement) con.prepareStatement(query);
            ps.setInt(1, book.getId());
            ps.executeUpdate();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "delete book Failed: " + ex.getMessage());
            Logger.getLogger(BookDatabaseHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static Book getBook(int bookId) {
        Connection con = DatabaseConnction.getInstance();
        Book book=null;
        try {
            PreparedStatement ps = (PreparedStatement) con.prepareStatement("select * from books where id= ?");
            ps.setInt(1, bookId);
            ResultSet rs = ps.executeQuery();   
            while(rs.next()){
                book= new Book(rs.getInt("id"), rs.getString("title"), rs.getString("author"), rs.getString("category"), rs.getString("isbn"), rs.getDate("publishDate").toString(), rs.getString("language"), rs.getString("bookImagePath"), rs.getInt("pageCount"), rs.getInt("copyCount"), rs.getString("publisher"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(BookDatabaseHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        return book;
    }
}
