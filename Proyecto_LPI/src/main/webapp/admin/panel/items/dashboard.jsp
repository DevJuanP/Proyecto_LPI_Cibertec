<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="util.DateUtil" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<div class="content-header">
    <h2 class="mb-0"><i class="bi bi-speedometer2 me-2"></i>Dashboard</h2>
    <p class="text-muted mb-0">Resumen general del sistema</p>
</div>

<!-- Statistics Cards -->
<div class="row g-4 mb-4">
    <div class="col-md-3">
        <div class="card stat-card border-start border-primary border-4">
            <div class="card-body">
                <div class="d-flex justify-content-between align-items-center">
                    <div>
                        <h6 class="text-muted mb-2">Total Libros</h6>
                        <h3 class="mb-0"><c:out value="${totalBooks}" default="0"/></h3>
                    </div>
                    <div class="bg-primary bg-opacity-10 p-3 rounded-circle">
                        <i class="bi bi-book fs-2 text-primary"></i>
                    </div>
                </div>
                <small class="text-muted">Registrados en el sistema</small>
            </div>
        </div>
    </div>
    
    <div class="col-md-3">
        <div class="card stat-card border-start border-warning border-4">
            <div class="card-body">
                <div class="d-flex justify-content-between align-items-center">
                    <div>
                        <h6 class="text-muted mb-2">En Alquiler</h6>
                        <h3 class="mb-0"><c:out value="${rentedCopies}" default="0"/></h3>
                    </div>
                    <div class="bg-warning bg-opacity-10 p-3 rounded-circle">
                        <i class="bi bi-bookmark-check fs-2 text-warning"></i>
                    </div>
                </div>
                <small class="text-muted">Ejemplares alquilados</small>
            </div>
        </div>
    </div>
    
    <div class="col-md-3">
        <div class="card stat-card border-start border-success border-4">
            <div class="card-body">
                <div class="d-flex justify-content-between align-items-center">
                    <div>
                        <h6 class="text-muted mb-2">Autores</h6>
                        <h3 class="mb-0"><c:out value="${totalAuthors}" default="0"/></h3>
                    </div>
                    <div class="bg-success bg-opacity-10 p-3 rounded-circle">
                        <i class="bi bi-people fs-2 text-success"></i>
                    </div>
                </div>
                <small class="text-muted">Registrados en el sistema</small>
            </div>
        </div>
    </div>
    
    <div class="col-md-3">
        <div class="card stat-card border-start border-danger border-4">
            <div class="card-body">
                <div class="d-flex justify-content-between align-items-center">
                    <div>
                        <h6 class="text-muted mb-2">Disponibles</h6>
                        <h3 class="mb-0"><c:out value="${availableCopies}" default="0"/></h3>
                    </div>
                    <div class="bg-danger bg-opacity-10 p-3 rounded-circle">
                        <i class="bi bi-box-seam fs-2 text-danger"></i>
                    </div>
                </div>
                <small class="text-muted">Ejemplares disponibles</small>
            </div>
        </div>
    </div>
</div>

<!-- Charts/Recent Activity -->
<div class="row g-4">
    <div class="col-md-8">
        <div class="card">
            <div class="card-header">
                <h5 class="mb-0"><i class="bi bi-bar-chart me-2"></i>Alquileres Recientes</h5>
            </div>
            <div class="card-body">
                <div class="table-responsive">
                    <table class="table table-hover">
                        <thead>
                            <tr>
                                <th>Libro</th>
                                <th>Usuario</th>
                                <th>Fecha</th>
                                <th>Estado</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:choose>
                                <c:when test="${not empty recentRentals}">
                                    <c:forEach var="rental" items="${recentRentals}">
                                        <tr>
                                            <td><strong><c:out value="${rental.bookCopy.book.title}"/></strong></td>
                                            <td><c:out value="${rental.user.email}"/></td>
                                            <td>
                                                <c:set var="currentRental" value="${rental}" scope="page"/>
                                                <% 
                                                    model.Rental r = (model.Rental) pageContext.getAttribute("currentRental");
                                                    out.print(DateUtil.formatDate(r.getRentalDate()));
                                                %>
                                            </td>
                                            <td>
                                                <c:choose>
                                                    <c:when test="${rental.rentalStatus.rentalStatusName == 'En Proceso'}">
                                                        <span class="badge bg-success">Activo</span>
                                                    </c:when>
                                                    <c:when test="${rental.rentalStatus.rentalStatusName == 'Devuelto'}">
                                                        <span class="badge bg-secondary">Devuelto</span>
                                                    </c:when>
                                                    <c:when test="${rental.rentalStatus.rentalStatusName == 'Cancelado'}">
                                                        <span class="badge bg-danger">Cancelado</span>
                                                    </c:when>
                                                    <c:otherwise>
                                                        <span class="badge bg-info"><c:out value="${rental.rentalStatus.rentalStatusName}"/></span>
                                                    </c:otherwise>
                                                </c:choose>
                                            </td>
                                        </tr>
                                    </c:forEach>
                                </c:when>
                                <c:otherwise>
                                    <tr>
                                        <td colspan="4" class="text-center text-muted">
                                            <i class="bi bi-inbox fs-3"></i>
                                            <p class="mb-0">No hay alquileres recientes</p>
                                        </td>
                                    </tr>
                                </c:otherwise>
                            </c:choose>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    </div>
    
    <div class="col-md-4">
        <div class="card">
            <div class="card-header">
                <h5 class="mb-0"><i class="bi bi-trophy me-2"></i>Más Populares</h5>
            </div>
            <div class="card-body">
                <c:choose>
                    <c:when test="${not empty topBooks}">
                        <c:forEach var="book" items="${topBooks}" varStatus="status">
                            <div class="d-flex align-items-center mb-3 p-2 bg-light rounded">
                                <div class="
                                    <c:choose>
                                        <c:when test="${status.index == 0}">bg-warning</c:when>
                                        <c:when test="${status.index == 1}">bg-secondary</c:when>
                                        <c:when test="${status.index == 2}">bg-info</c:when>
                                        <c:otherwise>bg-success</c:otherwise>
                                    </c:choose>
                                    text-white rounded-circle d-flex align-items-center justify-content-center me-3" 
                                     style="width: 40px; height: 40px;">
                                    <strong>${status.index + 1}</strong>
                                </div>
                                <div class="flex-grow-1">
                                    <h6 class="mb-0"><c:out value="${book.title}"/></h6>
                                    <small class="text-muted">${book.rentalCount} alquileres</small>
                                </div>
                            </div>
                        </c:forEach>
                    </c:when>
                    <c:otherwise>
                        <div class="text-center text-muted py-4">
                            <i class="bi bi-inbox fs-3"></i>
                            <p class="mb-0">No hay datos de alquileres</p>
                        </div>
                    </c:otherwise>
                </c:choose>
            </div>
        </div>
    </div>
</div>
