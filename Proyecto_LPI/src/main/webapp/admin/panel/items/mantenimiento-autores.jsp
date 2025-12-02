<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div class="content-header">
    <div class="d-flex justify-content-between align-items-center">
        <div>
            <h2 class="mb-0"><i class="bi bi-person-badge me-2"></i>Mantenimiento de Autores</h2>
            <p class="text-muted mb-0">Gestión completa de autores</p>
        </div>
        <div>
            <button class="btn btn-success" data-bs-toggle="modal" data-bs-target="#addAuthorModal">
                <i class="bi bi-plus-circle"></i> Nuevo Autor
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
                        <small class="text-muted">Total Autores</small>
                        <h4 class="mb-0">456</h4>
                    </div>
                    <i class="bi bi-people-fill fs-2 text-primary"></i>
                </div>
            </div>
        </div>
    </div>
    <div class="col-md-3">
        <div class="card border-start border-success border-4">
            <div class="card-body">
                <div class="d-flex justify-content-between">
                    <div>
                        <small class="text-muted">Con Obras</small>
                        <h4 class="mb-0">342</h4>
                    </div>
                    <i class="bi bi-check-circle-fill fs-2 text-success"></i>
                </div>
            </div>
        </div>
    </div>
    <div class="col-md-3">
        <div class="card border-start border-info border-4">
            <div class="card-body">
                <div class="d-flex justify-content-between">
                    <div>
                        <small class="text-muted">Nacionales</small>
                        <h4 class="mb-0">128</h4>
                    </div>
                    <i class="bi bi-flag-fill fs-2 text-info"></i>
                </div>
            </div>
        </div>
    </div>
    <div class="col-md-3">
        <div class="card border-start border-warning border-4">
            <div class="card-body">
                <div class="d-flex justify-content-between">
                    <div>
                        <small class="text-muted">Más Solicitado</small>
                        <h4 class="mb-0">G. García M.</h4>
                    </div>
                    <i class="bi bi-trophy-fill fs-2 text-warning"></i>
                </div>
            </div>
        </div>
    </div>
</div>

<!-- Search and Filters -->
<div class="card mb-4">
    <div class="card-body">
        <div class="row g-3">
            <div class="col-md-4">
                <label class="form-label">Buscar</label>
                <div class="input-group">
                    <span class="input-group-text"><i class="bi bi-search"></i></span>
                    <input type="text" class="form-control" placeholder="Nombre del autor...">
                </div>
            </div>
            <div class="col-md-3">
                <label class="form-label">Nacionalidad</label>
                <select class="form-select">
                    <option value="">Todas</option>
                    <option value="colombia">Colombia</option>
                    <option value="espana">España</option>
                    <option value="argentina">Argentina</option>
                    <option value="mexico">México</option>
                    <option value="chile">Chile</option>
                    <option value="otros">Otros</option>
                </select>
            </div>
            <div class="col-md-3">
                <label class="form-label">Estado</label>
                <select class="form-select">
                    <option value="">Todos</option>
                    <option value="activo">Activo</option>
                    <option value="inactivo">Inactivo</option>
                </select>
            </div>
            <div class="col-md-2">
                <label class="form-label">&nbsp;</label>
                <div class="d-grid gap-2">
                    <button class="btn btn-primary">
                        <i class="bi bi-funnel"></i> Filtrar
                    </button>
                </div>
            </div>
        </div>
    </div>
</div>

