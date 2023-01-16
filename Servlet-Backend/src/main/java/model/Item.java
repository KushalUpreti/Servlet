package model;

import java.util.List;

public class Item {
    private int id;
    private String title;
    private String description;
    private double price;
    private Category category;
    private List<String> images;

    public Item(String title, String description, double price) {
        this.title = title;
        this.description = description;
        this.price = price;
    }

    public Item(int id, String title, String description, double price) {
        this(title, description, price);
        this.id = id;
    }

    public Item(int id, String title, String description, double price, Category category) {
        this(id, title, description, price);
        this.category = category;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }
}
