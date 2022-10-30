package com.example.booksmanagement.Models;

public class BookDetails {

    String id;
    String image;
    String bookCode;
    String bookName;
    String bookPrice;
    String bookDescription;

    public BookDetails() {
    }

    public BookDetails(String id, String image, String bookCode, String bookName, String bookPrice, String bookDescription) {
        this.id = id;
        this.image = image;
        this.bookCode = bookCode;
        this.bookName = bookName;
        this.bookPrice = bookPrice;
        this.bookDescription = bookDescription;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getBookCode() {
        return bookCode;
    }

    public void setBookCode(String bookCode) {
        this.bookCode = bookCode;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public String getBookPrice() {
        return bookPrice;
    }

    public void setBookPrice(String bookPrice) {
        this.bookPrice = bookPrice;
    }

    public String getBookDescription() {
        return bookDescription;
    }

    public void setBookDescription(String bookDescription) {
        this.bookDescription = bookDescription;
    }
}
