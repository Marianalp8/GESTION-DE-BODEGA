package co.edu.uco.bodegapp.controlador.impl;

import co.edu.uco.bodegapp.controlador.AbstractRestController;
import co.edu.uco.bodegapp.controlador.respuesta.RespuestaOk;
import co.edu.uco.bodegapp.dto.EstadoUsuarioDto;
import co.edu.uco.bodegapp.negocio.fachada.impl.EstadoUsuarioFachadaImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/v1/estados")
public class EstadoUsuarioControlador extends AbstractRestController {
    //GET http://localhost:987/api/v1/estados
    @GetMapping
    public ResponseEntity<RespuestaOk<EstadoUsuarioDto>> consultarTodos() {
        var fachada = new EstadoUsuarioFachadaImpl();
        var estados = fachada.consultarTodos();
        RespuestaOk<EstadoUsuarioDto> respuesta = RespuestaOk.crear(estados);
        return ResponseEntity.ok(respuesta);
    }

}
