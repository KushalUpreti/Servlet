package repository;

import db.DBConnection;
import db.QueryBuilder;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.Part;
import model.Category;
import model.Image;
import model.Item;

import java.io.File;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ItemRepository extends DBConnection {

    public Item addItem(Item item) {
        createConnection();
        try {
            QueryBuilder queryBuilder = new QueryBuilder(getConnection());
            PreparedStatement prepareStatement = queryBuilder
                    .insert("items", "title", "description", "price", "category_id")
                    .build();
            prepareStatement.setString(1, item.getTitle());
            prepareStatement.setString(2, item.getDescription());
            prepareStatement.setDouble(3, item.getPrice());
            prepareStatement.setInt(4, item.getCategory().getId());
            prepareStatement.executeUpdate();

            ResultSet generatedKeys = prepareStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                item.setId(generatedKeys.getInt(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            terminateConnection();
        }
        return item;
    }

    public Item getItem(int itemId) {
        createConnection();
        Item item = null;
        try {
            String sql = "SELECT i.*,  c.title as category FROM servlet.categories c INNER JOIN servlet.items i ON c.id = i.category_id WHERE i.id = ?";
            PreparedStatement prepareStatement = getConnection().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            prepareStatement.setInt(1, itemId);
            ResultSet resultSet = prepareStatement.executeQuery();

            while (resultSet.next()) {
                int id = resultSet.getInt(1);
                String title = resultSet.getString(2);
                String description = resultSet.getString(3);
                double price = resultSet.getDouble(4);
                int categoryId = resultSet.getInt(5);
                String category = resultSet.getString(6);
                item = new Item(id, title, description, price, new Category(categoryId, category));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            terminateConnection();
        }
        return item;
    }

    public List<Item> getAllItems() {
        createConnection();
        List<Item> items = new ArrayList<>();
        try {
            String sql = "SELECT i.*,  c.title as category FROM servlet.categories c INNER JOIN servlet.items i ON c.id = i.category_id";
            Statement statement = getConnection().createStatement();
            ResultSet resultSet = statement.executeQuery(sql);

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
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            terminateConnection();
        }
        return items;
    }

    public void addImages(HttpServletRequest request, int itemId) throws ServletException, IOException {
        ArrayList<String> files = writeFiles(request);
        if (files.size() == 0) {
            return;
        }
        createConnection();
        try {
            QueryBuilder queryBuilder = new QueryBuilder(getConnection());
            PreparedStatement ps = queryBuilder.insert("images", "title", "item_id").build();
            for (String file : files) {
                ps.setString(1, file);
                ps.setInt(2, itemId);
                ps.addBatch();
                ps.clearParameters();
            }
            ps.executeBatch();
        } catch (SQLException s) {
            s.printStackTrace();
        } finally {
            terminateConnection();
        }
    }

    public List<Image> getImages(int itemId) {
        createConnection();
        List<Image> images = new ArrayList<>();
        try {
            QueryBuilder queryBuilder = new QueryBuilder(getConnection());
            PreparedStatement prepareStatement = queryBuilder
                    .select("*")
                    .from("images")
                    .where("item_id", "=")
                    .build();
            prepareStatement.setInt(1, itemId);
            ResultSet resultSet = prepareStatement.executeQuery();

            while (resultSet.next()) {
                images.add(new Image(resultSet.getInt(1), resultSet.getString(2)));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            terminateConnection();
        }
        return images;
    }

    public Image getSingleImage(int imageId) {
        createConnection();
        Image image = null;
        try {
            QueryBuilder queryBuilder = new QueryBuilder(getConnection());
            PreparedStatement prepareStatement = queryBuilder
                    .select("*")
                    .from("images")
                    .where("id", "=")
                    .build();
            prepareStatement.setInt(1, imageId);
            ResultSet resultSet = prepareStatement.executeQuery();

            while (resultSet.next()) {
                image = new Image(resultSet.getInt(1), resultSet.getString(2));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            terminateConnection();
        }
        return image;
    }

    public boolean deleteItem(int itemId) {
        createConnection();
        try {
            QueryBuilder queryBuilder = new QueryBuilder(getConnection());
            PreparedStatement prepareStatement = queryBuilder
                    .delete()
                    .from("items")
                    .where("id", "=")
                    .build();
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

    public boolean deleteImage(int imageId) {
        createConnection();
        try {
            QueryBuilder queryBuilder = new QueryBuilder(getConnection());
            PreparedStatement prepareStatement = queryBuilder
                    .delete()
                    .from("images")
                    .where("id", "=")
                    .build();
            prepareStatement.setInt(1, imageId);
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
        List<Image> images = null;
        images = new ArrayList<>(getImages(itemId));
        for (Image image : images) {
            File file = new File(uploadFilePath + File.separator + image.getTitle());
            file.delete();
        }
        return true;
    }

    public boolean deleteFile(String uploadFilePath, int imageId) {
        Image image = getSingleImage(imageId);
        File file = new File(uploadFilePath + File.separator + image.getTitle());
        return file.delete();
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
}
