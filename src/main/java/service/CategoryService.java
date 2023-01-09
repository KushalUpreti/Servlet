package service;

import com.google.gson.Gson;
import dto.CategoryDTO;
import dto.UserDTO;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import repository.CategoryRepository;
import utils.HTTPUtils;

import java.sql.SQLException;

public class CategoryService {
    private final CategoryRepository categoryRepository;
    private final Gson gson;

    public CategoryService() {
        this.gson = new Gson();
        this.categoryRepository = new CategoryRepository();
    }

    public void addCategory(HttpServletRequest request) throws ServletException, SQLException {
        String requestBody = HTTPUtils.jsonParser(request);
        CategoryDTO categoryDTO = gson.fromJson(requestBody, CategoryDTO.class);
        if(categoryDTO.getTitle().trim().equals("")){
            throw new ServletException("Invalid category name");
        }
        categoryRepository.addCategory(categoryDTO.getTitle());
    }
}
