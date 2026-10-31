package co.edu.uco.bodegapp.dao.datos.entidad;

import co.edu.uco.bodegapp.dao.datos.ActualizarDAO;
import co.edu.uco.bodegapp.dao.datos.ConsultarDAO;
import co.edu.uco.bodegapp.dao.datos.CrearDAO;
import co.edu.uco.bodegapp.entidad.UsuarioEntidad;

import java.util.UUID;

//un usuario se crea, se consulta y se actualiza; nunca se borra (se desactiva)
public interface UsuarioDAO extends CrearDAO<UsuarioEntidad>, ConsultarDAO<UsuarioEntidad, UUID>,
        ActualizarDAO<UsuarioEntidad, UUID> {
}
