package com.sannov.empleos.app.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.sannov.empleos.app.security.DatabaseWebSecurity;
/*
 * Clase de configuracion
 */
@Configuration
public class WebConfig implements WebMvcConfigurer
{
	/*
	 * Inyectamos la ruta de las imagenes que tenemos en el archivo application.properties
	 */
	@Value("${empleosapp.ruta.imagenes}")
	private String rutaImagenes;
	/*
	 * Inyectamos la ruta de los cvs que tenemos en el archivo application.properties
	 */
	@Value("${empleosapp.ruta.cv}")
	private String rutaCVS;
	
	/*
	 * Con este metodo indicamos que el proyecto podra tomar archivos del directorio que le especifiquemos
	 * Nos podremos meter a los archivos (imagenes) desde la url localhost/logos/nombre de la imagen
	 */
	public void addResourceHandlers(ResourceHandlerRegistry registry) 
	{
		//registry.addResourceHandler("/logos/**").addResourceLocations("file:c:/empleos/img-vacantes/"); // Windows
		registry.addResourceHandler("/logos/**").addResourceLocations("file:"+rutaImagenes); // Windows	
		registry.addResourceHandler("/cv/**").addResourceLocations("file:"+rutaCVS); // Windows	
	}
}
