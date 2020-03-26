package com.sannov.empleos.app.controllers;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.sannov.empleos.app.models.Solicitud;
import com.sannov.empleos.app.models.Usuario;
import com.sannov.empleos.app.models.Vacante;
import com.sannov.empleos.app.services.ISolicitudesService;
import com.sannov.empleos.app.services.IUsuariosService;
import com.sannov.empleos.app.services.IVacantesService;
import com.sannov.empleos.app.util.Utileria;

@Controller
@RequestMapping("/solicitudes")
public class SolicitudesController 
{
	@Autowired
	private IVacantesService serviceVac;
	@Autowired
	private IUsuariosService serviceUser;
	@Autowired
	private ISolicitudesService serviceSolicitud;
	
	/**
	 * EJERCICIO: Declarar esta propiedad en el archivo application.properties. El valor sera el directorio
	 * en donde se guardarán los archivos de los Curriculums Vitaes de los usuarios.
	 */
	@Value("${empleosapp.ruta.cv}")
	private String ruta;
		
    /*
	 * Metodo que muestra la lista de solicitudes sin paginacion
	 * Seguridad: Solo disponible para un usuarios con perfil ADMINISTRADOR/SUPERVISOR.
	 * @return
	 */
    @GetMapping("/index") 
	public String mostrarIndex(Model model) 
    {
    	
    	List<Solicitud> solicitudes = serviceSolicitud.buscarTodas();
    	model.addAttribute("solicitudes", solicitudes);
    	
		return "solicitudes/listSolicitudes";	
	}
    
    /**
	 * Metodo que muestra la lista de solicitudes con paginacion
	 * Seguridad: Solo disponible para usuarios con perfil ADMINISTRADOR/SUPERVISOR.
	 * @return
	 */
	@GetMapping("/indexPaginate")
	public String mostrarIndexPaginado() {
		
		// EJERCICIO
		return "solicitudes/listSolicitudes";
	}
    
	/**
	 * Método para renderizar el formulario para aplicar para una Vacante
	 * Seguridad: Solo disponible para un usuario con perfil USUARIO.
	 * @return
	 */
	@GetMapping("/create/{idVacante}")
	public String crear(@PathVariable("idVacante") int idVacante, Model model) 
	{
		Vacante vacante = serviceVac.buscarPorId(idVacante);
		model.addAttribute("vacante", vacante);
		return "solicitudes/formSolicitud";
	}
	
	/**
	 * Método que guarda la solicitud enviada por el usuario en la base de datos
	 * Seguridad: Solo disponible para un usuario con perfil USUARIO.
	 * @return
	 * 
	 * Guardamos la solicitud de la vacante
	 * Recibimos
	 * Solicitud - para recbibir los datos del formulario (comentarios)
	 * MultiparFile - Para recibir el archivo PDF
	 * @RequesParam("idVacante") - Recibimos el id de al vacante pr medio de un input oculto
	 * Authentication - Para poder obtener el nombre del usuario que inicio session y asi poder obtener su ID
	 */
	@PostMapping("/save")
	public String guardar(Solicitud solicitud, @RequestParam("archivoCV") MultipartFile multiPart, 
			@RequestParam("idVacante") int idVacante, Authentication auth,  RedirectAttributes attributes) 
	{	
		/*
		 * Metodo para poder guardar el CV en nuestra carpeta
		 * y poder guardar el nombre del archivo en la base de datos
		 */
		if (!multiPart.isEmpty()) 
		{
			String nombreCV = Utileria.guardarArchivo(multiPart, this.ruta);
			if (nombreCV != null){ // La imagen si se subio
				// Procesamos la variable nombreImagen
				solicitud.setArchivo(nombreCV);
			}
		}
		//Seteamos la fecha del dia de HOY
		solicitud.setFecha(new Date());
		/*
		 * Conseguimos el username de la session actual
		 * Para poder hacer una consulta y traer toda la informacion de ese usuario
		 */
		String username = auth.getName();
		Usuario userSesion = serviceUser.buscarPorUsername(username);
		solicitud.setUsuario(userSesion);
		/*
		 * Conseguimos el id de la vacante
		 * lo conseguimos de un input oculto
		 */
		Vacante vacante = serviceVac.buscarPorId(idVacante);
		solicitud.setVacante(vacante);
		
		System.out.println(solicitud);
		/*
		 * Una vez que tenemos todos los atributos de la tabla Solicutud
		 * la GUARDAMOS a la base de datos
		 * y MOSTRAMOS UN MENSAJE  de que se guardo exitosamente
		 */
		serviceSolicitud.guardar(solicitud);
		attributes.addFlashAttribute("mensaje", "¡Tu solicitud ha sido enviada con exito!. Sigue buscando tu trabajo ideal.");
		
		return "redirect:/";			
	}
	
	/**
	 * Método para eliminar una solicitud
	 * Seguridad: Solo disponible para usuarios con perfil ADMINISTRADOR/SUPERVISOR. 
	 * @return
	 */
	@GetMapping("/delete/{id}")
	public String eliminar(@PathVariable("id") int idSolicitud, RedirectAttributes attr) 
	{
		serviceSolicitud.eliminar(idSolicitud);
		attr.addFlashAttribute("mensaje", "¡La solicitud se ha eliminado con exito!");
		
		return "redirect:/solicitudes/index";	
	}		
	/**
	 * Personalizamos el Data Binding para todas las propiedades de tipo Date
	 * @param webDataBinder
	 */
	@InitBinder
	public void initBinder(WebDataBinder webDataBinder) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
		webDataBinder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
	}
}
