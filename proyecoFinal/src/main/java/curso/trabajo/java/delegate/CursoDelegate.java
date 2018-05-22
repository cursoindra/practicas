package curso.trabajo.java.delegate;

import java.sql.Date;
import java.util.GregorianCalendar;
import java.util.StringTokenizer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.mysql.jdbc.log.Log;

import curso.trabajo.java.beans.Login;
import curso.trabajo.java.beans.Usuario;
import curso.trabajo.java.servicios.CursoService;

@Component
public class CursoDelegate {

	@Autowired
	private CursoService cursoService;

	public void altaUsuario(String nombre, String apellido1, String apellido2, String dni, String fechaNacimiento,
			String email, String user, String clave) throws Exception {
		Usuario usuario = new Usuario();
		usuario.setNombre(nombre);
		usuario.setApellido1(apellido1);
		usuario.setApellido2(apellido2);
		usuario.setDni(dni);
		usuario.setEmail(email);
		StringTokenizer token = new StringTokenizer(fechaNacimiento, "-");
		int ano = Integer.parseInt(token.nextToken());
		int mes = Integer.parseInt(token.nextToken());
		int dia = Integer.parseInt(token.nextToken());
		GregorianCalendar fecha = new GregorianCalendar(ano, mes - 1, dia);
		Date date = new Date(fecha.getTimeInMillis());
		usuario.setFechaNacimiento(date);
		Login login = new Login();
		login.setUsuario(user);
		login.setClave(clave);
		getCursoService().altaUsuario(usuario, login);
	}

	public CursoService getCursoService() {
		return cursoService;
	}

	public void setCursoService(CursoService cursoService) {
		this.cursoService = cursoService;
	}

}
