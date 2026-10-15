package co.edu.uco.bodegapp.transversal.excepciones;

import co.edu.uco.bodegapp.transversal.excepciones.enums.Capa;

public class BodegappDatosExcepcion extends BodegappExcepcion {

    private static final long serialVersionUID = -7823416590374126810L;

    private BodegappDatosExcepcion(String mensajeUsuario, String mensajeTecnico, Exception excepcionRaiz) {
        super(Capa.DATOS, mensajeUsuario, mensajeTecnico, excepcionRaiz);
    }

    public static BodegappExcepcion crear(String mensajeUsuario) {
        return new BodegappDatosExcepcion(mensajeUsuario, mensajeUsuario,
                new Exception(mensajeUsuario));
    }

    public static BodegappExcepcion crear(String mensajeUsuario, String mensajeTecnico) {
        return new BodegappDatosExcepcion(mensajeUsuario, mensajeTecnico,
                new Exception(mensajeTecnico));
    }

    public static BodegappExcepcion crear(String mensajeUsuario, String mensajeTecnico, Exception excepcionRaiz) {
        return new BodegappDatosExcepcion(mensajeUsuario, mensajeTecnico,
                excepcionRaiz);
    }

}
