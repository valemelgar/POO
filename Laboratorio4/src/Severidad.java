import java.util.Locale;

// Clase abstracta que representa la severidad de una alerta. Todas las severidades tienen
// los mismos datos (nombre, prioridad, plazo y crédito); solo cambian los valores.
// Una severidad nueva es una subclase nueva, sin tocar la bandeja ni la facturación.
public abstract class Severidad {

    // Nombre que se muestra ("Baja", "Alta", "Crítica").
    private String nombre;

    // Número que indica qué tan urgente es: mayor número = más urgente.
    private int prioridad;

    // Horas que se tienen para atender la alerta antes de que se considere retrasada.
    private int plazoHoras;

    // Quetzales que se acreditan a la oficina por cada hora de retraso.
    private double creditoPorHora;

    // Constructor protegido: solo lo usan las subclases.
    protected Severidad(String nombre, int prioridad, int plazoHoras, double creditoPorHora) {
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new OperacionInvalidaException("El nombre de la severidad no puede estar vacío.");
        }
        if (prioridad <= 0 || plazoHoras <= 0 || Double.isNaN(creditoPorHora) || creditoPorHora <= 0) {
            throw new OperacionInvalidaException("Los valores de la severidad deben ser positivos.");
        }
        this.nombre = nombre;
        this.prioridad = prioridad;
        this.plazoHoras = plazoHoras;
        this.creditoPorHora = creditoPorHora;
    }

    // Calcula el crédito en quetzales para cierta cantidad de horas de retraso.
    public double calcularCredito(int horasRetraso) {
        if (horasRetraso <= 0) {
            return 0; // sin retraso no hay crédito
        }
        return horasRetraso * creditoPorHora;
    }

    // Devuelve el nombre.
    public String getNombre() {
        return nombre;
    }

    // Devuelve la prioridad (mayor = más urgente).
    public int getPrioridad() {
        return prioridad;
    }

    // Devuelve el plazo de atención en horas.
    public int getPlazoHoras() {
        return plazoHoras;
    }

    // Devuelve el crédito por hora de retraso.
    public double getCreditoPorHora() {
        return creditoPorHora;
    }

    // Texto: solo el nombre de la severidad.
    @Override
    public String toString() {
        return nombre;
    }
}