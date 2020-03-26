package com.sannov.empleos.app.services;

import java.util.List;

import com.sannov.empleos.app.models.Categoria;

public interface ICategoriasService {
	void guardar(Categoria categoria);
	List<Categoria> buscarTodas();
	Categoria buscarPorId(Integer idCategoria);
	void eliminarPorId(int idCategoria);
}