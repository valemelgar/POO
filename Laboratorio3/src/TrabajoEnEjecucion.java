// Estado "En ejecución": el trabajo está corriendo porque su lote ya inició.
public class TrabajoEnEjecucion extends EstadoTrabajo {

    // Constructor: solo llama al de la clase padre.
    public TrabajoEnEjecucion() {
        super();
    }

    // Nombre que se muestra para este estado.
    @Override
    public String getNombre() {
        return "En ejecución";
    }

    // Transición permitida: si la ejecución termina bien, el trabajo queda "Completado".
    @Override
    public EstadoTrabajo completar() {
        return new TrabajoCompletado();
    }

    // Transición permitida: si la ejecución falla, depende de los intentos que ya se usaron.
    @Override
    public EstadoTrabajo fallar(int intentos, int intentosMaximos) {
        // Si todavía quedan intentos, el trabajo vuelve a la cola para asignarse a otro lote.
        if (intentos < intentosMaximos) {
            return new TrabajoEnCola();
        }
        // Si ya agotó sus intentos, queda cancelado y no podrá asignarse de nuevo.
        return new TrabajoCancelado();
    }
}