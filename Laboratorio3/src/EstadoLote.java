// Clase abstracta que representa el estado de un lote.
// Funciona igual que EstadoTrabajo: cada transición devuelve el estado siguiente y, por defecto,
// lanza una excepción; las subclases sobrescriben solo lo que su estado permite.
public abstract class EstadoLote {

    // Constructor: la clase no tiene atributos, no hay nada que inicializar.
    public EstadoLote() {
    }

    // Nombre del estado, para mostrarlo en pantalla. Lo implementa cada subclase.
    public abstract String getNombre();

    // Indica si el lote está cerrado. Por defecto no; solo "Cerrado" lo sobrescribe con true.
    public boolean estaCerrado() {
        return false;
    }

    // Verifica que en este estado se puedan asignar o retirar trabajos. Por defecto lo rechaza.
    public void validarModificacion() {
        throw transicionInvalida("asignar o retirar trabajos");
    }

    // Transición: iniciar el lote. Por defecto es inválida.
    public EstadoLote iniciar() {
        throw transicionInvalida("iniciar el lote");
    }

    // Transición: registrar el resultado de un trabajo. El parámetro indica si es el último resultado
    // que faltaba (en ese caso el lote debe cerrarse). Por defecto es inválida.
    public EstadoLote registrarResultado(boolean esElUltimo) {
        throw transicionInvalida("registrar resultados");
    }

    // Construye (sin lanzar) la excepción con un mensaje claro que incluye la operación y el estado actual.
    protected ReglaNegocioException transicionInvalida(String operacion) {
        return new ReglaNegocioException("No se puede " + operacion
                + " cuando el lote está '" + getNombre() + "'.");
    }

    // toString sobrescrito: al imprimir un estado se muestra su nombre.
    @Override
    public String toString() {
        return getNombre();
    }
}