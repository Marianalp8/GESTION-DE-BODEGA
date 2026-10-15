package co.edu.uco.bodegapp.dto;

import co.edu.uco.bodegapp.transversal.utilitario.UtilFecha;
import co.edu.uco.bodegapp.transversal.utilitario.UtilId;
import co.edu.uco.bodegapp.transversal.utilitario.UtilObjeto;

import java.time.LocalDateTime;
import java.util.UUID;

//transporta un registro del historial de un usuario, no valida nada
public class HistorialUsuarioDto {

    private UUID id;
    private UsuarioDto usuario;
    private UsuarioDto administrador;
    private RolDto rol;
    private EstadoUsuarioDto estadoUsuario;
    private LocalDateTime fecha;

    public HistorialUsuarioDto() {
        setId(UtilId.VALOR_DEFECTO);
        setUsuario(new UsuarioDto());
        setAdministrador(new UsuarioDto());
        setRol(new RolDto());
        setEstadoUsuario(new EstadoUsuarioDto());
        setFecha(UtilFecha.FECHA_HORA_POR_DEFECTO);
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = UtilId.valorDefecto(id);
    }

    public UsuarioDto getUsuario() {
        return usuario;
    }

    public void setUsuario(UsuarioDto usuario) {
        this.usuario = UtilObjeto.obtenerValorDefectoSiValorOriginalEsNulo(usuario, new UsuarioDto());
    }

    public UsuarioDto getAdministrador() {
        return administrador;
    }

    public void setAdministrador(UsuarioDto administrador) {
        this.administrador = UtilObjeto.obtenerValorDefectoSiValorOriginalEsNulo(administrador, new UsuarioDto());
    }

    public RolDto getRol() {
        return rol;
    }

    public void setRol(RolDto rol) {
        this.rol = UtilObjeto.obtenerValorDefectoSiValorOriginalEsNulo(rol, new RolDto());
    }

    public EstadoUsuarioDto getEstadoUsuario() {
        return estadoUsuario;
    }

    public void setEstadoUsuario(EstadoUsuarioDto estadoUsuario) {
        this.estadoUsuario = UtilObjeto.obtenerValorDefectoSiValorOriginalEsNulo(estadoUsuario, new EstadoUsuarioDto());
    }

    public LocalDateTime getFecha() {
        return fecha;
    }

    public void setFecha(LocalDateTime fecha) {
        this.fecha = UtilFecha.valorDefecto(fecha);
    }
}
