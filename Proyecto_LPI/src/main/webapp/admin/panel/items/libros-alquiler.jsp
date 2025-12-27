<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page import="java.time.LocalDateTime" %>
<%@ page import="java.time.LocalDate" %>
<%@ page import="java.time.temporal.ChronoUnit" %>
<%@ page import="java.time.format.DateTimeFormatter" %>
<jsp:useBean id="activeRentals" type="dto.shared.PagedResult" scope="request" />
<c:set var="totalActiveRentals" value="${totalActiveRentals != null ? totalActiveRentals : 0}" />
<c:set var="onTimeRentals" value="${onTimeRentals != null ? onTimeRentals : 0}" />
<c:set var="dueSoonRentals" value="${dueSoonRentals != null ? dueSoonRentals : 0}" />
<c:set var="overdueRentals" value="${overdueRentals != null ? overdueRentals : 0}" />
<c:set var="searchValue" value="${searchValue != null ? searchValue : ''}" />
<c:set var="statusValue" value="${statusValue != null ? statusValue : ''}" />
<c:set var="dateFromValue" value="${dateFromValue != null ? dateFromValue : ''}" />
<c:set var="dateToValue" value="${dateToValue != null ? dateToValue : ''}" />
<%
    DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
    LocalDateTime now = LocalDateTime.now();
    Integer dueSoonDays = (Integer) request.getAttribute("dueSoonDays");
%>

<div class="content-header">
    <div class="d-flex justify-content-between align-items-center">
        <div>
            <h2 class="mb-0"><i class="bi bi-bookmark-check me-2"></i>Libros en Alquiler</h2>
            <p class="text-muted mb-0">Reporte de libros actualmente alquilados</p>
        </div>
        <div>
            <button class="btn btn-success" data-bs-toggle="tooltip" title="Exportar a Excel">
                <i class="bi bi-file-earmark-excel"></i> Exportar
            </button>
            <button class="btn btn-primary" data-bs-toggle="tooltip" title="Imprimir reporte">
                <i class="bi bi-printer"></i> Imprimir
            </button>
        </div>
    </div>
</div>

<!-- Summary Cards -->
<div class="row g-3 mb-4">
    <div class="col-md-3">
        <div class="card bg-primary text-white">
            <div class="card-body">
                <h6>Total Alquilados</h6>
                <h3 class="mb-0">${totalActiveRentals}</h3>
            </div>
        </div>
    </div>
    <div class="col-md-3">
        <div class="card bg-success text-white">
            <div class="card-body">
                <h6>Al Día</h6>
                <h3 class="mb-0">${onTimeRentals}</h3>
            </div>
        </div>
    </div>
    <div class="col-md-3">
        <div class="card bg-warning text-white">
            <div class="card-body">
                <h6>Por Vencer</h6>
                <h3 class="mb-0">${dueSoonRentals}</h3>
            </div>
        </div>
    </div>
    <div class="col-md-3">
        <div class="card bg-danger text-white">
            <div class="card-body">
                <h6>Vencidos</h6>
                <h3 class="mb-0">${overdueRentals}</h3>
            </div>
        </div>
    </div>
</div>

<!-- Filters -->
<div class="card mb-4">
    <div class="card-body">
        <form method="GET" action="${pageContext.request.contextPath}/admin/panel">
            <input type="hidden" name="page" value="libros-alquiler">
            <div class="row g-3">
                <div class="col-md-3">
                    <label class="form-label">Buscar</label>
                    <input type="text" class="form-control" name="search" 
                           value="${searchValue}"
                           placeholder="Libro, usuario o código...">
                </div>
                <div class="col-md-3">
                    <label class="form-label">Estado</label>
                    <select class="form-select" name="status">
                        <option value="" ${statusValue == '' ? 'selected' : ''}>Todos</option>
                        <option value="vencer" ${statusValue == 'vencer' ? 'selected' : ''}>Por vencer</option>
                        <option value="vencido" ${statusValue == 'vencido' ? 'selected' : ''}>Vencido</option>
                    </select>
                </div>
                <div class="col-md-3">
                    <label class="form-label">Fecha de alquiler desde</label>
                    <input type="date" class="form-control" name="dateFrom" 
                           value="${dateFromValue}">
                </div>
                <div class="col-md-3">
                    <label class="form-label">Fecha de alquiler hasta</label>
                    <input type="date" class="form-control" name="dateTo" 
                           value="${dateToValue}">
                </div>
            </div>
            <div class="mt-3">
                <button type="submit" class="btn btn-primary">
                    <i class="bi bi-search"></i> Buscar
                </button>
                <a href="${pageContext.request.contextPath}/admin/panel?page=libros-alquiler" 
                   class="btn btn-secondary">
                    <i class="bi bi-x-circle"></i> Limpiar
                </a>
            </div>
        </form>
    </div>
</div>

