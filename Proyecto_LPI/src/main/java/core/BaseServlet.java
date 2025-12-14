package core;

import jakarta.servlet.http.HttpServlet;

/**
 * Clase base para todos los servlets de la aplicación.
 * Proporciona acceso fácil al contenedor de inyección de dependencias.
 */
public abstract class BaseServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    
    /**
     * Obtiene el contenedor de servicios
     */
    protected ServiceContainer getContainer() {
        return AppStartup.getContainer(getServletContext());
    }
    
    /**
     * Obtiene un servicio del contenedor por su tipo
     * 
     * @param serviceType Clase del servicio a obtener
     * @return Instancia del servicio
     */
    protected <T> T getService(Class<T> serviceType) {
        return getContainer().getService(serviceType);
    }
    
    /**
     * Verifica si el usuario tiene una sesión activa
     */
    protected boolean isAuthenticated() {
        return getSession().getAttribute("user") != null;
    }
    
    /**
     * Obtiene el usuario actual de la sesión
     */
    protected Object getCurrentUser() {
        return getSession().getAttribute("user");
    }
    
    /**
     * Helper para obtener la sesión actual
     */
    private jakarta.servlet.http.HttpSession getSession() {
        // Este método será llamado desde doGet/doPost donde tenemos acceso al request
        // En esos casos, la subclase puede pasar el request
        throw new UnsupportedOperationException(
            "Use getCurrentUser(HttpServletRequest request) en su lugar");
    }
    
    /**
     * Obtiene el usuario actual de la sesión (versión con request)
     */
    protected Object getCurrentUser(jakarta.servlet.http.HttpServletRequest request) {
        return request.getSession().getAttribute("user");
    }
    
    /**
     * Verifica si el usuario tiene una sesión activa (versión con request)
     */
    protected boolean isAuthenticated(jakarta.servlet.http.HttpServletRequest request) {
        return request.getSession().getAttribute("user") != null;
    }
    
    /**
     * Cierra la sesión del usuario
     */
    protected void logout(jakarta.servlet.http.HttpServletRequest request) {
        request.getSession().invalidate();
    }
    
    /**
     * Redirige a una página de error con un mensaje
     */
    protected void redirectToError(jakarta.servlet.http.HttpServletRequest request, 
                                   jakarta.servlet.http.HttpServletResponse response,
                                   String errorMessage) throws java.io.IOException {
        request.getSession().setAttribute("errorMessage", errorMessage);
        response.sendRedirect(request.getContextPath() + "/error.jsp");
    }
    
    /**
     * Redirige a una página con un mensaje de éxito
     */
    protected void redirectWithSuccess(jakarta.servlet.http.HttpServletRequest request,
                                       jakarta.servlet.http.HttpServletResponse response,
                                       String successMessage,
                                       String redirectUrl) throws java.io.IOException {
        request.getSession().setAttribute("successMessage", successMessage);
        response.sendRedirect(request.getContextPath() + redirectUrl);
    }
}