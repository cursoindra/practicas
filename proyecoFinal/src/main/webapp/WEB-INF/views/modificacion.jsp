<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page session="false"%>
<html>
<head>
<title>Home</title>
</head>
<body>
	<form action="" method="post" id="formulario">
		Usuario: 
		<select>
		  <option value="">Prueba</option>
		</select>
		<table  border="1" align="center">
			<tr></tr>
		</table>
	</form>
	<h1 id="resultado" style="color: #ff0000"></h1>
	<h1 style="color: #ff0000">${resultado }</h1>
</html>