package core;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Clase base mejorada para todos los servlets de la aplicación.
 */
public abstract class BaseServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    
    /**
     * Obtiene el contenedor de servicios
     */
    protected ServiceContainer getContainer() {
        return AppStartup.getContainer(getServletContext());
    }
    
    @Override
    protected final void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        ServiceContainer container = getContainer();
        
        try (ServiceContainer.ServiceScope scope = container.beginScope()) {
            doGetScoped(request, response);
        } catch (Exception e) {
            handleException(request, response, e);
        }
    }
    
    @Override
    protected final void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        ServiceContainer container = getContainer();
        
        try (ServiceContainer.ServiceScope scope = container.beginScope()) {
            doPostScoped(request, response);
        } catch (Exception e) {
            handleException(request, response, e);
        }
    }
    
    /**
     * Sobrescribe este método en lugar de doGet()
     * Se ejecuta automáticamente dentro de un scope activo
     */
    protected void doGetScoped(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        super.doGet(request, response);
    }
    
    /**
     * Sobrescribe este método en lugar de doPost()
     * Se ejecuta automáticamente dentro de un scope activo
     */
    protected void doPostScoped(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        super.doPost(request, response);
    }
    
    /**
     * Obtiene un servicio del contenedor
     * IMPORTANTE: Solo llamar desde doGetScoped/doPostScoped
     */
    protected <T> T getService(Class<T> serviceType) {
        return getContainer().getService(serviceType);
    }
    
    /**
     * Verifica si el usuario tiene una sesión activa
     */
    protected boolean isAuthenticated(HttpServletRequest request) {
        return request.getSession(false) != null && 
               request.getSession().getAttribute("user") != null;
    }
    
    /**
     * Obtiene el usuario actual de la sesión
     */
    protected Object getCurrentUser(HttpServletRequest request) {
        if (request.getSession(false) == null) {
            return null;
        }
        return request.getSession().getAttribute("user");
    }
    
    /**
     * Cierra la sesión del usuario
     */
    protected void logout(HttpServletRequest request) {
        if (request.getSession(false) != null) {
            request.getSession().invalidate();
        }
    }
    
    /**
     * Redirige a una página de error con un mensaje
     */
    protected void redirectToError(HttpServletRequest request, 
                                   HttpServletResponse response,
                                   String errorMessage) throws IOException {
        request.getSession().setAttribute("errorMessage", errorMessage);
        response.sendRedirect(request.getContextPath() + "/error.jsp");
    }
    
    /**
     * Redirige a una página con un mensaje de éxito
     */
    protected void redirectWithSuccess(HttpServletRequest request,
                                       HttpServletResponse response,
                                       String successMessage,
                                       String redirectUrl) throws IOException {
        request.getSession().setAttribute("successMessage", successMessage);
        response.sendRedirect(request.getContextPath() + redirectUrl);
    }
    
    /**
     * Maneja excepciones de forma centralizada
     * Las subclases pueden sobrescribir este método para personalizar el manejo de errores
     */
    protected void handleException(HttpServletRequest request, 
                                   HttpServletResponse response,
                                   Exception e) throws ServletException, IOException {
        System.err.println("Error en " + this.getClass().getSimpleName() + ": " + e.getMessage());
        e.printStackTrace();
        
        redirectToError(request, response, "Error del servidor: " + e.getMessage());
    }
}