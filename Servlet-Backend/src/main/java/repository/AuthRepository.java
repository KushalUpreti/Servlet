package repository;

import db.DBConnection;
import db.QueryBuilder;
import model.User;
import util.HTTPUtils;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AuthRepository extends DBConnection {

    public void register(User user) {
        createConnection();
        try {
            QueryBuilder queryBuilder = new QueryBuilder(getConnection());
            PreparedStatement prepareStatement = queryBuilder
                    .insert("users", "email", "password")
                    .build();
            String passwordHash = HTTPUtils.encodePassword(user.getPassword());
            prepareStatement.setString(1, user.getEmail());
            prepareStatement.setString(2, passwordHash);
            prepareStatement.executeUpdate();
        } catch (SQLException s) {
            s.printStackTrace();
        } finally {
            terminateConnection();
        }
    }

    public User login(User userDTO) {
        createConnection();
        User user = null;
        try {
            QueryBuilder queryBuilder = new QueryBuilder(getConnection());
            PreparedStatement prepareStatement = queryBuilder
                    .select("*")
                    .from("users")
                    .where("email", "=")
                    .build();
            prepareStatement.setString(1, userDTO.getEmail());
            ResultSet resultSet = prepareStatement.executeQuery();
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String email = resultSet.getString("email");
                String password = resultSet.getString("password");
                user = new User(id, email, password);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            terminateConnection();
        }
        return user;
    }
}
