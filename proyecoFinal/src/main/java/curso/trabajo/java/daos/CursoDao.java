package curso.trabajo.java.daos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;
import javax.sql.DataSource;
import javax.sql.rowset.spi.TransactionalWriter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Repository;

import curso.trabajo.java.beans.Login;
import curso.trabajo.java.beans.Rol;
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
			PreparedStatement pst = conexion.prepareStatement("insert into login (usuario,clave) values (?,?)");
			pst.setString(1, login.getUsuario());
			BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
			String pwdEncriptado = encoder.encode(login.getClave());
			pst.setString(2, pwdEncriptado);
			pst.execute();

			Login loginBaseDatos = getLogin(login.getUsuario(), conexion);

			int idLogin = loginBaseDatos.getId();

			PreparedStatement pstUsuario = conexion.prepareStatement("insert into usuarios "
					+ "(nombre,apellido1,apellido2,dni,fechaNacimiento,email,idLogin,idRol) values (?,?,?,?,?,?,?,?)");
			pstUsuario.setString(1, usuario.getNombre());
			pstUsuario.setString(2, usuario.getApellido1());
			pstUsuario.setString(3, usuario.getApellido2());
			pstUsuario.setString(4, usuario.getDni());
			pstUsuario.setDate(5, usuario.getFechaNacimiento());
			pstUsuario.setString(6, usuario.getEmail());
			pstUsuario.setInt(7, idLogin);
			pstUsuario.setInt(8, usuario.getRol());
			pstUsuario.execute();
			conexion.commit();

		} catch (Exception e) {
			e.printStackTrace();
			conexion.rollback();
			throw e;
		}

	}

	
	public Usuario getUsuario(int id) throws Exception {
		Connection conexion = getDataSource().getConnection();
		Usuario resultado = new Usuario();
		Statement statement = conexion.createStatement();
		ResultSet resultSet = statement.executeQuery(
				"select nombre,apellido1,apellido2,dni,fechaNacimiento,email,idRol from usuarios where idLogin=" + id);
		try {
			if (resultSet.next()) {
				resultado.setNombre(resultSet.getString(1));
				resultado.setApellido1(resultSet.getString(2));
				resultado.setApellido2(resultSet.getString(3));
				resultado.setDni(resultSet.getString(4));
				resultado.setFechaNacimiento(resultSet.getDate(5));
				resultado.setEmail(resultSet.getString(6));
				resultado.setRol(resultSet.getInt(7));
				resultado.setLogin(id);
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw e;
		}
		return resultado;
	}

	public Login getLogin(String usuario, String clave) throws Exception {
		Connection conexion = getDataSource().getConnection();
		Login login = getLogin(usuario, conexion);
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

		if (!encoder.matches(clave, login.getClave()))
			throw new UsuarioNoExisteException();
		return login;
	}

	public Login getLogin(String usuario, Connection conexion) throws Exception {

		PreparedStatement pst1 = conexion.prepareStatement("select id,usuario,clave from login where usuario=?");
		pst1.setString(1, usuario);
		ResultSet rs = pst1.executeQuery();
		if (rs.next()) {
			Login login = new Login();
			login.setId(rs.getInt(1));
			login.setUsuario(rs.getString(2));
			login.setClave(rs.getString(3));
			return login;
		}
		throw new Exception();
	}

	public void bajaUsuario(String usuario) throws Exception {
		Connection conexion = getDataSource().getConnection();
		conexion.setAutoCommit(false);
		Statement statement = conexion.createStatement();
		ResultSet rs = statement.executeQuery(
				"select l.id from login l,usuarios u where " + "l.id=u.idLogin and l.usuario='" + usuario + "'");
		int id = 0;
		if (rs.next())
			id = rs.getInt(1);
		else
			throw new Exception();

		try {
			statement.executeUpdate("delete from usuarios where idLogin=" + id);
			statement.executeUpdate("delete from login where id=" + id);
			conexion.commit();
		} catch (Exception e) {
			e.printStackTrace();
			conexion.rollback();
		}

	}

	public List<Login> getUsuarios() throws Exception {
		Connection conexion = getDataSource().getConnection();
		List<Login> resultado = new ArrayList<Login>();
		Statement statement = conexion.createStatement();
		ResultSet resultSet = statement.executeQuery("select id,usuario from login order by usuario");
		while (resultSet.next()) {
			Login login = new Login();
			login.setId(resultSet.getInt(1));
			login.setUsuario(resultSet.getString(2));
			resultado.add(login);
		}

		return resultado;
	}

	public List<Rol> getRoles() throws SQLException {
		Connection conexion = getDataSource().getConnection();
		List<Rol> roles = new ArrayList<Rol>();
		Statement statement = conexion.createStatement();
		ResultSet resultSet = statement.executeQuery("select id, descripcion from roles");
		while (resultSet.next()) {
			Rol rol = new Rol();
			rol.setId(resultSet.getInt(1));
			rol.setRol(resultSet.getString(2));
			roles.add(rol);
		}
		return roles;
	}

	public int getUsuarioLogin(int id) throws Exception {
		Connection conexion = getDataSource().getConnection();
		Statement statement = conexion.createStatement();
		ResultSet resultSet = statement.executeQuery("select idRol from usuarios where idLogin=" + id);
		if (resultSet.next())
			return resultSet.getInt(1);
		throw new Exception();
	}

	public void actualizaUsuario(Usuario usuario, HttpSession session) throws Exception {
		Connection conexion = getDataSource().getConnection();
		PreparedStatement preparedStatement = conexion.prepareStatement(
				"update usuarios set nombre=?, apellido1=?,apellido2=?,dni=?,email=?,idRol=? where idLogin=?");
		try {
			preparedStatement.setString(1, usuario.getNombre());
			preparedStatement.setString(2, usuario.getApellido1());
			preparedStatement.setString(3, usuario.getApellido2());
			preparedStatement.setString(4, usuario.getDni());
			preparedStatement.setString(5, usuario.getEmail());
			preparedStatement.setInt(6, usuario.getRol());
			preparedStatement.setInt(7, ((Usuario)session.getAttribute("usuario")).getLogin());
			preparedStatement.execute();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw e;
		}

	}

	public DataSource getDataSource() {
		return dataSource;
	}

	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}

}
