package com.sannov.empleos.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
public class EmpleosApplication //extends SpringBootServletInitializer
{	
/*
	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) 
	{
		return builder.sources(EmpleosApplication.class);
	}
*/
	public static void main(String[] args) {
		SpringApplication.run(EmpleosApplication.class, args);
	}

}
