<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<div class="content-header">
    <div class="d-flex justify-content-between align-items-center">
        <div>
            <h2 class="mb-0"><i class="bi bi-graph-up-arrow me-2"></i>Libros Más Pedidos</h2>
            <p class="text-muted mb-0">Ranking de libros más solicitados</p>
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

<!-- Filters -->
<div class="card mb-4">
    <div class="card-body">
        <form method="get" action="${pageContext.request.contextPath}/admin/panel">
            <input type="hidden" name="page" value="libros-pedidos">
            <div class="row g-3 align-items-end">
                <div class="col-md-4">
                    <label class="form-label">Categoría</label>
                    <select class="form-select" name="categoryId">
                        <option value="">Todas las categorías</option>
                        <c:forEach var="category" items="${categories}">
                            <option value="${category.categoryId}" 
                                ${category.categoryId == categoryIdValue ? 'selected' : ''}>
                                ${category.categoryName}
                            </option>
                        </c:forEach>
                    </select>
                </div>
                <div class="col-md-4">
                    <label class="form-label">Resultados por página</label>
                    <select class="form-select" name="size">
                        <option value="10" ${param.size == '10' ? 'selected' : ''}>Top 10</option>
                        <option value="20" ${empty param.size || param.size == '20' ? 'selected' : ''}>Top 20</option>
                        <option value="50" ${param.size == '50' ? 'selected' : ''}>Top 50</option>
                        <option value="100" ${param.size == '100' ? 'selected' : ''}>Top 100</option>
                    </select>
                </div>
                <div class="col-md-4">
                    <button type="submit" class="btn btn-primary w-100">
                        <i class="bi bi-funnel"></i> Filtrar
                    </button>
                </div>
            </div>
        </form>
    </div>
</div>

<!-- Top 3 Highlight -->
<c:if test="${not empty booksStats.items && fn:length(booksStats.items) >= 3}">
    <div class="row g-4 mb-4">
        <!-- Primer lugar -->
        <c:set var="book1" value="${booksStats.items[0]}" />
        <div class="col-md-4">
            <div class="card border-warning shadow-lg">
                <div class="card-body text-center">
                    <div class="position-relative d-inline-block mb-3">
                        <i class="bi bi-trophy-fill text-warning" style="font-size: 4rem;"></i>
                        <span class="position-absolute top-0 start-100 translate-middle badge rounded-pill bg-warning">
                            #1
                        </span>
                    </div>
                    <h4 class="fw-bold">${book1.title}</h4>
                    <p class="text-muted mb-2">${book1.authorName}</p>
                    <h2 class="text-warning fw-bold">${book1.totalRentals}</h2>
                    <p class="text-muted">alquileres totales</p>
                    <div class="progress" style="height: 8px;">
                        <div class="progress-bar bg-warning" role="progressbar" style="width: 100%"></div>
                    </div>
                </div>
            </div>
        </div>
        
        <!-- Segundo lugar -->
        <c:set var="book2" value="${booksStats.items[1]}" />
        <div class="col-md-4">
            <div class="card border-secondary shadow">
                <div class="card-body text-center">
                    <div class="position-relative d-inline-block mb-3">
                        <i class="bi bi-trophy-fill text-secondary" style="font-size: 4rem;"></i>
                        <span class="position-absolute top-0 start-100 translate-middle badge rounded-pill bg-secondary">
                            #2
                        </span>
                    </div>
                    <h4 class="fw-bold">${book2.title}</h4>
                    <p class="text-muted mb-2">${book2.authorName}</p>
                    <h2 class="text-secondary fw-bold">${book2.totalRentals}</h2>
                    <p class="text-muted">alquileres totales</p>
                    <div class="progress" style="height: 8px;">
                        <div class="progress-bar bg-secondary" role="progressbar" 
                             style="width: ${book2.popularityPercentage}%"></div>
                    </div>
                </div>
            </div>
        </div>
        
        <!-- Tercer lugar -->
        <c:set var="book3" value="${booksStats.items[2]}" />
        <div class="col-md-4">
            <div class="card border-info shadow">
                <div class="card-body text-center">
                    <div class="position-relative d-inline-block mb-3">
                        <i class="bi bi-trophy-fill text-info" style="font-size: 4rem;"></i>
                        <span class="position-absolute top-0 start-100 translate-middle badge rounded-pill bg-info">
                            #3
                        </span>
                    </div>
                    <h4 class="fw-bold">${book3.title}</h4>
                    <p class="text-muted mb-2">${book3.authorName}</p>
                    <h2 class="text-info fw-bold">${book3.totalRentals}</h2>
                    <p class="text-muted">alquileres totales</p>
                    <div class="progress" style="height: 8px;">
                        <div class="progress-bar bg-info" role="progressbar" 
                             style="width: ${book3.popularityPercentage}%"></div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</c:if>

