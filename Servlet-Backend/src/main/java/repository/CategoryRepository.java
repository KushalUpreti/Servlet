package repository;

import dto.CategoryDTO;
import dto.CategoryItemDTO;
import util.Constants;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.groupingBy;

public class CategoryRepository {
    Connection connection;

    private void createConnection() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection =
                    DriverManager.getConnection("jdbc:mysql://localhost:3306/servlet", "root", Constants.env.get("MYSQL_PASS"));
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    public List<CategoryDTO> getAllCategories() throws SQLException {
        createConnection();
        String sql = "SELECT * FROM categories";
        Statement stmt = connection.createStatement();
        ResultSet resultSet = stmt.executeQuery(sql);

        List<CategoryDTO> categories = new ArrayList<>();
        while (resultSet.next()) {
            int categoryId = resultSet.getInt(1);
            String title = resultSet.getString(2);
            categories.add(new CategoryDTO(categoryId, title));
        }
        terminateConnection();
        return categories;
    }

    // TODO: Update getAllCategoriesWithItems logic using item list inside CategoryDTO
    public Map<String, List<CategoryItemDTO>> getAllCategoriesWithItems() throws SQLException {
        createConnection();
        String sql = "SELECT c.title as category, i.title, i.id AS item_id FROM categories c\n" +
                "INNER JOIN items i ON c.id = i.category_id";
        Statement stmt = connection.createStatement();
        ResultSet resultSet = stmt.executeQuery(sql);
        List<CategoryItemDTO> categoriesWithItems = new ArrayList<>();

        while (resultSet.next()) {
            String category = resultSet.getString(1);
            String item = resultSet.getString(2);
            int itemId = resultSet.getInt(3);
            categoriesWithItems.add(new CategoryItemDTO(category, item, itemId));
        }

        Map<String, List<CategoryItemDTO>> postsPerType = categoriesWithItems.stream()
                .collect(groupingBy(CategoryItemDTO::getCategory));

        terminateConnection();
        return postsPerType;
    }

    public void addCategory(String title) throws SQLException {
        createConnection();
        String sql = "Insert into categories (title) VALUES (?)";
        PreparedStatement prepareStatement = connection.prepareStatement(sql);
        prepareStatement.setString(1, title);
        prepareStatement.executeUpdate();
        terminateConnection();
    }

    private void terminateConnection() {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException exception) {
                exception.printStackTrace();
            }
        }
    }
}
