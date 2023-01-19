package model;

public class Image {
    private int id;
    private String title;
    private String imageURL;
    private Item item;

    public Image(String title) {
        this.title = title;
    }

    public Image(int id,String title) {
        this(title);
        this.id = id;
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

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

}
