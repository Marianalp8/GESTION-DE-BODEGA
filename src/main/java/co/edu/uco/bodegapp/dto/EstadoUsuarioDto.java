package co.edu.uco.bodegapp.dto;

import co.edu.uco.bodegapp.transversal.utilitario.UtilId;
import co.edu.uco.bodegapp.transversal.utilitario.UtilTexto;

import java.util.UUID;

//transporta un estado de usuario de un lado a otro, no valida nada
public class EstadoUsuarioDto {

    private UUID id;
    private String nombre;

    public EstadoUsuarioDto() {
        setId(UtilId.VALOR_DEFECTO);
        setNombre(UtilTexto.vacia);
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = UtilId.valorDefecto(id);
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = UtilTexto.getUtilTexto().primeraLetraMayuscula(nombre);
    }
}
