package co.edu.uco.bodegapp.negocio.negocio.reglas.impl.usuario;

import co.edu.uco.bodegapp.dao.factoria.DAOFactory;
import co.edu.uco.bodegapp.negocio.negocio.reglas.Rule;
import co.edu.uco.bodegapp.transversal.catalogo.CatalogoMensajes;
import co.edu.uco.bodegapp.transversal.excepciones.BodegappNegocioExcepcion;
import co.edu.uco.bodegapp.transversal.utilitario.UtilId;

import java.util.UUID;

//el rol que se le da a un usuario debe existir y no puede ser 'Sin asignar' (cuyo id es el de ceros)
public class AsegurarRolEsAsignableRule implements Rule<Object> {

    private static final Rule<Object> instancia = new AsegurarRolEsAsignableRule();

    private AsegurarRolEsAsignableRule() {
    }

    public static final Rule<Object> obtenerInstancia() {
        return instancia;
    }

    @Override
    public void ejecutar(Object... datos) {
        var idRol = (UUID) datos[0];
        var daoFactory = (DAOFactory) datos[1];

        if (UtilId.VALOR_DEFECTO.equals(idRol)) {
            var mensajeUsuario = CatalogoMensajes.UsuarioNegocioImpl.ROL_OBLIGATORIO;
            throw BodegappNegocioExcepcion.crear(mensajeUsuario);
        }

        var rolEncontrado = daoFactory.obtenerRolDAO().consultarPorId(idRol);

        //cuando el rol no existe, el DAO devuelve uno vacio con el id de ceros
        if (UtilId.VALOR_DEFECTO.equals(rolEncontrado.getId())) {
            var mensajeUsuario = CatalogoMensajes.UsuarioNegocioImpl.ROL_NO_EXISTE;
            throw BodegappNegocioExcepcion.crear(mensajeUsuario);
        }
    }
}
