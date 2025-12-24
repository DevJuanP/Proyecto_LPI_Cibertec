<!-- src/main/webapp/admin/panel/items/mantenimiento-alquileres.jsp -->
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.time.format.DateTimeFormatter" %>
<%@ page import="model.Rental" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>

<%
    DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
    request.setAttribute("dateFormatter", dateFormatter);
%>

<div class="content-header">
    <div class="d-flex justify-content-between align-items-center">
        <div>
            <h2 class="mb-0"><i class="bi bi-calendar-check me-2"></i>Gestión de Alquileres</h2>
            <p class="text-muted mb-0">Administración de alquileres de libros</p>
        </div>
        <div>
            <button class="btn btn-success" data-bs-toggle="modal" data-bs-target="#addRentalModal">
                <i class="bi bi-plus-circle"></i> Nuevo Alquiler
            </button>
        </div>
    </div>
</div>

<!-- Quick Stats -->
<div class="row g-3 mb-4">
    <div class="col-md-3">
        <div class="card border-start border-primary border-4">
            <div class="card-body">
                <div class="d-flex justify-content-between">
                    <div>
                        <small class="text-muted">Total Alquileres</small>
                        <h4 class="mb-0">${totalRentals}</h4>
                    </div>
                    <i class="bi bi-book fs-2 text-primary"></i>
                </div>
            </div>
        </div>
    </div>
    <div class="col-md-3">
        <div class="card border-start border-warning border-4">
            <div class="card-body">
                <div class="d-flex justify-content-between">
                    <div>
                        <small class="text-muted">En Proceso</small>
                        <h4 class="mb-0">${activeRentals}</h4>
                    </div>
                    <i class="bi bi-hourglass-split fs-2 text-warning"></i>
                </div>
            </div>
        </div>
    </div>
    <div class="col-md-3">
        <div class="card border-start border-danger border-4">
            <div class="card-body">
                <div class="d-flex justify-content-between">
                    <div>
                        <small class="text-muted">Vencidos</small>
                        <h4 class="mb-0">${overdueRentals}</h4>
                    </div>
                    <i class="bi bi-exclamation-triangle fs-2 text-danger"></i>
                </div>
            </div>
        </div>
    </div>
    <div class="col-md-3">
        <div class="card border-start border-info border-4">
            <div class="card-body">
                <div class="d-flex justify-content-between">
                    <div>
                        <small class="text-muted">Registros en Página</small>
                        <h4 class="mb-0">${fn:length(rentalsResult.items)}</h4>
                    </div>
                    <i class="bi bi-list-ul fs-2 text-info"></i>
                </div>
            </div>
        </div>
    </div>
</div>

<!-- Search and Filters -->
<div class="card mb-4">
    <div class="card-body">
        <form method="get" action="${pageContext.request.contextPath}/admin/panel">
            <input type="hidden" name="page" value="alquileres">
            <div class="row g-3">
                <div class="col-md-4">
                    <label class="form-label">Buscar</label>
                    <div class="input-group">
                        <span class="input-group-text"><i class="bi bi-search"></i></span>
                        <input type="text" name="search" class="form-control" 
                               placeholder="Email, título o ISBN..." value="${searchValue}">
                    </div>
                </div>
                <div class="col-md-3">
                    <label class="form-label">Usuario</label>
                    <select name="userId" class="form-select">
                        <option value="">Todos</option>
                        <c:forEach var="user" items="${users}">
                            <option value="${user.userId}" 
                                ${user.userId == userIdValue ? 'selected' : ''}>
                                ${user.email}
                            </option>
                        </c:forEach>
                    </select>
                </div>
                <div class="col-md-3">
                    <label class="form-label">Estado</label>
                    <select name="rentalStatusId" class="form-select">
                        <option value="">Todos</option>
                        <c:forEach var="status" items="${rentalStatuses}">
                            <option value="${status.rentalStatusId}" 
                                ${status.rentalStatusId == rentalStatusIdValue ? 'selected' : ''}>
                                ${status.rentalStatusName}
                            </option>
                        </c:forEach>
                    </select>
                </div>
                <div class="col-md-2">
                    <label class="form-label">&nbsp;</label>
                    <div class="d-grid gap-2">
                        <button type="submit" class="btn btn-primary">
                            <i class="bi bi-funnel"></i> Filtrar
                        </button>
                    </div>
                </div>
            </div>
        </form>
    </div>
