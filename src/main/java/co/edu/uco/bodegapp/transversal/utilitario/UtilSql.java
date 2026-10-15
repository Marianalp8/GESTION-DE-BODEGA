package co.edu.uco.bodegapp.transversal.utilitario;

import co.edu.uco.bodegapp.transversal.catalogo.CatalogoMensajes;
import co.edu.uco.bodegapp.transversal.excepciones.BodegappTransversalExcepcion;

import java.sql.Connection;
import java.sql.SQLException;

public class UtilSql {

    private UtilSql() {
    }

    public static boolean conexionEstaAbierta(Connection conexion) {
        try {
            return !conexionEstaVacia(conexion) && !conexion.isClosed();
        } catch (SQLException exception) {
            var mensajeUsuario = CatalogoMensajes.UtilSql.USUARIO_ERROR_PROBLEMA_VALIDANDO_SI_CONEXION_SQL_ESTA_ABIERTA;
            throw BodegappTransversalExcepcion.crear(mensajeUsuario, exception.getMessage(), exception);
        } catch (Exception exception) {
            var mensajeUsuario = CatalogoMensajes.UtilSql.USUARIO_ERROR_PROBLEMA_NO_CONTROLADO_VALIDANDO_SI_CONEXION_SQL_ESTA_ABIERTA;
            throw BodegappTransversalExcepcion.crear(mensajeUsuario, exception.getMessage(), exception);
        }
    }

    public static void asegurarConexionAbierta(Connection conexion) {
        if (!conexionEstaAbierta(conexion)) {
            var mensajeUsuario = CatalogoMensajes.UtilSql.USUARIO_ERROR_CONEXION_SQL_NO_ESTA_ABIERTA;
            throw BodegappTransversalExcepcion.crear(mensajeUsuario);
        }
    }

    //la conexion debe estar abierta y la transaccion no puede estar iniciada
    public static void iniciarTransaccion(Connection conexion) {
        if (transaccionEstaIniciada(conexion) || !conexionEstaAbierta(conexion)) {
            var mensajeUsuario = CatalogoMensajes.UtilSql.USUARIO_ERROR_NO_ES_POSIBLE_INICIAR_TRANSACCION_SQL;
            throw BodegappTransversalExcepcion.crear(mensajeUsuario);
        } else {
            try {
                conexion.setAutoCommit(false);
            } catch (SQLException exception) {
                var mensajeUsuario = CatalogoMensajes.UtilSql.USUARIO_ERROR_PROBLEMA_INICIANDO_TRANSACCION_SQL;
                throw BodegappTransversalExcepcion.crear(mensajeUsuario, exception.getMessage(), exception);
            } catch (Exception exception) {
                var mensajeUsuario = CatalogoMensajes.UtilSql.USUARIO_ERROR_PROBLEMA_NO_CONTROLADO_INICIANDO_TRANSACCION_SQL;
                throw BodegappTransversalExcepcion.crear(mensajeUsuario, exception.getMessage(), exception);
            }
        }
    }

    //no se puede confirmar algo que no se inicio
    public static void confirmarTransaccion(Connection conexion) {
        if (!transaccionEstaIniciada(conexion)) {
            var mensajeUsuario = CatalogoMensajes.UtilSql.USUARIO_ERROR_NO_ES_POSIBLE_CONFIRMAR_TRANSACCION_SQL;
            throw BodegappTransversalExcepcion.crear(mensajeUsuario);
        } else {
            try {
                conexion.commit();
            } catch (SQLException exception) {
                var mensajeUsuario = CatalogoMensajes.UtilSql.USUARIO_ERROR_PROBLEMA_CONFIRMANDO_TRANSACCION_SQL;
                throw BodegappTransversalExcepcion.crear(mensajeUsuario, exception.getMessage(), exception);
            } catch (Exception exception) {
                var mensajeUsuario = CatalogoMensajes.UtilSql.USUARIO_ERROR_PROBLEMA_NO_CONTROLADO_CONFIRMANDO_TRANSACCION_SQL;
                throw BodegappTransversalExcepcion.crear(mensajeUsuario, exception.getMessage(), exception);
            }
        }
    }

    //no se puede cancelar algo que no se inicio
    public static void cancelarTransaccion(Connection conexion) {
        if (!transaccionEstaIniciada(conexion)) {
            var mensajeUsuario = CatalogoMensajes.UtilSql.USUARIO_ERROR_NO_ES_POSIBLE_CANCELAR_TRANSACCION_SQL;
            throw BodegappTransversalExcepcion.crear(mensajeUsuario);
        } else {
            try {
                conexion.rollback();
            } catch (SQLException exception) {
                var mensajeUsuario = CatalogoMensajes.UtilSql.USUARIO_ERROR_PROBLEMA_CANCELANDO_TRANSACCION_SQL;
                throw BodegappTransversalExcepcion.crear(mensajeUsuario, exception.getMessage(), exception);
            } catch (Exception exception) {
                var mensajeUsuario = CatalogoMensajes.UtilSql.USUARIO_ERROR_PROBLEMA_NO_CONTROLADO_CANCELANDO_TRANSACCION_SQL;
                throw BodegappTransversalExcepcion.crear(mensajeUsuario, exception.getMessage(), exception);
            }
        }
    }

    //si la conexion no esta abierta, no se puede cerrar
    public static void cerrarConexion(Connection conexion) {
        if (!conexionEstaAbierta(conexion)) {
            var mensajeUsuario = CatalogoMensajes.UtilSql.USUARIO_ERROR_NO_ES_POSIBLE_CERRAR_CONEXION_SQL;
            throw BodegappTransversalExcepcion.crear(mensajeUsuario);
        }

        try {
            conexion.close();
        } catch (SQLException exception) {
            var mensajeUsuario = CatalogoMensajes.UtilSql.USUARIO_ERROR_PROBLEMA_CERRANDO_CONEXION_SQL;
            throw BodegappTransversalExcepcion.crear(mensajeUsuario, exception.getMessage(), exception);
        } catch (Exception exception) {
            var mensajeUsuario = CatalogoMensajes.UtilSql.USUARIO_ERROR_PROBLEMA_NO_CONTROLADO_CERRANDO_CONEXION_SQL;
            throw BodegappTransversalExcepcion.crear(mensajeUsuario, exception.getMessage(), exception);
        }
    }

    //si el autocommit esta apagado, la transaccion esta iniciada
    public static boolean transaccionEstaIniciada(Connection conexion) {
        try {
            return conexionEstaAbierta(conexion) && !conexion.getAutoCommit();
        } catch (SQLException exception) {
            var mensajeUsuario = CatalogoMensajes.UtilSql.USUARIO_ERROR_PROBLEMA_VALIDANDO_SI_TRANSACCION_SQL_ESTA_INICIADA;
            throw BodegappTransversalExcepcion.crear(mensajeUsuario, exception.getMessage(), exception);
        } catch (Exception exception) {
            var mensajeUsuario = CatalogoMensajes.UtilSql.USUARIO_ERROR_PROBLEMA_NO_CONTROLADO_VALIDANDO_SI_TRANSACCION_SQL_ESTA_INICIADA;
            throw BodegappTransversalExcepcion.crear(mensajeUsuario, exception.getMessage(), exception);
        }
    }

    public static boolean conexionEstaVacia(Connection conexion) {
        return UtilObjeto.esNulo(conexion);
    }

}
