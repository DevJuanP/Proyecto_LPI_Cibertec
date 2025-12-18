<!-- src/main/webapp/admin/panel/items/mantenimiento-libros.jsp -->
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>

<div class="content-header">
    <div class="d-flex justify-content-between align-items-center">
        <div>
            <h2 class="mb-0"><i class="bi bi-book me-2"></i>Mantenimiento de Libros</h2>
            <p class="text-muted mb-0">Gestión completa del catálogo de libros</p>
        </div>
        <div>
            <button class="btn btn-success" data-bs-toggle="modal" data-bs-target="#addBookModal">
                <i class="bi bi-plus-circle"></i> Nuevo Libro
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
                        <small class="text-muted">Total Libros</small>
                        <h4 class="mb-0">${totalBooks}</h4>
                    </div>
                    <i class="bi bi-book-fill fs-2 text-primary"></i>
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
                        <h4 class="mb-0">${activeBooksCount}</h4>
                    </div>
                    <i class="bi bi-check-circle-fill fs-2 text-success"></i>
                </div>
            </div>
        </div>
    </div>
    <div class="col-md-3">
        <div class="card border-start border-warning border-4">
            <div class="card-body">
                <div class="d-flex justify-content-between">
                    <div>
                        <small class="text-muted">En Alquiler</small>
                        <h4 class="mb-0">-</h4>
                    </div>
                    <i class="bi bi-arrow-repeat fs-2 text-warning"></i>
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
                        <h4 class="mb-0">${fn:length(booksResult.items)}</h4>
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
            <input type="hidden" name="page" value="mantenimiento-libros">
            <div class="row g-3">
                <div class="col-md-3">
                    <label class="form-label">Buscar</label>
                    <div class="input-group">
                        <span class="input-group-text"><i class="bi bi-search"></i></span>
                        <input type="text" name="search" class="form-control" 
                               placeholder="Título o ISBN..." value="${searchValue}">
                    </div>
                </div>
                <div class="col-md-2">
                    <label class="form-label">Autor</label>
                    <select name="authorId" class="form-select">
                        <option value="">Todos</option>
                        <c:forEach var="author" items="${authors}">
                            <option value="${author.authorId}" 
                                ${author.authorId == authorIdValue ? 'selected' : ''}>
                                ${author.fullName}
                            </option>
                        </c:forEach>
                    </select>
                </div>
                <div class="col-md-2">
                    <label class="form-label">Categoría</label>
                    <select name="categoryId" class="form-select">
                        <option value="">Todas</option>
                        <c:forEach var="category" items="${categories}">
                            <option value="${category.categoryId}" 
                                ${category.categoryId == categoryIdValue ? 'selected' : ''}>
                                ${category.categoryName}
                            </option>
                        </c:forEach>
                    </select>
                </div>
                <div class="col-md-2">
                    <label class="form-label">Estado</label>
                    <select name="bookStatusId" class="form-select">
                        <option value="">Todos</option>
                        <c:forEach var="bookStatus" items="${bookStatuses}">
                            <option value="${bookStatus.bookStatusId}" 
                                ${bookStatus.bookStatusId == bookStatusIdValue ? 'selected' : ''}>
                                ${bookStatus.bookStatusName}
                            </option>
                        </c:forEach>
                    </select>
                </div>
                <div class="col-md-3">
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

