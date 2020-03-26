package com.sannov.empleos.app.controllers;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.sannov.empleos.app.models.Perfil;
import com.sannov.empleos.app.models.Usuario;
import com.sannov.empleos.app.models.Vacante;
import com.sannov.empleos.app.services.ICategoriasService;
import com.sannov.empleos.app.services.IUsuariosService;
import com.sannov.empleos.app.services.IVacantesService;

@Controller
public class HomeController 
{
	@Autowired
	private IVacantesService serviceVacantes;
	@Autowired
   	private IUsuariosService serviceUsuarios;
	@Autowired
	private ICategoriasService serviceCategorias;
	/*
	 * Inyectamos el la interfaz del metodo que se encuentra en nuestra clase DatabaseWebSecurity
	 * para poder encriptar las contraseñas de los usuarios que se registran
	 */
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	/*
	 * Mostramos la vista de login para que el usuario pueda iniciar sesion 
	 */
	@GetMapping("/login" )
	public String mostrarLogin() 
	{
		return "formLogin";
	}
	/*
	 * Si en el login hay errores
	 * que la contraseña y usuario no coinicdan
	 */
	@GetMapping("/loginError")
	public String loginError()
	{
		System.out.println("hubo un error en el login");
		return "formLogin";
	}
	/*
	 * Metodo para el boton salir (de la sesion) 
	 */
	@GetMapping("/logout")
	public String logout(HttpServletRequest request)
	{
		SecurityContextLogoutHandler logoutHandler = new SecurityContextLogoutHandler();
		//Pasamos como parametro el request, que se encarga de guardar la sesion. Y la destruimos (cerramos)
		logoutHandler.logout(request, null, null);
		return "redirect:/login";
	}
	/*
	 * Regresamos el form para que se REGISTRE UN NUEVO USUARIO
	 */
	@GetMapping("/signup")
	public String registrarse(Usuario usuario) {
		return "formRegistro";
	}
	/*
	 * Al presionar guardar del formulario
	 * GUARDAMOS EL NUEVO REGISTRO DE USUARIO
	 */
	@PostMapping("/signup")
	public String guardarRegistro(Usuario usuario, RedirectAttributes attributes) {
		usuario.setEstatus(1); // Activado por defecto
		usuario.setFechaRegistro(new Date()); // Fecha de Registro, la fecha actual del servidor
		
		// Creamos el Perfil que le asignaremos al usuario nuevo
		Perfil perfil = new Perfil();
		perfil.setId(3); // Perfil USUARIO
		usuario.agregar(perfil);
		/*
		 * ENCRIPTAMOS LA CONTRASEÑA 
		 */
		String pwdPlano = usuario.getPassword();
		String pwdEncriptado = passwordEncoder.encode(pwdPlano);
		usuario.setPassword(pwdEncriptado);
		/*
		 * Guardamos el usuario en la base de datos. El Perfil se guarda automaticamente
		 */
		serviceUsuarios.guardar(usuario);
				
		attributes.addFlashAttribute("msg", "El registro fue guardado correctamente!");
		
		return "redirect:/usuarios/index";
	}
	/*
	 * Con esta anotacion pasamos los modelos (variables)  a las vistas de TODOS
	 * LOS METODOS QUE ESTAN EN ESTE CONTROLADOR
	 */
	@ModelAttribute
	public void setGenericos(Model model)
	{
		model.addAttribute("vacantes", serviceVacantes.buscarDestacadas());
		// Mostramos el listado de categorias para cuando quieran hacer flitrado
		model.addAttribute("categorias", serviceCategorias.buscarTodas());
		
		Vacante vacanteSearch = new Vacante();
		vacanteSearch.setImagen(null);
		model.addAttribute("search", vacanteSearch);
	}
	
