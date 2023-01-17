package filter;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import service.UserService;
import util.HTTPUtils;
import util.JWTUtils;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebFilter(filterName = "RoleFilter")
public class RoleFilter implements Filter {

    private final JWTUtils jwtUtils;
    private final UserService userService;
    private final Map<String, String[]> roleMap;

    public RoleFilter() {
        this.jwtUtils = new JWTUtils();
        this.userService = new UserService();

        roleMap = new HashMap<>();
        roleMap.put("^/admin/.*$", new String[]{"ADMIN"});
        roleMap.put("/category", new String[]{"SUPER_ADMIN"});
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        String path = request.getRequestURI();
        boolean allowed = HTTPUtils.isUrlAllowed(path);
        if (allowed) {
            filterChain.doFilter(servletRequest, servletResponse);
            return;
        }

        final String jwtToken = (String) servletRequest.getAttribute("token");
        String email = jwtUtils.extractEmail(jwtToken);
        List<String> roles = null;
        roles = userService.getRolesByEmail(email);
        if (roles.size() == 0) {
            HTTPUtils.sendErrorResponse(response, 418, "No privileges assigned");
            return;
        }

        for (Map.Entry<String, String[]> entry : roleMap.entrySet()) {
            String pattern = entry.getKey();
            if (path.matches(pattern)) {
                String[] apiRoles = entry.getValue(); // One of the role should be in within a user
                for (String apiRole : apiRoles) {
                    if (roles.contains(apiRole)) {
                        filterChain.doFilter(servletRequest, servletResponse);
                        return;
                    }
                }
                HTTPUtils.sendErrorResponse(response, 403, "Insufficient privileges to perform this action");
                return;
            }
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
    }

    @Override
    public void destroy() {
        Filter.super.destroy();
    }
}
