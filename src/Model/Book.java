/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;
import java.util.Date;
/**
 *
 * @author HP
 */
public class Book {
    private int id;
    private String title;
    private String author;
    private String category;
    private String isbn;
    private String publishDate;
    private String language;
    private String bookImagePath;
    private int pageCount;
    private int copyCount;
    private String publisher;

    public Book() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Book(int id, String title, String author, String category, String isbn, String publishDate, String language, String bookImagePath, int pageCount, int copyCount, String publisher) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.category = category;
        this.isbn = isbn;
        this.publishDate = publishDate;
        this.language = language;
        this.bookImagePath = bookImagePath;
        this.pageCount = pageCount;
        this.copyCount = copyCount;
        this.publisher = publisher;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }
    
    public Book(String title, String author, String category, String isbn, String publishDate, String language, String bookImagePath, int pageCount, int copyCount,String publisher) {
        this.title = title;
        this.author = author;
        this.category = category;
        this.isbn = isbn;
        this.publishDate = publishDate;
        this.language = language;
        this.bookImagePath = bookImagePath;
        this.pageCount = pageCount;
        this.copyCount = copyCount;
        this.publisher=publisher;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(String publishDate) {
        this.publishDate = publishDate;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getBookImagePath() {
        return bookImagePath;
    }

    public void setBookImagePath(String bookImagePath) {
        this.bookImagePath = bookImagePath;
    }

    public int getPageCount() {
        return pageCount;
    }

    public void setPageCount(int pageCount) {
        this.pageCount = pageCount;
    }

    public int getCopyCount() {
        return copyCount;
    }

    public void setCopyCount(int copyCount) {
        this.copyCount = copyCount;
    }


}
