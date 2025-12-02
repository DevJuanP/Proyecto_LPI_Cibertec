<%@ page contentType="text/html;charset=UTF-8" language="java" %>
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
                        <h3 class="mb-0">1,248</h3>
                    </div>
                    <div class="bg-primary bg-opacity-10 p-3 rounded-circle">
                        <i class="bi bi-book fs-2 text-primary"></i>
                    </div>
                </div>
                <small class="text-success"><i class="bi bi-arrow-up"></i> 12% este mes</small>
            </div>
        </div>
    </div>
    
    <div class="col-md-3">
        <div class="card stat-card border-start border-warning border-4">
            <div class="card-body">
                <div class="d-flex justify-content-between align-items-center">
                    <div>
                        <h6 class="text-muted mb-2">En Alquiler</h6>
                        <h3 class="mb-0">342</h3>
                    </div>
                    <div class="bg-warning bg-opacity-10 p-3 rounded-circle">
                        <i class="bi bi-bookmark-check fs-2 text-warning"></i>
                    </div>
                </div>
                <small class="text-info"><i class="bi bi-arrow-right"></i> 85 hoy</small>
            </div>
        </div>
    </div>
    
    <div class="col-md-3">
        <div class="card stat-card border-start border-success border-4">
            <div class="card-body">
                <div class="d-flex justify-content-between align-items-center">
                    <div>
                        <h6 class="text-muted mb-2">Autores</h6>
                        <h3 class="mb-0">456</h3>
                    </div>
                    <div class="bg-success bg-opacity-10 p-3 rounded-circle">
                        <i class="bi bi-people fs-2 text-success"></i>
                    </div>
                </div>
                <small class="text-success"><i class="bi bi-arrow-up"></i> 8 nuevos</small>
            </div>
        </div>
    </div>
    
    <div class="col-md-3">
        <div class="card stat-card border-start border-danger border-4">
            <div class="card-body">
                <div class="d-flex justify-content-between align-items-center">
                    <div>
                        <h6 class="text-muted mb-2">Disponibles</h6>
                        <h3 class="mb-0">906</h3>
                    </div>
                    <div class="bg-danger bg-opacity-10 p-3 rounded-circle">
                        <i class="bi bi-box-seam fs-2 text-danger"></i>
                    </div>
                </div>
                <small class="text-muted"><i class="bi bi-dash"></i> Sin cambios</small>
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
                            <tr>
                                <td><strong>Cien Años de Soledad</strong></td>
                                <td>Juan Pérez</td>
                                <td>02/12/2025</td>
                                <td><span class="badge bg-success">Activo</span></td>
                            </tr>
                            <tr>
                                <td><strong>El Quijote</strong></td>
                                <td>María García</td>
                                <td>01/12/2025</td>
                                <td><span class="badge bg-success">Activo</span></td>
                            </tr>
                            <tr>
                                <td><strong>La Odisea</strong></td>
                                <td>Carlos López</td>
                                <td>30/11/2025</td>
                                <td><span class="badge bg-warning">Por vencer</span></td>
                            </tr>
                            <tr>
                                <td><strong>1984</strong></td>
                                <td>Ana Martínez</td>
                                <td>28/11/2025</td>
                                <td><span class="badge bg-danger">Vencido</span></td>
                            </tr>
                            <tr>
                                <td><strong>El Principito</strong></td>
                                <td>Pedro Sánchez</td>
                                <td>27/11/2025</td>
                                <td><span class="badge bg-success">Activo</span></td>
                            </tr>
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
                <div class="d-flex align-items-center mb-3 p-2 bg-light rounded">
                    <div class="bg-warning text-white rounded-circle d-flex align-items-center justify-content-center me-3" 
                         style="width: 40px; height: 40px;">
                        <strong>1</strong>
                    </div>
                    <div>
                        <h6 class="mb-0">Cien Años de Soledad</h6>
                        <small class="text-muted">156 alquileres</small>
                    </div>
                </div>
                
                <div class="d-flex align-items-center mb-3 p-2 bg-light rounded">
                    <div class="bg-secondary text-white rounded-circle d-flex align-items-center justify-content-center me-3" 
                         style="width: 40px; height: 40px;">
                        <strong>2</strong>
                    </div>
                    <div>
                        <h6 class="mb-0">El Quijote</h6>
                        <small class="text-muted">142 alquileres</small>
                    </div>
                </div>
                
                <div class="d-flex align-items-center mb-3 p-2 bg-light rounded">
                    <div class="bg-info text-white rounded-circle d-flex align-items-center justify-content-center me-3" 
                         style="width: 40px; height: 40px;">
                        <strong>3</strong>
                    </div>
                    <div>
                        <h6 class="mb-0">1984</h6>
                        <small class="text-muted">138 alquileres</small>
                    </div>
                </div>
                
                <div class="d-flex align-items-center p-2 bg-light rounded">
                    <div class="bg-success text-white rounded-circle d-flex align-items-center justify-content-center me-3" 
                         style="width: 40px; height: 40px;">
                        <strong>4</strong>
                    </div>
                    <div>
                        <h6 class="mb-0">La Odisea</h6>
                        <small class="text-muted">125 alquileres</small>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
