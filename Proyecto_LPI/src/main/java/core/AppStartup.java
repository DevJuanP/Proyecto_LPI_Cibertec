package core;

import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;

/**
 * Listener que se ejecuta al iniciar y detener la aplicación web.
 * Inicializa el contenedor de inyección de dependencias.
 */
@WebListener
public class AppStartup implements ServletContextListener {
    
    private static final String CONTAINER_ATTRIBUTE = "ServiceContainer";
    
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        System.out.println("=================================");
        System.out.println("Iniciando aplicación web...");
        System.out.println("=================================");
        
        try {
            // Crear el contenedor de servicios
            ServiceContainer container = new ServiceContainer();
            
            // Configurar todos los servicios
            ServiceConfigurator.configure(container);
            
            // Guardar el contenedor en el contexto de la aplicación
            sce.getServletContext().setAttribute(CONTAINER_ATTRIBUTE, container);
            
            // Imprimir servicios registrados para debugging
            container.printRegisteredServices();
            
            System.out.println("Aplicación iniciada correctamente");
            System.out.println("Total de servicios: " + container.getServiceCount());
            System.out.println("=================================\n");
            
        } catch (Exception e) {
            System.err.println("ERROR: No se pudo iniciar la aplicación");
            e.printStackTrace();
            throw new RuntimeException("Fallo en la inicialización de la aplicación", e);
        }
    }
    
    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        System.out.println("\n=================================");
        System.out.println("Deteniendo aplicación web...");
        
        try {
            ServiceContainer container = (ServiceContainer) sce.getServletContext()
                .getAttribute(CONTAINER_ATTRIBUTE);
            
            if (container != null) {
                // Limpiar recursos si es necesario
                container.clearSingletons();
            }
            
            sce.getServletContext().removeAttribute(CONTAINER_ATTRIBUTE);
            
            System.out.println("Aplicación detenida correctamente");
        } catch (Exception e) {
            System.err.println("Error al detener la aplicación: " + e.getMessage());
        }
        
        System.out.println("=================================");
    }
    
    /**
     * Método estático para obtener el container desde cualquier lugar de la aplicación
     */
    public static ServiceContainer getContainer(jakarta.servlet.ServletContext context) {
        ServiceContainer container = (ServiceContainer) context.getAttribute(CONTAINER_ATTRIBUTE);
        if (container == null) {
            throw new IllegalStateException("ServiceContainer no está inicializado");
        }
        return container;
    }
}