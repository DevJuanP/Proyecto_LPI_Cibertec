package controller;

import java.io.IOException;
import java.io.PrintWriter;

import core.BaseServlet;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Book;
import service.IAuthorService;
import service.IBookService;
import service.IBookStatusService;
import service.ICategoryService;
import util.SessionUtil;

/**
 * Controlador para la gestión de libros.
 * Maneja operaciones CRUD y vistas de detalle.
 */
@WebServlet("/book")
public class BookController extends BaseServlet {
    private static final String ADMIN_PANEL = "/admin/panel?page=mantenimiento-libros";

    @Override
    protected void doGetScoped(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        if (!SessionUtil.isAuthenticated(request) || !SessionUtil.isAdmin(request)) {
            response.sendRedirect(request.getContextPath() + LOGIN_PAGE);
            return;
        }
        
        String action = request.getParameter("action");
        
        if (action == null) {
            response.sendRedirect(request.getContextPath() + ADMIN_PANEL);
            return;
        }
        
        try {
            switch (action) {
                case "view":
                    handleViewBook(request, response);
                    break;
                case "edit":
                    handleEditBookForm(request, response);
                    break;
                default:
                    response.sendRedirect(request.getContextPath() + ADMIN_PANEL);
            }
        } catch (Exception e) {
            System.err.println("Error en BookController GET: " + e.getMessage());
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error procesando la solicitud");
        }
    }

    @Override
    protected void doPostScoped(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        if (!SessionUtil.isAuthenticated(request) || !SessionUtil.isAdmin(request)) {
            response.sendRedirect(request.getContextPath() + LOGIN_PAGE);
            return;
        }
        
        String action = request.getParameter("action");
        
        if (action == null) {
            response.sendRedirect(request.getContextPath() + ADMIN_PANEL);
            return;
        }
        
        try {
            switch (action) {
                case "create":
                    handleCreateBook(request, response);
                    break;
                case "update":
                    handleUpdateBook(request, response);
                    break;
                case "delete":
                    handleDeleteBook(request, response);
                    break;
                default:
                    response.sendRedirect(request.getContextPath() + ADMIN_PANEL);
            }
        } catch (Exception e) {
            System.err.println("Error en BookController POST: " + e.getMessage());
            e.printStackTrace();
            request.getSession().setAttribute("error", "Error: " + e.getMessage());
            response.sendRedirect(request.getContextPath() + ADMIN_PANEL);
        }
    }

