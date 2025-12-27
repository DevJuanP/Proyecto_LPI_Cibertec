<!-- src/main/webapp/admin/panel/items/mantenimiento-ejemplares.jsp -->
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>

<div class="content-header">
    <div class="d-flex justify-content-between align-items-center">
        <div>
            <h2 class="mb-0"><i class="bi bi-collection me-2"></i>Mantenimiento de Ejemplares</h2>
            <p class="text-muted mb-0">Gestión de copias físicas de libros</p>
        </div>
        <div>
            <button class="btn btn-success" data-bs-toggle="modal" data-bs-target="#addBookCopyModal">
                <i class="bi bi-plus-circle"></i> Agregar Ejemplares
            </button>
            <button class="btn btn-warning" id="btnBatchUpdate" disabled>
                <i class="bi bi-arrow-repeat"></i> Cambiar Estado
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
                        <small class="text-muted">Total Ejemplares</small>
                        <h4 class="mb-0">${copiesResult.totalItems}</h4>
                    </div>
                    <i class="bi bi-collection-fill fs-2 text-primary"></i>
                </div>
            </div>
        </div>
    </div>
    <div class="col-md-3">
        <div class="card border-start border-success border-4">
            <div class="card-body">
                <div class="d-flex justify-content-between">
                    <div>
                        <small class="text-muted">Disponibles</small>
                        <h4 class="mb-0">${availableCopiesCount}</h4>
                    </div>
                    <i class="bi bi-check-circle-fill fs-2 text-success"></i>
                </div>
            </div>
        </div>
    </div>
    <div class="col-md-3">
        <div class="card border-start border-primary border-4">
            <div class="card-body">
                <div class="d-flex justify-content-between">
                    <div>
                        <small class="text-muted">Alquilados</small>
                        <h4 class="mb-0">${rentedCopiesCount}</h4>
                    </div>
                    <i class="bi bi-arrow-repeat fs-2 text-primary"></i>
                </div>
            </div>
        </div>
    </div>
    <div class="col-md-3">
        <div class="card border-start border-warning border-4">
            <div class="card-body">
                <div class="d-flex justify-content-between">
                    <div>
                        <small class="text-muted">Mantenimiento</small>
                        <h4 class="mb-0">${maintenanceCopiesCount}</h4>
                    </div>
                    <i class="bi bi-tools fs-2 text-warning"></i>
                </div>
            </div>
        </div>
    </div>
</div>

<!-- Search and Filters -->
<div class="card mb-4">
    <div class="card-body">
        <form method="get" action="${pageContext.request.contextPath}/admin/panel">
            <input type="hidden" name="page" value="mantenimiento-ejemplares">
            <div class="row g-3">
                <div class="col-md-4">
                    <label class="form-label">Buscar</label>
                    <div class="input-group">
                        <span class="input-group-text"><i class="bi bi-search"></i></span>
                        <input type="text" name="search" class="form-control" 
                               placeholder="Título o ISBN del libro..." value="${searchValue}">
                    </div>
                </div>
                <div class="col-md-3">
                    <label class="form-label">Libro</label>
                    <select name="bookId" class="form-select">
                        <option value="">Todas</option>
                        <c:forEach var="book" items="${books}">
                            <option value="${book.bookId}" 
                                ${book.bookId == bookIdValue ? 'selected' : ''}>
                                ${book.title}
                            </option>
                        </c:forEach>
                    </select>
                </div>
                <div class="col-md-3">
                    <label class="form-label">Estado</label>
                    <select name="bookStatusId" class="form-select">
                        <option value="">Todas</option>
                        <c:forEach var="status" items="${bookCopyStatuses}">
                            <option value="${status.bookCopyStatusId}" 
                                ${status.bookCopyStatusId == bookStatusIdValue ? 'selected' : ''}>
                                ${status.bookCopyStatusName}
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

