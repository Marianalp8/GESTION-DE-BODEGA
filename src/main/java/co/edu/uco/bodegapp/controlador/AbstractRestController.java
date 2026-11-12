package co.edu.uco.bodegapp.controlador;

import co.edu.uco.bodegapp.controlador.respuesta.RespuestaError;
import co.edu.uco.bodegapp.transversal.catalogo.CatalogoMensajes;
import co.edu.uco.bodegapp.transversal.excepciones.BodegappExcepcion;
import co.edu.uco.bodegapp.transversal.utilitario.UtilId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.util.UUID;

//this class is used to handle the exceptions
@RestControllerAdvice
public abstract class AbstractRestController {
    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractRestController.class);

    //lo controlado: un error nuestro (regla, DAO, fachada) que ya trae su mensaje para el usuario
    @ExceptionHandler(BodegappExcepcion.class)
    public ResponseEntity<RespuestaError<String>> controlarBodegappExcepcion(BodegappExcepcion excepcion) {
        LOGGER.warn("Capa: {} - Mensaje tecnico: {}", excepcion.getCapa(), excepcion.getMensajeTecnico(), excepcion.getExcepcionRaiz());
        var respuesta = RespuestaError.crear(excepcion.getMensajeUsuario());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(respuesta);
    }

    //lo inesperado: cualquier otra cosa
    @ExceptionHandler(Exception.class)
    public ResponseEntity<RespuestaError<String>> controlarExcepcion(Exception excepcion) {
        LOGGER.error("Se presento un problema inesperado en el controlador", excepcion);
        var respuesta = RespuestaError.crear(CatalogoMensajes.AbstractRestController.USUARIO_ERROR_PROBLEMA_INESPERADO_EN_CONTROLADOR);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(respuesta);
    }

    //alguien pidio una direccion que no existe
    @ExceptionHandler(NoResourceFoundException.class)
    public ResponseEntity<RespuestaError<String>> controlarNoResourceFoundException(NoResourceFoundException excepcion) {
        LOGGER.info("Recurso no encontrado: {} por el metodo: {}", excepcion.getResourcePath(), excepcion.getHttpMethod());
        var respuesta = RespuestaError.crear(CatalogoMensajes.AbstractRestController.USUARIO_ERROR_RECURSO_NO_EXISTE);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(respuesta);
    }
    protected UUID obtenerIdAdministrador() {
        return UtilId.VALOR_DEFECTO;
    }

}
