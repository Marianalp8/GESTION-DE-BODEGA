package co.edu.uco.bodegapp.negocio.negocio.reglas.impl.usuario;

import co.edu.uco.bodegapp.entidad.UsuarioEntidad;
import co.edu.uco.bodegapp.negocio.negocio.reglas.Rule;
import co.edu.uco.bodegapp.transversal.catalogo.CatalogoMensajes;
import co.edu.uco.bodegapp.transversal.excepciones.BodegappNegocioExcepcion;

import java.util.UUID;

//cambiar el rol por el mismo que ya tiene no es un cambio
public class AsegurarRolEsDiferenteAlActualRule implements Rule<Object> {

    private static final Rule<Object> instancia = new AsegurarRolEsDiferenteAlActualRule();

    private AsegurarRolEsDiferenteAlActualRule() {
    }

    public static final Rule<Object> obtenerInstancia() {
        return instancia;
    }

    @Override
    public void ejecutar(Object... datos) {
        var usuario = (UsuarioEntidad) datos[0];
        var idRolNuevo = (UUID) datos[1];

        if (usuario.getRol().getId().equals(idRolNuevo)) {
            var mensajeUsuario = CatalogoMensajes.UsuarioNegocioImpl.ROL_IGUAL_AL_ACTUAL;
            throw BodegappNegocioExcepcion.crear(mensajeUsuario);
        }
    }
}
