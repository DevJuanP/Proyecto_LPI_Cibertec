package core;

import repository.AuthorRepository;
import repository.BookCopyRepository;
import repository.BookCopyStatusRepository;
import repository.BookRepository;
import repository.BookStatusRepository;
import repository.CategoryRepository;
import repository.CountryRepository;
import repository.IAuthorRepository;
import repository.IBookCopyRepository;
import repository.IBookCopyStatusRepository;
import repository.IBookRepository;
import repository.IBookStatusRepository;
import repository.ICategoryRepository;
import repository.ICountryRepository;
import repository.IRentalRepository;
import repository.IRentalStatusRepository;
import repository.IStatusRepository;
import repository.IUserRepository;
import repository.RentalRepository;
import repository.RentalStatusRepository;
import repository.StatusRepository;
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
        container.addSingleton(IUserRepository.class, UserRepository.class);
        container.addSingleton(IAuthorRepository.class, AuthorRepository.class);
        container.addSingleton(IBookCopyRepository.class, BookCopyRepository.class);
        container.addSingleton(IBookCopyStatusRepository.class, BookCopyStatusRepository.class);
        container.addSingleton(IBookRepository.class, BookRepository.class);
        container.addSingleton(IBookStatusRepository.class, BookStatusRepository.class);
        container.addSingleton(ICategoryRepository.class, CategoryRepository.class);
        container.addSingleton(ICountryRepository.class, CountryRepository.class);
        container.addSingleton(IRentalRepository.class, RentalRepository.class);
        container.addSingleton(IRentalStatusRepository.class, RentalStatusRepository.class);
        container.addSingleton(IStatusRepository.class, StatusRepository.class);
        
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