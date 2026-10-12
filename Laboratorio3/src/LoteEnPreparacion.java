// Estado "En preparación": el lote se está armando; se pueden asignar y retirar trabajos.
public class LoteEnPreparacion extends EstadoLote {

    // Constructor: solo llama al de la clase padre.
    public LoteEnPreparacion() {
        super();
    }

    // Nombre que se muestra para este estado.
    @Override
    public String getNombre() {
        return "En preparación";
    }

    // En este estado SÍ se pueden modificar los trabajos del lote, por eso el método no hace nada
    // (no lanza excepción). Sobrescribe el comportamiento por defecto, que sí rechazaba.
    @Override
    public void validarModificacion() {
        // Intencionalmente vacío: significa "la modificación está permitida".
    }

    // Transición permitida: al iniciar, el lote pasa a "En ejecución".
    @Override
    public EstadoLote iniciar() {
        return new LoteEnEjecucion();
    }
}