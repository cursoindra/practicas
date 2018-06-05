package curso.trabajo.java;

// esto es de prueba
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import curso.trabajo.java.beans.Login;
import curso.trabajo.java.beans.Rol;
import curso.trabajo.java.beans.Usuario;
import curso.trabajo.java.delegate.CursoDelegate;
import curso.trabajo.java.excepciones.UsuarioNoExisteException;
import curso.trabajo.java.servicios.CursoService;

/**
 * Handles requests for the application home page.
 */
@Controller
public class HomeController {

	@Autowired
	private CursoDelegate cursoDelegate;
	@Autowired
	private CursoService cursoService;

	/**
	 * Simply selects the home view to render by returning its name.
	 */
	@RequestMapping(value = "/")
	public String home(Locale locale, Model model) {

		return "login";
	}

	@RequestMapping(value = "alta.html")
	public String alta() {

		return "alta";
	}
	
	@RequestMapping("grabaModificaUsuario.html")
	public String grabaModificaUsuario(@RequestParam("nombre") String nombre, @RequestParam("apellido1") String apellido1,
			@RequestParam("apellido2") String apellido2, @RequestParam("dni") String dni,
			@RequestParam("fechaNacimiento") String fechaNacimiento, @RequestParam("email") String email,
			 @RequestParam("rol") int rol,
			HttpSession session) {
		try {
			getCursoDelegate().actualizaUsuario(nombre,apellido1,apellido2,dni,fechaNacimiento,email,rol,session);
			session.setAttribute("usuario", getCursoService().getUsuario(((Usuario)session.getAttribute("usuario")).getLogin()));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return login(session);
		
	}

	@RequestMapping("altaUsuario.html")
	public ModelAndView altaUsuario(@RequestParam("nombre") String nombre, @RequestParam("apellido1") String apellido1,
			@RequestParam("apellido2") String apellido2, @RequestParam("dni") String dni,
			@RequestParam("fechaNacimiento") String fechaNacimiento, @RequestParam("email") String email,
			@RequestParam("usuario") String usuario, @RequestParam("clave") String clave, @RequestParam("rol") int rol,
			HttpSession session) {
		ModelAndView modelAndView = new ModelAndView("alta");
		try {
			if (((Integer) session.getAttribute("admin")) != 2) {
				modelAndView.addObject("resultado", "No Autorizado");
			}
			getCursoDelegate().altaUsuario(nombre, apellido1, apellido2, dni, fechaNacimiento, email, usuario, clave,
					rol);
			modelAndView.addObject("resultado", "Alta realizada correctamente");
		} catch (Exception e) {
			modelAndView.addObject("resultado", "Alta no realizada");
			e.printStackTrace();
		}
		return modelAndView;
	}

	@RequestMapping("login.html")
	public String login(HttpSession session) {
		if ((Integer) session.getAttribute("admin") != null) {
			return "admin";
		}
		if ((Integer) session.getAttribute("user") != null) {
			return "user";
		}
		
		return "login";
	}

	@RequestMapping(value = "verLogin.html", method = RequestMethod.POST)
	public ModelAndView verLogin(@RequestParam("usuario") String usuario, @RequestParam("clave") String clave,
			HttpSession session) {
		ModelAndView modelAndView = new ModelAndView();
		Login login=null;
		try {
			 login = getCursoService().getLogin(usuario, clave);
			int rol = getCursoService().getUsuarioLogin(login.getId());
			Usuario user=getCursoService().getUsuario(login.getId());
			if (rol == 1) {
				session.setAttribute("user", 1);
				modelAndView.setViewName("user");
			} else
			{
				session.setAttribute("admin", 1);
				modelAndView.setViewName("admin");
			}
			session.setAttribute("usuario", user);
			session.setAttribute("login", login);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			modelAndView.addObject("resultado", "USUARIO O CLAVE ERRONEO");
			modelAndView.setViewName("login");
			e.printStackTrace();

		}
		
		return modelAndView;
	}

	@RequestMapping("verLogin.html")
	public ModelAndView verLogin(@RequestParam("usuario") String usuario, @RequestParam("clave") String clave) {

		ModelAndView modelAndView = new ModelAndView("login");

		try {
			getCursoService().getLogin(usuario, clave);
			modelAndView.addObject("resultado", "LOGIN CORRECTO");
		} catch (Exception e) {
			e.printStackTrace();
			if (e.getClass().isInstance(new UsuarioNoExisteException()))
				modelAndView.addObject("resultado", "USUARIO O CLAVE NO ES CORRECTO");
			else
				modelAndView.addObject("resultado",
						"LO SENTIMOS, EN ESTE MOMENTO NO PODEMOS COMPROBAR SUS DATOS, INTENTELO MAS TARDE");
		}

		return modelAndView;
	}

	@RequestMapping(value = "baja.html")
	public String baja() {

		return "baja";
	}

	@RequestMapping(value = "bajaUsuario.html",method=RequestMethod.POST)
	public ModelAndView bajaUsuario(@RequestParam("usuario") String usuario) {

		ModelAndView modelAndView = new ModelAndView("login");
		try {
			getCursoService().bajaUsuario(usuario);
			modelAndView.addObject("resultado", "BAJA REALIZADA");
		} catch (Exception e) {
			e.printStackTrace();
			if (e.getClass().isInstance(new UsuarioNoExisteException()))
				modelAndView.addObject("resultado", "USUARIO O CLAVE NO ES CORRECTO");
			else
				modelAndView.addObject("resultado", " BAJA NO REALIZADA");

		}
		return modelAndView;
	}

	@RequestMapping("modificacion.html")
	public ModelAndView modificacion(HttpSession session) {
		ModelAndView modelAndView = new ModelAndView("modificacion");

		try {
			List<Rol> roles = getCursoService().getRoles();
			modelAndView.addObject("roles", roles);
			
		} catch (Exception e) {
			modelAndView.addObject("resultado", "NO SE PUEDE RECUPERAR LA INFORMACION");
			e.printStackTrace();
		}
		return modelAndView;
	}

	@RequestMapping("verUsuario.html")
	public ModelAndView verUsuario(HttpSession session) {
		ModelAndView modelAndView = new ModelAndView("modificacion");

		try {
			Usuario usuario = getCursoService().getUsuario(((Usuario)session.getAttribute("usuario")).getLogin());
			modelAndView.addObject("usuario", usuario);
			List<Rol> roles = getCursoService().getRoles();
			modelAndView.addObject("roles", roles);
		} catch (Exception e) {
			modelAndView.addObject("resultado", "NO SE PUEDE RECUPERAR LA INFORMACION");
			e.printStackTrace();
		}

		return modelAndView;
	}

	@RequestMapping("adios.html")
	public String adios(HttpSession session)
	{
		session.invalidate();
		return "login";
	}
	public CursoDelegate getCursoDelegate() {
		return cursoDelegate;
	}

	public void setCursoDelegate(CursoDelegate cursoDelegate) {
		this.cursoDelegate = cursoDelegate;
	}

	public CursoService getCursoService() {
		return cursoService;
	}

	public void setCursoService(CursoService cursoService) {
		this.cursoService = cursoService;
	}

}
