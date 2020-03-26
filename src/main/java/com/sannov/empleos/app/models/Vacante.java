package com.sannov.empleos.app.models;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

//Indicamos que se hara el mapeo de esta clase a una tabla de la base de datos
@Entity
//Indicamos el nombre exactamente como esta en la tabla de base de datos
@Table(name="Vacantes")
public class Vacante 
{
	//indeicamos que esta variable sera la llave primaria
	@Id
	//Indicamos que sera AUTOINCREMENTABLE
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private String nombre;
	private String descripcion;
	private Date fecha;
	private Double salario;
	private Integer destacado;
	private String imagen="no-image.png";
	private String estatus;
	private String detalles;
	/*
	 * hacemos que al momento de mapear estas variables con los atributos de la base de datos
	 * IGNORE ESTA VARIABLE y NO LA MAPIE
	 * @Transient
	 */
	//Decimos que este atributo tiene relacion con otra tabla (es llave foranea)
	@OneToOne
	//Indicamos el nombre del atributo que es la llave foranea (de esta misma tabla de la BD)
	@JoinColumn(name = "idCategoria")
	private Categoria categoria;
	
	public Categoria getCategoria() {
		return categoria;
	}
	public void setCategoria(Categoria categoria) {
		this.categoria = categoria;
	}
	public String getEstatus() {
		return estatus;
	}
	public void setEstatus(String estatus) {
		this.estatus = estatus;
	}
	public String getDetalles() {
		return detalles;
	}
	public void setDetalles(String detalles) {
		this.detalles = detalles;
	}
	public String getImagen() {
		return imagen;
	}
	public void setImagen(String imagen) {
		this.imagen = imagen;
	}
	public Integer getDestacado() {
		return destacado;
	}
	public void setDestacado(Integer destacado) {
		this.destacado = destacado;
	}
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
	public Date getFecha() {
		return fecha;
	}
	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}
	public Double getSalario() {
		return salario;
	}
	public void setSalario(Double salario) {
		this.salario = salario;
	}
	@Override
	public String toString() {
		return "Vacante [id=" + id + ", nombre=" + nombre + ", descripcion=" + descripcion + ", fecha=" + fecha
				+ ", salario=" + salario + ", destacado=" + destacado + ", imagen=" + imagen + ", estatus=" + estatus
				+ ", detalles=" + detalles + ", categoria=" + categoria + "]";
	}
}
