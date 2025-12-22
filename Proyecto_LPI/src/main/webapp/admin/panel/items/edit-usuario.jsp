<!-- src/main/webapp/admin/panel/items/edit-usuario.jsp -->
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>

<!-- Breadcrumb -->
<nav aria-label="breadcrumb" class="mb-4">
    <ol class="breadcrumb">
        <li class="breadcrumb-item"><a href="${pageContext.request.contextPath}/admin/panel">Dashboard</a></li>
        <li class="breadcrumb-item"><a href="${pageContext.request.contextPath}/admin/panel?page=mantenimiento-usuarios">Usuarios</a></li>
        <li class="breadcrumb-item active" aria-current="page">Editar</li>
    </ol>
</nav>

<!-- Page Header -->
<div class="content-header">
    <div class="d-flex justify-content-between align-items-center">
        <div>
            <h2 class="mb-0"><i class="bi bi-pencil-square me-2"></i>Editar Usuario</h2>
            <p class="text-muted mb-0">Modificar información del usuario: <strong>${user.email}</strong></p>
        </div>
        <div>
            <a href="${pageContext.request.contextPath}/admin/panel?page=mantenimiento-usuarios" 
               class="btn btn-secondary">
                <i class="bi bi-arrow-left"></i> Volver
            </a>
        </div>
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