</div>

<!-- Success/Error Messages -->
<c:if test="${not empty sessionScope.success}">
    <div class="alert alert-success alert-dismissible fade show" role="alert">
        <i class="bi bi-check-circle me-2"></i>${sessionScope.success}
        <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
    </div>
    <c:remove var="success" scope="session" />
</c:if>

<c:if test="${not empty sessionScope.error}">
    <div class="alert alert-danger alert-dismissible fade show" role="alert">
        <i class="bi bi-exclamation-triangle me-2"></i>${sessionScope.error}
        <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
    </div>
    <c:remove var="error" scope="session" />
</c:if>

<!-- Rentals Table -->
<div class="card">
    <div class="card-header d-flex justify-content-between align-items-center">
        <h5 class="mb-0">Listado de Alquileres</h5>
        <div>
            <span class="text-muted me-3">
                Mostrando ${rentalsResult.startItem} - ${rentalsResult.endItem} de ${rentalsResult.totalItems}
            </span>
        </div>
    </div>
    <div class="card-body">
        <c:choose>
            <c:when test="${empty rentalsResult.items}">
                <div class="text-center py-5">
                    <i class="bi bi-inbox fs-1 text-muted"></i>
                    <p class="text-muted mt-3">No se encontraron alquileres</p>
                    <c:if test="${not empty searchValue or not empty userIdValue or not empty rentalStatusIdValue}">
                        <a href="${pageContext.request.contextPath}/admin/panel?page=alquileres" 
                           class="btn btn-outline-primary">
                            <i class="bi bi-x-circle"></i> Limpiar filtros
                        </a>
                    </c:if>
                </div>
            </c:when>
            <c:otherwise>
                <div class="table-responsive">
                    <table class="table table-hover">
                        <thead class="table-dark">
                            <tr>
                                <th>Usuario</th>
                                <th>Libro</th>
                                <th>Fecha Alquiler</th>
                                <th>Fecha Vencimiento</th>
                                <th>Días</th>
                                <th>Costo Total</th>
                                <th>Estado</th>
                                <th class="text-center">Acciones</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="rental" items="${rentalsResult.items}">
                                <tr>
                                    <td>
                                        <div class="d-flex align-items-center">
                                            <i class="bi bi-person-circle fs-4 text-muted me-2"></i>
                                            <div>
                                                <div class="fw-medium">${rental.user.email}</div>
                                            </div>
                                        </div>
                                    </td>
                                    <td>
                                        <div class="fw-medium">${rental.bookCopy.book.title}</div>
                                        <small class="text-muted">${rental.bookCopy.book.isbn}</small>
                                    </td>
                                    <td>
                                        ${rental.rentalDate.format(dateFormatter)}
                                    </td>
                                    <td>
                                        ${rental.dueDate.format(dateFormatter)}
                                        <c:if test="${rental.rentalStatus.rentalStatusName == 'En Proceso'}">
                                            <c:set var="isOverdue" value="false"/>
                                            <%
                                                model.Rental rental = (model.Rental) pageContext.getAttribute("rental");
                                                if (rental != null && rental.getDueDate() != null) {
                                                    boolean isOverdue = rental.getDueDate().isBefore(java.time.LocalDateTime.now());
                                                    pageContext.setAttribute("isOverdue", isOverdue);
                                                }
                                            %>
                                            <c:if test="${isOverdue}">
                                                <br><span class="badge bg-danger">Vencido</span>
                                            </c:if>
                                        </c:if>
                                    </td>
                                    <td>${rental.rentalDays} días</td>
                                    <td>
                                        <strong>S/ ${rental.totalCost}</strong>
                                        <br><small class="text-muted">S/ ${rental.dailyRate}/día</small>
                                    </td>
                                    <td>
                                        <c:choose>
                                            <c:when test="${rental.rentalStatus.rentalStatusName == 'En Proceso'}">
                                                <span class="badge bg-warning">En Proceso</span>
                                            </c:when>
                                            <c:when test="${rental.rentalStatus.rentalStatusName == 'Devuelto'}">
                                                <span class="badge bg-success">Devuelto</span>
                                            </c:when>
                                            <c:when test="${rental.rentalStatus.rentalStatusName == 'Cancelado'}">
                                                <span class="badge bg-danger">Cancelado</span>
                                            </c:when>
                                            <c:otherwise>
                                                <span class="badge bg-secondary">${rental.rentalStatus.rentalStatusName}</span>
                                            </c:otherwise>
                                        </c:choose>
                                    </td>
                                    <td class="text-center">
                                        <div class="btn-group btn-group-sm" role="group">
                                            <button type="button" class="btn btn-outline-info" 
                                                    onclick="viewRental('${rental.rentalId}')"
                                                    title="Ver detalles">
                                                <i class="bi bi-eye"></i>
                                            </button>
                                            <c:if test="${rental.rentalStatus.rentalStatusName == 'En Proceso'}">
                                                <button type="button" class="btn btn-outline-success" 
                                                        onclick="markAsReturned('${rental.rentalId}')"
                                                        title="Marcar como devuelto">
                                                    <i class="bi bi-check-circle"></i>
                                                </button>
                                                <button type="button" class="btn btn-outline-danger" 
                                                        onclick="cancelRental('${rental.rentalId}')"
                                                        title="Cancelar">
                                                    <i class="bi bi-x-circle"></i>
                                                </button>
                                            </c:if>
                                        </div>
                                    </td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                </div>

                <!-- Pagination -->
                <c:if test="${rentalsResult.totalPages > 1}">
                    <nav aria-label="Page navigation" class="mt-4">
                        <ul class="pagination justify-content-center">
                            <li class="page-item ${rentalsResult.currentPage == 1 ? 'disabled' : ''}">
                                <a class="page-link" 
                                   href="${pageContext.request.contextPath}/admin/panel?page=alquileres&p=${rentalsResult.previousPage}&search=${searchValue}&userId=${userIdValue}&rentalStatusId=${rentalStatusIdValue}">
                                    <i class="bi bi-chevron-left"></i> Anterior
                                </a>
                            </li>

                            <c:forEach begin="${rentalsResult.startPage}" end="${rentalsResult.endPage}" var="pageNum">
                                <li class="page-item ${rentalsResult.currentPage == pageNum ? 'active' : ''}">
                                    <a class="page-link" 
                                       href="${pageContext.request.contextPath}/admin/panel?page=alquileres&p=${pageNum}&search=${searchValue}&userId=${userIdValue}&rentalStatusId=${rentalStatusIdValue}">
                                        ${pageNum}
                                    </a>
                                </li>
                            </c:forEach>

                            <li class="page-item ${rentalsResult.currentPage == rentalsResult.totalPages ? 'disabled' : ''}">
                                <a class="page-link" 
                                   href="${pageContext.request.contextPath}/admin/panel?page=alquileres&p=${rentalsResult.nextPage}&search=${searchValue}&userId=${userIdValue}&rentalStatusId=${rentalStatusIdValue}">
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

