<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page session="false"%>
<html>
<head>
<title>Home</title>
</head>
<body>
	<form action="" method="post" id="formulario" align="center">
		Usuario: 
		<select>
		  <option value="">Usuario 1</option>
		  <option value="">Usuario 2</option>
		  <option value="">Usuario 3</option>
		  <option value="">Usuario 4</option>
		</select>
		<br><br>
		<table border="1" align="center">
			<tr>
				<td>Texto de prueba</td>
				<td>Texto de prueba</td>
			</tr>
			<tr>
				<td>Texto de prueba</td>
				<td>Texto de prueba</td>
			</tr>
			<tr>
				<td colspan="2" align="center"><button type="submit">GUARDAR CAMBIOS</button></td>
			</tr>				
	</table>
	</form>
	<h1 id="resultado" style="color: #ff0000"></h1>
	<h1 style="color: #ff0000">${resultado }</h1>	
</html>
