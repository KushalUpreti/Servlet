package repository;

import dto.User;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Map;

public class AuthRepository {
    Connection connection;
//    Crypto

    private void createConnection() {
        Map<String, String> env = System.getenv();
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection =
                    DriverManager.getConnection("jdbc:mysql://localhost:3306/servlet", "root", env.get("MYSQL_PASS"));
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println(e);
        }
    }

    public void register(User user) {
        try {
            createConnection();
            String sql = "Insert into users (full_name, address, contact_number, gender) VALUES (?, ?, ?, ?)";
            PreparedStatement prepareStatement = connection.prepareStatement(sql);
            prepareStatement.setString(1, user.getEmail());
            prepareStatement.setString(2, user.getPassword());
            int isInserted = prepareStatement.executeUpdate();
            if (isInserted == 1) {
                System.out.println("Record added successfully.");
            }
        }catch (SQLException e){

        }
        finally {
            terminateConnection();
        }
    }

    private void terminateConnection() {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException exception) {
                System.out.println(Arrays.toString(exception.getStackTrace()));
            }

        }
    }


}
