package co.edu.uco.bodegapp.negocio.negocio.assembler;

//convierte entre el Dominio (lo que usa Negocio) y la Entidad (lo que usa el DAO)
public interface EntidadAssembler<D, E> {

    E convertirAEntidad(D dominio);

    D convertirADominio(E entidad);
}
