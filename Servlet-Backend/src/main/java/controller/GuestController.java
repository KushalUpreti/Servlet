package controller;

import dto.CategoryDTO;
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

    private void branch(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String path = request.getRequestURI();
        String[] urlSegment = path.split("/");

        if (urlSegment[2].equals("category")) {
            categoryGetEndPoint(request, response);
        } else if (urlSegment[2].equals("item")) {
            itemGetEndPoint(request, response);
        }
    }

    private void categoryGetEndPoint(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String requestType = request.getParameter("type");
        if (requestType.equals("category")) {
            try {
                List<CategoryDTO> categories = categoryService.getAllCategories();
                HTTPUtils.sendResponse(response, categories);
            } catch (SQLException s) {
                HTTPUtils.sendErrorResponse(response, 400, s.getMessage());
            }
        }
    }

    private void itemGetEndPoint(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        int index = Integer.parseInt(request.getPathInfo().split("/")[2]);

        try {
            ItemDTO item = itemService.getItem(index);
            HTTPUtils.sendResponse(response, item);
        } catch (SQLException | ServletException s) {
            HTTPUtils.sendErrorResponse(response, 400, s.getMessage());
        }
    }
}
