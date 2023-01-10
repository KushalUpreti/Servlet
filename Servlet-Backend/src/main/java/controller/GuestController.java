package controller;

import dto.CategoryDTO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import service.CategoryService;
import util.HTTPUtils;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/guest")
public class GuestController extends HttpServlet {

    private final CategoryService categoryService;

    public GuestController() {
        this.categoryService = new CategoryService();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse response) throws ServletException, IOException {
        String requestType = req.getParameter("type");
        if (requestType.equals("category")) {
            try {
                List<CategoryDTO> categories = categoryService.getAllCategories();
                HTTPUtils.sendResponse(response, categories);
            } catch (SQLException s) {
                HTTPUtils.sendErrorResponse(response, 400, s.getMessage());
            }
        }
    }
}
