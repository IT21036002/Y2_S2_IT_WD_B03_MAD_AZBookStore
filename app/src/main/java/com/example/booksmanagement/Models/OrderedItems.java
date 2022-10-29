package com.example.booksmanagement.Models;

public class OrderedItems {

    String id;
    String name;
    String price;
    String updatePrice;
    String qty;
    String image;


    public OrderedItems() {
    }

    public OrderedItems(String id, String name, String price, String updatePrice, String qty, String image) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.updatePrice = updatePrice;
        this.qty = qty;
        this.image = image;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getUpdatePrice() {
        return updatePrice;
    }

    public void setUpdatePrice(String updatePrice) {
        this.updatePrice = updatePrice;
    }

    public String getQty() {
        return qty;
    }

    public void setQty(String qty) {
        this.qty = qty;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
