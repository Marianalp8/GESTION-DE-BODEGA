package co.edu.uco.bodegapp.negocio.negocio.reglas.impl.usuario;

import co.edu.uco.bodegapp.dao.factoria.DAOFactory;
import co.edu.uco.bodegapp.negocio.negocio.reglas.Rule;
import co.edu.uco.bodegapp.transversal.catalogo.CatalogoMensajes;
import co.edu.uco.bodegapp.transversal.excepciones.BodegappNegocioExcepcion;
import co.edu.uco.bodegapp.transversal.utilitario.UtilId;

import java.util.UUID;

//el usuario al que se le hace la accion debe existir
public class AsegurarUsuarioExisteRule implements Rule<Object> {

    private static final Rule<Object> instancia = new AsegurarUsuarioExisteRule();

    private AsegurarUsuarioExisteRule() {
    }

    public static final Rule<Object> obtenerInstancia() {
        return instancia;
    }

    @Override
    public void ejecutar(Object... datos) {
        var idUsuario = (UUID) datos[0];
        var daoFactory = (DAOFactory) datos[1];

        var usuarioEncontrado = daoFactory.obtenerUsuarioDAO().consultarPorId(idUsuario);

        if (UtilId.VALOR_DEFECTO.equals(usuarioEncontrado.getId())) {
            var mensajeUsuario = CatalogoMensajes.UsuarioNegocioImpl.USUARIO_NO_EXISTE;
            throw BodegappNegocioExcepcion.crear(mensajeUsuario);
        }
    }
}
