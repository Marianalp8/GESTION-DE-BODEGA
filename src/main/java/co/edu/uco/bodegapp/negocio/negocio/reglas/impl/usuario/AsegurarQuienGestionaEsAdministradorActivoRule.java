package co.edu.uco.bodegapp.negocio.negocio.reglas.impl.usuario;

import co.edu.uco.bodegapp.dao.factoria.DAOFactory;
import co.edu.uco.bodegapp.negocio.negocio.reglas.Rule;
import co.edu.uco.bodegapp.transversal.catalogo.CatalogoMensajes;
import co.edu.uco.bodegapp.transversal.excepciones.BodegappNegocioExcepcion;
import co.edu.uco.bodegapp.transversal.utilitario.UtilId;

import java.util.UUID;

//quien aprueba, cambia roles, desactiva o reactiva debe existir, estar activo y ser administrador
public class AsegurarQuienGestionaEsAdministradorActivoRule implements Rule<Object> {

    private static final String ROL_ADMINISTRADOR = "Administrador";
    private static final String ESTADO_ACTIVO = "Activo";

    private static final Rule<Object> instancia = new AsegurarQuienGestionaEsAdministradorActivoRule();

    private AsegurarQuienGestionaEsAdministradorActivoRule() {
    }

    public static final Rule<Object> obtenerInstancia() {
        return instancia;
    }

    @Override
    public void ejecutar(Object... datos) {
        var idAdministrador = (UUID) datos[0];
        var daoFactory = (DAOFactory) datos[1];

        var administrador = daoFactory.obtenerUsuarioDAO().consultarPorId(idAdministrador);

        var existe = !UtilId.VALOR_DEFECTO.equals(administrador.getId());
        var estaActivo = ESTADO_ACTIVO.equals(administrador.getEstadoUsuario().getNombre());
        var esAdministrador = ROL_ADMINISTRADOR.equals(administrador.getRol().getNombre());

        if (!existe || !estaActivo || !esAdministrador) {
            var mensajeUsuario = CatalogoMensajes.UsuarioNegocioImpl.SIN_PERMISO_PARA_LA_ACCION;
            throw BodegappNegocioExcepcion.crear(mensajeUsuario);
        }
    }
}
