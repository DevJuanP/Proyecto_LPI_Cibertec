<!-- src/main/webapp/admin/panel/items/mantenimiento-autores.jsp -->
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>

<div class="content-header">
    <div class="d-flex justify-content-between align-items-center">
        <div>
            <h2 class="mb-0"><i class="bi bi-person-badge me-2"></i>Mantenimiento de Autores</h2>
            <p class="text-muted mb-0">Gestión completa de autores</p>
        </div>
        <div>
            <button class="btn btn-success" data-bs-toggle="modal" data-bs-target="#addAuthorModal">
                <i class="bi bi-plus-circle"></i> Nuevo Autor
            </button>
            <button class="btn btn-primary" data-bs-toggle="tooltip" title="Importar desde Excel">
                <i class="bi bi-upload"></i> Importar
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
                        <small class="text-muted">Total Autores</small>
                        <h4 class="mb-0">${totalAuthors}</h4>
                    </div>
                    <i class="bi bi-people-fill fs-2 text-primary"></i>
                </div>
            </div>
        </div>
    </div>
    <div class="col-md-3">
        <div class="card border-start border-success border-4">
            <div class="card-body">
                <div class="d-flex justify-content-between">
                    <div>
                        <small class="text-muted">Con Obras</small>
                        <h4 class="mb-0">-</h4>
                    </div>
                    <i class="bi bi-check-circle-fill fs-2 text-success"></i>
                </div>
            </div>
        </div>
    </div>
    <div class="col-md-3">
        <div class="card border-start border-info border-4">
            <div class="card-body">
                <div class="d-flex justify-content-between">
                    <div>
                        <small class="text-muted">Activos</small>
                        <h4 class="mb-0">${authorsResult.totalItems}</h4>
                    </div>
                    <i class="bi bi-flag-fill fs-2 text-info"></i>
                </div>
            </div>
        </div>
    </div>
    <div class="col-md-3">
        <div class="card border-start border-warning border-4">
            <div class="card-body">
                <div class="d-flex justify-content-between">
                    <div>
                        <small class="text-muted">Registros en Página</small>
                        <h4 class="mb-0">${fn:length(authorsResult.items)}</h4>
                    </div>
                    <i class="bi bi-list-ul fs-2 text-warning"></i>
                </div>
            </div>
        </div>
    </div>
</div>

<!-- Search and Filters -->
<div class="card mb-4">
    <div class="card-body">
        <form method="get" action="${pageContext.request.contextPath}/admin/panel">
            <input type="hidden" name="page" value="mantenimiento-autores">
            <div class="row g-3">
                <div class="col-md-4">
                    <label class="form-label">Buscar</label>
                    <div class="input-group">
                        <span class="input-group-text"><i class="bi bi-search"></i></span>
                        <input type="text" name="search" class="form-control" 
                               placeholder="Nombre del autor..." value="${searchValue}">
                    </div>
                </div>
                <div class="col-md-3">
                    <label class="form-label">Nacionalidad</label>
                    <select name="countryId" class="form-select">
                        <option value="">Todas</option>
                        <c:forEach var="country" items="${countries}">
                            <option value="${country.countryId}" 
                                ${country.countryId == countryIdValue ? 'selected' : ''}>
                                ${country.countryName}
                            </option>
                        </c:forEach>
                    </select>
                </div>
                <div class="col-md-3">
                    <label class="form-label">Estado</label>
                    <select name="statusId" class="form-select">
                        <option value="">Todos</option>
                        <option value="active" ${statusIdValue == 'active' ? 'selected' : ''}>Activo</option>
                        <option value="inactive" ${statusIdValue == 'inactive' ? 'selected' : ''}>Inactivo</option>
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

<!-- Error Message -->
<c:if test="${not empty error}">
    <div class="alert alert-danger alert-dismissible fade show" role="alert">
        <i class="bi bi-exclamation-triangle me-2"></i>${error}
        <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
    </div>
</c:if>

