package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import service.CategoryService;
import utils.HTTPUtils;

import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/category")
public class CategoryController extends HttpServlet {
    private final CategoryService categoryService;


    public CategoryController() {
        this.categoryService = new CategoryService();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    }

    @Override
    protected void doPost( HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            categoryService.addCategory(request);
        } catch (ServletException | SQLException s) {
            HTTPUtils.sendErrorResponse(response, 400, s.getMessage());
        }
    }
}
