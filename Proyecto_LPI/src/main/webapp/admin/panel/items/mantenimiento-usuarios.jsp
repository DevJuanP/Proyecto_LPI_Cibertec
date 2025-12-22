<!-- src/main/webapp/admin/panel/items/mantenimiento-usuarios.jsp -->
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>

<div class="content-header">
    <div class="d-flex justify-content-between align-items-center">
        <div>
            <h2 class="mb-0"><i class="bi bi-people me-2"></i>Mantenimiento de Usuarios</h2>
            <p class="text-muted mb-0">Gestión completa de usuarios del sistema</p>
        </div>
        <div>
            <button class="btn btn-success" data-bs-toggle="modal" data-bs-target="#addUserModal">
                <i class="bi bi-plus-circle"></i> Nuevo Usuario
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
                        <small class="text-muted">Total Usuarios</small>
                        <h4 class="mb-0">${totalUsers}</h4>
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
                        <small class="text-muted">Activos</small>
                        <h4 class="mb-0">${activeUsersCount}</h4>
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
                        <small class="text-muted">Administradores</small>
                        <h4 class="mb-0">
                            <c:set var="adminCount" value="0" />
                            <c:forEach var="user" items="${usersResult.items}">
                                <c:if test="${user.hasRole('Admin')}">
                                    <c:set var="adminCount" value="${adminCount + 1}" />
                                </c:if>
                            </c:forEach>
                            ${adminCount}
                        </h4>
                    </div>
                    <i class="bi bi-shield-check fs-2 text-info"></i>
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
                        <h4 class="mb-0">${fn:length(usersResult.items)}</h4>
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
            <input type="hidden" name="page" value="mantenimiento-usuarios">
            <div class="row g-3">
                <div class="col-md-4">
                    <label class="form-label">Buscar</label>
                    <div class="input-group">
                        <span class="input-group-text"><i class="bi bi-search"></i></span>
                        <input type="text" name="search" class="form-control" 
                               placeholder="Email del usuario..." value="${searchValue}">
                    </div>
                </div>
                <div class="col-md-3">
                    <label class="form-label">Rol</label>
                    <select name="roleId" class="form-select">
                        <option value="">Todos</option>
                        <c:forEach var="role" items="${roles}">
                            <option value="${role.roleId}" 
                                ${role.roleId == roleIdValue ? 'selected' : ''}>
                                ${role.roleName}
                            </option>
                        </c:forEach>
                    </select>
                </div>
                <div class="col-md-3">
                    <label class="form-label">Estado</label>
                    <select name="statusId" class="form-select">
                        <option value="">Todos</option>
                        <c:forEach var="status" items="${statuses}">
                            <option value="${status.statusId}" 
                                ${status.statusId == statusIdValue ? 'selected' : ''}>
                                ${status.statusName}
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