<!-- Add Rental Modal -->
<div class="modal fade" id="addRentalModal" tabindex="-1" aria-labelledby="addRentalModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header bg-primary text-white">
                <h5 class="modal-title" id="addRentalModalLabel">
                    <i class="bi bi-plus-circle me-2"></i>Crear Nuevo Alquiler
                </h5>
                <button type="button" class="btn-close btn-close-white" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <form action="${pageContext.request.contextPath}/rental" method="post" id="addRentalForm">
                <input type="hidden" name="action" value="create">
                <div class="modal-body">
                    <div class="mb-3">
                        <label class="form-label">Usuario *</label>
                        <select name="userId" class="form-select" required>
                            <option value="">Seleccione un usuario</option>
                            <c:forEach var="user" items="${users}">
                                <option value="${user.userId}">${user.email}</option>
                            </c:forEach>
                        </select>
                    </div>
                    
                    <div class="mb-3">
                        <label class="form-label">Libro *</label>
                        <select name="bookId" class="form-select" required>
                            <option value="">Seleccione un ejemplar</option>
                            <c:forEach var="book" items="${availableBooks}">
                                <option value="${book.bookId}">
                                    ${book.title} - ${book.isbn}
                                </option>
                            </c:forEach>
                        </select>
                        <div class="form-text">Solo se muestran libros disponibles</div>
                    </div>
                    
                    <div class="mb-3">
                        <label class="form-label">Días de Alquiler *</label>
                        <input type="number" name="rentalDays" class="form-control" 
                               placeholder="Ej: 7" required min="1" max="365" value="7"
                               onchange="calculateTotal()">
                    </div>
                    
                    <div class="mb-3">
                        <label class="form-label">Tarifa Diaria</label>
                        <input type="text" class="form-control" value="S/ ${defaultDailyRate}" disabled>
                        <input type="hidden" id="dailyRate" value="${defaultDailyRate}">
                    </div>
                    
                    <div class="mb-3">
                        <label class="form-label">Costo Total Estimado</label>
                        <input type="text" id="totalCostDisplay" class="form-control fw-bold" value="S/ 0.00" disabled>
                    </div>
                    
                    <div class="mb-3">
                        <label class="form-label">Notas (Opcional)</label>
                        <textarea name="notes" class="form-control" rows="3" 
                                  placeholder="Observaciones sobre el alquiler..."></textarea>
                    </div>
                    
                    <div class="alert alert-info">
                        <i class="bi bi-info-circle me-2"></i>
                        El alquiler se creará con estado <strong>En Proceso</strong> y el ejemplar 
                        será marcado como <strong>Alquilado</strong>.
                    </div>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">
                        <i class="bi bi-x-circle"></i> Cancelar
                    </button>
                    <button type="submit" class="btn btn-primary">
                        <i class="bi bi-save"></i> Crear Alquiler
                    </button>
                </div>
            </form>
        </div>
    </div>
