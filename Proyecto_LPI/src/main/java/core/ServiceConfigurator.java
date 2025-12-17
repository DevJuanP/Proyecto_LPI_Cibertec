package core;

import connection.DbContext;
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
import service.AuthorService;
import service.CountryService;
import service.IAuthorService;
import service.ICountryService;
import service.IStatusService;
import service.IUserService;
import service.StatusService;
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
        
        configureDataLayer(container);
        configureRepositories(container);
        configureBusinessServices(container);
        
        System.out.println("Servicios configurados correctamente\n");
    }

    /**
     * Configura la capa de acceso a datos
     */
    private static void configureDataLayer(ServiceContainer container) {
        System.out.println("  [Scoped] Capa de datos:");
        
        container.addScoped(DbContext.class, DbContext.class);
        
        System.out.println("    ✓ DbContext (conexión por request)");
    }
    
    /**
     * Configura todos los repositorios
     */
    private static void configureRepositories(ServiceContainer container) {
        container.addScoped(IUserRepository.class, UserRepository.class);
        container.addScoped(IAuthorRepository.class, AuthorRepository.class);
        container.addScoped(IBookCopyRepository.class, BookCopyRepository.class);
        container.addScoped(IBookCopyStatusRepository.class, BookCopyStatusRepository.class);
        container.addScoped(IBookRepository.class, BookRepository.class);
        container.addScoped(IBookStatusRepository.class, BookStatusRepository.class);
        container.addScoped(ICategoryRepository.class, CategoryRepository.class);
        container.addScoped(ICountryRepository.class, CountryRepository.class);
        container.addScoped(IRentalRepository.class, RentalRepository.class);
        container.addScoped(IRentalStatusRepository.class, RentalStatusRepository.class);
        container.addScoped(IStatusRepository.class, StatusRepository.class);
        
        System.out.println("✓ Repositorios configurados");
    }
    
    /**
     * Configura servicios de lógica de negocio
     */
    private static void configureBusinessServices(ServiceContainer container) {
        container.addScoped(IUserService.class, UserService.class);
        container.addScoped(IAuthorService.class, AuthorService.class);
        container.addScoped(ICountryService.class, CountryService.class);
        container.addScoped(IStatusService.class, StatusService.class);

        System.out.println("✓ Servicios de negocio configurados");
    }
}