<!-- Book Copies Table -->
<div class="card">
    <div class="card-header d-flex justify-content-between align-items-center">
        <h5 class="mb-0">Listado de Ejemplares</h5>
        <div>
            <span class="text-muted me-3">
                Mostrando ${copiesResult.startItem} - ${copiesResult.endItem} de ${copiesResult.totalItems}
            </span>
            <label class="me-2">
                <input type="checkbox" id="selectAll" class="form-check-input"> Seleccionar todos
            </label>
        </div>
    </div>
    <div class="card-body">
        <c:choose>
            <c:when test="${empty copiesResult.items}">
                <div class="text-center py-5">
                    <i class="bi bi-inbox fs-1 text-muted"></i>
                    <p class="text-muted mt-3">No se encontraron ejemplares</p>
                    <c:if test="${not empty searchValue or not empty bookIdValue or not empty statusIdValue}">
                        <a href="${pageContext.request.contextPath}/admin/panel?page=mantenimiento-ejemplares" 
                           class="btn btn-primary">
                            <i class="bi bi-arrow-clockwise"></i> Limpiar Filtros
                        </a>
                    </c:if>
                </div>
            </c:when>
            <c:otherwise>
                <div class="table-responsive">
                    <table class="table table-hover">
                        <thead class="table-light">
                            <tr>
                                <th style="width: 50px;">
                                    <input type="checkbox" class="form-check-input" id="selectAllHeader">
                                </th>
                                <th>ID</th>
                                <th>Libro</th>
                                <th>ISBN</th>
                                <th>Autor</th>
                                <th>Estado</th>
                                <th>Notas</th>
                                <th style="width: 180px;">Acciones</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="copy" items="${copiesResult.items}">
                                <tr>
                                    <td>
                                        <c:if test="${copy.bookCopyStatus.bookCopyStatusName != 'Alquilado'}">
                                            <input type="checkbox" class="form-check-input copy-checkbox" 
                                               value="${copy.bookCopyId}"
                                               data-status="${copy.bookCopyStatus.bookCopyStatusName}">
                                        </c:if>                                        
                                    </td>
                                    <td>
                                        <code class="text-muted" style="font-size: 0.85em;">
                                            ${copy.bookCopyId}
                                        </code>
                                    </td>
                                    <td>
                                        <strong>${copy.book.title}</strong>
                                    </td>
                                    <td>
                                        <code>${copy.book.isbn}</code>
                                    </td>
                                    <td>
                                        ${copy.book.author != null ? copy.book.author.fullName : '-'}
                                    </td>
                                    <td>
                                        <c:choose>
                                            <c:when test="${copy.bookCopyStatus.bookCopyStatusName == 'Disponible'}">
                                                <span class="badge bg-success">Disponible</span>
                                            </c:when>
                                            <c:when test="${copy.bookCopyStatus.bookCopyStatusName == 'Alquilado'}">
                                                <span class="badge bg-primary">Alquilado</span>
                                            </c:when>
                                            <c:when test="${copy.bookCopyStatus.bookCopyStatusName == 'Mantenimiento'}">
                                                <span class="badge bg-warning text-dark">Mantenimiento</span>
                                            </c:when>
                                            <c:when test="${copy.bookCopyStatus.bookCopyStatusName == 'Descontinuado'}">
                                                <span class="badge bg-secondary">Descontinuado</span>
                                            </c:when>
                                            <c:otherwise>
                                                <span class="badge bg-secondary">${copy.bookCopyStatus.bookCopyStatusName}</span>
                                            </c:otherwise>
                                        </c:choose>
                                    </td>
                                    <td>
                                        <c:choose>
                                            <c:when test="${not empty copy.notes}">
                                                <span class="text-truncate d-inline-block" style="max-width: 150px;" 
                                                      title="${copy.notes}">
                                                    ${copy.notes}
                                                </span>
                                            </c:when>
                                            <c:otherwise>
                                                <span class="text-muted">-</span>
                                            </c:otherwise>
                                        </c:choose>
                                    </td>
                                    <td>
                                        <div class="btn-group btn-group-sm" role="group">
                                            <button type="button" class="btn btn-info" 
                                                    onclick="viewBookCopy('${copy.bookCopyId}')"
                                                    data-bs-toggle="tooltip" title="Ver Detalles">
                                                <i class="bi bi-eye"></i>
                                            </button>
                                            <c:if test="${copy.bookCopyStatus.bookCopyStatusName != 'Alquilado'}">
                                                <button type="button" class="btn btn-warning" 
                                                        onclick="editBookCopy('${copy.bookCopyId}')"
                                                        data-bs-toggle="tooltip" title="Editar">
                                                    <i class="bi bi-pencil"></i>
                                                </button>
                                                <button type="button" class="btn btn-danger" 
                                                        onclick="deleteBookCopy('${copy.bookCopyId}', '${fn:escapeXml(copy.book.title)}', '${copy.book.isbn}')"
                                                        data-bs-toggle="tooltip" title="Eliminar">
                                                    <i class="bi bi-trash"></i>
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
                <c:if test="${copiesResult.totalPages > 1}">
                    <nav aria-label="Paginación de ejemplares" class="mt-4">
                        <ul class="pagination justify-content-center">
                            <%-- Botón Anterior --%>
                            <li class="page-item ${!copiesResult.hasPreviousPage() ? 'disabled' : ''}">
                                <a class="page-link" 
                                   href="${pageContext.request.contextPath}/admin/panel?page=mantenimiento-ejemplares&p=${copiesResult.previousPage}&search=${searchValue}&bookId=${bookIdValue}&statusId=${statusIdValue}">
                                    <i class="bi bi-chevron-left"></i> Anterior
                                </a>
                            </li>
                            
                            <%-- Números de página --%>
                            <c:set var="pageRange" value="${copiesResult.getPageRange(5)}" />
                            
                            <c:if test="${pageRange[0] > 1}">
                                <li class="page-item">
                                    <a class="page-link" 
                                       href="${pageContext.request.contextPath}/admin/panel?page=mantenimiento-ejemplares&p=1&search=${searchValue}&bookId=${bookIdValue}&statusId=${statusIdValue}">
                                        1
                                    </a>
                                </li>
                                <c:if test="${pageRange[0] > 2}">
                                    <li class="page-item disabled"><span class="page-link">...</span></li>
                                </c:if>
                            </c:if>
                            
                            <c:forEach begin="${pageRange[0]}" end="${pageRange[1]}" var="i">
                                <li class="page-item ${i == copiesResult.currentPage ? 'active' : ''}">
                                    <a class="page-link" 
                                       href="${pageContext.request.contextPath}/admin/panel?page=mantenimiento-ejemplares&p=${i}&search=${searchValue}&bookId=${bookIdValue}&statusId=${statusIdValue}">
                                        ${i}
                                    </a>
                                </li>
                            </c:forEach>
                            
                            <c:if test="${pageRange[1] < copiesResult.totalPages}">
                                <c:if test="${pageRange[1] < copiesResult.totalPages - 1}">
                                    <li class="page-item disabled"><span class="page-link">...</span></li>
                                </c:if>
                                <li class="page-item">
                                    <a class="page-link" 
                                       href="${pageContext.request.contextPath}/admin/panel?page=mantenimiento-ejemplares&p=${copiesResult.totalPages}&search=${searchValue}&bookId=${bookIdValue}&statusId=${statusIdValue}">
                                        ${copiesResult.totalPages}
                                    </a>
                                </li>
                            </c:if>
                            
                            <%-- Botón Siguiente --%>
                            <li class="page-item ${!copiesResult.hasNextPage() ? 'disabled' : ''}">
                                <a class="page-link" 
                                   href="${pageContext.request.contextPath}/admin/panel?page=mantenimiento-ejemplares&p=${copiesResult.nextPage}&search=${searchValue}&bookId=${bookIdValue}&statusId=${statusIdValue}">
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

