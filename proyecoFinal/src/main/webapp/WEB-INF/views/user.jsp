<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" href="resources/style.css">
</head>
<body>
	<c:choose>
		<c:when test="${sessionScope.user eq 1 }">
			<table border="1" align="center">

				<tr>
					<td class="rojo"><a href="modificacion.html">MODIFICACION</a></td>
				</tr>
				<tr>
					<td class="rojo"><a href="adios.html">logout</a></td>
				</tr>
			</table>
		</c:when>
		<c:otherwise>
			<h1>NO AUTORIZADO</h1>
		</c:otherwise>
	</c:choose>
</body>
</html>