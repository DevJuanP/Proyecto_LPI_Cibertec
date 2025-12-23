<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>

<!-- Breadcrumb -->
<nav aria-label="breadcrumb" class="mb-4">
    <ol class="breadcrumb">
        <li class="breadcrumb-item"><a href="${pageContext.request.contextPath}/admin/panel">Dashboard</a></li>
        <li class="breadcrumb-item"><a href="${pageContext.request.contextPath}/admin/panel?page=mantenimiento-ejemplares">Ejemplares</a></li>
        <li class="breadcrumb-item active" aria-current="page">Editar</li>
    </ol>
</nav>

<!-- Page Header -->
<div class="content-header">
    <div class="d-flex justify-content-between align-items-center">
        <div>
            <h2 class="mb-0"><i class="bi bi-pencil-square me-2"></i>Editar Ejemplar</h2>
            <p class="text-muted mb-0">
                Modificar información del ejemplar: <strong>${bookCopy.book != null ? bookCopy.book.title : 'N/A'}</strong>
            </p>
        </div>
        <div>
            <a href="${pageContext.request.contextPath}/admin/panel?page=mantenimiento-ejemplares" 
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
        <div class="card">
            <div class="card-header bg-warning text-white">
                <h5 class="mb-0"><i class="bi bi-collection me-2"></i>Información del Ejemplar</h5>
            </div>
            <div class="card-body">
                <form action="${pageContext.request.contextPath}/book-copy" method="post" id="editBookCopyForm">
                    <input type="hidden" name="action" value="update">
                    <input type="hidden" name="bookCopyId" value="${bookCopy.bookCopyId}">

                    <!-- Información del Libro (Solo Lectura) -->
                    <div class="row g-3 mb-4">
                        <div class="col-12">
                            <h6 class="border-bottom pb-2 mb-3">
                                <i class="bi bi-book-fill me-2"></i>Información del Libro
                            </h6>
                        </div>

                        <div class="col-md-12">
                            <label class="form-label">ID del Ejemplar</label>
                            <input type="text" 
                                   class="form-control-plaintext" 
                                   value="${bookCopy.bookCopyId}" 
                                   readonly>
                            <small class="text-muted">Este campo no se puede modificar</small>
                        </div>

                        <div class="col-md-12">
                            <label class="form-label">Libro</label>
                            <input type="text" 
                                   class="form-control-plaintext" 
                                   value="${bookCopy.book != null ? bookCopy.book.title : 'N/A'}" 
                                   readonly>
                        </div>

                        <div class="col-md-6">
                            <label class="form-label">ISBN</label>
                            <input type="text" 
                                   class="form-control-plaintext" 
                                   value="${bookCopy.book != null ? bookCopy.book.isbn : 'N/A'}" 
                                   readonly>
                        </div>

                        <div class="col-md-6">
                            <label class="form-label">Autor</label>
                            <input type="text" 
                                   class="form-control-plaintext" 
                                   value="${bookCopy.book != null && bookCopy.book.author != null ? bookCopy.book.author.fullName : 'N/A'}" 
                                   readonly>
                        </div>
                    </div>

                    <!-- Datos Editables del Ejemplar -->
                    <div class="row g-3 mb-4">
                        <div class="col-12">
                            <h6 class="border-bottom pb-2 mb-3">
                                <i class="bi bi-pencil-fill me-2"></i>Datos del Ejemplar
                            </h6>
                        </div>

                        <div class="col-md-12">
                            <label for="bookCopyStatusId" class="form-label">
                                Estado <span class="text-danger">*</span>
                            </label>
                            <select class="form-select" id="bookCopyStatusId" name="bookCopyStatusId" required>
                                <option value="">Seleccionar estado...</option>
                                <c:forEach var="status" items="${bookCopyStatuses}">
                                    <!-- No permitir cambiar a "Alquilado" -->
                                    <c:if test="${status.bookCopyStatusName != 'Alquilado'}">
                                        <option value="${status.bookCopyStatusId}" 
                                                ${status.bookCopyStatusId == bookCopy.bookCopyStatusId ? 'selected' : ''}>
                                            ${status.bookCopyStatusName}
                                        </option>
                                    </c:if>
                                </c:forEach>
                            </select>
                            <div class="form-text">
                                <i class="bi bi-info-circle"></i>
                                No se puede cambiar a estado "Alquilado" desde este formulario
                            </div>
                        </div>

                        <div class="col-md-12">
                            <label for="notes" class="form-label">Notas</label>
                            <textarea class="form-control" 
                                      id="notes" 
                                      name="notes" 
                                      rows="4" 
                                      placeholder="Observaciones adicionales sobre el estado físico del ejemplar...">${bookCopy.notes}</textarea>
                            <div class="form-text">
                                Información adicional sobre el ejemplar (opcional)
                            </div>
                        </div>
                    </div>

                    <!-- Información de Auditoría -->
                    <div class="row g-3 mb-4">
                        <div class="col-12">
                            <h6 class="border-bottom pb-2 mb-3">
                                <i class="bi bi-clock-history me-2"></i>Información de Auditoría
                            </h6>
                        </div>

                        <div class="col-md-6">
                            <label class="form-label text-muted">Fecha de Creación</label>
                            <input type="text" 
                                   class="form-control-plaintext" 
                                   value="${bookCopy.createdAt}" 
                                   readonly>
                        </div>

                        <div class="col-md-6">
                            <label class="form-label text-muted">Última Actualización</label>
                            <input type="text" 
                                   class="form-control-plaintext" 
                                   value="${bookCopy.updatedAt}" 
                                   readonly>
                        </div>
                    </div>

                    <!-- Action Buttons -->
                    <div class="d-grid gap-2 d-md-flex justify-content-md-end">
                        <a href="${pageContext.request.contextPath}/admin/panel?page=mantenimiento-ejemplares" 
                           class="btn btn-secondary">
                            <i class="bi bi-x-circle"></i> Cancelar
                        </a>
                        <button type="submit" class="btn btn-warning">
                            <i class="bi bi-save"></i> Guardar Cambios
                        </button>
                    </div>
                </form>
            </div>
        </div>
    </div>

    <!-- Info Card -->
    <div class="col-lg-4">
        <div class="card border-info">
            <div class="card-header bg-info text-white">
                <h6 class="mb-0"><i class="bi bi-info-circle me-2"></i>Información Importante</h6>
            </div>
            <div class="card-body">
                <ul class="mb-0">
                    <li class="mb-2">El ID del ejemplar y la información del libro no pueden ser modificados.</li>
                    <li class="mb-2">No se puede cambiar el estado a <strong>"Alquilado"</strong> desde este formulario.</li>
                    <li class="mb-2">Los ejemplares alquilados no pueden ser editados hasta que sean devueltos.</li>
                    <li class="mb-0">Utilice las notas para agregar información adicional sobre el estado físico del ejemplar.</li>
                </ul>
            </div>
        </div>

        <!-- Status Guide -->
        <div class="card mt-3">
            <div class="card-header">
                <h6 class="mb-0"><i class="bi bi-list-check me-2"></i>Estados Disponibles</h6>
            </div>
            <div class="card-body">
                <div class="mb-2">
                    <span class="badge bg-success">Disponible</span>
                    <small class="d-block text-muted">Ejemplar listo para alquilar</small>
                </div>
                <div class="mb-2">
                    <span class="badge bg-warning text-dark">Mantenimiento</span>
                    <small class="d-block text-muted">En reparación o revisión</small>
                </div>
                <div class="mb-0">
                    <span class="badge bg-secondary">Descontinuado</span>
                    <small class="d-block text-muted">Dado de baja permanentemente</small>
                </div>
            </div>
        </div>
    </div>
</div>

<script>
    document.getElementById('editBookCopyForm').addEventListener('submit', function(e) {
        const statusSelect = document.getElementById('bookCopyStatusId');
        if (!statusSelect.value) {
            e.preventDefault();
            alert('Por favor, seleccione un estado para el ejemplar');
            statusSelect.focus();
            return false;
        }
        return true;
});
</script>