<%@ page contentType="text/html;charset=UTF-8" language="java" %>
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
                        <h4 class="mb-0">1,248</h4>
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
                        <h4 class="mb-0">906</h4>
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
                        <h4 class="mb-0">342</h4>
                    </div>
                    <i class="bi bi-arrow-repeat fs-2 text-warning"></i>
                </div>
            </div>
        </div>
    </div>
    <div class="col-md-3">
        <div class="card border-start border-danger border-4">
            <div class="card-body">
                <div class="d-flex justify-content-between">
                    <div>
                        <small class="text-muted">Baja/Mantenimiento</small>
                        <h4 class="mb-0">18</h4>
                    </div>
                    <i class="bi bi-exclamation-triangle-fill fs-2 text-danger"></i>
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
                    <input type="text" class="form-control" placeholder="Título, ISBN, autor...">
                </div>
            </div>
            <div class="col-md-2">
                <label class="form-label">Categoría</label>
                <select class="form-select">
                    <option value="">Todas</option>
                    <option value="ficcion">Ficción</option>
                    <option value="no-ficcion">No ficción</option>
                    <option value="tecnico">Técnico</option>
                    <option value="academico">Académico</option>
                    <option value="infantil">Infantil</option>
                </select>
            </div>
            <div class="col-md-2">
                <label class="form-label">Estado</label>
                <select class="form-select">
                    <option value="">Todos</option>
                    <option value="disponible">Disponible</option>
                    <option value="alquilado">Alquilado</option>
                    <option value="mantenimiento">Mantenimiento</option>
                </select>
            </div>
            <div class="col-md-2">
                <label class="form-label">Editorial</label>
                <select class="form-select">
                    <option value="">Todas</option>
                    <option value="planeta">Planeta</option>
                    <option value="santillana">Santillana</option>
                    <option value="alfaguara">Alfaguara</option>
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

<!-- Books Table -->
<div class="card">
    <div class="card-header d-flex justify-content-between align-items-center">
        <h5 class="mb-0">Catálogo de Libros</h5>
        <div>
            <button class="btn btn-sm btn-outline-secondary" data-bs-toggle="tooltip" title="Vista de cuadrícula">
                <i class="bi bi-grid-3x3"></i>
            </button>
            <button class="btn btn-sm btn-outline-secondary active" data-bs-toggle="tooltip" title="Vista de lista">
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
                        <th>ISBN</th>
                        <th>Título</th>
                        <th>Autor</th>
                        <th>Editorial</th>
                        <th>Categoría</th>
                        <th>Año</th>
                        <th>Ejemplares</th>
                        <th>Estado</th>
                        <th style="width: 150px;">Acciones</th>
                    </tr>
                </thead>
                <tbody>
                    <tr>
                        <td><input type="checkbox" class="form-check-input"></td>
                        <td><code>978-84-376-0494-7</code></td>
                        <td><strong>Cien Años de Soledad</strong></td>
                        <td>Gabriel García Márquez</td>
                        <td>Editorial Sudamericana</td>
                        <td><span class="badge bg-primary">Ficción</span></td>
                        <td>1967</td>
                        <td>
                            <span class="badge bg-info">3 disp.</span> / 3
                        </td>
                        <td><span class="badge bg-success">Disponible</span></td>
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
                        <td><code>978-84-670-0039-7</code></td>
                        <td><strong>El Quijote</strong></td>
                        <td>Miguel de Cervantes</td>
                        <td>Ediciones Cátedra</td>
                        <td><span class="badge bg-primary">Ficción</span></td>
                        <td>1605</td>
                        <td>
                            <span class="badge bg-info">2 disp.</span> / 5
                        </td>
                        <td><span class="badge bg-warning">Parcialmente Alquilado</span></td>
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
                        <td><code>978-84-204-8742-1</code></td>
                        <td><strong>1984</strong></td>
                        <td>George Orwell</td>
                        <td>Debolsillo</td>
                        <td><span class="badge bg-primary">Ficción</span></td>
                        <td>1949</td>
                        <td>
                            <span class="badge bg-danger">0 disp.</span> / 4
                        </td>
                        <td><span class="badge bg-danger">Totalmente Alquilado</span></td>
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
                        <td><code>978-84-204-8743-8</code></td>
                        <td><strong>La Odisea</strong></td>
                        <td>Homero</td>
                        <td>Alianza Editorial</td>
                        <td><span class="badge bg-primary">Ficción</span></td>
                        <td>-800</td>
                        <td>
                            <span class="badge bg-info">3 disp.</span> / 4
                        </td>
                        <td><span class="badge bg-success">Disponible</span></td>
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
                        <td><code>978-84-261-1336-1</code></td>
                        <td><strong>El Principito</strong></td>
                        <td>Antoine de Saint-Exupéry</td>
                        <td>Salamandra</td>
                        <td><span class="badge bg-success">Infantil</span></td>
                        <td>1943</td>
                        <td>
                            <span class="badge bg-info">5 disp.</span> / 6
                        </td>
                        <td><span class="badge bg-success">Disponible</span></td>
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
                        <td><code>978-84-204-8744-5</code></td>
                        <td><strong>Rayuela</strong></td>
                        <td>Julio Cortázar</td>
                        <td>Alfaguara</td>
                        <td><span class="badge bg-primary">Ficción</span></td>
                        <td>1963</td>
                        <td>
                            <span class="badge bg-secondary">1 mant.</span> / 3
                        </td>
                        <td><span class="badge bg-secondary">Mantenimiento</span></td>
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
                <li class="page-item"><a class="page-link" href="#">25</a></li>
                <li class="page-item">
                    <a class="page-link" href="#">Siguiente</a>
                </li>
            </ul>
        </nav>
    </div>
