package co.edu.uco.bodegapp.transversal.excepciones;

import co.edu.uco.bodegapp.transversal.excepciones.enums.Capa;

public class BodegappTransversalExcepcion extends BodegappExcepcion {

    private static final long serialVersionUID = -7823416590374126845L;

    private BodegappTransversalExcepcion(String mensajeUsuario, String mensajeTecnico, Exception excepcionRaiz) {
        super(Capa.TRANSVERSAL, mensajeUsuario, mensajeTecnico, excepcionRaiz);
    }

    public static BodegappExcepcion crear(String mensajeUsuario) {
        return new BodegappTransversalExcepcion(mensajeUsuario, mensajeUsuario,
                new Exception(mensajeUsuario));
    }

    public static BodegappExcepcion crear(String mensajeUsuario, String mensajeTecnico) {
        return new BodegappTransversalExcepcion(mensajeUsuario, mensajeTecnico,
                new Exception(mensajeTecnico));
    }

    public static BodegappExcepcion crear(String mensajeUsuario, String mensajeTecnico, Exception excepcionRaiz) {
        return new BodegappTransversalExcepcion(mensajeUsuario, mensajeTecnico,
                excepcionRaiz);
    }

}