<!-- Users Table -->
<div class="card">
    <div class="card-header d-flex justify-content-between align-items-center">
        <h5 class="mb-0">Listado de Usuarios</h5>
        <div>
            <span class="text-muted me-3">
                Mostrando ${usersResult.startItem} - ${usersResult.endItem} de ${usersResult.totalItems}
            </span>
        </div>
    </div>
    <div class="card-body">
        <c:choose>
            <c:when test="${empty usersResult.items}">
                <div class="text-center py-5">
                    <i class="bi bi-inbox fs-1 text-muted"></i>
                    <p class="text-muted mt-3">No se encontraron usuarios</p>
                    <c:if test="${not empty searchValue or not empty statusIdValue}">
                        <a href="${pageContext.request.contextPath}/admin/panel?page=mantenimiento-usuarios" 
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
                                <th>Email</th>
                                <th>Roles</th>
                                <th>Estado</th>
                                <th>Fecha Registro</th>
                                <th>Última Actualización</th>
                                <th style="width: 150px;">Acciones</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="user" items="${usersResult.items}" varStatus="status">
                                <tr>
                                    <td>
                                        <input type="checkbox" class="form-check-input row-checkbox" 
                                               value="${user.userId}">
                                    </td>
                                    <td>
                                        <div class="d-flex align-items-center">
                                            <div class="bg-primary bg-opacity-10 rounded-circle d-flex align-items-center justify-content-center me-2"
                                                 style="width: 40px; height: 40px;">
                                                <i class="bi bi-person-fill text-primary fs-5"></i>
                                            </div>
                                            <div>
                                                <strong>${user.email}</strong>
                                                <br>
                                                <small class="text-muted">ID: ${user.userId}</small>
                                            </div>
                                        </div>
                                    </td>
                                    <td>
                                        <c:choose>
                                            <c:when test="${not empty user.roleNames}">
                                                <c:forEach var="roleName" items="${user.roleNames}">
                                                    <c:choose>
                                                        <c:when test="${roleName == 'Admin'}">
                                                            <span class="badge bg-danger me-1">
                                                                <i class="bi bi-shield-fill-check me-1"></i>${roleName}
                                                            </span>
                                                        </c:when>
                                                        <c:otherwise>
                                                            <span class="badge bg-primary me-1">${roleName}</span>
                                                        </c:otherwise>
                                                    </c:choose>
                                                </c:forEach>
                                            </c:when>
                                            <c:otherwise>
                                                <span class="text-muted">Sin roles</span>
                                            </c:otherwise>
                                        </c:choose>
                                    </td>
                                    <td>
                                        <c:choose>
                                            <c:when test="${user.statusName == 'Active'}">
                                                <span class="badge bg-success">
                                                    <i class="bi bi-check-circle me-1"></i>Activo
                                                </span>
                                            </c:when>
                                            <c:otherwise>
                                                <span class="badge bg-secondary">
                                                    <i class="bi bi-x-circle me-1"></i>Inactivo
                                                </span>
                                            </c:otherwise>
                                        </c:choose>
                                    </td>
                                    <td>
                                        <small>${user.createdAt}</small>
                                    </td>
                                    <td>
                                        <small>${user.updatedAt}</small>
                                    </td>
                                    <td>
                                        <div class="btn-group btn-group-sm">
                                            <button class="btn btn-info" data-bs-toggle="tooltip" title="Ver detalles"
                                                    onclick="viewUser('${user.userId}')">
                                                <i class="bi bi-eye"></i>
                                            </button>
                                            <button class="btn btn-warning" data-bs-toggle="tooltip" title="Editar"
                                                    onclick="editUser('${user.userId}')">
                                                <i class="bi bi-pencil"></i>
                                            </button>
                                            <button class="btn btn-danger" data-bs-toggle="tooltip" title="Eliminar"
                                                    onclick="deleteUser('${user.userId}', '${fn:escapeXml(user.email)}')"
                                                    ${user.userId == sessionScope.userId ? 'disabled' : ''}>
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
                <c:if test="${usersResult.totalPages > 1}">
                    <nav aria-label="Paginación de usuarios" class="mt-4">
                        <ul class="pagination justify-content-center">
                            <%-- Botón Anterior --%>
                            <li class="page-item ${!usersResult.hasPreviousPage() ? 'disabled' : ''}">
                                <a class="page-link" 
                                   href="${pageContext.request.contextPath}/admin/panel?page=mantenimiento-usuarios&p=${usersResult.previousPage}&search=${searchValue}&statusId=${statusIdValue}">
                                    <i class="bi bi-chevron-left"></i> Anterior
                                </a>
                            </li>
                            
                            <%-- Números de página --%>
                            <c:set var="pageRange" value="${usersResult.getPageRange(5)}" />
                            
                            <c:if test="${pageRange[0] > 1}">
                                <li class="page-item">
                                    <a class="page-link" 
                                       href="${pageContext.request.contextPath}/admin/panel?page=mantenimiento-usuarios&p=1&search=${searchValue}&statusId=${statusIdValue}">
                                        1
                                    </a>
                                </li>
                                <c:if test="${pageRange[0] > 2}">
                                    <li class="page-item disabled"><span class="page-link">...</span></li>
                                </c:if>
                            </c:if>
                            
                            <c:forEach begin="${pageRange[0]}" end="${pageRange[1]}" var="i">
                                <li class="page-item ${i == usersResult.currentPage ? 'active' : ''}">
                                    <a class="page-link" 
                                       href="${pageContext.request.contextPath}/admin/panel?page=mantenimiento-usuarios&p=${i}&search=${searchValue}&statusId=${statusIdValue}">
                                        ${i}
                                    </a>
                                </li>
                            </c:forEach>
                            
                            <c:if test="${pageRange[1] < usersResult.totalPages}">
                                <c:if test="${pageRange[1] < usersResult.totalPages - 1}">
                                    <li class="page-item disabled"><span class="page-link">...</span></li>
                                </c:if>
                                <li class="page-item">
                                    <a class="page-link" 
                                       href="${pageContext.request.contextPath}/admin/panel?page=mantenimiento-usuarios&p=${usersResult.totalPages}&search=${searchValue}&statusId=${statusIdValue}">
                                        ${usersResult.totalPages}
                                    </a>
                                </li>
                            </c:if>
                            
                            <%-- Botón Siguiente --%>
                            <li class="page-item ${!usersResult.hasNextPage() ? 'disabled' : ''}">
                                <a class="page-link" 
                                   href="${pageContext.request.contextPath}/admin/panel?page=mantenimiento-usuarios&p=${usersResult.nextPage}&search=${searchValue}&statusId=${statusIdValue}">
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

