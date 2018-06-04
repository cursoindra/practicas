<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
	<form action="bajaUsuario.html" method="post">

		<table border="1" align="center">
			<tr>
				<td>USUARIO*</td>
				<td><input type="text" name="usuario" required></td>
			</tr>
			<tr>
				<td colspan="2" align="center"><button type="submit">DAR
						DE BAJA</button></td>
			</tr>
		</table>
	</form>
	<h1 class="rojo">${resultado }</h1>
</body>
</html>