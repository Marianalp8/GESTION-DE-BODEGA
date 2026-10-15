package co.edu.uco.bodegapp.transversal.utilitario;

public class UtilObjeto {

    private UtilObjeto(){

    }

    public static <O> boolean esNulo(O objeto){
        return objeto == null; // retorna si el objeto es nulo o no con true or false
    }

    public static <O> O obtenerValorDefectoSiValorOriginalEsNulo(O valor , O valorDefecto){
        return esNulo(valor) ? valorDefecto : valor;
    }
}
