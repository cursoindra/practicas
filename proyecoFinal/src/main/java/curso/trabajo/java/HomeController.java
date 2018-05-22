package curso.trabajo.java;

import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

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

		return "home";
	}

	@RequestMapping(value = "alta.html")
	public String alta() {

		return "alta";
	}

	@RequestMapping("altaUsuario.html")
	public ModelAndView altaUsuario(@RequestParam("nombre") String nombre, @RequestParam("apellido1") String apellido1,
			@RequestParam("apellido2") String apellido2, @RequestParam("dni") String dni,
			@RequestParam("fechaNacimiento") String fechaNacimiento, @RequestParam("email") String email,
			@RequestParam("usuario") String usuario, @RequestParam("clave") String clave) {
		ModelAndView modelAndView = new ModelAndView("alta");
		try {
			getCursoDelegate().altaUsuario(nombre, apellido1, apellido2, dni, fechaNacimiento, email, usuario, clave);
			modelAndView.addObject("resultado", "Alta realizada correctamente");
		} catch (Exception e) {
			modelAndView.addObject("resultado", "Alta no realizada");
			e.printStackTrace();
		}
		return modelAndView;
	}
	
	@RequestMapping("login.html")
	public String login()
	{
		return "login";
	}
	

	@RequestMapping("verLogin.html")
	public ModelAndView verLogin(@RequestParam("usuario") String usuario, @RequestParam("clave") String clave) {

		ModelAndView modelAndView= new ModelAndView("login");
		
		try {
			getCursoService().getLogin(usuario, clave);
			modelAndView.addObject("resultado","LOGIN CORRECTO");
		} catch (Exception e) {
			e.printStackTrace();
			if(e.getClass().isInstance(new UsuarioNoExisteException()))
				modelAndView.addObject("resultado","USUARIO O CLAVE NO ES CORRECTO");
			else
				modelAndView.addObject("resultado","LO SENTIMOS, EN ESTE MOMENTO NO PODEMOS COMPROBAR SUS DATOS, INTENTELO MAS TARDE");
		}
		
		return modelAndView;
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
