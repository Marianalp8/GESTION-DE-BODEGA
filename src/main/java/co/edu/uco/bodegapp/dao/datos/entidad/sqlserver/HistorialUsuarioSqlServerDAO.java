package co.edu.uco.bodegapp.dao.datos.entidad.sqlserver;

import co.edu.uco.bodegapp.dao.datos.entidad.HistorialUsuarioDAO;
import co.edu.uco.bodegapp.dao.datos.entidad.SqlDAO;
import co.edu.uco.bodegapp.entidad.EstadoUsuarioEntidad;
import co.edu.uco.bodegapp.entidad.HistorialUsuarioEntidad;
import co.edu.uco.bodegapp.entidad.RolEntidad;
import co.edu.uco.bodegapp.entidad.UsuarioEntidad;
import co.edu.uco.bodegapp.transversal.catalogo.CatalogoMensajes;
import co.edu.uco.bodegapp.transversal.excepciones.BodegappDatosExcepcion;
import co.edu.uco.bodegapp.transversal.utilitario.UtilId;
import co.edu.uco.bodegapp.transversal.utilitario.UtilTexto;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class HistorialUsuarioSqlServerDAO extends SqlDAO implements HistorialUsuarioDAO {

    public HistorialUsuarioSqlServerDAO(Connection conexion) {
        super(conexion);
    }

    @Override
    public void crear(HistorialUsuarioEntidad entidad) {
        var sentenciaSql = "insert into historial_usuario(id_historial_usuario, id_usuario, id_administrador, "
                + "id_rol, id_estado_usuario, fecha) values(?, ?, ?, ?, ?, ?)";
        try (var sentencia = getConexion().prepareStatement(sentenciaSql)) {

            sentencia.setObject(1, entidad.getId());
            sentencia.setObject(2, entidad.getUsuario().getId());
            sentencia.setObject(3, entidad.getAdministrador().getId());
            sentencia.setObject(4, entidad.getRol().getId());
            sentencia.setObject(5, entidad.getEstadoUsuario().getId());
            sentencia.setObject(6, entidad.getFecha());
            sentencia.executeUpdate();
        } catch (SQLException excepcion) {
            var mensajeUsuario = CatalogoMensajes.HistorialUsuarioSqlServerDAO.USUARIO_ERROR_PROBLEMA_CREANDO_HISTORIAL_USUARIO;
            throw BodegappDatosExcepcion.crear(mensajeUsuario, excepcion.getMessage(), excepcion);
        } catch (Exception excepcion) {
            var mensajeUsuario = CatalogoMensajes.HistorialUsuarioSqlServerDAO.USUARIO_ERROR_PROBLEMA_NO_CONTROLADO_CREANDO_HISTORIAL_USUARIO;
            throw BodegappDatosExcepcion.crear(mensajeUsuario, excepcion.getMessage(), excepcion);
        }
    }

    @Override
    public HistorialUsuarioEntidad consultarPorId(UUID id) {
        var sentenciaSql = "select h.id_historial_usuario, h.fecha, "
                + "u.id_usuario, u.nombre as nombre_del_usuario, u.correo as correo_usuario, u.nombre_usuario, "
                + "a.id_usuario as id_administrador, a.nombre as nombre_del_administrador, "
                + "a.correo as correo_administrador, a.nombre_usuario as nombre_usuario_administrador, "
                + "r.id_rol, r.nombre as nombre_rol, e.id_estado_usuario, e.nombre as nombre_estado_usuario "
                + "from historial_usuario as h "
                + "inner join usuario as u on h.id_usuario = u.id_usuario "
                + "inner join usuario as a on h.id_administrador = a.id_usuario "
                + "inner join rol as r on h.id_rol = r.id_rol "
                + "inner join estado_usuario as e on h.id_estado_usuario = e.id_estado_usuario "
                + "where h.id_historial_usuario = ?";
        //si no existe ninguno con ese id, se devuelve un historial vacio (nunca null)
        var historialEncontrado = new HistorialUsuarioEntidad.Builder().build();
        try (var sentencia = getConexion().prepareStatement(sentenciaSql)) {
            sentencia.setObject(1, id);

            var resultado = sentencia.executeQuery();
            if (resultado.next()) {
                historialEncontrado = new HistorialUsuarioEntidad.Builder()
                        .id(UUID.fromString(resultado.getString("id_historial_usuario")))
                        .usuario(new UsuarioEntidad.Builder()
                                .id(UUID.fromString(resultado.getString("id_usuario")))
                                .nombre(resultado.getString("nombre_del_usuario"))
                                .correo(resultado.getString("correo_usuario"))
                                .nombreUsuario(resultado.getString("nombre_usuario"))
                                .build())
                        .administrador(new UsuarioEntidad.Builder()
                                .id(UUID.fromString(resultado.getString("id_administrador")))
                                .nombre(resultado.getString("nombre_del_administrador"))
                                .correo(resultado.getString("correo_administrador"))
                                .nombreUsuario(resultado.getString("nombre_usuario_administrador"))
                                .build())
                        .rol(new RolEntidad.Builder()
                                .id(UUID.fromString(resultado.getString("id_rol")))
                                .nombre(resultado.getString("nombre_rol"))
                                .build())
                        .estadoUsuario(new EstadoUsuarioEntidad.Builder()
                                .id(UUID.fromString(resultado.getString("id_estado_usuario")))
                                .nombre(resultado.getString("nombre_estado_usuario"))
                                .build())
                        .fecha(resultado.getObject("fecha", LocalDateTime.class))
                        .build();
            }
        } catch (SQLException excepcion) {
            var mensajeUsuario = CatalogoMensajes.HistorialUsuarioSqlServerDAO.USUARIO_ERROR_PROBLEMA_CONSULTANDO_HISTORIAL_USUARIO_POR_ID;
            throw BodegappDatosExcepcion.crear(mensajeUsuario, excepcion.getMessage(), excepcion);
        } catch (Exception excepcion) {
            var mensajeUsuario = CatalogoMensajes.HistorialUsuarioSqlServerDAO.USUARIO_ERROR_PROBLEMA_NO_CONTROLADO_CONSULTANDO_HISTORIAL_USUARIO_POR_ID;
            throw BodegappDatosExcepcion.crear(mensajeUsuario, excepcion.getMessage(), excepcion);
        }
        return historialEncontrado;
    }

    @Override
    public List<HistorialUsuarioEntidad> consultarPorFiltro(HistorialUsuarioEntidad filtro) {
        var cont = 0;
        var historialesEncontrados = new ArrayList<HistorialUsuarioEntidad>();
        var sentenciaSql = "select h.id_historial_usuario, h.fecha, "
                + "u.id_usuario, u.nombre as nombre_del_usuario, u.correo as correo_usuario, u.nombre_usuario, "
                + "a.id_usuario as id_administrador, a.nombre as nombre_del_administrador, "
                + "a.correo as correo_administrador, a.nombre_usuario as nombre_usuario_administrador, "
                + "r.id_rol, r.nombre as nombre_rol, e.id_estado_usuario, e.nombre as nombre_estado_usuario "
                + "from historial_usuario as h "
                + "inner join usuario as u on h.id_usuario = u.id_usuario "
                + "inner join usuario as a on h.id_administrador = a.id_usuario "
                + "inner join rol as r on h.id_rol = r.id_rol "
                + "inner join estado_usuario as e on h.id_estado_usuario = e.id_estado_usuario ";
        var parametros = new ArrayList<Object>();
        //solo se filtra por los datos que vengan llenos
        if (!UtilId.VALOR_DEFECTO.equals(filtro.getId())) {
            sentenciaSql = UtilTexto.getUtilTexto().concatenar(sentenciaSql, (cont == 0 ? " where " : " and "), "h.id_historial_usuario = ?");
            parametros.add(filtro.getId());
            cont = cont + 1;
        }
        if (!UtilId.VALOR_DEFECTO.equals(filtro.getUsuario().getId())) {
            sentenciaSql = UtilTexto.getUtilTexto().concatenar(sentenciaSql, (cont == 0 ? " where " : " and "), "h.id_usuario = ?");
            parametros.add(filtro.getUsuario().getId());
            cont = cont + 1;
        }
        if (!UtilId.VALOR_DEFECTO.equals(filtro.getAdministrador().getId())) {
            sentenciaSql = UtilTexto.getUtilTexto().concatenar(sentenciaSql, (cont == 0 ? " where " : " and "), "h.id_administrador = ?");
            parametros.add(filtro.getAdministrador().getId());
            cont = cont + 1;
        }
        if (!UtilId.VALOR_DEFECTO.equals(filtro.getRol().getId())) {
            sentenciaSql = UtilTexto.getUtilTexto().concatenar(sentenciaSql, (cont == 0 ? " where " : " and "), "h.id_rol = ?");
            parametros.add(filtro.getRol().getId());
            cont = cont + 1;
        }
        if (!UtilId.VALOR_DEFECTO.equals(filtro.getEstadoUsuario().getId())) {
            sentenciaSql = UtilTexto.getUtilTexto().concatenar(sentenciaSql, (cont == 0 ? " where " : " and "), "h.id_estado_usuario = ?");
            parametros.add(filtro.getEstadoUsuario().getId());
            cont = cont + 1;
        }

        //primero lo mas reciente
        sentenciaSql = UtilTexto.getUtilTexto().concatenar(sentenciaSql, " order by h.fecha desc");

        try (var sentencia = getConexion().prepareStatement(sentenciaSql)) {

            for (var indice = 0; indice < parametros.size(); indice++) {
                sentencia.setObject(indice + 1, parametros.get(indice));
            }

            var resultado = sentencia.executeQuery();
            while (resultado.next()) {
                var historialUsuario = new HistorialUsuarioEntidad.Builder()
                        .id(UUID.fromString(resultado.getString("id_historial_usuario")))
                        .usuario(new UsuarioEntidad.Builder()
                                .id(UUID.fromString(resultado.getString("id_usuario")))
                                .nombre(resultado.getString("nombre_del_usuario"))
                                .correo(resultado.getString("correo_usuario"))
                                .nombreUsuario(resultado.getString("nombre_usuario"))
                                .build())
                        .administrador(new UsuarioEntidad.Builder()
                                .id(UUID.fromString(resultado.getString("id_administrador")))
                                .nombre(resultado.getString("nombre_del_administrador"))
                                .correo(resultado.getString("correo_administrador"))
                                .nombreUsuario(resultado.getString("nombre_usuario_administrador"))
                                .build())
                        .rol(new RolEntidad.Builder()
                                .id(UUID.fromString(resultado.getString("id_rol")))
                                .nombre(resultado.getString("nombre_rol"))
                                .build())
                        .estadoUsuario(new EstadoUsuarioEntidad.Builder()
                                .id(UUID.fromString(resultado.getString("id_estado_usuario")))
                                .nombre(resultado.getString("nombre_estado_usuario"))
                                .build())
                        .fecha(resultado.getObject("fecha", LocalDateTime.class))
                        .build();
                historialesEncontrados.add(historialUsuario);
            }
        } catch (SQLException excepcion) {
            var mensajeUsuario = CatalogoMensajes.HistorialUsuarioSqlServerDAO.USUARIO_ERROR_PROBLEMA_CONSULTANDO_HISTORIAL_USUARIO_POR_FILTRO;
            throw BodegappDatosExcepcion.crear(mensajeUsuario, excepcion.getMessage(), excepcion);
        } catch (Exception excepcion) {
            var mensajeUsuario = CatalogoMensajes.HistorialUsuarioSqlServerDAO.USUARIO_ERROR_PROBLEMA_NO_CONTROLADO_CONSULTANDO_HISTORIAL_USUARIO_POR_FILTRO;
            throw BodegappDatosExcepcion.crear(mensajeUsuario, excepcion.getMessage(), excepcion);
        }
        return historialesEncontrados;
    }

    @Override
    public List<HistorialUsuarioEntidad> consultarTodos() {
        var sentenciaSql = "select h.id_historial_usuario, h.fecha, "
                + "u.id_usuario, u.nombre as nombre_del_usuario, u.correo as correo_usuario, u.nombre_usuario, "
                + "a.id_usuario as id_administrador, a.nombre as nombre_del_administrador, "
                + "a.correo as correo_administrador, a.nombre_usuario as nombre_usuario_administrador, "
                + "r.id_rol, r.nombre as nombre_rol, e.id_estado_usuario, e.nombre as nombre_estado_usuario "
                + "from historial_usuario as h "
                + "inner join usuario as u on h.id_usuario = u.id_usuario "
                + "inner join usuario as a on h.id_administrador = a.id_usuario "
                + "inner join rol as r on h.id_rol = r.id_rol "
                + "inner join estado_usuario as e on h.id_estado_usuario = e.id_estado_usuario "
                + "order by h.fecha desc";
        var historialesEncontrados = new ArrayList<HistorialUsuarioEntidad>();

        try (var sentencia = getConexion().prepareStatement(sentenciaSql)) {

            var resultado = sentencia.executeQuery();
            while (resultado.next()) {
                var historialUsuario = new HistorialUsuarioEntidad.Builder()
                        .id(UUID.fromString(resultado.getString("id_historial_usuario")))
                        .usuario(new UsuarioEntidad.Builder()
                                .id(UUID.fromString(resultado.getString("id_usuario")))
                                .nombre(resultado.getString("nombre_del_usuario"))
                                .correo(resultado.getString("correo_usuario"))
                                .nombreUsuario(resultado.getString("nombre_usuario"))
                                .build())
                        .administrador(new UsuarioEntidad.Builder()
                                .id(UUID.fromString(resultado.getString("id_administrador")))
                                .nombre(resultado.getString("nombre_del_administrador"))
                                .correo(resultado.getString("correo_administrador"))
                                .nombreUsuario(resultado.getString("nombre_usuario_administrador"))
                                .build())
                        .rol(new RolEntidad.Builder()
                                .id(UUID.fromString(resultado.getString("id_rol")))
                                .nombre(resultado.getString("nombre_rol"))
                                .build())
                        .estadoUsuario(new EstadoUsuarioEntidad.Builder()
                                .id(UUID.fromString(resultado.getString("id_estado_usuario")))
                                .nombre(resultado.getString("nombre_estado_usuario"))
                                .build())
                        .fecha(resultado.getObject("fecha", LocalDateTime.class))
                        .build();
                historialesEncontrados.add(historialUsuario);
            }
        } catch (SQLException excepcion) {
            var mensajeUsuario = CatalogoMensajes.HistorialUsuarioSqlServerDAO.USUARIO_ERROR_PROBLEMA_CONSULTANDO_TODOS_LOS_HISTORIALES_USUARIO;
            throw BodegappDatosExcepcion.crear(mensajeUsuario, excepcion.getMessage(), excepcion);
        } catch (Exception excepcion) {
            var mensajeUsuario = CatalogoMensajes.HistorialUsuarioSqlServerDAO.USUARIO_ERROR_PROBLEMA_NO_CONTROLADO_CONSULTANDO_TODOS_LOS_HISTORIALES_USUARIO;
            throw BodegappDatosExcepcion.crear(mensajeUsuario, excepcion.getMessage(), excepcion);
        }
        return historialesEncontrados;
    }
}