<!-- Add Book Copy Modal -->
<div class="modal fade" id="addBookCopyModal" tabindex="-1" aria-labelledby="addBookCopyModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="addBookCopyModalLabel">
                    <i class="bi bi-plus-circle me-2"></i>Agregar Ejemplares
                </h5>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <form method="post" action="${pageContext.request.contextPath}/book-copy?action=createBatch">
                <div class="modal-body">
                    <div class="alert alert-info" role="alert">
                        <i class="bi bi-info-circle me-2"></i>
                        Los ejemplares se crearán con estado <strong>Disponible</strong>
                    </div>
                    
                    <div class="mb-3">
                        <label for="bookId" class="form-label">Libro <span class="text-danger">*</span></label>
                        <select class="form-select" id="bookId" name="bookId" required>
                            <option value="">Seleccione un libro...</option>
                            <c:forEach var="book" items="${books}">
                                <option value="${book.bookId}">
                                    ${book.title} - ${book.isbn}
                                </option>
                            </c:forEach>
                        </select>
                    </div>
                    
                    <div class="mb-3">
                        <label for="quantity" class="form-label">Cantidad <span class="text-danger">*</span></label>
                        <input type="number" class="form-control" id="quantity" name="quantity" 
                               min="1" max="100" value="1" required>
                        <small class="form-text text-muted">Máximo 100 ejemplares por operación</small>
                    </div>
                    
                    <div class="mb-3">
                        <label for="notes" class="form-label">Notas (Opcional)</label>
                        <textarea class="form-control" id="notes" name="notes" rows="3" 
                                  placeholder="Observaciones adicionales..."></textarea>
                    </div>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Cancelar</button>
                    <button type="submit" class="btn btn-success">
                        <i class="bi bi-save"></i> Crear Ejemplares
                    </button>
                </div>
            </form>
        </div>
    </div>
