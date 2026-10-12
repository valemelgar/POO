// Excepción propia del sistema: se lanza cada vez que una operación viola una regla del negocio
// (dato inválido, estado que no lo permite, capacidad excedida, código repetido, etc.).
// Hereda de RuntimeException para no tener que declarar "throws" en cada método que la lance.
public class ReglaNegocioException extends RuntimeException {

    // Constructor: recibe el mensaje claro que verá el usuario cuando se rechace la operación.
    public ReglaNegocioException(String mensaje) {
        // super(mensaje) guarda el mensaje en la clase padre para poder leerlo después con getMessage().
        super(mensaje);
    }
}