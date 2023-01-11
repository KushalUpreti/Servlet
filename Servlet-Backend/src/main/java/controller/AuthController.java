package controller;


import dto.AuthResponseDTO;
import exception.ClientException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import service.AuthService;
import util.HTTPUtils;

import java.io.IOException;

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
            } catch (ClientException c) {
                HTTPUtils.sendErrorResponse(response, c.getStatusCode(), c.getMessage());
            }
        } else {
            try {
                authService.register(request);
            } catch (ClientException c) {
                HTTPUtils.sendErrorResponse(response, c.getStatusCode(), c.getMessage());
            }
        }
    }
}
