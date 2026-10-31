package co.edu.uco.bodegapp.dao.factoria;

import co.edu.uco.bodegapp.dao.datos.entidad.EstadoUsuarioDAO;
import co.edu.uco.bodegapp.dao.datos.entidad.HistorialUsuarioDAO;
import co.edu.uco.bodegapp.dao.datos.entidad.RolDAO;
import co.edu.uco.bodegapp.dao.datos.entidad.UsuarioDAO;
import co.edu.uco.bodegapp.dao.factoria.enums.FactoriaEnum;
import co.edu.uco.bodegapp.dao.factoria.impl.SqlServerDAOFactory;
import co.edu.uco.bodegapp.transversal.catalogo.CatalogoMensajes;
import co.edu.uco.bodegapp.transversal.excepciones.BodegappDatosExcepcion;
import co.edu.uco.bodegapp.transversal.utilitario.UtilSql;

import java.sql.Connection;
import java.sql.SQLException;

public abstract class DAOFactory {
    private Connection conexion;
    private static FactoriaEnum factoria = FactoriaEnum.SQLSERVER;

    //entrega la factoria de la base de datos con la que trabaja el proyecto
    public static DAOFactory obtenerFactoria() {
        switch (factoria) {
            case SQLSERVER:
                return new SqlServerDAOFactory();
            default:
                var mensajeUsuario = CatalogoMensajes.DAOFactory.USUARIO_ERROR_FUENTE_INFORMACION_NO_HABILITADA;
                throw BodegappDatosExcepcion.crear(mensajeUsuario);
        }
    }

    //de esta manera garantizo que cada que cree una nueva afctoria todas deben abrir la conexion

    protected DAOFactory(){
        abrirConexion();
    }

    protected Connection getConexion(){
        return conexion;
    }
    protected void setConexion(Connection conexion){

        if (conexion == null) {
            var mensajeUsuario = CatalogoMensajes.DAOFactory.USUARIO_ERROR_CONEXION_NULA;
            throw BodegappDatosExcepcion.crear(mensajeUsuario);
        }
        try {
            if (conexion.isClosed()) {
                var mensajeUsuario = CatalogoMensajes.DAOFactory.USUARIO_ERROR_CONEXION_CERRADA;
                throw BodegappDatosExcepcion.crear(mensajeUsuario);
            }
        } catch (SQLException excepcion) {
            var mensajeUsuario = CatalogoMensajes.DAOFactory.USUARIO_ERROR_PROBLEMA_VALIDANDO_CONEXION;
            throw BodegappDatosExcepcion.crear(mensajeUsuario, excepcion.getMessage(), excepcion);
        }
        this.conexion= conexion;
    }
    protected abstract void abrirConexion();

    public void cerrarConexion(){
        UtilSql.cerrarConexion(conexion);
    }
    public void iniciarTransaccion(){
        UtilSql.iniciarTransaccion(conexion);
    }
    public void confirmarTransaccion(){
        UtilSql.confirmarTransaccion(conexion);
    }
    public void cancelarTransaccion(){
        UtilSql.cancelarTransaccion(conexion);
    }

    //va a fabricar los DAO de las entidades

    public abstract RolDAO obtenerRolDAO();

    public abstract EstadoUsuarioDAO obtenerEstadoUsuarioDAO();

    public abstract UsuarioDAO obtenerUsuarioDAO();

    public abstract HistorialUsuarioDAO obtenerHistorialUsuarioDAO();

}