<!-- Edit Form -->
<div class="row">
    <div class="col-lg-8">
        <!-- Basic Information Card -->
        <div class="card mb-4">
            <div class="card-header bg-warning text-white">
                <h5 class="mb-0"><i class="bi bi-person me-2"></i>Información Básica</h5>
            </div>
            <div class="card-body">
                <form action="${pageContext.request.contextPath}/user" method="post" id="editUserForm">
                    <input type="hidden" name="action" value="update">
                    <input type="hidden" name="userId" value="${user.userId}">

                    <div class="row g-3">
                        <div class="col-md-8">
                            <label for="email" class="form-label">Email *</label>
                            <input type="email" 
                                   class="form-control" 
                                   id="email" 
                                   name="email" 
                                   value="${user.email}"
                                   placeholder="usuario@example.com" 
                                   required>
                            <div class="invalid-feedback">
                                Por favor ingrese un email válido.
                            </div>
                        </div>

                        <div class="col-md-4">
                            <label for="statusId" class="form-label">Estado *</label>
                            <select class="form-select" id="statusId" name="statusId" required>
                                <option value="">Seleccionar...</option>
                                <option value="active" ${user.status.statusName == 'Active' ? 'selected' : ''}>
                                    Activo
                                </option>
                                <option value="inactive" ${user.status.statusName != 'Active' ? 'selected' : ''}>
                                    Inactivo
                                </option>
                            </select>
                            <div class="form-text">
                                Los usuarios inactivos no pueden iniciar sesión
                            </div>
                        </div>
                    </div>

                    <hr class="my-4">

                    <div class="d-flex justify-content-between">
                        <a href="${pageContext.request.contextPath}/admin/panel?page=mantenimiento-usuarios" 
                           class="btn btn-secondary">
                            <i class="bi bi-x-circle me-1"></i> Cancelar
                        </a>
                        <button type="submit" class="btn btn-warning">
                            <i class="bi bi-save me-1"></i> Guardar Cambios
                        </button>
                    </div>
                </form>
            </div>
        </div>

        <!-- Change Password Card -->
        <div class="card mb-4">
            <div class="card-header bg-primary text-white">
                <h5 class="mb-0"><i class="bi bi-key me-2"></i>Cambiar Contraseña</h5>
            </div>
            <div class="card-body">
                <form action="${pageContext.request.contextPath}/user" method="post" id="changePasswordForm">
                    <input type="hidden" name="action" value="changePassword">
                    <input type="hidden" name="userId" value="${user.userId}">

                    <div class="alert alert-info">
                        <i class="bi bi-info-circle me-2"></i>
                        Como administrador, puedes establecer una nueva contraseña directamente sin necesidad de la contraseña actual.
                    </div>

                    <div class="mb-3">
                        <label for="newPassword" class="form-label">Nueva Contraseña *</label>
                        <div class="input-group">
                            <input type="password" 
                                   class="form-control" 
                                   id="newPassword" 
                                   name="newPassword" 
                                   placeholder="Mínimo 6 caracteres"
                                   required 
                                   minlength="6">
                            <button class="btn btn-outline-secondary" type="button" 
                                    onclick="togglePasswordVisibility('newPassword')">
                                <i class="bi bi-eye" id="newPassword-icon"></i>
                            </button>
                        </div>
                        <div class="form-text">La contraseña debe tener al menos 6 caracteres</div>
                    </div>

                    <div class="mb-3">
                        <label for="confirmPassword" class="form-label">Confirmar Contraseña *</label>
                        <div class="input-group">
                            <input type="password" 
                                   class="form-control" 
                                   id="confirmPassword" 
                                   placeholder="Repite la contraseña"
                                   required 
                                   minlength="6">
                            <button class="btn btn-outline-secondary" type="button" 
                                    onclick="togglePasswordVisibility('confirmPassword')">
                                <i class="bi bi-eye" id="confirmPassword-icon"></i>
                            </button>
                        </div>
                        <div class="invalid-feedback" id="passwordMismatch">
                            Las contraseñas no coinciden
                        </div>
                    </div>

                    <div class="text-end">
                        <button type="submit" class="btn btn-primary">
                            <i class="bi bi-shield-lock me-1"></i> Cambiar Contraseña
                        </button>
                    </div>
                </form>
            </div>
        </div>

        <!-- Roles Management Card -->
        <div class="card mb-4">
            <div class="card-header bg-success text-white">
                <h5 class="mb-0"><i class="bi bi-shield-check me-2"></i>Gestión de Roles</h5>
            </div>
            <div class="card-body">
                <h6 class="mb-3">Roles Actuales</h6>
                <div class="mb-3">
                    <c:choose>
                        <c:when test="${not empty user.roles}">
                            <c:forEach var="role" items="${user.roles}">
                                <div class="d-flex align-items-center justify-content-between border rounded p-2 mb-2">
                                    <div>
                                        <c:choose>
                                            <c:when test="${role.roleName == 'Admin'}">
                                                <span class="badge bg-danger">
                                                    <i class="bi bi-shield-fill-check me-1"></i>${role.roleName}
                                                </span>
                                            </c:when>
                                            <c:otherwise>
                                                <span class="badge bg-primary">${role.roleName}</span>
                                            </c:otherwise>
                                        </c:choose>
                                        <small class="text-muted ms-2">Asignado desde: ${role.createdAt}</small>
                                    </div>
                                    <form action="${pageContext.request.contextPath}/user" method="post" style="display: inline;">
                                        <input type="hidden" name="action" value="removeRole">
                                        <input type="hidden" name="userId" value="${user.userId}">
                                        <input type="hidden" name="roleId" value="${role.roleId}">
                                        <button type="submit" class="btn btn-sm btn-outline-danger"
                                                onclick="return confirm('¿Está seguro de remover este rol?')">
                                            <i class="bi bi-trash"></i>
                                        </button>
                                    </form>
                                </div>
                            </c:forEach>
                        </c:when>
                        <c:otherwise>
                            <div class="alert alert-warning">
                                <i class="bi bi-exclamation-triangle me-2"></i>
                                Este usuario no tiene roles asignados
                            </div>
                        </c:otherwise>
                    </c:choose>
                </div>

                <hr>

                <h6 class="mb-3">Asignar Nuevo Rol</h6>
                <form action="${pageContext.request.contextPath}/user" method="post" class="row g-3">
                    <input type="hidden" name="action" value="assignRole">
                    <input type="hidden" name="userId" value="${user.userId}">
                    
                    <div class="col-md-8">
                        <select name="roleId" class="form-select" required>
                            <option value="">Seleccionar rol...</option>
                            <c:forEach var="role" items="${roles}">
                                <option value="${role.roleId}" >
                                    ${role.roleName}
                                </option>
                            </c:forEach>
                        </select>
                    </div>
                    <div class="col-md-4">
                        <button type="submit" class="btn btn-success w-100">
                            <i class="bi bi-plus-circle me-1"></i> Asignar
                        </button>
                    </div>
                </form>

                <div class="alert alert-info mt-3">
                    <i class="bi bi-info-circle me-2"></i>
                    <strong>Nota:</strong> Los cambios de roles toman efecto inmediatamente. Si el usuario tiene sesión activa, deberá cerrarla e iniciar sesión nuevamente.
                </div>
            </div>
        </div>
    </div>

    <!-- Sidebar with Info -->
    <div class="col-lg-4">
        <!-- User Info Card -->
        <div class="card mb-4">
            <div class="card-header bg-info text-white">
                <h6 class="mb-0"><i class="bi bi-info-circle me-2"></i>Información del Usuario</h6>
            </div>
            <div class="card-body text-center">
                <div class="bg-primary bg-opacity-10 rounded-circle d-inline-flex align-items-center justify-content-center mb-3"
                     style="width: 100px; height: 100px;">
                    <i class="bi bi-person-fill text-primary" style="font-size: 3rem;"></i>
                </div>
                <h5>${user.email}</h5>
                <c:choose>
                    <c:when test="${user.status.statusName == 'Active'}">
                        <span class="badge bg-success">Activo</span>
                    </c:when>
                    <c:otherwise>
                        <span class="badge bg-secondary">Inactivo</span>
                    </c:otherwise>
                </c:choose>

                <hr>

                <table class="table table-sm table-borderless mb-0">
                    <tr>
                        <th style="width: 40%">ID:</th>
                        <td><code class="small">${user.userId}</code></td>
                    </tr>
                    <tr>
                        <th>Creado:</th>
                        <td class="small">${user.createdAt}</td>
                    </tr>
                    <tr>
                        <th>Actualizado:</th>
                        <td class="small">${user.updatedAt}</td>
                    </tr>
                </table>
            </div>
        </div>

        <!-- Help Card -->
        <div class="card mb-4">
            <div class="card-header bg-primary text-white">
                <h6 class="mb-0"><i class="bi bi-question-circle me-2"></i>Ayuda</h6>
            </div>
            <div class="card-body">
                <h6 class="fw-bold">Roles del Sistema</h6>
                <ul class="small">
                    <li><strong>Admin:</strong> Acceso completo al panel de administración</li>
                    <li><strong>User:</strong> Acceso básico al sistema</li>
                </ul>

                <h6 class="fw-bold mt-3">Seguridad</h6>
                <ul class="small mb-0">
                    <li>Las contraseñas se almacenan encriptadas</li>
                    <li>Se recomienda usar contraseñas fuertes</li>
                    <li>Los usuarios inactivos no pueden iniciar sesión</li>
                </ul>
            </div>
        </div>

        <!-- Warning Card -->
        <div class="card border-danger">
            <div class="card-header bg-danger text-white">
                <h6 class="mb-0"><i class="bi bi-exclamation-triangle me-2"></i>Zona Peligrosa</h6>
            </div>
            <div class="card-body">
                <p class="small mb-3">Eliminar este usuario es una acción permanente y no se puede deshacer.</p>
                <button type="button" class="btn btn-danger w-100"
                        onclick="confirmDelete('${user.userId}', '${fn:escapeXml(user.email)}')"
                        ${user.userId == sessionScope.userId ? 'disabled' : ''}>
                    <i class="bi bi-trash me-1"></i> Eliminar Usuario
                </button>
                <c:if test="${user.userId == sessionScope.userId}">
                    <small class="text-muted d-block mt-2">
                        <i class="bi bi-info-circle me-1"></i>No puedes eliminar tu propia cuenta
                    </small>
                </c:if>
            </div>
        </div>
    </div>