</div>

<!-- Add Book Modal -->
<div class="modal fade" id="addBookModal" tabindex="-1" aria-labelledby="addBookModalLabel" aria-hidden="true">
    <div class="modal-dialog modal-lg">
        <div class="modal-content">
            <div class="modal-header bg-primary text-white">
                <h5 class="modal-title" id="addBookModalLabel">
                    <i class="bi bi-plus-circle me-2"></i>Agregar Nuevo Libro
                </h5>
                <button type="button" class="btn-close btn-close-white" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <div class="modal-body">
                <form>
                    <div class="row g-3">
                        <div class="col-md-6">
                            <label class="form-label">ISBN *</label>
                            <input type="text" class="form-control" placeholder="978-84-xxx-xxxx-x" required>
                        </div>
                        <div class="col-md-6">
                            <label class="form-label">Título *</label>
                            <input type="text" class="form-control" placeholder="Título del libro" required>
                        </div>
                        <div class="col-md-6">
                            <label class="form-label">Autor *</label>
                            <select class="form-select" required>
                                <option value="">Seleccionar autor...</option>
                                <option>Gabriel García Márquez</option>
                                <option>Miguel de Cervantes</option>
                                <option>Jorge Luis Borges</option>
                            </select>
                        </div>
                        <div class="col-md-6">
                            <label class="form-label">Editorial *</label>
                            <input type="text" class="form-control" placeholder="Nombre de la editorial" required>
                        </div>
                        <div class="col-md-4">
                            <label class="form-label">Categoría *</label>
                            <select class="form-select" required>
                                <option value="">Seleccionar...</option>
                                <option>Ficción</option>
                                <option>No ficción</option>
                                <option>Técnico</option>
                                <option>Académico</option>
                                <option>Infantil</option>
                            </select>
                        </div>
                        <div class="col-md-4">
                            <label class="form-label">Año de Publicación</label>
                            <input type="number" class="form-control" placeholder="2024">
                        </div>
                        <div class="col-md-4">
                            <label class="form-label">Número de Ejemplares *</label>
                            <input type="number" class="form-control" value="1" min="1" required>
                        </div>
                        <div class="col-md-4">
                            <label class="form-label">Páginas</label>
                            <input type="number" class="form-control" placeholder="350">
                        </div>
                        <div class="col-md-4">
                            <label class="form-label">Idioma</label>
                            <select class="form-select">
                                <option>Español</option>
                                <option>Inglés</option>
                                <option>Francés</option>
                                <option>Alemán</option>
                                <option>Otro</option>
                            </select>
                        </div>
                        <div class="col-md-4">
                            <label class="form-label">Ubicación</label>
                            <input type="text" class="form-control" placeholder="Estante / Sección">
                        </div>
                        <div class="col-12">
                            <label class="form-label">Descripción</label>
                            <textarea class="form-control" rows="3" placeholder="Breve descripción del libro..."></textarea>
                        </div>
                        <div class="col-12">
                            <label class="form-label">Portada del Libro</label>
                            <input type="file" class="form-control" accept="image/*">
                        </div>
                    </div>
                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">
                    <i class="bi bi-x-circle"></i> Cancelar
                </button>
                <button type="button" class="btn btn-primary">
                    <i class="bi bi-save"></i> Guardar Libro
                </button>
            </div>
        </div>
    </div>
</div>
