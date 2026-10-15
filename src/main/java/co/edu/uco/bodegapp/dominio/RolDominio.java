package co.edu.uco.bodegapp.dominio;

import co.edu.uco.bodegapp.transversal.utilitario.UtilId;
import co.edu.uco.bodegapp.transversal.utilitario.UtilTexto;

import java.util.UUID;

//un rol que puede tener un usuario: 'Sin asignar', 'Administrador' o 'Repartidor'
public class RolDominio {

    private UUID id;
    private String nombre;

    private RolDominio(Builder builder) {
        this.id = builder.id;
        this.nombre = builder.nombre;
    }

    public UUID getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public static class Builder {

        private UUID id;
        private String nombre;

        public Builder() {
            id = UtilId.valorDefecto(id);
            nombre = UtilTexto.vacia;
        }

        public Builder id(UUID id) {
            this.id = UtilId.valorDefecto(id);
            return this;
        }

        public Builder nombre(String nombre) {
            this.nombre = UtilTexto.getUtilTexto().primeraLetraMayuscula(nombre);
            return this;
        }

        public RolDominio build() {
            return new RolDominio(this);
        }
    }
}
