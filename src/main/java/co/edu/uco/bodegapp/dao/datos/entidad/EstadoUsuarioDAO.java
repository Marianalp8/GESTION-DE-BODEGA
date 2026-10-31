package co.edu.uco.bodegapp.dao.datos.entidad;

import co.edu.uco.bodegapp.dao.datos.ConsultarDAO;
import co.edu.uco.bodegapp.entidad.EstadoUsuarioEntidad;

import java.util.UUID;

//los estados son fijos (se llenan con el script): desde la app solo se consultan
public interface EstadoUsuarioDAO extends ConsultarDAO<EstadoUsuarioEntidad, UUID> {
}
