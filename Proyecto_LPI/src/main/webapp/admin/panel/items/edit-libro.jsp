<!-- src/main/webapp/admin/panel/items/edit-libro.jsp -->
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>

<!-- Breadcrumb -->
<nav aria-label="breadcrumb" class="mb-4">
    <ol class="breadcrumb">
        <li class="breadcrumb-item"><a href="${pageContext.request.contextPath}/admin/panel">Dashboard</a></li>
        <li class="breadcrumb-item"><a href="${pageContext.request.contextPath}/admin/panel?page=mantenimiento-libros">Libros</a></li>
        <li class="breadcrumb-item active" aria-current="page">Editar</li>
    </ol>
</nav>

<!-- Page Header -->
<div class="content-header">
    <div class="d-flex justify-content-between align-items-center">
        <div>
            <h2 class="mb-0"><i class="bi bi-pencil-square me-2"></i>Editar Libro</h2>
            <p class="text-muted mb-0">Modificar información del libro: <strong>${libro.title}</strong></p>
        </div>
        <div>
            <a href="${pageContext.request.contextPath}/admin/panel?page=mantenimiento-libros" 
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
                <h5 class="mb-0"><i class="bi bi-pencil me-2"></i>Información del Libro</h5>
            </div>
            <div class="card-body">
                <form action="${pageContext.request.contextPath}/book" method="post" id="editBookForm">
                    <input type="hidden" name="action" value="update">
                    <input type="hidden" name="bookId" value="${libro.bookId}">

                    <!-- Información Básica -->
                    <div class="row g-3 mb-4">
                        <div class="col-12">
                            <h6 class="border-bottom pb-2 mb-3">
                                <i class="bi bi-info-circle-fill me-2"></i>Datos Principales
                            </h6>
                        </div>

                        <div class="col-md-6">
                            <label for="isbn" class="form-label">ISBN *</label>
                            <input type="text" 
                                   class="form-control" 
                                   id="isbn" 
                                   name="isbn" 
                                   value="${libro.isbn}"
                                   placeholder="978-84-xxx-xxxx-x" 
                                   required>
                            <div class="form-text">Código ISBN único del libro</div>
                            <div class="invalid-feedback">
                                Por favor ingrese el ISBN del libro.
                            </div>
                        </div>

                        <div class="col-md-6">
                            <label for="title" class="form-label">Título *</label>
                            <input type="text" 
                                   class="form-control" 
                                   id="title" 
                                   name="title" 
                                   value="${libro.title}"
                                   placeholder="Título del libro" 
                                   required>
                            <div class="invalid-feedback">
                                Por favor ingrese el título del libro.
                            </div>
                        </div>

                        <div class="col-md-6">
                            <label for="authorId" class="form-label">Autor *</label>
                            <select class="form-select" id="authorId" name="authorId" required>
                                <option value="">Seleccionar autor...</option>
                                <c:forEach var="author" items="${authors}">
                                    <option value="${author.authorId}" 
                                            ${author.authorId == libro.authorId ? 'selected' : ''}>
                                        ${author.fullName}
                                    </option>
                                </c:forEach>
                            </select>
                            <div class="invalid-feedback">
                                Por favor seleccione un autor.
                            </div>
                        </div>

                        <div class="col-md-6">
                            <label for="categoryId" class="form-label">Categoría</label>
                            <select class="form-select" id="categoryId" name="categoryId">
                                <option value="">Seleccionar categoría...</option>
                                <%-- TODO: Agregar lista de categorías cuando esté disponible --%>
                                <c:if test="${not empty libro.category}">
                                    <option value="${libro.categoryId}" selected>
                                        ${libro.category.categoryName}
                                    </option>
                                </c:if>
                            </select>
                            <div class="form-text">Opcional: clasificación del libro</div>
                        </div>
                    </div>

                    <!-- Información de Publicación -->
                    <div class="row g-3 mb-4">
                        <div class="col-12">
                            <h6 class="border-bottom pb-2 mb-3">
                                <i class="bi bi-building me-2"></i>Información de Publicación
                            </h6>
                        </div>

                        <div class="col-md-6">
                            <label for="publisher" class="form-label">Editorial</label>
                            <input type="text" 
                                   class="form-control" 
                                   id="publisher" 
                                   name="publisher" 
                                   value="${libro.publisher}"
                                   placeholder="Nombre de la editorial">
                            <div class="form-text">Casa editorial que publicó el libro</div>
                        </div>

                        <div class="col-md-3">
                            <label for="publicationYear" class="form-label">Año de Publicación</label>
                            <input type="number" 
                                   class="form-control" 
                                   id="publicationYear" 
                                   name="publicationYear" 
                                   value="${libro.publicationYear}"
                                   placeholder="2024"
                                   min="1" 
                                   max="2100"
                                   step="1">
                            <div class="form-text">Año de la publicación del libro</div>
                        </div>

                        <div class="col-md-3">
                            <label for="pages" class="form-label">Número de Páginas</label>
                            <input type="number" 
                                   class="form-control" 
                                   id="pages" 
                                   name="pages" 
                                   value="${libro.pages}"
                                   placeholder="350"
                                   min="1"
                                   step="1">
                            <div class="form-text">Total de páginas</div>
                        </div>

                        <div class="col-md-6">
                            <label for="language" class="form-label">Idioma</label>
                            <select class="form-select" id="language" name="language">
                                <option value="">Seleccionar idioma...</option>
                                <option value="Español" ${libro.language == 'Español' ? 'selected' : ''}>Español</option>
                                <option value="Inglés" ${libro.language == 'Inglés' ? 'selected' : ''}>Inglés</option>
                                <option value="Francés" ${libro.language == 'Francés' ? 'selected' : ''}>Francés</option>
                                <option value="Alemán" ${libro.language == 'Alemán' ? 'selected' : ''}>Alemán</option>
                                <option value="Italiano" ${libro.language == 'Italiano' ? 'selected' : ''}>Italiano</option>
                                <option value="Portugués" ${libro.language == 'Portugués' ? 'selected' : ''}>Portugués</option>
                                <option value="Latín" ${libro.language == 'Latín' ? 'selected' : ''}>Latín</option>
                                <option value="Griego" ${libro.language == 'Griego' ? 'selected' : ''}>Griego</option>
                                <option value="Otro" ${libro.language == 'Otro' ? 'selected' : ''}>Otro</option>
                            </select>
                            <div class="form-text">Idioma original del libro</div>
                        </div>
                    </div>

                    <!-- Descripción -->
                    <div class="row g-3 mb-4">
                        <div class="col-12">
                            <h6 class="border-bottom pb-2 mb-3">
                                <i class="bi bi-card-text me-2"></i>Descripción del Contenido
                            </h6>
                        </div>

                        <div class="col-12">
                            <label for="description" class="form-label">Descripción</label>
                            <textarea class="form-control" 
                                      id="description" 
                                      name="description" 
                                      rows="5"
                                      placeholder="Sinopsis o descripción del libro...">${libro.description}</textarea>
                            <div class="form-text">
                                <span id="descCount">0</span> caracteres
                            </div>
                        </div>
                    </div>

                    <!-- Portada -->
                    <div class="row g-3 mb-4">
                        <div class="col-12">
                            <h6 class="border-bottom pb-2 mb-3">
                                <i class="bi bi-image me-2"></i>Portada del Libro
                            </h6>
                        </div>

                        <div class="col-12">
                            <label for="coverImageUrl" class="form-label">URL de la Portada</label>
                            <input type="url" 
                                   class="form-control" 
                                   id="coverImageUrl" 
                                   name="coverImageUrl" 
                                   value="${libro.coverImageUrl}"
                                   placeholder="https://example.com/portada.jpg">
                            <div class="form-text">URL de la imagen de portada (JPG, PNG)</div>
                        </div>

                        <div class="col-12">
                            <div class="card bg-light">
                                <div class="card-body text-center">
                                    <p class="mb-2"><strong>Vista previa:</strong></p>
                                    <c:set var="placeholderUrl" value="https://via.placeholder.com/200x300/3498db/ffffff?text=${fn:substring(libro.title, 0, 1)}" />
                                    <img id="coverPreview" 
                                         src="${not empty libro.coverImageUrl ? libro.coverImageUrl : placeholderUrl}" 
                                         class="img-fluid rounded shadow" 
                                         style="max-height: 300px;"
                                         alt="Portada del libro"
                                         onerror="this.src='${placeholderUrl}'">
                                </div>
                            </div>
                        </div>
                    </div>

                    <!-- Estado -->
                    <div class="row g-3 mb-4">
                        <div class="col-12">
                            <h6 class="border-bottom pb-2 mb-3">
                                <i class="bi bi-toggle-on me-2"></i>Estado de Disponibilidad
                            </h6>
                        </div>

                        <div class="col-md-6">
                            <label for="bookStatusId" class="form-label">Estado del Libro *</label>
                            <select class="form-select" id="bookStatusId" name="bookStatusId" required>
                                <option value="">Seleccionar...</option>
                                <c:forEach var="status" items="${bookStatuses}">
                                    <option value="${status.bookStatusId}" 
                                        ${status.bookStatusId == libro.bookStatusId ? 'selected' : ''}>
                                        ${status.bookStatusName}
                                    </option>
                                </c:forEach>
                            </select>
                            <div class="form-text">
                                Estado actual del libro en el sistema
                            </div>
                        </div>
                    </div>

                    <!-- Botones de Acción -->
                    <div class="row">
                        <div class="col-12">
                            <hr class="my-4">
                            <div class="d-flex justify-content-between">
                                <a href="${pageContext.request.contextPath}/admin/panel?page=mantenimiento-libros" 
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
        <!-- Book Info Card -->
        <div class="card mb-4">
            <div class="card-header bg-info text-white">
                <h6 class="mb-0"><i class="bi bi-info-circle me-2"></i>Información del Registro</h6>
            </div>
            <div class="card-body">
                <table class="table table-sm table-borderless mb-0">
                    <tr>
                        <th style="width: 40%">ID:</th>
                        <td><code class="small">${fn:substring(libro.bookId, 0, 8)}...</code></td>
                    </tr>
                    <tr>
                        <th>ISBN:</th>
                        <td><code class="small">${libro.isbn}</code></td>
                    </tr>
                    <tr>
                        <th>Creado:</th>
                        <td class="small">${libro.createdAt}</td>
                    </tr>
                    <tr>
                        <th>Actualizado:</th>
                        <td class="small">${libro.updatedAt}</td>
                    </tr>
                    <tr>
                        <th>Estado Actual:</th>
                        <td>
                            <c:choose>
                                <c:when test="${libro.bookStatus.bookStatusName == 'Activo'}">
                                    <span class="badge bg-success">Disponible</span>
                                </c:when>
                                <c:otherwise>
                                    <span class="badge bg-secondary">No Disponible</span>
                                </c:otherwise>
                            </c:choose>
                        </td>
                    </tr>
                </table>
            </div>
        </div>

        <!-- Author Info Card -->
        <c:if test="${not empty libro.author}">
            <div class="card mb-4">
                <div class="card-header bg-primary text-white">
                    <h6 class="mb-0"><i class="bi bi-person-badge me-2"></i>Información del Autor</h6>
                </div>
                <div class="card-body">
                    <div class="text-center mb-3">
                        <c:set var="avatarUrl" value="https://ui-avatars.com/api/?name=${fn:replace(libro.author.fullName, ' ', '+')}&size=80&background=3498db&color=fff" />
                        <img src="${not empty libro.author.photoUrl ? libro.author.photoUrl : avatarUrl}" 
                             class="rounded-circle mb-2" 
                             width="80" height="80"
                             alt="${libro.author.fullName}"
                             onerror="this.src='${avatarUrl}'">
                        <h6 class="mb-0">${libro.author.fullName}</h6>
                        <c:if test="${not empty libro.author.country}">
                            <small class="text-muted">
                                <i class="bi bi-flag-fill"></i> ${libro.author.country.countryName}
                            </small>
                        </c:if>
                    </div>
                    <div class="d-grid">
                        <a href="${pageContext.request.contextPath}/admin/panel?page=edit-autor&id=${libro.author.authorId}" 
                           class="btn btn-sm btn-outline-primary">
                            <i class="bi bi-eye"></i> Ver Autor
                        </a>
                    </div>
                </div>
            </div>
        </c:if>

        <!-- Help Card -->
        <div class="card mb-4">
            <div class="card-header bg-success text-white">
                <h6 class="mb-0"><i class="bi bi-question-circle me-2"></i>Ayuda</h6>
            </div>
            <div class="card-body">
                <h6 class="fw-bold">Campos obligatorios</h6>
                <ul class="small">
                    <li>ISBN</li>
                    <li>Título</li>
                    <li>Autor</li>
                    <li>Estado del Libro</li>
                </ul>

                <h6 class="fw-bold mt-3">Consejos</h6>
                <ul class="small mb-0">
                    <li>El ISBN debe ser único en el sistema</li>
                    <li>Una buena descripción ayuda a los usuarios</li>
                    <li>La imagen de portada mejora la presentación</li>
                    <li>Verifica el autor y la categoría sean correctos</li>
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
                            onclick="viewBook('${libro.bookId}')">
                        <i class="bi bi-eye me-1"></i> Ver Detalles
                    </button>
                    <button type="button" class="btn btn-outline-danger btn-sm"
                            onclick="confirmDelete('${libro.bookId}', '${fn:escapeXml(libro.title)}')">
                        <i class="bi bi-trash me-1"></i> Eliminar Libro
                    </button>
                </div>
            </div>
        </div>
    </div>
