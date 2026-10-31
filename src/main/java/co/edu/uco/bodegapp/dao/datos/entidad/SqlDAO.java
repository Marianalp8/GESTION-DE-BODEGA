package co.edu.uco.bodegapp.dao.datos.entidad;

import co.edu.uco.bodegapp.transversal.utilitario.UtilSql;

import java.sql.Connection;

//de esta clase heredan todos los DAO de base de datos SQL
//la factoria les pasa la conexion que ya abrio
public abstract class SqlDAO {

    private Connection conexion;

    protected SqlDAO(Connection conexion) {
        setConexion(conexion);
    }

    //antes de guardar la conexion me aseguro de que exista y este abierta
    private void setConexion(Connection conexion) {
        UtilSql.asegurarConexionAbierta(conexion);
        this.conexion = conexion;
    }

    protected Connection getConexion() {
        return conexion;
    }
}
