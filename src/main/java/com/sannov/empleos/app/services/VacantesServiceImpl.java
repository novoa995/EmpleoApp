package com.sannov.empleos.app.services;

import java.text.SimpleDateFormat;
import java.util.LinkedList;
import java.util.List;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.sannov.empleos.app.models.Vacante;

@Service
public class VacantesServiceImpl implements IVacantesService
{
	private List<Vacante> lista = null;
	
	public VacantesServiceImpl() 
	{
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
		lista = new LinkedList<Vacante>();
		try 
		{
			Vacante vacante1 = new Vacante();
			vacante1.setId(1);
			vacante1.setNombre("Ingeniero Civil");
			vacante1.setDescripcion("Trabajo para diseñar puente");
			vacante1.setFecha(sdf.parse("09-06-2200"));
			vacante1.setSalario(8500.60);
			vacante1.setDestacado(1);
			vacante1.setImagen("logo1.png");
			
			Vacante vacante2 = new Vacante();
			vacante2.setId(2);
			vacante2.setNombre("Contador publico");
			vacante2.setDescripcion("Empresa importante solicita contador");
			vacante2.setFecha(sdf.parse("19-08-2050"));
			vacante2.setSalario(6505.50);
			vacante2.setDestacado(0);
			vacante2.setImagen("logo2.png");
			
			Vacante vacante3 = new Vacante();
			vacante3.setId(3);
			vacante3.setNombre("Ingeniero Electrico");
			vacante3.setDescripcion("Para que repare la luz");
			vacante3.setFecha(sdf.parse("10-12-2070"));
			vacante3.setSalario(10952.14);
			vacante3.setDestacado(1);
			
			Vacante vacante4 = new Vacante();
			vacante4.setId(4);
			vacante4.setNombre("Diseador grafico");
			vacante4.setDescripcion("Para que diseñe");
			vacante4.setFecha(sdf.parse("30-01-9970"));
			vacante4.setSalario(10952.14);
			vacante4.setDestacado(0);
			vacante4.setImagen("logo3.png");
			
			/*
			 * Agregamos los objetos vacantes a la lista
			 */
			lista.add(vacante1);
			lista.add(vacante2);
			lista.add(vacante3);
			lista.add(vacante4);
		}
		catch (Exception e) 
		{
			System.out.println("Error: "+e.getMessage());
		}
	}
	
	public List<Vacante> buscarTodas() 
	{
		return lista;
	}

	public Vacante buscarPorId(Integer idVacante) {
		for (Vacante v : lista) 
		{
			if(v.getId() == idVacante)
			{
				return v;
			}
		}
		return null;
	}

	@Override
	public void guardar(Vacante vacante) 
	{
		lista.add(vacante);
	}

	@Override
	public List<Vacante> buscarDestacadas() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void eliminar(Integer idVacante) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<Vacante> buscarByExample(Example<Vacante> example) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Page<Vacante> buscarTodas(Pageable page) {
		// TODO Auto-generated method stub
		return null;
	}

}
