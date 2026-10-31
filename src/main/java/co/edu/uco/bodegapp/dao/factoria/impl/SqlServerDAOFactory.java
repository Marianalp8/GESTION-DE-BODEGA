package co.edu.uco.bodegapp.dao.factoria.impl;

import co.edu.uco.bodegapp.dao.datos.entidad.EstadoUsuarioDAO;
import co.edu.uco.bodegapp.dao.datos.entidad.HistorialUsuarioDAO;
import co.edu.uco.bodegapp.dao.datos.entidad.RolDAO;
import co.edu.uco.bodegapp.dao.datos.entidad.UsuarioDAO;
import co.edu.uco.bodegapp.dao.datos.entidad.sqlserver.EstadoUsuarioSqlServerDAO;
import co.edu.uco.bodegapp.dao.datos.entidad.sqlserver.HistorialUsuarioSqlServerDAO;
import co.edu.uco.bodegapp.dao.datos.entidad.sqlserver.RolSqlServerDAO;
import co.edu.uco.bodegapp.dao.datos.entidad.sqlserver.UsuarioSqlServerDAO;
import co.edu.uco.bodegapp.dao.factoria.DAOFactory;
import co.edu.uco.bodegapp.transversal.excepciones.BodegappDatosExcepcion;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import co.edu.uco.bodegapp.transversal.catalogo.CatalogoMensajes;
import java.sql.Connection;
import java.sql.SQLException;

public class SqlServerDAOFactory extends DAOFactory {
    private static final String URL =
            "jdbc:sqlserver://localhost:1433;databaseName=Bodegapp;encrypt=true;trustServerCertificate=true";
    private static final String USUARIO = "bodegappUser";
    private static final String CLAVE = "Bodega2026*";

    private static final HikariDataSource POOL = crearPool();

    private static HikariDataSource crearPool() {
        HikariConfig configuracion = new HikariConfig();
        configuracion.setJdbcUrl(URL);
        configuracion.setUsername(USUARIO);
        configuracion.setPassword(CLAVE);
        configuracion.setMaximumPoolSize(10);
        //si SQL Server no está disponible, no falla al crear el pool sino al pedir la conexión (dentro del try)
        configuracion.setInitializationFailTimeout(-1);
        return new HikariDataSource(configuracion);
    }

    @Override
    protected void abrirConexion() {
        Connection conexion;
        try {
            conexion = POOL.getConnection();
        } catch (SQLException exception) {
            var mensajeUsuario = CatalogoMensajes.SqlServerDAOFactory.USUARIO_ERROR_PROBLEMA_ABRIENDO_CONEXION_SQL_SERVER;
            throw BodegappDatosExcepcion.crear(mensajeUsuario, exception.getMessage(), exception);
        } catch (Exception exception) {
            var mensajeUsuario = CatalogoMensajes.SqlServerDAOFactory.USUARIO_ERROR_PROBLEMA_NO_CONTROLADO_ABRIENDO_CONEXION_SQL_SERVER;
            throw BodegappDatosExcepcion.crear(mensajeUsuario, exception.getMessage(), exception);
        }
        // se lleva a daofactory a la madre
        setConexion(conexion);
    }

    //cada DAO recibe la misma conexion, asi todos trabajan dentro de la misma transaccion

    @Override
    public RolDAO obtenerRolDAO() {
        return new RolSqlServerDAO(getConexion());
    }

    @Override
    public EstadoUsuarioDAO obtenerEstadoUsuarioDAO() {
        return new EstadoUsuarioSqlServerDAO(getConexion());
    }

    @Override
    public UsuarioDAO obtenerUsuarioDAO() {
        return new UsuarioSqlServerDAO(getConexion());
    }

    @Override
    public HistorialUsuarioDAO obtenerHistorialUsuarioDAO() {
        return new HistorialUsuarioSqlServerDAO(getConexion());
    }

}
