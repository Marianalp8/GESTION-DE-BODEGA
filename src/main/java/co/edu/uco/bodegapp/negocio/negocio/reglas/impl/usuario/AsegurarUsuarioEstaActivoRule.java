package co.edu.uco.bodegapp.negocio.negocio.reglas.impl.usuario;

import co.edu.uco.bodegapp.entidad.UsuarioEntidad;
import co.edu.uco.bodegapp.negocio.negocio.reglas.Rule;
import co.edu.uco.bodegapp.transversal.catalogo.CatalogoMensajes;
import co.edu.uco.bodegapp.transversal.excepciones.BodegappNegocioExcepcion;

//solo se le cambia el rol o se desactiva a un usuario que este activo
public class AsegurarUsuarioEstaActivoRule implements Rule<UsuarioEntidad> {

    private static final String ESTADO_ACTIVO = "Activo";

    private static final Rule<UsuarioEntidad> instancia = new AsegurarUsuarioEstaActivoRule();

    private AsegurarUsuarioEstaActivoRule() {
    }

    public static final Rule<UsuarioEntidad> obtenerInstancia() {
        return instancia;
    }

    @Override
    public void ejecutar(UsuarioEntidad... datos) {
        var usuario = datos[0];

        if (!ESTADO_ACTIVO.equals(usuario.getEstadoUsuario().getNombre())) {
            var mensajeUsuario = CatalogoMensajes.UsuarioNegocioImpl.USUARIO_NO_ESTA_ACTIVO;
            throw BodegappNegocioExcepcion.crear(mensajeUsuario);
        }
    }
}
