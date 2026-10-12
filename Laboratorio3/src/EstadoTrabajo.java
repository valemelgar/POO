// Clase abstracta que representa el estado en que se encuentra un trabajo.
// Cada transición (asignar, retirar, iniciar, completar, fallar) devuelve el ESTADO SIGUIENTE.
// Por defecto toda transición está prohibida y lanza una excepción; cada subclase sobrescribe
// únicamente las transiciones que sí permite. Así el estado decide qué puede hacerse con el trabajo.
public abstract class EstadoTrabajo {

    // Constructor: la clase no tiene atributos, no hay nada que inicializar.
    public EstadoTrabajo() {
    }

    // Nombre del estado, para mostrarlo en pantalla y agrupar en los reportes. Lo implementa cada subclase.
    public abstract String getNombre();

    // Indica si un trabajo en este estado puede asignarse a un lote. Por defecto no; solo "En cola" lo permite.
    public boolean puedeAsignarse() {
        return false;
    }

    // Transición: asignar el trabajo a un lote. Por defecto es inválida.
    public EstadoTrabajo asignar() {
        // throw lanza la excepción que arma transicionInvalida con el mensaje correspondiente.
        throw transicionInvalida("asignar a un lote");
    }

    // Transición: retirar el trabajo de un lote en preparación. Por defecto es inválida.
    public EstadoTrabajo retirar() {
        throw transicionInvalida("retirar de un lote");
    }

    // Transición: iniciar la ejecución del trabajo (cuando su lote inicia). Por defecto es inválida.
    public EstadoTrabajo iniciar() {
        throw transicionInvalida("iniciar la ejecución");
    }

    // Transición: registrar que el trabajo se completó. Por defecto es inválida.
    public EstadoTrabajo completar() {
        throw transicionInvalida("registrar como completado");
    }

    // Transición: registrar una ejecución fallida. Recibe los intentos realizados y los máximos permitidos
    // para que el estado decida si el trabajo vuelve a cola o se cancela. Por defecto es inválida.
    public EstadoTrabajo fallar(int intentos, int intentosMaximos) {
        throw transicionInvalida("registrar una ejecución fallida");
    }

    // Construye (sin lanzar) la excepción con un mensaje claro que incluye la operación y el estado actual.
    protected ReglaNegocioException transicionInvalida(String operacion) {
        return new ReglaNegocioException("No se puede " + operacion
                + " cuando el trabajo está '" + getNombre() + "'.");
    }

    // toString sobrescrito: al imprimir un estado se muestra su nombre.
    @Override
    public String toString() {
        return getNombre();
    }
}