package controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import core.BaseServlet;
import dto.author.AuthorData;
import dto.shared.PagedResult;
import dto.user.UserData;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Author;
import model.Book;
import model.BookCopy;
import model.BookCopyStatus;
import model.BookStatus;
import model.Category;
import model.Country;
import model.Role;
import model.Status;
import model.User;
import repository.IBookCopyStatusRepository;
import service.IAuthorService;
import service.IBookCopyService;
import service.IBookCopyStatusService;
import service.IBookService;
import service.IBookStatusService;
import service.ICategoryService;
import service.ICountryService;
import service.IRoleService;
import service.IStatusService;
import service.IUserService;
import util.SessionUtil;

/**
 * Controlador principal para el panel de administración.
 * Maneja la carga de datos según la página seleccionada.
 */
@WebServlet("/admin/panel")
public class AdminPanelController extends BaseServlet {
    private static final long serialVersionUID = 1L;
    
    private static final String PANEL_JSP = "/admin/panel/index.jsp";
    
    private static final int DEFAULT_PAGE_SIZE = 15;

    @Override
    protected void doGetScoped(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        if (!SessionUtil.isAuthenticated(request) || !SessionUtil.isAdmin(request)) {
            response.sendRedirect(request.getContextPath() + LOGIN_PAGE);
            return;
        }
        
        String page = request.getParameter("page");
        if (page == null || page.isEmpty()) {
            page = "dashboard";
        }
        
        try {
            switch (page) {
                case "mantenimiento-autores":
                    loadAuthorsData(request);
                    break;
                case "edit-autor":
                    loadEditAuthorData(request);
                    break;
                case "mantenimiento-libros":
                    loadBooksData(request);
                    break;
                case "edit-libro":
                    loadEditBookData(request);
                    break;
                case "libros-alquiler":
                    loadRentalsData(request);
                    break;
                case "libros-pedidos":
                    loadMostRequestedBooksData(request);
                    break;
                case "autores-pedidos":
                    loadMostRequestedAuthorsData(request);
                    break;
                case "mantenimiento-usuarios":
                    loadUsersData(request);
                    break;
                case "edit-usuario":
                    loadEditUserData(request);
                    break;
                case "mantenimiento-ejemplares":
                    loadBookCopiesData(request);
                    break;
                case "edit-ejemplar":
                    loadEditBookCopyData(request);
                    break;
                case "dashboard":
                default:
                    loadDashboardData(request);
                    break;
            }
        } catch (Exception e) {
            System.err.println("Error cargando datos para página " + page + ": " + e.getMessage());
            e.printStackTrace();
            request.setAttribute("error", "Error al cargar los datos: " + e.getMessage());
        }
        
        request.getRequestDispatcher(PANEL_JSP).forward(request, response);
    }

    /**
     * Carga los datos para la página de mantenimiento de autores.
     */
    private void loadAuthorsData(HttpServletRequest request) throws SQLException, ClassNotFoundException {
        IAuthorService authorService = getService(IAuthorService.class);
        ICountryService countryService = getService(ICountryService.class);
        IStatusService statusService = getService(IStatusService.class);
        
        int currentPage = getIntParameter(request, "p", 1);
        int pageSize = getIntParameter(request, "size", DEFAULT_PAGE_SIZE);
        String search = request.getParameter("search");
        String countryId = request.getParameter("countryId");
        String statusId = request.getParameter("statusId");
        
        PagedResult<AuthorData> authorsResult = authorService.getRegisteredAuthors(
            currentPage, pageSize, search, countryId, statusId);
        
        List<Country> countries = countryService.findAll();
        List<Status> statuses = statusService.findAll();
        int activeAuthorsCount = authorService.getActiveAuthorsCount();
        
        request.setAttribute("authorsResult", authorsResult);
        request.setAttribute("activeAuthorsCount", activeAuthorsCount);
        request.setAttribute("countries", countries);
        request.setAttribute("statuses", statuses);
        request.setAttribute("totalAuthors", authorService.getTotalAuthorsCount());
        
        request.setAttribute("searchValue", search != null ? search : "");
        request.setAttribute("countryIdValue", countryId != null ? countryId : "");
        request.setAttribute("statusIdValue", statusId != null ? statusId : "");
    }

    /**
     * Carga los datos para la página de edición de autor.
     */
    private void loadEditAuthorData(HttpServletRequest request) throws SQLException, ClassNotFoundException {
        String authorId = request.getParameter("id");
        
        if (authorId == null || authorId.isEmpty()) {
            request.getSession().setAttribute("error", "ID de autor requerido");
            request.setAttribute("page", "mantenimiento-autores");
            return;
        }
        
        IAuthorService authorService = getService(IAuthorService.class);
        ICountryService countryService = getService(ICountryService.class);
        
        Author author = authorService.findById(authorId);
        
        if (author == null) {
            request.getSession().setAttribute("error", "Autor no encontrado");
            request.setAttribute("page", "mantenimiento-autores");
            return;
        }
        
        List<Country> countries = countryService.findAll();
        
        request.setAttribute("author", author);
        request.setAttribute("countries", countries);
    }

