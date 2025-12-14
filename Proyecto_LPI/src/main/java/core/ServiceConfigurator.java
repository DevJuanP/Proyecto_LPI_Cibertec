package core;

import repository.IUserRepository;
import repository.UserRepository;
import service.IUserService;
import service.UserService;

/**
 * Configurador central de todos los servicios de la aplicación.
 * Aquí se registran todas las dependencias que serán inyectadas.
 */
public class ServiceConfigurator {
    
    /**
     * Configura todos los servicios de la aplicación
     */
    public static void configure(ServiceContainer container) {
        System.out.println("Configurando servicios...\n");
        
        // ========================================
        // CONFIGURACIÓN DE REPOSITORIOS
        // ========================================
        configureRepositories(container);
        
        // ========================================
        // CONFIGURACIÓN DE SERVICIOS DE NEGOCIO
        // ========================================
        configureBusinessServices(container);
        
        // ========================================
        // OTROS SERVICIOS (Email, Storage, etc.)
        // ========================================
        // configureUtilityServices(container);
        
        System.out.println("Servicios configurados correctamente\n");
    }
    
    /**
     * Configura todos los repositorios
     */
    private static void configureRepositories(ServiceContainer container) {
        // Los repositorios generalmente son singleton porque no mantienen estado
        container.addSingleton(IUserRepository.class, UserRepository.class);
        // container.addSingleton(LibroRepository.class, LibroRepository.class);
        // container.addSingleton(AutorRepository.class, AutorRepository.class);
        // container.addSingleton(AlquilerRepository.class, AlquilerRepository.class);
        
        System.out.println("✓ Repositorios configurados");
    }
    
    /**
     * Configura servicios de lógica de negocio
     */
    private static void configureBusinessServices(ServiceContainer container) {
        // Los servicios pueden ser singleton o transient según las necesidades
        
        // Singleton - una sola instancia compartida
        container.addSingleton(IUserService.class, UserService.class);
        // container.addSingleton(LibroService.class, LibroService.class);
        // container.addSingleton(AutorService.class, AutorService.class);
        // container.addSingleton(AlquilerService.class, AlquilerService.class);
        
        // Ejemplo de servicio Transient (nueva instancia cada vez)
        // container.addTransient(EmailService.class, EmailService.class);
        
        System.out.println("✓ Servicios de negocio configurados");
    }
}