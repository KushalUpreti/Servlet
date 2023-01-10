package controller;

import dto.ItemDTO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import service.ItemService;
import util.HTTPUtils;

import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/item")
public class ItemController extends HttpServlet {
    private final ItemService itemService;

    public ItemController() {
        this.itemService = new ItemService();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            ItemDTO itemDTO = itemService.addItem(req);
            HTTPUtils.sendResponse(resp, itemDTO);
        } catch (SQLException exception) {
            HTTPUtils.sendErrorResponse(resp, 400, exception.getMessage());
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doDelete(req, resp);
    }
}
