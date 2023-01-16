package model;

import java.util.ArrayList;
import java.util.List;

public class Category {
    private int id;
    private String title;
    private List<Item> items;

    public Category(String title) {
        this.title = title;
    }

    public Category(int id, String title) {
        this(title);
        this.id = id;
        items = new ArrayList<>();
    }

    public Category(int id, String title, List<Item> list) {
        this(id, title);
        this.items = list;
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

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }
}