</div>

<!-- View Rental Modal -->
<div class="modal fade" id="rentalDetailModal" tabindex="-1" aria-hidden="true">
    <div class="modal-dialog modal-lg">
        <div class="modal-content">
            <div class="modal-header bg-info text-white">
                <h5 class="modal-title">
                    <i class="bi bi-info-circle me-2"></i>Detalle del Alquiler
                </h5>
                <button type="button" class="btn-close btn-close-white" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <div class="modal-body" id="rentalDetailContent">
                <!-- Content loaded via JavaScript -->
            </div>
        </div>
    </div>
</div>

<!-- Mark as Returned Modal -->
<div class="modal fade" id="markReturnedModal" tabindex="-1" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header bg-success text-white">
                <h5 class="modal-title">
                    <i class="bi bi-check-circle me-2"></i>Marcar como Devuelto
                </h5>
                <button type="button" class="btn-close btn-close-white" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <div class="modal-body">
                <p>¿Está seguro de que desea marcar este alquiler como <strong>Devuelto</strong>?</p>
                <p class="text-muted mb-0">
                    <i class="bi bi-info-circle me-1"></i>
                    El ejemplar será marcado como Disponible nuevamente.
                </p>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">
                    <i class="bi bi-x-circle"></i> Cancelar
                </button>
                <form action="${pageContext.request.contextPath}/rental" method="post" style="display: inline;">
                    <input type="hidden" name="action" value="markReturned">
                    <input type="hidden" name="rentalId" id="markReturnedRentalId">
                    <button type="submit" class="btn btn-success">
                        <i class="bi bi-check-circle"></i> Confirmar Devolución
                    </button>
                </form>
            </div>
        </div>
    </div>
