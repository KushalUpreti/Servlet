package dto;

import java.util.ArrayList;
import java.util.List;

public class CategoryDTO {
    private int id;
    private String title;
    private List<ItemDTO> items;

    public CategoryDTO(String title) {
        this.title = title;
    }

    public CategoryDTO(int id, String title) {
        this(title);
        this.id = id;
        items = new ArrayList<>();
    }

    public CategoryDTO(int id, String title, List<ItemDTO> list) {
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

    public List<ItemDTO> getItems() {
        return items;
    }

    public void setItems(List<ItemDTO> items) {
        this.items = items;
    }
}
