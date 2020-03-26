package com.sannov.empleos.app.controllers;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.sannov.empleos.app.models.Vacante;
import com.sannov.empleos.app.services.ICategoriasService;
import com.sannov.empleos.app.services.IVacantesService;
import com.sannov.empleos.app.util.Utileria;

@Controller
@RequestMapping("/vacantes")
public class VacantesController 
{
	/*
	 * Inyectamos el valor de la variable que se encuentra en nustro application.propierties
	 */
	@Value("${empleosapp.ruta.imagenes}")
	private String ruta;
	
	@Autowired
	private IVacantesService serviceVacantes;
	/*
	 * Seleccionamos una de las varias clases que implementan a esta interfaz
	 * ponemos el nombre de la clase con la primera letra minuscula
	 * hace algo similar a PRIMARY
	 *	@Qualifier("categoriasServiceJPA")
	 */
	@Autowired
	private ICategoriasService serviceCategorias;
	
	@GetMapping("/index")
	public String mostrarIndex(Model model) {
		List<Vacante> lista = serviceVacantes.buscarTodas();
		model.addAttribute("vacantes", lista);
		return "vacantes/listVacantes";
	}
	
	//Metodo para crear (publicar) una vacante
	@GetMapping("/create")
	//pasamos objeto de tipo vacante para asi poder saber si despues habra o no errores
	//a la hora guardar la informacion del form
	public String crear(Vacante vacante, Model model)
	{
		//Pasamos la lista de categorias para que se muestre en el select del form
		model.addAttribute("categorias", serviceCategorias.buscarTodas());
		return "vacantes/formVacante";
	}
	/*
	//Metodo para guardar o modificar una vacante
	 *	Si en el binding result, el id esta vacio, va a guardar, lo contrario, va a modificar registro 
	 */
	@PostMapping("/save")
	public String guardar(Vacante vacante, BindingResult result, RedirectAttributes attributes, 
			//recibimos lo que tiene guardado el input "archivoImagen"
			@RequestParam("archivoImagen") MultipartFile multiPart )
	{
		if(result.hasErrors())
		{
			for (ObjectError error: result.getAllErrors())
			{
				System.out.println("Ocurrio un error: " + error.getDefaultMessage());
			}
			return "vacantes/formVacante";
		}
		if (!multiPart.isEmpty()) 
		{
			//String ruta = "c:/empleos/img-vacantes/"; // Windows
			String nombreImagen = Utileria.guardarArchivo(multiPart, ruta);
			if (nombreImagen != null)
			{ // La imagen si se subio
				// Procesamos la variable nombreImagen
				vacante.setImagen(nombreImagen);
			}
		}

		System.out.println(vacante);
		serviceVacantes.guardar(vacante);
		
		//pasamos la variable msg a la peticion (metodo) del redirect
		attributes.addFlashAttribute("msg", "Registro guardado");
		
		//mandamos a llamar el metodo con la direccion /vacantes/index
		return "redirect:/vacantes/index";
	}
	
	//Sirve para cuando usamos databinder cuando recibimos datos de fecha
	//con este metodo despecificamos que la fecha la tiene que transofmar a dicho formato
	@InitBinder
	public void initBinder(WebDataBinder webDataBinder) 
	{
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
		webDataBinder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, false));
	}

	//al poner {id} indicamos que ese parametro sera dinamico
	@GetMapping("/view/{id}")
	//obtenemos la variable que viene en la URL (id)
	public String verDetalle( @PathVariable("id") int idVacante, Model model )
	{
		Vacante vacante = serviceVacantes.buscarPorId(idVacante);
		model.addAttribute("vacante", vacante);
		
		return "detalle";
	}
	
	@GetMapping("/delete")
	//obtenemos la variable id por medio de la url = /vacantes/delete?id=5
	public String eliminar( @RequestParam("id") int idVacante, RedirectAttributes attributes )
	{
		System.out.println("Borrando vacante con id: " + idVacante);
		serviceVacantes.eliminar(idVacante);
		attributes.addFlashAttribute("msg", "La vacante fue eliminada");
		
		return "redirect:/vacantes/index";
	}
	/*
	 * Metodo para editar un registro de Vacantes
	 */
	@GetMapping("/edit/{id}")
	public String editar(@PathVariable("id") int idVacante, Model model)
	{
		//obtenemos la informacion del registro con el id que obtenemos
		Vacante vacante = serviceVacantes.buscarPorId(idVacante);
		model.addAttribute("vacante", vacante);
		//Pasamos la lista de categorias para que se muestre en el select del form
		model.addAttribute("categorias", serviceCategorias.buscarTodas());
		return "vacantes/formVacante";
	}
	/*
	 * Mostramos un index de la lista de vacantes pero con paginacion
	 * en el archivo propierties indicamos cuantas vacantes por pagina
	 */
	@GetMapping(value = "/indexPaginate")
	public String mostrarIndexPaginado(Model model, Pageable page) 
	{
		Page<Vacante> lista = serviceVacantes.buscarTodas(page);
		model.addAttribute("vacantes", lista);

		return "vacantes/listVacantes";
	}

}
 