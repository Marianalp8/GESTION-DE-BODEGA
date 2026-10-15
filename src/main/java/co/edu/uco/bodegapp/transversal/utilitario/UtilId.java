package co.edu.uco.bodegapp.transversal.utilitario;

import java.util.UUID;

public class UtilId {

    //id por defecto cuando no me dan uno; tambien es el id fijo de 'Sin asignar' y 'Pendiente' en la base de datos
    public static final UUID VALOR_DEFECTO = UUID.fromString("00000000-0000-0000-0000-000000000000");

    private UtilId(){
    }

    public static UUID generarId(){
        return UUID.randomUUID();
    }

    public static UUID valorDefecto(UUID id){
        return UtilObjeto.obtenerValorDefectoSiValorOriginalEsNulo(id, VALOR_DEFECTO);
    }
}
