package core;

import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import core.ServiceContainer.ValidationResult;
import core.ServiceContainer.ContainerStatistics;

/**
 * Listener que se ejecuta al iniciar y detener la aplicación web.
 * Inicializa el contenedor de inyección de dependencias con validación completa.
 */
@WebListener
public class AppStartup implements ServletContextListener {
    
    private static final String CONTAINER_ATTRIBUTE = "ServiceContainer";
    
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        System.out.println("═════════════════════════════════════════════════");
        System.out.println("    Iniciando aplicación web...");
        System.out.println("═════════════════════════════════════════════════");
        
        ServiceContainer container = null;
        
        try {
            // 1. Crear el contenedor de servicios
            container = new ServiceContainer();
            
            // 2. Configurar todos los servicios
            System.out.println("\n[1/3] Configurando servicios...");
            ServiceConfigurator.configure(container);
            
            // 3. VALIDAR la configuración (CRÍTICO - detecta errores antes de runtime!)
            System.out.println("[2/3] Validando configuración del contenedor...");
            ValidationResult validation = container.validate();
            
            // Mostrar warnings (como captive dependencies)
            if (!validation.warnings.isEmpty()) {
                System.out.println("\n⚠️  ADVERTENCIAS DE CONFIGURACIÓN:");
                validation.warnings.forEach(warning -> 
                    System.out.println("  ⚠️  " + warning));
                System.out.println();
            }
            
            // Si hay errores, detener el arranque
            if (!validation.isValid()) {
                System.err.println("\n❌ ERRORES CRÍTICOS DE CONFIGURACIÓN:");
                validation.errors.forEach(error -> 
                    System.err.println("  ❌ " + error));
                throw new RuntimeException(
                    "La configuración del contenedor DI tiene errores. " +
                    "Revisa las dependencias faltantes o mal configuradas.");
            }
            
            System.out.println("✓ Validación completada exitosamente");
            
            // 4. Guardar el contenedor en el contexto de la aplicación
            sce.getServletContext().setAttribute(CONTAINER_ATTRIBUTE, container);
            
            // 5. Mostrar información del contenedor
            System.out.println("\n[3/3] Contenedor inicializado:");
            ContainerStatistics stats = container.getStatistics();
            System.out.println("  • " + stats);
            
            // Opcional: imprimir todos los servicios registrados (útil en desarrollo)
            if (isDevelopmentMode()) {
                System.out.println();
                container.printRegisteredServices();
            }
            
            System.out.println("\n✓ Aplicación iniciada correctamente");
            System.out.println("═════════════════════════════════════════════════\n");
            
        } catch (Exception e) {
            System.err.println("\n❌ ERROR FATAL: No se pudo iniciar la aplicación");
            System.err.println("═════════════════════════════════════════════════");
            e.printStackTrace();
            
            // Limpiar si algo salió mal
            if (container != null) {
                try {
                    container.close();
                } catch (Exception ex) {
                    System.err.println("Error adicional al limpiar el contenedor: " + ex.getMessage());
                }
            }
            
            throw new RuntimeException("Fallo en la inicialización de la aplicación", e);
        }
    }
    
    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        System.out.println("\n═════════════════════════════════════════════════");
        System.out.println("    Deteniendo aplicación web...");
        System.out.println("═════════════════════════════════════════════════");
        
        try {
            ServiceContainer container = (ServiceContainer) sce.getServletContext()
                .getAttribute(CONTAINER_ATTRIBUTE);
            
            if (container != null) {
                // Cerrar el contenedor (dispone todos los singletons que implementan AutoCloseable)
                System.out.println("Liberando recursos del contenedor...");
                container.close();
                System.out.println("✓ Contenedor cerrado correctamente");
            }
            
            sce.getServletContext().removeAttribute(CONTAINER_ATTRIBUTE);
            
            System.out.println("✓ Aplicación detenida correctamente");
            
        } catch (Exception e) {
            System.err.println("❌ Error al detener la aplicación: " + e.getMessage());
            e.printStackTrace();
        }
        
        System.out.println("═════════════════════════════════════════════════\n");
    }
    
    /**
     * Método estático para obtener el container desde cualquier lugar de la aplicación
     */
    public static ServiceContainer getContainer(jakarta.servlet.ServletContext context) {
        if (context == null) {
            throw new IllegalArgumentException("ServletContext no puede ser null");
        }
        
        ServiceContainer container = (ServiceContainer) context.getAttribute(CONTAINER_ATTRIBUTE);
        
        if (container == null) {
            throw new IllegalStateException(
                "ServiceContainer no está inicializado. " +
                "¿La aplicación se inició correctamente?");
        }
        
        return container;
    }
    
    /**
     * Verifica si la aplicación está en modo desarrollo
     * (puedes configurar esto con una variable de entorno o propiedad del sistema)
     */
    private boolean isDevelopmentMode() {
        String mode = System.getProperty("app.mode", "production");
        return "development".equalsIgnoreCase(mode) || "dev".equalsIgnoreCase(mode);
    }
}