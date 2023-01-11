package dto;

import java.util.List;

public class CategoryDTO {
    private int id;
    private String title;
//    private List<ItemDTO> items;

    public CategoryDTO(String title) {
        this.title = title;
    }

    public CategoryDTO(int id, String title) {
        this.id = id;
        this.title = title;
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
}
