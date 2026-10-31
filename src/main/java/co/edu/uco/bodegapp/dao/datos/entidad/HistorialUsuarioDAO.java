package co.edu.uco.bodegapp.dao.datos.entidad;

import co.edu.uco.bodegapp.dao.datos.ConsultarDAO;
import co.edu.uco.bodegapp.dao.datos.CrearDAO;
import co.edu.uco.bodegapp.entidad.HistorialUsuarioEntidad;

import java.util.UUID;

//el historial solo se crea y se consulta; nunca se edita ni se borra
public interface HistorialUsuarioDAO extends CrearDAO<HistorialUsuarioEntidad>,
        ConsultarDAO<HistorialUsuarioEntidad, UUID> {
}
