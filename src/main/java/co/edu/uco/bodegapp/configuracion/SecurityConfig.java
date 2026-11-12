package co.edu.uco.bodegapp.configuracion;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.security.web.context.SecurityContextRepository;

//el portero de la aplicacion: dice quien puede entrar a cada direccion (RF10.8 control de acceso por rol)
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private static final String ROL_ADMINISTRADOR = "ADMINISTRADOR";
    @Bean
    public SecurityFilterChain filtroSeguridad(HttpSecurity http, SecurityContextRepository repositorioSesion) throws Exception {
        http
                .authorizeHttpRequests(permisos -> permisos
                        //cualquiera: registrarse, iniciar/cerrar sesion y pedir el token csrf
                        .requestMatchers(HttpMethod.POST, "/api/v1/usuarios").permitAll()
                        .requestMatchers("/api/v1/sesion", "/api/v1/csrf").permitAll()
                        //solo el administrador: buscar usuarios, aprobar, cambiar rol, desactivar, reactivar, historial y las listas
                        .requestMatchers("/api/v1/usuarios/**", "/api/v1/roles", "/api/v1/estados").hasRole(ROL_ADMINISTRADOR)
                        //cualquier otra direccion del api que no este aqui: nadie
                        .requestMatchers("/api/**").denyAll()
                        //lo demas son las pantallas (html, css, js): las puede abrir cualquiera
                        .anyRequest().permitAll()
                )
                //donde se guarda quien inicio sesion: en la sesion del servidor
                .securityContext(contexto -> contexto.securityContextRepository(repositorioSesion))
                //si alguien sin sesion pide algo protegido: 401 (no ha iniciado sesion)
                .exceptionHandling(errores -> errores.authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED)));

        return http.build();
    }

    //lo usa el SesionControlador para guardar al usuario en la sesion cuando el login sale bien
    @Bean
    public SecurityContextRepository repositorioSesion() {
        return new HttpSessionSecurityContextRepository();
    }

}
