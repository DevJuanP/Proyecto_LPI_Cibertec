package controller;

import java.io.IOException;
import java.io.PrintWriter;

import core.BaseServlet;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.User;
import service.IStatusService;
import service.IUserService;
import util.SessionUtil;

/**
 * Controlador para la gestión de usuarios.
 * Maneja operaciones CRUD y asignación de roles.
 */
@WebServlet("/user")
public class UserController extends BaseServlet {
    private static final String ADMIN_PANEL = "/admin/panel?page=mantenimiento-usuarios";

    @Override
    protected void doGetScoped(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        if (!SessionUtil.isAuthenticated(request) || !SessionUtil.isAdmin(request)) {
            response.sendRedirect(request.getContextPath() + LOGIN_PAGE);
            return;
        }
        
        String action = request.getParameter("action");
        
        if (action == null) {
            response.sendRedirect(request.getContextPath() + ADMIN_PANEL);
            return;
        }
        
        try {
            switch (action) {
                case "view":
                    handleViewUser(request, response);
                    break;
                case "edit":
                    handleEditUserForm(request, response);
                    break;
                default:
                    response.sendRedirect(request.getContextPath() + ADMIN_PANEL);
            }
        } catch (Exception e) {
            System.err.println("Error en UserController GET: " + e.getMessage());
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error procesando la solicitud");
        }
    }

    @Override
    protected void doPostScoped(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        if (!SessionUtil.isAuthenticated(request) || !SessionUtil.isAdmin(request)) {
            response.sendRedirect(request.getContextPath() + LOGIN_PAGE);
            return;
        }
        
        String action = request.getParameter("action");
        
        if (action == null) {
            response.sendRedirect(request.getContextPath() + ADMIN_PANEL);
            return;
        }
        
        try {
            switch (action) {
                case "create":
                    handleCreateUser(request, response);
                    break;
                case "update":
                    handleUpdateUser(request, response);
                    break;
                case "delete":
                    handleDeleteUser(request, response);
                    break;
                case "changePassword":
                    handleChangePassword(request, response);
                    break;
                case "assignRole":
                    handleAssignRole(request, response);
                    break;
                case "removeRole":
                    handleRemoveRole(request, response);
                    break;
                default:
                    response.sendRedirect(request.getContextPath() + ADMIN_PANEL);
            }
        } catch (Exception e) {
            System.err.println("Error en UserController POST: " + e.getMessage());
            e.printStackTrace();
            request.getSession().setAttribute("error", "Error: " + e.getMessage());
            response.sendRedirect(request.getContextPath() + ADMIN_PANEL);
        }
    }

