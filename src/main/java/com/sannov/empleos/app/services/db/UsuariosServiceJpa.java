package com.sannov.empleos.app.services.db;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sannov.empleos.app.models.Usuario;
import com.sannov.empleos.app.repository.UsuariosRepository;
import com.sannov.empleos.app.services.IUsuariosService;

@Service
public class UsuariosServiceJpa implements IUsuariosService {

	@Autowired
	private UsuariosRepository usuariosRepo;
	
	public void guardar(com.sannov.empleos.app.models.Usuario usuario) {
		usuariosRepo.save(usuario);
	}

	public void eliminar(Integer idUsuario) {
		usuariosRepo.deleteById(idUsuario);
	}

	public List<Usuario> buscarTodos() {
		return usuariosRepo.findAll();
	}

	@Override
	public Usuario buscarPorUsername(String username) {
		return usuariosRepo.findByUsername(username);
	}

	@Override
	public Usuario buscarPorId(Integer idUsuario) {
		Optional<Usuario> usuario = usuariosRepo.findById(idUsuario);
		
		if(usuario.isPresent())
			return usuario.get();
		else
			return null;
	}

}
