package controller;

import dto.ItemDTO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import service.CategoryService;
import service.ItemService;
import util.HTTPUtils;

import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/admin/*")
public class AdminController extends HttpServlet {

    private final CategoryService categoryService;
    private final ItemService itemService;

    public AdminController() {
        this.categoryService = new CategoryService();
        this.itemService = new ItemService();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        branch(req, resp);
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doDelete(req, resp);
    }

    private void branch(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String path = request.getRequestURI();
        String[] urlSegment = path.split("/");

        if (urlSegment[2].equals("category")) {
            categoryPostEndPoint(request, response);
        } else if (urlSegment[2].equals("item")) {
            itemPostEndPoint(request, response);
        }
    }

    private void categoryPostEndPoint(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            categoryService.addCategory(request);
        } catch (ServletException | SQLException s) {
            HTTPUtils.sendErrorResponse(response, 400, s.getMessage());
        }
    }

    private void itemPostEndPoint(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            ItemDTO itemDTO = itemService.addItem(request);
            HTTPUtils.sendResponse(response, itemDTO);
        } catch (SQLException exception) {
            HTTPUtils.sendErrorResponse(response, 400, exception.getMessage());
        }
    }
}