	@GetMapping("/")
	public String mostrarHome(Model model)
	{
		/*
		List<Vacante> lista = serviceVacantes.buscarDestacadas();
		model.addAttribute("vacantes", lista);
		 */
		return "home";
	}
	/*
	 * Mapeamos este metodo al boton de ingresar
	 * ya que esta URL esta protegida, automaticamente pedira que el usuario se registre
	 */
	@GetMapping("/index")
	public String mostrarIndex(Authentication auth, HttpSession session)
	{
		//obtenemos el nombre de la cuenta en ingreso 
		String username = auth.getName();
		System.out.println("Nombre: " + username);
		//Obtenemos los roles de la cuenta que ingreso
		for(GrantedAuthority rol : auth.getAuthorities())
		{
			System.out.println("Rol : " + rol.getAuthority());
		}
		
		//si la sesion no existe el usuario
		if(session.getAttribute("usuario") == null)
		{
			Usuario usuario = serviceUsuarios.buscarPorUsername(username);
			//pasamos null para que no este la contraseña en la session
			usuario.setPassword(null);
			System.out.println("Usuario: " + usuario);
			session.setAttribute("usuario", usuario);			
		}
		
		return "redirect:/";
	}
	
	@GetMapping("/listado")
	public String mostrarListado(Model model)
	{
		List<String> lista = new LinkedList<String>();
		lista.add("Ingeniero en Sistemas Computacionales");
		lista.add("Auxiliar de Contrabilidad");
		lista.add("Enfermero");
		
		model.addAttribute("empleos", lista);
		
		return "listado";
	}
	
	@GetMapping("/detalle")
	public String mostrarDetalle(Model model)
	{
		Vacante vacante = new Vacante();
		vacante.setNombre("Ingeniero en comunicaciones");
		vacante.setDescripcion("Se solicita ingeniero para dar sopoerte a redes");
		vacante.setFecha(new Date());
		vacante.setSalario(9700.0);
		
		model.addAttribute("vacante", vacante);
		
		return "detalle";
	}
	
	/*
	 * Metodo para mostrar en una tabla todas las vacantes disponibles
	 */
	@GetMapping("/tabla")
	public String mostrarTabla(Model model)
	{
		List<Vacante> lista = serviceVacantes.buscarTodas();
		model.addAttribute("vacantes", lista);
		
		return "tabla";
	}
	/*
	 * buscamos las vacantes foltradas por descripcion y categoria
	 */
	@GetMapping("/search")
	public String buscar(@ModelAttribute("search") Vacante vacante, Model model)
	{
		/*
		 * Hacemos que cuando se haga el filtrado (consulta), se consulte lo siguiente
		 *  where descripcion like '% ? %'
		 */
		ExampleMatcher matcher = ExampleMatcher.matching().withMatcher("descripcion", ExampleMatcher.GenericPropertyMatchers.contains());
		/*
		 * pasamos como vacante de ejemplo, la vacante que tiene solo la descipcion y categoria
		 * (todos los demas valores tienen que estar en null)
		 */
		Example<Vacante> example = Example.of(vacante, matcher);
		List<Vacante> lista = serviceVacantes.buscarByExample(example);
		model.addAttribute("vacantes", lista);
		
		return "home";
	}
	/*
	 * InitBinder para Strings si los detecta vacios en el Data Binding los settea null
	 */
	@InitBinder
	public void initBinder(WebDataBinder binder)
	{
		binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
	}
	/*
	 * Realizamos este metodo solo para ver como se encripta cada texto
	 *  en este caso pondremos la contraseñas de la base de datos que no estan encriptadas
	 *  para copiar la encriptacion y guardarlas en la base de datos 
	 *  @ResponseBody - Nos servi para NO RETORNAR una vista HTML, y solo retornar texto en pag vacia
	 */
	@GetMapping("/bcrypt/{texto}")
	@ResponseBody
	public String encriptar(@PathVariable("texto") String texto)
	{
		return texto + " encriptado en Bcript: " + passwordEncoder.encode(texto);
	}
}
