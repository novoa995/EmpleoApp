package com.sannov.empleos.app.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.sannov.empleos.app.models.Usuario;
import com.sannov.empleos.app.services.IUsuariosService;

@Controller
@RequestMapping("/usuarios")
public class UsuariosController {

    @Autowired
	private IUsuariosService serviceUsuarios;
    
   @GetMapping("/index")
	public String mostrarIndex(Model model) {
    	List<Usuario> lista = serviceUsuarios.buscarTodos();
    	model.addAttribute("usuarios", lista);
		return "usuarios/listUsuarios";
	}
    
    @GetMapping("/delete/{id}")
	public String eliminar(@PathVariable("id") int idUsuario, RedirectAttributes attributes) {		    	
		// Eliminamos el usuario
    	serviceUsuarios.eliminar(idUsuario);			
		attributes.addFlashAttribute("msg", "El usuario fue eliminado!.");
		return "redirect:/usuarios/index";
	}
    /*
     * Bloqueamos el acceso al sistema al usuario
     * modificamos a 0 el valor de su atributo estatus
     */
    @GetMapping("/lock/{id}")
    public String bloquear(@PathVariable("id") int idUsuario, RedirectAttributes at)
    {
    	Usuario usuario = serviceUsuarios.buscarPorId(idUsuario);
    	//Bloqueamos al usuario pasandole 0 a su estatus
    	usuario.setEstatus(0);
    	serviceUsuarios.guardar(usuario);
    	at.addFlashAttribute("msg", "El usuario "+usuario.getUsername()+" se ha bloqueado exitosamente. Ya no tendra acceso al sistema.");
    	
    	return "redirect:/usuarios/index";
    }
    /*
     * Desbloqueamos el acceso al sistema al usuario
     * modificamos a 1 el valor de su atributo estatus
     */
    @GetMapping("/unlock/{id}")
    public String desbloquear(@PathVariable("id") int idUsuario, RedirectAttributes at)
    {
    	Usuario usuario = serviceUsuarios.buscarPorId(idUsuario);
    	//desbloqueamos al usuario pasandole 1 a su estatus
    	usuario.setEstatus(1);
    	serviceUsuarios.guardar(usuario);
    	at.addFlashAttribute("msg", "El usuario "+usuario.getUsername()+" ha sido desbloqueado exitosamente. Tendra acceso al sistema.");
    	
    	return "redirect:/usuarios/index";
    }
}