    /**
     * Muestra los detalles de un usuario (respuesta HTML parcial para modal).
     */
    private void handleViewUser(HttpServletRequest request, HttpServletResponse response) 
            throws Exception {
        
        String userId = request.getParameter("id");
        
        if (userId == null || userId.isEmpty()) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "ID de usuario requerido");
            return;
        }
        
        IUserService userService = getService(IUserService.class);
        User user = userService.getUserById(userId);
        
        if (user == null) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "Usuario no encontrado");
            return;
        }
        
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        
        out.println("<div class=\"row\">");
        out.println("  <div class=\"col-md-4 text-center\">");
        out.println("    <i class=\"bi bi-person-circle\" style=\"font-size: 8rem; color: #3498db;\"></i>");
        out.println("    <h4 class=\"mt-3\">" + escapeHtml(user.getEmail()) + "</h4>");
        
        if (user.getStatus() != null) {
            String statusBadge = "Active".equals(user.getStatus().getStatusName()) 
                                ? "<span class=\"badge bg-success\">Activo</span>" 
                                : "<span class=\"badge bg-secondary\">Inactivo</span>";
            out.println("    " + statusBadge);
        }
        
        out.println("  </div>");
        out.println("  <div class=\"col-md-8\">");
        out.println("    <table class=\"table table-borderless\">");
        
        out.println("      <tr>");
        out.println("        <th style=\"width: 40%\">ID:</th>");
        out.println("        <td><code class=\"small\">" + escapeHtml(user.getUserId()) + "</code></td>");
        out.println("      </tr>");
        
        out.println("      <tr>");
        out.println("        <th>Email:</th>");
        out.println("        <td>" + escapeHtml(user.getEmail()) + "</td>");
        out.println("      </tr>");
        
        out.println("      <tr>");
        out.println("        <th>Roles:</th>");
        out.println("        <td>");
        if (user.getRoles() != null && !user.getRoles().isEmpty()) {
            user.getRoles().forEach(role -> 
                out.println("<span class=\"badge bg-primary me-1\">" + escapeHtml(role.getRoleName()) + "</span>")
            );
        } else {
            out.println("<span class=\"text-muted\">Sin roles asignados</span>");
        }
        out.println("        </td>");
        out.println("      </tr>");
        
        out.println("      <tr>");
        out.println("        <th>Registrado:</th>");
        out.println("        <td>" + user.getCreatedAt() + "</td>");
        out.println("      </tr>");
        
        out.println("      <tr>");
        out.println("        <th>Última actualización:</th>");
        out.println("        <td>" + user.getUpdatedAt() + "</td>");
        out.println("      </tr>");
        
        out.println("    </table>");
        out.println("  </div>");
        out.println("</div>");
        
        out.println("<div class=\"text-end mt-3\">");
        out.println("  <button type=\"button\" class=\"btn btn-secondary\" data-bs-dismiss=\"modal\">Cerrar</button>");
        out.println("  <button type=\"button\" class=\"btn btn-warning\" onclick=\"editUser('" + user.getUserId() + "')\">");
        out.println("    <i class=\"bi bi-pencil\"></i> Editar");
        out.println("  </button>");
        out.println("</div>");
    }

    /**
     * Muestra el formulario de edición de un usuario.
     */
    private void handleEditUserForm(HttpServletRequest request, HttpServletResponse response) 
            throws Exception {
        
        String userId = request.getParameter("id");
        
        if (userId == null || userId.isEmpty()) {
            response.sendRedirect(request.getContextPath() + ADMIN_PANEL);
            return;
        }
        
        IUserService userService = getService(IUserService.class);
        IStatusService statusService = getService(IStatusService.class);
        
        User user = userService.getUserById(userId);
        
        if (user == null) {
            request.getSession().setAttribute("error", "Usuario no encontrado");
            response.sendRedirect(request.getContextPath() + ADMIN_PANEL);
            return;
        }
        
        request.setAttribute("user", user);
        request.setAttribute("statuses", statusService.findAll());
        request.getRequestDispatcher("/admin/panel/items/edit-usuario.jsp").forward(request, response);
    }

    /**
     * Crea un nuevo usuario.
     */
    private void handleCreateUser(HttpServletRequest request, HttpServletResponse response) 
            throws Exception {
        
        IUserService userService = getService(IUserService.class);
        IStatusService statusService = getService(IStatusService.class);
        
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        
        if (email == null || email.trim().isEmpty() || password == null || password.trim().isEmpty()) {
            request.getSession().setAttribute("error", "Email y contraseña son requeridos");
            response.sendRedirect(request.getContextPath() + ADMIN_PANEL);
            return;
        }
        
        String activeStatusId = statusService.getActiveStatusId();
        
        userService.register(email, password, activeStatusId);
        
        request.getSession().setAttribute("success", "Usuario creado exitosamente");
        response.sendRedirect(request.getContextPath() + ADMIN_PANEL);
    }

    /**
     * Actualiza un usuario existente.
     */
    private void handleUpdateUser(HttpServletRequest request, HttpServletResponse response) 
            throws Exception {
        
        String userId = request.getParameter("userId");
        
        if (userId == null || userId.isEmpty()) {
            request.getSession().setAttribute("error", "ID de usuario requerido");
            response.sendRedirect(request.getContextPath() + ADMIN_PANEL);
            return;
        }
        
        IUserService userService = getService(IUserService.class);
        IStatusService statusService = getService(IStatusService.class);
        
        User user = userService.getUserById(userId);
        
        if (user == null) {
            request.getSession().setAttribute("error", "Usuario no encontrado");
            response.sendRedirect(request.getContextPath() + ADMIN_PANEL);
            return;
        }
        
        String email = request.getParameter("email");
        if (email != null && !email.trim().isEmpty()) {
            user.setEmail(email);
        }
        
        String statusName = request.getParameter("statusId");
        if (statusName != null && !statusName.isEmpty()) {
            if ("active".equalsIgnoreCase(statusName)) {
                user.setStatusId(statusService.getActiveStatusId());
            } else if ("inactive".equalsIgnoreCase(statusName)) {
                user.setStatusId(statusService.getInactiveStatusId());
            }
        }
        
        userService.updateUser(user);
        
        request.getSession().setAttribute("success", "Usuario actualizado exitosamente");
        response.sendRedirect(request.getContextPath() + ADMIN_PANEL);
    }

    /**
     * Elimina un usuario.
     */
    private void handleDeleteUser(HttpServletRequest request, HttpServletResponse response) 
            throws Exception {
        
        String userId = request.getParameter("userId");
        
        if (userId == null || userId.isEmpty()) {
            request.getSession().setAttribute("error", "ID de usuario requerido");
            response.sendRedirect(request.getContextPath() + ADMIN_PANEL);
            return;
        }
        
        // No permitir que el usuario se elimine a sí mismo
        String currentUserId = SessionUtil.getCurrentUserId(request);
        if (userId.equals(currentUserId)) {
            request.getSession().setAttribute("error", "No puedes eliminar tu propia cuenta");
            response.sendRedirect(request.getContextPath() + ADMIN_PANEL);
            return;
        }
        
        IUserService userService = getService(IUserService.class);
        userService.deleteUser(userId);
        
        request.getSession().setAttribute("success", "Usuario eliminado exitosamente");
        response.sendRedirect(request.getContextPath() + ADMIN_PANEL);
    }

    /**
     * Cambia la contraseña de un usuario.
     */
    private void handleChangePassword(HttpServletRequest request, HttpServletResponse response) 
            throws Exception {
        
        String userId = request.getParameter("userId");
        String newPassword = request.getParameter("newPassword");
        
        if (userId == null || userId.isEmpty() || newPassword == null || newPassword.trim().isEmpty()) {
            request.getSession().setAttribute("error", "Datos incompletos");
            response.sendRedirect(request.getContextPath() + "/admin/panel?page=edit-usuario&id=" + userId);
            return;
        }
        
        IUserService userService = getService(IUserService.class);
        User user = userService.getUserById(userId);
        
        if (user == null) {
            request.getSession().setAttribute("error", "Usuario no encontrado");
            response.sendRedirect(request.getContextPath() + ADMIN_PANEL);
            return;
        }
        
        // Actualizar contraseña directamente (como admin)
        user.setPassword(util.PasswordUtil.hashPassword(newPassword));
        userService.updateUser(user);
        
        request.getSession().setAttribute("success", "Contraseña actualizada exitosamente");
        response.sendRedirect(request.getContextPath() + "/admin/panel?page=edit-usuario&id=" + userId);
    }

    /**
     * Asigna un rol a un usuario.
     */
    private void handleAssignRole(HttpServletRequest request, HttpServletResponse response) 
            throws Exception {
        
        String userId = request.getParameter("userId");
        String roleId = request.getParameter("roleId");
        
        if (userId == null || userId.isEmpty() || roleId == null || roleId.isEmpty()) {
            request.getSession().setAttribute("error", "Datos incompletos");
            response.sendRedirect(request.getContextPath() + ADMIN_PANEL);
            return;
        }
        
        IUserService userService = getService(IUserService.class);
        userService.assignRole(userId, roleId);
        
        request.getSession().setAttribute("success", "Rol asignado exitosamente");
        response.sendRedirect(request.getContextPath() + "/admin/panel?page=edit-usuario&id=" + userId);
    }

    /**
     * Remueve un rol de un usuario.
     */
    private void handleRemoveRole(HttpServletRequest request, HttpServletResponse response) 
            throws Exception {
        
        String userId = request.getParameter("userId");
        String roleId = request.getParameter("roleId");
        
        if (userId == null || userId.isEmpty() || roleId == null || roleId.isEmpty()) {
            request.getSession().setAttribute("error", "Datos incompletos");
            response.sendRedirect(request.getContextPath() + ADMIN_PANEL);
            return;
        }
        
        IUserService userService = getService(IUserService.class);
        userService.removeRole(userId, roleId);
        
        request.getSession().setAttribute("success", "Rol removido exitosamente");
        response.sendRedirect(request.getContextPath() + "/admin/panel?page=edit-usuario&id=" + userId);
    }
}