<!-- Report Table -->
<div class="card">
    <div class="card-header d-flex justify-content-between align-items-center">
        <h5 class="mb-0">Listado de Alquileres Activos</h5>
        <span class="badge bg-secondary">
            ${activeRentals.totalItems} resultado(s)
        </span>
    </div>
    <div class="card-body">
        <c:choose>
            <c:when test="${activeRentals.totalItems == 0}">
                <div class="alert alert-info">
                    <i class="bi bi-info-circle me-2"></i>
                    <c:choose>
                        <c:when test="${not empty searchValue or not empty statusValue or not empty dateFromValue or not empty dateToValue}">
                            No se encontraron alquileres con los filtros aplicados.
                        </c:when>
                        <c:otherwise>
                            No hay alquileres activos en este momento.
                        </c:otherwise>
                    </c:choose>
                </div>
            </c:when>
            <c:otherwise>
                <div class="mb-3">
                    <small class="text-muted">
                        Mostrando ${activeRentals.startItem} - ${activeRentals.endItem} de ${activeRentals.totalItems} resultados
                    </small>
                </div>
                <div class="table-responsive">
                    <table class="table table-hover table-striped">
                        <thead class="table-dark">
                            <tr>
                                <th>Código</th>
                                <th>Libro</th>
                                <th>Autor</th>
                                <th>Usuario</th>
                                <th>Fecha Alquiler</th>
                                <th>Fecha Vencimiento</th>
                                <th>Días Restantes</th>
                                <th>Estado</th>
                                <th>Acciones</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach items="${activeRentals.items}" var="rental">
                                <% 
                                    model.Rental rental = (model.Rental) pageContext.getAttribute("rental");
    
                                    LocalDateTime dueDateTime = rental.getDueDate();
                                    LocalDate dueDateOnly = dueDateTime.toLocalDate();
                                    LocalDate nowOnly = now.toLocalDate();
                                    
                                    long daysRemaining = ChronoUnit.DAYS.between(nowOnly, dueDateOnly);
                                    
                                    String statusLabel = "En Proceso";
                                    String statusClass = "bg-success";
                                    String daysClass = "bg-success";
                                    String daysText = daysRemaining > 1 ? daysRemaining + " días" : "1 día";
                                    
                                    if (now.isAfter(dueDateTime)) {
                                        statusLabel = "Vencido";
                                        statusClass = "bg-danger";
                                        daysClass = "bg-danger";
                                        
                                        long daysPassed = ChronoUnit.DAYS.between(dueDateOnly, nowOnly);
                                        long daysOverdue = daysPassed;
                                        
                                        daysText = daysOverdue > 1 ? daysOverdue + " días atrasado" : "1 día atrasado";
                                    } else if (daysRemaining == 0) {
                                        daysText = "Hoy";
                                        daysClass = "bg-warning";
                                    } else if (daysRemaining <= dueSoonDays) {
                                        daysClass = "bg-warning";
                                    }
                                    
                                    String bookTitle = "N/A";
                                    String authorName = "N/A";
                                    String userEmail = "N/A";
                                    
                                    if (rental.getBookCopy() != null && rental.getBookCopy().getBook() != null) {
                                        bookTitle = rental.getBookCopy().getBook().getTitle();
                                        if (rental.getBookCopy().getBook().getAuthor() != null) {
                                            authorName = rental.getBookCopy().getBook().getAuthor().getFullName();
                                        }
                                    }
                                    
                                    if (rental.getUser() != null) {
                                        userEmail = rental.getUser().getEmail();
                                    }
                                    
                                    String rentalDateStr = rental.getRentalDate().format(dateFormatter);
                                    String dueDateStr = rental.getDueDate().format(dateFormatter);
                                    
                                    pageContext.setAttribute("bookTitle", bookTitle);
                                    pageContext.setAttribute("authorName", authorName);
                                    pageContext.setAttribute("userEmail", userEmail);
                                    pageContext.setAttribute("rentalDateStr", rentalDateStr);
                                    pageContext.setAttribute("dueDateStr", dueDateStr);
                                    pageContext.setAttribute("daysText", daysText);
                                    pageContext.setAttribute("daysClass", daysClass);
                                    pageContext.setAttribute("statusLabel", statusLabel);
                                    pageContext.setAttribute("statusClass", statusClass);
                                %>
                                <tr>
                                    <td><code class="small">${rental.rentalId}</code></td>
                                    <td><strong>${bookTitle}</strong></td>
                                    <td>${authorName}</td>
                                    <td>${userEmail}</td>
                                    <td>${rentalDateStr}</td>
                                    <td>${dueDateStr}</td>
                                    <td><span class="badge ${daysClass}">${daysText}</span></td>
                                    <td><span class="badge ${statusClass}">${statusLabel}</span></td>
                                    <td>
                                        <button class="btn btn-sm btn-info view-rental-btn" 
                                                data-rental-id="${rental.rentalId}"
                                                data-bs-toggle="tooltip" 
                                                title="Ver detalles">
                                            <i class="bi bi-eye"></i>
                                        </button>
                                    </td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                </div>
                
                <!-- Pagination -->
                <c:if test="${activeRentals.totalPages > 1}">
                    <nav aria-label="Paginación de alquileres activos" class="mt-4">
                        <ul class="pagination justify-content-center">
                            <%-- Botón Anterior --%>
                            <li class="page-item ${!activeRentals.hasPreviousPage() ? 'disabled' : ''}">
                                <a class="page-link" 
                                   href="${pageContext.request.contextPath}/admin/panel?page=libros-alquiler&p=${activeRentals.previousPage}&search=${searchValue}&status=${statusValue}&dateFrom=${dateFromValue}&dateTo=${dateToValue}">
                                    <i class="bi bi-chevron-left"></i> Anterior
                                </a>
                            </li>
                            
                            <%-- Números de página --%>
                            <c:set var="pageRange" value="${activeRentals.getPageRange(5)}" />
                            
                            <c:if test="${pageRange[0] > 1}">
                                <li class="page-item">
                                    <a class="page-link" 
                                       href="${pageContext.request.contextPath}/admin/panel?page=libros-alquiler&p=1&search=${searchValue}&status=${statusValue}&dateFrom=${dateFromValue}&dateTo=${dateToValue}">
                                        1
                                    </a>
                                </li>
                                <c:if test="${pageRange[0] > 2}">
                                    <li class="page-item disabled"><span class="page-link">...</span></li>
                                </c:if>
                            </c:if>
                            
                            <c:forEach begin="${pageRange[0]}" end="${pageRange[1]}" var="i">
                                <li class="page-item ${i == activeRentals.currentPage ? 'active' : ''}">
                                    <a class="page-link" 
                                       href="${pageContext.request.contextPath}/admin/panel?page=libros-alquiler&p=${i}&search=${searchValue}&status=${statusValue}&dateFrom=${dateFromValue}&dateTo=${dateToValue}">
                                        ${i}
                                    </a>
                                </li>
                            </c:forEach>
                            
                            <c:if test="${pageRange[1] < activeRentals.totalPages}">
                                <c:if test="${pageRange[1] < activeRentals.totalPages - 1}">
                                    <li class="page-item disabled"><span class="page-link">...</span></li>
                                </c:if>
                                <li class="page-item">
                                    <a class="page-link" 
                                       href="${pageContext.request.contextPath}/admin/panel?page=libros-alquiler&p=${activeRentals.totalPages}&search=${searchValue}&status=${statusValue}&dateFrom=${dateFromValue}&dateTo=${dateToValue}">
                                        ${activeRentals.totalPages}
                                    </a>
                                </li>
                            </c:if>
                            
                            <%-- Botón Siguiente --%>
                            <li class="page-item ${!activeRentals.hasNextPage() ? 'disabled' : ''}">
                                <a class="page-link" 
                                   href="${pageContext.request.contextPath}/admin/panel?page=libros-alquiler&p=${activeRentals.nextPage}&search=${searchValue}&status=${statusValue}&dateFrom=${dateFromValue}&dateTo=${dateToValue}">
                                    Siguiente <i class="bi bi-chevron-right"></i>
                                </a>
                            </li>
                        </ul>
                    </nav>
                </c:if>
            </c:otherwise>
        </c:choose>
    </div>
