<%@ page contentType="text/html;charset=UTF-8" language="java" %>
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

<!-- Period and Filters -->
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
                </select>
            </div>
            <div class="col-md-3">
                <label class="form-label">Nacionalidad</label>
                <select class="form-select">
                    <option value="">Todas</option>
                    <option value="colombia">Colombia</option>
                    <option value="espana">España</option>
                    <option value="argentina">Argentina</option>
                    <option value="mexico">México</option>
                    <option value="otros">Otros</option>
                </select>
            </div>
            <div class="col-md-3">
                <label class="form-label">Top</label>
                <select class="form-select">
                    <option value="10">Top 10</option>
                    <option value="20" selected>Top 20</option>
                    <option value="50">Top 50</option>
                </select>
            </div>
            <div class="col-md-3">
                <button class="btn btn-primary w-100"><i class="bi bi-funnel"></i> Filtrar</button>
            </div>
        </div>
    </div>
</div>

<!-- Top 3 Authors -->
<div class="row g-4 mb-4">
    <div class="col-md-4">
        <div class="card border-warning shadow-lg">
            <div class="card-body text-center">
                <div class="position-relative d-inline-block mb-3">
                    <img src="https://ui-avatars.com/api/?name=Gabriel+Garcia+Marquez&size=120&background=ffc107&color=000" 
                         class="rounded-circle border border-warning border-4" 
                         alt="Gabriel García Márquez">
                    <span class="position-absolute top-0 start-100 translate-middle badge rounded-pill bg-warning fs-6">
                        #1
                    </span>
                </div>
                <h4 class="fw-bold">Gabriel García Márquez</h4>
                <p class="text-muted mb-2"><i class="bi bi-flag-fill"></i> Colombia</p>
                <h2 class="text-warning fw-bold">254</h2>
                <p class="text-muted mb-3">alquileres totales</p>
                <div class="row g-2 text-start">
                    <div class="col-6">
                        <small class="text-muted">Libros:</small>
                        <div class="fw-bold">8</div>
                    </div>
                    <div class="col-6">
                        <small class="text-muted">Disponibles:</small>
                        <div class="fw-bold">24</div>
                    </div>
                </div>
            </div>
        </div>
    </div>
    
    <div class="col-md-4">
        <div class="card border-secondary shadow">
            <div class="card-body text-center">
                <div class="position-relative d-inline-block mb-3">
                    <img src="https://ui-avatars.com/api/?name=Miguel+Cervantes&size=120&background=6c757d&color=fff" 
                         class="rounded-circle border border-secondary border-4" 
                         alt="Miguel de Cervantes">
                    <span class="position-absolute top-0 start-100 translate-middle badge rounded-pill bg-secondary fs-6">
                        #2
                    </span>
                </div>
                <h4 class="fw-bold">Miguel de Cervantes</h4>
                <p class="text-muted mb-2"><i class="bi bi-flag-fill"></i> España</p>
                <h2 class="text-secondary fw-bold">186</h2>
                <p class="text-muted mb-3">alquileres totales</p>
                <div class="row g-2 text-start">
                    <div class="col-6">
                        <small class="text-muted">Libros:</small>
                        <div class="fw-bold">5</div>
                    </div>
                    <div class="col-6">
                        <small class="text-muted">Disponibles:</small>
                        <div class="fw-bold">18</div>
                    </div>
                </div>
            </div>
        </div>
    </div>
    
    <div class="col-md-4">
        <div class="card border-info shadow">
            <div class="card-body text-center">
                <div class="position-relative d-inline-block mb-3">
                    <img src="https://ui-avatars.com/api/?name=Jorge+Luis+Borges&size=120&background=0dcaf0&color=000" 
                         class="rounded-circle border border-info border-4" 
                         alt="Jorge Luis Borges">
                    <span class="position-absolute top-0 start-100 translate-middle badge rounded-pill bg-info fs-6">
                        #3
                    </span>
                </div>
                <h4 class="fw-bold">Jorge Luis Borges</h4>
                <p class="text-muted mb-2"><i class="bi bi-flag-fill"></i> Argentina</p>
                <h2 class="text-info fw-bold">172</h2>
                <p class="text-muted mb-3">alquileres totales</p>
                <div class="row g-2 text-start">
                    <div class="col-6">
                        <small class="text-muted">Libros:</small>
                        <div class="fw-bold">12</div>
                    </div>
                    <div class="col-6">
                        <small class="text-muted">Disponibles:</small>
                        <div class="fw-bold">35</div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<!-- Statistics Cards -->
