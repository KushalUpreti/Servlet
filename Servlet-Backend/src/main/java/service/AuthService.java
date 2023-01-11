package service;

import com.google.gson.Gson;
import dto.AuthResponseDTO;
import dto.UserDTO;
import exception.ClientException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import repository.AuthRepository;
import util.HTTPUtils;
import util.JWTUtils;
import util.Validation;

import java.sql.SQLException;
import java.util.List;

// TODO: Login SQL Exception handling
public class AuthService {
    private final Gson gson;
    private final AuthRepository authRepository;
    private final UserService userService;
    private final JWTUtils jwtUtils;

    public AuthService() {
        this.gson = new Gson();
        this.authRepository = new AuthRepository();
        this.userService = new UserService();
        this.jwtUtils = new JWTUtils();
    }

    public void register(HttpServletRequest request) throws ServletException, ClientException {
        String requestBody = HTTPUtils.jsonParser(request);
        UserDTO userDTO = gson.fromJson(requestBody, UserDTO.class);
        if (!Validation.isEmail(userDTO.getEmail())
                || Validation.isEmpty(userDTO.getPassword())) {
            throw new ServletException("Email or password cannot be empty.");
        }
        try {
            authRepository.register(userDTO);
        } catch (SQLException s) {
            throw new ClientException(403, s.getMessage());
        }

    }

    public AuthResponseDTO login(HttpServletRequest request) throws ServletException, ClientException {
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
            throw new ClientException(401, "Invalid credentials. Try again");
        }
        List<String> roles = null;
        try {
            roles = userService.getRolesByEmail(userDTO.getEmail());
        } catch (SQLException s) {
            s.printStackTrace();
        }
        String token = jwtUtils.generateToken(user.getEmail());

        return new AuthResponseDTO(user, token, roles);
    }
}