</div>

<!-- Modal para ver detalles del alquiler -->
<div class="modal fade" id="viewRentalModal" tabindex="-1" aria-labelledby="viewRentalModalLabel" aria-hidden="true">
    <div class="modal-dialog modal-lg">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="viewRentalModalLabel">Detalles del Alquiler</h5>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <div class="modal-body" id="rentalDetailsContent">
                <div class="text-center">
                    <div class="spinner-border" role="status">
                        <span class="visually-hidden">Cargando...</span>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<script>
document.addEventListener('DOMContentLoaded', function() {
    const viewButtons = document.querySelectorAll('.view-rental-btn');
    const modal = new bootstrap.Modal(document.getElementById('viewRentalModal'));
    const modalContent = document.getElementById('rentalDetailsContent');
    
    viewButtons.forEach(button => {
        button.addEventListener('click', function() {
            const rentalId = this.getAttribute('data-rental-id');
            
            modalContent.innerHTML = `
                <div class="text-center">
                    <div class="spinner-border" role="status">
                        <span class="visually-hidden">Cargando...</span>
                    </div>
                </div>
            `;
            
            modal.show();
            
            fetch('${pageContext.request.contextPath}/rental?action=view&id=' + rentalId)
                .then(response => response.text())
                .then(html => {
                    modalContent.innerHTML = html;
                })
                .catch(error => {
                    modalContent.innerHTML = `
                        <div class="alert alert-danger">
                            <i class="bi bi-exclamation-triangle me-2"></i>
                            Error al cargar los detalles del alquiler.
                        </div>
                    `;
                    console.error('Error:', error);
                });
        });
    });
});

var tooltipTriggerList = [].slice.call(document.querySelectorAll('[data-bs-toggle="tooltip"]'))
var tooltipList = tooltipTriggerList.map(function (tooltipTriggerEl) {
    return new bootstrap.Tooltip(tooltipTriggerEl)
});
</script>
