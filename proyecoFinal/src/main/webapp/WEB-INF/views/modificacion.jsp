<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
<title>Home</title>
<script type="text/javascript">
	function validar() {

		document.querySelector("#formulario").submit();

	}
	function consultar(sel) {
		id = sel.options[sel.selectedIndex].value;
		document.location = "verUsuario.html?id=" + id;
	}
	function cargar() {

	}
</script>
</head>
<body onload="cargar()">

	<br>
	<br>
	<form action="grabaModificaUsuario.html" method="post" id="formulario"
		align="center">
		<table border="1" align="center">
			
			<tr>
				<td>Nombre*</td>
				<td><input type="text" name="nombre" value="${sessionScope.usuario.nombre}"
					required></td>
			</tr>
			<tr>
				<td>Primer Apellido*</td>
				<td><input type="text" name="apellido1"
					value="${sessionScope.usuario.apellido1}" required></td>
			</tr>
			<tr>
				<td>Segundo Apellido*</td>
				<td><input type="text" name="apellido2"
					value="${sessionScope.usuario.apellido2}" required></td>
			</tr>
			<tr>
				<td>DNI*</td>
				<td><input type="text" name="dni" value="${sessionScope.usuario.dni}"
					required></td>
			</tr>
			<tr>
				<td>Fecha Nacimiento*</td>
				<td><input type="date" name="fechaNacimiento"
					value="${sessionScope.usuario.fechaNacimiento }" required></td>
			</tr>
			<tr>
				<td>Correo Electronico*</td>
				<td><input type="text" name="email" value="${sessionScope.usuario.email}"
					required></td>
			</tr>
			<tr>
				<td>Rol</td>
				<td><select name="rol" id="rol">
						<option value="0">Selecciona rol....</option>
						<c:forEach items="${roles}" var="rol">
							<option value="${rol.id }" <c:if test="${rol.id eq sessionScope.usuario.rol }">selected</c:if> >${rol.rol}</option>
						</c:forEach>
				</select></td>
			</tr>
			<tr>

				<td colspan="2" align="center"><button onclick="validar()">ENVIAR</button>
				</td>
			</tr>
		</table>
	</form>
	<h1 id="resultado" style="color: #ff0000"></h1>
	<h1 style="color: #ff0000">${resultado }</h1>
</html>
