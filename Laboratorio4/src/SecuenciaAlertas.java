// Lleva el contador de códigos de las alertas: 1, 2, 3, ...
// Es una clase aparte para que el código solo avance cuando de verdad se crea una alerta.
public class SecuenciaAlertas {

    // Código que tendrá la próxima alerta.
    private int siguiente;

    // Constructor: la primera alerta tendrá el código 1.
    public SecuenciaAlertas() {
        this.siguiente = 1;
    }

    // Entrega el código actual y avanza el contador (el ++ lo incrementa después de devolverlo).
    public int siguiente() {
        return siguiente++;
    }
}