package repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Map;

public class CategoryRepository {
    Connection connection;

    private void createConnection() {
        Map<String, String> env = System.getenv();
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection =
                    DriverManager.getConnection("jdbc:mysql://localhost:3306/servlet", "root", env.get("MYSQL_PASS"));
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
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
