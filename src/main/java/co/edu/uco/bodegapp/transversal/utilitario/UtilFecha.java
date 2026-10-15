package co.edu.uco.bodegapp.transversal.utilitario;

import java.time.LocalDateTime;

public class UtilFecha {

    //fecha y hora por defecto cuando no me dan una (01/01/1000 a las 00:00)
    public static final LocalDateTime FECHA_HORA_POR_DEFECTO = LocalDateTime.of(1000, 1, 1, 0, 0);

    private UtilFecha(){
    }

    // aseguro que la fecha nunca sea nula
    public static LocalDateTime valorDefecto(LocalDateTime fecha){
        return UtilObjeto.obtenerValorDefectoSiValorOriginalEsNulo(fecha, FECHA_HORA_POR_DEFECTO);
    }

    //la fecha y la hora de este momento (para la fecha de registro y la del historial)
    public static LocalDateTime obtenerFechaHoraActual(){
        return LocalDateTime.now();
    }

}