<!-- Books Table -->
<div class="card">
    <div class="card-header d-flex justify-content-between align-items-center">
        <h5 class="mb-0">Catálogo de Libros</h5>
        <div>
            <span class="text-muted me-3">
                Mostrando ${booksResult.startItem} - ${booksResult.endItem} de ${booksResult.totalItems}
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
            <c:when test="${empty booksResult.items}">
                <div class="text-center py-5">
                    <i class="bi bi-inbox fs-1 text-muted"></i>
                    <p class="text-muted mt-3">No se encontraron libros</p>
                    <c:if test="${not empty searchValue or not empty authorIdValue or not empty categoryIdValue or not empty bookStatusIdValue}">
                        <a href="${pageContext.request.contextPath}/admin/panel?page=mantenimiento-libros" 
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
                                <th>ISBN</th>
                                <th>Título</th>
                                <th>Autor</th>
                                <th>Editorial</th>
                                <th>Año</th>
                                <th>Categoría</th>
                                <th>Estado</th>
                                <th style="width: 150px;">Acciones</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="book" items="${booksResult.items}" varStatus="status">
                                <tr>
                                    <td>
                                        <input type="checkbox" class="form-check-input row-checkbox" 
                                               value="${book.bookId}">
                                    </td>
                                    <td><code>${book.isbn}</code></td>
                                    <td>
                                        <div class="d-flex align-items-center">
                                            <c:set var="coverUrl" value="https://via.placeholder.com/40x60/3498db/ffffff?text=${fn:substring(book.title, 0, 1)}" />
                                            <c:choose>
                                                <c:when test="${not empty book.coverImageUrl}">
                                                    <img src="${book.coverImageUrl}" 
                                                        class="rounded me-2" 
                                                        width="30" height="40"
                                                        alt="${book.title}"
                                                        style="object-fit: cover;"
                                                        onerror="this.src='${coverUrl}'">
                                                </c:when>
                                                <c:otherwise>
                                                    <img src="${coverUrl}" 
                                                        class="rounded me-2" 
                                                        width="30" height="40"
                                                        alt="${book.title}">
                                                </c:otherwise>
                                            </c:choose>
                                            <strong>${book.title}</strong>
                                        </div>
                                    </td>
                                    <td>
                                        <c:choose>
                                            <c:when test="${not empty book.author}">
                                                ${book.author.fullName}
                                            </c:when>
                                            <c:otherwise>
                                                <span class="text-muted">-</span>
                                            </c:otherwise>
                                        </c:choose>
                                    </td>
                                    <td>
                                        <c:choose>
                                            <c:when test="${not empty book.publisher}">
                                                ${book.publisher}
                                            </c:when>
                                            <c:otherwise>
                                                <span class="text-muted">-</span>
                                            </c:otherwise>
                                        </c:choose>
                                    </td>
                                    <td>
                                        <c:choose>
                                            <c:when test="${not empty book.publicationYear}">
                                                ${book.publicationYear}
                                            </c:when>
                                            <c:otherwise>
                                                <span class="text-muted">-</span>
                                            </c:otherwise>
                                        </c:choose>
                                    </td>
                                    <td>
                                        <c:choose>
                                            <c:when test="${not empty book.category}">
                                                <span class="badge bg-primary">${book.category.categoryName}</span>
                                            </c:when>
                                            <c:otherwise>
                                                <span class="badge bg-secondary">Sin categoría</span>
                                            </c:otherwise>
                                        </c:choose>
                                    </td>
                                    <td>
                                        <c:choose>
                                            <c:when test="${book.bookStatus.bookStatusName == 'Activo'}">
                                                <span class="badge bg-success">Disponible</span>
                                            </c:when>
                                            <c:otherwise>
                                                <span class="badge bg-secondary">No Disponible</span>
                                            </c:otherwise>
                                        </c:choose>
                                    </td>
                                    <td>
                                        <div class="btn-group btn-group-sm">
                                            <button class="btn btn-info" data-bs-toggle="tooltip" title="Ver detalles"
                                                    onclick="viewBook('${book.bookId}')">
                                                <i class="bi bi-eye"></i>
                                            </button>
                                            <button class="btn btn-warning" data-bs-toggle="tooltip" title="Editar"
                                                    onclick="editBook('${book.bookId}')">
                                                <i class="bi bi-pencil"></i>
                                            </button>
                                            <button class="btn btn-danger" data-bs-toggle="tooltip" title="Eliminar"
                                                    onclick="deleteBook('${book.bookId}', '${fn:escapeXml(book.title)}')">
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
                <c:if test="${booksResult.totalPages > 1}">
                    <nav aria-label="Paginación de libros" class="mt-4">
                        <ul class="pagination justify-content-center">
                            <%-- Botón Anterior --%>
                            <li class="page-item ${!booksResult.hasPreviousPage() ? 'disabled' : ''}">
                                <a class="page-link" 
                                href="${pageContext.request.contextPath}/admin/panel?page=mantenimiento-libros&p=${booksResult.previousPage}&search=${searchValue}&authorId=${authorIdValue}&categoryId=${categoryIdValue}&bookStatusId=${bookStatusIdValue}">
                                    <i class="bi bi-chevron-left"></i> Anterior
                                </a>
                            </li>
                            
                            <%-- Números de página --%>
                            <c:set var="pageRange" value="${booksResult.getPageRange(5)}" />
                            
                            <c:if test="${pageRange[0] > 1}">
                                <li class="page-item">
                                    <a class="page-link" 
                                    href="${pageContext.request.contextPath}/admin/panel?page=mantenimiento-libros&p=1&search=${searchValue}&authorId=${authorIdValue}&categoryId=${categoryIdValue}&bookStatusId=${bookStatusIdValue}">
                                        1
                                    </a>
                                </li>
                                <c:if test="${pageRange[0] > 2}">
                                    <li class="page-item disabled"><span class="page-link">...</span></li>
                                </c:if>
                            </c:if>
                            
                            <c:forEach begin="${pageRange[0]}" end="${pageRange[1]}" var="i">
                                <li class="page-item ${i == booksResult.currentPage ? 'active' : ''}">
                                    <a class="page-link" 
                                    href="${pageContext.request.contextPath}/admin/panel?page=mantenimiento-libros&p=${i}&search=${searchValue}&authorId=${authorIdValue}&categoryId=${categoryIdValue}&bookStatusId=${bookStatusIdValue}">
                                        ${i}
                                    </a>
                                </li>
                            </c:forEach>
                            
                            <c:if test="${pageRange[1] < booksResult.totalPages}">
                                <c:if test="${pageRange[1] < booksResult.totalPages - 1}">
                                    <li class="page-item disabled"><span class="page-link">...</span></li>
                                </c:if>
                                <li class="page-item">
                                    <a class="page-link" 
                                    href="${pageContext.request.contextPath}/admin/panel?page=mantenimiento-libros&p=${booksResult.totalPages}&search=${searchValue}&authorId=${authorIdValue}&categoryId=${categoryIdValue}&bookStatusId=${bookStatusIdValue}">
                                        ${booksResult.totalPages}
                                    </a>
                                </li>
                            </c:if>
                            
                            <%-- Botón Siguiente --%>
                            <li class="page-item ${!booksResult.hasNextPage() ? 'disabled' : ''}">
                                <a class="page-link" 
                                href="${pageContext.request.contextPath}/admin/panel?page=mantenimiento-libros&p=${booksResult.nextPage}&search=${searchValue}&authorId=${authorIdValue}&categoryId=${categoryIdValue}&bookStatusId=${bookStatusIdValue}">
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

