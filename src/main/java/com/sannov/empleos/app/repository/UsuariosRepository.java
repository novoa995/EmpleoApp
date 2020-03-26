package com.sannov.empleos.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sannov.empleos.app.models.Usuario;

public interface UsuariosRepository extends JpaRepository<Usuario, Integer> {
	Usuario	findByUsername(String username);
}
