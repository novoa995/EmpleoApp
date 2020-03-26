package com.sannov.empleos.app.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sannov.empleos.app.models.Vacante;

public interface VacantesRepository extends JpaRepository<Vacante, Integer> 
{
	/*
	 * Buscamos por estatus
	 */
	List<Vacante> findByEstatus(String estatus);
	/*
	 * Buscamos por destacado y estatus ordanos por id descendente
	 */
	List<Vacante> findByDestacadoAndEstatusOrderByIdDesc(int destacado, String estatus);
	/*
	 * Buscamos vacantes entre un rango de salario
	 */
	List<Vacante> findBySalarioBetweenOrderBySalario(double salario1, double salario2);
	/*
	 * Buscamos vacantes por varios estatus
	 */
	List<Vacante> findByEstatusIn(String[] estatus);
}
