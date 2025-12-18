package controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import core.BaseServlet;
import dto.author.AuthorData;
import dto.shared.PagedResult;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Author;
import model.Country;
import model.Status;
import service.IAuthorService;
import service.ICountryService;
import service.IStatusService;
import util.SessionUtil;

/**
 * Controlador principal para el panel de administración.
 * Maneja la carga de datos según la página seleccionada.
 */
@WebServlet("/admin/panel")
public class AdminPanelController extends BaseServlet {
    private static final long serialVersionUID = 1L;
    
    private static final String PANEL_JSP = "/admin/panel/index.jsp";
    private static final String LOGIN_PAGE = "/admin/login";
    
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
                case "libros-alquiler":
                    loadRentalsData(request);
                    break;
                case "libros-pedidos":
                    loadMostRequestedBooksData(request);
                    break;
                case "autores-pedidos":
                    loadMostRequestedAuthorsData(request);
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
        
        request.setAttribute("authorsResult", authorsResult);
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
        
        // Cargar lista de países para el select
        List<Country> countries = countryService.findAll();
        
        // Establecer atributos en el request
        request.setAttribute("author", author);
        request.setAttribute("countries", countries);
    }

    /**
     * Carga los datos para la página de mantenimiento de libros.
     */
    private void loadBooksData(HttpServletRequest request) {
        // TODO: Implementar cuando exista BookService
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