package co.edu.uco.bodegapp.controlador.impl;

import co.edu.uco.bodegapp.controlador.AbstractRestController;
import co.edu.uco.bodegapp.controlador.respuesta.RespuestaOk;
import co.edu.uco.bodegapp.dto.HistorialUsuarioDto;
import co.edu.uco.bodegapp.negocio.fachada.impl.HistorialUsuarioFachadaImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/usuarios")
public class HistorialUsuarioControlador extends AbstractRestController {
    //GET http://localhost:987/api/v1/usuarios/{id}/historial
    @GetMapping("/{id}/historial")
    public ResponseEntity<RespuestaOk<HistorialUsuarioDto>> consultarPorUsuario(@PathVariable UUID id) {
        var fachada = new HistorialUsuarioFachadaImpl();
        var historial = fachada.consultarPorUsuario(obtenerIdAdministrador(), id);
        RespuestaOk<HistorialUsuarioDto> respuesta = RespuestaOk.crear(historial);
        return ResponseEntity.ok(respuesta);
    }

}
