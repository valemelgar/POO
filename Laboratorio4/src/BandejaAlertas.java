import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.PriorityQueue;

// Bandeja de alertas pendientes. Implementa Iterable<Alerta>: quien la recorra con for-each
// recibe las alertas en orden de urgencia sin saber cómo se guardan ni cómo se ordenan.
public class BandejaAlertas implements Iterable<Alerta> {

    // Cola de prioridad: siempre deja primero a la alerta MÁS urgente según Alerta.compareTo.
    private PriorityQueue<Alerta> alertas;

    // Constructor: crea la cola vacía. No se le pasa comparador porque Alerta ya es Comparable.
    public BandejaAlertas() {
        this.alertas = new PriorityQueue<Alerta>();
    }

    // Agrega una alerta pendiente a la bandeja.
    public void agregar(Alerta alerta) {
        if (alerta == null) {
            throw new OperacionInvalidaException("No se puede agregar una alerta nula.");
        }
        alertas.add(alerta);
    }

    // Saca y devuelve la alerta más urgente. Si no hay ninguna, rechaza la operación.
    public Alerta extraerMasUrgente() {
        if (alertas.isEmpty()) {
            throw new OperacionInvalidaException("No hay alertas pendientes en la bandeja.");
        }
        return alertas.poll(); // poll() quita y devuelve la cabeza de la cola
    }

    // Indica si no hay alertas pendientes.
    public boolean estaVacia() {
        return alertas.isEmpty();
    }

    // Cantidad de alertas pendientes.
    public int tamanio() {
        return alertas.size();
    }

    // Recorrido en orden de urgencia. Se recorre una COPIA ordenada porque el for-each normal
    // de una PriorityQueue NO sigue el orden. La copia no deja modificar la bandeja real.
    @Override
    public Iterator<Alerta> iterator() {
        ArrayList<Alerta> copia = new ArrayList<Alerta>(alertas);
        Collections.sort(copia); // usa Alerta.compareTo, el criterio vive solo allí
        return Collections.unmodifiableList(copia).iterator();
    }
}