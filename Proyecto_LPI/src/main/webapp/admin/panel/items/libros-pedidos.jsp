<%@ page contentType="text/html;charset=UTF-8" language="java" %>
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

<!-- Period Selector -->
<div class="card mb-4">
    <div class="card-body">
        <div class="row g-3 align-items-end">
            <div class="col-md-3">
                <label class="form-label">Período</label>
                <select class="form-select">
                    <option value="mes">Último mes</option>
                    <option value="trimestre">Último trimestre</option>
                    <option value="semestre">Último semestre</option>
                    <option value="anio" selected>Último año</option>
                    <option value="todo">Todo el tiempo</option>
                    <option value="custom">Personalizado</option>
                </select>
            </div>
            <div class="col-md-3">
                <label class="form-label">Categoría</label>
                <select class="form-select">
                    <option value="">Todas las categorías</option>
                    <option value="ficcion">Ficción</option>
                    <option value="no-ficcion">No ficción</option>
                    <option value="tecnico">Técnico</option>
                    <option value="academico">Académico</option>
                    <option value="infantil">Infantil</option>
                </select>
            </div>
            <div class="col-md-3">
                <label class="form-label">Top</label>
                <select class="form-select">
                    <option value="10">Top 10</option>
                    <option value="20" selected>Top 20</option>
                    <option value="50">Top 50</option>
                    <option value="100">Top 100</option>
                </select>
            </div>
            <div class="col-md-3">
                <button class="btn btn-primary w-100"><i class="bi bi-funnel"></i> Filtrar</button>
            </div>
        </div>
    </div>
</div>

<!-- Top 3 Highlight -->
<div class="row g-4 mb-4">
    <div class="col-md-4">
        <div class="card border-warning shadow-lg">
            <div class="card-body text-center">
                <div class="position-relative d-inline-block mb-3">
                    <i class="bi bi-trophy-fill text-warning" style="font-size: 4rem;"></i>
                    <span class="position-absolute top-0 start-100 translate-middle badge rounded-pill bg-warning">
                        #1
                    </span>
                </div>
                <h4 class="fw-bold">Cien Años de Soledad</h4>
                <p class="text-muted mb-2">Gabriel García Márquez</p>
                <h2 class="text-warning fw-bold">156</h2>
                <p class="text-muted">alquileres este año</p>
                <div class="progress" style="height: 8px;">
                    <div class="progress-bar bg-warning" role="progressbar" style="width: 100%"></div>
                </div>
            </div>
        </div>
    </div>
    
    <div class="col-md-4">
        <div class="card border-secondary shadow">
            <div class="card-body text-center">
                <div class="position-relative d-inline-block mb-3">
                    <i class="bi bi-trophy-fill text-secondary" style="font-size: 4rem;"></i>
                    <span class="position-absolute top-0 start-100 translate-middle badge rounded-pill bg-secondary">
                        #2
                    </span>
                </div>
                <h4 class="fw-bold">El Quijote</h4>
                <p class="text-muted mb-2">Miguel de Cervantes</p>
                <h2 class="text-secondary fw-bold">142</h2>
                <p class="text-muted">alquileres este año</p>
                <div class="progress" style="height: 8px;">
                    <div class="progress-bar bg-secondary" role="progressbar" style="width: 91%"></div>
                </div>
            </div>
        </div>
    </div>
    
    <div class="col-md-4">
        <div class="card border-info shadow">
            <div class="card-body text-center">
                <div class="position-relative d-inline-block mb-3">
                    <i class="bi bi-trophy-fill text-info" style="font-size: 4rem;"></i>
                    <span class="position-absolute top-0 start-100 translate-middle badge rounded-pill bg-info">
                        #3
                    </span>
                </div>
                <h4 class="fw-bold">1984</h4>
                <p class="text-muted mb-2">George Orwell</p>
                <h2 class="text-info fw-bold">138</h2>
                <p class="text-muted">alquileres este año</p>
                <div class="progress" style="height: 8px;">
                    <div class="progress-bar bg-info" role="progressbar" style="width: 88%"></div>
                </div>
            </div>
        </div>
    </div>
</div>

