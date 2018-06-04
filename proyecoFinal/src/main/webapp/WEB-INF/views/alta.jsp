<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<html>
<head>
<title>Home</title>
<script type="text/javascript">
	function validar() {
		event.preventDefault();
		var clave1 = document.querySelector("#clave1").value;
		var clave2 = document.querySelector("#clave2").value;
		var rol = document.querySelector("#rol").selectedIndex;
		if (clave1 === clave2 && rol>0) {
			document.querySelector("#formulario").submit();
		} 
		if(!(clave1===clave2)){
			document.querySelector("#resultado").innerHTML = "Las contraseñas no coinciden";
			return;
		}
		if(rol===0)
		{
			document.querySelector("#resultado").innerHTML = "Seleccion de rol obligatoria";
			return;
			}
	}
</script>
</head>
<body>
	<form action="altaUsuario.html" method="post" id="formulario">


		<table border="1" align="center">
			<tr>
				<td>Nombre*</td>
				<td><input type="text" name="nombre" required></td>
			</tr>
			<tr>
				<td>Primer Apellido*</td>
				<td><input type="text" name="apellido1" required></td>
			</tr>
			<tr>
				<td>Segundo Apellido*</td>
				<td><input type="text" name="apellido2"  required></td>
			</tr>
			<tr>
				<td>DNI*</td>
				<td><input type="text" name="dni" required></td>
			</tr>
			<tr>
				<td>Fecha Nacimiento*</td>
				<td><input type="date" name="fechaNacimiento" value="01/01/2000" required></td>
			</tr>
			<tr>
				<td>Correo Electronico*</td>
				<td><input type="text" name="email" required></td>
			</tr>
			<tr>
				<td>Usuario*</td>
				<td><input type="text" name="usuario" required></td>
			</tr>
			<tr>
				<td>Clave*</td>
				<td><input type="password" name="clave" id="clave1" required></td>
			</tr>
			<tr>
				<td>Repetir clave*</td>
				<td><input type="password" id="clave2" required></td>
			</tr>
			
			<tr>
				<td>Rol</td>
				<td><select name="rol" id="rol">
						<option value="0">Selecciona rol....</option>
						<option value="1">Usuario</option>
						<option value="2">Administrador</option>
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
