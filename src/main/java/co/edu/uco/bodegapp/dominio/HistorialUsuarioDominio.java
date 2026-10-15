package co.edu.uco.bodegapp.dominio;

import co.edu.uco.bodegapp.transversal.utilitario.UtilFecha;
import co.edu.uco.bodegapp.transversal.utilitario.UtilId;
import co.edu.uco.bodegapp.transversal.utilitario.UtilObjeto;

import java.time.LocalDateTime;
import java.util.UUID;

//un cambio que hizo un administrador sobre un usuario (aprobar, cambiar rol, desactivar o reactivar)
//usuario = a quien se lo hicieron, administrador = quien lo hizo, rol y estado = con lo que quedo
public class HistorialUsuarioDominio {

    private UUID id;
    private UsuarioDominio usuario;
    private UsuarioDominio administrador;
    private RolDominio rol;
    private EstadoUsuarioDominio estadoUsuario;
    private LocalDateTime fecha;

    private HistorialUsuarioDominio(Builder builder) {
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

    public UsuarioDominio getUsuario() {
        return usuario;
    }

    public UsuarioDominio getAdministrador() {
        return administrador;
    }

    public RolDominio getRol() {
        return rol;
    }

    public EstadoUsuarioDominio getEstadoUsuario() {
        return estadoUsuario;
    }

    public LocalDateTime getFecha() {
        return fecha;
    }

    public static class Builder {

        private UUID id;
        private UsuarioDominio usuario;
        private UsuarioDominio administrador;
        private RolDominio rol;
        private EstadoUsuarioDominio estadoUsuario;
        private LocalDateTime fecha;

        public Builder() {
            id = UtilId.valorDefecto(id);
            usuario = new UsuarioDominio.Builder().build();
            administrador = new UsuarioDominio.Builder().build();
            rol = new RolDominio.Builder().build();
            estadoUsuario = new EstadoUsuarioDominio.Builder().build();
            fecha = UtilFecha.FECHA_HORA_POR_DEFECTO;
        }

        public Builder id(UUID id) {
            this.id = UtilId.valorDefecto(id);
            return this;
        }

        public Builder usuario(UsuarioDominio usuario) {
            this.usuario = UtilObjeto.obtenerValorDefectoSiValorOriginalEsNulo(
                            usuario,
                            new UsuarioDominio.Builder().build());
            return this;
        }

        public Builder administrador(UsuarioDominio administrador) {
            this.administrador = UtilObjeto.obtenerValorDefectoSiValorOriginalEsNulo(
                            administrador,
                            new UsuarioDominio.Builder().build());
            return this;
        }

        public Builder rol(RolDominio rol) {
            this.rol = UtilObjeto.obtenerValorDefectoSiValorOriginalEsNulo(
                            rol,
                            new RolDominio.Builder().build());
            return this;
        }

        public Builder estadoUsuario(EstadoUsuarioDominio estadoUsuario) {
            this.estadoUsuario = UtilObjeto.obtenerValorDefectoSiValorOriginalEsNulo(
                            estadoUsuario,
                            new EstadoUsuarioDominio.Builder().build());
            return this;
        }

        public Builder fecha(LocalDateTime fecha) {
            this.fecha = UtilFecha.valorDefecto(fecha);
            return this;
        }

        public HistorialUsuarioDominio build() {
            return new HistorialUsuarioDominio(this);
        }
    }
}