<div class="row g-3 mb-4">
    <div class="col-md-3">
        <div class="card bg-primary bg-gradient text-white">
            <div class="card-body">
                <h6><i class="bi bi-people me-2"></i>Total Autores</h6>
                <h3 class="mb-0">456</h3>
            </div>
        </div>
    </div>
    <div class="col-md-3">
        <div class="card bg-success bg-gradient text-white">
            <div class="card-body">
                <h6><i class="bi bi-star me-2"></i>Con Alquileres</h6>
                <h3 class="mb-0">342</h3>
            </div>
        </div>
    </div>
    <div class="col-md-3">
        <div class="card bg-warning bg-gradient text-white">
            <div class="card-body">
                <h6><i class="bi bi-trophy me-2"></i>Más Popular</h6>
                <h3 class="mb-0">G. García Márquez</h3>
            </div>
        </div>
    </div>
    <div class="col-md-3">
        <div class="card bg-info bg-gradient text-white">
            <div class="card-body">
                <h6><i class="bi bi-graph-up me-2"></i>Promedio</h6>
                <h3 class="mb-0">56 <small class="fs-6">alq/autor</small></h3>
            </div>
        </div>
    </div>
</div>

<!-- Complete Ranking -->
<div class="card">
    <div class="card-header">
        <h5 class="mb-0">Ranking Completo de Autores - Top 20</h5>
    </div>
    <div class="card-body">
        <div class="table-responsive">
            <table class="table table-hover">
                <thead class="table-dark">
                    <tr>
                        <th style="width: 80px;">Pos.</th>
                        <th>Autor</th>
                        <th>Nacionalidad</th>
                        <th>Obras en Sistema</th>
                        <th>Ejemplares</th>
                        <th>Alquileres Totales</th>
                        <th>Promedio/Libro</th>
                        <th>Disponibilidad</th>
                    </tr>
                </thead>
                <tbody>
                    <tr class="table-warning">
                        <td class="text-center">
                            <span class="badge bg-warning fs-6">1</span>
                        </td>
                        <td>
                            <div class="d-flex align-items-center">
                                <img src="https://ui-avatars.com/api/?name=Gabriel+Garcia+Marquez&size=40&background=ffc107&color=000" 
                                     class="rounded-circle me-2" alt="GGM">
                                <strong>Gabriel García Márquez</strong>
                            </div>
                        </td>
                        <td><span class="badge bg-primary"><i class="bi bi-flag-fill"></i> Colombia</span></td>
                        <td class="text-center">8</td>
                        <td class="text-center">24</td>
                        <td class="text-center"><strong>254</strong></td>
                        <td class="text-center">31.8</td>
                        <td>
                            <div class="progress" style="height: 20px;">
                                <div class="progress-bar bg-success" role="progressbar" style="width: 85%">85%</div>
                            </div>
                        </td>
                    </tr>
                    <tr>
                        <td class="text-center">
                            <span class="badge bg-secondary fs-6">2</span>
                        </td>
                        <td>
                            <div class="d-flex align-items-center">
                                <img src="https://ui-avatars.com/api/?name=Miguel+Cervantes&size=40&background=6c757d&color=fff" 
                                     class="rounded-circle me-2" alt="MC">
                                <strong>Miguel de Cervantes</strong>
                            </div>
                        </td>
                        <td><span class="badge bg-danger"><i class="bi bi-flag-fill"></i> España</span></td>
                        <td class="text-center">5</td>
                        <td class="text-center">18</td>
                        <td class="text-center"><strong>186</strong></td>
                        <td class="text-center">37.2</td>
                        <td>
                            <div class="progress" style="height: 20px;">
                                <div class="progress-bar bg-success" role="progressbar" style="width: 78%">78%</div>
                            </div>
                        </td>
                    </tr>
                    <tr>
                        <td class="text-center">
                            <span class="badge bg-info fs-6">3</span>
                        </td>
                        <td>
                            <div class="d-flex align-items-center">
                                <img src="https://ui-avatars.com/api/?name=Jorge+Luis+Borges&size=40&background=0dcaf0&color=000" 
                                     class="rounded-circle me-2" alt="JLB">
                                <strong>Jorge Luis Borges</strong>
                            </div>
                        </td>
                        <td><span class="badge bg-info"><i class="bi bi-flag-fill"></i> Argentina</span></td>
                        <td class="text-center">12</td>
                        <td class="text-center">35</td>
                        <td class="text-center"><strong>172</strong></td>
                        <td class="text-center">14.3</td>
                        <td>
                            <div class="progress" style="height: 20px;">
                                <div class="progress-bar bg-success" role="progressbar" style="width: 92%">92%</div>
                            </div>
                        </td>
                    </tr>
                    <tr>
                        <td class="text-center">4</td>
                        <td>
                            <div class="d-flex align-items-center">
                                <img src="https://ui-avatars.com/api/?name=Isabel+Allende&size=40&background=e74c3c&color=fff" 
                                     class="rounded-circle me-2" alt="IA">
                                Isabel Allende
                            </div>
                        </td>
                        <td><span class="badge bg-warning"><i class="bi bi-flag-fill"></i> Chile</span></td>
                        <td class="text-center">7</td>
                        <td class="text-center">22</td>
                        <td class="text-center">165</td>
                        <td class="text-center">23.6</td>
                        <td>
                            <div class="progress" style="height: 20px;">
                                <div class="progress-bar bg-success" role="progressbar" style="width: 88%">88%</div>
                            </div>
                        </td>
                    </tr>
                    <tr>
                        <td class="text-center">5</td>
                        <td>
                            <div class="d-flex align-items-center">
                                <img src="https://ui-avatars.com/api/?name=Julio+Cortazar&size=40&background=9b59b6&color=fff" 
                                     class="rounded-circle me-2" alt="JC">
                                Julio Cortázar
                            </div>
                        </td>
                        <td><span class="badge bg-info"><i class="bi bi-flag-fill"></i> Argentina</span></td>
                        <td class="text-center">9</td>
                        <td class="text-center">28</td>
                        <td class="text-center">158</td>
                        <td class="text-center">17.6</td>
                        <td>
                            <div class="progress" style="height: 20px;">
                                <div class="progress-bar bg-success" role="progressbar" style="width: 82%">82%</div>
                            </div>
                        </td>
                    </tr>
                    <tr>
                        <td class="text-center">6</td>
                        <td>
                            <div class="d-flex align-items-center">
                                <img src="https://ui-avatars.com/api/?name=George+Orwell&size=40&background=34495e&color=fff" 
                                     class="rounded-circle me-2" alt="GO">
                                George Orwell
                            </div>
                        </td>
                        <td><span class="badge bg-dark"><i class="bi bi-flag-fill"></i> Reino Unido</span></td>
                        <td class="text-center">4</td>
                        <td class="text-center">15</td>
                        <td class="text-center">145</td>
                        <td class="text-center">36.3</td>
                        <td>
                            <div class="progress" style="height: 20px;">
                                <div class="progress-bar bg-warning" role="progressbar" style="width: 65%">65%</div>
                            </div>
                        </td>
                    </tr>
                    <tr>
                        <td class="text-center">7</td>
                        <td>
                            <div class="d-flex align-items-center">
                                <img src="https://ui-avatars.com/api/?name=Mario+Vargas+Llosa&size=40&background=e67e22&color=fff" 
                                     class="rounded-circle me-2" alt="MVL">
                                Mario Vargas Llosa
                            </div>
                        </td>
                        <td><span class="badge bg-danger"><i class="bi bi-flag-fill"></i> Perú</span></td>
                        <td class="text-center">10</td>
                        <td class="text-center">31</td>
                        <td class="text-center">138</td>
                        <td class="text-center">13.8</td>
                        <td>
                            <div class="progress" style="height: 20px;">
                                <div class="progress-bar bg-success" role="progressbar" style="width: 90%">90%</div>
                            </div>
                        </td>
                    </tr>
                    <tr>
                        <td class="text-center">8</td>
                        <td>
                            <div class="d-flex align-items-center">
                                <img src="https://ui-avatars.com/api/?name=Pablo+Neruda&size=40&background=27ae60&color=fff" 
                                     class="rounded-circle me-2" alt="PN">
                                Pablo Neruda
                            </div>
                        </td>
                        <td><span class="badge bg-warning"><i class="bi bi-flag-fill"></i> Chile</span></td>
                        <td class="text-center">6</td>
                        <td class="text-center">19</td>
                        <td class="text-center">126</td>
                        <td class="text-center">21.0</td>
                        <td>
                            <div class="progress" style="height: 20px;">
                                <div class="progress-bar bg-success" role="progressbar" style="width: 87%">87%</div>
                            </div>
                        </td>
                    </tr>
                    <tr>
                        <td class="text-center">9</td>
                        <td>
                            <div class="d-flex align-items-center">
                                <img src="https://ui-avatars.com/api/?name=Juan+Rulfo&size=40&background=16a085&color=fff" 
                                     class="rounded-circle me-2" alt="JR">
                                Juan Rulfo
                            </div>
                        </td>
                        <td><span class="badge bg-success"><i class="bi bi-flag-fill"></i> México</span></td>
                        <td class="text-center">3</td>
                        <td class="text-center">12</td>
                        <td class="text-center">118</td>
                        <td class="text-center">39.3</td>
                        <td>
                            <div class="progress" style="height: 20px;">
                                <div class="progress-bar bg-success" role="progressbar" style="width: 75%">75%</div>
                            </div>
                        </td>
                    </tr>
                    <tr>
                        <td class="text-center">10</td>
                        <td>
                            <div class="d-flex align-items-center">
                                <img src="https://ui-avatars.com/api/?name=Octavio+Paz&size=40&background=8e44ad&color=fff" 
                                     class="rounded-circle me-2" alt="OP">
                                Octavio Paz
                            </div>
                        </td>
                        <td><span class="badge bg-success"><i class="bi bi-flag-fill"></i> México</span></td>
                        <td class="text-center">5</td>
                        <td class="text-center">16</td>
                        <td class="text-center">112</td>
                        <td class="text-center">22.4</td>
                        <td>
                            <div class="progress" style="height: 20px;">
                                <div class="progress-bar bg-success" role="progressbar" style="width: 94%">94%</div>
                            </div>
                        </td>
                    </tr>
                </tbody>
            </table>
        </div>
    </div>
</div>