    /**
     * Muestra los detalles de un libro (respuesta HTML parcial para modal).
     */
    private void handleViewBook(HttpServletRequest request, HttpServletResponse response) 
            throws Exception {
        
        String bookId = request.getParameter("id");
        
        if (bookId == null || bookId.isEmpty()) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "ID de libro requerido");
            return;
        }
        
        IBookService bookService = getService(IBookService.class);
        Book book = bookService.findById(bookId);
        
        if (book == null) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "Libro no encontrado");
            return;
        }
        
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        
        out.println("<div class=\"row\">");
        out.println("  <div class=\"col-md-4 text-center\">");
        
        String coverUrl = book.getCoverImageUrl();
        if (coverUrl == null || coverUrl.isEmpty()) {
            coverUrl = "https://via.placeholder.com/200x300/3498db/ffffff?text=" + 
                       escapeHtml(book.getTitle()).replace(" ", "+");
        }
        
        out.println("    <img src=\"" + coverUrl + "\" class=\"img-fluid rounded shadow mb-3\" alt=\"Portada\" style=\"max-height: 300px;\">");
        out.println("    <h4>" + escapeHtml(book.getTitle()) + "</h4>");
        out.println("    <p class=\"text-muted\">ISBN: <code>" + escapeHtml(book.getIsbn()) + "</code></p>");
        
        if (book.getAuthor() != null) {
            out.println("    <p class=\"mb-1\"><strong>Autor:</strong></p>");
            out.println("    <p class=\"text-muted\">" + escapeHtml(book.getAuthor().getFullName()) + "</p>");
        }
        
        if (book.getCategory() != null) {
            out.println("    <span class=\"badge bg-primary\">" + escapeHtml(book.getCategory().getCategoryName()) + "</span>");
        }
        
        out.println("  </div>");
        out.println("  <div class=\"col-md-8\">");
        out.println("    <table class=\"table table-borderless\">");
        
        out.println("      <tr>");
        out.println("        <th style=\"width: 40%\">Editorial:</th>");
        out.println("        <td>" + (book.getPublisher() != null ? escapeHtml(book.getPublisher()) : "-") + "</td>");
        out.println("      </tr>");
        
        out.println("      <tr>");
        out.println("        <th>Año de Publicación:</th>");
        out.println("        <td>" + (book.getPublicationYear() != null ? book.getPublicationYear() : "-") + "</td>");
        out.println("      </tr>");
        
        out.println("      <tr>");
        out.println("        <th>Páginas:</th>");
        out.println("        <td>" + (book.getPages() != null ? book.getPages() : "-") + "</td>");
        out.println("      </tr>");
        
        out.println("      <tr>");
        out.println("        <th>Idioma:</th>");
        out.println("        <td>" + (book.getLanguage() != null ? escapeHtml(book.getLanguage()) : "-") + "</td>");
        out.println("      </tr>");
        
        out.println("      <tr>");
        out.println("        <th>Estado:</th>");
        String statusBadge = book.getBookStatus() != null && "Activo".equals(book.getBookStatus().getBookStatusName()) 
                            ? "<span class=\"badge bg-success\">Disponible</span>" 
                            : "<span class=\"badge bg-secondary\">No Disponible</span>";
        out.println("        <td>" + statusBadge + "</td>");
        out.println("      </tr>");
        
        out.println("    </table>");
        
        if (book.getDescription() != null && !book.getDescription().isEmpty()) {
            out.println("    <hr>");
            out.println("    <h6>Descripción</h6>");
            out.println("    <p class=\"text-justify\">" + escapeHtml(book.getDescription()) + "</p>");
        }
        
        out.println("  </div>");
        out.println("</div>");
        
        out.println("<div class=\"text-end mt-3\">");
        out.println("  <button type=\"button\" class=\"btn btn-secondary\" data-bs-dismiss=\"modal\">Cerrar</button>");
        out.println("  <button type=\"button\" class=\"btn btn-warning\" onclick=\"editBook('" + book.getBookId() + "')\">");
        out.println("    <i class=\"bi bi-pencil\"></i> Editar");
        out.println("  </button>");
        out.println("</div>");
    }

    /**
     * Muestra el formulario de edición de un libro.
     */
    private void handleEditBookForm(HttpServletRequest request, HttpServletResponse response) 
            throws Exception {
        
        String bookId = request.getParameter("id");
        
        if (bookId == null || bookId.isEmpty()) {
            request.getSession().setAttribute("error", "ID de libro requerido");
            response.sendRedirect(request.getContextPath() + ADMIN_PANEL);
            return;
        }
        
        IBookService bookService = getService(IBookService.class);
        IAuthorService authorService = getService(IAuthorService.class);
        IBookStatusService bookStatusService = getService(IBookStatusService.class);
        ICategoryService categoryService = getService(ICategoryService.class);
        
        Book book = bookService.findById(bookId);
        
        if (book == null) {
            request.getSession().setAttribute("error", "Libro no encontrado");
            response.sendRedirect(request.getContextPath() + ADMIN_PANEL);
            return;
        }
        
        request.setAttribute("libro", book);
        request.setAttribute("authors", authorService.findAll());
        request.setAttribute("bookStatuses", bookStatusService.findAll());
        request.setAttribute("categories", categoryService.findAll());
        request.getRequestDispatcher("/admin/panel/items/edit-libro.jsp").forward(request, response);
    }

    /**
     * Crea un nuevo libro.
     */
    private void handleCreateBook(HttpServletRequest request, HttpServletResponse response) 
            throws Exception {
        
        IBookService bookService = getService(IBookService.class);
        IBookStatusService bookStatusService = getService(IBookStatusService.class);        
        
        Book book = new Book();
        populateBookFromRequest(book, request);
        
        book.setBookStatusId(bookStatusService.getActiveStatusId());
        
        bookService.save(book);
        
        request.getSession().setAttribute("success", "Libro creado exitosamente");
        response.sendRedirect(request.getContextPath() + ADMIN_PANEL);
    }

    /**
     * Actualiza un libro existente.
     */
    private void handleUpdateBook(HttpServletRequest request, HttpServletResponse response) 
            throws Exception {
        
        String bookId = request.getParameter("bookId");
        
        if (bookId == null || bookId.isEmpty()) {
            request.getSession().setAttribute("error", "ID de libro requerido");
            response.sendRedirect(request.getContextPath() + ADMIN_PANEL);
            return;
        }
        
        IBookService bookService = getService(IBookService.class);
        IBookStatusService bookStatusService = getService(IBookStatusService.class);
        
        Book book = bookService.findById(bookId);
        
        if (book == null) {
            request.getSession().setAttribute("error", "Libro no encontrado");
            response.sendRedirect(request.getContextPath() + ADMIN_PANEL);
            return;
        }
        
        populateBookFromRequest(book, request);
        
        String statusName = request.getParameter("bookStatusId");
        if (statusName != null && !statusName.isEmpty()) {
            if ("activo".equalsIgnoreCase(statusName)) {
                book.setBookStatusId(bookStatusService.getActiveStatusId());
            } else if ("inactivo".equalsIgnoreCase(statusName)) {
                book.setBookStatusId(bookStatusService.getInactiveStatusId());
            }
        }
        
        bookService.update(book);
        
        request.getSession().setAttribute("success", "Libro actualizado exitosamente");
        response.sendRedirect(request.getContextPath() + ADMIN_PANEL);
    }

    /**
     * Elimina un libro.
     */
    private void handleDeleteBook(HttpServletRequest request, HttpServletResponse response) 
            throws Exception {
        
        String bookId = request.getParameter("bookId");
        
        if (bookId == null || bookId.isEmpty()) {
            request.getSession().setAttribute("error", "ID de libro requerido");
            response.sendRedirect(request.getContextPath() + ADMIN_PANEL);
            return;
        }
        
        IBookService bookService = getService(IBookService.class);
        bookService.delete(bookId);
        
        request.getSession().setAttribute("success", "Libro eliminado exitosamente");
        response.sendRedirect(request.getContextPath() + ADMIN_PANEL);
    }

    /**
     * Puebla un objeto Book con los datos del request.
     */
    private void populateBookFromRequest(Book book, HttpServletRequest request) {
        book.setIsbn(request.getParameter("isbn"));
        book.setTitle(request.getParameter("title"));
        book.setAuthorId(request.getParameter("authorId"));
        book.setCategoryId(request.getParameter("categoryId"));
        book.setPublisher(getStringOrNull(request.getParameter("publisher")));
        book.setLanguage(getStringOrNull(request.getParameter("language")));
        book.setDescription(getStringOrNull(request.getParameter("description")));
        book.setCoverImageUrl(getStringOrNull(request.getParameter("coverImageUrl")));
        
        String publicationYearStr = request.getParameter("publicationYear");
        if (publicationYearStr != null && !publicationYearStr.isEmpty()) {
            book.setPublicationYear(Integer.parseInt(publicationYearStr));
        }
        
        String pagesStr = request.getParameter("pages");
        if (pagesStr != null && !pagesStr.isEmpty()) {
            book.setPages(Integer.parseInt(pagesStr));
        }
    }
}