<!-- Authors Table -->
<div class="card">
    <div class="card-header d-flex justify-content-between align-items-center">
        <h5 class="mb-0">Listado de Autores</h5>
        <div>
            <span class="text-muted me-3">
                Mostrando ${authorsResult.startItem} - ${authorsResult.endItem} de ${authorsResult.totalItems}
            </span>
            <button class="btn btn-sm btn-outline-secondary" data-bs-toggle="tooltip" title="Vista de cuadrícula">
                <i class="bi bi-grid-3x3"></i>
            </button>
            <button class="btn btn-sm btn-outline-secondary active" data-bs-toggle="tooltip" title="Vista de tabla">
                <i class="bi bi-list-ul"></i>
            </button>
        </div>
    </div>
    <div class="card-body">
        <c:choose>
            <c:when test="${empty authorsResult.items}">
                <div class="text-center py-5">
                    <i class="bi bi-inbox fs-1 text-muted"></i>
                    <p class="text-muted mt-3">No se encontraron autores</p>
                    <c:if test="${not empty searchValue or not empty countryIdValue or not empty statusIdValue}">
                        <a href="${pageContext.request.contextPath}/admin/panel?page=mantenimiento-autores" 
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
                                <th style="width: 50px;">
                                    <input type="checkbox" class="form-check-input" id="selectAll">
                                </th>
                                <th>Autor</th>
                                <th>Pseudónimo</th>
                                <th>Nacionalidad</th>
                                <th>Año Nacimiento</th>
                                <th>Email</th>
                                <th>Estado</th>
                                <th style="width: 150px;">Acciones</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="author" items="${authorsResult.items}" varStatus="status">
                                <tr>
                                    <td>
                                        <input type="checkbox" class="form-check-input row-checkbox" 
                                               value="${author.authorId}">
                                    </td>
                                    <td>
                                        <div class="d-flex align-items-center">
                                            <c:set var="avatarUrl" value="https://ui-avatars.com/api/?name=${fn:replace(author.fullName, ' ', '+')}&size=40&background=3498db&color=fff" />
                                            <c:choose>
                                                <c:when test="${not empty author.photoUrl}">
                                                    <img src="${author.photoUrl}" 
                                                        class="rounded-circle me-2" 
                                                        width="40" height="40"
                                                        alt="${author.fullName}"
                                                        onerror="this.src='${avatarUrl}'">
                                                </c:when>
                                                <c:otherwise>
                                                    <img src="${avatarUrl}" 
                                                        class="rounded-circle me-2" 
                                                        width="40" height="40"
                                                        alt="${author.fullName}">
                                                </c:otherwise>
                                            </c:choose>
                                            <div>
                                                <strong>${author.fullName}</strong>
                                                <c:if test="${not empty author.website}">
                                                    <a href="${author.website}" target="_blank" class="ms-1 text-muted">
                                                        <i class="bi bi-link-45deg"></i>
                                                    </a>
                                                </c:if>
                                            </div>
                                        </div>
                                    </td>
                                    <td>
                                        <c:choose>
                                            <c:when test="${not empty author.pseudonym}">
                                                <em>${author.pseudonym}</em>
                                            </c:when>
                                            <c:otherwise>
                                                <span class="text-muted">-</span>
                                            </c:otherwise>
                                        </c:choose>
                                    </td>
                                    <td>
                                        <span class="badge bg-primary">
                                            <i class="bi bi-flag-fill me-1"></i>${author.countryName}
                                        </span>
                                    </td>
                                    <td>
                                        <c:choose>
                                            <c:when test="${not empty author.birthDate}">
                                                ${author.birthDate}
                                                <c:if test="${not empty author.deathDate}">
                                                    - ${author.deathDate}
                                                </c:if>
                                            </c:when>
                                            <c:otherwise>
                                                <span class="text-muted">-</span>
                                            </c:otherwise>
                                        </c:choose>
                                    </td>
                                    <td>
                                        <c:choose>
                                            <c:when test="${not empty author.email}">
                                                <a href="mailto:${author.email}" class="text-decoration-none">
                                                    ${author.email}
                                                </a>
                                            </c:when>
                                            <c:otherwise>
                                                <span class="text-muted">-</span>
                                            </c:otherwise>
                                        </c:choose>
                                    </td>
                                    <td>
                                        <c:choose>
                                            <c:when test="${author.statusName == 'Active'}">
                                                <span class="badge bg-success">Activo</span>
                                            </c:when>
                                            <c:otherwise>
                                                <span class="badge bg-secondary">Inactivo</span>
                                            </c:otherwise>
                                        </c:choose>
                                    </td>
                                    <td>
                                        <div class="btn-group btn-group-sm">
                                            <button class="btn btn-info" data-bs-toggle="tooltip" title="Ver detalles"
                                                    onclick="viewAuthor('${author.authorId}')">
                                                <i class="bi bi-eye"></i>
                                            </button>
                                            <button class="btn btn-warning" data-bs-toggle="tooltip" title="Editar"
                                                    onclick="editAuthor('${author.authorId}')">
                                                <i class="bi bi-pencil"></i>
                                            </button>
                                            <button class="btn btn-danger" data-bs-toggle="tooltip" title="Eliminar"
                                                    onclick="deleteAuthor('${author.authorId}', '${fn:escapeXml(author.fullName)}')">
                                                <i class="bi bi-trash"></i>
                                            </button>
                                        </div>
                                    </td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                </div>
                
                <!-- Pagination -->
                <c:if test="${authorsResult.totalPages > 1}">
                    <nav aria-label="Paginación de autores" class="mt-4">
                        <ul class="pagination justify-content-center">
                            <%-- Botón Anterior --%>
                            <li class="page-item ${!authorsResult.hasPreviousPage() ? 'disabled' : ''}">
                                <a class="page-link" 
                                   href="${pageContext.request.contextPath}/admin/panel?page=mantenimiento-autores&p=${authorsResult.previousPage}&search=${searchValue}&countryId=${countryIdValue}&statusId=${statusIdValue}">
                                    <i class="bi bi-chevron-left"></i> Anterior
                                </a>
                            </li>
                            
                            <%-- Números de página --%>
                            <c:set var="pageRange" value="${authorsResult.getPageRange(5)}" />
                            
                            <c:if test="${pageRange[0] > 1}">
                                <li class="page-item">
                                    <a class="page-link" 
                                       href="${pageContext.request.contextPath}/admin/panel?page=mantenimiento-autores&p=1&search=${searchValue}&countryId=${countryIdValue}&statusId=${statusIdValue}">
                                        1
                                    </a>
                                </li>
                                <c:if test="${pageRange[0] > 2}">
                                    <li class="page-item disabled"><span class="page-link">...</span></li>
                                </c:if>
                            </c:if>
                            
                            <c:forEach begin="${pageRange[0]}" end="${pageRange[1]}" var="i">
                                <li class="page-item ${i == authorsResult.currentPage ? 'active' : ''}">
                                    <a class="page-link" 
                                       href="${pageContext.request.contextPath}/admin/panel?page=mantenimiento-autores&p=${i}&search=${searchValue}&countryId=${countryIdValue}&statusId=${statusIdValue}">
                                        ${i}
                                    </a>
                                </li>
                            </c:forEach>
                            
                            <c:if test="${pageRange[1] < authorsResult.totalPages}">
                                <c:if test="${pageRange[1] < authorsResult.totalPages - 1}">
                                    <li class="page-item disabled"><span class="page-link">...</span></li>
                                </c:if>
                                <li class="page-item">
                                    <a class="page-link" 
                                       href="${pageContext.request.contextPath}/admin/panel?page=mantenimiento-autores&p=${authorsResult.totalPages}&search=${searchValue}&countryId=${countryIdValue}&statusId=${statusIdValue}">
                                        ${authorsResult.totalPages}
                                    </a>
                                </li>
                            </c:if>
                            
                            <%-- Botón Siguiente --%>
                            <li class="page-item ${!authorsResult.hasNextPage() ? 'disabled' : ''}">
                                <a class="page-link" 
                                   href="${pageContext.request.contextPath}/admin/panel?page=mantenimiento-autores&p=${authorsResult.nextPage}&search=${searchValue}&countryId=${countryIdValue}&statusId=${statusIdValue}">
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

