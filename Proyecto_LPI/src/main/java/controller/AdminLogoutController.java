package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

/**
 * Controlador para cerrar la sesión del administrador.
 */
@WebServlet("/admin/logout")
public class AdminLogoutController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    
    private static final String LOGIN_PAGE = "/admin/login";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        performLogout(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        performLogout(request, response);
    }

    /**
     * Invalida la sesión actual y redirige al login con mensaje de éxito.
     */
    private void performLogout(HttpServletRequest request, HttpServletResponse response) 
            throws IOException {
        
        HttpSession session = request.getSession(false);
        
        if (session != null) {
            session.invalidate();
        }
        
        String contextPath = request.getContextPath();
        response.sendRedirect(contextPath + LOGIN_PAGE + "?logout=success");
    }
}