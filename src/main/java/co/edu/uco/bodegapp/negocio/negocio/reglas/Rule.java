package co.edu.uco.bodegapp.negocio.negocio.reglas;

//una regla de negocio: revisa algo y, si no se cumple, lanza una excepcion con el mensaje para el usuario
public interface Rule<O> {

    void ejecutar(O... datos);
}