    /**
     * Carga los datos para la página de mantenimiento de libros.
     */
    private void loadBooksData(HttpServletRequest request) throws SQLException, ClassNotFoundException {
        IBookService bookService = getService(IBookService.class);
        IAuthorService authorService = getService(IAuthorService.class);
        IBookStatusService bookStatusService = getService(IBookStatusService.class);
        ICategoryService categoryService = getService(ICategoryService.class);
        
        int currentPage = getIntParameter(request, "p", 1);
        int pageSize = getIntParameter(request, "size", DEFAULT_PAGE_SIZE);
        String search = request.getParameter("search");
        String authorId = request.getParameter("authorId");
        String bookStatusId = request.getParameter("bookStatusId");
        String categoryId = request.getParameter("categoryId");
        
        PagedResult<Book> booksResult = bookService.getRegisteredBooks(
            currentPage, pageSize, search, authorId, categoryId, bookStatusId);
        
        ArrayList<Author> authors = authorService.findAll();
        ArrayList<BookStatus> bookStatuses = bookStatusService.findAll();
        List<Category> categories = categoryService.findAll();
        int activeBooksCount = bookService.getActiveBooksCount();
        
        request.setAttribute("booksResult", booksResult);
        request.setAttribute("activeBooksCount", activeBooksCount);
        request.setAttribute("authors", authors);
        request.setAttribute("bookStatuses", bookStatuses);
        request.setAttribute("categories", categories);
        request.setAttribute("totalBooks", bookService.getTotalBooksCount());
        
        request.setAttribute("searchValue", search != null ? search : "");
        request.setAttribute("authorIdValue", authorId != null ? authorId : "");
        request.setAttribute("categoryIdValue", categoryId != null ? categoryId : "");
        request.setAttribute("bookStatusIdValue", bookStatusId != null ? bookStatusId : "");
    }

    /**
     * Carga los datos para la página de edición de libro.
     */
    private void loadEditBookData(HttpServletRequest request) throws SQLException, ClassNotFoundException {
        String bookId = request.getParameter("id");
        
        if (bookId == null || bookId.isEmpty()) {
            request.getSession().setAttribute("error", "ID de libro requerido");
            request.setAttribute("page", "mantenimiento-libros");
            return;
        }
        
        IBookService bookService = getService(IBookService.class);
        IAuthorService authorService = getService(IAuthorService.class);
        IBookStatusService bookStatusService = getService(IBookStatusService.class);
        ICategoryService categoryService = getService(ICategoryService.class);
        
        Book book = bookService.findById(bookId);
        
        if (book == null) {
            request.getSession().setAttribute("error", "Libro no encontrado");
            request.setAttribute("page", "mantenimiento-libros");
            return;
        }
        
        ArrayList<Author> authors = authorService.findAll();
        ArrayList<BookStatus> bookStatuses = bookStatusService.findAll();
        List<Category> categories = categoryService.findAll();
        
        request.setAttribute("libro", book);
        request.setAttribute("authors", authors);
        request.setAttribute("bookStatuses", bookStatuses);
        request.setAttribute("categories", categories);
    }

    private void loadUsersData(HttpServletRequest request) throws SQLException, ClassNotFoundException {
        IUserService userService = getService(IUserService.class);
        IStatusService statusService = getService(IStatusService.class);
        IRoleService roleService = getService(IRoleService.class);
        
        int currentPage = getIntParameter(request, "p", 1);
        int pageSize = getIntParameter(request, "size", DEFAULT_PAGE_SIZE);
        String search = request.getParameter("search");
        String roleId = request.getParameter("roleId");
        String statusId = request.getParameter("statusId");
        
        PagedResult<UserData> usersResult = userService.getRegisteredUsers(
            currentPage, pageSize, search, roleId, statusId);
        
        List<Status> statuses = statusService.findAll();
        List<Role> roles =  roleService.findAll();
        int activeUsersCount = userService.getActiveUsersCount();
        
        request.setAttribute("usersResult", usersResult);
        request.setAttribute("activeUsersCount", activeUsersCount);
        request.setAttribute("statuses", statuses);
        request.setAttribute("roles", roles);
        request.setAttribute("totalUsers", userService.getTotalUsersCount());
        
        request.setAttribute("searchValue", search != null ? search : "");
        request.setAttribute("roleIdValue", roleId != null ? roleId : "");
        request.setAttribute("statusIdValue", statusId != null ? statusId : "");
    }

