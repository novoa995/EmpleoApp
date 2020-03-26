package com.sannov.empleos.app.services.db;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.sannov.empleos.app.models.Vacante;
import com.sannov.empleos.app.repository.VacantesRepository;
import com.sannov.empleos.app.services.IVacantesService;

@Service
@Primary
public class VacantesServiceJPA implements IVacantesService
{
	@Autowired
	private VacantesRepository vacantesRepo;
	
	@Override
	public List<Vacante> buscarTodas() {
		return vacantesRepo.findAll();
	}

	@Override
	public Vacante buscarPorId(Integer idVacante) {
		Optional<Vacante> op = vacantesRepo.findById(idVacante);
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
	public void guardar(Vacante vacante) {
		vacantesRepo.save(vacante);
	}

	@Override
	public List<Vacante> buscarDestacadas() {
		return vacantesRepo.findByDestacadoAndEstatusOrderByIdDesc(1, "Aprobada");
	}

	@Override
	public void eliminar(Integer idVacante) 
	{
		vacantesRepo.deleteById(idVacante);
	}
	/*
	 * Recibimos un objeto como ejemplo, que buscara registros en la base de datos
	 * con la informacion de los mismos atributos que el ejemplo
	 */
	@Override
	public List<Vacante> buscarByExample(Example<Vacante> example) {
		return vacantesRepo.findAll(example);
	}

	@Override
	public Page<Vacante> buscarTodas(Pageable page) {
		return vacantesRepo.findAll(page);
	}

}
