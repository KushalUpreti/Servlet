package controller;


import com.google.gson.Gson;
import dto.AuthResponseDTO;
import dto.ErrorResponseDTO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import service.AuthService;

import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/auth")
public class AuthController extends HttpServlet {
    private final AuthService authService;

    public AuthController() {
        authService = new AuthService();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String studentID = request.getParameter("id");
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        out.println("<h3>Hello World From Servlet</h3>");
    }

    @Override
    protected void doPost(
            HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String requestType = request.getParameter("type");
        if (requestType.equals("login")) {
            try {
                AuthResponseDTO authResponse = authService.login(request);
                response.setContentType("application/json");
                response.setCharacterEncoding("UTF-8");
                response.getWriter().print(new Gson().toJson(authResponse));

            } catch (ServletException s) {
                sendErrorResponse(response, 404, s.getMessage());
            }
        } else {
            try {
                authService.register(request);
            } catch (ServletException s) {
                sendErrorResponse(response, 401, s.getMessage());
            }
        }
    }

    private void sendErrorResponse(HttpServletResponse response, int status, String message) throws IOException {
        ErrorResponseDTO err = new ErrorResponseDTO(status, message);
        response.setStatus(status);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().print(new Gson().toJson(err));
    }
}
