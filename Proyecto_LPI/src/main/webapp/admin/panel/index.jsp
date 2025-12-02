<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <title>BiblioAdmin - Panel de Administración</title>
    <jsp:include page="../shared/admin-head.jsp" />
</head>
<body>
    <!-- Top Navigation Bar -->
    <jsp:include page="../shared/admin-header.jsp" />
    
    <!-- Sidebar Navigation -->
    <div class="sidebar">
        <div class="sidebar-header">
            <h5 class="mb-0">Panel de Control</h5>
            <small class="text-white-50">Sistema de Gestión</small>
        </div>
        
        <nav class="nav flex-column py-3">
            <!-- Dashboard -->
            <a class="nav-link" href="?page=dashboard">
                <i class="bi bi-speedometer2"></i>
                <span>Dashboard</span>
            </a>
            
            <!-- Reports Section -->
            <div class="px-3 pt-3 pb-2">
                <small class="text-white-50 text-uppercase fw-bold">Reportes</small>
            </div>
            
            <a class="nav-link" href="?page=libros-alquiler">
                <i class="bi bi-bookmark-check"></i>
                <span>Libros en Alquiler</span>
            </a>
            
            <a class="nav-link" href="?page=libros-pedidos">
                <i class="bi bi-graph-up-arrow"></i>
                <span>Libros Más Pedidos</span>
            </a>
            
            <a class="nav-link" href="?page=autores-pedidos">
                <i class="bi bi-people-fill"></i>
                <span>Autores Más Pedidos</span>
            </a>
            
            <!-- Maintenance Section -->
            <div class="px-3 pt-3 pb-2">
                <small class="text-white-50 text-uppercase fw-bold">Mantenimiento</small>
            </div>
            
            <a class="nav-link" href="?page=mantenimiento-libros">
                <i class="bi bi-book"></i>
                <span>Gestión de Libros</span>
            </a>
            
            <a class="nav-link" href="?page=mantenimiento-autores">
                <i class="bi bi-person-badge"></i>
                <span>Gestión de Autores</span>
            </a>
            
            <!-- Additional Options -->
            <div class="px-3 pt-3 pb-2">
                <small class="text-white-50 text-uppercase fw-bold">Sistema</small>
            </div>
            
            <a class="nav-link" href="#">
                <i class="bi bi-people"></i>
                <span>Usuarios</span>
            </a>
            
            <a class="nav-link" href="#">
                <i class="bi bi-gear"></i>
                <span>Configuración</span>
            </a>
        </nav>
    </div>
    
    <!-- Main Content Area -->
    <div class="main-content">
        <%
            String currentPage = request.getParameter("page");
            if (currentPage == null || currentPage.isEmpty()) {
                currentPage = "dashboard";
            }
            
            String contentPage = "";
            switch (currentPage) {
                case "dashboard":
                    contentPage = "items/dashboard.jsp";
                    break;
                case "libros-alquiler":
                    contentPage = "items/libros-alquiler.jsp";
                    break;
                case "libros-pedidos":
                    contentPage = "items/libros-pedidos.jsp";
                    break;
                case "autores-pedidos":
                    contentPage = "items/autores-pedidos.jsp";
                    break;
                case "mantenimiento-libros":
                    contentPage = "items/mantenimiento-libros.jsp";
                    break;
                case "mantenimiento-autores":
                    contentPage = "items/mantenimiento-autores.jsp";
                    break;
                default:
                    contentPage = "items/dashboard.jsp";
            }
        %>
        
        <!-- Include the selected partial view -->
        <jsp:include page="<%= contentPage %>" />
    </div>
    
    <!-- Footer -->
    <jsp:include page="../shared/admin-footer.jsp" />
</body>
</html>
