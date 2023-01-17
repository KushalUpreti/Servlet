package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Category;
import model.Item;
import service.CategoryService;
import service.ItemService;
import util.HTTPUtils;

import java.io.IOException;
import java.util.List;

//TODO: Remove type dependency from url
@WebServlet("/guest/*")
public class GuestController extends HttpServlet {

    private final CategoryService categoryService;
    private final ItemService itemService;

    public GuestController() {
        this.categoryService = new CategoryService();
        this.itemService = new ItemService();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        branch(request, response);
    }

    private void branch(HttpServletRequest request, HttpServletResponse response) {
        String path = request.getRequestURI();
        String[] urlSegment = path.split("/");

        if (urlSegment[2].equals("category")) {
            categoryGetEndPoint(request, response);
        } else if (urlSegment[2].equals("item")) {
            itemGetEndPoint(request, response);
        }
    }

    private void categoryGetEndPoint(HttpServletRequest request, HttpServletResponse response) {
        String requestType = request.getParameter("type");
        if (requestType.equals("category")) {
            List<Category> categories = categoryService.getAllCategories();
            HTTPUtils.sendResponse(response, categories);
        }
    }

    private void itemGetEndPoint(HttpServletRequest request, HttpServletResponse response) {
        try {
            Item item = itemService.getItem(request);
            HTTPUtils.sendResponse(response, item);
        } catch (ServletException s) {
            HTTPUtils.sendErrorResponse(response, 400, s.getMessage());
        }
    }
}