<!-- Add Author Modal -->
<div class="modal fade" id="addAuthorModal" tabindex="-1" aria-labelledby="addAuthorModalLabel" aria-hidden="true">
    <div class="modal-dialog modal-lg">
        <div class="modal-content">
            <div class="modal-header bg-primary text-white">
                <h5 class="modal-title" id="addAuthorModalLabel">
                    <i class="bi bi-plus-circle me-2"></i>Agregar Nuevo Autor
                </h5>
                <button type="button" class="btn-close btn-close-white" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <form action="${pageContext.request.contextPath}/author" method="post">
                <input type="hidden" name="action" value="create">
                <div class="modal-body">
                    <div class="row g-3">
                        <div class="col-md-6">
                            <label class="form-label">Nombre Completo *</label>
                            <input type="text" name="fullName" class="form-control" 
                                   placeholder="Nombre del autor" required>
                        </div>
                        <div class="col-md-6">
                            <label class="form-label">Pseudónimo</label>
                            <input type="text" name="pseudonym" class="form-control" 
                                   placeholder="Si tiene pseudónimo">
                        </div>
                        <div class="col-md-4">
                            <label class="form-label">Nacionalidad *</label>
                            <select name="countryId" class="form-select" required>
                                <option value="">Seleccionar...</option>
                                <c:forEach var="country" items="${countries}">
                                    <option value="${country.countryId}">${country.countryName}</option>
                                </c:forEach>
                            </select>
                        </div>
                        <div class="col-md-4">
                            <label class="form-label">Fecha de Nacimiento</label>
                            <input type="date" name="birthDate" class="form-control">
                        </div>
                        <div class="col-md-4">
                            <label class="form-label">Fecha de Fallecimiento</label>
                            <input type="date" name="deathDate" class="form-control">
                        </div>
                        <div class="col-md-6">
                            <label class="form-label">Sitio Web</label>
                            <input type="url" name="website" class="form-control" placeholder="https://...">
                        </div>
                        <div class="col-md-6">
                            <label class="form-label">Email de Contacto</label>
                            <input type="email" name="email" class="form-control" placeholder="autor@example.com">
                        </div>
                        <div class="col-12">
                            <label class="form-label">Biografía</label>
                            <textarea name="biography" class="form-control" rows="4" 
                                      placeholder="Breve biografía del autor..."></textarea>
                        </div>
                        <div class="col-12">
                            <label class="form-label">URL Foto del Autor</label>
                            <input type="url" name="photoUrl" class="form-control" 
                                   placeholder="https://example.com/foto.jpg">
                        </div>
                    </div>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">
                        <i class="bi bi-x-circle"></i> Cancelar
                    </button>
                    <button type="submit" class="btn btn-primary">
                        <i class="bi bi-save"></i> Guardar Autor
                    </button>
                </div>
            </form>
        </div>
    </div>
