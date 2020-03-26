package com.sannov.empleos.app.services.db;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.sannov.empleos.app.models.Solicitud;
import com.sannov.empleos.app.repository.SolicitudesRepository;
import com.sannov.empleos.app.services.ISolicitudesService;

@Service
public class SolicitudesServiceJpa implements ISolicitudesService {

	@Autowired
	private SolicitudesRepository repoSolicitudes;
	
	@Override
	public void guardar(Solicitud solicitud) 
	{
		repoSolicitudes.save(solicitud);
	}

	@Override
	public void eliminar(Integer idSolicitud) {
		repoSolicitudes.deleteById(idSolicitud);
	}

	@Override
	public List<Solicitud> buscarTodas() {
		return repoSolicitudes.findAll();
	}

	@Override
	public Solicitud buscarPorId(Integer idSolicitud) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Page<Solicitud> buscarTodas(Pageable page) {
		// TODO Auto-generated method stub
		return null;
	}	
}
