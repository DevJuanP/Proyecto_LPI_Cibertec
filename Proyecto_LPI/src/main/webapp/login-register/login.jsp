<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Login</title>
<jsp:include page="./shared/head.jsp"></jsp:include>
</head>
<body
	class="diagonal-line-bg vh-100 d-flex flex-column justify-content-center align-items-center">

	<div class="container">

		<div class="row justify-content-center">
			<div class="col-12 col-md-5 col-lg-4">

				<div class="card shadow-lg border-0 rounded-3">
					<div class="card-header bg-dark text-white text-center p-4">
						<h4 class="mb-0">Acceso al Sistema</h4>
					</div>

					<div class="card-body p-4">
						<form action="LoginServlet" method="post">

							<div class="mb-3">
								<label class="form-label fw-bold">Email address</label> <input
									type="text" class="form-control form-control-lg" name="usuario"
									placeholder="Ingrese usuario" required>
							</div>

							<div class="mb-3">
								<label class="form-label fw-bold">Password</label> <input
									type="password" class="form-control form-control-lg"
									name="password" placeholder="Ingrese contraseña" required>
							</div>

							<div class="mb-3 form-check">
								<input type="checkbox" class="form-check-input" id="rememberMe"
									name="rememberMe" value="true"> <label
									class="form-check-label" for="rememberMe">Recordarme</label>
							</div>

							<div class="d-grid mt-4">
								<button type="submit" class="btn btn-dark btn-lg">Log
									In</button>
							</div>

							<div class="text-center my-3">
								<small class="text-muted">O</small>
							</div>
							
							

							<div class="d-grid gap-2">
								<a href="GoogleLoginServlet" class="btn btn-google btn-lg"
									role="button"> <i class="bi bi-google me-2"></i> Ingresar
									con Google
								</a>
							</div>


						</form>
						
						<hr class="mb-3 mt-3"/>

                        <div class="text-center">
                            <a href="registro.jsp" class="text-decoration-none">Crear una cuenta</a>
                        </div>
						
						
					</div>
					<div class="card-footer text-center py-3 bg-white border-0">
						<small class="text-muted">Proyecto LP1-MVC- Cibertec</small>
					</div>
				</div>

			</div>
		</div>
	</div>
	<jsp:include page="../footer.jsp"></jsp:include>

</body>
</html>