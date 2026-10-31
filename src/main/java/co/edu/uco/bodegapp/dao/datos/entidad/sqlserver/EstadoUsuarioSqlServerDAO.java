package co.edu.uco.bodegapp.dao.datos.entidad.sqlserver;

import co.edu.uco.bodegapp.dao.datos.entidad.EstadoUsuarioDAO;
import co.edu.uco.bodegapp.dao.datos.entidad.SqlDAO;
import co.edu.uco.bodegapp.entidad.EstadoUsuarioEntidad;
import co.edu.uco.bodegapp.transversal.catalogo.CatalogoMensajes;
import co.edu.uco.bodegapp.transversal.excepciones.BodegappDatosExcepcion;
import co.edu.uco.bodegapp.transversal.utilitario.UtilId;
import co.edu.uco.bodegapp.transversal.utilitario.UtilTexto;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class EstadoUsuarioSqlServerDAO extends SqlDAO implements EstadoUsuarioDAO {

    public EstadoUsuarioSqlServerDAO(Connection conexion) {
        super(conexion);
    }

    @Override
    public EstadoUsuarioEntidad consultarPorId(UUID id) {
        var sentenciaSql = "select id_estado_usuario, nombre from estado_usuario where id_estado_usuario = ?";
        //si no existe ninguno con ese id, se devuelve un estado vacio (nunca null)
        var estadoUsuarioEncontrado = new EstadoUsuarioEntidad.Builder().build();
        try (var sentencia = getConexion().prepareStatement(sentenciaSql)) {
            sentencia.setObject(1, id);

            var resultado = sentencia.executeQuery();
            if (resultado.next()) {
                estadoUsuarioEncontrado = new EstadoUsuarioEntidad.Builder()
                        .id(UUID.fromString(resultado.getString("id_estado_usuario")))
                        .nombre(resultado.getString("nombre"))
                        .build();
            }
        } catch (SQLException excepcion) {
            var mensajeUsuario = CatalogoMensajes.EstadoUsuarioSqlServerDAO.USUARIO_ERROR_PROBLEMA_CONSULTANDO_ESTADO_USUARIO_POR_ID;
            throw BodegappDatosExcepcion.crear(mensajeUsuario, excepcion.getMessage(), excepcion);
        } catch (Exception excepcion) {
            var mensajeUsuario = CatalogoMensajes.EstadoUsuarioSqlServerDAO.USUARIO_ERROR_PROBLEMA_NO_CONTROLADO_CONSULTANDO_ESTADO_USUARIO_POR_ID;
            throw BodegappDatosExcepcion.crear(mensajeUsuario, excepcion.getMessage(), excepcion);
        }
        return estadoUsuarioEncontrado;
    }

    @Override
    public List<EstadoUsuarioEntidad> consultarPorFiltro(EstadoUsuarioEntidad filtro) {
        var cont = 0;
        var estadosUsuarioEncontrados = new ArrayList<EstadoUsuarioEntidad>();
        var sentenciaSql = "select id_estado_usuario, nombre from estado_usuario";
        var parametros = new ArrayList<Object>();
        //solo se filtra por los datos que vengan llenos
        if (!UtilId.VALOR_DEFECTO.equals(filtro.getId())) {
            sentenciaSql = UtilTexto.getUtilTexto().concatenar(sentenciaSql, (cont == 0 ? " where " : " and "), "id_estado_usuario = ?");
            parametros.add(filtro.getId());
            cont = cont + 1;
        }
        if (!UtilTexto.getUtilTexto().esVacia(filtro.getNombre())) {
            sentenciaSql = UtilTexto.getUtilTexto().concatenar(sentenciaSql, (cont == 0 ? " where " : " and "), "nombre = ?");
            parametros.add(filtro.getNombre());
            cont = cont + 1;
        }

        sentenciaSql = UtilTexto.getUtilTexto().concatenar(sentenciaSql, " order by nombre asc");

        try (var sentencia = getConexion().prepareStatement(sentenciaSql)) {

            for (var indice = 0; indice < parametros.size(); indice++) {
                sentencia.setObject(indice + 1, parametros.get(indice));
            }

            var resultado = sentencia.executeQuery();
            while (resultado.next()) {
                var estadoUsuario = new EstadoUsuarioEntidad.Builder()
                        .id(UUID.fromString(resultado.getString("id_estado_usuario")))
                        .nombre(resultado.getString("nombre"))
                        .build();
                estadosUsuarioEncontrados.add(estadoUsuario);
            }
        } catch (SQLException excepcion) {
            var mensajeUsuario = CatalogoMensajes.EstadoUsuarioSqlServerDAO.USUARIO_ERROR_PROBLEMA_CONSULTANDO_ESTADO_USUARIO_POR_FILTRO;
            throw BodegappDatosExcepcion.crear(mensajeUsuario, excepcion.getMessage(), excepcion);
        } catch (Exception excepcion) {
            var mensajeUsuario = CatalogoMensajes.EstadoUsuarioSqlServerDAO.USUARIO_ERROR_PROBLEMA_NO_CONTROLADO_CONSULTANDO_ESTADO_USUARIO_POR_FILTRO;
            throw BodegappDatosExcepcion.crear(mensajeUsuario, excepcion.getMessage(), excepcion);
        }
        return estadosUsuarioEncontrados;
    }

    @Override
    public List<EstadoUsuarioEntidad> consultarTodos() {
        var sentenciaSql = "select id_estado_usuario, nombre from estado_usuario order by nombre asc";
        var estadosUsuarioEncontrados = new ArrayList<EstadoUsuarioEntidad>();

        try (var sentencia = getConexion().prepareStatement(sentenciaSql)) {

            var resultado = sentencia.executeQuery();
            while (resultado.next()) {
                var estadoUsuario = new EstadoUsuarioEntidad.Builder()
                        .id(UUID.fromString(resultado.getString("id_estado_usuario")))
                        .nombre(resultado.getString("nombre"))
                        .build();
                estadosUsuarioEncontrados.add(estadoUsuario);
            }
        } catch (SQLException excepcion) {
            var mensajeUsuario = CatalogoMensajes.EstadoUsuarioSqlServerDAO.USUARIO_ERROR_PROBLEMA_CONSULTANDO_TODOS_LOS_ESTADOS_USUARIO;
            throw BodegappDatosExcepcion.crear(mensajeUsuario, excepcion.getMessage(), excepcion);
        } catch (Exception excepcion) {
            var mensajeUsuario = CatalogoMensajes.EstadoUsuarioSqlServerDAO.USUARIO_ERROR_PROBLEMA_NO_CONTROLADO_CONSULTANDO_TODOS_LOS_ESTADOS_USUARIO;
            throw BodegappDatosExcepcion.crear(mensajeUsuario, excepcion.getMessage(), excepcion);
        }
        return estadosUsuarioEncontrados;
    }
}
