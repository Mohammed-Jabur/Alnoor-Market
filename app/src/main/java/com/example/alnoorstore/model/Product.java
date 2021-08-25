package com.example.alnoorstore.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(tableName = "Product", indices = {@Index(value = {"Number"}, unique = true)})
public class Product {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "Number")
    private String number;

    @ColumnInfo(name = "Name")
    private String name;

    @ColumnInfo(name = "Price")
    private float price;

    @ColumnInfo(name = "Category")
    private String category;

    public Product(int id, String number, String name, float price, String category) {
        this.id = id;
        this.number = number;
        this.name = name;
        this.price = price;
        this.category = category;
    }

    public int getId() {
        return id;
    }

    public String getNumber() {
        return number;
    }

    public String getName() {
        return name;
    }

    public float getPrice() {
        return price;
    }

    public String getCategory() {
        return category;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
