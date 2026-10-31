package co.edu.uco.bodegapp.negocio.negocio.reglas.impl.usuario;

import co.edu.uco.bodegapp.negocio.negocio.reglas.Rule;
import co.edu.uco.bodegapp.transversal.catalogo.CatalogoMensajes;
import co.edu.uco.bodegapp.transversal.excepciones.BodegappNegocioExcepcion;
import co.edu.uco.bodegapp.transversal.utilitario.UtilTexto;

//para iniciar sesion hay que escribir las dos cosas: el usuario (o correo) y la contrasena
public class ValidarDatosInicioSesionRule implements Rule<String> {

    private static final Rule<String> instancia = new ValidarDatosInicioSesionRule();

    private ValidarDatosInicioSesionRule() {
    }

    public static final Rule<String> obtenerInstancia() {
        return instancia;
    }

    @Override
    public void ejecutar(String... datos) {
        var usuarioOCorreo = datos[0];
        var contrasena = datos[1];

        if (UtilTexto.getUtilTexto().esVacia(usuarioOCorreo) || UtilTexto.getUtilTexto().esVacia(contrasena)) {
            var mensajeUsuario = CatalogoMensajes.UsuarioNegocioImpl.CREDENCIALES_OBLIGATORIAS;
            throw BodegappNegocioExcepcion.crear(mensajeUsuario);
        }
    }
}
