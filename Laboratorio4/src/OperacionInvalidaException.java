// Excepción propia del programa. Se lanza cada vez que una operación NO es válida
// (código repetido, nivel fuera de rango, dispositivo apagado, etc.).
// Extiende RuntimeException para no obligar a escribir "throws" en todos los métodos.
public class OperacionInvalidaException extends RuntimeException {

    // Constructor: recibe el mensaje claro que verá el usuario y se lo pasa a la clase padre.
    public OperacionInvalidaException(String mensaje) {
        super(mensaje); // RuntimeException guarda el mensaje; se recupera con getMessage()
    }
}