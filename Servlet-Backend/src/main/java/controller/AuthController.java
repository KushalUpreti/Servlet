package controller;


import dto.AuthResponseDTO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import service.AuthService;
import util.HTTPUtils;

import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/auth")
public class AuthController extends HttpServlet {
    private final AuthService authService;

    public AuthController() {
        authService = new AuthService();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    @Override
    protected void doPost(
            HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String requestType = request.getParameter("type");
        if (requestType.equals("login")) {
            try {
                AuthResponseDTO authResponse = authService.login(request);
                HTTPUtils.sendResponse(response, authResponse);
            } catch (ServletException s) {
                HTTPUtils.sendErrorResponse(response, 404, s.getMessage());
            }
        } else {
            try {
                authService.register(request);
            } catch (ServletException | SQLException s) {
                HTTPUtils.sendErrorResponse(response, 401, s.getMessage());
            }
        }
    }
}
