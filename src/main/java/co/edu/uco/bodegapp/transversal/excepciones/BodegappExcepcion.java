package co.edu.uco.bodegapp.transversal.excepciones;

import co.edu.uco.bodegapp.transversal.excepciones.enums.Capa;
import co.edu.uco.bodegapp.transversal.utilitario.UtilObjeto;
import co.edu.uco.bodegapp.transversal.utilitario.UtilTexto;

//clase padre de todas las excepciones de Bodegapp
//las excepciones de cada capa heredan de ella y reutilizan sus atributos
public class BodegappExcepcion extends RuntimeException {

    private static final long serialVersionUID = -4512873690215478963L;
    private Capa capa;
    private String mensajeUsuario;
    private String mensajeTecnico;
    private Exception excepcionRaiz;

    //protegido para que solo lo usen las clases hijas
    //usa los sets para controlar cada parametro antes de construir el objeto
    protected BodegappExcepcion(Capa capa, String mensajeUsuario, String mensajeTecnico, Exception excepcionRaiz) {
        super();
        setCapa(capa);
        setMensajeUsuario(mensajeUsuario);
        setMensajeTecnico(mensajeTecnico);
        setExcepcionRaiz(excepcionRaiz);
    }

    public static long getSerialVersionuid() {
        return serialVersionUID;
    }

    public Capa getCapa() {
        return capa;
    }

    //si no me dicen en que capa se presento, digo que fue en la general
    private void setCapa(Capa capa) {
        this.capa = UtilObjeto.obtenerValorDefectoSiValorOriginalEsNulo(capa, Capa.GENERAL);
    }

    public String getMensajeUsuario() {
        return mensajeUsuario;
    }

    //si no tiene mensaje, se envia el vacio
    private void setMensajeUsuario(String mensajeUsuario) {
        this.mensajeUsuario = UtilTexto.getUtilTexto().obtenerValorDefecto(mensajeUsuario);
    }

    public String getMensajeTecnico() {
        return mensajeTecnico;
    }

    //si no pusieron mensaje tecnico, se usa el mensaje de usuario
    private void setMensajeTecnico(String mensajeTecnico) {
        this.mensajeTecnico = UtilTexto.obtenerValorDefecto(mensajeTecnico, getMensajeUsuario());
    }

    public Exception getExcepcionRaiz() {
        return excepcionRaiz;
    }

    //si es nula, se crea una nueva Exception con el mensaje tecnico
    private void setExcepcionRaiz(Exception excepcionRaiz) {
        this.excepcionRaiz = UtilObjeto.obtenerValorDefectoSiValorOriginalEsNulo(excepcionRaiz, new Exception(getMensajeTecnico()));
    }

}
