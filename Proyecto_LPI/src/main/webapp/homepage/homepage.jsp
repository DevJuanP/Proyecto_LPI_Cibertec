<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<jsp:include page="../head.jsp"></jsp:include>
<body class="container bg-light">
	<jsp:include page="../header.jsp"></jsp:include>
	<main>
		<section>
			<jsp:include page="carrusel.jsp"></jsp:include>
		</section>
		<section class="container">

    <h1 class="display-5 fw-bold text-center mb-4">Misterio</h1>
    <jsp:include page="cards.jsp">
        <jsp:param name="listaLibros" value="${misterio}" />
    </jsp:include>

    <h1 class="display-5 fw-bold text-center mb-4">Acción</h1>
    <jsp:include page="cards.jsp">
        <jsp:param name="listaLibros" value="${accion}" />
    </jsp:include>

    <h1 class="display-5 fw-bold text-center mb-4">Romance</h1>
    <jsp:include page="cards.jsp">
        <jsp:param name="listaLibros" value="${romance}" />
    </jsp:include>

</section>

	</main>

	<jsp:include page="../footer.jsp"></jsp:include>

</body>

</html>