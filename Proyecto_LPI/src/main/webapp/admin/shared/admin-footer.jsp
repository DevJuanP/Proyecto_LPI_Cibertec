<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<footer class="mt-5 py-3 text-center text-muted">
    <div class="container">
        <hr class="mb-3">
        <p class="mb-0">&copy; <%= new java.util.Date().getYear() + 1900 %> BiblioAdmin - Sistema de Gestión Bibliotecaria</p>
        <p class="small mb-0">Desarrollado con <i class="bi bi-heart-fill text-danger"></i> para bibliotecas modernas</p>
    </div>
</footer>

<!-- Bootstrap 5 JS Bundle -->
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>

<script>
    // Initialize Bootstrap tooltips
    document.addEventListener('DOMContentLoaded', function() {
        var tooltipTriggerList = [].slice.call(document.querySelectorAll('[data-bs-toggle="tooltip"]'));
        var tooltipList = tooltipTriggerList.map(function(tooltipTriggerEl) {
            return new bootstrap.Tooltip(tooltipTriggerEl);
        });
    });
    
    // Active navigation link highlight
    document.addEventListener('DOMContentLoaded', function() {
        const currentPage = window.location.search.split('page=')[1] || 'dashboard';
        const navLinks = document.querySelectorAll('.sidebar .nav-link');
        
        navLinks.forEach(link => {
            const linkPage = link.getAttribute('href').split('page=')[1];
            if (linkPage === currentPage) {
                link.classList.add('active');
            } else {
                link.classList.remove('active');
            }
        });
    });
</script>
