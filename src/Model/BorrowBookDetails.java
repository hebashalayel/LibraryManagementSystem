/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.time.LocalDate;

public class BorrowBookDetails {
    private int id;

    public BorrowBookDetails() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    private int  userId ;
    private String userName ;
    private String  userImage;
    private int  bookId ;
    private String bookTitle ;
    private String  bookImage;
    private String status;
    private LocalDate deliveryDate;
    private LocalDate returnDate;
    public BorrowBookDetails(int id, int userId, String userName, String userImage, int bookId, String bookTitle, String bookImage, String status,LocalDate deliveryDate, LocalDate returnDate) {
        this.id = id;
        this.userId = userId;
        this.userName = userName;
        this.userImage = userImage;
        this.bookId = bookId;
        this.bookTitle = bookTitle;
        this.bookImage = bookImage;
        this.status = status;
    }

    public BorrowBookDetails(int userId, String userName, String userImage, int bookId, String bookTitle, String bookImage, String status,LocalDate deliveryDate, LocalDate returnDate) {
        this.userId = userId;
        this.userName = userName;
        this.userImage = userImage;
        this.bookId = bookId;
        this.bookTitle = bookTitle;
        this.bookImage = bookImage;
        this.status = status;
    }

    public LocalDate getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(LocalDate deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public LocalDate getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(LocalDate returnDate) {
        this.returnDate = returnDate;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserImage() {
        return userImage;
    }

    public void setUserImage(String userImage) {
        this.userImage = userImage;
    }

    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    public String getBookTitle() {
        return bookTitle;
    }

    public void setBookTitle(String bookTitle) {
        this.bookTitle = bookTitle;
    }

    public String getBookImage() {
        return bookImage;
    }

    public void setBookImage(String bookImage) {
        this.bookImage = bookImage;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}
