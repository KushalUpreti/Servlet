package controller;


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
        switch (requestType) {
            case "Login" -> System.out.println("HII");
            case "Register" -> System.out.println("Hello");
        }
    }
}
