package dto;

public class CategoryItemDTO {
    private String category;
    private String item;
    private int itemId;

    public CategoryItemDTO(String category, String item, int itemId) {
        this.category = category;
        this.item = item;
        this.itemId = itemId;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    @Override
    public String toString() {
        return "CategoryItemDTO{" +
                "category='" + category + '\'' +
                ", item='" + item + '\'' +
                ", itemId=" + itemId +
                '}';
    }
}
