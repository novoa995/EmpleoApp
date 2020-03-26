package com.sannov.empleos.app.util;

import java.io.File;
import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;

public class Utileria {
	public static String guardarArchivo(MultipartFile multiPart, String ruta) 
	{
		// Obtenemos el nombre original del archivo.
		String nombreOriginal = multiPart.getOriginalFilename();
		//cambiamos los espacios que esten en el nombre de la imagen por "-"
		nombreOriginal = nombreOriginal.replace(' ', '-');
		//agregamos 8 caracteres ramdom al principip del nombre para que nunca se valla a repetir
		String nombreFinal = randomAlphaNumeric(8) + nombreOriginal;
		try 
		{
			// Formamos el nombre del archivo para guardarlo en el disco duro.
			File imageFile = new File(ruta + nombreFinal);
			System.out.println("Archivo: " + imageFile.getAbsolutePath());
			// Guardamos fisicamente el archivo en HD.
			multiPart.transferTo(imageFile);
			return nombreFinal;
		} 
		catch (IOException e) 
		{
			System.out.println("Error " + e.getMessage());
			return null;
		}
	}

	/*
	 * Metodo para generar una cadena aleatoria de longitud N
	 * - en este caso para agregarlo al nombre de la imagen y asi no se repita
	 */
	public static String randomAlphaNumeric(int count)
	{
		String caracteres = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
		StringBuilder builder = new StringBuilder();
		while(count-- != 0)
		{
			int character = (int) (Math.random() * caracteres.length());
			builder.append(caracteres.charAt(character));
		}
		return builder.toString();
	}
	
}
