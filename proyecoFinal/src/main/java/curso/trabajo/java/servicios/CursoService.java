package curso.trabajo.java.servicios;

import java.sql.SQLException;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import curso.trabajo.java.beans.Login;
import curso.trabajo.java.beans.Rol;
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

	public void bajaUsuario(String usuario) throws Exception {
		getCursoDao().bajaUsuario(usuario);
		
	}

	public List<Login> getUsuarios() throws Exception {
	
		return getCursoDao().getUsuarios();
	}

	public Usuario getUsuario(int id) throws Exception {
		return getCursoDao().getUsuario(id);
		
	}

	public List<Rol> getRoles() throws SQLException {
		return getCursoDao().getRoles();
	}

	public int getUsuarioLogin(int id) throws Exception {
		// TODO Auto-generated method stub
		return getCursoDao().getUsuarioLogin(id);
	}

	public void actualizaUsuario(Usuario usuario,HttpSession session) throws Exception {
		getCursoDao().actualizaUsuario(usuario, session);
		
	}
}