</div>

<!-- Batch Status Update Modal -->
<div class="modal fade" id="batchStatusModal" tabindex="-1" aria-labelledby="batchStatusModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="batchStatusModalLabel">
                    <i class="bi bi-arrow-repeat me-2"></i>Cambiar Estado Masivamente
                </h5>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <form method="post" action="${pageContext.request.contextPath}/book-copy?action=updateStatusBatch" id="batchUpdateForm">
                <div class="modal-body">
                    <div class="alert alert-warning" role="alert">
                        <i class="bi bi-exclamation-triangle me-2"></i>
                        No se puede cambiar a estado <strong>Alquilado</strong> desde este mantenimiento.
                        Los ejemplares alquilados no pueden ser modificados.
                    </div>
                    
                    <div class="mb-3">
                        <label class="form-label">Ejemplares seleccionados: <strong id="selectedCount">0</strong></label>
                    </div>
                    
                    <div class="mb-3">
                        <label for="newStatusId" class="form-label">Nuevo Estado <span class="text-danger">*</span></label>
                        <select class="form-select" id="newStatusId" name="newStatusId" required>
                            <option value="">Seleccione un estado...</option>
                            <c:forEach var="status" items="${bookCopyStatuses}">
                                <c:if test="${status.bookCopyStatusName != 'Alquilado'}">
                                    <option value="${status.bookCopyStatusId}">
                                        ${status.bookCopyStatusName}
                                    </option>
                                </c:if>
                            </c:forEach>
                        </select>
                    </div>
                    
                    <input type="hidden" name="selectedCopies" id="selectedCopiesInput">
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Cancelar</button>
                    <button type="submit" class="btn btn-warning">
                        <i class="bi bi-arrow-repeat"></i> Actualizar Estado
                    </button>
                </div>
            </form>
        </div>
    </div>
</div>

<!-- View Book Copy Modal -->
<div class="modal fade" id="viewBookCopyModal" tabindex="-1" aria-labelledby="viewBookCopyModalLabel" aria-hidden="true">
    <div class="modal-dialog modal-lg">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="viewBookCopyModalLabel">
                    <i class="bi bi-info-circle me-2"></i>Detalles del Ejemplar
                </h5>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <div class="modal-body" id="viewBookCopyContent">
                <div class="text-center py-4">
                    <div class="spinner-border text-primary" role="status">
                        <span class="visually-hidden">Cargando...</span>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<!-- Delete Confirmation Modal -->
<div class="modal fade" id="deleteBookCopyModal" tabindex="-1" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header bg-danger text-white">
                <h5 class="modal-title">
                    <i class="bi bi-exclamation-triangle me-2"></i>Confirmar Eliminación
                </h5>
                <button type="button" class="btn-close btn-close-white" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <div class="modal-body">
                <p>¿Está seguro de que desea eliminar el ejemplar del libro:</p>
                <div class="alert alert-light border mb-3">
                    <h6 class="mb-1"><strong id="deleteBookCopyTitle"></strong></h6>
                    <small class="text-muted">ISBN: <code id="deleteBookCopyIsbn"></code></small>
                </div>
                <p class="text-danger mb-0">
                    <i class="bi bi-exclamation-circle me-1"></i>
                    Esta acción no se puede deshacer.
                </p>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">
                    <i class="bi bi-x-circle"></i> Cancelar
                </button>
                <form action="${pageContext.request.contextPath}/book-copy" method="post" style="display: inline;">
                    <input type="hidden" name="action" value="delete">
                    <input type="hidden" name="bookCopyId" id="deleteBookCopyId">
                    <button type="submit" class="btn btn-danger">
                        <i class="bi bi-trash"></i> Eliminar
                    </button>
                </form>
            </div>
        </div>
    </div>
