package co.edu.uco.bodegapp.dto;


//lo que manda el formulario de inicio de sesion: el usuario (o el correo) y la contrasena
//para habilitar permisos dependiendo de quien sea

import co.edu.uco.bodegapp.transversal.utilitario.UtilTexto;

public class InicioSesionDto {
    private String usuarioOCorreo;
    private String contrasena;

    public InicioSesionDto() {
        setUsuarioOCorreo(UtilTexto.vacia);
        setContrasena(UtilTexto.vacia);
    }

    public String getUsuarioOCorreo() {
        return usuarioOCorreo;
    }

    public void setUsuarioOCorreo(String usuarioOCorreo) {
        //en minuscula, porque asi se guardan el nombre de usuario y el correo
        this.usuarioOCorreo = UtilTexto.getUtilTexto().minuscula(usuarioOCorreo);
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        //igual que en UsuarioDto: la contrasena no se toca, cada caracter cuenta
        this.contrasena = UtilTexto.obtenerValorDefecto(contrasena);
    }
}
