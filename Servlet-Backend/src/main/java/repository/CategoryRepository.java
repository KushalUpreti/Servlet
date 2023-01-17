package repository;

import db.DBConnection;
import db.QueryBuilder;
import model.Category;
import model.Item;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CategoryRepository extends DBConnection {

    public Category getCategory(int id) {
        createConnection();
        Category category = null;

        try {
            String sql = "SELECT * FROM categories WHERE id = ?";
            PreparedStatement prepareStatement = getConnection().prepareStatement(sql);
            prepareStatement.setInt(1, id);
            ResultSet resultSet = prepareStatement.executeQuery();

            while (resultSet.next()) {
                int categoryId = resultSet.getInt(1);
                String title = resultSet.getString(2);
                category = new Category(categoryId, title);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            terminateConnection();
        }
        return category;
    }

    public List<Category> getAllCategories() {
        createConnection();
        List<Category> categories = new ArrayList<>();
        try {
            String sql = "SELECT c.id as category_id, c.title as category, i.id as item_id, i.title, i.description, i.price FROM categories c LEFT JOIN items i ON c.id = i.category_id;";
            Statement stmt = getConnection().createStatement();
            ResultSet resultSet = stmt.executeQuery(sql);

            Map<Integer, Category> categoryMap = new HashMap<>();
            while (resultSet.next()) {
                int categoryId = resultSet.getInt(1);
                String category = resultSet.getString(2);
                int itemId = resultSet.getInt(3);
                String itemTitle = resultSet.getString(4);
                String itemDescription = resultSet.getString(5);
                double itemPrice = resultSet.getDouble(6);

                if (categoryMap.containsKey(categoryId)) {
                    Category categoryDTO = categoryMap.get(categoryId);
                    if (itemId != 0) {
                        categoryDTO.getItems().add(new Item(itemId, itemTitle, itemDescription, itemPrice));
                    }
                } else {
                    List<Item> list = new ArrayList<>();
                    if (itemId != 0) {
                        list.add(new Item(itemId, itemTitle, itemDescription, itemPrice));
                    }
                    categoryMap.put(categoryId, new Category(categoryId, category, list));
                }
            }
            categories = new ArrayList<>(categoryMap.values());
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            terminateConnection();
        }
        return categories;
    }

    public void addCategory(String title) {
        createConnection();
        try {
            QueryBuilder queryBuilder = new QueryBuilder(getConnection());
            PreparedStatement prepareStatement = queryBuilder.insert("categories", "title")
                    .build();
            prepareStatement.setString(1, title);
            prepareStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            terminateConnection();
        }
    }
}
