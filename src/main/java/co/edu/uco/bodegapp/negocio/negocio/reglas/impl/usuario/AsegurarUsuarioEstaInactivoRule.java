package co.edu.uco.bodegapp.negocio.negocio.reglas.impl.usuario;

import co.edu.uco.bodegapp.entidad.UsuarioEntidad;
import co.edu.uco.bodegapp.negocio.negocio.reglas.Rule;
import co.edu.uco.bodegapp.transversal.catalogo.CatalogoMensajes;
import co.edu.uco.bodegapp.transversal.excepciones.BodegappNegocioExcepcion;

//solo se reactiva una cuenta que este desactivada
public class AsegurarUsuarioEstaInactivoRule implements Rule<UsuarioEntidad> {

    private static final String ESTADO_INACTIVO = "Inactivo";

    private static final Rule<UsuarioEntidad> instancia = new AsegurarUsuarioEstaInactivoRule();

    private AsegurarUsuarioEstaInactivoRule() {
    }

    public static final Rule<UsuarioEntidad> obtenerInstancia() {
        return instancia;
    }

    @Override
    public void ejecutar(UsuarioEntidad... datos) {
        var usuario = datos[0];

        if (!ESTADO_INACTIVO.equals(usuario.getEstadoUsuario().getNombre())) {
            var mensajeUsuario = CatalogoMensajes.UsuarioNegocioImpl.USUARIO_NO_ESTA_INACTIVO;
            throw BodegappNegocioExcepcion.crear(mensajeUsuario);
        }
    }
}
