<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>

<!-- Breadcrumb -->
<nav aria-label="breadcrumb" class="mb-4">
    <ol class="breadcrumb">
        <li class="breadcrumb-item"><a href="${pageContext.request.contextPath}/admin/panel">Dashboard</a></li>
        <li class="breadcrumb-item"><a href="${pageContext.request.contextPath}/admin/panel?page=mantenimiento-autores">Autores</a></li>
        <li class="breadcrumb-item active" aria-current="page">Editar</li>
    </ol>
</nav>

<!-- Page Header -->
<div class="content-header">
    <div class="d-flex justify-content-between align-items-center">
        <div>
            <h2 class="mb-0"><i class="bi bi-pencil-square me-2"></i>Editar Autor</h2>
            <p class="text-muted mb-0">Modificar información del autor: <strong>${author.fullName}</strong></p>
        </div>
        <div>
            <a href="${pageContext.request.contextPath}/admin/panel?page=mantenimiento-autores" 
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
                <h5 class="mb-0"><i class="bi bi-pencil me-2"></i>Información del Autor</h5>
            </div>
            <div class="card-body">
                <form action="${pageContext.request.contextPath}/author" method="post" id="editAuthorForm">
                    <input type="hidden" name="action" value="update">
                    <input type="hidden" name="authorId" value="${author.authorId}">

                    <!-- Información Básica -->
                    <div class="row g-3 mb-4">
                        <div class="col-12">
                            <h6 class="border-bottom pb-2 mb-3">
                                <i class="bi bi-person-fill me-2"></i>Datos Personales
                            </h6>
                        </div>

                        <div class="col-md-6">
                            <label for="fullName" class="form-label">Nombre Completo *</label>
                            <input type="text" 
                                   class="form-control" 
                                   id="fullName" 
                                   name="fullName" 
                                   value="${author.fullName}"
                                   placeholder="Nombre completo del autor" 
                                   required>
                            <div class="invalid-feedback">
                                Por favor ingrese el nombre del autor.
                            </div>
                        </div>

                        <div class="col-md-6">
                            <label for="pseudonym" class="form-label">Pseudónimo</label>
                            <input type="text" 
                                   class="form-control" 
                                   id="pseudonym" 
                                   name="pseudonym" 
                                   value="${author.pseudonym}"
                                   placeholder="Nombre artístico o pseudónimo">
                            <div class="form-text">Opcional: si el autor usa un nombre diferente</div>
                        </div>

                        <div class="col-md-4">
                            <label for="countryId" class="form-label">Nacionalidad *</label>
                            <select class="form-select" id="countryId" name="countryId" required>
                                <option value="">Seleccionar...</option>
                                <c:forEach var="country" items="${countries}">
                                    <option value="${country.countryId}" 
                                            ${country.countryId == author.countryId ? 'selected' : ''}>
                                        ${country.countryName}
                                    </option>
                                </c:forEach>
                            </select>
                            <div class="invalid-feedback">
                                Por favor seleccione un país.
                            </div>
                        </div>

                        <div class="col-md-4">
                            <label for="birthYear" class="form-label">Año de Nacimiento</label>
                            <input type="number" 
                                   class="form-control" 
                                   id="birthYear" 
                                   name="birthYear" 
                                   value="${author.birthYear}"
                                   placeholder="ej. 1950 o -300 (a.C.)"
                                   min="-3000" 
                                   max="2025"
                                   step="1">
                            <div class="form-text">Usa números negativos para años a.C. (ej. -300 = 300 a.C.)</div>
                        </div>

                        <div class="col-md-4">
                            <label for="deathYear" class="form-label">Año de Fallecimiento</label>
                            <input type="number" 
                                   class="form-control" 
                                   id="deathYear" 
                                   name="deathYear" 
                                   value="${author.deathYear}"
                                   placeholder="ej. 2020 o -200 (a.C.)"
                                   min="-3000" 
                                   max="2025"
                                   step="1">
                            <div class="form-text">Usa números negativos para años a.C. Deja vacío si vive.</div>
                        </div>
                    </div>

                    <!-- Información de Contacto -->
                    <div class="row g-3 mb-4">
                        <div class="col-12">
                            <h6 class="border-bottom pb-2 mb-3">
                                <i class="bi bi-envelope-fill me-2"></i>Información de Contacto
                            </h6>
                        </div>

                        <div class="col-md-6">
                            <label for="email" class="form-label">
                                <i class="bi bi-envelope me-1"></i>Email de Contacto
                            </label>
                            <input type="email" 
                                   class="form-control" 
                                   id="email" 
                                   name="email" 
                                   value="${author.email}"
                                   placeholder="autor@example.com">
                            <div class="form-text">Email público del autor</div>
                        </div>

                        <div class="col-md-6">
                            <label for="website" class="form-label">
                                <i class="bi bi-globe me-1"></i>Sitio Web
                            </label>
                            <input type="url" 
                                   class="form-control" 
                                   id="website" 
                                   name="website" 
                                   value="${author.website}"
                                   placeholder="https://www.ejemplo.com">
                            <div class="form-text">Sitio web oficial o página personal</div>
                        </div>
                    </div>

                    <!-- Biografía -->
                    <div class="row g-3 mb-4">
                        <div class="col-12">
                            <h6 class="border-bottom pb-2 mb-3">
                                <i class="bi bi-card-text me-2"></i>Biografía y Descripción
                            </h6>
                        </div>

                        <div class="col-12">
                            <label for="biography" class="form-label">Biografía</label>
                            <textarea class="form-control" 
                                      id="biography" 
                                      name="biography" 
                                      rows="5"
                                      placeholder="Breve biografía o descripción del autor...">${author.biography}</textarea>
                            <div class="form-text">
                                <span id="bioCount">0</span> caracteres
                            </div>
                        </div>
                    </div>

                    <!-- Foto -->
                    <div class="row g-3 mb-4">
                        <div class="col-12">
                            <h6 class="border-bottom pb-2 mb-3">
                                <i class="bi bi-image me-2"></i>Imagen del Autor
                            </h6>
                        </div>

                        <div class="col-12">
                            <label for="photoUrl" class="form-label">URL de la Foto</label>
                            <input type="url" 
                                   class="form-control" 
                                   id="photoUrl" 
                                   name="photoUrl" 
                                   value="${author.photoUrl}"
                                   placeholder="https://example.com/foto.jpg">
                            <div class="form-text">URL de la imagen del autor (JPG, PNG)</div>
                        </div>

                        <div class="col-12">
                            <div class="card bg-light">
                                <div class="card-body text-center">
                                    <p class="mb-2"><strong>Vista previa:</strong></p>
                                    <c:set var="avatarUrl" value="https://ui-avatars.com/api/?name=${fn:replace(author.fullName, ' ', '+')}&size=150&background=3498db&color=fff" />
                                    <img id="photoPreview" 
                                         src="${not empty author.photoUrl ? author.photoUrl : avatarUrl}" 
                                         class="rounded-circle border border-3" 
                                         width="150" 
                                         height="150"
                                         alt="Foto del autor"
                                         onerror="this.src='${avatarUrl}'">
                                </div>
                            </div>
                        </div>
                    </div>

                    <!-- Estado -->
                    <div class="row g-3 mb-4">
                        <div class="col-12">
                            <h6 class="border-bottom pb-2 mb-3">
                                <i class="bi bi-toggle-on me-2"></i>Estado
                            </h6>
                        </div>

                        <div class="col-md-6">
                            <label for="statusId" class="form-label">Estado del Registro *</label>
                            <select class="form-select" id="statusId" name="statusId" required>
                                <option value="">Seleccionar...</option>
                                <option value="active" ${author.status.statusName == 'Active' ? 'selected' : ''}>
                                    Activo
                                </option>
                                <option value="inactive" ${author.status.statusName != 'Active' ? 'selected' : ''}>
                                    Inactivo
                                </option>
                            </select>
                            <div class="form-text">
                                Los autores inactivos no aparecerán en listados públicos
                            </div>
                        </div>
                    </div>

                    <!-- Botones de Acción -->
                    <div class="row">
                        <div class="col-12">
                            <hr class="my-4">
                            <div class="d-flex justify-content-between">
                                <a href="${pageContext.request.contextPath}/admin/panel?page=mantenimiento-autores" 
                                   class="btn btn-secondary">
                                    <i class="bi bi-x-circle me-1"></i> Cancelar
                                </a>
                                <div>
                                    <button type="reset" class="btn btn-outline-secondary me-2">
                                        <i class="bi bi-arrow-counterclockwise me-1"></i> Restablecer
                                    </button>
                                    <button type="submit" class="btn btn-warning">
                                        <i class="bi bi-save me-1"></i> Guardar Cambios
                                    </button>
                                </div>
                            </div>
                        </div>
                    </div>
                </form>
            </div>
        </div>
    </div>

    <!-- Sidebar with Info -->
    <div class="col-lg-4">
        <!-- Author Info Card -->
        <div class="card mb-4">
            <div class="card-header bg-info text-white">
                <h6 class="mb-0"><i class="bi bi-info-circle me-2"></i>Información del Registro</h6>
            </div>
            <div class="card-body">
                <table class="table table-sm table-borderless mb-0">
                    <tr>
                        <th style="width: 40%">ID:</th>
                        <td><code class="small">${fn:substring(author.authorId, 0, 8)}...</code></td>
                    </tr>
                    <tr>
                        <th>Creado:</th>
                        <td class="small">${author.createdAt}</td>
                    </tr>
                    <tr>
                        <th>Actualizado:</th>
                        <td class="small">${author.updatedAt}</td>
                    </tr>
                    <tr>
                        <th>Estado Actual:</th>
                        <td>
                            <c:choose>
                                <c:when test="${author.status.statusName == 'Active'}">
                                    <span class="badge bg-success">Activo</span>
                                </c:when>
                                <c:otherwise>
                                    <span class="badge bg-secondary">Inactivo</span>
                                </c:otherwise>
                            </c:choose>
                        </td>
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
                <h6 class="fw-bold">Campos obligatorios</h6>
                <ul class="small">
                    <li>Nombre Completo</li>
                    <li>Nacionalidad</li>
                    <li>Estado</li>
                </ul>

                <h6 class="fw-bold mt-3">Consejos</h6>
                <ul class="small mb-0">
                    <li>Usa el pseudónimo si el autor es conocido por otro nombre</li>
                    <li>Para autores antiguos, usa años negativos (ej. -300 = 300 a.C.)</li>
                    <li>La biografía ayuda a los usuarios a conocer al autor</li>
                    <li>Una buena foto mejora la presentación</li>
                </ul>
            </div>
        </div>

        <!-- Quick Actions -->
        <div class="card">
            <div class="card-header bg-dark text-white">
                <h6 class="mb-0"><i class="bi bi-lightning me-2"></i>Acciones Rápidas</h6>
            </div>
            <div class="card-body">
                <div class="d-grid gap-2">
                    <button type="button" class="btn btn-outline-info btn-sm" 
                            onclick="viewAuthor('${author.authorId}')">
                        <i class="bi bi-eye me-1"></i> Ver Detalles
                    </button>
                    <button type="button" class="btn btn-outline-danger btn-sm"
                            onclick="confirmDelete('${author.authorId}', '${fn:escapeXml(author.fullName)}')">
                        <i class="bi bi-trash me-1"></i> Eliminar Autor
                    </button>
                </div>
            </div>
        </div>
    </div>
