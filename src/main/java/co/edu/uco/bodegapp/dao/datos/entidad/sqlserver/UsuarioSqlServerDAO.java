package co.edu.uco.bodegapp.dao.datos.entidad.sqlserver;

import co.edu.uco.bodegapp.dao.datos.entidad.SqlDAO;
import co.edu.uco.bodegapp.dao.datos.entidad.UsuarioDAO;
import co.edu.uco.bodegapp.entidad.EstadoUsuarioEntidad;
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

public class UsuarioSqlServerDAO extends SqlDAO implements UsuarioDAO {

    public UsuarioSqlServerDAO(Connection conexion) {
        super(conexion);
    }

    @Override
    public void crear(UsuarioEntidad entidad) {
        var sentenciaSql = "insert into usuario(id_usuario, nombre, correo, nombre_usuario, contrasena, "
                + "id_rol, id_estado_usuario, fecha_registro) values(?, ?, ?, ?, ?, ?, ?, ?)";
        try (var sentencia = getConexion().prepareStatement(sentenciaSql)) {

            sentencia.setObject(1, entidad.getId());
            sentencia.setString(2, entidad.getNombre());
            sentencia.setString(3, entidad.getCorreo());
            sentencia.setString(4, entidad.getNombreUsuario());
            sentencia.setString(5, entidad.getContrasena());
            sentencia.setObject(6, entidad.getRol().getId());
            sentencia.setObject(7, entidad.getEstadoUsuario().getId());
            sentencia.setObject(8, entidad.getFechaRegistro());
            sentencia.executeUpdate();
        } catch (SQLException excepcion) {
            var mensajeUsuario = CatalogoMensajes.UsuarioSqlServerDAO.USUARIO_ERROR_PROBLEMA_CREANDO_USUARIO;
            throw BodegappDatosExcepcion.crear(mensajeUsuario, excepcion.getMessage(), excepcion);
        } catch (Exception excepcion) {
            var mensajeUsuario = CatalogoMensajes.UsuarioSqlServerDAO.USUARIO_ERROR_PROBLEMA_NO_CONTROLADO_CREANDO_USUARIO;
            throw BodegappDatosExcepcion.crear(mensajeUsuario, excepcion.getMessage(), excepcion);
        }
    }

    @Override
    public UsuarioEntidad consultarPorId(UUID id) {
        var sentenciaSql = "select u.id_usuario, u.nombre, u.correo, u.nombre_usuario, u.contrasena, u.fecha_registro, "
                + "r.id_rol, r.nombre as nombre_rol, e.id_estado_usuario, e.nombre as nombre_estado_usuario "
                + "from usuario as u "
                + "inner join rol as r on u.id_rol = r.id_rol "
                + "inner join estado_usuario as e on u.id_estado_usuario = e.id_estado_usuario "
                + "where u.id_usuario = ?";
        //si no existe ninguno con ese id, se devuelve un usuario vacio (nunca null)
        var usuarioEncontrado = new UsuarioEntidad.Builder().build();
        try (var sentencia = getConexion().prepareStatement(sentenciaSql)) {
            sentencia.setObject(1, id);

            var resultado = sentencia.executeQuery();
            if (resultado.next()) {
                usuarioEncontrado = new UsuarioEntidad.Builder()
                        .id(UUID.fromString(resultado.getString("id_usuario")))
                        .nombre(resultado.getString("nombre"))
                        .correo(resultado.getString("correo"))
                        .nombreUsuario(resultado.getString("nombre_usuario"))
                        .contrasena(resultado.getString("contrasena"))
                        .rol(new RolEntidad.Builder()
                                .id(UUID.fromString(resultado.getString("id_rol")))
                                .nombre(resultado.getString("nombre_rol"))
                                .build())
                        .estadoUsuario(new EstadoUsuarioEntidad.Builder()
                                .id(UUID.fromString(resultado.getString("id_estado_usuario")))
                                .nombre(resultado.getString("nombre_estado_usuario"))
                                .build())
                        .fechaRegistro(resultado.getObject("fecha_registro", LocalDateTime.class))
                        .build();
            }
        } catch (SQLException excepcion) {
            var mensajeUsuario = CatalogoMensajes.UsuarioSqlServerDAO.USUARIO_ERROR_PROBLEMA_CONSULTANDO_USUARIO_POR_ID;
            throw BodegappDatosExcepcion.crear(mensajeUsuario, excepcion.getMessage(), excepcion);
        } catch (Exception excepcion) {
            var mensajeUsuario = CatalogoMensajes.UsuarioSqlServerDAO.USUARIO_ERROR_PROBLEMA_NO_CONTROLADO_CONSULTANDO_USUARIO_POR_ID;
            throw BodegappDatosExcepcion.crear(mensajeUsuario, excepcion.getMessage(), excepcion);
        }
        return usuarioEncontrado;
    }

