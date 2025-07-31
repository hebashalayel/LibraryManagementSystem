package DataBase;

import Model.BorrowBookDetails;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javax.swing.JOptionPane;

public class BorrowBookDetailsDatabaseHandler {

    public static ObservableList<BorrowBookDetails> getBorrowBooksDetailsData() {
        ObservableList<BorrowBookDetails> BorrowedBooksDetails = FXCollections.observableArrayList();
        Connection con = DatabaseConnction.getInstance();
        try {
            String query = "SELECT * FROM borrowbookdetails";
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                BorrowBookDetails details = new BorrowBookDetails();
                details.setId(rs.getInt("id"));
                details.setUserId(rs.getInt("userId"));
                details.setUserName(rs.getString("userName"));
                details.setUserImage(rs.getString("userImage"));
                details.setBookId(rs.getInt("bookId"));
                details.setBookTitle(rs.getString("bookTitle"));
                details.setBookImage(rs.getString("bookImage"));
                details.setStatus(rs.getString("status"));
                details.setDeliveryDate(rs.getDate("delivery_date") != null ? rs.getDate("delivery_date").toLocalDate() : null); // Set delivery date
                details.setReturnDate(rs.getDate("return_date") != null ? rs.getDate("return_date").toLocalDate() : null); // Set return date
                BorrowedBooksDetails.add(details);
                System.out.println("Added BorrowBookDetails: " + details.getId());
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Failed to load data: " + ex.getMessage());
            Logger.getLogger(BorrowBookDetailsDatabaseHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        return BorrowedBooksDetails;
    }

    public static void addBorrowBookDetails(BorrowBookDetails borrowBookDetails) {
        Connection con = DatabaseConnction.getInstance();
        try {
            String query = "INSERT INTO borrowbookdetails (userId, userName, userImage, bookId, bookTitle, bookImage, status, delivery_date, return_date) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement ps = con.prepareStatement(query);
            ps.setInt(1, borrowBookDetails.getUserId());
            ps.setString(2, borrowBookDetails.getUserName());
            ps.setString(3, borrowBookDetails.getUserImage());
            ps.setInt(4, borrowBookDetails.getBookId());
            ps.setString(5, borrowBookDetails.getBookTitle());
            ps.setString(6, borrowBookDetails.getBookImage());
            ps.setString(7, borrowBookDetails.getStatus());
            ps.setDate(8, borrowBookDetails.getDeliveryDate() != null ? java.sql.Date.valueOf(borrowBookDetails.getDeliveryDate()) : null);
            ps.setDate(9, borrowBookDetails.getReturnDate() != null ? java.sql.Date.valueOf(borrowBookDetails.getReturnDate()) : null);
            ps.executeUpdate();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Insertion Failed: " + ex.getMessage());
            Logger.getLogger(BorrowBookDetailsDatabaseHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void updateBorrowBookDetails(BorrowBookDetails details, String status) {
    Connection con = DatabaseConnction.getInstance();
    PreparedStatement ps = null;
    try {
        String query = "UPDATE borrowbookdetails SET status = ?, delivery_date = ? WHERE userId = ? AND bookId = ?";
        ps = con.prepareStatement(query);
        ps.setString(1, status);
        ps.setDate(2, details.getDeliveryDate() != null ? java.sql.Date.valueOf(details.getDeliveryDate()) : null);
        ps.setInt(3, details.getUserId());
        ps.setInt(4, details.getBookId());
        ps.executeUpdate();
    } catch (SQLException ex) {
        Logger.getLogger(BorrowBookDetailsDatabaseHandler.class.getName()).log(Level.SEVERE, null, ex);
    }
}

    public static void deleteBorrowBookDetails(BorrowBookDetails bbd) {
        Connection con = DatabaseConnction.getInstance();
        try {
            String query = "DELETE FROM borrowbookdetails WHERE id=?";
            PreparedStatement ps = con.prepareStatement(query);
            ps.setInt(1, bbd.getId());
            ps.executeUpdate();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Delete book Failed: " + ex.getMessage());
            Logger.getLogger(BorrowBookDetailsDatabaseHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static ObservableList<BorrowBookDetails> get(String status) {
        Connection con = DatabaseConnction.getInstance();
        ObservableList<BorrowBookDetails> tempBorrowBookDetails = FXCollections.observableArrayList();
        try {
            PreparedStatement ps = con.prepareStatement("SELECT * FROM borrowbookdetails WHERE status = ?");
            ps.setString(1, status);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                tempBorrowBookDetails.add(new BorrowBookDetails(
                        rs.getInt("id"),
                        rs.getInt("userId"),
                        rs.getString("userName"),
                        rs.getString("userImage"),
                        rs.getInt("bookId"),
                        rs.getString("bookTitle"),
                        rs.getString("bookImage"),
                        rs.getString("status"),
                        rs.getDate("delivery_date") != null ? rs.getDate("delivery_date").toLocalDate() : null,
                        rs.getDate("return_date") != null ? rs.getDate("return_date").toLocalDate() : null
                ));
            }
        } catch (SQLException ex) {
            Logger.getLogger(BorrowBookDetailsDatabaseHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        return tempBorrowBookDetails;
    }

    public static ObservableList<BorrowBookDetails> getBorrowBooksDetailsDataPerUser(int userId) {
        ObservableList<BorrowBookDetails> BorrowedBooksDetails = FXCollections.observableArrayList();
        Connection con = DatabaseConnction.getInstance();
        try {
            String query = "SELECT * FROM borrowbookdetails WHERE userId = ?";
            PreparedStatement ps = con.prepareStatement(query);
            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                BorrowBookDetails details = new BorrowBookDetails();
                details.setId(rs.getInt("id"));
                details.setUserId(rs.getInt("userId"));
                details.setUserName(rs.getString("userName"));
                details.setUserImage(rs.getString("userImage"));
                details.setBookId(rs.getInt("bookId"));
                details.setBookTitle(rs.getString("bookTitle"));
                details.setBookImage(rs.getString("bookImage"));
                details.setStatus(rs.getString("status"));
                details.setDeliveryDate(rs.getDate("delivery_date") != null ? rs.getDate("delivery_date").toLocalDate() : null);
                details.setReturnDate(rs.getDate("return_date") != null ? rs.getDate("return_date").toLocalDate() : null);
                BorrowedBooksDetails.add(details);
                System.out.println("Added BorrowBookDetails: " + details.getId());
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Failed to load data: " + ex.getMessage());
            Logger.getLogger(BorrowBookDetailsDatabaseHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        return BorrowedBooksDetails;
    }
}
