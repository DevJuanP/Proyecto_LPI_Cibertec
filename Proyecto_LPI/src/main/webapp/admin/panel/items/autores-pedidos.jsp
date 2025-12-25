<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<c:set var="topAuthors" value="${topAuthors != null ? topAuthors : []}" />
<c:set var="totalAuthorsWithRentals" value="${totalAuthorsWithRentals != null ? totalAuthorsWithRentals : 0}" />
<c:set var="totalRentals" value="${totalRentals != null ? totalRentals : 0}" />
<c:set var="totalAuthors" value="${totalAuthors != null ? totalAuthors : 0}" />
<c:set var="avgRentalsPerAuthor" value="${avgRentalsPerAuthor != null ? avgRentalsPerAuthor : 0.0}" />
<c:set var="countryIdValue" value="${countryIdValue != null ? countryIdValue : ''}" />
<c:set var="statusIdValue" value="${statusIdValue != null ? statusIdValue : ''}" />
<c:set var="topValue" value="${topValue != null ? topValue : 20}" />

<div class="content-header">
    <div class="d-flex justify-content-between align-items-center">
        <div>
            <h2 class="mb-0"><i class="bi bi-people-fill me-2"></i>Autores Más Pedidos</h2>
            <p class="text-muted mb-0">Ranking de autores más solicitados</p>
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
        <form method="GET" action="${pageContext.request.contextPath}/admin/panel">
            <input type="hidden" name="page" value="autores-pedidos">
            <div class="row g-3 align-items-end">
                <div class="col-md-3">
                    <label class="form-label">Nacionalidad</label>
                    <select class="form-select" name="countryId">
                        <option value="" ${countryIdValue == '' ? 'selected' : ''}>Todas</option>
                        <c:forEach var="country" items="${countries}">
                            <option value="${country.countryId}" 
                                    ${countryIdValue == country.countryId ? 'selected' : ''}>
                                ${country.countryName}
                            </option>
                        </c:forEach>
                    </select>
                </div>
                <div class="col-md-3">
                    <label class="form-label">Estado</label>
                    <select class="form-select" name="statusId">
                        <option value="" ${statusIdValue == '' ? 'selected' : ''}>Todos</option>
                        <c:forEach var="status" items="${statuses}">
                            <option value="${status.statusId}" 
                                    ${statusIdValue == status.statusId ? 'selected' : ''}>
                                ${status.statusName}
                            </option>
                        </c:forEach>
                    </select>
                </div>
                <div class="col-md-3">
                    <label class="form-label">Top</label>
                    <select class="form-select" name="top">
                        <option value="10" ${topValue == 10 ? 'selected' : ''}>Top 10</option>
                        <option value="20" ${topValue == 20 ? 'selected' : ''}>Top 20</option>
                        <option value="50" ${topValue == 50 ? 'selected' : ''}>Top 50</option>
                        <option value="100" ${topValue == 100 ? 'selected' : ''}>Top 100</option>
                    </select>
                </div>
                <div class="col-md-3">
                    <button type="submit" class="btn btn-primary w-100">
                        <i class="bi bi-funnel"></i> Filtrar
                    </button>
                </div>
            </div>
        </form>
    </div>
</div>

