package co.edu.uco.bodegapp.negocio.negocio.assembler.impl;

import co.edu.uco.bodegapp.dominio.UsuarioDominio;
import co.edu.uco.bodegapp.entidad.UsuarioEntidad;
import co.edu.uco.bodegapp.negocio.negocio.assembler.EntidadAssembler;
import co.edu.uco.bodegapp.transversal.utilitario.UtilObjeto;

public final class UsuarioEntidadAssembler implements EntidadAssembler<UsuarioDominio, UsuarioEntidad> {

    private static final EntidadAssembler<UsuarioDominio, UsuarioEntidad> INSTANCIA = new UsuarioEntidadAssembler();

    private UsuarioEntidadAssembler() {
    }

    public static EntidadAssembler<UsuarioDominio, UsuarioEntidad> getInstance() {
        return INSTANCIA;
    }

    //el rol y el estado se convierten con su propio assembler
    @Override
    public UsuarioEntidad convertirAEntidad(UsuarioDominio dominio) {
        var dominioSeguro = UtilObjeto.obtenerValorDefectoSiValorOriginalEsNulo(dominio, new UsuarioDominio.Builder().build());
        return new UsuarioEntidad.Builder()
                .id(dominioSeguro.getId())
                .nombre(dominioSeguro.getNombre())
                .correo(dominioSeguro.getCorreo())
                .nombreUsuario(dominioSeguro.getNombreUsuario())
                .contrasena(dominioSeguro.getContrasena())
                .rol(RolEntidadAssembler.getInstance().convertirAEntidad(dominioSeguro.getRol()))
                .estadoUsuario(EstadoUsuarioEntidadAssembler.getInstance().convertirAEntidad(dominioSeguro.getEstadoUsuario()))
                .fechaRegistro(dominioSeguro.getFechaRegistro())
                .build();
    }

    @Override
    public UsuarioDominio convertirADominio(UsuarioEntidad entidad) {
        var entidadSegura = UtilObjeto.obtenerValorDefectoSiValorOriginalEsNulo(entidad, new UsuarioEntidad.Builder().build());
        return new UsuarioDominio.Builder()
                .id(entidadSegura.getId())
                .nombre(entidadSegura.getNombre())
                .correo(entidadSegura.getCorreo())
                .nombreUsuario(entidadSegura.getNombreUsuario())
                .contrasena(entidadSegura.getContrasena())
                .rol(RolEntidadAssembler.getInstance().convertirADominio(entidadSegura.getRol()))
                .estadoUsuario(EstadoUsuarioEntidadAssembler.getInstance().convertirADominio(entidadSegura.getEstadoUsuario()))
                .fechaRegistro(entidadSegura.getFechaRegistro())
                .build();
    }
}
