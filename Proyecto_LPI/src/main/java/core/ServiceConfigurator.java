package core;

import connection.DbContext;
import repository.AuthorRepository;
import repository.BookCopyRepository;
import repository.BookCopyStatusRepository;
import repository.BookRepository;
import repository.BookStatusRepository;
import repository.CategoryRepository;
import repository.ConfigurationRepository;
import repository.CountryRepository;
import repository.IAuthorRepository;
import repository.IBookCopyRepository;
import repository.IBookCopyStatusRepository;
import repository.IBookRepository;
import repository.IBookStatusRepository;
import repository.ICategoryRepository;
import repository.IConfigurationRepository;
import repository.ICountryRepository;
import repository.IRentalRepository;
import repository.IRentalStatusRepository;
import repository.IRoleRepository;
import repository.IStatusRepository;
import repository.IUserRepository;
import repository.RentalRepository;
import repository.RentalStatusRepository;
import repository.RoleRepository;
import repository.StatusRepository;
import repository.UserRepository;
import service.AuthorService;
import service.BookCopyService;
import service.BookCopyStatusService;
import service.BookService;
import service.BookStatusService;
import service.CategoryService;
import service.ConfigurationService;
import service.CountryService;
import service.IAuthorService;
import service.IBookCopyService;
import service.IBookCopyStatusService;
import service.IBookService;
import service.IBookStatusService;
import service.ICategoryService;
import service.IConfigurationService;
import service.ICountryService;
import service.IRentalService;
import service.IRoleService;
import service.IStatusService;
import service.IUserService;
import service.RentalService;
import service.RoleService;
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
        container.addScoped(IRoleRepository.class, RoleRepository.class);
        container.addScoped(IConfigurationRepository.class, ConfigurationRepository.class);
        
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
        container.addScoped(IBookService.class, BookService.class);
        container.addScoped(IBookStatusService.class, BookStatusService.class);
        container.addScoped(ICategoryService.class, CategoryService.class);
        container.addScoped(IRoleService.class, RoleService.class);
        container.addScoped(IRentalService.class, RentalService.class);
        container.addScoped(IBookCopyService.class, BookCopyService.class);
        container.addScoped(IBookCopyStatusService.class, BookCopyStatusService.class);
        container.addScoped(IConfigurationService.class, ConfigurationService.class);

        System.out.println("✓ Servicios de negocio configurados");
    }
}