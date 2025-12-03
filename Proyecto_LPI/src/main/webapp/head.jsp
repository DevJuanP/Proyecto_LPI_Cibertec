<head>
<meta charset="UTF-8">
<title>Ejercicio 01</title>
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.8/dist/css/bootstrap.min.css"
	rel="stylesheet">
<link rel="stylesheet"
	href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.13.1/font/bootstrap-icons.min.css">
<style>
/* 1. Definimos una clase personalizada */
.diagonal-line-bg {
	background-color: #f8f9fa; /
	/* el patrón de líneas diagonales */
	background-image: repeating-linear-gradient(-45deg, /* Ángulo: -45 grados (diagonal) */
            #343a4030,
		/* Color de la línea (Negro semi-transparente 30%) */
            #343a4030 1px, /* Espesor de la línea: 1 píxel */
            transparent 1px, transparent 10px
		/* Espacio entre líneas: 10 píxeles */
        );
	/* 3. Definimos el tamaño del "azulejo" que se repite */
	background-size: 20px 20px;
}

/*Boton de google*/
.btn-google {
	color: white;
	background-color: #db4437;
	border-color: #db4437;
	transition: background-color 0.3s;
}

.btn-google:hover {
	background-color: #c0392b;
	border-color: #c0392b;
}
</style>
</head>