package curso.trabajo.java.daos;

import java.security.GeneralSecurityException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Repository;

import curso.trabajo.java.beans.Login;
import curso.trabajo.java.beans.Usuario;
import curso.trabajo.java.excepciones.UsuarioNoExisteException;

@Repository
public class CursoDao {

	@Autowired
	private DataSource dataSource;

	public void altaUsuario(Usuario usuario, Login login) throws Exception {
		Connection conexion = getDataSource().getConnection();
		conexion.setAutoCommit(false);
		try {
			PreparedStatement pst = conexion
					.prepareStatement("insert into login (usuario,clave,enable) values (?,?,?)");
			pst.setString(1, login.getUsuario());
			BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
			String pwdEncriptado = encoder.encode(login.getClave());
			pst.setString(2, pwdEncriptado);
			pst.setInt(3, 0);
			pst.execute();

			Login loginBaseDatos = getLogin(login.getUsuario(),conexion);
			
			int idLogin = loginBaseDatos.getId();

			PreparedStatement pstUsuario = conexion.prepareStatement("insert into usuarios "
					+ "(nombre,apellido1,apellido2,dni,fechaNacimiento,email,idLogin) values (?,?,?,?,?,?,?)");
			pstUsuario.setString(1, usuario.getNombre());
			pstUsuario.setString(2, usuario.getApellido1());
			pstUsuario.setString(3, usuario.getApellido2());
			pstUsuario.setString(4, usuario.getDni());
			pstUsuario.setDate(5, usuario.getFechaNacimiento());
			pstUsuario.setString(6, usuario.getEmail());
			pstUsuario.setInt(7, idLogin);
			pstUsuario.execute();
			conexion.commit();
			enviaCorreo(usuario.getEmail());
			
		} catch (Exception e) {e.printStackTrace();
			conexion.rollback();
			throw e;
		}

	}

	public void modificaUsuario(Usuario usuario, Login login) throws Exception {
	}

	public Usuario getUsuario(String usuario) throws Exception {
		return null;
	}

	public Login getLogin(String usuario,String clave) throws Exception {
		Connection conexion= getDataSource().getConnection();
		Login login= getLogin(usuario, conexion);
		BCryptPasswordEncoder encoder= new BCryptPasswordEncoder();
		
		if(!encoder.matches(clave, login.getClave()))
			throw new UsuarioNoExisteException();
		return login;
	}
	
	public Login getLogin(String usuario,Connection conexion) throws Exception {
		
		PreparedStatement pst1 = conexion.prepareStatement("select id,usuario,clave,enable from login where usuario=?");
		pst1.setString(1, usuario);
		ResultSet rs = pst1.executeQuery();
		if (rs.next()) {
			Login login = new Login();
			login.setId(rs.getInt(1));
			login.setUsuario(rs.getString(2));
			login.setUsuario(rs.getString(2));
			login.setClave(rs.getString(3));
			login.setEnable(rs.getInt(4));

			return login;
		}
		throw new Exception();
	}
	
	

	public void bajaUsuario(String usuario) throws Exception {
		Connection conexion = getDataSource().getConnection();
		conexion.setAutoCommit(false);
		Statement statement= conexion.createStatement();
		ResultSet rs=statement.executeQuery("select l.id from login l,usuarios u where "
				+ "l.id=u.idLogin and u.usuario='"+usuario+"'");
		int id=0;
		if(rs.next())
			id=rs.getInt(1);
				
		
		try {
			statement.executeUpdate("delete from usuarios where idLogin="+id);
			statement.executeUpdate("delete from login where id="+id);
			conexion.commit();
		} catch (Exception e) {
			e.printStackTrace();
			conexion.rollback();
		}
		
		
	}

	public List<Usuario> getUsuarios() {
		return null;
	}

	public void enviaCorreo(String direccion) {

	}

	public DataSource getDataSource() {
		return dataSource;
	}

	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}

}
