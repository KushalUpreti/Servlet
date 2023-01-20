package service;

import com.google.gson.Gson;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import model.Category;
import model.Image;
import model.Item;
import repository.CategoryRepository;
import repository.ItemRepository;

import java.io.IOException;
import java.util.List;

public class ItemService {
    private final ItemRepository itemRepository;
    private final CategoryRepository categoryRepository;
    private final Gson gson;

    public ItemService() {
        this.itemRepository = new ItemRepository();
        this.categoryRepository = new CategoryRepository();
        this.gson = new Gson();
    }

    //TODO: Add input validation
    public Item addItem(HttpServletRequest request) throws Exception {
        int categoryId = Integer.parseInt(request.getPathInfo().split("/")[2]);
        String title = request.getParameter("title");
        String description = request.getParameter("description");
        double price = Double.parseDouble(request.getParameter("price"));

        Category category = categoryRepository.getCategory(categoryId);
        Item item = new Item(title, description, price);
        item.setCategory(category);

        Item addedItem = itemRepository.addItem(item);
        itemRepository.addImages(request, addedItem.getId());
        return addedItem;
    }

    public Item updateItem(HttpServletRequest request) throws ServletException, IOException {
        int itemId = Integer.parseInt(request.getPathInfo().split("/")[2]);
        String title = request.getParameter("title");
        String description = request.getParameter("description");
        double price = Double.parseDouble(request.getParameter("price"));
        int categoryId = Integer.parseInt(request.getParameter("category"));

        Category category = categoryRepository.getCategory(categoryId);
        Item item = new Item(itemId, title, description, price);
        item.setCategory(category);

        Item updatedItem = itemRepository.updateItem(item);
        itemRepository.addImages(request, updatedItem.getId());
        return updatedItem;

    }

    public Item getItem(HttpServletRequest request) throws ServletException {
        int itemId = Integer.parseInt(request.getPathInfo().split("/")[2]);
        Item item = itemRepository.getItem(itemId);
        if (item == null) {
            throw new ServletException("Item not found");
        }
        List<Image> images = itemRepository.getImages(itemId);
        item.setImages(images);
        return item;
    }

    public List<Item> getAllItems() {
        List<Item> items = itemRepository.getAllItems();
        return items;
    }

    public boolean deleteItem(HttpServletRequest request) {
        int itemId = Integer.parseInt(request.getPathInfo().split("/")[2]);
        String uploadFilePath = request.getServletContext().getAttribute("FILES_DIR").toString();
        if (!itemRepository.deleteFiles(uploadFilePath, itemId)) {
            return false;
        }
        return itemRepository.deleteItem(itemId);
    }

    public boolean deleteImage(HttpServletRequest request) {
        int imageId = Integer.parseInt(request.getPathInfo().split("/")[2]);
        String uploadFilePath = request.getServletContext().getAttribute("FILES_DIR").toString();
        if (!itemRepository.deleteFile(uploadFilePath, imageId)) {
            return false;
        }
        return itemRepository.deleteImage(imageId);
    }
}