<!-- Complete Ranking Table -->
<div class="card">
    <div class="card-header d-flex justify-content-between align-items-center">
        <h5 class="mb-0">Ranking Completo</h5>
        <span class="badge bg-secondary">
            ${booksStats.totalItems} libro(s) encontrado(s)
        </span>
    </div>
    <div class="card-body">
        <c:choose>
            <c:when test="${empty booksStats.items}">
                <div class="text-center py-5">
                    <i class="bi bi-inbox fs-1 text-muted"></i>
                    <p class="text-muted mt-3">No hay libros alquilados aún</p>
                    <c:if test="${not empty categoryIdValue}">
                        <a href="${pageContext.request.contextPath}/admin/panel?page=libros-pedidos" 
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
                                <th style="width: 80px;">Posición</th>
                                <th>ISBN</th>
                                <th>Título</th>
                                <th>Autor</th>
                                <th>Categoría</th>
                                <th>Alquileres</th>
                                <th>Tendencia Diaria</th>
                                <th>Popularidad</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="bookStat" items="${booksStats.items}" varStatus="status">
                                <c:set var="position" value="${booksStats.startItem + status.index}" />
                                <tr class="${position <= 3 ? (position == 1 ? 'table-warning' : (position == 2 ? 'table-secondary' : 'table-info')) : ''}">
                                    <td class="text-center">
                                        <c:choose>
                                            <c:when test="${position == 1}">
                                                <span class="badge bg-warning fs-6">${position}</span>
                                            </c:when>
                                            <c:when test="${position == 2}">
                                                <span class="badge bg-secondary fs-6">${position}</span>
                                            </c:when>
                                            <c:when test="${position == 3}">
                                                <span class="badge bg-info fs-6">${position}</span>
                                            </c:when>
                                            <c:otherwise>
                                                ${position}
                                            </c:otherwise>
                                        </c:choose>
                                    </td>
                                    <td><code>${bookStat.isbn}</code></td>
                                    <td>
                                        <c:choose>
                                            <c:when test="${position <= 3}">
                                                <strong>${bookStat.title}</strong>
                                            </c:when>
                                            <c:otherwise>
                                                ${bookStat.title}
                                            </c:otherwise>
                                        </c:choose>
                                    </td>
                                    <td>${bookStat.authorName}</td>
                                    <td>
                                        <span class="badge bg-primary">${bookStat.categoryName}</span>
                                    </td>
                                    <td>
                                        <c:choose>
                                            <c:when test="${position <= 3}">
                                                <strong>${bookStat.totalRentals}</strong>
                                            </c:when>
                                            <c:otherwise>
                                                ${bookStat.totalRentals}
                                            </c:otherwise>
                                        </c:choose>
                                    </td>
                                    <td>
                                        <span class="${bookStat.trendClass}">
                                            <i class="bi ${bookStat.trendIcon}"></i> 
                                            <fmt:formatNumber value="${bookStat.trendPercentage}" pattern="+#,##0.#;-#" />%
                                        </span>
                                        <br>
                                        <small class="text-muted">
                                            Ayer: ${bookStat.yesterdayRentals} | Hoy: ${bookStat.todayRentals}
                                        </small>
                                    </td>
                                    <td>
                                        <div class="progress" style="height: 20px;">
                                            <div class="progress-bar ${position == 1 ? 'bg-warning' : (position == 2 ? 'bg-secondary' : (position == 3 ? 'bg-info' : ''))}" 
                                                 role="progressbar" 
                                                 style="width: ${bookStat.popularityPercentage}%">
                                                ${bookStat.popularityPercentage}%
                                            </div>
                                        </div>
                                    </td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                </div>
                
                <!-- Pagination -->
                <c:if test="${booksStats.totalPages > 1}">
                    <nav aria-label="Page navigation" class="mt-4">
                        <ul class="pagination justify-content-center">
                            <!-- Previous Page -->
                            <li class="page-item ${booksStats.currentPage == 1 ? 'disabled' : ''}">
                                <a class="page-link" 
                                   href="${pageContext.request.contextPath}/admin/panel?page=libros-pedidos&p=${booksStats.currentPage - 1}&size=${booksStats.pageSize}${not empty categoryIdValue ? '&categoryId='.concat(categoryIdValue) : ''}">
                                    Anterior
                                </a>
                            </li>
                            
                            <!-- Page Numbers -->
                            <c:forEach begin="1" end="${booksStats.totalPages}" var="pageNum">
                                <c:if test="${pageNum <= 5 || pageNum > booksStats.totalPages - 5 || (pageNum >= booksStats.currentPage - 2 && pageNum <= booksStats.currentPage + 2)}">
                                    <li class="page-item ${booksStats.currentPage == pageNum ? 'active' : ''}">
                                        <a class="page-link" 
                                           href="${pageContext.request.contextPath}/admin/panel?page=libros-pedidos&p=${pageNum}&size=${booksStats.pageSize}${not empty categoryIdValue ? '&categoryId='.concat(categoryIdValue) : ''}">
                                            ${pageNum}
                                        </a>
                                    </li>
                                </c:if>
                            </c:forEach>
                            
                            <!-- Next Page -->
                            <li class="page-item ${booksStats.currentPage == booksStats.totalPages ? 'disabled' : ''}">
                                <a class="page-link" 
                                   href="${pageContext.request.contextPath}/admin/panel?page=libros-pedidos&p=${booksStats.currentPage + 1}&size=${booksStats.pageSize}${not empty categoryIdValue ? '&categoryId='.concat(categoryIdValue) : ''}">
                                    Siguiente
                                </a>
                            </li>
                        </ul>
                    </nav>
                    
                    <!-- Page Info -->
                    <div class="text-center text-muted">
                        <small>
                            Mostrando ${booksStats.startItem} - ${booksStats.endItem} de ${booksStats.totalItems} libros
                        </small>
                    </div>
                </c:if>
            </c:otherwise>
        </c:choose>
    </div>
</div>
