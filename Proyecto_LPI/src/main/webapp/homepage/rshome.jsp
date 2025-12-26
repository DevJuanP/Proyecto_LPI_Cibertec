<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<section>	
		<h1>Misterio</h1>
			<jsp:include page="cards.jsp">
			    <jsp:param name="categoria" value="misterio"/>
			</jsp:include>
			
			<h1>Acción</h1>
			<jsp:include page="cards.jsp">
			    <jsp:param name="categoria" value="accion"/>
			</jsp:include>
			
			<h1>Romance</h1>
			<jsp:include page="cards.jsp">
			    <jsp:param name="categoria" value="romance"/>
			</jsp:include>

	</section>
</body>
</html>