<!-- Top 3 Authors -->
<c:if test="${fn:length(topAuthors) >= 3}">
    <div class="row g-4 mb-4">
        <!-- Primer Lugar -->
        <c:set var="author1" value="${topAuthors[0]}" />
        <div class="col-md-4">
            <div class="card border-warning shadow-lg">
                <div class="card-body text-center">
                    <div class="position-relative d-inline-block mb-3">
                        <c:choose>
                            <c:when test="${not empty author1.photoUrl}">
                                <img src="${author1.photoUrl}" 
                                     class="rounded-circle border border-warning border-4" 
                                     alt="${author1.fullName}"
                                     style="width: 120px; height: 120px; object-fit: cover;">
                            </c:when>
                            <c:otherwise>
                                <img src="https://ui-avatars.com/api/?name=${fn:replace(author1.fullName, ' ', '+')}&size=120&background=ffc107&color=000" 
                                     class="rounded-circle border border-warning border-4" 
                                     alt="${author1.fullName}">
                            </c:otherwise>
                        </c:choose>
                        <span class="position-absolute top-0 start-100 translate-middle badge rounded-pill bg-warning fs-6">
                            #1
                        </span>
                    </div>
                    <h4 class="fw-bold">${author1.fullName}</h4>
                    <p class="text-muted mb-2"><i class="bi bi-flag-fill"></i> ${author1.countryName}</p>
                    <h2 class="text-warning fw-bold">${author1.totalRentals}</h2>
                    <p class="text-muted mb-3">alquileres totales</p>
                    <div class="row g-2 text-start">
                        <div class="col-6">
                            <small class="text-muted">Libros:</small>
                            <div class="fw-bold">${author1.totalBooks}</div>
                        </div>
                        <div class="col-6">
                            <small class="text-muted">Disponibles:</small>
                            <div class="fw-bold">${author1.availableCopies}</div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        
        <!-- Segundo Lugar -->
        <c:set var="author2" value="${topAuthors[1]}" />
        <div class="col-md-4">
            <div class="card border-secondary shadow">
                <div class="card-body text-center">
                    <div class="position-relative d-inline-block mb-3">
                        <c:choose>
                            <c:when test="${not empty author2.photoUrl}">
                                <img src="${author2.photoUrl}" 
                                     class="rounded-circle border border-secondary border-4" 
                                     alt="${author2.fullName}"
                                     style="width: 120px; height: 120px; object-fit: cover;">
                            </c:when>
                            <c:otherwise>
                                <img src="https://ui-avatars.com/api/?name=${fn:replace(author2.fullName, ' ', '+')}&size=120&background=6c757d&color=fff" 
                                     class="rounded-circle border border-secondary border-4" 
                                     alt="${author2.fullName}">
                            </c:otherwise>
                        </c:choose>
                        <span class="position-absolute top-0 start-100 translate-middle badge rounded-pill bg-secondary fs-6">
                            #2
                        </span>
                    </div>
                    <h4 class="fw-bold">${author2.fullName}</h4>
                    <p class="text-muted mb-2"><i class="bi bi-flag-fill"></i> ${author2.countryName}</p>
                    <h2 class="text-secondary fw-bold">${author2.totalRentals}</h2>
                    <p class="text-muted mb-3">alquileres totales</p>
                    <div class="row g-2 text-start">
                        <div class="col-6">
                            <small class="text-muted">Libros:</small>
                            <div class="fw-bold">${author2.totalBooks}</div>
                        </div>
                        <div class="col-6">
                            <small class="text-muted">Disponibles:</small>
                            <div class="fw-bold">${author2.availableCopies}</div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        
        <!-- Tercer Lugar -->
        <c:set var="author3" value="${topAuthors[2]}" />
        <div class="col-md-4">
            <div class="card border-info shadow">
                <div class="card-body text-center">
                    <div class="position-relative d-inline-block mb-3">                        
                        <c:choose>
                            <c:when test="${not empty author3.photoUrl}">
                                <img src="${author3.photoUrl}" 
                                     class="rounded-circle border border-info border-4" 
                                     alt="${author3.fullName}"
                                     style="width: 120px; height: 120px; object-fit: cover;">
                            </c:when>
                            <c:otherwise>
                                <img src="https://ui-avatars.com/api/?name=${fn:replace(author3.fullName, ' ', '+')}&size=120&background=0dcaf0&color=000" 
                                     class="rounded-circle border border-info border-4" 
                                     alt="${author3.fullName}">
                            </c:otherwise>
                        </c:choose>
                        <span class="position-absolute top-0 start-100 translate-middle badge rounded-pill bg-info fs-6">
                            #3
                        </span>
                    </div>
                    <h4 class="fw-bold">${author3.fullName}</h4>
                    <p class="text-muted mb-2"><i class="bi bi-flag-fill"></i> ${author3.countryName}</p>
                    <h2 class="text-info fw-bold">${author3.totalRentals}</h2>
                    <p class="text-muted mb-3">alquileres totales</p>
                    <div class="row g-2 text-start">
                        <div class="col-6">
                            <small class="text-muted">Libros:</small>
                            <div class="fw-bold">${author3.totalBooks}</div>
                        </div>
                        <div class="col-6">
                            <small class="text-muted">Disponibles:</small>
                            <div class="fw-bold">${author3.availableCopies}</div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</c:if>

<!-- Statistics Cards -->
<div class="row g-3 mb-4">
    <div class="col-md-3">
        <div class="card bg-primary bg-gradient text-white">
            <div class="card-body">
                <h6><i class="bi bi-people me-2"></i>Total Autores</h6>
                <h3 class="mb-0">${totalAuthors}</h3>
            </div>
        </div>
    </div>
    <div class="col-md-3">
        <div class="card bg-success bg-gradient text-white">
            <div class="card-body">
                <h6><i class="bi bi-star me-2"></i>Con Alquileres</h6>
                <h3 class="mb-0">${totalAuthorsWithRentals}</h3>
            </div>
        </div>
    </div>
    <div class="col-md-3">
        <div class="card bg-info bg-gradient text-white">
            <div class="card-body">
                <h6><i class="bi bi-bookmark-check me-2"></i>Total Alquileres</h6>
                <h3 class="mb-0">${totalRentals}</h3>
            </div>
        </div>
    </div>
    <div class="col-md-3">
        <div class="card bg-warning bg-gradient text-white">
            <div class="card-body">
                <h6><i class="bi bi-graph-up me-2"></i>Promedio/Autor</h6>
                <h3 class="mb-0"><fmt:formatNumber value="${avgRentalsPerAuthor}" maxFractionDigits="1"/></h3>
            </div>
        </div>
    </div>
