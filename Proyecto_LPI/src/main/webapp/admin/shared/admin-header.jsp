<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<nav class="navbar navbar-expand-lg navbar-dark bg-dark fixed-top shadow-sm" style="height: var(--topbar-height);">
    <div class="container-fluid">
        <!-- Sidebar Toggle Button (Mobile) -->
        <button class="btn btn-dark sidebar-toggle me-3" type="button" id="sidebarToggle">
            <i class="bi bi-list fs-4"></i>
        </button>
        
        <!-- Brand/Logo -->
        <a class="navbar-brand d-flex align-items-center" href="${pageContext.request.contextPath}/admin/panel/">
            <i class="bi bi-book-fill me-2 fs-4"></i>
            <span class="fw-bold">BiblioAdmin</span>
        </a>
        
        <!-- Navbar Items -->
        <div class="d-flex align-items-center ms-auto">
            <!-- Notifications -->
            <div class="dropdown me-3">
                <button class="btn btn-dark position-relative" type="button" id="notificationsDropdown" data-bs-toggle="dropdown">
                    <i class="bi bi-bell fs-5"></i>
                    <span class="position-absolute top-0 start-100 translate-middle badge rounded-pill bg-danger">
                        3
                    </span>
                </button>
                <ul class="dropdown-menu dropdown-menu-end" aria-labelledby="notificationsDropdown">
                    <li><h6 class="dropdown-header">Notificaciones</h6></li>
                    <li><a class="dropdown-item" href="#"><i class="bi bi-info-circle me-2"></i>Nuevo alquiler registrado</a></li>
                    <li><a class="dropdown-item" href="#"><i class="bi bi-exclamation-triangle me-2"></i>Libro próximo a vencer</a></li>
                    <li><a class="dropdown-item" href="#"><i class="bi bi-check-circle me-2"></i>Devolución completada</a></li>
                    <li><hr class="dropdown-divider"></li>
                    <li><a class="dropdown-item text-center" href="#">Ver todas</a></li>
                </ul>
            </div>
            
            <!-- User Profile -->
            <div class="dropdown">
                <button class="btn btn-dark d-flex align-items-center" type="button" id="userDropdown" data-bs-toggle="dropdown">
                    <img src="https://ui-avatars.com/api/?name=Admin&background=3498db&color=fff" 
                         class="rounded-circle me-2" 
                         width="35" 
                         height="35" 
                         alt="Admin">
                    <span class="d-none d-md-inline">Administrador</span>
                    <i class="bi bi-chevron-down ms-2"></i>
                </button>
                <ul class="dropdown-menu dropdown-menu-end" aria-labelledby="userDropdown">
                    <li><a class="dropdown-item" href="#"><i class="bi bi-person me-2"></i>Mi Perfil</a></li>
                    <li><a class="dropdown-item" href="#"><i class="bi bi-gear me-2"></i>Configuración</a></li>
                    <li><hr class="dropdown-divider"></li>
                    <li><a class="dropdown-item text-danger" href="#"><i class="bi bi-box-arrow-right me-2"></i>Cerrar Sesión</a></li>
                </ul>
            </div>
        </div>
    </div>
</nav>

<script>
    // Sidebar toggle for mobile
    document.addEventListener('DOMContentLoaded', function() {
        const sidebarToggle = document.getElementById('sidebarToggle');
        const sidebar = document.querySelector('.sidebar');
        
        if (sidebarToggle) {
            sidebarToggle.addEventListener('click', function() {
                sidebar.classList.toggle('show');
            });
        }
        
        // Close sidebar when clicking outside on mobile
        document.addEventListener('click', function(event) {
            if (window.innerWidth <= 768) {
                if (!sidebar.contains(event.target) && !sidebarToggle.contains(event.target)) {
                    sidebar.classList.remove('show');
                }
            }
        });
    });
</script>
