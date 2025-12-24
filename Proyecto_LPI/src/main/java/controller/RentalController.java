package controller;

import java.io.IOException;
import java.io.PrintWriter;

import core.BaseServlet;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Rental;
import service.IRentalService;
import util.SessionUtil;

/**
 * Controlador para la gestión de alquileres.
 * Maneja operaciones de crear alquiler, marcar como devuelto y cancelar.
 */
@WebServlet("/rental")
public class RentalController extends BaseServlet {
    private static final String ADMIN_PANEL = "/admin/panel?page=alquileres";

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
                    handleViewRental(request, response);
                    break;
                default:
                    response.sendRedirect(request.getContextPath() + ADMIN_PANEL);
            }
        } catch (Exception e) {
            System.err.println("Error en RentalController GET: " + e.getMessage());
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
                    handleCreateRental(request, response);
                    break;
                case "markReturned":
                    handleMarkAsReturned(request, response);
                    break;
                case "cancel":
                    handleCancelRental(request, response);
                    break;
                default:
                    response.sendRedirect(request.getContextPath() + ADMIN_PANEL);
            }
        } catch (Exception e) {
            System.err.println("Error en RentalController POST: " + e.getMessage());
            e.printStackTrace();
            request.getSession().setAttribute("error", "Error: " + e.getMessage());
            response.sendRedirect(request.getContextPath() + ADMIN_PANEL);
        }
    }

    /**
     * Muestra los detalles de un alquiler (respuesta HTML parcial para modal).
     */
    private void handleViewRental(HttpServletRequest request, HttpServletResponse response) 
            throws Exception {
        
        String rentalId = request.getParameter("id");
        
        if (rentalId == null || rentalId.isEmpty()) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "ID de alquiler requerido");
            return;
        }
        
        IRentalService rentalService = getService(IRentalService.class);
        Rental rental = rentalService.findById(rentalId);
        
        if (rental == null) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "Alquiler no encontrado");
            return;
        }
        
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        
        out.println("<div class=\"row\">");
        out.println("  <div class=\"col-12\">");
        out.println("    <table class=\"table table-borderless\">");
        
        out.println("      <tr>");
        out.println("        <th style=\"width: 40%\">ID:</th>");
        out.println("        <td><code class=\"small\">" + escapeHtml(rental.getRentalId()) + "</code></td>");
        out.println("      </tr>");
        
        if (rental.getUser() != null) {
            out.println("      <tr>");
            out.println("        <th>Usuario:</th>");
            out.println("        <td>" + escapeHtml(rental.getUser().getEmail()) + "</td>");
            out.println("      </tr>");
        }
        
        if (rental.getBookCopy() != null && rental.getBookCopy().getBook() != null) {
            out.println("      <tr>");
            out.println("        <th>Libro:</th>");
            out.println("        <td>" + escapeHtml(rental.getBookCopy().getBook().getTitle()) + "</td>");
            out.println("      </tr>");
            
            out.println("      <tr>");
            out.println("        <th>ISBN:</th>");
            out.println("        <td>" + escapeHtml(rental.getBookCopy().getBook().getIsbn()) + "</td>");
            out.println("      </tr>");
        }
        
        if (rental.getRentalStatus() != null) {
            String statusBadge = "";
            switch (rental.getRentalStatus().getRentalStatusName()) {
                case "En Proceso":
                    statusBadge = "<span class=\"badge bg-warning\">En Proceso</span>";
                    break;
                case "Devuelto":
                    statusBadge = "<span class=\"badge bg-success\">Devuelto</span>";
                    break;
                case "Cancelado":
                    statusBadge = "<span class=\"badge bg-danger\">Cancelado</span>";
                    break;
                default:
                    statusBadge = "<span class=\"badge bg-secondary\">" + 
                                 escapeHtml(rental.getRentalStatus().getRentalStatusName()) + 
                                 "</span>";
            }
            out.println("      <tr>");
            out.println("        <th>Estado:</th>");
            out.println("        <td>" + statusBadge + "</td>");
            out.println("      </tr>");
        }
        
        out.println("      <tr>");
        out.println("        <th>Fecha de Alquiler:</th>");
        out.println("        <td>" + rental.getRentalDate() + "</td>");
        out.println("      </tr>");
        
        out.println("      <tr>");
        out.println("        <th>Fecha de Vencimiento:</th>");
        out.println("        <td>" + rental.getDueDate() + "</td>");
        out.println("      </tr>");
        
        if (rental.getReturnDate() != null) {
            out.println("      <tr>");
            out.println("        <th>Fecha de Devolución:</th>");
            out.println("        <td>" + rental.getReturnDate() + "</td>");
            out.println("      </tr>");
        }
        
        out.println("      <tr>");
        out.println("        <th>Días de Alquiler:</th>");
        out.println("        <td>" + rental.getRentalDays() + " días</td>");
        out.println("      </tr>");
        
        out.println("      <tr>");
        out.println("        <th>Tarifa Diaria:</th>");
        out.println("        <td>S/ " + rental.getDailyRate() + "</td>");
        out.println("      </tr>");
        
        out.println("      <tr>");
        out.println("        <th>Costo Total:</th>");
        out.println("        <td><strong>S/ " + rental.getTotalCost() + "</strong></td>");
        out.println("      </tr>");
        
        if (rental.getNotes() != null && !rental.getNotes().isEmpty()) {
            out.println("      <tr>");
            out.println("        <th>Notas:</th>");
            out.println("        <td>" + escapeHtml(rental.getNotes()) + "</td>");
            out.println("      </tr>");
        }
        
        out.println("      <tr>");
        out.println("        <th>Creado:</th>");
        out.println("        <td>" + rental.getCreatedAt() + "</td>");
        out.println("      </tr>");
        
        out.println("    </table>");
        out.println("  </div>");
        out.println("</div>");
        
        out.println("<div class=\"text-end mt-3\">");
        out.println("  <button type=\"button\" class=\"btn btn-secondary\" data-bs-dismiss=\"modal\">Cerrar</button>");
        out.println("</div>");
    }

    /**
     * Crea un nuevo alquiler.
     */
    private void handleCreateRental(HttpServletRequest request, HttpServletResponse response) 
            throws Exception {
        
        IRentalService rentalService = getService(IRentalService.class);
        
        String userId = request.getParameter("userId");
        String bookId = request.getParameter("bookId");
        String rentalDaysStr = request.getParameter("rentalDays");
        String notes = request.getParameter("notes");
        
        if (userId == null || userId.trim().isEmpty() || 
            bookId == null || bookId.trim().isEmpty() ||
            rentalDaysStr == null || rentalDaysStr.trim().isEmpty()) {
            request.getSession().setAttribute("error", "Usuario, libro y días de alquiler son requeridos");
            response.sendRedirect(request.getContextPath() + ADMIN_PANEL);
            return;
        }
        
        int rentalDays;
        try {
            rentalDays = Integer.parseInt(rentalDaysStr);
            if (rentalDays <= 0) {
                request.getSession().setAttribute("error", "Los días de alquiler deben ser mayor a 0");
                response.sendRedirect(request.getContextPath() + ADMIN_PANEL);
                return;
            }
        } catch (NumberFormatException e) {
            request.getSession().setAttribute("error", "Días de alquiler inválido");
            response.sendRedirect(request.getContextPath() + ADMIN_PANEL);
            return;
        }
        
        try {
            rentalService.createRental(userId, bookId, rentalDays, notes);
            request.getSession().setAttribute("success", "Alquiler creado exitosamente");
        } catch (IllegalStateException e) {
            request.getSession().setAttribute("error", e.getMessage());
        }
        
        response.sendRedirect(request.getContextPath() + ADMIN_PANEL);
    }

    /**
     * Marca un alquiler como devuelto.
     */
    private void handleMarkAsReturned(HttpServletRequest request, HttpServletResponse response) 
            throws Exception {
        
        String rentalId = request.getParameter("rentalId");
        
        if (rentalId == null || rentalId.isEmpty()) {
            request.getSession().setAttribute("error", "ID de alquiler requerido");
            response.sendRedirect(request.getContextPath() + ADMIN_PANEL);
            return;
        }
        
        IRentalService rentalService = getService(IRentalService.class);
        
        try {
            rentalService.markAsReturned(rentalId);
            request.getSession().setAttribute("success", "Alquiler marcado como devuelto exitosamente");
        } catch (IllegalArgumentException e) {
            request.getSession().setAttribute("error", e.getMessage());
        }
        
        response.sendRedirect(request.getContextPath() + ADMIN_PANEL);
    }

    /**
     * Cancela un alquiler.
     */
    private void handleCancelRental(HttpServletRequest request, HttpServletResponse response) 
            throws Exception {
        
        String rentalId = request.getParameter("rentalId");
        
        if (rentalId == null || rentalId.isEmpty()) {
            request.getSession().setAttribute("error", "ID de alquiler requerido");
            response.sendRedirect(request.getContextPath() + ADMIN_PANEL);
            return;
        }
        
        IRentalService rentalService = getService(IRentalService.class);
        
        try {
            rentalService.cancelRental(rentalId);
            request.getSession().setAttribute("success", "Alquiler cancelado exitosamente");
        } catch (IllegalArgumentException e) {
            request.getSession().setAttribute("error", e.getMessage());
        }
        
        response.sendRedirect(request.getContextPath() + ADMIN_PANEL);
    }
}