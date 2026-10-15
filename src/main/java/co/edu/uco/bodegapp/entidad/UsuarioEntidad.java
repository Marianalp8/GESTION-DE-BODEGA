package co.edu.uco.bodegapp.entidad;

import co.edu.uco.bodegapp.transversal.utilitario.UtilFecha;
import co.edu.uco.bodegapp.transversal.utilitario.UtilId;
import co.edu.uco.bodegapp.transversal.utilitario.UtilObjeto;
import co.edu.uco.bodegapp.transversal.utilitario.UtilTexto;

import java.time.LocalDateTime;
import java.util.UUID;

//una persona registrada en el sistema, con el rol y el estado que tiene hoy
//si no le dan rol ni estado queda con los por defecto (id de ceros = 'Sin asignar' y 'Pendiente')
public class UsuarioEntidad {

    private UUID id;
    private String nombre;
    private String correo;
    private String nombreUsuario;
    private String contrasena;
    private RolEntidad rol;
    private EstadoUsuarioEntidad estadoUsuario;
    private LocalDateTime fechaRegistro;

    private UsuarioEntidad(Builder builder) {
        this.id = builder.id;
        this.nombre = builder.nombre;
        this.correo = builder.correo;
        this.nombreUsuario = builder.nombreUsuario;
        this.contrasena = builder.contrasena;
        this.rol = builder.rol;
        this.estadoUsuario = builder.estadoUsuario;
        this.fechaRegistro = builder.fechaRegistro;
    }

    public UUID getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getCorreo() {
        return correo;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public String getContrasena() {
        return contrasena;
    }

    public RolEntidad getRol() {
        return rol;
    }

    public EstadoUsuarioEntidad getEstadoUsuario() {
        return estadoUsuario;
    }

    public LocalDateTime getFechaRegistro() {
        return fechaRegistro;
    }

    public static class Builder {

        private UUID id;
        private String nombre;
        private String correo;
        private String nombreUsuario;
        private String contrasena;
        private RolEntidad rol;
        private EstadoUsuarioEntidad estadoUsuario;
        private LocalDateTime fechaRegistro;

        public Builder() {
            id = UtilId.valorDefecto(id);
            nombre = UtilTexto.vacia;
            correo = UtilTexto.vacia;
            nombreUsuario = UtilTexto.vacia;
            contrasena = UtilTexto.vacia;
            rol = new RolEntidad.Builder().build();
            estadoUsuario = new EstadoUsuarioEntidad.Builder().build();
            fechaRegistro = UtilFecha.FECHA_HORA_POR_DEFECTO;
        }

        public Builder id(UUID id) {
            this.id = UtilId.valorDefecto(id);
            return this;
        }

        public Builder nombre(String nombre) {
            this.nombre = UtilTexto.getUtilTexto().quitarEspaciosEnBlanco(nombre);
            return this;
        }

        public Builder correo(String correo) {
            this.correo = UtilTexto.getUtilTexto().minuscula(correo);
            return this;
        }

        public Builder nombreUsuario(String nombreUsuario) {
            this.nombreUsuario = UtilTexto.getUtilTexto().minuscula(nombreUsuario);
            return this;
        }

        public Builder contrasena(String contrasena) {
            //la contrasena no se recorta ni se pasa a minuscula: cada caracter cuenta
            this.contrasena = UtilTexto.obtenerValorDefecto(contrasena);
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

        public Builder fechaRegistro(LocalDateTime fechaRegistro) {
            this.fechaRegistro = UtilFecha.valorDefecto(fechaRegistro);
            return this;
        }

        public UsuarioEntidad build() {
            return new UsuarioEntidad(this);
        }
    }
}