<!-- Authors Grid View -->
<div class="row g-4 mb-4">
    <div class="col-md-6 col-lg-4">
        <div class="card h-100 shadow-sm">
            <div class="card-body">
                <div class="d-flex align-items-start">
                    <img src="https://ui-avatars.com/api/?name=Gabriel+Garcia+Marquez&size=80&background=3498db&color=fff" 
                         class="rounded-circle me-3" alt="GGM">
                    <div class="flex-grow-1">
                        <h5 class="mb-1">Gabriel García Márquez</h5>
                        <p class="text-muted small mb-2">
                            <i class="bi bi-flag-fill"></i> Colombia
                        </p>
                        <div class="mb-2">
                            <span class="badge bg-primary">Ficción</span>
                            <span class="badge bg-info">Realismo Mágico</span>
                        </div>
                    </div>
                </div>
                <hr>
                <div class="row text-center g-2 mb-3">
                    <div class="col-4">
                        <small class="text-muted d-block">Obras</small>
                        <strong>8</strong>
                    </div>
                    <div class="col-4">
                        <small class="text-muted d-block">Ejemplares</small>
                        <strong>24</strong>
                    </div>
                    <div class="col-4">
                        <small class="text-muted d-block">Alquileres</small>
                        <strong>254</strong>
                    </div>
                </div>
                <div class="d-flex gap-2">
                    <button class="btn btn-sm btn-info flex-fill" data-bs-toggle="tooltip" title="Ver detalles">
                        <i class="bi bi-eye"></i> Ver
                    </button>
                    <button class="btn btn-sm btn-warning" data-bs-toggle="tooltip" title="Editar">
                        <i class="bi bi-pencil"></i>
                    </button>
                    <button class="btn btn-sm btn-danger" data-bs-toggle="tooltip" title="Eliminar">
                        <i class="bi bi-trash"></i>
                    </button>
                </div>
            </div>
        </div>
    </div>
    
    <div class="col-md-6 col-lg-4">
        <div class="card h-100 shadow-sm">
            <div class="card-body">
                <div class="d-flex align-items-start">
                    <img src="https://ui-avatars.com/api/?name=Miguel+Cervantes&size=80&background=e74c3c&color=fff" 
                         class="rounded-circle me-3" alt="MC">
                    <div class="flex-grow-1">
                        <h5 class="mb-1">Miguel de Cervantes</h5>
                        <p class="text-muted small mb-2">
                            <i class="bi bi-flag-fill"></i> España
                        </p>
                        <div class="mb-2">
                            <span class="badge bg-primary">Ficción</span>
                            <span class="badge bg-warning">Clásico</span>
                        </div>
                    </div>
                </div>
                <hr>
                <div class="row text-center g-2 mb-3">
                    <div class="col-4">
                        <small class="text-muted d-block">Obras</small>
                        <strong>5</strong>
                    </div>
                    <div class="col-4">
                        <small class="text-muted d-block">Ejemplares</small>
                        <strong>18</strong>
                    </div>
                    <div class="col-4">
                        <small class="text-muted d-block">Alquileres</small>
                        <strong>186</strong>
                    </div>
                </div>
                <div class="d-flex gap-2">
                    <button class="btn btn-sm btn-info flex-fill" data-bs-toggle="tooltip" title="Ver detalles">
                        <i class="bi bi-eye"></i> Ver
                    </button>
                    <button class="btn btn-sm btn-warning" data-bs-toggle="tooltip" title="Editar">
                        <i class="bi bi-pencil"></i>
                    </button>
                    <button class="btn btn-sm btn-danger" data-bs-toggle="tooltip" title="Eliminar">
                        <i class="bi bi-trash"></i>
                    </button>
                </div>
            </div>
        </div>
    </div>
    
    <div class="col-md-6 col-lg-4">
        <div class="card h-100 shadow-sm">
            <div class="card-body">
                <div class="d-flex align-items-start">
                    <img src="https://ui-avatars.com/api/?name=Jorge+Luis+Borges&size=80&background=9b59b6&color=fff" 
                         class="rounded-circle me-3" alt="JLB">
                    <div class="flex-grow-1">
                        <h5 class="mb-1">Jorge Luis Borges</h5>
                        <p class="text-muted small mb-2">
                            <i class="bi bi-flag-fill"></i> Argentina
                        </p>
                        <div class="mb-2">
                            <span class="badge bg-primary">Ficción</span>
                            <span class="badge bg-secondary">Cuentos</span>
                        </div>
                    </div>
                </div>
                <hr>
                <div class="row text-center g-2 mb-3">
                    <div class="col-4">
                        <small class="text-muted d-block">Obras</small>
                        <strong>12</strong>
                    </div>
                    <div class="col-4">
                        <small class="text-muted d-block">Ejemplares</small>
                        <strong>35</strong>
                    </div>
                    <div class="col-4">
                        <small class="text-muted d-block">Alquileres</small>
                        <strong>172</strong>
                    </div>
                </div>
                <div class="d-flex gap-2">
                    <button class="btn btn-sm btn-info flex-fill" data-bs-toggle="tooltip" title="Ver detalles">
                        <i class="bi bi-eye"></i> Ver
                    </button>
                    <button class="btn btn-sm btn-warning" data-bs-toggle="tooltip" title="Editar">
                        <i class="bi bi-pencil"></i>
                    </button>
                    <button class="btn btn-sm btn-danger" data-bs-toggle="tooltip" title="Eliminar">
                        <i class="bi bi-trash"></i>
                    </button>
                </div>
            </div>
        </div>
    </div>
