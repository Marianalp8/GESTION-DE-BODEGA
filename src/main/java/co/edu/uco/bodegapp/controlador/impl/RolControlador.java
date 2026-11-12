package co.edu.uco.bodegapp.controlador.impl;


import co.edu.uco.bodegapp.controlador.AbstractRestController;
import co.edu.uco.bodegapp.controlador.respuesta.RespuestaOk;
import co.edu.uco.bodegapp.dto.RolDto;
import co.edu.uco.bodegapp.negocio.fachada.impl.RolFachadaImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/roles")
public class RolControlador extends AbstractRestController {
    //GET http://localhost:987/api/v1/roles
    @GetMapping
    public ResponseEntity<RespuestaOk<RolDto>> consultarTodos() {
        var fachada = new RolFachadaImpl();
        var roles = fachada.consultarTodos();
        RespuestaOk<RolDto> respuesta = RespuestaOk.crear(roles);
        return ResponseEntity.ok(respuesta);
    }
}