<!-- Add Book Modal -->
<div class="modal fade" id="addBookModal" tabindex="-1" aria-labelledby="addBookModalLabel" aria-hidden="true">
    <div class="modal-dialog modal-xl">
        <div class="modal-content">
            <div class="modal-header bg-primary text-white">
                <h5 class="modal-title" id="addBookModalLabel">
                    <i class="bi bi-plus-circle me-2"></i>Agregar Nuevo Libro
                </h5>
                <button type="button" class="btn-close btn-close-white" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <form action="${pageContext.request.contextPath}/book" method="post">
                <input type="hidden" name="action" value="create">
                <div class="modal-body">
                    <div class="row g-3">
                        <div class="col-md-6">
                            <label class="form-label">ISBN *</label>
                            <input type="text" name="isbn" class="form-control" 
                                   placeholder="978-84-xxx-xxxx-x" required>
                        </div>
                        <div class="col-md-6">
                            <label class="form-label">Título *</label>
                            <input type="text" name="title" class="form-control" 
                                   placeholder="Título del libro" required>
                        </div>
                        <div class="col-md-6">
                            <label class="form-label">Autor *</label>
                            <select name="authorId" class="form-select" required>
                                <option value="">Seleccionar autor...</option>
                                <c:forEach var="author" items="${authors}">
                                    <option value="${author.authorId}">${author.fullName}</option>
                                </c:forEach>
                            </select>
                        </div>
                        <div class="col-md-6">
                            <label class="form-label">Categoría</label>
                            <select name="categoryId" class="form-select">
                                <option value="">Seleccionar categoría...</option>
                                <c:forEach var="category" items="${categories}">
                                    <option value="${category.categoryId}">${category.categoryName}</option>
                                </c:forEach>
                            </select>
                        </div>
                        <div class="col-md-6">
                            <label class="form-label">Editorial</label>
                            <input type="text" name="publisher" class="form-control" 
                                   placeholder="Nombre de la editorial">
                        </div>
                        <div class="col-md-3">
                            <label class="form-label">Año de Publicación</label>
                            <input type="number" name="publicationYear" class="form-control" 
                                   placeholder="2024" min="1" max="2100">
                        </div>
                        <div class="col-md-3">
                            <label class="form-label">Páginas</label>
                            <input type="number" name="pages" class="form-control" 
                                   placeholder="350" min="1">
                        </div>
                        <div class="col-md-6">
                            <label class="form-label">Idioma</label>
                            <select name="language" class="form-select">
                                <option value="">Seleccionar...</option>
                                <option value="Español">Español</option>
                                <option value="Inglés">Inglés</option>
                                <option value="Francés">Francés</option>
                                <option value="Alemán">Alemán</option>
                                <option value="Italiano">Italiano</option>
                                <option value="Portugués">Portugués</option>
                                <option value="Otro">Otro</option>
                            </select>
                        </div>
                        <div class="col-12">
                            <label class="form-label">Descripción</label>
                            <textarea name="description" class="form-control" rows="3" 
                                      placeholder="Breve descripción del libro..."></textarea>
                        </div>
                        <div class="col-12">
                            <label class="form-label">URL de Portada</label>
                            <input type="url" name="coverImageUrl" class="form-control" 
                                   placeholder="https://example.com/portada.jpg">
                        </div>
                    </div>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">
                        <i class="bi bi-x-circle"></i> Cancelar
                    </button>
                    <button type="submit" class="btn btn-primary">
                        <i class="bi bi-save"></i> Guardar Libro
                    </button>
                </div>
            </form>
        </div>
    </div>
