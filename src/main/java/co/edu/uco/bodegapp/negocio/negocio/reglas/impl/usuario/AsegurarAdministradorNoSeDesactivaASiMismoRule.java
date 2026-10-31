package co.edu.uco.bodegapp.negocio.negocio.reglas.impl.usuario;

import co.edu.uco.bodegapp.negocio.negocio.reglas.Rule;
import co.edu.uco.bodegapp.transversal.catalogo.CatalogoMensajes;
import co.edu.uco.bodegapp.transversal.excepciones.BodegappNegocioExcepcion;

import java.util.UUID;

//un administrador no puede desactivar su propia cuenta
public class AsegurarAdministradorNoSeDesactivaASiMismoRule implements Rule<UUID> {

    private static final Rule<UUID> instancia = new AsegurarAdministradorNoSeDesactivaASiMismoRule();

    private AsegurarAdministradorNoSeDesactivaASiMismoRule() {
    }

    public static final Rule<UUID> obtenerInstancia() {
        return instancia;
    }

    @Override
    public void ejecutar(UUID... datos) {
        var idAdministrador = datos[0];
        var idUsuario = datos[1];

        if (idAdministrador.equals(idUsuario)) {
            var mensajeUsuario = CatalogoMensajes.UsuarioNegocioImpl.ADMINISTRADOR_NO_PUEDE_DESACTIVARSE_A_SI_MISMO;
            throw BodegappNegocioExcepcion.crear(mensajeUsuario);
        }
    }
}
