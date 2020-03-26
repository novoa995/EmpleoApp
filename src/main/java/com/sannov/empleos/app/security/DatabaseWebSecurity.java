package com.sannov.empleos.app.security;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class DatabaseWebSecurity extends WebSecurityConfigurerAdapter {
	/*
	 * Inyectamos de la variable que tenemos en el documento application.properties
	 */
	@Autowired
	private DataSource dataSource;

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.jdbcAuthentication().dataSource(dataSource)
				// Establecemos los tres atributos de NUESTRA TABLA que Spring nos pide (user,
				// password and enable)
				.usersByUsernameQuery("select username, password, estatus from Usuarios where username=?")
				.authoritiesByUsernameQuery("select u.username, p.perfil from UsuarioPerfil up "
						+ "inner join Usuarios u on u.id = up.idUsuario "
						+ "inner join Perfiles p on p.id = up.idPerfil " + "where u.username = ?");
	}
	/*
	 * Especificamos las urls que seran publicas y las que ocuparan un usuario y contraseña
	 */
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
				// Los recursos estáticos no requieren autenticación
				.antMatchers("/bootstrap/**", "/images/**", "/tinymce/**", "/logos/**").permitAll()
				// Las vistas públicas no requieren autenticación
				.antMatchers("/", "/signup", "/search", "/bcrypt/**", "/vacantes/view/**").permitAll()
				/*
				 * Asignar permisos a URLs por ROLES
				 * Asignamos que las cuentas con supervisor y administrados solo puedan acceder a ciertas vistas 
				 */
				.antMatchers("/vacantes/**").hasAnyAuthority("SUPERVISOR","ADMINISTRADOR")
				.antMatchers("/categorias/**").hasAnyAuthority("SUPERVISOR","ADMINISTRADOR")
				.antMatchers("/usuarios/**").hasAnyAuthority("ADMINISTRADOR")
				.antMatchers("/solicitudes/create/**").hasAnyAuthority("USUARIO","SUPERVISOR","ADMINISTRADOR")
				.antMatchers("/solicitudes/index").hasAnyAuthority("SUPERVISOR","ADMINISTRADOR")

				// Todas las demás URLs de la Aplicación requieren autenticación
				.anyRequest().authenticated()
				// El formulario de Login no requiere autenticacion
				.and().formLogin().loginPage("/login").permitAll()
				//si se ha logeado exitosamente se redigira a esta pag
				.defaultSuccessUrl("/");
				//si existe un error al logearse, se redigira a esta pag
				//.failureUrl("/loginError");
	}
	/*
	 * Meotod para poder encriptar las contraseñas de los usuarios
	 * que inician session
	 */
	@Bean
	public PasswordEncoder passwordEncoder() {
	return new BCryptPasswordEncoder();
	}

}