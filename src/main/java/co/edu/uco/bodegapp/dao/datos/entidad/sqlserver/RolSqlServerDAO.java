package co.edu.uco.bodegapp.dao.datos.entidad.sqlserver;

import co.edu.uco.bodegapp.dao.datos.entidad.RolDAO;
import co.edu.uco.bodegapp.dao.datos.entidad.SqlDAO;
import co.edu.uco.bodegapp.entidad.RolEntidad;
import co.edu.uco.bodegapp.transversal.catalogo.CatalogoMensajes;
import co.edu.uco.bodegapp.transversal.excepciones.BodegappDatosExcepcion;
import co.edu.uco.bodegapp.transversal.utilitario.UtilId;
import co.edu.uco.bodegapp.transversal.utilitario.UtilTexto;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class RolSqlServerDAO extends SqlDAO implements RolDAO {

    public RolSqlServerDAO(Connection conexion) {
        super(conexion);
    }

    @Override
    public RolEntidad consultarPorId(UUID id) {
        var sentenciaSql = "select id_rol, nombre from rol where id_rol = ?";
        //si no existe ninguno con ese id, se devuelve un rol vacio (nunca null)
        var rolEncontrado = new RolEntidad.Builder().build();
        try (var sentencia = getConexion().prepareStatement(sentenciaSql)) {
            sentencia.setObject(1, id);

            var resultado = sentencia.executeQuery();
            if (resultado.next()) {
                rolEncontrado = new RolEntidad.Builder()
                        .id(UUID.fromString(resultado.getString("id_rol")))
                        .nombre(resultado.getString("nombre"))
                        .build();
            }
        } catch (SQLException excepcion) {
            var mensajeUsuario = CatalogoMensajes.RolSqlServerDAO.USUARIO_ERROR_PROBLEMA_CONSULTANDO_ROL_POR_ID;
            throw BodegappDatosExcepcion.crear(mensajeUsuario, excepcion.getMessage(), excepcion);
        } catch (Exception excepcion) {
            var mensajeUsuario = CatalogoMensajes.RolSqlServerDAO.USUARIO_ERROR_PROBLEMA_NO_CONTROLADO_CONSULTANDO_ROL_POR_ID;
            throw BodegappDatosExcepcion.crear(mensajeUsuario, excepcion.getMessage(), excepcion);
        }
        return rolEncontrado;
    }

    @Override
    public List<RolEntidad> consultarPorFiltro(RolEntidad filtro) {
        var cont = 0;
        var rolesEncontrados = new ArrayList<RolEntidad>();
        var sentenciaSql = "select id_rol, nombre from rol";
        var parametros = new ArrayList<Object>();
        //solo se filtra por los datos que vengan llenos
        if (!UtilId.VALOR_DEFECTO.equals(filtro.getId())) {
            sentenciaSql = UtilTexto.getUtilTexto().concatenar(sentenciaSql, (cont == 0 ? " where " : " and "), "id_rol = ?");
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
                var rol = new RolEntidad.Builder()
                        .id(UUID.fromString(resultado.getString("id_rol")))
                        .nombre(resultado.getString("nombre"))
                        .build();
                rolesEncontrados.add(rol);
            }
        } catch (SQLException excepcion) {
            var mensajeUsuario = CatalogoMensajes.RolSqlServerDAO.USUARIO_ERROR_PROBLEMA_CONSULTANDO_ROL_POR_FILTRO;
            throw BodegappDatosExcepcion.crear(mensajeUsuario, excepcion.getMessage(), excepcion);
        } catch (Exception excepcion) {
            var mensajeUsuario = CatalogoMensajes.RolSqlServerDAO.USUARIO_ERROR_PROBLEMA_NO_CONTROLADO_CONSULTANDO_ROL_POR_FILTRO;
            throw BodegappDatosExcepcion.crear(mensajeUsuario, excepcion.getMessage(), excepcion);
        }
        return rolesEncontrados;
    }

    @Override
    public List<RolEntidad> consultarTodos() {
        var sentenciaSql = "select id_rol, nombre from rol order by nombre asc";
        var rolesEncontrados = new ArrayList<RolEntidad>();

        try (var sentencia = getConexion().prepareStatement(sentenciaSql)) {

            var resultado = sentencia.executeQuery();
            while (resultado.next()) {
                var rol = new RolEntidad.Builder()
                        .id(UUID.fromString(resultado.getString("id_rol")))
                        .nombre(resultado.getString("nombre"))
                        .build();
                rolesEncontrados.add(rol);
            }
        } catch (SQLException excepcion) {
            var mensajeUsuario = CatalogoMensajes.RolSqlServerDAO.USUARIO_ERROR_PROBLEMA_CONSULTANDO_TODOS_LOS_ROLES;
            throw BodegappDatosExcepcion.crear(mensajeUsuario, excepcion.getMessage(), excepcion);
        } catch (Exception excepcion) {
            var mensajeUsuario = CatalogoMensajes.RolSqlServerDAO.USUARIO_ERROR_PROBLEMA_NO_CONTROLADO_CONSULTANDO_TODOS_LOS_ROLES;
            throw BodegappDatosExcepcion.crear(mensajeUsuario, excepcion.getMessage(), excepcion);
        }
        return rolesEncontrados;
    }
}
