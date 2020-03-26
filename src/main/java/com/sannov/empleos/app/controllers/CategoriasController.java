package com.sannov.empleos.app.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.sannov.empleos.app.models.Categoria;
import com.sannov.empleos.app.services.ICategoriasService;

@Controller
@RequestMapping(value="/categorias")
public class CategoriasController 
{
	/*
	 * Seleccionamos una de las varias clases que implementan a esta interfaz
	 * ponemos el nombre de la clase con la primera letra minuscula
	 * hace algo similar a PRIMARY
	 *	@Qualifier("categoriasServiceJPA")
	 */
	@Autowired
   	private ICategoriasService serviceCategorias;
	
	// @GetMapping("/index")
	@RequestMapping(value="/index", method=RequestMethod.GET)
	public String mostrarIndex(Model model) 
	{
		List<Categoria> lista = serviceCategorias.buscarTodas();
    	model.addAttribute("categorias", lista);
		return "categorias/listCategorias";
	}
	
	// @GetMapping("/create")
	@RequestMapping(value="/create", method=RequestMethod.GET)
	public String crear(Categoria categoria) 
	{
		return "categorias/formCategoria";
	}
	/*
	 * Con este mimso metodo editamos y guardamos una categoria
	 * Si en el requestparam no viene con algun valor, vamos a guardar, lo contrario, se modifica el registro
	 * Esta es la segunda foma que se puede hacer (La mia)
	 */
	// @PostMapping("/save")
	@RequestMapping(value="/save", method=RequestMethod.POST)
	public String guardar( @RequestParam("idCategoria") String idCategoria, Categoria categoria, BindingResult result, RedirectAttributes attributes ) 
	{
		if(idCategoria.equals(""))
		{
			idCategoria = "0";
		}
		System.out.println("El id es: " + idCategoria);
		categoria.setId( Integer.parseInt(idCategoria) );
		if (result.hasErrors())
		{		
			System.out.println("Existieron errores");
			return "categorias/formCategoria";
		}	
		// Guadamos el objeto categoria en la bd
		serviceCategorias.guardar(categoria);
		attributes.addFlashAttribute("msg", "Los datos de la categoria fueron guardados!");		
		return "redirect:/categorias/index";
	}
	/*
	 * Mostramos el form con la informacion a editar
	 */
	@GetMapping("/update/{id}")
	public String actualizar(@PathVariable("id") int idCategoria, Model model) 
	{
		model.addAttribute( "categoria", serviceCategorias.buscarPorId(idCategoria) );
		return "categorias/formCategoria";
	}
	/*
	 * Eliminamos la categoria recibiendo el ID
	 */
	@GetMapping("/delete")
	public String eliminar(@RequestParam("id") int id, RedirectAttributes attributes)
	{
		serviceCategorias.eliminarPorId(id);
		attributes.addFlashAttribute("msg", "La categoria se elimino existosamente");
		
		return "redirect:/categorias/index";
	}
}