<!-- Add User Modal -->
<div class="modal fade" id="addUserModal" tabindex="-1" aria-labelledby="addUserModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header bg-primary text-white">
                <h5 class="modal-title" id="addUserModalLabel">
                    <i class="bi bi-plus-circle me-2"></i>Agregar Nuevo Usuario
                </h5>
                <button type="button" class="btn-close btn-close-white" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <form action="${pageContext.request.contextPath}/user" method="post" id="addUserForm">
                <input type="hidden" name="action" value="create">
                <div class="modal-body">
                    <div class="mb-3">
                        <label class="form-label">Email *</label>
                        <input type="email" name="email" class="form-control" 
                               placeholder="usuario@example.com" required>
                        <div class="form-text">El email será el nombre de usuario</div>
                    </div>
                    <div class="mb-3">
                        <label class="form-label">Contraseña *</label>
                        <div class="input-group">
                            <input type="password" name="password" id="newPassword" class="form-control" 
                                   placeholder="Mínimo 6 caracteres" required minlength="6">
                            <button class="btn btn-outline-secondary" type="button" 
                                    onclick="togglePassword('newPassword')">
                                <i class="bi bi-eye" id="newPassword-icon"></i>
                            </button>
                        </div>
                        <div class="form-text">La contraseña debe tener al menos 6 caracteres</div>
                    </div>
                    <div class="alert alert-info">
                        <i class="bi bi-info-circle me-2"></i>
                        El usuario se creará con estado <strong>Activo</strong>. 
                        Los roles se asignan después de la creación.
                    </div>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">
                        <i class="bi bi-x-circle"></i> Cancelar
                    </button>
                    <button type="submit" class="btn btn-primary">
                        <i class="bi bi-save"></i> Crear Usuario
                    </button>
                </div>
            </form>
        </div>
    </div>
</div>

<!-- View User Modal -->
<div class="modal fade" id="userDetailModal" tabindex="-1" aria-hidden="true">
    <div class="modal-dialog modal-lg">
        <div class="modal-content">
            <div class="modal-header bg-info text-white">
                <h5 class="modal-title">
                    <i class="bi bi-person-badge me-2"></i>Detalle del Usuario
                </h5>
                <button type="button" class="btn-close btn-close-white" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <div class="modal-body" id="userDetailContent">
                <!-- Content loaded via JavaScript -->
            </div>
        </div>
    </div>
</div>

<!-- Delete Confirmation Modal -->
<div class="modal fade" id="deleteUserModal" tabindex="-1" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header bg-danger text-white">
                <h5 class="modal-title">
                    <i class="bi bi-exclamation-triangle me-2"></i>Confirmar Eliminación
                </h5>
                <button type="button" class="btn-close btn-close-white" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <div class="modal-body">
                <p>¿Está seguro de que desea eliminar al usuario <strong id="deleteUserEmail"></strong>?</p>
                <p class="text-danger mb-0">
                    <i class="bi bi-exclamation-circle me-1"></i>
                    Esta acción no se puede deshacer y se eliminarán todos los datos relacionados.
                </p>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">
                    <i class="bi bi-x-circle"></i> Cancelar
                </button>
                <form action="${pageContext.request.contextPath}/user" method="post" style="display: inline;">
                    <input type="hidden" name="action" value="delete">
                    <input type="hidden" name="userId" id="deleteUserId">
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

    // Toggle password visibility
    function togglePassword(fieldId) {
        const field = document.getElementById(fieldId);
        const icon = document.getElementById(fieldId + '-icon');
        
        if (field.type === 'password') {
            field.type = 'text';
            icon.classList.remove('bi-eye');
            icon.classList.add('bi-eye-slash');
        } else {
            field.type = 'password';
            icon.classList.remove('bi-eye-slash');
            icon.classList.add('bi-eye');
        }
    }

    // View user details
    function viewUser(userId) {
        const modal = new bootstrap.Modal(document.getElementById('userDetailModal'));
        document.getElementById('userDetailContent').innerHTML = 
            '<div class="text-center py-4"><div class="spinner-border text-primary" role="status"></div><p class="mt-2">Cargando...</p></div>';
        modal.show();
        
        fetch('${pageContext.request.contextPath}/user?action=view&id=' + userId)
            .then(response => response.text())
            .then(html => {
                document.getElementById('userDetailContent').innerHTML = html;
            })
            .catch(error => {
                document.getElementById('userDetailContent').innerHTML = 
                    '<div class="alert alert-danger">Error al cargar los detalles</div>';
            });
    }

    // Edit user
    function editUser(userId) {
        window.location.href = '${pageContext.request.contextPath}/admin/panel?page=edit-usuario&id=' + userId;
    }

    // Delete user
    function deleteUser(userId, userEmail) {
        document.getElementById('deleteUserId').value = userId;
        document.getElementById('deleteUserEmail').textContent = userEmail;
        const modal = new bootstrap.Modal(document.getElementById('deleteUserModal'));
        modal.show();
    }

    // Form validation
    const addUserForm = document.getElementById('addUserForm');
    if (addUserForm) {
        addUserForm.addEventListener('submit', function(event) {
            if (!addUserForm.checkValidity()) {
                event.preventDefault();
                event.stopPropagation();
            }
            addUserForm.classList.add('was-validated');
        }, false);
    }
</script>
