package co.edu.uco.bodegapp.negocio.negocio.reglas.impl.usuario;

import co.edu.uco.bodegapp.entidad.UsuarioEntidad;
import co.edu.uco.bodegapp.negocio.negocio.reglas.Rule;
import co.edu.uco.bodegapp.transversal.catalogo.CatalogoMensajes;
import co.edu.uco.bodegapp.transversal.excepciones.BodegappNegocioExcepcion;

//solo se aprueba una cuenta que este pendiente
public class AsegurarUsuarioEstaPendienteRule implements Rule<UsuarioEntidad> {

    private static final String ESTADO_PENDIENTE = "Pendiente";

    private static final Rule<UsuarioEntidad> instancia = new AsegurarUsuarioEstaPendienteRule();

    private AsegurarUsuarioEstaPendienteRule() {
    }

    public static final Rule<UsuarioEntidad> obtenerInstancia() {
        return instancia;
    }

    @Override
    public void ejecutar(UsuarioEntidad... datos) {
        var usuario = datos[0];

        if (!ESTADO_PENDIENTE.equals(usuario.getEstadoUsuario().getNombre())) {
            var mensajeUsuario = CatalogoMensajes.UsuarioNegocioImpl.USUARIO_NO_ESTA_PENDIENTE;
            throw BodegappNegocioExcepcion.crear(mensajeUsuario);
        }
    }
}
