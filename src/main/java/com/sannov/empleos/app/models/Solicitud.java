/**
 * Esta clase representa una entidad (un registro) en la tabla de Solicitudes de la base de datos
 */
package com.sannov.empleos.app.models;

import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "Solicitudes")
public class Solicitud {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY) // auto_increment MySQL
	private Integer id;
	private Date fecha; // Fecha en que aplico el usuario para esta oferta de trabajo
	private String comentarios;
	private String archivo; // El nombre del archivo PDF, DOCX del CV.
	//Decimos que este atributo tiene relacion con otra tabla (es llave foranea)
	@OneToOne
	//Indicamos el nombre del atributo que es la llave foranea (de esta misma tabla de la BD)
	@JoinColumn(name = "idVacante")
	private Vacante vacante;
	//Decimos que este atributo tiene relacion con otra tabla (es llave foranea)
	@OneToOne
	//Indicamos el nombre del atributo que es la llave foranea (de esta misma tabla de la BD)
	@JoinColumn(name = "idUsuario")
	private Usuario usuario;

	public Solicitud() {

	}

	public Solicitud(Date fecha) {
		this.fecha = new Date();
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public String getArchivo() {
		return archivo;
	}

	public void setArchivo(String archivo) {
		this.archivo = archivo;
	}

	public Vacante getVacante() {
		return vacante;
	}

	public void setVacante(Vacante vacante) {
		this.vacante = vacante;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public String getComentarios() {
		return comentarios;
	}

	public void setComentarios(String comentarios) {
		this.comentarios = comentarios;
	}

	@Override
	public String toString() {
		return "Solicitud [id=" + id + ", fecha=" + fecha + ", comentarios=" + comentarios + ", archivo=" + archivo
				+ ", vacante=" + vacante + ", usuario=" + usuario + "]";
	}

}
