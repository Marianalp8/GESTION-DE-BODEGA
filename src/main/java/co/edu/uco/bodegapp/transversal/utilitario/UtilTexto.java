package co.edu.uco.bodegapp.transversal.utilitario;

public class UtilTexto {

    private static UtilTexto instancia;

    public static final String vacia = "";

    private UtilTexto() {
    }

    public static UtilTexto getUtilTexto() {
            synchronized (UtilTexto.class) {
                //pregunta si instancia es nula ent se crea si no , la retorna
                if (UtilObjeto.esNulo(instancia)) {
                    instancia = new UtilTexto();
                }
            }
            return instancia;
    }

    public boolean esNula(String texto) {
        return UtilObjeto.esNulo(texto);
    }

    public boolean esVacia(String texto) {
        return vacia.equals(quitarEspaciosEnBlanco(texto));
    }

    public static String obtenerValorDefecto(String valor, String valorDefecto) {
        return UtilObjeto.obtenerValorDefectoSiValorOriginalEsNulo(valor, valorDefecto);
    }
    public static String obtenerValorDefecto(String valor){
        return obtenerValorDefecto(valor,vacia);
    }

    public String quitarEspaciosEnBlanco(String valor){
        return obtenerValorDefecto(valor).trim();
    }

    //para nombres de catalogo como 'Sin asignar' o 'Repartidor'
    public String primeraLetraMayuscula(String valor){
        var valorSanitizado = quitarEspaciosEnBlanco(valor).toLowerCase();
        return esVacia(valorSanitizado)
                ? vacia
                : valorSanitizado.substring(0, 1).toUpperCase() + valorSanitizado.substring(1);
    }

    //para el correo y el nombre de usuario: sin espacios a los lados y todo en minuscula
    public String minuscula(String valor){
        return quitarEspaciosEnBlanco(valor).toLowerCase();
    }

    public int obtenerLongitudCadena(String valor){
        return obtenerValorDefecto(valor).length();
    }

    public int obtenerLongitudCadena(String valor, boolean quitarEspaciosEnBlanco){

        return quitarEspaciosEnBlanco ?
                obtenerLongitudCadena(quitarEspaciosEnBlanco(valor)): obtenerLongitudCadena(valor);
    }

    public boolean longitudCadenaEsValida(String valor, int longitudInicial , int longitudFinal, boolean quitarEspaciosEnBlanco){

        var valorSanitizado = quitarEspaciosEnBlanco ? quitarEspaciosEnBlanco(valor): valor;

        return obtenerLongitudCadena(valorSanitizado)>= longitudInicial && obtenerLongitudCadena(valorSanitizado)<=longitudFinal;
    }

    //revisa si el texto tiene la forma que pide el patron (ej. solo letras, o forma de correo)
    public boolean formatoEsValido(String valor, String patron){
        return obtenerValorDefecto(valor).matches(obtenerValorDefecto(patron));
    }

    public String concatenar(Object...valores){
        var resultado= new StringBuilder();
        for(var valor:valores){
            resultado.append(UtilObjeto.obtenerValorDefectoSiValorOriginalEsNulo(valor, vacia));
        }
        return  resultado.toString();
    }

}
