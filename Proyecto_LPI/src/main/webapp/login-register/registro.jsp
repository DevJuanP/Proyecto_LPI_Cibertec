<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>

<jsp:include page="../head.jsp"></jsp:include>

<body class="container">



	<main>
		<div class="container text-center">
			<body class="bg-light">

				<div
					class="container d-flex justify-content-center align-items-center min-vh-100">
					<div class="card shadow-lg p-4"
						style="width: 420px; border-radius: 15px;">

						<h3 class="text-center mb-4">Registro de Usuario</h3>

						<form action="#" method="POST" class="needs-validation" novalidate>

							<!-- Nombre -->
							<div class="mb-3">
								<label for="nombre" class="form-label">Nombre</label> <input
									type="text" class="form-control" id="nombre" name="nombre"
									required>
								<div class="invalid-feedback">Ingresa tu nombre.</div>
							</div>

							<!-- Email -->
							<div class="mb-3">
								<label for="email" class="form-label">Correo electrónico</label>
								<input type="email" class="form-control" id="email" name="email"
									required>
								<div class="invalid-feedback">Ingresa un correo válido.</div>
							</div>

							<!-- Contraseña -->
							<div class="mb-3">
								<label for="password" class="form-label">Contraseña</label> <input
									type="password" class="form-control" id="password"
									name="password" required>
								<div class="invalid-feedback">La contraseña es
									obligatoria.</div>
							</div>

							<!-- Confirmar contraseña -->
							<div class="mb-3">
								<label for="confirmPassword" class="form-label">Confirmar
									Contraseña</label> <input type="password" class="form-control"
									id="confirmPassword" name="confirmPassword" required>
								<div class="invalid-feedback">Debe coincidir con la
									contraseña.</div>
							</div>

							<!-- Botón -->
							<button type="submit" class="btn btn-primary w-100">Registrarse</button>

							<p class="text-center mt-3 small">
								¿Ya tienes una cuenta? <a href="#">Inicia sesión</a>
							</p>
						</form>
					</div>
				</div>
		</div>
	</main>

	<jsp:include page="../footer.jsp"></jsp:include>

</body>
</html>