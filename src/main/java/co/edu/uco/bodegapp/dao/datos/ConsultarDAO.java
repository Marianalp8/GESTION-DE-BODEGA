package co.edu.uco.bodegapp.dao.datos;

import java.util.List;

public interface ConsultarDAO<E, Id> {

    E consultarPorId(Id id);

    List<E> consultarPorFiltro(E filtro);

    List<E> consultarTodos();
}
