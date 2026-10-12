import java.util.Locale;

// Nodo de cómputo de la granja: tiene un código único, una capacidad máxima de horas por lote
// y la indicación de si cuenta con GPU.
public class Nodo {

    // Atributos privados.
    private String codigo;            // identifica al nodo (único en el sistema)
    private double capacidadMaxima;   // horas máximas de cómputo que admite un lote en este nodo
    private boolean tieneGpu;         // true si el nodo cuenta con GPU

    // Constructor: valida los datos y los guarda.
    public Nodo(String codigo, double capacidadMaxima, boolean tieneGpu) {
        // El código no puede ser nulo ni estar vacío (trim() ignora los espacios).
        if (codigo == null || codigo.trim().isEmpty()) {
            throw new ReglaNegocioException("El código del nodo no puede estar vacío.");
        }
        // La capacidad debe ser un número positivo y finito.
        if (Double.isNaN(capacidadMaxima) || Double.isInfinite(capacidadMaxima) || capacidadMaxima <= 0) {
            throw new ReglaNegocioException("La capacidad del nodo debe ser un número mayor que cero.");
        }
        this.codigo = codigo.trim();
        this.capacidadMaxima = capacidadMaxima;
        this.tieneGpu = tieneGpu;
    }

    // Devuelve el código del nodo.
    public String getCodigo() {
        return codigo;
    }

    // Devuelve la capacidad máxima de horas por lote.
    public double getCapacidadMaxima() {
        return capacidadMaxima;
    }

    // Indica si el nodo tiene GPU.
    public boolean tieneGpu() {
        return tieneGpu;
    }

    // Texto con los datos del nodo, para mostrarlo en pantalla.
    @Override
    public String toString() {
        return String.format(Locale.US, "Nodo %s | Capacidad: %.2f horas | GPU: %s",
                codigo, capacidadMaxima, tieneGpu ? "Sí" : "No");
    }
}