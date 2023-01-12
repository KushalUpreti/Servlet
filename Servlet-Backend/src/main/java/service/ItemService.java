package service;

import com.google.gson.Gson;
import dto.CategoryDTO;
import dto.ItemDTO;
import exception.ClientException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import repository.CategoryRepository;
import repository.ItemRepository;
import util.HTTPUtils;

import java.sql.SQLException;

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
    public ItemDTO addItem(HttpServletRequest request) throws SQLException, ClientException {
        int categoryId = Integer.parseInt(request.getPathInfo().split("/")[2]);

        String requestBody = HTTPUtils.jsonParser(request);

        CategoryDTO category = categoryRepository.getCategory(categoryId);
        ItemDTO itemDTO = gson.fromJson(requestBody, ItemDTO.class);
        itemDTO.setCategory(category);
        return itemRepository.addItem(itemDTO);
    }

    public ItemDTO getItem(int itemId) throws SQLException, ServletException {
        ItemDTO dto = itemRepository.getItem(itemId);
        if (dto == null) {
            throw new ServletException("Item not found");
        }
        return dto;
    }

}
