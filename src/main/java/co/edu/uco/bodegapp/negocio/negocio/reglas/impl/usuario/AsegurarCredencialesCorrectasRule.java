package co.edu.uco.bodegapp.negocio.negocio.reglas.impl.usuario;

import co.edu.uco.bodegapp.entidad.UsuarioEntidad;
import co.edu.uco.bodegapp.negocio.negocio.reglas.Rule;
import co.edu.uco.bodegapp.transversal.catalogo.CatalogoMensajes;
import co.edu.uco.bodegapp.transversal.excepciones.BodegappNegocioExcepcion;
import co.edu.uco.bodegapp.transversal.utilitario.UtilContrasena;
import co.edu.uco.bodegapp.transversal.utilitario.UtilId;

//si el usuario no existe o la contrasena no coincide, el mensaje es UNO solo:
//asi quien intenta entrar a la fuerza no se entera de cual de las dos cosas fallo
public class AsegurarCredencialesCorrectasRule implements Rule<Object> {

    private static final Rule<Object> instancia = new AsegurarCredencialesCorrectasRule();

    private AsegurarCredencialesCorrectasRule() {
    }

    public static final Rule<Object> obtenerInstancia() {
        return instancia;
    }

    @Override
    public void ejecutar(Object... datos) {
        var usuarioEncontrado = (UsuarioEntidad) datos[0];
        var contrasena = (String) datos[1];

        var usuarioNoExiste = UtilId.VALOR_DEFECTO.equals(usuarioEncontrado.getId());

        if (usuarioNoExiste || !UtilContrasena.coincide(contrasena, usuarioEncontrado.getContrasena())) {
            var mensajeUsuario = CatalogoMensajes.UsuarioNegocioImpl.CREDENCIALES_INCORRECTAS;
            throw BodegappNegocioExcepcion.crear(mensajeUsuario);
        }
    }
}
