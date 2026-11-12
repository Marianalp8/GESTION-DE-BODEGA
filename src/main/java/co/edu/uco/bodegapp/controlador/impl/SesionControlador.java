package co.edu.uco.bodegapp.controlador.impl;


import co.edu.uco.bodegapp.controlador.respuesta.RespuestaOk;
import co.edu.uco.bodegapp.dto.InicioSesionDto;
import co.edu.uco.bodegapp.dto.UsuarioDto;
import co.edu.uco.bodegapp.negocio.fachada.impl.UsuarioFachadaImpl;
import co.edu.uco.bodegapp.transversal.catalogo.CatalogoMensajes;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//la puerta de la sesion: iniciar sesion, saber quien esta logueado y cerrar sesion
@RestController
@RequestMapping("/api/v1/sesion")
public class SesionControlador {
    private static final String PREFIJO_ROL = "ROLE_";

    //el mismo lugar donde el SecurityConfig busca quien inicio sesion (el @Bean repositorioSesion)
    private final SecurityContextRepository repositorioSesion;

    public SesionControlador(SecurityContextRepository repositorioSesion) {
        this.repositorioSesion = repositorioSesion;
    }

    //POST http://localhost:987/api/v1/sesion
    //body: { "usuarioOCorreo": "...", "contrasena": "..." }
    @PostMapping
    public ResponseEntity<RespuestaOk<UsuarioDto>> iniciarSesion(@RequestBody InicioSesionDto datos,
                                                                 HttpServletRequest peticion,
                                                                 HttpServletResponse respuestaHttp) {
        //1. el login lo hace NUESTRO negocio (credenciales, cuenta pendiente, cuenta desactivada...)
        var fachada = new UsuarioFachadaImpl();
        var usuario = fachada.iniciarSesion(datos.getUsuarioOCorreo(), datos.getContrasena());

        //2. si llego aqui, salio bien: le decimos a Spring Security quien es y que rol tiene
        var rol = new SimpleGrantedAuthority(PREFIJO_ROL + usuario.getRol().getNombre().toUpperCase());
        var autenticacion = UsernamePasswordAuthenticationToken.authenticated(usuario, null, List.of(rol));

        var contexto = SecurityContextHolder.createEmptyContext();
        contexto.setAuthentication(autenticacion);
        SecurityContextHolder.setContext(contexto);

        //3. cambia el numero de la sesion al iniciar sesion (proteccion contra robo de sesion)
        if (peticion.getSession(false) != null) {
            peticion.changeSessionId();
        }

        //4. lo guarda en la sesion para las siguientes peticiones
        repositorioSesion.saveContext(contexto, peticion, respuestaHttp);

        return ResponseEntity.ok(RespuestaOk.crear(usuario));
    }

    //GET http://localhost:987/api/v1/sesion
    //la pantalla pregunta quien esta logueado: si nadie, datos vacio
    @GetMapping
    public ResponseEntity<RespuestaOk<UsuarioDto>> consultarSesion() {
        var autenticacion = SecurityContextHolder.getContext().getAuthentication();
        if (autenticacion != null && autenticacion.getPrincipal() instanceof UsuarioDto usuario) {
            return ResponseEntity.ok(RespuestaOk.crear(usuario));
        }
        return ResponseEntity.ok(RespuestaOk.crear());
    }

    //DELETE http://localhost:987/api/v1/sesion
    @DeleteMapping
    public ResponseEntity<RespuestaOk<UsuarioDto>> cerrarSesion(HttpServletRequest peticion,
                                                                HttpServletResponse respuestaHttp) {
        var autenticacion = SecurityContextHolder.getContext().getAuthentication();
        new SecurityContextLogoutHandler().logout(peticion, respuestaHttp, autenticacion);
        RespuestaOk<UsuarioDto> respuesta = RespuestaOk.crear(CatalogoMensajes.SesionControlador.SESION_CERRADA);
        return ResponseEntity.ok(respuesta);
    }

}
