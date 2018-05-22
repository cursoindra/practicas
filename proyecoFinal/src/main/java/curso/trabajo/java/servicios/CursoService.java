package curso.trabajo.java.servicios;

import java.lang.reflect.Method;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import curso.trabajo.java.beans.Login;
import curso.trabajo.java.beans.Usuario;
import curso.trabajo.java.daos.CursoDao;

@Service
public class CursoService {
	
	@Autowired
	private CursoDao cursoDao;

	
	public void altaUsuario(Usuario usuario, Login login) throws Exception {
		getCursoDao().altaUsuario(usuario, login);
	
	}
	
	public Login getLogin(String usuario,String clave) throws Exception {
		return getCursoDao().getLogin(usuario, clave);
	}
	public CursoDao getCursoDao() {
		return cursoDao;
	}

	public void setCursoDao(CursoDao cursoDao) {
		this.cursoDao = cursoDao;
	}
}
