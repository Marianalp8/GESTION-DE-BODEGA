package co.edu.uco.bodegapp.dto;

import co.edu.uco.bodegapp.transversal.utilitario.UtilFecha;
import co.edu.uco.bodegapp.transversal.utilitario.UtilId;
import co.edu.uco.bodegapp.transversal.utilitario.UtilObjeto;
import co.edu.uco.bodegapp.transversal.utilitario.UtilTexto;

import java.time.LocalDateTime;
import java.util.UUID;

//transporta los datos de un usuario (por ejemplo, lo que escribe la persona al registrarse), no valida nada
public class UsuarioDto {

    private UUID id;
    private String nombre;
    private String correo;
    private String nombreUsuario;
    private String contrasena;
    private RolDto rol;
    private EstadoUsuarioDto estadoUsuario;
    private LocalDateTime fechaRegistro;

    public UsuarioDto() {
        setId(UtilId.VALOR_DEFECTO);
        setNombre(UtilTexto.vacia);
        setCorreo(UtilTexto.vacia);
        setNombreUsuario(UtilTexto.vacia);
        setContrasena(UtilTexto.vacia);
        setRol(new RolDto());
        setEstadoUsuario(new EstadoUsuarioDto());
        setFechaRegistro(UtilFecha.FECHA_HORA_POR_DEFECTO);
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = UtilId.valorDefecto(id);
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = UtilTexto.getUtilTexto().quitarEspaciosEnBlanco(nombre);
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = UtilTexto.getUtilTexto().minuscula(correo);
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = UtilTexto.getUtilTexto().minuscula(nombreUsuario);
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        //la contrasena no se recorta ni se pasa a minuscula: cada caracter cuenta
        this.contrasena = UtilTexto.obtenerValorDefecto(contrasena);
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

    public LocalDateTime getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(LocalDateTime fechaRegistro) {
        this.fechaRegistro = UtilFecha.valorDefecto(fechaRegistro);
    }
}
