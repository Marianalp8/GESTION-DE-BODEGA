package co.edu.uco.bodegapp.negocio.negocio.reglas.impl.usuario;

import co.edu.uco.bodegapp.dominio.UsuarioDominio;
import co.edu.uco.bodegapp.negocio.negocio.reglas.Rule;
import co.edu.uco.bodegapp.transversal.catalogo.CatalogoMensajes;
import co.edu.uco.bodegapp.transversal.excepciones.BodegappNegocioExcepcion;
import co.edu.uco.bodegapp.transversal.utilitario.UtilObjeto;
import co.edu.uco.bodegapp.transversal.utilitario.UtilTexto;

//revisa los datos que escribio la persona al registrarse, mirando solo el objeto (sin ir a la base de datos)
public class ValidarDatosRegistrarNuevoUsuarioRule implements Rule<UsuarioDominio> {

    private static final int LONGITUD_MINIMA_CONTRASENA = 8;
    //\\p{L} significa "cualquier letra", incluidas las que llevan tilde y la enie
    private static final String PATRON_SOLO_LETRAS_Y_ESPACIOS = "^[\\p{L} ]+$";
    private static final String PATRON_CORREO = "^[^@\\s]+@[^@\\s]+\\.[^@\\s]+$";
    private static final String PATRON_NOMBRE_USUARIO = "^[a-z0-9._]+$";
    private static final String PATRON_TIENE_LETRA = ".*\\p{L}.*";
    private static final String PATRON_TIENE_NUMERO = ".*[0-9].*";

    private static final Rule<UsuarioDominio> instancia = new ValidarDatosRegistrarNuevoUsuarioRule();

    private ValidarDatosRegistrarNuevoUsuarioRule() {
    }

    public static final Rule<UsuarioDominio> obtenerInstancia() {
        return instancia;
    }

    @Override
    public void ejecutar(UsuarioDominio... datos) {
        var dominio = datos[0];

        validarObligatoriedadDatos(dominio);
        validarNombre(dominio.getNombre());
        validarCorreo(dominio.getCorreo());
        validarNombreUsuario(dominio.getNombreUsuario());
        validarContrasena(dominio.getContrasena());
    }

    private void validarObligatoriedadDatos(UsuarioDominio dominio) {
        if (UtilObjeto.esNulo(dominio)) {
            var mensajeUsuario = CatalogoMensajes.UsuarioNegocioImpl.DATOS_USUARIO_OBLIGATORIOS;
            throw BodegappNegocioExcepcion.crear(mensajeUsuario);
        }
    }

    private void validarNombre(String nombre) {
        if (UtilTexto.getUtilTexto().esVacia(nombre)) {
            var mensajeUsuario = CatalogoMensajes.UsuarioNegocioImpl.NOMBRE_OBLIGATORIO;
            throw BodegappNegocioExcepcion.crear(mensajeUsuario);
        }
        if (!UtilTexto.getUtilTexto().longitudCadenaEsValida(nombre, 2, 100, true)) {
            var mensajeUsuario = CatalogoMensajes.UsuarioNegocioImpl.NOMBRE_LONGITUD_NO_VALIDA;
            throw BodegappNegocioExcepcion.crear(mensajeUsuario);
        }
        if (!UtilTexto.getUtilTexto().formatoEsValido(nombre, PATRON_SOLO_LETRAS_Y_ESPACIOS)) {
            var mensajeUsuario = CatalogoMensajes.UsuarioNegocioImpl.NOMBRE_FORMATO_NO_VALIDO;
            throw BodegappNegocioExcepcion.crear(mensajeUsuario);
        }
    }

    private void validarCorreo(String correo) {
        if (UtilTexto.getUtilTexto().esVacia(correo)) {
            var mensajeUsuario = CatalogoMensajes.UsuarioNegocioImpl.CORREO_OBLIGATORIO;
            throw BodegappNegocioExcepcion.crear(mensajeUsuario);
        }
        if (!UtilTexto.getUtilTexto().longitudCadenaEsValida(correo, 1, 100, true)) {
            var mensajeUsuario = CatalogoMensajes.UsuarioNegocioImpl.CORREO_LONGITUD_NO_VALIDA;
            throw BodegappNegocioExcepcion.crear(mensajeUsuario);
        }
        if (!UtilTexto.getUtilTexto().formatoEsValido(correo, PATRON_CORREO)) {
            var mensajeUsuario = CatalogoMensajes.UsuarioNegocioImpl.CORREO_FORMATO_NO_VALIDO;
            throw BodegappNegocioExcepcion.crear(mensajeUsuario);
        }
    }

    private void validarNombreUsuario(String nombreUsuario) {
        if (UtilTexto.getUtilTexto().esVacia(nombreUsuario)) {
            var mensajeUsuario = CatalogoMensajes.UsuarioNegocioImpl.NOMBRE_USUARIO_OBLIGATORIO;
            throw BodegappNegocioExcepcion.crear(mensajeUsuario);
        }
        if (!UtilTexto.getUtilTexto().longitudCadenaEsValida(nombreUsuario, 4, 30, true)) {
            var mensajeUsuario = CatalogoMensajes.UsuarioNegocioImpl.NOMBRE_USUARIO_LONGITUD_NO_VALIDA;
            throw BodegappNegocioExcepcion.crear(mensajeUsuario);
        }
        if (!UtilTexto.getUtilTexto().formatoEsValido(nombreUsuario, PATRON_NOMBRE_USUARIO)) {
            var mensajeUsuario = CatalogoMensajes.UsuarioNegocioImpl.NOMBRE_USUARIO_FORMATO_NO_VALIDO;
            throw BodegappNegocioExcepcion.crear(mensajeUsuario);
        }
    }

    //la contrasena no se recorta: los espacios tambien cuentan como caracteres
    private void validarContrasena(String contrasena) {
        if (UtilTexto.getUtilTexto().esVacia(contrasena)) {
            var mensajeUsuario = CatalogoMensajes.UsuarioNegocioImpl.CONTRASENA_OBLIGATORIA;
            throw BodegappNegocioExcepcion.crear(mensajeUsuario);
        }
        if (UtilTexto.getUtilTexto().obtenerLongitudCadena(contrasena) < LONGITUD_MINIMA_CONTRASENA) {
            var mensajeUsuario = CatalogoMensajes.UsuarioNegocioImpl.CONTRASENA_LONGITUD_NO_VALIDA;
            throw BodegappNegocioExcepcion.crear(mensajeUsuario);
        }
        if (!UtilTexto.getUtilTexto().formatoEsValido(contrasena, PATRON_TIENE_LETRA)
                || !UtilTexto.getUtilTexto().formatoEsValido(contrasena, PATRON_TIENE_NUMERO)) {
            var mensajeUsuario = CatalogoMensajes.UsuarioNegocioImpl.CONTRASENA_FORMATO_NO_VALIDO;
            throw BodegappNegocioExcepcion.crear(mensajeUsuario);
        }
    }
}
