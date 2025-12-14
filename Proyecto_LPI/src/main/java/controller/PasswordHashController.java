package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import util.PasswordUtil;

import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/api/hash-password")
public class PasswordHashController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        
        PrintWriter out = response.getWriter();
        
        try {
            String password = request.getParameter("password");
            
            if (password == null || password.trim().isEmpty()) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                out.print("{\"error\": \"El parámetro 'password' es requerido\"}");
                out.flush();
                return;
            }
            
            String hashedPassword = PasswordUtil.hashPassword(password);
            
            response.setStatus(HttpServletResponse.SC_OK);
            out.print("{");
            out.print("\"password\": \"" + escapeJson(password) + "\",");
            out.print("\"hash\": \"" + escapeJson(hashedPassword) + "\",");
            out.print("\"algorithm\": \"BCrypt\",");
            out.print("\"cost\": 12");
            out.print("}");
            out.flush();
            
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            out.print("{\"error\": \"Error al generar el hash: " + escapeJson(e.getMessage()) + "\"}");
            out.flush();
        }
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.setStatus(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
        
        PrintWriter out = response.getWriter();
        out.print("{");
        out.print("\"error\": \"Método no permitido. Use POST\",");
        out.print("\"usage\": \"POST /api/hash-password con parámetro 'password'\",");
        out.print("\"example\": \"curl -X POST http://localhost:8080/tu-app/api/hash-password -d 'password=MiPassword123'\"");
        out.print("}");
        out.flush();
    }
    
    /**
     * Escapa caracteres especiales para JSON
     */
    private String escapeJson(String str) {
        if (str == null) {
            return "";
        }
        return str.replace("\\", "\\\\")
                  .replace("\"", "\\\"")
                  .replace("\n", "\\n")
                  .replace("\r", "\\r")
                  .replace("\t", "\\t");
    }
}