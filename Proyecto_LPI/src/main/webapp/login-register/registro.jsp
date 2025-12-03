<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>

<jsp:include page="../head.jsp"></jsp:include>

<body
	class="diagonal-line-bg vh-100 d-flex flex-column justify-content-center align-items-center">

	<div class="container">

		<div class="row justify-content-center">
			<div class="col-12 col-md-5 col-lg-4">

				<div class="card shadow-lg border-0 rounded-3">
					<div class="card-header bg-dark text-white text-center p-4">
						<h4 class="mb-0">Resgitro de usuario</h4>
					</div>

					<div class="card-body p-4">
						<form action="../homepage/homepage.jsp" method="post">
						
							<div class="mb-3">
								<label class="form-label fw-bold">Nombre</label> <input
									type="text" class="form-control form-control-lg" name="usuario"
									placeholder="Ingrese su nombre" required>
							</div>

							<div class="mb-3">
								<label class="form-label fw-bold">Email address</label> <input
									type="Email" class="form-control form-control-lg" name="usuario"
									placeholder="Ingrese su Email" required>
							</div>

							<div class="mb-3">
								<label class="form-label fw-bold">Contraseña</label> <input
									type="password" class="form-control form-control-lg"
									name="password" placeholder="Ingrese contraseña" required>
							</div>
							
							<div class="mb-3">
								<label class="form-label fw-bold">Confirme su contraseña</label> <input
									type="password" class="form-control form-control-lg"
									name="password" placeholder="Ingrese contraseña nuevamente" required>
							</div>

							<div class="d-grid mt-4">
								<button type="submit" class="btn btn-dark btn-lg">registrarse</button>
							</div>

						</form>
						
						<hr class="mb-3 mt-3"/>

                        <div class="text-center">
                            ¿Ya tiene una cuenta? <a href="login.jsp" class="text-decoration-none"> Inicie sesión</a>
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