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

    public ItemDTO addItem(HttpServletRequest request) throws ServletException, SQLException {
        String requestBody = HTTPUtils.jsonParser(request);
        ItemDTO itemDTO = gson.fromJson(requestBody, ItemDTO.class);
        return itemRepository.addItem(itemDTO);
    }

}
