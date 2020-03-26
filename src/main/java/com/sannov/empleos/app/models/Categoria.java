package com.sannov.empleos.app.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

//Indicamos que se hara el mapeo de esta clase a una tabla de la base de datos
@Entity
//Indicamos el nombre exactamente como esta en la tabla de base de datos
@Table(name="Categorias")
public class Categoria {
	//indeicamos que esta variable sera la llave primaria
	@Id
	//Indicamos que sera AUTOINCREMENTABLE
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private String nombre;
	private String descripcion;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	@Override
	public String toString() {
		return "Categoria [id=" + id + ", nombre=" + nombre + ", descripcion=" + descripcion + "]";
	}

}
