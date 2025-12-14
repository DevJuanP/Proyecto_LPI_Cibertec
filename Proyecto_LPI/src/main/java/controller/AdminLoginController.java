package controller;

import java.io.IOException;
import java.nio.file.AccessDeniedException;

import core.BaseServlet;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.User;
import service.IUserService;
import util.SessionUtil;

/**
 * Controlador para el login del panel de administración.
 */
@WebServlet("/admin/login")
public class AdminLoginController extends BaseServlet {
    private static final long serialVersionUID = 1L;
    
    private static final String LOGIN_JSP = "/admin/panel/admin-login.jsp";
    private static final String ADMIN_PANEL = "/admin/panel";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        if (SessionUtil.isAuthenticated(request) && SessionUtil.isAdmin(request)) {
            response.sendRedirect(request.getContextPath() + ADMIN_PANEL);
            return;
        }
        
        request.getRequestDispatcher(LOGIN_JSP).forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String rememberMe = request.getParameter("rememberMe");

        if (email == null || email.trim().isEmpty() || 
            password == null || password.trim().isEmpty()) {
            request.setAttribute("error", "Por favor complete todos los campos");
            request.setAttribute("email", email);
            request.getRequestDispatcher(LOGIN_JSP).forward(request, response);
            return;
        }

        try {
            IUserService userService = getService(IUserService.class);
            User user = userService.adminAuthenticate(email, password);

            if (user != null) {
                boolean remember = "on".equals(rememberMe);
                SessionUtil.createUserSession(request, user, remember);
                
                response.sendRedirect(request.getContextPath() + ADMIN_PANEL);
            } else {
                request.setAttribute("error", "Email o contraseña incorrectos");
                request.setAttribute("email", email);
                request.getRequestDispatcher(LOGIN_JSP).forward(request, response);
            }
        } catch (IllegalStateException e) {
            request.setAttribute("error", e.getMessage());
            request.setAttribute("email", email);
            request.getRequestDispatcher(LOGIN_JSP).forward(request, response);
        } catch (AccessDeniedException e) {
            request.setAttribute("error", "Acceso denegado: No tiene permisos de administrador");
            request.setAttribute("email", email);
            request.getRequestDispatcher(LOGIN_JSP).forward(request, response);
        } catch (Exception e) {
            System.err.println("Error en login de administrador: " + e.getMessage());
            e.printStackTrace();
            request.setAttribute("error", "Error al procesar el login. Intente nuevamente.");
            request.setAttribute("email", email);
            request.getRequestDispatcher(LOGIN_JSP).forward(request, response);
        }
    }
}