<!-- Complete Ranking Table -->
<div class="card">
    <div class="card-header">
        <h5 class="mb-0">Ranking Completo - Top 20</h5>
    </div>
    <div class="card-body">
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
                        <th>Tendencia</th>
                        <th>Popularidad</th>
                    </tr>
                </thead>
                <tbody>
                    <tr class="table-warning">
                        <td class="text-center">
                            <span class="badge bg-warning fs-6">1</span>
                        </td>
                        <td><code>978-84-376-0494-7</code></td>
                        <td><strong>Cien Años de Soledad</strong></td>
                        <td>Gabriel García Márquez</td>
                        <td><span class="badge bg-primary">Ficción</span></td>
                        <td><strong>156</strong></td>
                        <td><span class="text-success"><i class="bi bi-arrow-up-circle-fill"></i> +12%</span></td>
                        <td>
                            <div class="progress" style="height: 20px;">
                                <div class="progress-bar bg-warning" role="progressbar" style="width: 100%">100%</div>
                            </div>
                        </td>
                    </tr>
                    <tr>
                        <td class="text-center">
                            <span class="badge bg-secondary fs-6">2</span>
                        </td>
                        <td><code>978-84-670-0039-7</code></td>
                        <td><strong>El Quijote</strong></td>
                        <td>Miguel de Cervantes</td>
                        <td><span class="badge bg-primary">Ficción</span></td>
                        <td><strong>142</strong></td>
                        <td><span class="text-success"><i class="bi bi-arrow-up-circle-fill"></i> +8%</span></td>
                        <td>
                            <div class="progress" style="height: 20px;">
                                <div class="progress-bar bg-secondary" role="progressbar" style="width: 91%">91%</div>
                            </div>
                        </td>
                    </tr>
                    <tr>
                        <td class="text-center">
                            <span class="badge bg-info fs-6">3</span>
                        </td>
                        <td><code>978-84-204-8742-1</code></td>
                        <td><strong>1984</strong></td>
                        <td>George Orwell</td>
                        <td><span class="badge bg-primary">Ficción</span></td>
                        <td><strong>138</strong></td>
                        <td><span class="text-success"><i class="bi bi-arrow-up-circle-fill"></i> +15%</span></td>
                        <td>
                            <div class="progress" style="height: 20px;">
                                <div class="progress-bar bg-info" role="progressbar" style="width: 88%">88%</div>
                            </div>
                        </td>
                    </tr>
                    <tr>
                        <td class="text-center">4</td>
                        <td><code>978-84-204-8743-8</code></td>
                        <td>La Odisea</td>
                        <td>Homero</td>
                        <td><span class="badge bg-primary">Ficción</span></td>
                        <td>125</td>
                        <td><span class="text-muted"><i class="bi bi-dash-circle-fill"></i> 0%</span></td>
                        <td>
                            <div class="progress" style="height: 20px;">
                                <div class="progress-bar" role="progressbar" style="width: 80%">80%</div>
                            </div>
                        </td>
                    </tr>
                    <tr>
                        <td class="text-center">5</td>
                        <td><code>978-84-261-1336-1</code></td>
                        <td>El Principito</td>
                        <td>Antoine de Saint-Exupéry</td>
                        <td><span class="badge bg-success">Infantil</span></td>
                        <td>118</td>
                        <td><span class="text-success"><i class="bi bi-arrow-up-circle-fill"></i> +20%</span></td>
                        <td>
                            <div class="progress" style="height: 20px;">
                                <div class="progress-bar" role="progressbar" style="width: 76%">76%</div>
                            </div>
                        </td>
                    </tr>
                    <tr>
                        <td class="text-center">6</td>
                        <td><code>978-84-204-8744-5</code></td>
                        <td>Rayuela</td>
                        <td>Julio Cortázar</td>
                        <td><span class="badge bg-primary">Ficción</span></td>
                        <td>112</td>
                        <td><span class="text-success"><i class="bi bi-arrow-up-circle-fill"></i> +5%</span></td>
                        <td>
                            <div class="progress" style="height: 20px;">
                                <div class="progress-bar" role="progressbar" style="width: 72%">72%</div>
                            </div>
                        </td>
                    </tr>
                    <tr>
                        <td class="text-center">7</td>
                        <td><code>978-84-204-8745-2</code></td>
                        <td>La Casa de los Espíritus</td>
                        <td>Isabel Allende</td>
                        <td><span class="badge bg-primary">Ficción</span></td>
                        <td>108</td>
                        <td><span class="text-danger"><i class="bi bi-arrow-down-circle-fill"></i> -3%</span></td>
                        <td>
                            <div class="progress" style="height: 20px;">
                                <div class="progress-bar" role="progressbar" style="width: 69%">69%</div>
                            </div>
                        </td>
                    </tr>
                    <tr>
                        <td class="text-center">8</td>
                        <td><code>978-84-204-8746-9</code></td>
                        <td>Ficciones</td>
                        <td>Jorge Luis Borges</td>
                        <td><span class="badge bg-primary">Ficción</span></td>
                        <td>102</td>
                        <td><span class="text-success"><i class="bi bi-arrow-up-circle-fill"></i> +7%</span></td>
                        <td>
                            <div class="progress" style="height: 20px;">
                                <div class="progress-bar" role="progressbar" style="width: 65%">65%</div>
                            </div>
                        </td>
                    </tr>
                    <tr>
                        <td class="text-center">9</td>
                        <td><code>978-84-204-8747-6</code></td>
                        <td>Crónica de una Muerte Anunciada</td>
                        <td>Gabriel García Márquez</td>
                        <td><span class="badge bg-primary">Ficción</span></td>
                        <td>98</td>
                        <td><span class="text-success"><i class="bi bi-arrow-up-circle-fill"></i> +10%</span></td>
                        <td>
                            <div class="progress" style="height: 20px;">
                                <div class="progress-bar" role="progressbar" style="width: 63%">63%</div>
                            </div>
                        </td>
                    </tr>
                    <tr>
                        <td class="text-center">10</td>
                        <td><code>978-84-204-8748-3</code></td>
                        <td>Pedro Páramo</td>
                        <td>Juan Rulfo</td>
                        <td><span class="badge bg-primary">Ficción</span></td>
                        <td>94</td>
                        <td><span class="text-muted"><i class="bi bi-dash-circle-fill"></i> 0%</span></td>
                        <td>
                            <div class="progress" style="height: 20px;">
                                <div class="progress-bar" role="progressbar" style="width: 60%">60%</div>
                            </div>
                        </td>
                    </tr>
                </tbody>
            </table>
        </div>
    </div>
</div>
