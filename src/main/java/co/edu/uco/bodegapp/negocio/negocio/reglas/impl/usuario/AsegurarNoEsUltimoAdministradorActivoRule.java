package co.edu.uco.bodegapp.negocio.negocio.reglas.impl.usuario;

import co.edu.uco.bodegapp.dao.factoria.DAOFactory;
import co.edu.uco.bodegapp.entidad.EstadoUsuarioEntidad;
import co.edu.uco.bodegapp.entidad.RolEntidad;
import co.edu.uco.bodegapp.entidad.UsuarioEntidad;
import co.edu.uco.bodegapp.negocio.negocio.reglas.Rule;
import co.edu.uco.bodegapp.transversal.catalogo.CatalogoMensajes;
import co.edu.uco.bodegapp.transversal.excepciones.BodegappNegocioExcepcion;

//el sistema nunca puede quedar sin un administrador activo, porque nadie podria aprobar cuentas
//si el usuario no es administrador, esta regla no tiene nada que revisar
public class AsegurarNoEsUltimoAdministradorActivoRule implements Rule<Object> {

    private static final String ROL_ADMINISTRADOR = "Administrador";
    private static final String ESTADO_ACTIVO = "Activo";

    private static final Rule<Object> instancia = new AsegurarNoEsUltimoAdministradorActivoRule();

    private AsegurarNoEsUltimoAdministradorActivoRule() {
    }

    public static final Rule<Object> obtenerInstancia() {
        return instancia;
    }

    @Override
    public void ejecutar(Object... datos) {
        var usuario = (UsuarioEntidad) datos[0];
        var daoFactory = (DAOFactory) datos[1];

        if (ROL_ADMINISTRADOR.equals(usuario.getRol().getNombre())) {
            var filtro = new UsuarioEntidad.Builder()
                    .rol(new RolEntidad.Builder().nombre(ROL_ADMINISTRADOR).build())
                    .estadoUsuario(new EstadoUsuarioEntidad.Builder().nombre(ESTADO_ACTIVO).build())
                    .build();
            var administradoresActivos = daoFactory.obtenerUsuarioDAO().consultarPorFiltro(filtro);

            if (administradoresActivos.size() <= 1) {
                var mensajeUsuario = CatalogoMensajes.UsuarioNegocioImpl.ULTIMO_ADMINISTRADOR_ACTIVO;
                throw BodegappNegocioExcepcion.crear(mensajeUsuario);
            }
        }
    }
}
