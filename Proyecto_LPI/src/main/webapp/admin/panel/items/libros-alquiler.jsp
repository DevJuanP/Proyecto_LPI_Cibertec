<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div class="content-header">
    <div class="d-flex justify-content-between align-items-center">
        <div>
            <h2 class="mb-0"><i class="bi bi-bookmark-check me-2"></i>Libros en Alquiler</h2>
            <p class="text-muted mb-0">Reporte de libros actualmente alquilados</p>
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

<!-- Summary Cards -->
<div class="row g-3 mb-4">
    <div class="col-md-3">
        <div class="card bg-primary text-white">
            <div class="card-body">
                <h6>Total Alquilados</h6>
                <h3 class="mb-0">342</h3>
            </div>
        </div>
    </div>
    <div class="col-md-3">
        <div class="card bg-success text-white">
            <div class="card-body">
                <h6>Al Día</h6>
                <h3 class="mb-0">298</h3>
            </div>
        </div>
    </div>
    <div class="col-md-3">
        <div class="card bg-warning text-white">
            <div class="card-body">
                <h6>Por Vencer</h6>
                <h3 class="mb-0">32</h3>
            </div>
        </div>
    </div>
    <div class="col-md-3">
        <div class="card bg-danger text-white">
            <div class="card-body">
                <h6>Vencidos</h6>
                <h3 class="mb-0">12</h3>
            </div>
        </div>
    </div>
</div>

<!-- Filters -->
<div class="card mb-4">
    <div class="card-body">
        <div class="row g-3">
            <div class="col-md-3">
                <label class="form-label">Buscar</label>
                <input type="text" class="form-control" placeholder="Libro, usuario o código...">
            </div>
            <div class="col-md-3">
                <label class="form-label">Estado</label>
                <select class="form-select">
                    <option value="">Todos</option>
                    <option value="activo">Activo</option>
                    <option value="vencer">Por vencer</option>
                    <option value="vencido">Vencido</option>
                </select>
            </div>
            <div class="col-md-3">
                <label class="form-label">Fecha Desde</label>
                <input type="date" class="form-control">
            </div>
            <div class="col-md-3">
                <label class="form-label">Fecha Hasta</label>
                <input type="date" class="form-control">
            </div>
        </div>
        <div class="mt-3">
            <button class="btn btn-primary"><i class="bi bi-search"></i> Buscar</button>
            <button class="btn btn-secondary"><i class="bi bi-x-circle"></i> Limpiar</button>
        </div>
    </div>
</div>