</div>

<script>
// Manejo de checkboxes
document.addEventListener('DOMContentLoaded', function() {
    const selectAllCheckbox = document.getElementById('selectAll');
    const selectAllHeaderCheckbox = document.getElementById('selectAllHeader');
    const copyCheckboxes = document.querySelectorAll('.copy-checkbox');
    const btnBatchUpdate = document.getElementById('btnBatchUpdate');
    
    // Sincronizar ambos checkboxes de "seleccionar todos"
    if (selectAllCheckbox && selectAllHeaderCheckbox) {
        selectAllCheckbox.addEventListener('change', function() {
            selectAllHeaderCheckbox.checked = this.checked;
            copyCheckboxes.forEach(cb => {
                // No seleccionar ejemplares alquilados
                if (cb.dataset.status !== 'Alquilado') {
                    cb.checked = this.checked;
                }
            });
            updateBatchButton();
        });
        
        selectAllHeaderCheckbox.addEventListener('change', function() {
            selectAllCheckbox.checked = this.checked;
            copyCheckboxes.forEach(cb => {
                if (cb.dataset.status !== 'Alquilado') {
                    cb.checked = this.checked;
                }
            });
            updateBatchButton();
        });
    }
    
    // Actualizar botón cuando se seleccionan checkboxes individuales
    copyCheckboxes.forEach(cb => {
        cb.addEventListener('change', updateBatchButton);
    });
    
    function updateBatchButton() {
        const selectedCount = document.querySelectorAll('.copy-checkbox:checked').length;
        if (btnBatchUpdate) {
            btnBatchUpdate.disabled = selectedCount === 0;
        }
    }
    
    // Abrir modal de actualización masiva
    if (btnBatchUpdate) {
        btnBatchUpdate.addEventListener('click', function() {
            const selectedCopies = Array.from(document.querySelectorAll('.copy-checkbox:checked'))
                                       .map(cb => cb.value);
            
            document.getElementById('selectedCount').textContent = selectedCopies.length;
            
            // Crear campos hidden para cada ID seleccionado
            const form = document.getElementById('batchUpdateForm');
            const existingHiddens = form.querySelectorAll('input[name="selectedCopies[]"]');
            existingHiddens.forEach(h => h.remove());
            
            selectedCopies.forEach(id => {
                const hidden = document.createElement('input');
                hidden.type = 'hidden';
                hidden.name = 'selectedCopies[]';
                hidden.value = id;
                form.appendChild(hidden);
            });
            
            const modal = new bootstrap.Modal(document.getElementById('batchStatusModal'));
            modal.show();
        });
    }
    
    // Inicializar tooltips
    var tooltipTriggerList = [].slice.call(document.querySelectorAll('[data-bs-toggle="tooltip"]'));
    var tooltipList = tooltipTriggerList.map(function (tooltipTriggerEl) {
        return new bootstrap.Tooltip(tooltipTriggerEl);
    });
});

function viewBookCopy(bookCopyId) {
    const modal = new bootstrap.Modal(document.getElementById('viewBookCopyModal'));
    const content = document.getElementById('viewBookCopyContent');
    
    content.innerHTML = '<div class="text-center py-4"><div class="spinner-border text-primary" role="status"><span class="visually-hidden">Cargando...</span></div></div>';
    
    fetch('${pageContext.request.contextPath}/book-copy?action=view&id=' + bookCopyId)
        .then(response => response.text())
        .then(html => {
            content.innerHTML = html;
            modal.show();
        })
        .catch(error => {
            content.innerHTML = '<div class="alert alert-danger">Error al cargar los detalles</div>';
            console.error('Error:', error);
        });
}

function editBookCopy(bookCopyId) {
    window.location.href = '${pageContext.request.contextPath}/admin/panel?page=edit-ejemplar&id=' + bookCopyId;
}

function deleteBookCopy(bookCopyId, bookTitle, bookIsbn) {
    document.getElementById('deleteBookCopyId').value = bookCopyId;
    document.getElementById('deleteBookCopyTitle').textContent = bookTitle;
    document.getElementById('deleteBookCopyIsbn').textContent = bookIsbn;
    
    const modal = new bootstrap.Modal(document.getElementById('deleteBookCopyModal'));
    modal.show();
}
</script>