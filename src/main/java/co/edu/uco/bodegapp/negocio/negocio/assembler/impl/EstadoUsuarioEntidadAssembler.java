package co.edu.uco.bodegapp.negocio.negocio.assembler.impl;

import co.edu.uco.bodegapp.dominio.EstadoUsuarioDominio;
import co.edu.uco.bodegapp.entidad.EstadoUsuarioEntidad;
import co.edu.uco.bodegapp.negocio.negocio.assembler.EntidadAssembler;
import co.edu.uco.bodegapp.transversal.utilitario.UtilObjeto;

public final class EstadoUsuarioEntidadAssembler implements EntidadAssembler<EstadoUsuarioDominio, EstadoUsuarioEntidad> {

    private static final EntidadAssembler<EstadoUsuarioDominio, EstadoUsuarioEntidad> INSTANCIA = new EstadoUsuarioEntidadAssembler();

    private EstadoUsuarioEntidadAssembler() {
    }

    public static EntidadAssembler<EstadoUsuarioDominio, EstadoUsuarioEntidad> getInstance() {
        return INSTANCIA;
    }

    @Override
    public EstadoUsuarioEntidad convertirAEntidad(EstadoUsuarioDominio dominio) {
        var dominioSeguro = UtilObjeto.obtenerValorDefectoSiValorOriginalEsNulo(dominio, new EstadoUsuarioDominio.Builder().build());
        return new EstadoUsuarioEntidad.Builder()
                .id(dominioSeguro.getId())
                .nombre(dominioSeguro.getNombre())
                .build();
    }

    @Override
    public EstadoUsuarioDominio convertirADominio(EstadoUsuarioEntidad entidad) {
        var entidadSegura = UtilObjeto.obtenerValorDefectoSiValorOriginalEsNulo(entidad, new EstadoUsuarioEntidad.Builder().build());
        return new EstadoUsuarioDominio.Builder()
                .id(entidadSegura.getId())
                .nombre(entidadSegura.getNombre())
                .build();
    }
}