<!-- Report Table -->
<div class="card">
    <div class="card-header">
        <h5 class="mb-0">Listado de Alquileres Activos</h5>
    </div>
    <div class="card-body">
        <div class="table-responsive">
            <table class="table table-hover table-striped">
                <thead class="table-dark">
                    <tr>
                        <th>Código</th>
                        <th>Libro</th>
                        <th>Autor</th>
                        <th>Usuario</th>
                        <th>Fecha Alquiler</th>
                        <th>Fecha Vencimiento</th>
                        <th>Días Restantes</th>
                        <th>Estado</th>
                        <th>Acciones</th>
                    </tr>
                </thead>
                <tbody>
                    <tr>
                        <td><code>ALQ-001</code></td>
                        <td><strong>Cien Años de Soledad</strong></td>
                        <td>Gabriel García Márquez</td>
                        <td>Juan Pérez</td>
                        <td>02/12/2025</td>
                        <td>16/12/2025</td>
                        <td><span class="badge bg-success">14 días</span></td>
                        <td><span class="badge bg-success">Activo</span></td>
                        <td>
                            <button class="btn btn-sm btn-info" data-bs-toggle="tooltip" title="Ver detalles">
                                <i class="bi bi-eye"></i>
                            </button>
                        </td>
                    </tr>
                    <tr>
                        <td><code>ALQ-002</code></td>
                        <td><strong>El Quijote</strong></td>
                        <td>Miguel de Cervantes</td>
                        <td>María García</td>
                        <td>01/12/2025</td>
                        <td>15/12/2025</td>
                        <td><span class="badge bg-success">13 días</span></td>
                        <td><span class="badge bg-success">Activo</span></td>
                        <td>
                            <button class="btn btn-sm btn-info" data-bs-toggle="tooltip" title="Ver detalles">
                                <i class="bi bi-eye"></i>
                            </button>
                        </td>
                    </tr>
                    <tr>
                        <td><code>ALQ-003</code></td>
                        <td><strong>La Odisea</strong></td>
                        <td>Homero</td>
                        <td>Carlos López</td>
                        <td>30/11/2025</td>
                        <td>05/12/2025</td>
                        <td><span class="badge bg-warning">3 días</span></td>
                        <td><span class="badge bg-warning">Por vencer</span></td>
                        <td>
                            <button class="btn btn-sm btn-info" data-bs-toggle="tooltip" title="Ver detalles">
                                <i class="bi bi-eye"></i>
                            </button>
                        </td>
                    </tr>
                    <tr>
                        <td><code>ALQ-004</code></td>
                        <td><strong>1984</strong></td>
                        <td>George Orwell</td>
                        <td>Ana Martínez</td>
                        <td>15/11/2025</td>
                        <td>29/11/2025</td>
                        <td><span class="badge bg-danger">-3 días</span></td>
                        <td><span class="badge bg-danger">Vencido</span></td>
                        <td>
                            <button class="btn btn-sm btn-info" data-bs-toggle="tooltip" title="Ver detalles">
                                <i class="bi bi-eye"></i>
                            </button>
                        </td>
                    </tr>
                    <tr>
                        <td><code>ALQ-005</code></td>
                        <td><strong>El Principito</strong></td>
                        <td>Antoine de Saint-Exupéry</td>
                        <td>Pedro Sánchez</td>
                        <td>27/11/2025</td>
                        <td>11/12/2025</td>
                        <td><span class="badge bg-success">9 días</span></td>
                        <td><span class="badge bg-success">Activo</span></td>
                        <td>
                            <button class="btn btn-sm btn-info" data-bs-toggle="tooltip" title="Ver detalles">
                                <i class="bi bi-eye"></i>
                            </button>
                        </td>
                    </tr>
                    <tr>
                        <td><code>ALQ-006</code></td>
                        <td><strong>Rayuela</strong></td>
                        <td>Julio Cortázar</td>
                        <td>Laura Fernández</td>
                        <td>25/11/2025</td>
                        <td>09/12/2025</td>
                        <td><span class="badge bg-success">7 días</span></td>
                        <td><span class="badge bg-success">Activo</span></td>
                        <td>
                            <button class="btn btn-sm btn-info" data-bs-toggle="tooltip" title="Ver detalles">
                                <i class="bi bi-eye"></i>
                            </button>
                        </td>
                    </tr>
                    <tr>
                        <td><code>ALQ-007</code></td>
                        <td><strong>La Casa de los Espíritus</strong></td>
                        <td>Isabel Allende</td>
                        <td>Roberto Torres</td>
                        <td>20/11/2025</td>
                        <td>04/12/2025</td>
                        <td><span class="badge bg-warning">2 días</span></td>
                        <td><span class="badge bg-warning">Por vencer</span></td>
                        <td>
                            <button class="btn btn-sm btn-info" data-bs-toggle="tooltip" title="Ver detalles">
                                <i class="bi bi-eye"></i>
                            </button>
                        </td>
                    </tr>
                    <tr>
                        <td><code>ALQ-008</code></td>
                        <td><strong>Ficciones</strong></td>
                        <td>Jorge Luis Borges</td>
                        <td>Sofía Ramírez</td>
                        <td>18/11/2025</td>
                        <td>02/12/2025</td>
                        <td><span class="badge bg-success">Hoy</span></td>
                        <td><span class="badge bg-success">Activo</span></td>
                        <td>
                            <button class="btn btn-sm btn-info" data-bs-toggle="tooltip" title="Ver detalles">
                                <i class="bi bi-eye"></i>
                            </button>
                        </td>
                    </tr>
                </tbody>
            </table>
        </div>
        
        <!-- Pagination -->
        <nav aria-label="Page navigation" class="mt-4">
            <ul class="pagination justify-content-center">
                <li class="page-item disabled">
                    <a class="page-link" href="#" tabindex="-1">Anterior</a>
                </li>
                <li class="page-item active"><a class="page-link" href="#">1</a></li>
                <li class="page-item"><a class="page-link" href="#">2</a></li>
                <li class="page-item"><a class="page-link" href="#">3</a></li>
                <li class="page-item"><a class="page-link" href="#">4</a></li>
                <li class="page-item"><a class="page-link" href="#">5</a></li>
                <li class="page-item">
                    <a class="page-link" href="#">Siguiente</a>
                </li>
            </ul>
        </nav>
    </div>
</div>
