package controller;

import model.Item;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import service.CategoryService;
import service.ItemService;
import util.HTTPUtils;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

//TODO: Consistent error handling
@WebServlet("/admin/*")
@MultipartConfig(fileSizeThreshold = 1024 * 1024,
        maxFileSize = 1024 * 1024 * 5,
        maxRequestSize = 1024 * 1024 * 5 * 5)
public class AdminController extends HttpServlet {

    private final CategoryService categoryService;
    private final ItemService itemService;


    public AdminController() {
        this.categoryService = new CategoryService();
        this.itemService = new ItemService();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        getBranch(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        postBranch(req, resp);
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doDelete(req, resp);
    }

    private void postBranch(HttpServletRequest request, HttpServletResponse response) throws ServletException {
        String path = request.getRequestURI();
        String[] urlSegment = path.split("/");

        if (urlSegment[2].equals("category")) {
            categoryPostEndPoint(request, response);
        } else if (urlSegment[2].equals("item")) {
            itemPostEndPoint(request, response);
        }
    }

    private void getBranch(HttpServletRequest request, HttpServletResponse response) {
        String path = request.getRequestURI();
        String[] urlSegment = path.split("/");

        if (urlSegment[2].equals("category")) {
            categoryPostEndPoint(request, response);
        } else if (urlSegment[2].equals("item")) {
            itemGetEndPoints(request, response);
        }
    }

    private void categoryPostEndPoint(HttpServletRequest request, HttpServletResponse response) {
        try {
            categoryService.addCategory(request);
        } catch (ServletException | SQLException s) {
            HTTPUtils.sendErrorResponse(response, 400, s.getMessage());
        }
    }

    private void itemPostEndPoint(HttpServletRequest request, HttpServletResponse response) throws ServletException {
        try {

            Item item = itemService.addItem(request);
            HTTPUtils.sendResponse(response, item);
        } catch (Exception exception) {
            HTTPUtils.sendErrorResponse(response, 400, exception.getMessage());
        }
    }


    private void itemGetEndPoints(HttpServletRequest request, HttpServletResponse response) {
        try {
            List<Item> items = itemService.getAllItems();
            HTTPUtils.sendResponse(response, items);
        } catch (SQLException exception) {
            HTTPUtils.sendErrorResponse(response, 400, exception.getMessage());
        }
    }
}
