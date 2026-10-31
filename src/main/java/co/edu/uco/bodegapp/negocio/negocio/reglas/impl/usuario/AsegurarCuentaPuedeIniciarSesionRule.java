package co.edu.uco.bodegapp.negocio.negocio.reglas.impl.usuario;

import co.edu.uco.bodegapp.entidad.UsuarioEntidad;
import co.edu.uco.bodegapp.negocio.negocio.reglas.Rule;
import co.edu.uco.bodegapp.transversal.catalogo.CatalogoMensajes;
import co.edu.uco.bodegapp.transversal.excepciones.BodegappNegocioExcepcion;

//solo entra quien tiene la cuenta activa: la pendiente espera aprobacion y la inactiva esta desactivada
public class AsegurarCuentaPuedeIniciarSesionRule implements Rule<UsuarioEntidad> {

    private static final String ESTADO_PENDIENTE = "Pendiente";
    private static final String ESTADO_ACTIVO = "Activo";

    private static final Rule<UsuarioEntidad> instancia = new AsegurarCuentaPuedeIniciarSesionRule();

    private AsegurarCuentaPuedeIniciarSesionRule() {
    }

    public static final Rule<UsuarioEntidad> obtenerInstancia() {
        return instancia;
    }

    @Override
    public void ejecutar(UsuarioEntidad... datos) {
        var usuario = datos[0];
        var nombreEstado = usuario.getEstadoUsuario().getNombre();

        if (ESTADO_PENDIENTE.equals(nombreEstado)) {
            var mensajeUsuario = CatalogoMensajes.UsuarioNegocioImpl.CUENTA_PENDIENTE_DE_APROBACION;
            throw BodegappNegocioExcepcion.crear(mensajeUsuario);
        }
        if (!ESTADO_ACTIVO.equals(nombreEstado)) {
            var mensajeUsuario = CatalogoMensajes.UsuarioNegocioImpl.CUENTA_DESACTIVADA;
            throw BodegappNegocioExcepcion.crear(mensajeUsuario);
        }
    }
}