</div>

<!-- Authors Table -->
<div class="card">
    <div class="card-header d-flex justify-content-between align-items-center">
        <h5 class="mb-0">Listado de Autores</h5>
        <div>
            <button class="btn btn-sm btn-outline-secondary active" data-bs-toggle="tooltip" title="Vista de cuadrícula">
                <i class="bi bi-grid-3x3"></i>
            </button>
            <button class="btn btn-sm btn-outline-secondary" data-bs-toggle="tooltip" title="Vista de tabla">
                <i class="bi bi-list-ul"></i>
            </button>
        </div>
    </div>
    <div class="card-body">
        <div class="table-responsive">
            <table class="table table-hover">
                <thead class="table-dark">
                    <tr>
                        <th style="width: 50px;">
                            <input type="checkbox" class="form-check-input">
                        </th>
                        <th>ID</th>
                        <th>Autor</th>
                        <th>Nacionalidad</th>
                        <th>Género Literario</th>
                        <th>Obras</th>
                        <th>Ejemplares</th>
                        <th>Total Alquileres</th>
                        <th>Estado</th>
                        <th style="width: 150px;">Acciones</th>
                    </tr>
                </thead>
                <tbody>
                    <tr>
                        <td><input type="checkbox" class="form-check-input"></td>
                        <td><code>AUT-001</code></td>
                        <td>
                            <div class="d-flex align-items-center">
                                <img src="https://ui-avatars.com/api/?name=Gabriel+Garcia+Marquez&size=35&background=3498db&color=fff" 
                                     class="rounded-circle me-2" alt="GGM">
                                <strong>Gabriel García Márquez</strong>
                            </div>
                        </td>
                        <td><i class="bi bi-flag-fill text-warning"></i> Colombia</td>
                        <td><span class="badge bg-primary">Ficción</span></td>
                        <td class="text-center">8</td>
                        <td class="text-center">24</td>
                        <td class="text-center"><strong>254</strong></td>
                        <td><span class="badge bg-success">Activo</span></td>
                        <td>
                            <div class="btn-group btn-group-sm">
                                <button class="btn btn-info" data-bs-toggle="tooltip" title="Ver detalles">
                                    <i class="bi bi-eye"></i>
                                </button>
                                <button class="btn btn-warning" data-bs-toggle="tooltip" title="Editar">
                                    <i class="bi bi-pencil"></i>
                                </button>
                                <button class="btn btn-danger" data-bs-toggle="tooltip" title="Eliminar">
                                    <i class="bi bi-trash"></i>
                                </button>
                            </div>
                        </td>
                    </tr>
                    <tr>
                        <td><input type="checkbox" class="form-check-input"></td>
                        <td><code>AUT-002</code></td>
                        <td>
                            <div class="d-flex align-items-center">
                                <img src="https://ui-avatars.com/api/?name=Miguel+Cervantes&size=35&background=e74c3c&color=fff" 
                                     class="rounded-circle me-2" alt="MC">
                                <strong>Miguel de Cervantes</strong>
                            </div>
                        </td>
                        <td><i class="bi bi-flag-fill text-danger"></i> España</td>
                        <td><span class="badge bg-primary">Ficción</span></td>
                        <td class="text-center">5</td>
                        <td class="text-center">18</td>
                        <td class="text-center"><strong>186</strong></td>
                        <td><span class="badge bg-success">Activo</span></td>
                        <td>
                            <div class="btn-group btn-group-sm">
                                <button class="btn btn-info" data-bs-toggle="tooltip" title="Ver detalles">
                                    <i class="bi bi-eye"></i>
                                </button>
                                <button class="btn btn-warning" data-bs-toggle="tooltip" title="Editar">
                                    <i class="bi bi-pencil"></i>
                                </button>
                                <button class="btn btn-danger" data-bs-toggle="tooltip" title="Eliminar">
                                    <i class="bi bi-trash"></i>
                                </button>
                            </div>
                        </td>
                    </tr>
                    <tr>
                        <td><input type="checkbox" class="form-check-input"></td>
                        <td><code>AUT-003</code></td>
                        <td>
                            <div class="d-flex align-items-center">
                                <img src="https://ui-avatars.com/api/?name=Jorge+Luis+Borges&size=35&background=9b59b6&color=fff" 
                                     class="rounded-circle me-2" alt="JLB">
                                <strong>Jorge Luis Borges</strong>
                            </div>
                        </td>
                        <td><i class="bi bi-flag-fill text-info"></i> Argentina</td>
                        <td><span class="badge bg-primary">Ficción</span></td>
                        <td class="text-center">12</td>
                        <td class="text-center">35</td>
                        <td class="text-center"><strong>172</strong></td>
                        <td><span class="badge bg-success">Activo</span></td>
                        <td>
                            <div class="btn-group btn-group-sm">
                                <button class="btn btn-info" data-bs-toggle="tooltip" title="Ver detalles">
                                    <i class="bi bi-eye"></i>
                                </button>
                                <button class="btn btn-warning" data-bs-toggle="tooltip" title="Editar">
                                    <i class="bi bi-pencil"></i>
                                </button>
                                <button class="btn btn-danger" data-bs-toggle="tooltip" title="Eliminar">
                                    <i class="bi bi-trash"></i>
                                </button>
                            </div>
                        </td>
                    </tr>
                    <tr>
                        <td><input type="checkbox" class="form-check-input"></td>
                        <td><code>AUT-004</code></td>
                        <td>
                            <div class="d-flex align-items-center">
                                <img src="https://ui-avatars.com/api/?name=Isabel+Allende&size=35&background=e67e22&color=fff" 
                                     class="rounded-circle me-2" alt="IA">
                                <strong>Isabel Allende</strong>
                            </div>
                        </td>
                        <td><i class="bi bi-flag-fill text-primary"></i> Chile</td>
                        <td><span class="badge bg-primary">Ficción</span></td>
                        <td class="text-center">7</td>
                        <td class="text-center">22</td>
                        <td class="text-center"><strong>165</strong></td>
                        <td><span class="badge bg-success">Activo</span></td>
                        <td>
                            <div class="btn-group btn-group-sm">
                                <button class="btn btn-info" data-bs-toggle="tooltip" title="Ver detalles">
                                    <i class="bi bi-eye"></i>
                                </button>
                                <button class="btn btn-warning" data-bs-toggle="tooltip" title="Editar">
                                    <i class="bi bi-pencil"></i>
                                </button>
                                <button class="btn btn-danger" data-bs-toggle="tooltip" title="Eliminar">
                                    <i class="bi bi-trash"></i>
                                </button>
                            </div>
                        </td>
                    </tr>
                    <tr>
                        <td><input type="checkbox" class="form-check-input"></td>
                        <td><code>AUT-005</code></td>
                        <td>
                            <div class="d-flex align-items-center">
                                <img src="https://ui-avatars.com/api/?name=Julio+Cortazar&size=35&background=1abc9c&color=fff" 
                                     class="rounded-circle me-2" alt="JC">
                                <strong>Julio Cortázar</strong>
                            </div>
                        </td>
                        <td><i class="bi bi-flag-fill text-info"></i> Argentina</td>
                        <td><span class="badge bg-primary">Ficción</span></td>
                        <td class="text-center">9</td>
                        <td class="text-center">28</td>
                        <td class="text-center"><strong>158</strong></td>
                        <td><span class="badge bg-success">Activo</span></td>
                        <td>
                            <div class="btn-group btn-group-sm">
                                <button class="btn btn-info" data-bs-toggle="tooltip" title="Ver detalles">
                                    <i class="bi bi-eye"></i>
                                </button>
                                <button class="btn btn-warning" data-bs-toggle="tooltip" title="Editar">
                                    <i class="bi bi-pencil"></i>
                                </button>
                                <button class="btn btn-danger" data-bs-toggle="tooltip" title="Eliminar">
                                    <i class="bi bi-trash"></i>
                                </button>
                            </div>
                        </td>
                    </tr>
                </tbody>
            </table>
        </div>
        
        <!-- Pagination -->
        <nav aria-label="Page navigation" class="mt-4">
            <ul class="pagination justify-content-center">
                <li class="page-item disabled">
                    <a class="page-link">Anterior</a>
                </li>
                <li class="page-item active"><a class="page-link" href="#">1</a></li>
                <li class="page-item"><a class="page-link" href="#">2</a></li>
                <li class="page-item"><a class="page-link" href="#">3</a></li>
                <li class="page-item"><a class="page-link" href="#">...</a></li>
                <li class="page-item"><a class="page-link" href="#">10</a></li>
                <li class="page-item">
                    <a class="page-link" href="#">Siguiente</a>
                </li>
            </ul>
        </nav>
    </div>
