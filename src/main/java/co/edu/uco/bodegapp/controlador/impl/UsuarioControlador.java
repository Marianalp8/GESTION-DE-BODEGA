package co.edu.uco.bodegapp.controlador.impl;

import co.edu.uco.bodegapp.controlador.AbstractRestController;
import co.edu.uco.bodegapp.controlador.respuesta.RespuestaOk;
import co.edu.uco.bodegapp.dto.RolDto;
import co.edu.uco.bodegapp.dto.UsuarioDto;
import co.edu.uco.bodegapp.negocio.fachada.impl.UsuarioFachadaImpl;
import co.edu.uco.bodegapp.transversal.catalogo.CatalogoMensajes;
import co.edu.uco.bodegapp.transversal.utilitario.UtilId;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/usuarios")
public class UsuarioControlador extends AbstractRestController {

    @PostMapping
    public ResponseEntity<RespuestaOk<UsuarioDto>> registrar(@RequestBody UsuarioDto datos) {
        var fachada = new UsuarioFachadaImpl();
        fachada.registrarInformacionNuevoUsuario(datos);
        RespuestaOk<UsuarioDto> respuesta = RespuestaOk.crear(CatalogoMensajes.UsuarioControlador.USUARIO_REGISTRADO);
        return ResponseEntity.status(HttpStatus.CREATED).body(respuesta);
    }
    //GET http://localhost:987/api/v1/usuarios?nombre=ana&rol.id=...&estadoUsuario.id=...
    @GetMapping
    public ResponseEntity<RespuestaOk<UsuarioDto>> consultarUsuarios(UsuarioDto filtro) {
        var fachada = new UsuarioFachadaImpl();
        var usuarios = fachada.consultarUsuarios(obtenerIdAdministrador(), filtro);
        RespuestaOk<UsuarioDto> respuesta = RespuestaOk.crear(usuarios);
        return ResponseEntity.ok(respuesta);
    }
    //PUT http://localhost:987/api/v1/usuarios/{id}/aprobacion
    //body: { "id": "id del rol que se le da" }
    @PutMapping("/{id}/aprobacion")
    public ResponseEntity<RespuestaOk<UsuarioDto>> aprobar(@PathVariable UUID id, @RequestBody RolDto rol) {
        var fachada = new UsuarioFachadaImpl();
        fachada.aprobarUsuario(obtenerIdAdministrador(), id, rol.getId());
        RespuestaOk<UsuarioDto> respuesta = RespuestaOk.crear(CatalogoMensajes.UsuarioControlador.USUARIO_APROBADO);
        return ResponseEntity.ok(respuesta);
    }

    //PUT http://localhost:987/api/v1/usuarios/{id}/rol
    //body: { "id": "id del rol nuevo" }
    @PutMapping("/{id}/rol")
    public ResponseEntity<RespuestaOk<UsuarioDto>> cambiarRol(@PathVariable UUID id, @RequestBody RolDto rol) {
        var fachada = new UsuarioFachadaImpl();
        fachada.cambiarRolUsuario(obtenerIdAdministrador(), id, rol.getId());
        RespuestaOk<UsuarioDto> respuesta = RespuestaOk.crear(CatalogoMensajes.UsuarioControlador.ROL_CAMBIADO);
        return ResponseEntity.ok(respuesta);
    }

    //PUT http://localhost:987/api/v1/usuarios/{id}/desactivacion
    @PutMapping("/{id}/desactivacion")
    public ResponseEntity<RespuestaOk<UsuarioDto>> desactivar(@PathVariable UUID id) {
        var fachada = new UsuarioFachadaImpl();
        fachada.desactivarUsuario(obtenerIdAdministrador(), id);
        RespuestaOk<UsuarioDto> respuesta = RespuestaOk.crear(CatalogoMensajes.UsuarioControlador.USUARIO_DESACTIVADO);
        return ResponseEntity.ok(respuesta);
    }

    //PUT http://localhost:987/api/v1/usuarios/{id}/reactivacion
    @PutMapping("/{id}/reactivacion")
    public ResponseEntity<RespuestaOk<UsuarioDto>> reactivar(@PathVariable UUID id) {
        var fachada = new UsuarioFachadaImpl();
        fachada.reactivarUsuario(obtenerIdAdministrador(), id);
        RespuestaOk<UsuarioDto> respuesta = RespuestaOk.crear(CatalogoMensajes.UsuarioControlador.USUARIO_REACTIVADO);
        return ResponseEntity.ok(respuesta);
    }


}