</div>

<!-- Ranking Table -->
<div class="card">
    <div class="card-header d-flex justify-content-between align-items-center">
        <h5 class="mb-0">Ranking Completo</h5>
        <span class="badge bg-secondary">${fn:length(topAuthors)} resultado(s)</span>
    </div>
    <div class="card-body p-0">
        <c:choose>
            <c:when test="${fn:length(topAuthors) == 0}">
                <div class="alert alert-info m-4">
                    <i class="bi bi-info-circle me-2"></i>
                    <c:choose>
                        <c:when test="${not empty countryIdValue or not empty statusIdValue}">
                            No se encontraron autores con alquileres para los filtros aplicados.
                        </c:when>
                        <c:otherwise>
                            No hay autores con alquileres registrados en el sistema.
                        </c:otherwise>
                    </c:choose>
                </div>
            </c:when>
            <c:otherwise>
                <div class="table-responsive">
                    <table class="table table-hover mb-0">
                        <thead class="table-dark">
                            <tr>
                                <th class="text-center" width="80">Ranking</th>
                                <th>Autor</th>
                                <th class="text-center">Nacionalidad</th>
                                <th class="text-center">Libros</th>
                                <th class="text-center">Ejemplares</th>
                                <th class="text-center">Alquileres</th>
                                <th class="text-center">Promedio/Libro</th>
                                <th class="text-center">Disponibilidad</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="author" items="${topAuthors}" varStatus="status">
                                <c:set var="ranking" value="${status.index + 1}" />
                                <c:set var="badgeColor" 
                                       value="${ranking == 1 ? 'warning' : (ranking == 2 ? 'secondary' : (ranking == 3 ? 'info' : 'light text-dark'))}" />
                                
                                <tr>
                                    <td class="text-center">
                                        <span class="badge bg-${badgeColor} fs-6">#${ranking}</span>
                                    </td>
                                    <td>
                                        <div class="d-flex align-items-center">
                                            <c:choose>
                                                <c:when test="${not empty author.photoUrl}">
                                                    <img src="${author.photoUrl}" 
                                                         class="rounded-circle me-2" 
                                                         alt="${author.fullName}"
                                                         style="width: 40px; height: 40px; object-fit: cover;">
                                                </c:when>
                                                <c:otherwise>
                                                    <img src="https://ui-avatars.com/api/?name=${fn:replace(author.fullName, ' ', '+')}&size=40&background=random" 
                                                         class="rounded-circle me-2" 
                                                         alt="${author.fullName}">
                                                </c:otherwise>
                                            </c:choose>
                                            <strong>${author.fullName}</strong>
                                        </div>
                                    </td>
                                    <td class="text-center">
                                        <span class="badge bg-primary">
                                            <i class="bi bi-flag-fill"></i> ${author.countryName}
                                        </span>
                                    </td>
                                    <td class="text-center">${author.totalBooks}</td>
                                    <td class="text-center">${author.totalCopies}</td>
                                    <td class="text-center"><strong>${author.totalRentals}</strong></td>
                                    <td class="text-center">
                                        <fmt:formatNumber value="${author.avgRentalsPerBook}" maxFractionDigits="1"/>
                                    </td>
                                    <td class="text-center">
                                        <c:set var="availRate" value="${author.availabilityRate}" />
                                        <c:set var="progressColor" 
                                               value="${availRate >= 70 ? 'success' : (availRate >= 40 ? 'warning' : 'danger')}" />
                                        <div class="progress" style="height: 20px;">
                                            <div class="progress-bar bg-${progressColor}" 
                                                 role="progressbar" 
                                                 style="width: ${availRate}%">
                                                <fmt:formatNumber value="${availRate}" maxFractionDigits="0"/>%
                                            </div>
                                        </div>
                                    </td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                </div>
            </c:otherwise>
        </c:choose>
    </div>
</div>

<script>
// Inicializar tooltips
var tooltipTriggerList = [].slice.call(document.querySelectorAll('[data-bs-toggle="tooltip"]'))
var tooltipList = tooltipTriggerList.map(function (tooltipTriggerEl) {
    return new bootstrap.Tooltip(tooltipTriggerEl)
});
</script>