</div>

<!-- View/Edit Author Modal -->
<div class="modal fade" id="authorDetailModal" tabindex="-1" aria-hidden="true">
    <div class="modal-dialog modal-lg">
        <div class="modal-content">
            <div class="modal-header bg-info text-white">
                <h5 class="modal-title">
                    <i class="bi bi-person-badge me-2"></i>Detalle del Autor
                </h5>
                <button type="button" class="btn-close btn-close-white" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <div class="modal-body" id="authorDetailContent">
                <!-- Content loaded via JavaScript -->
            </div>
        </div>
    </div>
</div>

<!-- Delete Confirmation Modal -->
<div class="modal fade" id="deleteAuthorModal" tabindex="-1" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header bg-danger text-white">
                <h5 class="modal-title">
                    <i class="bi bi-exclamation-triangle me-2"></i>Confirmar Eliminación
                </h5>
                <button type="button" class="btn-close btn-close-white" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <div class="modal-body">
                <p>¿Está seguro de que desea eliminar al autor <strong id="deleteAuthorName"></strong>?</p>
                <p class="text-danger mb-0">
                    <i class="bi bi-exclamation-circle me-1"></i>
                    Esta acción no se puede deshacer.
                </p>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">
                    <i class="bi bi-x-circle"></i> Cancelar
                </button>
                <form action="${pageContext.request.contextPath}/author" method="post" style="display: inline;">
                    <input type="hidden" name="action" value="delete">
                    <input type="hidden" name="authorId" id="deleteAuthorId">
                    <button type="submit" class="btn btn-danger">
                        <i class="bi bi-trash"></i> Eliminar
                    </button>
                </form>
            </div>
        </div>
    </div>
</div>

<script>
    // Select all checkbox
    document.getElementById('selectAll')?.addEventListener('change', function() {
        const checkboxes = document.querySelectorAll('.row-checkbox');
        checkboxes.forEach(checkbox => checkbox.checked = this.checked);
    });

    // View author details
    function viewAuthor(authorId) {
        // TODO: Implementar carga de detalles via AJAX
        const modal = new bootstrap.Modal(document.getElementById('authorDetailModal'));
        document.getElementById('authorDetailContent').innerHTML = '<div class="text-center py-4"><div class="spinner-border text-primary" role="status"></div><p class="mt-2">Cargando...</p></div>';
        modal.show();
        
        // Fetch author details
        fetch('${pageContext.request.contextPath}/author?action=view&id=' + authorId)
            .then(response => response.text())
            .then(html => {
                document.getElementById('authorDetailContent').innerHTML = html;
            })
            .catch(error => {
                document.getElementById('authorDetailContent').innerHTML = '<div class="alert alert-danger">Error al cargar los detalles</div>';
            });
    }

    // Edit author
    function editAuthor(authorId) {
        // TODO: Implementar edición
        window.location.href = '${pageContext.request.contextPath}/author?action=edit&id=' + authorId;
    }

    // Delete author
    function deleteAuthor(authorId, authorName) {
        document.getElementById('deleteAuthorId').value = authorId;
        document.getElementById('deleteAuthorName').textContent = authorName;
        const modal = new bootstrap.Modal(document.getElementById('deleteAuthorModal'));
        modal.show();
    }
</script>
