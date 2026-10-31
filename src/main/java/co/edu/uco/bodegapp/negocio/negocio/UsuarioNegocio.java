package co.edu.uco.bodegapp.negocio.negocio;

import co.edu.uco.bodegapp.dominio.UsuarioDominio;

import java.util.UUID;

//todo lo que el sistema sabe hacer con un usuario en el inicio de sesion
public interface UsuarioNegocio {

    void registrarInformacionNuevoUsuario(UsuarioDominio datos);

    UsuarioDominio iniciarSesion(String usuarioOCorreo, String contrasena);

    //acciones del administrador: idAdministrador es quien la hace, idUsuario es a quien se la hacen
    void aprobarUsuario(UUID idAdministrador, UUID idUsuario, UUID idRol);

    void cambiarRolUsuario(UUID idAdministrador, UUID idUsuario, UUID idRol);

    void desactivarUsuario(UUID idAdministrador, UUID idUsuario);

    void reactivarUsuario(UUID idAdministrador, UUID idUsuario);
}
