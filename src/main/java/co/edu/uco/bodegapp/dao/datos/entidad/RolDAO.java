package co.edu.uco.bodegapp.dao.datos.entidad;

import co.edu.uco.bodegapp.dao.datos.ConsultarDAO;
import co.edu.uco.bodegapp.entidad.RolEntidad;

import java.util.UUID;

//los roles son fijos (se llenan con el script): desde la app solo se consultan
public interface RolDAO extends ConsultarDAO<RolEntidad, UUID> {
}
