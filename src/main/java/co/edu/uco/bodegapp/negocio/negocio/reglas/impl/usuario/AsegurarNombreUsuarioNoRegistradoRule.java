package co.edu.uco.bodegapp.negocio.negocio.reglas.impl.usuario;

import co.edu.uco.bodegapp.dao.factoria.DAOFactory;
import co.edu.uco.bodegapp.entidad.UsuarioEntidad;
import co.edu.uco.bodegapp.negocio.negocio.reglas.Rule;
import co.edu.uco.bodegapp.transversal.catalogo.CatalogoMensajes;
import co.edu.uco.bodegapp.transversal.excepciones.BodegappNegocioExcepcion;

//dos personas no pueden tener el mismo nombre de usuario
public class AsegurarNombreUsuarioNoRegistradoRule implements Rule<Object> {

    private static final Rule<Object> instancia = new AsegurarNombreUsuarioNoRegistradoRule();

    private AsegurarNombreUsuarioNoRegistradoRule() {
    }

    public static final Rule<Object> obtenerInstancia() {
        return instancia;
    }

    @Override
    public void ejecutar(Object... datos) {
        var nombreUsuario = (String) datos[0];
        var daoFactory = (DAOFactory) datos[1];

        var filtro = new UsuarioEntidad.Builder().nombreUsuario(nombreUsuario).build();
        var usuariosEncontrados = daoFactory.obtenerUsuarioDAO().consultarPorFiltro(filtro);

        if (!usuariosEncontrados.isEmpty()) {
            var mensajeUsuario = CatalogoMensajes.UsuarioNegocioImpl.NOMBRE_USUARIO_YA_REGISTRADO;
            throw BodegappNegocioExcepcion.crear(mensajeUsuario);
        }
    }
}
