package com.sannov.empleos.app.services;

import java.util.List;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.sannov.empleos.app.models.Vacante;

public interface IVacantesService {
	List<Vacante> buscarTodas();
	Vacante buscarPorId(Integer idVacante);
	void guardar(Vacante vacante);
	List<Vacante> buscarDestacadas();
	void eliminar(Integer idVacante);
	/*
	 * Recibimos un objeto como ejemplo, que buscara registros en la base de datos
	 * con la informacion de los mismos atributos que el ejemplo
	 */
	List<Vacante> buscarByExample(Example<Vacante> example);
	/*
	 * Devolvemos todas las vacantes pero paginadas
	 */
	Page<Vacante> buscarTodas(Pageable page);
}
