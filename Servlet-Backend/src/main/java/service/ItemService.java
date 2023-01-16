package service;

import com.google.gson.Gson;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import model.Category;
import model.Item;
import repository.CategoryRepository;
import repository.ItemRepository;

import java.sql.SQLException;
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

        Item item2 = itemRepository.addItem(item);
        itemRepository.addImages(request, item.getId());
        return item2;
    }

    public Item getItem(int itemId) throws SQLException, ServletException {
        Item item = itemRepository.getItem(itemId);
        List<String> images = itemRepository.getImages(itemId);
        item.setImages(images);
        if (item == null) {
            throw new ServletException("Item not found");
        }
        return item;
    }

    public List<Item> getAllItems() throws SQLException {
        List<Item> items = itemRepository.getAllItems();
        return items;
    }
}
