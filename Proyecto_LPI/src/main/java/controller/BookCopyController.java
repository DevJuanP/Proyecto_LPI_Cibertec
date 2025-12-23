package controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import core.BaseServlet;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.BookCopy;
import service.IBookCopyService;
import util.SessionUtil;

/**
 * Controlador para la gestión de ejemplares de libros.
 * Incluye funcionalidad especial: inserción en batch, cambio de estado masivo.
 */
@WebServlet("/book-copy")
public class BookCopyController extends BaseServlet {
    private static final String ADMIN_PANEL = "/admin/panel?page=mantenimiento-ejemplares";

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
                    handleViewBookCopy(request, response);
                    break;
                case "edit":
                    handleEditBookCopyForm(request, response);
                    break;
                default:
                    response.sendRedirect(request.getContextPath() + ADMIN_PANEL);
            }
        } catch (Exception e) {
            System.err.println("Error en BookCopyController GET: " + e.getMessage());
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
                case "createBatch":
                    handleCreateBatch(request, response);
                    break;
                case "update":
                    handleUpdateBookCopy(request, response);
                    break;
                case "updateStatusBatch":
                    handleUpdateStatusBatch(request, response);
                    break;
                case "delete":
                    handleDeleteBookCopy(request, response);
                    break;
                default:
                    response.sendRedirect(request.getContextPath() + ADMIN_PANEL);
            }
        } catch (Exception e) {
            System.err.println("Error en BookCopyController POST: " + e.getMessage());
            e.printStackTrace();
            request.getSession().setAttribute("error", "Error: " + e.getMessage());
            response.sendRedirect(request.getContextPath() + ADMIN_PANEL);
        }
    }

    /**
     * Muestra los detalles de un ejemplar (respuesta HTML parcial para modal).
     */
    private void handleViewBookCopy(HttpServletRequest request, HttpServletResponse response) 
            throws Exception {
        
        String bookCopyId = request.getParameter("id");
        
        if (bookCopyId == null || bookCopyId.isEmpty()) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "ID de ejemplar requerido");
            return;
        }
        
        IBookCopyService bookCopyService = getService(IBookCopyService.class);
        BookCopy bookCopy = bookCopyService.findById(bookCopyId);
        
        if (bookCopy == null) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "Ejemplar no encontrado");
            return;
        }
        
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        
        out.println("<div class=\"row\">");
        out.println("  <div class=\"col-md-12\">");
        out.println("    <table class=\"table table-borderless\">");
        
        out.println("      <tr>");
        out.println("        <th style=\"width: 30%\">ID del Ejemplar:</th>");
        out.println("        <td><code>" + escapeHtml(bookCopy.getBookCopyId()) + "</code></td>");
        out.println("      </tr>");
        
        if (bookCopy.getBook() != null) {
            out.println("      <tr>");
            out.println("        <th>Libro:</th>");
            out.println("        <td>" + escapeHtml(bookCopy.getBook().getTitle()) + "</td>");
            out.println("      </tr>");
            
            out.println("      <tr>");
            out.println("        <th>ISBN:</th>");
            out.println("        <td><code>" + escapeHtml(bookCopy.getBook().getIsbn()) + "</code></td>");
            out.println("      </tr>");
            
            if (bookCopy.getBook().getAuthor() != null) {
                out.println("      <tr>");
                out.println("        <th>Autor:</th>");
                out.println("        <td>" + escapeHtml(bookCopy.getBook().getAuthor().getFullName()) + "</td>");
                out.println("      </tr>");
            }
        }
        
        if (bookCopy.getBookCopyStatus() != null) {
            out.println("      <tr>");
            out.println("        <th>Estado:</th>");
            String statusBadge = getStatusBadge(bookCopy.getBookCopyStatus().getBookCopyStatusName());
            out.println("        <td>" + statusBadge + "</td>");
            out.println("      </tr>");
        }
        
        out.println("      <tr>");
        out.println("        <th>Notas:</th>");
        out.println("        <td>" + (bookCopy.getNotes() != null ? escapeHtml(bookCopy.getNotes()) : "-") + "</td>");
        out.println("      </tr>");
        
        if (bookCopy.getCreatedAt() != null) {
            out.println("      <tr>");
            out.println("        <th>Creado:</th>");
            out.println("        <td>" + bookCopy.getCreatedAt() + "</td>");
            out.println("      </tr>");
        }
        
        if (bookCopy.getUpdatedAt() != null) {
            out.println("      <tr>");
            out.println("        <th>Actualizado:</th>");
            out.println("        <td>" + bookCopy.getUpdatedAt() + "</td>");
            out.println("      </tr>");
        }
        
        out.println("    </table>");
        out.println("  </div>");
        out.println("</div>");
        
        out.println("<div class=\"text-end mt-3\">");
        out.println("  <button type=\"button\" class=\"btn btn-secondary\" data-bs-dismiss=\"modal\">Cerrar</button>");
        
        // Solo mostrar botones de editar/eliminar si no está alquilado
        boolean isRented = bookCopy.getBookCopyStatus() != null && 
                          "Alquilado".equals(bookCopy.getBookCopyStatus().getBookCopyStatusName());
        
        if (!isRented) {
            out.println("  <button type=\"button\" class=\"btn btn-warning\" onclick=\"editBookCopy('" + bookCopy.getBookCopyId() + "')\">");
            out.println("    <i class=\"bi bi-pencil\"></i> Editar");
            out.println("  </button>");
        }
        out.println("</div>");
    }

    /**
     * Muestra el formulario de edición de un ejemplar.
     */
    private void handleEditBookCopyForm(HttpServletRequest request, HttpServletResponse response) 
            throws Exception {
        
        String bookCopyId = request.getParameter("id");
        
        if (bookCopyId == null || bookCopyId.isEmpty()) {
            request.getSession().setAttribute("error", "ID de ejemplar requerido");
            response.sendRedirect(request.getContextPath() + ADMIN_PANEL);
            return;
        }
        
        IBookCopyService bookCopyService = getService(IBookCopyService.class);
        BookCopy bookCopy = bookCopyService.findById(bookCopyId);
        
        if (bookCopy == null) {
            request.getSession().setAttribute("error", "Ejemplar no encontrado");
            response.sendRedirect(request.getContextPath() + ADMIN_PANEL);
            return;
        }
        
        request.setAttribute("bookCopy", bookCopy);
        request.getRequestDispatcher("/admin/panel/items/edit-ejemplar.jsp").forward(request, response);
    }

    /**
     * Crea múltiples ejemplares en batch.
     */
    private void handleCreateBatch(HttpServletRequest request, HttpServletResponse response) 
            throws Exception {
        
        String bookId = request.getParameter("bookId");
        String quantityStr = request.getParameter("quantity");
        String notes = request.getParameter("notes");
        
        if (bookId == null || bookId.isEmpty() || quantityStr == null || quantityStr.isEmpty()) {
            request.getSession().setAttribute("error", "Libro y cantidad son requeridos");
            response.sendRedirect(request.getContextPath() + ADMIN_PANEL);
            return;
        }
        
        int quantity = Integer.parseInt(quantityStr);
        
        if (quantity < 1 || quantity > 100) {
            request.getSession().setAttribute("error", "La cantidad debe estar entre 1 y 100");
            response.sendRedirect(request.getContextPath() + ADMIN_PANEL);
            return;
        }
        
        IBookCopyService bookCopyService = getService(IBookCopyService.class);
        bookCopyService.saveBatch(bookId, quantity, notes);
        
        request.getSession().setAttribute("success", quantity + " ejemplar(es) creado(s) exitosamente");
        response.sendRedirect(request.getContextPath() + ADMIN_PANEL);
    }

    /**
     * Actualiza un ejemplar existente.
     */
    private void handleUpdateBookCopy(HttpServletRequest request, HttpServletResponse response) 
            throws Exception {
        
        String bookCopyId = request.getParameter("bookCopyId");
        
        if (bookCopyId == null || bookCopyId.isEmpty()) {
            request.getSession().setAttribute("error", "ID de ejemplar requerido");
            response.sendRedirect(request.getContextPath() + ADMIN_PANEL);
            return;
        }
        
        IBookCopyService bookCopyService = getService(IBookCopyService.class);
        BookCopy bookCopy = bookCopyService.findById(bookCopyId);
        
        if (bookCopy == null) {
            request.getSession().setAttribute("error", "Ejemplar no encontrado");
            response.sendRedirect(request.getContextPath() + ADMIN_PANEL);
            return;
        }
        
        String statusId = request.getParameter("bookCopyStatusId");
        String notes = request.getParameter("notes");
        
        if (statusId != null && !statusId.isEmpty()) {
            bookCopy.setBookCopyStatusId(statusId);
        }
        
        if (notes != null) {
            bookCopy.setNotes(notes.trim().isEmpty() ? null : notes);
        }
        
        bookCopyService.update(bookCopy);
        
        request.getSession().setAttribute("success", "Ejemplar actualizado exitosamente");
        response.sendRedirect(request.getContextPath() + ADMIN_PANEL);
    }

    /**
     * Actualiza el estado de múltiples ejemplares.
     */
    private void handleUpdateStatusBatch(HttpServletRequest request, HttpServletResponse response) 
            throws Exception {
        
        String[] selectedIds = request.getParameterValues("selectedCopies[]");
        String newStatusId = request.getParameter("newStatusId");
        
        if (selectedIds == null || selectedIds.length == 0) {
            request.getSession().setAttribute("error", "Debe seleccionar al menos un ejemplar");
            response.sendRedirect(request.getContextPath() + ADMIN_PANEL);
            return;
        }
        
        if (newStatusId == null || newStatusId.isEmpty()) {
            request.getSession().setAttribute("error", "Debe seleccionar un estado");
            response.sendRedirect(request.getContextPath() + ADMIN_PANEL);
            return;
        }
        
        List<String> bookCopyIds = new ArrayList<>();
        for (String id : selectedIds) {
            bookCopyIds.add(id);
        }
        
        IBookCopyService bookCopyService = getService(IBookCopyService.class);
        bookCopyService.updateStatusBatch(bookCopyIds, newStatusId);
        
        request.getSession().setAttribute("success", 
            selectedIds.length + " ejemplar(es) actualizado(s) exitosamente");
        response.sendRedirect(request.getContextPath() + ADMIN_PANEL);
    }

    /**
     * Elimina un ejemplar.
     */
    private void handleDeleteBookCopy(HttpServletRequest request, HttpServletResponse response) 
            throws Exception {
        
        String bookCopyId = request.getParameter("bookCopyId");
        
        if (bookCopyId == null || bookCopyId.isEmpty()) {
            request.getSession().setAttribute("error", "ID de ejemplar requerido");
            response.sendRedirect(request.getContextPath() + ADMIN_PANEL);
            return;
        }
        
        IBookCopyService bookCopyService = getService(IBookCopyService.class);
        bookCopyService.delete(bookCopyId);
        
        request.getSession().setAttribute("success", "Ejemplar eliminado exitosamente");
        response.sendRedirect(request.getContextPath() + ADMIN_PANEL);
    }

    /**
     * Obtiene el badge HTML según el estado.
     */
    private String getStatusBadge(String statusName) {
        if (statusName == null) return "<span class=\"badge bg-secondary\">Desconocido</span>";
        
        switch (statusName) {
            case "Disponible":
                return "<span class=\"badge bg-success\">Disponible</span>";
            case "Alquilado":
                return "<span class=\"badge bg-primary\">Alquilado</span>";
            case "Mantenimiento":
                return "<span class=\"badge bg-warning text-dark\">Mantenimiento</span>";
            case "Descontinuado":
                return "<span class=\"badge bg-secondary\">Descontinuado</span>";
            default:
                return "<span class=\"badge bg-secondary\">" + escapeHtml(statusName) + "</span>";
        }
    }
}