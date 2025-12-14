package filter;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.Role;
import model.User;

import java.io.IOException;
import java.util.List;

/**
 * Filtro que protege las páginas del panel de administración.
 * Verifica que el usuario tenga una sesión válida y rol de ADMIN.
 */
@WebFilter(urlPatterns = {"/admin/panel/*"})
public class AdminAuthFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        
        HttpSession session = httpRequest.getSession(false);
        
        boolean isAuthenticated = false;
        boolean isAdmin = false;
        
        if (session != null) {
            User user = (User) session.getAttribute("user");
            
            if (user != null) {
                isAuthenticated = true;
                
                List<Role> roles = user.getRoles();
                if (roles != null) {
                    isAdmin = roles.stream()
                        .anyMatch(role -> "Admin".equalsIgnoreCase(role.getRoleName()));
                }
            }
        }
        
        if (isAuthenticated && isAdmin) {
            chain.doFilter(request, response);
        } else {
            String contextPath = httpRequest.getContextPath();
            
            if (session != null && session.getAttribute("user") != null) {
                session.invalidate();
                httpResponse.sendRedirect(contextPath + "/admin/login?error=unauthorized");
            } else {
                httpResponse.sendRedirect(contextPath + "/admin/login");
            }
        }
    }

    @Override
    public void destroy() {
        
    }
}