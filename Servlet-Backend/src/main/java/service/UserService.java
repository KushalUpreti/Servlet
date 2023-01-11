package service;

import jakarta.servlet.ServletException;
import repository.UserRepository;
import util.Validation;

import java.sql.SQLException;
import java.util.List;

public class UserService {
    private final UserRepository userRepository;

    public UserService() {
        this.userRepository = new UserRepository();
    }

    public List<String> getRolesByEmail(String email) throws ServletException, SQLException {
        if (!Validation.isEmail(email)) {
            throw new ServletException("Email or password is invalid");
        }
        return userRepository.getUserRoles(email);
    }


}