</div>

<!-- Cancel Rental Modal -->
<div class="modal fade" id="cancelRentalModal" tabindex="-1" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header bg-danger text-white">
                <h5 class="modal-title">
                    <i class="bi bi-exclamation-triangle me-2"></i>Cancelar Alquiler
                </h5>
                <button type="button" class="btn-close btn-close-white" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <div class="modal-body">
                <p>¿Está seguro de que desea <strong>cancelar</strong> este alquiler?</p>
                <p class="text-danger mb-0">
                    <i class="bi bi-exclamation-circle me-1"></i>
                    Esta acción marcará el alquiler como cancelado y el ejemplar quedará disponible.
                </p>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">
                    <i class="bi bi-x-circle"></i> No, regresar
                </button>
                <form action="${pageContext.request.contextPath}/rental" method="post" style="display: inline;">
                    <input type="hidden" name="action" value="cancel">
                    <input type="hidden" name="rentalId" id="cancelRentalId">
                    <button type="submit" class="btn btn-danger">
                        <i class="bi bi-trash"></i> Sí, cancelar
                    </button>
                </form>
            </div>
        </div>
    </div>
</div>

<jsp:useBean id="now" class="java.util.Date"/>

<script>
    // Calculate total cost
    function calculateTotal() {
        const rentalDays = parseInt(document.querySelector('[name="rentalDays"]').value) || 0;
        const dailyRate = parseFloat(document.getElementById('dailyRate').value) || 0;
        const totalCost = (rentalDays * dailyRate).toFixed(2);
        document.getElementById('totalCostDisplay').value = 'S/ ' + totalCost;
    }

    // Initialize calculation on page load
    document.addEventListener('DOMContentLoaded', function() {
        calculateTotal();
    });

    // View rental details
    function viewRental(rentalId) {
        const modal = new bootstrap.Modal(document.getElementById('rentalDetailModal'));
        document.getElementById('rentalDetailContent').innerHTML = 
            '<div class="text-center py-4"><div class="spinner-border text-primary" role="status"></div><p class="mt-2">Cargando...</p></div>';
        modal.show();
        
        fetch('${pageContext.request.contextPath}/rental?action=view&id=' + rentalId)
            .then(response => response.text())
            .then(html => {
                document.getElementById('rentalDetailContent').innerHTML = html;
            })
            .catch(error => {
                document.getElementById('rentalDetailContent').innerHTML = 
                    '<div class="alert alert-danger">Error al cargar los detalles</div>';
            });
    }

    // Mark as returned
    function markAsReturned(rentalId) {
        document.getElementById('markReturnedRentalId').value = rentalId;
        const modal = new bootstrap.Modal(document.getElementById('markReturnedModal'));
        modal.show();
    }

    // Cancel rental
    function cancelRental(rentalId) {
        document.getElementById('cancelRentalId').value = rentalId;
        const modal = new bootstrap.Modal(document.getElementById('cancelRentalModal'));
        modal.show();
    }

    // Form validation
    const addRentalForm = document.getElementById('addRentalForm');
    if (addRentalForm) {
        addRentalForm.addEventListener('submit', function(event) {
            if (!addRentalForm.checkValidity()) {
                event.preventDefault();
                event.stopPropagation();
            }
            addRentalForm.classList.add('was-validated');
        }, false);
    }
</script>
