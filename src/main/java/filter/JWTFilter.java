package filter;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import utils.Constants;
import utils.HTTPUtils;
import utils.JWTUtils;

import java.io.IOException;

@WebFilter(filterName = "JWTFilter")
public class JWTFilter implements Filter {

    private final JWTUtils jwtUtils;

    public JWTFilter() {
        this.jwtUtils = new JWTUtils();
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        final String authHeader = request.getHeader("Authorization");
        final String jwtToken;

        String path = request.getRequestURI();
        boolean allowed = Constants.ALLOWED_PATHS.contains(path);
        if (allowed) {
            filterChain.doFilter(servletRequest, servletResponse);
            return;
        }

        if (authHeader == null || !authHeader.startsWith("Bearer")) {
            HTTPUtils.sendErrorResponse(response, 403, "Resource access denied");
            return;
        }

        jwtToken = authHeader.substring(7);

        if (!jwtUtils.isTokenValid(jwtToken)) {
            HTTPUtils.sendErrorResponse(response, 403, "Resource access denied");
            return;
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {
        Filter.super.destroy();
    }
}