    private void loadEditUserData(HttpServletRequest request) throws SQLException, ClassNotFoundException {
        String userId = request.getParameter("id");
        
        if (userId == null || userId.isEmpty()) {
            request.getSession().setAttribute("error", "ID de usuario requerido");
            request.setAttribute("page", "mantenimiento-usuarios");
            return;
        }
        
        IUserService userService = getService(IUserService.class);
        IStatusService statusService = getService(IStatusService.class);
        IRoleService roleService = getService(IRoleService.class);
        
        User user = userService.getUserById(userId);
        
        if (user == null) {
            request.getSession().setAttribute("error", "Usuario no encontrado");
            request.setAttribute("page", "mantenimiento-usuarios");
            return;
        }
        
        ArrayList<Status> statuses = statusService.findAll();
        List<Role> roles =  roleService.findAll();
        
        request.setAttribute("user", user);
        request.setAttribute("statuses", statuses);
        request.setAttribute("roles", roles);
    }

    private void loadBookCopiesData(HttpServletRequest request) throws SQLException, ClassNotFoundException {
        IBookCopyService bookCopyService = getService(IBookCopyService.class);
        IBookService bookService = getService(IBookService.class);
        IBookCopyStatusRepository bookCopyStatusRepository = getService(IBookCopyStatusRepository.class);
        
        int currentPage = getIntParameter(request, "p", 1);
        int pageSize = getIntParameter(request, "size", DEFAULT_PAGE_SIZE);
        String search = request.getParameter("search");
        String bookId = request.getParameter("bookId");
        String bookStatusId = request.getParameter("bookStatusId");
        
        PagedResult<BookCopy> copiesResult = bookCopyService.getRegisteredBookCopies(
            currentPage, pageSize, search, bookId, bookStatusId);
        
        List<Book> books = bookService.findAll();
        List<BookCopyStatus> bookCopyStatuses = bookCopyStatusRepository.findAll();
        
        int availableCopiesCount = bookCopyService.findByStatus(
            bookCopyService.getAvailableStatusId()).size();
        int rentedCopiesCount = bookCopyService.findByStatus(
            bookCopyService.getRentedStatusId()).size();
        int maintenanceCopiesCount = bookCopyService.findByStatus(
            bookCopyService.getMaintenanceStatusId()).size();
        
        request.setAttribute("copiesResult", copiesResult);
        request.setAttribute("books", books);
        request.setAttribute("bookCopyStatuses", bookCopyStatuses);
        request.setAttribute("availableCopiesCount", availableCopiesCount);
        request.setAttribute("rentedCopiesCount", rentedCopiesCount);
        request.setAttribute("maintenanceCopiesCount", maintenanceCopiesCount);
        
        request.setAttribute("searchValue", search != null ? search : "");
        request.setAttribute("bookIdValue", bookId != null ? bookId : "");
        request.setAttribute("bookStatusIdValue", bookStatusId != null ? bookStatusId : "");
    }

    private void loadEditBookCopyData(HttpServletRequest request) throws SQLException, ClassNotFoundException {
        String bookCopyId = request.getParameter("id");
        
        if (bookCopyId == null || bookCopyId.isEmpty()) {
            request.getSession().setAttribute("error", "ID de ejemplar requerido");
            request.setAttribute("page", "mantenimiento-ejemplares");
            return;
        }
        
        IBookCopyService bookCopyService = getService(IBookCopyService.class);
        IBookCopyStatusService bookCopyStatusService = getService(IBookCopyStatusService.class);
        
        BookCopy bookCopy = bookCopyService.findById(bookCopyId);
        
        if (bookCopy == null) {
            request.getSession().setAttribute("error", "Ejemplar no encontrado");
            request.setAttribute("page", "mantenimiento-ejemplares");
            return;
        }
        
        List<BookCopyStatus> bookCopyStatuses =  bookCopyStatusService.findAll();
        
        request.setAttribute("bookCopyStatuses", bookCopyStatuses);
        request.setAttribute("bookCopy", bookCopy);
    }

    /**
     * Carga los datos para la página de libros en alquiler.
     */
    private void loadRentalsData(HttpServletRequest request) {
        // TODO: Implementar cuando exista RentalService
    }

    /**
     * Carga los datos para la página de libros más pedidos.
     */
    private void loadMostRequestedBooksData(HttpServletRequest request) {
        // TODO: Implementar cuando exista BookService
    }

    /**
     * Carga los datos para la página de autores más pedidos.
     */
    private void loadMostRequestedAuthorsData(HttpServletRequest request) {
        // TODO: Implementar cuando exista estadísticas
    }

    /**
     * Carga los datos para el dashboard.
     */
    private void loadDashboardData(HttpServletRequest request) throws SQLException, ClassNotFoundException {
        IAuthorService authorService = getService(IAuthorService.class);
        
        // Estadísticas básicas
        request.setAttribute("totalAuthors", authorService.getTotalAuthorsCount());
        
        // TODO: Agregar más estadísticas cuando existan los servicios
    }

    /**
     * Obtiene un parámetro entero del request con valor por defecto.
     */
    private int getIntParameter(HttpServletRequest request, String name, int defaultValue) {
        String value = request.getParameter(name);
        if (value == null || value.isEmpty()) {
            return defaultValue;
        }
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException e) {
            return defaultValue;
        }
    }
}