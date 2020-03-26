package com.sannov.empleos.app.services.db;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import com.sannov.empleos.app.models.Categoria;
import com.sannov.empleos.app.repository.CategoriasRepository;
import com.sannov.empleos.app.services.ICategoriasService;
@Service
/*
 * Hacemos que al momento de querer una instancia a una variable
 * tome la instancia de ESTA CLASE Y NO DE OTRA CLASE DE SERVICIO
 */
@Primary
public class CategoriasServiceJPA implements ICategoriasService {

	@Autowired
	private CategoriasRepository categoriasRepo;
	
	@Override
	public void guardar(Categoria categoria) {
		categoriasRepo.save(categoria);
	}

	@Override
	public List<Categoria> buscarTodas() {
		return categoriasRepo.findAll();
	}

	@Override
	public Categoria buscarPorId(Integer idCategoria) {
		Optional<Categoria> op = categoriasRepo.findById(idCategoria);
		if(op.isPresent())
		{
			return op.get();
		}
		else
		{
			return null;			
		}
	}

	@Override
	public void eliminarPorId(int idCategoria) 
	{
		categoriasRepo.deleteById(idCategoria);
	}
}