    @Override
    public List<UsuarioEntidad> consultarPorFiltro(UsuarioEntidad filtro) {
        var cont = 0;
        var usuariosEncontrados = new ArrayList<UsuarioEntidad>();
        var sentenciaSql = "select u.id_usuario, u.nombre, u.correo, u.nombre_usuario, u.contrasena, u.fecha_registro, "
                + "r.id_rol, r.nombre as nombre_rol, e.id_estado_usuario, e.nombre as nombre_estado_usuario "
                + "from usuario as u "
                + "inner join rol as r on u.id_rol = r.id_rol "
                + "inner join estado_usuario as e on u.id_estado_usuario = e.id_estado_usuario ";
        var parametros = new ArrayList<Object>();
        //solo se filtra por los datos que vengan llenos
        if (!UtilId.VALOR_DEFECTO.equals(filtro.getId())) {
            sentenciaSql = UtilTexto.getUtilTexto().concatenar(sentenciaSql, (cont == 0 ? " where " : " and "), "u.id_usuario = ?");
            parametros.add(filtro.getId());
            cont = cont + 1;
        }
        if (!UtilTexto.getUtilTexto().esVacia(filtro.getNombre())) {
            sentenciaSql = UtilTexto.getUtilTexto().concatenar(sentenciaSql, (cont == 0 ? " where " : " and "), "u.nombre = ?");
            parametros.add(filtro.getNombre());
            cont = cont + 1;
        }
        if (!UtilTexto.getUtilTexto().esVacia(filtro.getCorreo())) {
            sentenciaSql = UtilTexto.getUtilTexto().concatenar(sentenciaSql, (cont == 0 ? " where " : " and "), "u.correo = ?");
            parametros.add(filtro.getCorreo());
            cont = cont + 1;
        }
        if (!UtilTexto.getUtilTexto().esVacia(filtro.getNombreUsuario())) {
            sentenciaSql = UtilTexto.getUtilTexto().concatenar(sentenciaSql, (cont == 0 ? " where " : " and "), "u.nombre_usuario = ?");
            parametros.add(filtro.getNombreUsuario());
            cont = cont + 1;
        }

        if (!UtilId.VALOR_DEFECTO.equals(filtro.getRol().getId())) {
            sentenciaSql = UtilTexto.getUtilTexto().concatenar(sentenciaSql, (cont == 0 ? " where " : " and "), "r.id_rol = ?");
            parametros.add(filtro.getRol().getId());
            cont = cont + 1;
        }
        if (!UtilTexto.getUtilTexto().esVacia(filtro.getRol().getNombre())) {
            sentenciaSql = UtilTexto.getUtilTexto().concatenar(sentenciaSql, (cont == 0 ? " where " : " and "), "r.nombre = ?");
            parametros.add(filtro.getRol().getNombre());
            cont = cont + 1;
        }

        if (!UtilId.VALOR_DEFECTO.equals(filtro.getEstadoUsuario().getId())) {
            sentenciaSql = UtilTexto.getUtilTexto().concatenar(sentenciaSql, (cont == 0 ? " where " : " and "), "e.id_estado_usuario = ?");
            parametros.add(filtro.getEstadoUsuario().getId());
            cont = cont + 1;
        }
        if (!UtilTexto.getUtilTexto().esVacia(filtro.getEstadoUsuario().getNombre())) {
            sentenciaSql = UtilTexto.getUtilTexto().concatenar(sentenciaSql, (cont == 0 ? " where " : " and "), "e.nombre = ?");
            parametros.add(filtro.getEstadoUsuario().getNombre());
            cont = cont + 1;
        }

        sentenciaSql = UtilTexto.getUtilTexto().concatenar(sentenciaSql, " order by u.nombre asc");

        try (var sentencia = getConexion().prepareStatement(sentenciaSql)) {

            for (var indice = 0; indice < parametros.size(); indice++) {
                sentencia.setObject(indice + 1, parametros.get(indice));
            }

            var resultado = sentencia.executeQuery();
            while (resultado.next()) {
                var usuario = new UsuarioEntidad.Builder()
                        .id(UUID.fromString(resultado.getString("id_usuario")))
                        .nombre(resultado.getString("nombre"))
                        .correo(resultado.getString("correo"))
                        .nombreUsuario(resultado.getString("nombre_usuario"))
                        .contrasena(resultado.getString("contrasena"))
                        .rol(new RolEntidad.Builder()
                                .id(UUID.fromString(resultado.getString("id_rol")))
                                .nombre(resultado.getString("nombre_rol"))
                                .build())
                        .estadoUsuario(new EstadoUsuarioEntidad.Builder()
                                .id(UUID.fromString(resultado.getString("id_estado_usuario")))
                                .nombre(resultado.getString("nombre_estado_usuario"))
                                .build())
                        .fechaRegistro(resultado.getObject("fecha_registro", LocalDateTime.class))
                        .build();
                usuariosEncontrados.add(usuario);
            }
        } catch (SQLException excepcion) {
            var mensajeUsuario = CatalogoMensajes.UsuarioSqlServerDAO.USUARIO_ERROR_PROBLEMA_CONSULTANDO_USUARIO_POR_FILTRO;
            throw BodegappDatosExcepcion.crear(mensajeUsuario, excepcion.getMessage(), excepcion);
        } catch (Exception excepcion) {
            var mensajeUsuario = CatalogoMensajes.UsuarioSqlServerDAO.USUARIO_ERROR_PROBLEMA_NO_CONTROLADO_CONSULTANDO_USUARIO_POR_FILTRO;
            throw BodegappDatosExcepcion.crear(mensajeUsuario, excepcion.getMessage(), excepcion);
        }
        return usuariosEncontrados;
    }

