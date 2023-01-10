package service;

import com.google.gson.Gson;
import dto.AuthResponseDTO;
import dto.UserDTO;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import repository.AuthRepository;
import util.HTTPUtils;
import util.JWTUtils;
import util.Validation;

import java.sql.SQLException;

public class AuthService {
    private final Gson gson;
    private final AuthRepository authRepository;
    private final JWTUtils jwtUtils;

    public AuthService() {
        this.gson = new Gson();
        this.authRepository = new AuthRepository();
        this.jwtUtils = new JWTUtils();
    }

    public void register(HttpServletRequest request) throws ServletException, SQLException {
        String requestBody = HTTPUtils.jsonParser(request);
        UserDTO userDTO = gson.fromJson(requestBody, UserDTO.class);
        if (!Validation.isEmail(userDTO.getEmail())
                || Validation.isEmpty(userDTO.getPassword())) {
            throw new ServletException("Email or password cannot be empty.");
        }
        authRepository.register(userDTO);
    }

    public AuthResponseDTO login(HttpServletRequest request) throws ServletException {
        String requestBody = HTTPUtils.jsonParser(request);
        UserDTO userDTO = gson.fromJson(requestBody, UserDTO.class);
        String password = userDTO.getPassword().trim();
        if (!Validation.isEmail(userDTO.getEmail())
                || Validation.isEmpty(password)) {
            throw new ServletException("Email or password is invalid");
        }
        UserDTO user = authRepository.login(userDTO);
        if (user == null) {
            throw new ServletException("User not found");
        }
        boolean passwordMatch = HTTPUtils.checkPasswordMatch(password, user.getPassword());
        if (!passwordMatch) {
            throw new ServletException("Invalid credentials. Try again!");
        }
        String token = jwtUtils.generateToken(user.getEmail());
        return new AuthResponseDTO(user, token);
    }
}