</div>

<!-- View Book Modal -->
<div class="modal fade" id="viewBookModal" tabindex="-1" aria-hidden="true">
    <div class="modal-dialog modal-lg">
        <div class="modal-content">
            <div class="modal-header bg-info text-white">
                <h5 class="modal-title">
                    <i class="bi bi-book me-2"></i>Detalle del Libro
                </h5>
                <button type="button" class="btn-close btn-close-white" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <div class="modal-body" id="bookDetailContent">
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
                <p>¿Está seguro de que desea eliminar el libro <strong id="deleteBookTitle"></strong>?</p>
                <p class="text-danger mb-0">
                    <i class="bi bi-exclamation-circle me-1"></i>
                    Esta acción no se puede deshacer y se eliminarán todos los datos relacionados.
                </p>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">
                    <i class="bi bi-x-circle"></i> Cancelar
                </button>
                <form action="${pageContext.request.contextPath}/book" method="post" style="display: inline;">
                    <input type="hidden" name="action" value="delete">
                    <input type="hidden" name="bookId" id="deleteBookId">
                    <button type="submit" class="btn btn-danger">
                        <i class="bi bi-trash"></i> Eliminar
                    </button>
                </form>
            </div>
        </div>
    </div>
</div>

<script>
    // Character counter for description
    const descriptionTextarea = document.getElementById('description');
    const descCount = document.getElementById('descCount');
    
    function updateCharCount() {
        const count = descriptionTextarea.value.length;
        descCount.textContent = count;
        
        if (count > 2000) {
            descCount.classList.add('text-warning');
        } else {
            descCount.classList.remove('text-warning');
        }
    }
    
    if (descriptionTextarea) {
        descriptionTextarea.addEventListener('input', updateCharCount);
        updateCharCount(); // Initial count
    }

    // Cover URL preview
    const coverUrlInput = document.getElementById('coverImageUrl');
    const coverPreview = document.getElementById('coverPreview');
    
    if (coverUrlInput) {
        coverUrlInput.addEventListener('change', function() {
            const url = this.value.trim();
            if (url) {
                coverPreview.src = url;
            }
        });
    }

    // Form validation
    const form = document.getElementById('editBookForm');
    
    if (form) {
        form.addEventListener('submit', function(event) {
            if (!form.checkValidity()) {
                event.preventDefault();
                event.stopPropagation();
            }
            form.classList.add('was-validated');
        }, false);
    }

    // View book details
    function viewBook(bookId) {
        const modal = new bootstrap.Modal(document.getElementById('viewBookModal'));
        document.getElementById('bookDetailContent').innerHTML = 
            '<div class="text-center py-4"><div class="spinner-border text-primary" role="status"></div><p class="mt-2">Cargando...</p></div>';
        modal.show();
        
        fetch('${pageContext.request.contextPath}/book?action=view&id=' + bookId)
            .then(response => response.text())
            .then(html => {
                document.getElementById('bookDetailContent').innerHTML = html;
            })
            .catch(error => {
                document.getElementById('bookDetailContent').innerHTML = 
                    '<div class="alert alert-danger">Error al cargar los detalles</div>';
            });
    }

    // Confirm delete
    function confirmDelete(bookId, bookTitle) {
        document.getElementById('deleteBookId').value = bookId;
        document.getElementById('deleteBookTitle').textContent = bookTitle;
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

    // Initialize tooltips
    document.addEventListener('DOMContentLoaded', function() {
        var tooltipTriggerList = [].slice.call(document.querySelectorAll('[data-bs-toggle="tooltip"]'));
        var tooltipList = tooltipTriggerList.map(function(tooltipTriggerEl) {
            return new bootstrap.Tooltip(tooltipTriggerEl);
        });
    });
</script>