    @Override
    public List<UsuarioEntidad> consultarTodos() {
        var sentenciaSql = "select u.id_usuario, u.nombre, u.correo, u.nombre_usuario, u.contrasena, u.fecha_registro, "
                + "r.id_rol, r.nombre as nombre_rol, e.id_estado_usuario, e.nombre as nombre_estado_usuario "
                + "from usuario as u "
                + "inner join rol as r on u.id_rol = r.id_rol "
                + "inner join estado_usuario as e on u.id_estado_usuario = e.id_estado_usuario "
                + "order by u.nombre asc";
        var usuariosEncontrados = new ArrayList<UsuarioEntidad>();

        try (var sentencia = getConexion().prepareStatement(sentenciaSql)) {

            var resultado = sentencia.executeQuery();
            while (resultado.next()) {
                var usuario = new UsuarioEntidad.Builder()
                        .id(UUID.fromString(resultado.getString("id_usuario")))
                        .nombre(resultado.getString("nombre"))
                        .correo(resultado.getString("correo"))
                        .nombreUsuario(resultado.getString("nombre_usuario"))
                        .contrasena(resultado.getString("contrasena"))
                        .rol(new RolEntidad.Builder()
                                .id(UUID.fromString(resultado.getString("id_rol")))
                                .nombre(resultado.getString("nombre_rol"))
                                .build())
                        .estadoUsuario(new EstadoUsuarioEntidad.Builder()
                                .id(UUID.fromString(resultado.getString("id_estado_usuario")))
                                .nombre(resultado.getString("nombre_estado_usuario"))
                                .build())
                        .fechaRegistro(resultado.getObject("fecha_registro", LocalDateTime.class))
                        .build();
                usuariosEncontrados.add(usuario);
            }
        } catch (SQLException excepcion) {
            var mensajeUsuario = CatalogoMensajes.UsuarioSqlServerDAO.USUARIO_ERROR_PROBLEMA_CONSULTANDO_TODOS_LOS_USUARIOS;
            throw BodegappDatosExcepcion.crear(mensajeUsuario, excepcion.getMessage(), excepcion);
        } catch (Exception excepcion) {
            var mensajeUsuario = CatalogoMensajes.UsuarioSqlServerDAO.USUARIO_ERROR_PROBLEMA_NO_CONTROLADO_CONSULTANDO_TODOS_LOS_USUARIOS;
            throw BodegappDatosExcepcion.crear(mensajeUsuario, excepcion.getMessage(), excepcion);
        }
        return usuariosEncontrados;
    }

    //la fecha de registro no se actualiza: es el dia en que la persona se registro
    @Override
    public void actualizar(UUID id, UsuarioEntidad entidad) {
        var sentenciaSql = "update usuario set nombre = ?, correo = ?, nombre_usuario = ?, contrasena = ?, "
                + "id_rol = ?, id_estado_usuario = ? where id_usuario = ?";
        try (var sentencia = getConexion().prepareStatement(sentenciaSql)) {

            sentencia.setString(1, entidad.getNombre());
            sentencia.setString(2, entidad.getCorreo());
            sentencia.setString(3, entidad.getNombreUsuario());
            sentencia.setString(4, entidad.getContrasena());
            sentencia.setObject(5, entidad.getRol().getId());
            sentencia.setObject(6, entidad.getEstadoUsuario().getId());
            sentencia.setObject(7, id);
            sentencia.executeUpdate();
        } catch (SQLException excepcion) {
            var mensajeUsuario = CatalogoMensajes.UsuarioSqlServerDAO.USUARIO_ERROR_PROBLEMA_ACTUALIZANDO_USUARIO;
            throw BodegappDatosExcepcion.crear(mensajeUsuario, excepcion.getMessage(), excepcion);
        } catch (Exception excepcion) {
            var mensajeUsuario = CatalogoMensajes.UsuarioSqlServerDAO.USUARIO_ERROR_PROBLEMA_NO_CONTROLADO_ACTUALIZANDO_USUARIO;
            throw BodegappDatosExcepcion.crear(mensajeUsuario, excepcion.getMessage(), excepcion);
        }
    }
}
