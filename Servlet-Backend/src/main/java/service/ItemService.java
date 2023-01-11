package service;

import com.google.gson.Gson;
import dto.ItemDTO;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import repository.ItemRepository;
import util.HTTPUtils;

import java.sql.SQLException;

public class ItemService {
    private final ItemRepository itemRepository;
    private final Gson gson;

    public ItemService() {
        this.itemRepository = new ItemRepository();
        this.gson = new Gson();
    }

    //TODO: Add input validation
    public ItemDTO addItem(HttpServletRequest request) throws SQLException {
        String requestBody = HTTPUtils.jsonParser(request);
        ItemDTO itemDTO = gson.fromJson(requestBody, ItemDTO.class);
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
