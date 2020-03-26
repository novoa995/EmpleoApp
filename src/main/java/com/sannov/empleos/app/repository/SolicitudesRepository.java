package com.sannov.empleos.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sannov.empleos.app.models.Solicitud;

public interface SolicitudesRepository extends JpaRepository<Solicitud, Integer> 
{

}