</div>

<!-- View Book Modal -->
<div class="modal fade" id="bookDetailModal" tabindex="-1" aria-hidden="true">
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
<div class="modal fade" id="deleteBookModal" tabindex="-1" aria-hidden="true">
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
                    Esta acción no se puede deshacer.
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
    // Select all checkbox
    document.getElementById('selectAll')?.addEventListener('change', function() {
        const checkboxes = document.querySelectorAll('.row-checkbox');
        checkboxes.forEach(checkbox => checkbox.checked = this.checked);
    });

    // View book details
    function viewBook(bookId) {
        const modal = new bootstrap.Modal(document.getElementById('bookDetailModal'));
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

    // Edit book
    function editBook(bookId) {
        window.location.href = '${pageContext.request.contextPath}/admin/panel?page=edit-libro&id=' + bookId;
    }

    // Delete book
    function deleteBook(bookId, bookTitle) {
        document.getElementById('deleteBookId').value = bookId;
        document.getElementById('deleteBookTitle').textContent = bookTitle;
        const modal = new bootstrap.Modal(document.getElementById('deleteBookModal'));
        modal.show();
    }

    // Initialize tooltips
    document.addEventListener('DOMContentLoaded', function() {
        var tooltipTriggerList = [].slice.call(document.querySelectorAll('[data-bs-toggle="tooltip"]'));
        var tooltipList = tooltipTriggerList.map(function(tooltipTriggerEl) {
            return new bootstrap.Tooltip(tooltipTriggerEl);
        });
    });
</script>
