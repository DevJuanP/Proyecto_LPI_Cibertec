package controller;

import java.io.IOException;
import java.io.PrintWriter;

import core.BaseServlet;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Author;
import service.IAuthorService;
import service.ICountryService;
import service.IStatusService;
import util.DateUtil;
import util.SessionUtil;

/**
 * Controlador para la gestión de autores.
 * Maneja operaciones CRUD y vistas de detalle.
 */
@WebServlet("/author")
public class AuthorController extends BaseServlet {
    private static final String ADMIN_PANEL = "/admin/panel?page=mantenimiento-autores";

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
                    handleViewAuthor(request, response);
                    break;
                case "edit":
                    handleEditAuthorForm(request, response);
                    break;
                default:
                    response.sendRedirect(request.getContextPath() + ADMIN_PANEL);
            }
        } catch (Exception e) {
            System.err.println("Error en AuthorController GET: " + e.getMessage());
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
                    handleCreateAuthor(request, response);
                    break;
                case "update":
                    handleUpdateAuthor(request, response);
                    break;
                case "delete":
                    handleDeleteAuthor(request, response);
                    break;
                default:
                    response.sendRedirect(request.getContextPath() + ADMIN_PANEL);
            }
        } catch (Exception e) {
            System.err.println("Error en AuthorController POST: " + e.getMessage());
            e.printStackTrace();
            request.getSession().setAttribute("error", "Error: " + e.getMessage());
            response.sendRedirect(request.getContextPath() + ADMIN_PANEL);
        }
    }

    /**
     * Muestra los detalles de un autor (respuesta HTML parcial para modal).
     */
    private void handleViewAuthor(HttpServletRequest request, HttpServletResponse response) 
            throws Exception {
        
        String authorId = request.getParameter("id");
        
        if (authorId == null || authorId.isEmpty()) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "ID de autor requerido");
            return;
        }
        
        IAuthorService authorService = getService(IAuthorService.class);
        Author author = authorService.findById(authorId);
        
        if (author == null) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "Autor no encontrado");
            return;
        }
        
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        
        out.println("<div class=\"row\">");
        out.println("  <div class=\"col-md-4 text-center\">");
        
        String photoUrl = author.getPhotoUrl();
        if (photoUrl == null || photoUrl.isEmpty()) {
            photoUrl = "https://ui-avatars.com/api/?name=" + 
                       author.getFullName().replace(" ", "+") + 
                       "&size=150&background=3498db&color=fff";
        }
        
        out.println("    <img src=\"" + photoUrl + "\" class=\"rounded-circle mb-3\" width=\"150\" height=\"150\" alt=\"Foto\">");
        out.println("    <h4>" + escapeHtml(author.getFullName()) + "</h4>");
        
        if (author.getPseudonym() != null && !author.getPseudonym().isEmpty()) {
            out.println("    <p class=\"text-muted\"><em>\"" + escapeHtml(author.getPseudonym()) + "\"</em></p>");
        }
        
        if (author.getCountry() != null) {
            out.println("    <span class=\"badge bg-primary\"><i class=\"bi bi-flag-fill me-1\"></i>" + 
                       escapeHtml(author.getCountry().getCountryName()) + "</span>");
        }
       
        out.println("  </div>");
        out.println("  <div class=\"col-md-8\">");
        out.println("    <table class=\"table table-borderless\">");        
        out.println("      <tr>");
        out.println("        <th style=\"width: 40%\">Nacimiento:</th>");
        out.println("        <td>" + (DateUtil.formatYear(author.getBirthYear())) + "</td>");
        out.println("      </tr>");
        
        if (author.getDeathYear() != null) {
            out.println("      <tr>");
            out.println("        <th>Fallecimiento:</th>");
            out.println("        <td>" + DateUtil.formatYear(author.getDeathYear()) + "</td>");
            out.println("      </tr>");
        }
        
        out.println("      <tr>");
        out.println("        <th>Email:</th>");
        out.println("        <td>" + (author.getEmail() != null ? 
                   "<a href=\"mailto:" + escapeHtml(author.getEmail()) + "\">" + escapeHtml(author.getEmail()) + "</a>" : "-") + "</td>");
        out.println("      </tr>");        
        out.println("      <tr>");
        out.println("        <th>Sitio Web:</th>");
        out.println("        <td>" + (author.getWebsite() != null ? 
                   "<a href=\"" + escapeHtml(author.getWebsite()) + "\" target=\"_blank\">" + escapeHtml(author.getWebsite()) + "</a>" : "-") + "</td>");
        out.println("      </tr>");
        out.println("      <tr>");
        out.println("        <th>Estado:</th>");
        String statusBadge = author.getStatus() != null && "Active".equals(author.getStatus().getStatusName()) 
                            ? "<span class=\"badge bg-success\">Activo</span>" 
                            : "<span class=\"badge bg-secondary\">Inactivo</span>";
        out.println("        <td>" + statusBadge + "</td>");
        out.println("      </tr>");
        out.println("      <tr>");
        out.println("        <th style=\"width: 40%\">Libros registrados:</th>");
        out.println("        <td>" + authorService.getAuthorBookCount(author.getAuthorId()) + "</td>");
        out.println("      </tr>");
        
        out.println("    </table>");
        
        if (author.getBiography() != null && !author.getBiography().isEmpty()) {
            out.println("    <hr>");
            out.println("    <h6>Biografía</h6>");
            out.println("    <p class=\"text-justify\">" + escapeHtml(author.getBiography()) + "</p>");
        }
        
        out.println("  </div>");
        out.println("</div>");        
        out.println("<div class=\"text-end mt-3\">");
        out.println("  <button type=\"button\" class=\"btn btn-secondary\" data-bs-dismiss=\"modal\">Cerrar</button>");
        out.println("  <button type=\"button\" class=\"btn btn-warning\" onclick=\"editAuthor('" + author.getAuthorId() + "')\">");
        out.println("    <i class=\"bi bi-pencil\"></i> Editar");
        out.println("  </button>");
        out.println("</div>");
    }

    /**
     * Muestra el formulario de edición de un autor.
     */
    private void handleEditAuthorForm(HttpServletRequest request, HttpServletResponse response) 
            throws Exception {
        
        String authorId = request.getParameter("id");
        
        if (authorId == null || authorId.isEmpty()) {
            response.sendRedirect(request.getContextPath() + ADMIN_PANEL);
            return;
        }
        
        IAuthorService authorService = getService(IAuthorService.class);
        IStatusService statusService = getService(IStatusService.class);
        ICountryService countryService = getService(ICountryService.class);
        
        Author author = authorService.findById(authorId);
        
        if (author == null) {
            request.getSession().setAttribute("error", "Autor no encontrado");
            response.sendRedirect(request.getContextPath() + ADMIN_PANEL);
            return;
        }
        
        request.setAttribute("author", author);
        request.setAttribute("countries", countryService.findAll());
        request.setAttribute("statuses", statusService.findAll());
        request.getRequestDispatcher("/admin/panel/items/edit-autor.jsp").forward(request, response);
    }

    /**
     * Crea un nuevo autor.
     */
    private void handleCreateAuthor(HttpServletRequest request, HttpServletResponse response) 
            throws Exception {
        
        IAuthorService authorService = getService(IAuthorService.class);
        IStatusService statusService = getService(IStatusService.class);
        
        Author author = new Author();
        populateAuthorFromRequest(author, request);
        
        author.setStatusId(statusService.getActiveStatusId());
        
        authorService.save(author);
        
        request.getSession().setAttribute("success", "Autor creado exitosamente");
        response.sendRedirect(request.getContextPath() + ADMIN_PANEL);
    }

    /**
     * Actualiza un autor existente.
     */
    private void handleUpdateAuthor(HttpServletRequest request, HttpServletResponse response) 
            throws Exception {
        
        String authorId = request.getParameter("authorId");
        
        if (authorId == null || authorId.isEmpty()) {
            request.getSession().setAttribute("error", "ID de autor requerido");
            response.sendRedirect(request.getContextPath() + ADMIN_PANEL);
            return;
        }
        
        IAuthorService authorService = getService(IAuthorService.class);
        IStatusService statusService = getService(IStatusService.class);
        
        Author author = authorService.findById(authorId);
        
        if (author == null) {
            request.getSession().setAttribute("error", "Autor no encontrado");
            response.sendRedirect(request.getContextPath() + ADMIN_PANEL);
            return;
        }
        
        populateAuthorFromRequest(author, request);
        
        String statusName = request.getParameter("statusId");
        if (statusName != null && !statusName.isEmpty()) {
            if ("active".equalsIgnoreCase(statusName)) {
                author.setStatusId(statusService.getActiveStatusId());
            } else if ("inactive".equalsIgnoreCase(statusName)) {
                author.setStatusId(statusService.getInactiveStatusId());
            }
        }
        
        authorService.update(author);
        
        request.getSession().setAttribute("success", "Autor actualizado exitosamente");
        response.sendRedirect(request.getContextPath() + ADMIN_PANEL);
    }

    /**
     * Elimina un autor.
     */
    private void handleDeleteAuthor(HttpServletRequest request, HttpServletResponse response) 
            throws Exception {
        
        String authorId = request.getParameter("authorId");
        
        if (authorId == null || authorId.isEmpty()) {
            request.getSession().setAttribute("error", "ID de autor requerido");
            response.sendRedirect(request.getContextPath() + ADMIN_PANEL);
            return;
        }
        
        IAuthorService authorService = getService(IAuthorService.class);
        authorService.delete(authorId);
        
        request.getSession().setAttribute("success", "Autor eliminado exitosamente");
        response.sendRedirect(request.getContextPath() + ADMIN_PANEL);
    }

    /**
     * Puebla un objeto Author con los datos del request.
     */
    private void populateAuthorFromRequest(Author author, HttpServletRequest request) {
        author.setFullName(request.getParameter("fullName"));
        author.setPseudonym(getStringOrNull(request.getParameter("pseudonym")));
        author.setCountryId(request.getParameter("countryId"));
        author.setBiography(getStringOrNull(request.getParameter("biography")));
        author.setWebsite(getStringOrNull(request.getParameter("website")));
        author.setEmail(getStringOrNull(request.getParameter("email")));
        author.setPhotoUrl(getStringOrNull(request.getParameter("photoUrl")));
        
        String birthYearStr = request.getParameter("birthYear");
        if (birthYearStr != null && !birthYearStr.isEmpty()) {
            author.setBirthYear(Integer.parseInt(birthYearStr));
        }
        
        String deathYearStr = request.getParameter("deathYear");
        if (deathYearStr != null && !deathYearStr.isEmpty()) {
            author.setDeathYear(Integer.parseInt(deathYearStr));
        }
    }
}