</div>

<!-- Delete Confirmation Modal -->
<div class="modal fade" id="deleteConfirmModal" tabindex="-1" aria-hidden="true">
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
    // Toggle password visibility
    function togglePasswordVisibility(fieldId) {
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

    // Password confirmation validation
    const changePasswordForm = document.getElementById('changePasswordForm');
    const newPassword = document.getElementById('newPassword');
    const confirmPassword = document.getElementById('confirmPassword');

    if (changePasswordForm) {
        changePasswordForm.addEventListener('submit', function(event) {
            if (newPassword.value !== confirmPassword.value) {
                event.preventDefault();
                event.stopPropagation();
                confirmPassword.classList.add('is-invalid');
                document.getElementById('passwordMismatch').style.display = 'block';
            } else {
                confirmPassword.classList.remove('is-invalid');
            }
            
            if (!changePasswordForm.checkValidity()) {
                event.preventDefault();
                event.stopPropagation();
            }
            
            changePasswordForm.classList.add('was-validated');
        }, false);

        confirmPassword.addEventListener('input', function() {
            if (this.value === newPassword.value) {
                this.classList.remove('is-invalid');
                this.classList.add('is-valid');
            } else {
                this.classList.remove('is-valid');
                this.classList.add('is-invalid');
            }
        });
    }

    // Form validation for edit form
    const editUserForm = document.getElementById('editUserForm');
    
    if (editUserForm) {
        editUserForm.addEventListener('submit', function(event) {
            if (!editUserForm.checkValidity()) {
                event.preventDefault();
                event.stopPropagation();
            }
            editUserForm.classList.add('was-validated');
        }, false);
    }

    // Confirm delete
    function confirmDelete(userId, userEmail) {
        document.getElementById('deleteUserId').value = userId;
        document.getElementById('deleteUserEmail').textContent = userEmail;
        const modal = new bootstrap.Modal(document.getElementById('deleteConfirmModal'));
        modal.show();
    }

    // Unsaved changes warning
    let formChanged = false;
    
    if (editUserForm) {
        const formInputs = editUserForm.querySelectorAll('input, select, textarea');
        formInputs.forEach(input => {
            input.addEventListener('change', function() {
                formChanged = true;
            });
        });

        window.addEventListener('beforeunload', function(e) {
            if (formChanged) {
                e.preventDefault();
                e.returnValue = '';
                return '';
            }
        });

        editUserForm.addEventListener('submit', function() {
            formChanged = false;
        });
    }
</script>
