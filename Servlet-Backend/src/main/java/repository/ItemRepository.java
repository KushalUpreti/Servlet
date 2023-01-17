package repository;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.Part;
import model.Category;
import model.Item;
import util.Constants;

import java.io.File;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ItemRepository {
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

    public Item addItem(Item item) throws SQLException {
        createConnection();
        String sql = "Insert into items (title,description,price,category_id) VALUES (?,?,?,?)";
        PreparedStatement prepareStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        prepareStatement.setString(1, item.getTitle());
        prepareStatement.setString(2, item.getDescription());
        prepareStatement.setDouble(3, item.getPrice());
        prepareStatement.setInt(4, item.getCategory().getId());
        prepareStatement.executeUpdate();

        ResultSet generatedKeys = prepareStatement.getGeneratedKeys();
        if (generatedKeys.next()) {
            item.setId(generatedKeys.getInt(1));
        }
        terminateConnection();
        return item;
    }

    public Item getItem(int itemId) throws SQLException {
        createConnection();
        String sql = "SELECT i.*,  c.title as category FROM servlet.categories c INNER JOIN servlet.items i ON c.id = i.category_id WHERE i.id = ?";
        PreparedStatement prepareStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        prepareStatement.setInt(1, itemId);
        ResultSet resultSet = prepareStatement.executeQuery();
        Item item = null;
        while (resultSet.next()) {
            int id = resultSet.getInt(1);
            String title = resultSet.getString(2);
            String description = resultSet.getString(3);
            double price = resultSet.getDouble(4);
            int categoryId = resultSet.getInt(5);
            String category = resultSet.getString(6);
            item = new Item(id, title, description, price, new Category(categoryId, category));
        }
        return item;
    }

    public List<Item> getAllItems() throws SQLException {
        createConnection();
        String sql = "SELECT i.*,  c.title as category FROM servlet.categories c INNER JOIN servlet.items i ON c.id = i.category_id";
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(sql);

        List<Item> items = new ArrayList<>();
        while (resultSet.next()) {
            int id = resultSet.getInt(1);
            String title = resultSet.getString(2);
            String description = resultSet.getString(3);
            double price = resultSet.getDouble(4);
            int categoryId = resultSet.getInt(5);
            String category = resultSet.getString(6);
            Item item = new Item(id, title, description, price, new Category(categoryId, category));
            items.add(item);
        }
        return items;
    }

    public void addImages(HttpServletRequest request, int itemId) throws ServletException, IOException {
        ArrayList<String> files = writeFiles(request);
        ArrayList<Integer> imageIdList = new ArrayList<>();
        if (files.size() == 0) {
            return;
        }
        createConnection();
        String sql = "Insert into images (title,item_id) VALUES (?,?)";
        try {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            for (String file : files) {
                ps.setString(1, file);
                ps.setInt(2, itemId);
                ps.addBatch();
                ps.clearParameters();
            }
            ps.executeBatch();

            ResultSet generatedKeys = ps.getGeneratedKeys();
            if (generatedKeys.next()) {
                imageIdList.add(generatedKeys.getInt(1));
            }
        } catch (SQLException s) {
            s.printStackTrace();
        } finally {
            terminateConnection();
        }
    }

    public List<String> getImages(int itemId) throws SQLException {
        createConnection();
        List<String> images = new ArrayList<>();
        String sql = "SELECT title FROM images WHERE item_id = ?";
        PreparedStatement prepareStatement = connection.prepareStatement(sql);
        prepareStatement.setInt(1, itemId);
        ResultSet resultSet = prepareStatement.executeQuery();

        while (resultSet.next()) {
            images.add(resultSet.getString(1));
        }
        terminateConnection();
        return images;
    }

    public boolean deleteItem(int itemId) {
        createConnection();
        try {
            String sql = "DELETE FROM items WHERE id = ?";
            PreparedStatement prepareStatement = connection.prepareStatement(sql);
            prepareStatement.setInt(1, itemId);
            prepareStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            terminateConnection();
        }
        return true;
    }


    public ArrayList<String> writeFiles(HttpServletRequest request) throws ServletException, IOException {
        String uploadFilePath = request.getServletContext().getAttribute("FILES_DIR").toString();
        ArrayList<String> fileNames = new ArrayList<>();

        for (Part part : request.getParts()) {
            String fileName = getFileName(part);
            if (!fileName.trim().equals("")) {
                part.write(uploadFilePath + File.separator + fileName);
                fileNames.add(fileName);
            }
        }
        return fileNames;
    }

    public boolean deleteFiles(String uploadFilePath, int itemId) {
        List<String> images = null;
        try {
            images = new ArrayList<>(getImages(itemId));
        } catch (SQLException s) {
            s.printStackTrace();
            return false;
        }
        for (String image : images) {
            File file = new File(uploadFilePath + File.separator + image);
            file.delete();
        }
        return true;
    }

    private String getFileName(Part part) {
        String contentDisp = part.getHeader("content-disposition");
        String[] tokens = contentDisp.split(";");
        for (String token : tokens) {
            if (token.trim().startsWith("filename")) {
                return token.substring(token.indexOf("=") + 2, token.length() - 1);
            }
        }
        return "";
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