</div>

<!-- Add Author Modal -->
<div class="modal fade" id="addAuthorModal" tabindex="-1" aria-labelledby="addAuthorModalLabel" aria-hidden="true">
    <div class="modal-dialog modal-lg">
        <div class="modal-content">
            <div class="modal-header bg-primary text-white">
                <h5 class="modal-title" id="addAuthorModalLabel">
                    <i class="bi bi-plus-circle me-2"></i>Agregar Nuevo Autor
                </h5>
                <button type="button" class="btn-close btn-close-white" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <div class="modal-body">
                <form>
                    <div class="row g-3">
                        <div class="col-md-6">
                            <label class="form-label">Nombre Completo *</label>
                            <input type="text" class="form-control" placeholder="Nombre del autor" required>
                        </div>
                        <div class="col-md-6">
                            <label class="form-label">Seudónimo</label>
                            <input type="text" class="form-control" placeholder="Si tiene seudónimo">
                        </div>
                        <div class="col-md-4">
                            <label class="form-label">Nacionalidad *</label>
                            <select class="form-select" required>
                                <option value="">Seleccionar...</option>
                                <option>Colombia</option>
                                <option>España</option>
                                <option>Argentina</option>
                                <option>México</option>
                                <option>Chile</option>
                                <option>Perú</option>
                                <option>Otros</option>
                            </select>
                        </div>
                        <div class="col-md-4">
                            <label class="form-label">Fecha de Nacimiento</label>
                            <input type="date" class="form-control">
                        </div>
                        <div class="col-md-4">
                            <label class="form-label">Fecha de Fallecimiento</label>
                            <input type="date" class="form-control">
                        </div>
                        <div class="col-md-6">
                            <label class="form-label">Género Literario Principal</label>
                            <select class="form-select">
                                <option value="">Seleccionar...</option>
                                <option>Ficción</option>
                                <option>No ficción</option>
                                <option>Poesía</option>
                                <option>Teatro</option>
                                <option>Ensayo</option>
                            </select>
                        </div>
                        <div class="col-md-6">
                            <label class="form-label">Especialidad</label>
                            <input type="text" class="form-control" placeholder="Ej: Realismo mágico, Ciencia ficción">
                        </div>
                        <div class="col-12">
                            <label class="form-label">Biografía</label>
                            <textarea class="form-control" rows="4" placeholder="Breve biografía del autor..."></textarea>
                        </div>
                        <div class="col-md-6">
                            <label class="form-label">Sitio Web</label>
                            <input type="url" class="form-control" placeholder="https://...">
                        </div>
                        <div class="col-md-6">
                            <label class="form-label">Email de Contacto</label>
                            <input type="email" class="form-control" placeholder="autor@example.com">
                        </div>
                        <div class="col-12">
                            <label class="form-label">Foto del Autor</label>
                            <input type="file" class="form-control" accept="image/*">
                        </div>
                        <div class="col-12">
                            <div class="form-check">
                                <input class="form-check-input" type="checkbox" id="activeAuthor" checked>
                                <label class="form-check-label" for="activeAuthor">
                                    Autor activo
                                </label>
                            </div>
                        </div>
                    </div>
                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">
                    <i class="bi bi-x-circle"></i> Cancelar
                </button>
                <button type="button" class="btn btn-primary">
                    <i class="bi bi-save"></i> Guardar Autor
                </button>
            </div>
        </div>
    </div>
</div>
