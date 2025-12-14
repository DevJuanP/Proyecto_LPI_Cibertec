package controller;

import java.io.IOException;
import java.nio.file.AccessDeniedException;

import core.BaseServlet;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.User;
import service.IUserService;

@WebServlet("/admin/login")
public class AdminLoginController extends BaseServlet {
    private static final long serialVersionUID = 1L;
    
    private static final String LOGIN_JSP = "/admin/panel/admin-login.jsp";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
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
                HttpSession session = request.getSession();
                session.setAttribute("user", user);
                session.setAttribute("userId", user.getUserId());
                session.setAttribute("userEmail", user.getEmail());

                if ("on".equals(rememberMe)) {
                    session.setMaxInactiveInterval(30 * 24 * 60 * 60);
                } else {
                    session.setMaxInactiveInterval(2 * 60 * 60);
                }
                
                response.sendRedirect(request.getContextPath() + "/admin/panel");
            } else {
                request.setAttribute("error", "Email o contraseña incorrectos");
                request.setAttribute("email", email);
                request.getRequestDispatcher(LOGIN_JSP).forward(request, response);
            }
        } catch (IllegalStateException | AccessDeniedException e) {
            request.setAttribute("error", e.getMessage());
            request.setAttribute("email", email);
            request.getRequestDispatcher(LOGIN_JSP).forward(request, response);            
        } catch (Exception e) {
            System.err.println("Error en login: " + e.getMessage());
            e.printStackTrace();
            request.setAttribute("error", "Error al procesar el login. Intente nuevamente.");
            request.setAttribute("email", email);
            request.getRequestDispatcher(LOGIN_JSP).forward(request, response);
        }
    }
}