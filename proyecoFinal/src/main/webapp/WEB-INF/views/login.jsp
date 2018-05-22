<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" href="resources/style.css">
</head>
<body>

	<form action="verLogin.html" method="post">

		<table border="1" align="center">
			<tr>
				<td>USUARIO*</td>
				<td><input type="text"  name="usuario"  required> </td>
			</tr>
			<tr>
				<td>CLAVE*</td>
				<td><input type="password"  name="clave"  required  > </td>
			</tr>
			<tr>
				
				<td colspan="2" align="center" ><input type="submit"> </td>
			</tr>
		</table>
	</form>
<h1 class="rojo">${resultado }</h1>
</body>
</html>