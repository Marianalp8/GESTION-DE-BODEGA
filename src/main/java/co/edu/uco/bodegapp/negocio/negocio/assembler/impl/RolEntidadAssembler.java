package co.edu.uco.bodegapp.negocio.negocio.assembler.impl;

import co.edu.uco.bodegapp.dominio.RolDominio;
import co.edu.uco.bodegapp.entidad.RolEntidad;
import co.edu.uco.bodegapp.negocio.negocio.assembler.EntidadAssembler;
import co.edu.uco.bodegapp.transversal.utilitario.UtilObjeto;

public final class RolEntidadAssembler implements EntidadAssembler<RolDominio, RolEntidad> {

    private static final EntidadAssembler<RolDominio, RolEntidad> INSTANCIA = new RolEntidadAssembler();

    private RolEntidadAssembler() {
    }

    public static EntidadAssembler<RolDominio, RolEntidad> getInstance() {
        return INSTANCIA;
    }

    @Override
    public RolEntidad convertirAEntidad(RolDominio dominio) {
        var dominioSeguro = UtilObjeto.obtenerValorDefectoSiValorOriginalEsNulo(dominio, new RolDominio.Builder().build());
        return new RolEntidad.Builder()
                .id(dominioSeguro.getId())
                .nombre(dominioSeguro.getNombre())
                .build();
    }

    @Override
    public RolDominio convertirADominio(RolEntidad entidad) {
        var entidadSegura = UtilObjeto.obtenerValorDefectoSiValorOriginalEsNulo(entidad, new RolEntidad.Builder().build());
        return new RolDominio.Builder()
                .id(entidadSegura.getId())
                .nombre(entidadSegura.getNombre())
                .build();
    }
}
