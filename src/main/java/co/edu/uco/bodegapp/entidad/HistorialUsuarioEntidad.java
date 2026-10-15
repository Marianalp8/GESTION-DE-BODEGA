package co.edu.uco.bodegapp.entidad;

import co.edu.uco.bodegapp.transversal.utilitario.UtilFecha;
import co.edu.uco.bodegapp.transversal.utilitario.UtilId;
import co.edu.uco.bodegapp.transversal.utilitario.UtilObjeto;

import java.time.LocalDateTime;
import java.util.UUID;

//un cambio que hizo un administrador sobre un usuario (aprobar, cambiar rol, desactivar o reactivar)
//usuario = a quien se lo hicieron, administrador = quien lo hizo, rol y estado = con lo que quedo
public class HistorialUsuarioEntidad {

    private UUID id;
    private UsuarioEntidad usuario;
    private UsuarioEntidad administrador;
    private RolEntidad rol;
    private EstadoUsuarioEntidad estadoUsuario;
    private LocalDateTime fecha;

    private HistorialUsuarioEntidad(Builder builder) {
        this.id = builder.id;
        this.usuario = builder.usuario;
        this.administrador = builder.administrador;
        this.rol = builder.rol;
        this.estadoUsuario = builder.estadoUsuario;
        this.fecha = builder.fecha;
    }

    public UUID getId() {
        return id;
    }

    public UsuarioEntidad getUsuario() {
        return usuario;
    }

    public UsuarioEntidad getAdministrador() {
        return administrador;
    }

    public RolEntidad getRol() {
        return rol;
    }

    public EstadoUsuarioEntidad getEstadoUsuario() {
        return estadoUsuario;
    }

    public LocalDateTime getFecha() {
        return fecha;
    }

    public static class Builder {

        private UUID id;
        private UsuarioEntidad usuario;
        private UsuarioEntidad administrador;
        private RolEntidad rol;
        private EstadoUsuarioEntidad estadoUsuario;
        private LocalDateTime fecha;

        public Builder() {
            id = UtilId.valorDefecto(id);
            usuario = new UsuarioEntidad.Builder().build();
            administrador = new UsuarioEntidad.Builder().build();
            rol = new RolEntidad.Builder().build();
            estadoUsuario = new EstadoUsuarioEntidad.Builder().build();
            fecha = UtilFecha.FECHA_HORA_POR_DEFECTO;
        }

        public Builder id(UUID id) {
            this.id = UtilId.valorDefecto(id);
            return this;
        }

        public Builder usuario(UsuarioEntidad usuario) {
            this.usuario = UtilObjeto.obtenerValorDefectoSiValorOriginalEsNulo(
                            usuario,
                            new UsuarioEntidad.Builder().build());
            return this;
        }

        public Builder administrador(UsuarioEntidad administrador) {
            this.administrador = UtilObjeto.obtenerValorDefectoSiValorOriginalEsNulo(
                            administrador,
                            new UsuarioEntidad.Builder().build());
            return this;
        }

        public Builder rol(RolEntidad rol) {
            this.rol = UtilObjeto.obtenerValorDefectoSiValorOriginalEsNulo(
                            rol,
                            new RolEntidad.Builder().build());
            return this;
        }

        public Builder estadoUsuario(EstadoUsuarioEntidad estadoUsuario) {
            this.estadoUsuario = UtilObjeto.obtenerValorDefectoSiValorOriginalEsNulo(
                            estadoUsuario,
                            new EstadoUsuarioEntidad.Builder().build());
            return this;
        }

        public Builder fecha(LocalDateTime fecha) {
            this.fecha = UtilFecha.valorDefecto(fecha);
            return this;
        }

        public HistorialUsuarioEntidad build() {
            return new HistorialUsuarioEntidad(this);
        }
    }
}
