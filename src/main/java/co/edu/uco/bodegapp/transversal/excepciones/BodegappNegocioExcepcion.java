package co.edu.uco.bodegapp.transversal.excepciones;

import co.edu.uco.bodegapp.transversal.excepciones.enums.Capa;

public class BodegappNegocioExcepcion extends BodegappExcepcion {

    private static final long serialVersionUID = -7823416590374126820L;

    private BodegappNegocioExcepcion(String mensajeUsuario, String mensajeTecnico, Exception excepcionRaiz) {
        super(Capa.NEGOCIO, mensajeUsuario, mensajeTecnico, excepcionRaiz);
    }

    public static BodegappExcepcion crear(String mensajeUsuario) {
        return new BodegappNegocioExcepcion(mensajeUsuario, mensajeUsuario,
                new Exception(mensajeUsuario));
    }

    public static BodegappExcepcion crear(String mensajeUsuario, String mensajeTecnico) {
        return new BodegappNegocioExcepcion(mensajeUsuario, mensajeTecnico,
                new Exception(mensajeTecnico));
    }

    public static BodegappExcepcion crear(String mensajeUsuario, String mensajeTecnico, Exception excepcionRaiz) {
        return new BodegappNegocioExcepcion(mensajeUsuario, mensajeTecnico,
                excepcionRaiz);
    }

}
