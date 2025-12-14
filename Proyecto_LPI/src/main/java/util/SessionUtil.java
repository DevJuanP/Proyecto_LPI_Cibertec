package util;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import model.User;

/**
 * Utilidad para manejo centralizado de sesiones de usuario.
 */
public class SessionUtil {
    
    public static final String USER_ATTR = "user";
    public static final String USER_ID_ATTR = "userId";
    public static final String USER_EMAIL_ATTR = "userEmail";
    public static final String USER_ROLES_ATTR = "userRoles";
    
    public static final int SESSION_TIMEOUT_DEFAULT = 2 * 60 * 60;
    public static final int SESSION_TIMEOUT_REMEMBER = 30 * 24 * 60 * 60;
    
    /**
     * Crea una nueva sesión para el usuario autenticado.
     * 
     * @param request La solicitud HTTP
     * @param user El usuario autenticado
     * @param rememberMe Si se debe recordar la sesión por 30 días
     */
    public static void createUserSession(HttpServletRequest request, User user, boolean rememberMe) {
        HttpSession session = request.getSession(true);
        
        session.setAttribute(USER_ATTR, user);
        session.setAttribute(USER_ID_ATTR, user.getUserId());
        session.setAttribute(USER_EMAIL_ATTR, user.getEmail());
        session.setAttribute(USER_ROLES_ATTR, user.getRoles());
        
        if (rememberMe) {
            session.setMaxInactiveInterval(SESSION_TIMEOUT_REMEMBER);
        } else {
            session.setMaxInactiveInterval(SESSION_TIMEOUT_DEFAULT);
        }
    }
    
    /**
     * Obtiene el usuario de la sesión actual.
     * 
     * @param request La solicitud HTTP
     * @return El usuario autenticado o null si no hay sesión
     */
    public static User getCurrentUser(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        
        if (session != null) {
            return (User) session.getAttribute(USER_ATTR);
        }
        
        return null;
    }
    
    /**
     * Obtiene el ID del usuario de la sesión actual.
     * 
     * @param request La solicitud HTTP
     * @return El ID del usuario o null si no hay sesión
     */
    public static String getCurrentUserId(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        
        if (session != null) {
            return (String) session.getAttribute(USER_ID_ATTR);
        }
        
        return null;
    }
    
    /**
     * Obtiene el email del usuario de la sesión actual.
     * 
     * @param request La solicitud HTTP
     * @return El email del usuario o null si no hay sesión
     */
    public static String getCurrentUserEmail(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        
        if (session != null) {
            return (String) session.getAttribute(USER_EMAIL_ATTR);
        }
        
        return null;
    }
    
    /**
     * Verifica si hay un usuario autenticado en la sesión.
     * 
     * @param request La solicitud HTTP
     * @return true si hay un usuario autenticado, false en caso contrario
     */
    public static boolean isAuthenticated(HttpServletRequest request) {
        return getCurrentUser(request) != null;
    }
    
    /**
     * Verifica si el usuario actual tiene un rol específico.
     * 
     * @param request La solicitud HTTP
     * @param roleName El nombre del rol a verificar
     * @return true si el usuario tiene el rol, false en caso contrario
     */
    public static boolean hasRole(HttpServletRequest request, String roleName) {
        User user = getCurrentUser(request);
        
        if (user == null || user.getRoles() == null) {
            return false;
        }
        
        return user.getRoles().stream()
            .anyMatch(role -> roleName.equalsIgnoreCase(role.getRoleName()));
    }
    
    /**
     * Verifica si el usuario actual tiene rol de administrador.
     * 
     * @param request La solicitud HTTP
     * @return true si el usuario es administrador, false en caso contrario
     */
    public static boolean isAdmin(HttpServletRequest request) {
        return hasRole(request, "Admin");
    }
    
    /**
     * Invalida la sesión actual del usuario.
     * 
     * @param request La solicitud HTTP
     */
    public static void invalidateSession(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        
        if (session != null) {
            session.invalidate();
        }
    }
}