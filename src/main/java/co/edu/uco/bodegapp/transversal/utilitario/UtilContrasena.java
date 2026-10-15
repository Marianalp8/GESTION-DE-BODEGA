package co.edu.uco.bodegapp.transversal.utilitario;

import co.edu.uco.bodegapp.transversal.catalogo.CatalogoMensajes;
import co.edu.uco.bodegapp.transversal.excepciones.BodegappTransversalExcepcion;
import org.mindrot.jbcrypt.BCrypt;

//la contrasena nunca se guarda tal cual: se guarda encriptada con BCrypt (un texto como $2a$10$...)
public class UtilContrasena {

    private UtilContrasena() {
    }

    //convierte la contrasena que escribio la persona en el texto encriptado que se guarda
    public static String encriptar(String contrasena) {
        try {
            return BCrypt.hashpw(UtilTexto.obtenerValorDefecto(contrasena), BCrypt.gensalt());
        } catch (Exception excepcion) {
            var mensajeUsuario = CatalogoMensajes.UtilContrasena.USUARIO_ERROR_PROBLEMA_ENCRIPTANDO_CONTRASENA;
            throw BodegappTransversalExcepcion.crear(mensajeUsuario, excepcion.getMessage(), excepcion);
        }
    }

    //dice si la contrasena que escribio la persona es la misma que esta guardada encriptada
    //no se puede "desencriptar": se encripta la escrita y se comparan los dos resultados
    public static boolean coincide(String contrasena, String contrasenaEncriptada) {
        try {
            return BCrypt.checkpw(UtilTexto.obtenerValorDefecto(contrasena),
                    UtilTexto.obtenerValorDefecto(contrasenaEncriptada));
        } catch (Exception excepcion) {
            //si lo guardado no es un texto encriptado valido (ej. esta vacio), no coincide
            return false;
        }
    }
}
