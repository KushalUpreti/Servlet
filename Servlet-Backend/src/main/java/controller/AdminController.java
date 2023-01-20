package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Item;
import service.CategoryService;
import service.ItemService;
import util.HTTPUtils;

import java.io.IOException;

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
        deleteBranch(req, resp);
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String path = request.getRequestURI();
        String[] urlSegment = path.split("/");

        if (urlSegment[2].equals("category")) {
        } else if (urlSegment[2].equals("item")) {
            try {
                HTTPUtils.sendResponse(response,itemService.updateItem(request));
            } catch (ServletException s) {
                HTTPUtils.sendErrorResponse(response, 400, s.getMessage());
            }
        }
    }

    private void postBranch(HttpServletRequest request, HttpServletResponse response) {
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

    private void deleteBranch(HttpServletRequest request, HttpServletResponse response) {
        String path = request.getRequestURI();
        String[] urlSegment = path.split("/");

        if (urlSegment[2].equals("category")) {
            categoryDeleteEndPoint(request, response);
        } else if (urlSegment[2].equals("item")) {
            itemDeleteEndPoint(request, response);
        } else if (urlSegment[2].equals("image")) {
            imageDeleteEndPoint(request, response);
        }
    }

    private void categoryPostEndPoint(HttpServletRequest request, HttpServletResponse response) {
        try {
            categoryService.addCategory(request);
        } catch (ServletException s) {
            HTTPUtils.sendErrorResponse(response, 400, s.getMessage());
        }
    }

    private void itemPostEndPoint(HttpServletRequest request, HttpServletResponse response) {
        try {
            Item item = itemService.addItem(request);
            HTTPUtils.sendResponse(response, item);
        } catch (Exception exception) {
            HTTPUtils.sendErrorResponse(response, 400, exception.getMessage());
        }
    }


    private void itemGetEndPoints(HttpServletRequest request, HttpServletResponse response) {
        HTTPUtils.sendResponse(response, itemService.getAllItems());
    }

    private void categoryDeleteEndPoint(HttpServletRequest request, HttpServletResponse response) {

    }

    private void itemDeleteEndPoint(HttpServletRequest request, HttpServletResponse response) {
        try {
            if (itemService.deleteItem(request)) {
                HTTPUtils.sendResponse(response, "Item deleted successfully");
            }
        } catch (Exception s) {
            HTTPUtils.sendErrorResponse(response, 400, s.getMessage());
        }
    }

    private void imageDeleteEndPoint(HttpServletRequest request, HttpServletResponse response) {
        try {
            if (itemService.deleteImage(request)) {
                HTTPUtils.sendResponse(response, "Item deleted successfully");
            }
        } catch (Exception s) {
            HTTPUtils.sendErrorResponse(response, 400, s.getMessage());
        }
    }
}