</div>

<!-- View Author Modal -->
<div class="modal fade" id="viewAuthorModal" tabindex="-1" aria-hidden="true">
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
                <p>¿Está seguro de que desea eliminar al autor <strong id="deleteAuthorName"></strong>?</p>
                <p class="text-danger mb-0">
                    <i class="bi bi-exclamation-circle me-1"></i>
                    Esta acción no se puede deshacer y se eliminarán todos los datos relacionados.
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
    // Character counter for biography
    const biographyTextarea = document.getElementById('biography');
    const bioCount = document.getElementById('bioCount');
    
    function updateCharCount() {
        const count = biographyTextarea.value.length;
        bioCount.textContent = count;
        
        if (count > 1000) {
            bioCount.classList.add('text-warning');
        } else {
            bioCount.classList.remove('text-warning');
        }
    }
    
    if (biographyTextarea) {
        biographyTextarea.addEventListener('input', updateCharCount);
        updateCharCount(); // Initial count
    }

    // Photo URL preview
    const photoUrlInput = document.getElementById('photoUrl');
    const photoPreview = document.getElementById('photoPreview');
    
    if (photoUrlInput) {
        photoUrlInput.addEventListener('change', function() {
            const url = this.value.trim();
            if (url) {
                photoPreview.src = url;
            }
        });
    }

    // Form validation
    const form = document.getElementById('editAuthorForm');
    
    if (form) {
        form.addEventListener('submit', function(event) {
            if (!form.checkValidity()) {
                event.preventDefault();
                event.stopPropagation();
            }
            form.classList.add('was-validated');
        }, false);
    }

    // Year validation
    const birthYearInput = document.getElementById('birthYear');
    const deathYearInput = document.getElementById('deathYear');
    
    if (birthYearInput && deathYearInput) {
        deathYearInput.addEventListener('change', function() {
            const birthYear = parseInt(birthYearInput.value) || 0;
            const deathYear = parseInt(this.value) || 0;
            
            if (birthYear && deathYear && deathYear <= birthYear) {
                alert('El año de fallecimiento debe ser posterior al año de nacimiento');
                this.value = '';
            }
        });
    }

    // View author details
    function viewAuthor(authorId) {
        const modal = new bootstrap.Modal(document.getElementById('viewAuthorModal'));
        document.getElementById('authorDetailContent').innerHTML = 
            '<div class="text-center py-4"><div class="spinner-border text-primary" role="status"></div><p class="mt-2">Cargando...</p></div>';
        modal.show();
        
        fetch('${pageContext.request.contextPath}/author?action=view&id=' + authorId)
            .then(response => response.text())
            .then(html => {
                document.getElementById('authorDetailContent').innerHTML = html;
            })
            .catch(error => {
                document.getElementById('authorDetailContent').innerHTML = 
                    '<div class="alert alert-danger">Error al cargar los detalles</div>';
            });
    }

    // Confirm delete
    function confirmDelete(authorId, authorName) {
        document.getElementById('deleteAuthorId').value = authorId;
        document.getElementById('deleteAuthorName').textContent = authorName;
        const modal = new bootstrap.Modal(document.getElementById('deleteConfirmModal'));
        modal.show();
    }

    // Unsaved changes warning
    let formChanged = false;
    
    if (form) {
        const formInputs = form.querySelectorAll('input, select, textarea');
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

        form.addEventListener('submit', function() {
            formChanged = false;
        });
    }
</script>
