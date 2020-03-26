package com.sannov.empleos.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sannov.empleos.app.models.Categoria;

//Extendemos crud repository para poder ejecutar los metodos de un crud
//public interface CategoriasRepository extends CrudRepository<Categoria, Integer>
//Extendemos JpaRepository para tener metodos ADICIONALES a los que teniamos con crudReposotory
public interface CategoriasRepository extends JpaRepository<Categoria, Integer>
{
	
}
